import { BaseEntity } from './../../shared';

export class Table_bar implements BaseEntity {
    constructor(
        public id?: number,
        public activities?: BaseEntity[],
    ) {
    }
}
