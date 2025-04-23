// Libs
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';

// Services
import { ApiService } from '../../api/api.service';

// Interfaces
import {
    CreateRoomRequest,
    CreateRoomResponse,
    RoomDtoResponse,
} from './room.interface';

@Injectable({
    providedIn: 'root',
})
export class RoomService extends ApiService {
    constructor(protected override http: HttpClient) {
        super(http);
    }

    public async createRoom(
        body: CreateRoomRequest,
    ): Promise<CreateRoomResponse> {
        try {
            const token: string | null = localStorage.getItem('auth_token');
            if (!token) throw new Error('Token de autenticação não encontrado');

            return await lastValueFrom(
                this.post<CreateRoomResponse>('rooms', body, token),
            );
        } catch (error) {
            const errorResponse = {
                success: false,
                message: error,
            };

            throw errorResponse;
        }
    }

    public async listPublicRooms(): Promise<RoomDtoResponse[]> {
        try {
            // TODO: A rota deve usar BearerToken, após ajuste no Back
            return await lastValueFrom(
                this.get<RoomDtoResponse[]>('rooms/public'),
            );
        } catch (error) {
            const errorResponse = {
                success: false,
                message: error,
            };
            throw errorResponse;
        }
    }

    public async listRoomsByUser(userId: number): Promise<RoomDtoResponse[]> {
        try {
            // TODO: A rota deve usar BearerToken, após ajuste no Back
            return await lastValueFrom(
                this.get<RoomDtoResponse[]>(`rooms/my-rooms/${userId}`),
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
