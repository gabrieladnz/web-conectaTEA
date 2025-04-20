import { Injectable } from '@angular/core';
import { ApiService } from '../../api/api.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class InviteService extends ApiService {
    constructor(protected override http: HttpClient) {
        super(http);
    }
}
