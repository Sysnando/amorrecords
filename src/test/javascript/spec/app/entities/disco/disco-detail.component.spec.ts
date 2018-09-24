/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AmorrecordsTestModule } from '../../../test.module';
import { DiscoDetailComponent } from '../../../../../../main/webapp/app/entities/disco/disco-detail.component';
import { DiscoService } from '../../../../../../main/webapp/app/entities/disco/disco.service';
import { Disco } from '../../../../../../main/webapp/app/entities/disco/disco.model';

describe('Component Tests', () => {

    describe('Disco Management Detail Component', () => {
        let comp: DiscoDetailComponent;
        let fixture: ComponentFixture<DiscoDetailComponent>;
        let service: DiscoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmorrecordsTestModule],
                declarations: [DiscoDetailComponent],
                providers: [
                    DiscoService
                ]
            })
            .overrideTemplate(DiscoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiscoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiscoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Disco(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.disco).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
