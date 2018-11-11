import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TableBar } from './table-bar.model';
import { TableBarService } from './table-bar.service';

@Component({
    selector: 'jhi-table-bar-detail',
    templateUrl: './table-bar-detail.component.html'
})
export class TableBarDetailComponent implements OnInit, OnDestroy {

    tableBar: TableBar;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tableBarService: TableBarService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTableBars();
    }

    load(id) {
        this.tableBarService.find(id)
            .subscribe((tableBarResponse: HttpResponse<TableBar>) => {
                this.tableBar = tableBarResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTableBars() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tableBarListModification',
            (response) => this.load(this.tableBar.id)
        );
    }
}
