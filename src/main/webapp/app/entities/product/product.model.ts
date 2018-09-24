import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public disco?: BaseEntity,
        public bar?: BaseEntity,
        public activity?: BaseEntity,
        public supplier?: BaseEntity,
    ) {
    }
}
