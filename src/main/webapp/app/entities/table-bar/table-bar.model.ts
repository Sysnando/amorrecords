import { BaseEntity } from './../../shared';

export class TableBar implements BaseEntity {
    constructor(
        public id?: number,
        public customerName?: string,
        public date?: any,
        public activities?: BaseEntity[],
    ) {
    }
}
