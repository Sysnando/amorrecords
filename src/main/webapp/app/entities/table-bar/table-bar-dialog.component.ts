import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Table_bar } from './table-bar.model';
import { Table_barPopupService } from './table-bar-popup.service';
import { Table_barService } from './table-bar.service';

@Component({
    selector: 'jhi-table-bar-dialog',
    templateUrl: './table-bar-dialog.component.html'
})
export class Table_barDialogComponent implements OnInit {

    table_bar: Table_bar;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private table_barService: Table_barService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.table_bar.id !== undefined) {
            this.subscribeToSaveResponse(
                this.table_barService.update(this.table_bar));
        } else {
            this.subscribeToSaveResponse(
                this.table_barService.create(this.table_bar));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Table_bar>>) {
        result.subscribe((res: HttpResponse<Table_bar>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Table_bar) {
        this.eventManager.broadcast({ name: 'table_barListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-table-bar-popup',
    template: ''
})
export class Table_barPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private table_barPopupService: Table_barPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.table_barPopupService
                    .open(Table_barDialogComponent as Component, params['id']);
            } else {
                this.table_barPopupService
                    .open(Table_barDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
