import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bar } from './bar.model';
import { BarService } from './bar.service';

@Component({
    selector: 'jhi-bar-detail',
    templateUrl: './bar-detail.component.html'
})
export class BarDetailComponent implements OnInit, OnDestroy {

    bar: Bar;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private barService: BarService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBars();
    }

    load(id) {
        this.barService.find(id)
            .subscribe((barResponse: HttpResponse<Bar>) => {
                this.bar = barResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBars() {
        this.eventSubscriber = this.eventManager.subscribe(
            'barListModification',
            (response) => this.load(this.bar.id)
        );
    }
}
