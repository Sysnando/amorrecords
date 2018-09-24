/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AmorrecordsTestModule } from '../../../test.module';
import { DiscoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/disco/disco-delete-dialog.component';
import { DiscoService } from '../../../../../../main/webapp/app/entities/disco/disco.service';

describe('Component Tests', () => {

    describe('Disco Management Delete Component', () => {
        let comp: DiscoDeleteDialogComponent;
        let fixture: ComponentFixture<DiscoDeleteDialogComponent>;
        let service: DiscoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmorrecordsTestModule],
                declarations: [DiscoDeleteDialogComponent],
                providers: [
                    DiscoService
                ]
            })
            .overrideTemplate(DiscoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiscoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiscoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
