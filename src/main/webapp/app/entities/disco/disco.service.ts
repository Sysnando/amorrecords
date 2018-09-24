import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Disco } from './disco.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Disco>;

@Injectable()
export class DiscoService {

    private resourceUrl =  SERVER_API_URL + 'api/discos';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(disco: Disco): Observable<EntityResponseType> {
        const copy = this.convert(disco);
        return this.http.post<Disco>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(disco: Disco): Observable<EntityResponseType> {
        const copy = this.convert(disco);
        return this.http.put<Disco>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Disco>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Disco[]>> {
        const options = createRequestOption(req);
        return this.http.get<Disco[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Disco[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Disco = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Disco[]>): HttpResponse<Disco[]> {
        const jsonResponse: Disco[] = res.body;
        const body: Disco[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Disco.
     */
    private convertItemFromServer(disco: Disco): Disco {
        const copy: Disco = Object.assign({}, disco);
        copy.datePurchase = this.dateUtils
            .convertDateTimeFromServer(disco.datePurchase);
        copy.dateSale = this.dateUtils
            .convertDateTimeFromServer(disco.dateSale);
        return copy;
    }

    /**
     * Convert a Disco to a JSON which can be sent to the server.
     */
    private convert(disco: Disco): Disco {
        const copy: Disco = Object.assign({}, disco);

        copy.datePurchase = this.dateUtils.toDate(disco.datePurchase);

        copy.dateSale = this.dateUtils.toDate(disco.dateSale);
        return copy;
    }
}
