import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Table_bar } from './table-bar.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Table_bar>;

@Injectable()
export class Table_barService {

    private resourceUrl =  SERVER_API_URL + 'api/table-bars';

    constructor(private http: HttpClient) { }

    create(table_bar: Table_bar): Observable<EntityResponseType> {
        const copy = this.convert(table_bar);
        return this.http.post<Table_bar>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(table_bar: Table_bar): Observable<EntityResponseType> {
        const copy = this.convert(table_bar);
        return this.http.put<Table_bar>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Table_bar>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Table_bar[]>> {
        const options = createRequestOption(req);
        return this.http.get<Table_bar[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Table_bar[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Table_bar = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Table_bar[]>): HttpResponse<Table_bar[]> {
        const jsonResponse: Table_bar[] = res.body;
        const body: Table_bar[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Table_bar.
     */
    private convertItemFromServer(table_bar: Table_bar): Table_bar {
        const copy: Table_bar = Object.assign({}, table_bar);
        return copy;
    }

    /**
     * Convert a Table_bar to a JSON which can be sent to the server.
     */
    private convert(table_bar: Table_bar): Table_bar {
        const copy: Table_bar = Object.assign({}, table_bar);
        return copy;
    }
}
