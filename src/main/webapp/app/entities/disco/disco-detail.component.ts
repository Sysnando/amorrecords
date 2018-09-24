import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Disco } from './disco.model';
import { DiscoService } from './disco.service';

@Component({
    selector: 'jhi-disco-detail',
    templateUrl: './disco-detail.component.html'
})
export class DiscoDetailComponent implements OnInit, OnDestroy {

    disco: Disco;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private discoService: DiscoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDiscos();
    }

    load(id) {
        this.discoService.find(id)
            .subscribe((discoResponse: HttpResponse<Disco>) => {
                this.disco = discoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDiscos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'discoListModification',
            (response) => this.load(this.disco.id)
        );
    }
}
