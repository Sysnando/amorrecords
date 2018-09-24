import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AmorrecordsProductModule } from './product/product.module';
import { AmorrecordsActivityModule } from './activity/activity.module';
import { AmorrecordsSupplierModule } from './supplier/supplier.module';
import { AmorrecordsBarModule } from './bar/bar.module';
import { AmorrecordsDiscoModule } from './disco/disco.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AmorrecordsProductModule,
        AmorrecordsActivityModule,
        AmorrecordsSupplierModule,
        AmorrecordsBarModule,
        AmorrecordsDiscoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmorrecordsEntityModule {}
