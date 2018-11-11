/!* tslint:disable max-line-length *!/
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AmorrecordsTestModule } from '../../../test.module';
import { TableBarDetailComponent } from '../../../../../../main/webapp/app/entities/table-bar/table-bar-detail.component';
import { TableBarService } from '../../../../../../main/webapp/app/entities/table-bar/table-bar.service';
import { TableBar } from '../../../../../../main/webapp/app/entities/table-bar/table-bar.model';

describe('Component Tests', () => {

    describe('TableBar Management Detail Component', () => {
        let comp: TableBarDetailComponent;
        let fixture: ComponentFixture<TableBarDetailComponent>;
        let service: TableBarService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmorrecordsTestModule],
                declarations: [TableBarDetailComponent],
                providers: [
                    TableBarService
                ]
            })
                .overrideTemplate(TableBarDetailComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TableBarDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TableBarService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TableBar(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
            });
        });
    });

});

