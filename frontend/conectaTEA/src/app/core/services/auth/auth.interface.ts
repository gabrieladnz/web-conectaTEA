export interface RegisterRequest {
    username: string;
    email: string;
    password: string;
}

export interface RegisterResponse {
    success: boolean;
    message: string;
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    success: boolean;
    message: string;
    token: string;
    name: string;
    userId: number;
}

export interface LogoutResponse {
    success: boolean;
    message: string;
}
