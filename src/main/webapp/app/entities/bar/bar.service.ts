import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bar } from './bar.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Bar>;

@Injectable()
export class BarService {

    private resourceUrl =  SERVER_API_URL + 'api/bars';

    constructor(private http: HttpClient) { }

    create(bar: Bar): Observable<EntityResponseType> {
        const copy = this.convert(bar);
        return this.http.post<Bar>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bar: Bar): Observable<EntityResponseType> {
        const copy = this.convert(bar);
        return this.http.put<Bar>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Bar>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Bar[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bar[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bar[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Bar = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Bar[]>): HttpResponse<Bar[]> {
        const jsonResponse: Bar[] = res.body;
        const body: Bar[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Bar.
     */
    private convertItemFromServer(bar: Bar): Bar {
        const copy: Bar = Object.assign({}, bar);
        return copy;
    }

    /**
     * Convert a Bar to a JSON which can be sent to the server.
     */
    private convert(bar: Bar): Bar {
        const copy: Bar = Object.assign({}, bar);
        return copy;
    }
}
