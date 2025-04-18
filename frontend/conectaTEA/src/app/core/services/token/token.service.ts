import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root',
})
export class TokenService {
    private type: 'sessionStorage' | 'localStorage' = 'localStorage';
    private readonly tokenUser = 'auth_token';

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
    }

    /**
     * Função responsável por armazenar o token de autenticação do usuário
     * @param token o token a ser armazenado
     */
    public save(token: string): void {
        window[this.type].setItem(this.tokenUser, token);
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
}
