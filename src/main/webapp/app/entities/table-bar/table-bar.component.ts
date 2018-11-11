import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TableBar } from './table-bar.model';
import { TableBarService } from './table-bar.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-table-bar',
    templateUrl: './table-bar.component.html'
})
export class TableBarComponent implements OnInit, OnDestroy {
table_bars: TableBar[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tableBarService: TableBarService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tableBarService.query().subscribe(
            (res: HttpResponse<TableBar[]>) => {
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
        this.registerChangeInTableBars();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TableBar) {
        return item.id;
    }
    registerChangeInTableBars() {
        this.eventSubscriber = this.eventManager.subscribe('tableBarListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
