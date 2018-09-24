import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmorrecordsSharedModule } from '../../shared';
import {
    DiscoService,
    DiscoPopupService,
    DiscoComponent,
    DiscoDetailComponent,
    DiscoDialogComponent,
    DiscoPopupComponent,
    DiscoDeletePopupComponent,
    DiscoDeleteDialogComponent,
    discoRoute,
    discoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...discoRoute,
    ...discoPopupRoute,
];

@NgModule({
    imports: [
        AmorrecordsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DiscoComponent,
        DiscoDetailComponent,
        DiscoDialogComponent,
        DiscoDeleteDialogComponent,
        DiscoPopupComponent,
        DiscoDeletePopupComponent,
    ],
    entryComponents: [
        DiscoComponent,
        DiscoDialogComponent,
        DiscoPopupComponent,
        DiscoDeleteDialogComponent,
        DiscoDeletePopupComponent,
    ],
    providers: [
        DiscoService,
        DiscoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmorrecordsDiscoModule {}
