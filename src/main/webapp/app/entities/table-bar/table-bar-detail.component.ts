import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Table_bar } from './table-bar.model';
import { Table_barService } from './table-bar.service';

@Component({
    selector: 'jhi-table-bar-detail',
    templateUrl: './table-bar-detail.component.html'
})
export class Table_barDetailComponent implements OnInit, OnDestroy {

    table_bar: Table_bar;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private table_barService: Table_barService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTable_bars();
    }

    load(id) {
        this.table_barService.find(id)
            .subscribe((table_barResponse: HttpResponse<Table_bar>) => {
                this.table_bar = table_barResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTable_bars() {
        this.eventSubscriber = this.eventManager.subscribe(
            'table_barListModification',
            (response) => this.load(this.table_bar.id)
        );
    }
}
