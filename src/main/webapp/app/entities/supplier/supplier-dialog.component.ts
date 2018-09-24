import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Supplier } from './supplier.model';
import { SupplierPopupService } from './supplier-popup.service';
import { SupplierService } from './supplier.service';

@Component({
    selector: 'jhi-supplier-dialog',
    templateUrl: './supplier-dialog.component.html'
})
export class SupplierDialogComponent implements OnInit {

    supplier: Supplier;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private supplierService: SupplierService,
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
        if (this.supplier.id !== undefined) {
            this.subscribeToSaveResponse(
                this.supplierService.update(this.supplier));
        } else {
            this.subscribeToSaveResponse(
                this.supplierService.create(this.supplier));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Supplier>>) {
        result.subscribe((res: HttpResponse<Supplier>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Supplier) {
        this.eventManager.broadcast({ name: 'supplierListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-supplier-popup',
    template: ''
})
export class SupplierPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private supplierPopupService: SupplierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.supplierPopupService
                    .open(SupplierDialogComponent as Component, params['id']);
            } else {
                this.supplierPopupService
                    .open(SupplierDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
