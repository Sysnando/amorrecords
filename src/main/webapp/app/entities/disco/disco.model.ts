import { BaseEntity } from './../../shared';

export const enum Condition {
    'MINT',
    'NEAR_MINT',
    'VERY_GOOD_PLUS',
    'VERY_GOOD',
    'GOOD_PLUS',
    'GOOD',
    'FAIR',
    'POOR'
}

export const enum DiscoType {
    'NEW',
    'USED'
}

export class Disco implements BaseEntity {
    constructor(
        public id?: number,
        public query?: string,
        public type?: string,
        public title?: string,
        public releaseTitle?: string,
        public credit?: string,
        public artist?: string,
        public anv?: string,
        public label?: string,
        public genre?: string,
        public style?: string,
        public country?: string,
        public year?: string,
        public format?: string,
        public catno?: string,
        public barCode?: string,
        public track?: string,
        public submitter?: string,
        public contributor?: string,
        public condition?: Condition,
        public discoType?: DiscoType,
        public datePurchase?: any,
        public dateSale?: any,
        public cover?: number,
        public detail?: number,
        public pricePurchase?: number,
        public priceSale?: number,
        public priceSaleStore?: number,
        public priceSaleThirdParty?: number,
        public priceSaleInReal?: number,
    ) {
    }
}
