import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TableBarComponent } from './table-bar.component';
import { TableBarDetailComponent } from './table-bar-detail.component';
import { TableBarPopupComponent } from './table-bar-dialog.component';
import { TableBarDeletePopupComponent } from './table-bar-delete-dialog.component';

export const TableBarRoute: Routes = [
    {
        path: 'table-bar',
        component: TableBarComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableBars'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'table-bar/:id',
        component: TableBarDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableBars'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const TableBarPopupRoute: Routes = [
    {
        path: 'table-bar-new',
        component: TableBarPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableBars'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'table-bar/:id/edit',
        component: TableBarPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableBars'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'table-bar/:id/delete',
        component: TableBarDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableBars'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
