import { BaseEntity } from './../../shared';

export class Bar implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
    ) {
    }
}
