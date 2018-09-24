import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Disco } from './disco.model';
import { DiscoPopupService } from './disco-popup.service';
import { DiscoService } from './disco.service';

@Component({
    selector: 'jhi-disco-dialog',
    templateUrl: './disco-dialog.component.html'
})
export class DiscoDialogComponent implements OnInit {

    disco: Disco;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private discoService: DiscoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.disco.id !== undefined) {
            this.subscribeToSaveResponse(
                this.discoService.update(this.disco));
        } else {
            this.subscribeToSaveResponse(
                this.discoService.create(this.disco));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Disco>>) {
        result.subscribe((res: HttpResponse<Disco>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Disco) {
        this.eventManager.broadcast({ name: 'discoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-disco-popup',
    template: ''
})
export class DiscoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private discoPopupService: DiscoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.discoPopupService
                    .open(DiscoDialogComponent as Component, params['id']);
            } else {
                this.discoPopupService
                    .open(DiscoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
