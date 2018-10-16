/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AmorrecordsTestModule } from '../../../test.module';
import { Table_barDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/table-bar/table-bar-delete-dialog.component';
import { Table_barService } from '../../../../../../main/webapp/app/entities/table-bar/table-bar.service';

describe('Component Tests', () => {

    describe('Table_bar Management Delete Component', () => {
        let comp: Table_barDeleteDialogComponent;
        let fixture: ComponentFixture<Table_barDeleteDialogComponent>;
        let service: Table_barService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmorrecordsTestModule],
                declarations: [Table_barDeleteDialogComponent],
                providers: [
                    Table_barService
                ]
            })
            .overrideTemplate(Table_barDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Table_barDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Table_barService);
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
