import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root',
})
export class TokenService {
    private type: 'sessionStorage' | 'localStorage' = 'localStorage';
    private readonly tokenUser = 'auth_token';
    private readonly nameKey = 'auth_name';
    private readonly userNameKey = 'auth_user_name';
    private readonly userIdKey = 'auth_user_id';

    constructor(private router: Router) {}

    /**
     * Função responsável por recuperar o token de autenticação
     * @returns o token de autenticação armazenado ou null, se não estiver presente
     */
    public get(): string | null {
        return window[this.type].getItem(this.tokenUser);
    }

    /**
     * Função responsável por remover o token de autenticação
     */
    public delete(): void {
        window[this.type].removeItem(this.tokenUser);
        window[this.type].removeItem(this.nameKey);
        window[this.type].removeItem(this.userNameKey);
        window[this.type].removeItem(this.userIdKey);
    }

    /**
     * Função responsável por armazenar o token de autenticação do usuário
     * @param token o token a ser armazenado
     */
    public saveAll(
        token: string,
        name: string,
        username: string,
        userId: number,
    ): void {
        window[this.type].setItem(this.tokenUser, token);
        window[this.type].setItem(this.nameKey, name);
        window[this.type].setItem(this.userNameKey, username);
        window[this.type].setItem(this.userIdKey, userId.toString());
    }

    /**
     * Função responsável por fazer o logout, deletar o token e redirecionar o usuário para a página de login
     */
    public async logout(): Promise<boolean> {
        this.delete();
        return this.router.navigate(['/auth/login'], {
            replaceUrl: true,
        });
    }

    public userIsAuthenticated(): boolean {
        return !!this.get();
    }

    public getName(): string | null {
        return window[this.type].getItem(this.nameKey);
    }

    public getUserName(): string | null {
        return window[this.type].getItem(this.userNameKey);
    }

    public getUserId(): number {
        const id = window[this.type].getItem(this.userIdKey);
        return Number(id);
    }
}
