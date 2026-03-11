import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { HistoryService } from '../../historyService';
import { FilterLabelI, OptionsI, QueryParameterI } from '../../mhdbdb-graph.service';
import { FormDirective } from '../formDirective';

@Component({
    selector: 'dhpp-form-workSearch',
    templateUrl: './formWorkSearch.html',
    styleUrls: ['./formWorkSearch.scss']
})
export class FormWorkSearchComponent<qT extends QueryParameterI<f, o>, f extends FilterLabelI, o extends OptionsI, instanceClass> extends FormDirective<qT,f,o,instanceClass> implements OnInit, OnDestroy {

    constructor(
        public historyService: HistoryService<qT, f, o, instanceClass>,
        public help: MatDialog
    ) {
        super(historyService, help)
    }

    initHtmlForm(filterMap: f) {
        console.log("FormWorkSearchComponent.initHtmlForm", {filter: filterMap});
        this.form = new FormGroup({
            label: new FormControl(filterMap.label),
            isLabelActive: new FormControl(filterMap.isLabelActive),
        });
    }

    loadFilter(filterMap: f) {
        console.log("FormWorkSearchComponent.loadFilter", {filter: filterMap});
        if (this.form.get('isLabelActive').value != filterMap.isLabelActive) {
            this.form.patchValue({
                isLabelActive: filterMap.isLabelActive,
            })
        }

        if (this.form.get('label').value != filterMap.label) {
            this.form.patchValue({
                label: filterMap.label,
            })
        }
    }

    onValueChanges(value) {
        console.log("FormWorkSearchComponent.onValueChanges", {value: value});
        let changed:boolean = false
        if (this.qp.filter.isLabelActive != value.isLabelActive) {
            this.qp.filter.isLabelActive = value.isLabelActive
            changed = true
        }
        if (this.qp.filter.label != value.label) {
            this.qp.filter.label = value.label
            changed = true
        }
        return changed
    }

    openHelp() {
        const dialogRef = this.help.open(FormWorkSearchHelpComponent);
        dialogRef.afterClosed().subscribe(result => {
            console.log(`Dialog result: ${result}`);
        });
    }

    ngOnInit() {
        super.ngOnInit()
    }

    ngOnDestroy(): void {
        super.ngOnDestroy()
    }
}

@Component({
    selector: 'dhpp-form-workSearch-help',
    templateUrl: './formWorkSearchHelp.html',
})
export class FormWorkSearchHelpComponent { }
