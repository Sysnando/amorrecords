import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Activity } from './activity.model';
import { ActivityPopupService } from './activity-popup.service';
import { ActivityService } from './activity.service';
// import { Supplier, SupplierService } from '../supplier';
// import {Product} from '../product/product.model';
// import {ProductService} from '../product/product.service';
import {Bar} from '../bar/bar.model';
import {BarService} from '../bar/bar.service';
import * as moment from 'moment';

@Component({
    selector: 'jhi-activity-dialog',
    templateUrl: './activity-dialog.component.html'
})
export class ActivityDialogComponent implements OnInit {

    activity: Activity;
    isSaving: boolean;

    // suppliers: Supplier[];
    // products: Product[];
    bar: Bar[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private activityService: ActivityService,
        // private supplierService: SupplierService,
        // private productService: ProductService,
        private barService: BarService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.activity.date = moment().format("YYYY-MM-DD");
        /*this.productService
            .findAll()
            .subscribe((res: HttpResponse<Product[]>) => {
                this.products = res.body;
            }, (res: HttpErrorResponse) => this.onError(res.message))
        ;*/

        this.barService
            .findAll()
            .subscribe((res: HttpResponse<Bar[]>) => {
                this.bar = res.body;
            }, (res: HttpErrorResponse) => this.onError(res.message));

        /* this.supplierService
            .query({filter: 'activity-is-null'})
            .subscribe((res: HttpResponse<Supplier[]>) => {
                if (!this.activity.supplier || !this.activity.supplier.id) {
                    this.suppliers = res.body;
                } else {
                    this.supplierService
                        .find(this.activity.supplier.id)
                        .subscribe((subRes: HttpResponse<Supplier>) => {
                            this.suppliers = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));*/
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        console.log(this.activity)
        this.isSaving = true;
        if (this.activity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.activityService.update(this.activity));
        } else {
            this.subscribeToSaveResponse(
                this.activityService.create(this.activity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Activity>>) {
        result.subscribe((res: HttpResponse<Activity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Activity) {
        this.eventManager.broadcast({ name: 'activityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBarById(index: number, item: Bar) {
        return item.id;
    }

    /*trackProductById(index: number, item: Product) {
        return item.id;
    }*/
    /*
        trackSupplierById(index: number, item: Supplier) {
        return item.id;
    }*/

    public onChange(bar: Bar):void{
        this.activity.quantity = 1;
        this.activity.amountUnit = bar.value;
        this.activity.amountTotal = bar.value * this.activity.quantity;
    }

    public updateTotal():void{
        if(this.activity.amountUnit != 0){
            this.activity.amountTotal = this.activity.amountUnit * this.activity.quantity;
        } else {
            this.activity.amountTotal = 0;
        }
    }
}

@Component({
    selector: 'jhi-activity-popup',
    template: ''
})
export class ActivityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private activityPopupService: ActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.activityPopupService
                    .open(ActivityDialogComponent as Component, params['id']);
            } else {
                this.activityPopupService
                    .open(ActivityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
