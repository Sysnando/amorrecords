import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TableBar } from './table-bar.model';
import { TableBarPopupService } from './table-bar-popup.service';
import { TableBarService } from './table-bar.service';

@Component({
    selector: 'jhi-table-bar-dialog',
    templateUrl: './table-bar-dialog.component.html'
})
export class TableBarDialogComponent implements OnInit {

    table_bar: TableBar;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tableBarService: TableBarService,
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
                this.tableBarService.update(this.table_bar));
        } else {
            this.subscribeToSaveResponse(
                this.tableBarService.create(this.table_bar));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TableBar>>) {
        result.subscribe((res: HttpResponse<TableBar>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TableBar) {
        this.eventManager.broadcast({ name: 'tableBarListModification', content: 'OK'});
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
export class TableBarPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tableBarPopupService: TableBarPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tableBarPopupService.open(TableBarDialogComponent as Component, params['id']);
            } else {
                this.tableBarPopupService.open(TableBarDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
