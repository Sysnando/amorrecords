import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TableBar } from './table-bar.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TableBar>;

@Injectable()
export class TableBarService {

    private resourceUrl =  SERVER_API_URL + 'api/table-bars';

    constructor(private http: HttpClient) { }

    create(table_bar: TableBar): Observable<EntityResponseType> {
        const copy = this.convert(table_bar);
        return this.http.post<TableBar>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(table_bar: TableBar): Observable<EntityResponseType> {
        const copy = this.convert(table_bar);
        return this.http.put<TableBar>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TableBar>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TableBar[]>> {
        const options = createRequestOption(req);
        return this.http.get<TableBar[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TableBar[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TableBar = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TableBar[]>): HttpResponse<TableBar[]> {
        const jsonResponse: TableBar[] = res.body;
        const body: TableBar[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TableBar.
     */
    private convertItemFromServer(table_bar: TableBar): TableBar {
        const copy: TableBar = Object.assign({}, table_bar);
        return copy;
    }

    /**
     * Convert a TableBar to a JSON which can be sent to the server.
     */
    private convert(table_bar: TableBar): TableBar {
        const copy: TableBar = Object.assign({}, table_bar);
        return copy;
    }
}
