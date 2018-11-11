import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmorrecordsSharedModule } from '../../shared';
import {
    TableBarService,
    TableBarPopupService,
    TableBarComponent,
    TableBarDetailComponent,
    TableBarDialogComponent,
    TableBarPopupComponent,
    TableBarDeletePopupComponent,
    TableBarDeleteDialogComponent,
    TableBarRoute,
    TableBarPopupRoute,
} from './';

const ENTITY_STATES = [
    ...TableBarRoute,
    ...TableBarPopupRoute,
];

@NgModule({
    imports: [
        AmorrecordsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TableBarComponent,
        TableBarDetailComponent,
        TableBarDialogComponent,
        TableBarDeleteDialogComponent,
        TableBarPopupComponent,
        TableBarDeletePopupComponent,
    ],
    entryComponents: [
        TableBarComponent,
        TableBarDialogComponent,
        TableBarPopupComponent,
        TableBarDeleteDialogComponent,
        TableBarDeletePopupComponent,
    ],
    providers: [
        TableBarService,
        TableBarPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmorrecordsTableBarModule {}
