import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Table_bar } from './table-bar.model';
import { Table_barService } from './table-bar.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-table-bar',
    templateUrl: './table-bar.component.html'
})
export class Table_barComponent implements OnInit, OnDestroy {
table_bars: Table_bar[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private table_barService: Table_barService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.table_barService.query().subscribe(
            (res: HttpResponse<Table_bar[]>) => {
                this.table_bars = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTable_bars();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Table_bar) {
        return item.id;
    }
    registerChangeInTable_bars() {
        this.eventSubscriber = this.eventManager.subscribe('table_barListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
