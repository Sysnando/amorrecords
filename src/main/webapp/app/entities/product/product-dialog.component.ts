import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Product } from './product.model';
import { ProductPopupService } from './product-popup.service';
import { ProductService } from './product.service';
import { Disco, DiscoService } from '../disco';
import { Bar, BarService } from '../bar';
import { Activity, ActivityService } from '../activity';
import { Supplier, SupplierService } from '../supplier';

@Component({
    selector: 'jhi-product-dialog',
    templateUrl: './product-dialog.component.html'
})
export class ProductDialogComponent implements OnInit {

    product: Product;
    isSaving: boolean;

    discos: Disco[];

    bars: Bar[];

    activities: Activity[];

    suppliers: Supplier[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private productService: ProductService,
        private discoService: DiscoService,
        private barService: BarService,
        private activityService: ActivityService,
        private supplierService: SupplierService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.discoService
            .query({filter: 'product-is-null'})
            .subscribe((res: HttpResponse<Disco[]>) => {
                if (!this.product.disco || !this.product.disco.id) {
                    this.discos = res.body;
                } else {
                    this.discoService
                        .find(this.product.disco.id)
                        .subscribe((subRes: HttpResponse<Disco>) => {
                            this.discos = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.barService
            .query({filter: 'product-is-null'})
            .subscribe((res: HttpResponse<Bar[]>) => {
                if (!this.product.bar || !this.product.bar.id) {
                    this.bars = res.body;
                } else {
                    this.barService
                        .find(this.product.bar.id)
                        .subscribe((subRes: HttpResponse<Bar>) => {
                            this.bars = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.activityService.query()
            .subscribe((res: HttpResponse<Activity[]>) => { this.activities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.supplierService.query()
            .subscribe((res: HttpResponse<Supplier[]>) => { this.suppliers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.product.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productService.update(this.product));
        } else {
            this.subscribeToSaveResponse(
                this.productService.create(this.product));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Product>>) {
        result.subscribe((res: HttpResponse<Product>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Product) {
        this.eventManager.broadcast({ name: 'productListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDiscoById(index: number, item: Disco) {
        return item.id;
    }

    trackBarById(index: number, item: Bar) {
        return item.id;
    }

    trackActivityById(index: number, item: Activity) {
        return item.id;
    }

    trackSupplierById(index: number, item: Supplier) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-popup',
    template: ''
})
export class ProductPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productPopupService: ProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.productPopupService
                    .open(ProductDialogComponent as Component, params['id']);
            } else {
                this.productPopupService
                    .open(ProductDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
