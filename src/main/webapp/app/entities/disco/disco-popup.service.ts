import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Disco } from './disco.model';
import { DiscoService } from './disco.service';

@Injectable()
export class DiscoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private discoService: DiscoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.discoService.find(id)
                    .subscribe((discoResponse: HttpResponse<Disco>) => {
                        const disco: Disco = discoResponse.body;
                        disco.datePurchase = this.datePipe
                            .transform(disco.datePurchase, 'yyyy-MM-ddTHH:mm:ss');
                        disco.dateSale = this.datePipe
                            .transform(disco.dateSale, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.discoModalRef(component, disco);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.discoModalRef(component, new Disco());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    discoModalRef(component: Component, disco: Disco): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.disco = disco;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
