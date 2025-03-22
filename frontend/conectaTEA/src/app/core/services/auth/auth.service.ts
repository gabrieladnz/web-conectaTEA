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

    public async login(body: LoginRequest): Promise<void> {
        /** TODO: Implemente a função de login aqui com sua devida rota, método e interface */
    }
}
