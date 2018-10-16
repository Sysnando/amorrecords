import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { Table_barComponent } from './table-bar.component';
import { Table_barDetailComponent } from './table-bar-detail.component';
import { Table_barPopupComponent } from './table-bar-dialog.component';
import { Table_barDeletePopupComponent } from './table-bar-delete-dialog.component';

export const table_barRoute: Routes = [
    {
        path: 'table-bar',
        component: Table_barComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Table_bars'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'table-bar/:id',
        component: Table_barDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Table_bars'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const table_barPopupRoute: Routes = [
    {
        path: 'table-bar-new',
        component: Table_barPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Table_bars'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'table-bar/:id/edit',
        component: Table_barPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Table_bars'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'table-bar/:id/delete',
        component: Table_barDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Table_bars'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
