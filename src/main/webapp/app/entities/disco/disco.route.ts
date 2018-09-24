import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DiscoComponent } from './disco.component';
import { DiscoDetailComponent } from './disco-detail.component';
import { DiscoPopupComponent } from './disco-dialog.component';
import { DiscoDeletePopupComponent } from './disco-delete-dialog.component';

export const discoRoute: Routes = [
    {
        path: 'disco',
        component: DiscoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Discos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'disco/:id',
        component: DiscoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Discos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const discoPopupRoute: Routes = [
    {
        path: 'disco-new',
        component: DiscoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Discos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'disco/:id/edit',
        component: DiscoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Discos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'disco/:id/delete',
        component: DiscoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Discos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
