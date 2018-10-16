import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Table_bar } from './table-bar.model';
import { Table_barPopupService } from './table-bar-popup.service';
import { Table_barService } from './table-bar.service';

@Component({
    selector: 'jhi-table-bar-delete-dialog',
    templateUrl: './table-bar-delete-dialog.component.html'
})
export class Table_barDeleteDialogComponent {

    table_bar: Table_bar;

    constructor(
        private table_barService: Table_barService,
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
export class Table_barDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private table_barPopupService: Table_barPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.table_barPopupService
                .open(Table_barDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
