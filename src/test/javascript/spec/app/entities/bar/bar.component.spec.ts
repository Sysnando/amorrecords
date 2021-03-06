/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmorrecordsTestModule } from '../../../test.module';
import { BarComponent } from '../../../../../../main/webapp/app/entities/bar/bar.component';
import { BarService } from '../../../../../../main/webapp/app/entities/bar/bar.service';
import { Bar } from '../../../../../../main/webapp/app/entities/bar/bar.model';

describe('Component Tests', () => {

    describe('Bar Management Component', () => {
        let comp: BarComponent;
        let fixture: ComponentFixture<BarComponent>;
        let service: BarService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmorrecordsTestModule],
                declarations: [BarComponent],
                providers: [
                    BarService
                ]
            })
            .overrideTemplate(BarComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BarComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BarService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Bar(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bars[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
