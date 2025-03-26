// Libs
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';

// Services
import { ApiService } from '../../api/api.service';

// Models
import {
    LoginRequest,
    LoginResponse,
    LogoutResponse,
    RegisterRequest,
    RegisterResponse,
} from './auth.interface';

@Injectable({
    providedIn: 'root',
})
export class AuthService extends ApiService {
    constructor(protected override http: HttpClient) {
        super(http);
    }

    /**
     * @description Cadastra um novo usuário
     */
    public async register(body: RegisterRequest): Promise<RegisterResponse> {
        try {
            return await lastValueFrom(
                this.post<RegisterResponse>('auth/register', body),
            );
        } catch (error) {
            const errorResponse = {
                success: false,
                message: error,
            };

            throw errorResponse;
        }
    }

    /**
     * @description Realiza o login do usuário
     */
    public async login(body: LoginRequest): Promise<LoginResponse> {
        try {
            return await lastValueFrom(
                this.post<LoginResponse>('auth/login', body),
            );
        } catch (error) {
            const errorResponse = {
                success: false,
                message: error,
            };

            throw errorResponse;
        }
    }

    /**
     * @description Realiza o logout do usuário
     */
    public async logout(): Promise<LogoutResponse> {
        try {
            /** TODO: Ajustar esses pontos para a lógica desenvolvida na hora de lidar com os tokens */
            const token: string | null = localStorage.getItem('token');

            if (!token) throw new Error('Token de autenticação não encontrado');

            return await lastValueFrom(
                this.post<LogoutResponse>('auth/logout', {}, token),
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
