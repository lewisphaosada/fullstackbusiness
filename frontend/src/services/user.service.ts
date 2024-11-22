import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class UserService {
    private apiUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) {}

    createUser(companyId: number, userRequest: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/users/${companyId}/users`, userRequest);
    }

    getUsersByCompany(companyId: number): Observable<any[]> {
        return this.http.get<any[]>(`${this.apiUrl}/company/${companyId}/users`);
    }
}
