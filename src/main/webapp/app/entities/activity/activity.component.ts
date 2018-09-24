import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Activity } from './activity.model';
import { ActivityService } from './activity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-activity',
    templateUrl: './activity.component.html'
})
export class ActivityComponent implements OnInit, OnDestroy {
activities: Activity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private activityService: ActivityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.activityService.query().subscribe(
            (res: HttpResponse<Activity[]>) => {
                this.activities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInActivities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Activity) {
        return item.id;
    }
    registerChangeInActivities() {
        this.eventSubscriber = this.eventManager.subscribe('activityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
