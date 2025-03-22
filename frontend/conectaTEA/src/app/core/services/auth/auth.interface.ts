/* eslint-disable @typescript-eslint/no-empty-object-type */
export interface RegisterRequest {
    username: string;
    email: string;
    password: string;
}

export interface RegisterResponse {
    success: boolean;
    message: string;
    /** TODO: Alinhar com o Back se mais dados serão retornados */
}

export interface LoginRequest {
    /** TODO: Implemente a interface */
}

export interface LoginResponse {
    /** TODO: Implemente a interface */
}
