import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Disco } from './disco.model';
import { DiscoService } from './disco.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-disco',
    templateUrl: './disco.component.html'
})
export class DiscoComponent implements OnInit, OnDestroy {
discos: Disco[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private discoService: DiscoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.discoService.query().subscribe(
            (res: HttpResponse<Disco[]>) => {
                this.discos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDiscos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Disco) {
        return item.id;
    }
    registerChangeInDiscos() {
        this.eventSubscriber = this.eventManager.subscribe('discoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
