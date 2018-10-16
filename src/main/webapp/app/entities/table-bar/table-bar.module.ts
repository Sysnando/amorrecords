import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmorrecordsSharedModule } from '../../shared';
import {
    Table_barService,
    Table_barPopupService,
    Table_barComponent,
    Table_barDetailComponent,
    Table_barDialogComponent,
    Table_barPopupComponent,
    Table_barDeletePopupComponent,
    Table_barDeleteDialogComponent,
    table_barRoute,
    table_barPopupRoute,
} from './';

const ENTITY_STATES = [
    ...table_barRoute,
    ...table_barPopupRoute,
];

@NgModule({
    imports: [
        AmorrecordsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        Table_barComponent,
        Table_barDetailComponent,
        Table_barDialogComponent,
        Table_barDeleteDialogComponent,
        Table_barPopupComponent,
        Table_barDeletePopupComponent,
    ],
    entryComponents: [
        Table_barComponent,
        Table_barDialogComponent,
        Table_barPopupComponent,
        Table_barDeleteDialogComponent,
        Table_barDeletePopupComponent,
    ],
    providers: [
        Table_barService,
        Table_barPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmorrecordsTable_barModule {}
