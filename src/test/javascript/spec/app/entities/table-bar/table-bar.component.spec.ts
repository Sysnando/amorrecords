/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmorrecordsTestModule } from '../../../test.module';
import { TableBarComponent } from '../../../../../../main/webapp/app/entities/table-bar/table-bar.component';
import { TableBarService } from '../../../../../../main/webapp/app/entities/table-bar/table-bar.service';
import { TableBar } from '../../../../../../main/webapp/app/entities/table-bar/table-bar.model';

describe('Component Tests', () => {

    describe('TableBar Management Component', () => {
        let comp: TableBarComponent;
        let fixture: ComponentFixture<TableBarComponent>;
        let service: TableBarService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmorrecordsTestModule],
                declarations: [TableBarComponent],
                providers: [
                    TableBarService
                ]
            })
            .overrideTemplate(TableBarComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TableBarComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TableBarService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TableBar(123)],
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
