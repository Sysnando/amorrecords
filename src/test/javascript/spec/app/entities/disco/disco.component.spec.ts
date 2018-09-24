/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmorrecordsTestModule } from '../../../test.module';
import { DiscoComponent } from '../../../../../../main/webapp/app/entities/disco/disco.component';
import { DiscoService } from '../../../../../../main/webapp/app/entities/disco/disco.service';
import { Disco } from '../../../../../../main/webapp/app/entities/disco/disco.model';

describe('Component Tests', () => {

    describe('Disco Management Component', () => {
        let comp: DiscoComponent;
        let fixture: ComponentFixture<DiscoComponent>;
        let service: DiscoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmorrecordsTestModule],
                declarations: [DiscoComponent],
                providers: [
                    DiscoService
                ]
            })
            .overrideTemplate(DiscoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiscoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiscoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Disco(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.discos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
