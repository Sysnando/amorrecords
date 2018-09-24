/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AmorrecordsTestModule } from '../../../test.module';
import { BarDetailComponent } from '../../../../../../main/webapp/app/entities/bar/bar-detail.component';
import { BarService } from '../../../../../../main/webapp/app/entities/bar/bar.service';
import { Bar } from '../../../../../../main/webapp/app/entities/bar/bar.model';

describe('Component Tests', () => {

    describe('Bar Management Detail Component', () => {
        let comp: BarDetailComponent;
        let fixture: ComponentFixture<BarDetailComponent>;
        let service: BarService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmorrecordsTestModule],
                declarations: [BarDetailComponent],
                providers: [
                    BarService
                ]
            })
            .overrideTemplate(BarDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BarDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BarService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Bar(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bar).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
