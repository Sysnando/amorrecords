import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TableBar } from './table-bar.model';
import { TableBarPopupService } from './table-bar-popup.service';
import { TableBarService } from './table-bar.service';

@Component({
    selector: 'jhi-table-bar-delete-dialog',
    templateUrl: './table-bar-delete-dialog.component.html'
})
export class TableBarDeleteDialogComponent {

    table_bar: TableBar;

    constructor(
        private table_barService: TableBarService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.table_barService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'table_barListModification',
                content: 'Deleted an table_bar'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-table-bar-delete-popup',
    template: ''
})
export class TableBarDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private table_barPopupService: TableBarPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.table_barPopupService
                .open(TableBarDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
