import { BaseEntity } from './../../shared';

export class Supplier implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public contry?: string,
        public email?: string,
        public phone?: string,
        public site?: string,
        public datePurchase?: any,
        public contact?: string,
        public products?: BaseEntity[],
    ) {
    }
}
