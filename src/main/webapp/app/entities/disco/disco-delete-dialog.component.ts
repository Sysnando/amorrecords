import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Disco } from './disco.model';
import { DiscoPopupService } from './disco-popup.service';
import { DiscoService } from './disco.service';

@Component({
    selector: 'jhi-disco-delete-dialog',
    templateUrl: './disco-delete-dialog.component.html'
})
export class DiscoDeleteDialogComponent {

    disco: Disco;

    constructor(
        private discoService: DiscoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.discoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'discoListModification',
                content: 'Deleted an disco'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-disco-delete-popup',
    template: ''
})
export class DiscoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private discoPopupService: DiscoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.discoPopupService
                .open(DiscoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
