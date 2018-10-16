/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmorrecordsTestModule } from '../../../test.module';
import { Table_barComponent } from '../../../../../../main/webapp/app/entities/table-bar/table-bar.component';
import { Table_barService } from '../../../../../../main/webapp/app/entities/table-bar/table-bar.service';
import { Table_bar } from '../../../../../../main/webapp/app/entities/table-bar/table-bar.model';

describe('Component Tests', () => {

    describe('Table_bar Management Component', () => {
        let comp: Table_barComponent;
        let fixture: ComponentFixture<Table_barComponent>;
        let service: Table_barService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmorrecordsTestModule],
                declarations: [Table_barComponent],
                providers: [
                    Table_barService
                ]
            })
            .overrideTemplate(Table_barComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Table_barComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Table_barService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Table_bar(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.table_bars[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
