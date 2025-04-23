import { Injectable } from '@angular/core';
import { ApiService } from '../../api/api.service';
import { HttpClient } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';
import { InviteRequest, InviteResponse } from './invite.interface';

@Injectable({
    providedIn: 'root',
})
export class InviteService extends ApiService {
    constructor(protected override http: HttpClient) {
        super(http);
    }

    public async getReceivedInvites(userId: number): Promise<InviteResponse[]> {
        try {
            return await lastValueFrom(
                this.get<InviteResponse[]>(`invites/received/${userId}`),
            );
        } catch (error) {
            const errorResponse = {
                success: false,
                message: error,
            };

            throw errorResponse;
        }
    }

    public async sendInvite(body: InviteRequest): Promise<void> {
        try {
            const token = localStorage.getItem('auth_token');
            if (!token) throw new Error('Token de autenticação não encontrado');

            await lastValueFrom(this.post<void>('invites/send', body, token));
        } catch (error) {
            const errorResponse = {
                success: false,
                message: error,
            };

            throw errorResponse;
        }
    }

    public async acceptInvite(inviteId: number): Promise<void> {
        try {
            const token = localStorage.getItem('auth_token');
            if (!token) throw new Error('Token de autenticação não encontrado');
            await lastValueFrom(
                this.post<void>(`invites/accept/${inviteId}`, {}, token),
            );
        } catch (error) {
            const errorResponse = {
                success: false,
                message: error,
            };

            throw errorResponse;
        }
    }

    public async declineInvite(inviteId: number): Promise<void> {
        try {
            const token = localStorage.getItem('auth_token');
            if (!token) throw new Error('Token de autenticação não encontrado');

            return await lastValueFrom(
                this.post<void>(`invites/decline/${inviteId}`, {}, token),
            );
        } catch (error) {
            const errorResponse = {
                success: false,
                message: error,
            };

            throw errorResponse;
        }
    }
}
