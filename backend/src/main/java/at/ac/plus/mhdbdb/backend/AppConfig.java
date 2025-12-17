package at.ac.plus.mhdbdb.backend;

import java.util.function.Consumer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.base.AbstractRepository;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ontotext.graphdb.repository.http.GraphDBHTTPRepository;
import com.ontotext.graphdb.repository.http.GraphDBHTTPRepositoryBuilder;

@Configuration
public class AppConfig {
    @Value("${target.host}")
    protected String targetHost;

    @Value("${target.repository:dhPLUS}")
    protected String targetRepository;

    @Value("${target.host.engine:graphdb}")
    protected String targetHostEngine;

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    Consumer<HttpClientBuilder> createSslBuilderConsumer() {
        return new HttpBuilderConsumerSsl();
    }

    @Bean
    HostnameVerifier createIgnoringHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                logger.debug("Verifying {} against {}", hostname, session.getPeerHost());
                boolean ret = hostname.equals("localhost") || hostname.equals(session.getPeerHost());
                if (!ret) {
                    logger.warn("{} failed to verify against {}", hostname, session.getPeerHost());
                }
                return ret;
            }
        };
        return hostnameVerifier;
    }

    /**
     * creates and returns the repository connection according to the target.host settings
     * This will be a connection to either a {@link GraphDBHTTPRepository} or a {@link SPARQLRepository}
     * @return a connection to the repository
     */
    @Bean
    protected RepositoryConnection getRepositoryConnection() {
        RepositoryConnection connection = null;
        AbstractRepository repo = null;
        switch (targetHostEngine) {
            case "fuseki":
                // TODO: we will prolly need to deal with self signed certificates at some point...
                // and maybe think about HTTPRepository?
                // see https://rdf4j.org/documentation/programming/repository/
                repo = new SPARQLRepository(targetHost);
                connection = repo.getConnection();
                break;
        
            default:
                repo = new GraphDBHTTPRepositoryBuilder()
                    .withServerUrl(targetHost)
                    .withRepositoryId(targetRepository)
                    .withHttpClientSetup(createSslBuilderConsumer())
                    .build();
                connection = repo.getConnection();
                break;
        }
        return connection;
    }    
}
