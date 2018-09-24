import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bar } from './bar.model';
import { BarService } from './bar.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-bar',
    templateUrl: './bar.component.html'
})
export class BarComponent implements OnInit, OnDestroy {
bars: Bar[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private barService: BarService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.barService.query().subscribe(
            (res: HttpResponse<Bar[]>) => {
                this.bars = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBars();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Bar) {
        return item.id;
    }
    registerChangeInBars() {
        this.eventSubscriber = this.eventManager.subscribe('barListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
