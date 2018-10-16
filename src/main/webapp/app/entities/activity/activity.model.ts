import { BaseEntity } from './../../shared';
// import {Supplier} from '../supplier/supplier.model';
// import {Product} from '../product/product.model';
import {Bar} from '../bar/bar.model';

export const enum TypePayment {
    'NU',
    'CC',
    'CD',
    'CO',
    'CS',
    'DE',
    'TR',
    'MB',
    'OU',
    'CH',
    'LC',
    'PR',
    'MBWAY'
}

export const enum Operation {
    'IN',
    'OUT',
    'OPEN',
    'CLOSE',
    'POINT'
}

export class Activity implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        //public dueDate?: any,
        public reference?: string,
        public description?: string,
        public quantity?: number,
        public amountUnit?: number,
        public amountTotal?: number,
        public invoiceNumber?: string,
        public paymentType?: TypePayment,
        public operation?: Operation,
        // public supplier?: Supplier,
        // public products?: Product,
        public bar?: Bar,
    ) {
    }
}
