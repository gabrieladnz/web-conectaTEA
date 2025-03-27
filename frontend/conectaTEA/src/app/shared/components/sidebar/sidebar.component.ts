// Libs
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';

// Services
import { AuthService } from '../../../core/services/auth/auth.service';

@Component({
    selector: 'app-sidebar',
    imports: [MatIconModule, RouterModule],
    templateUrl: './sidebar.component.html',
    styleUrl: './sidebar.component.scss',
})
export class SidebarComponent {
    constructor(
        private router: Router,
        private authentication: AuthService,
    ) {}

    /**
     * @description Realiza o logout do usuário
     */
    protected async logout(): Promise<void> {
        /** TODO: Validar método com integração */
        await this.authentication.logout();
        this.router.navigate(['/login']);
    }

    /**
     * @description Abre a modal de informações
     */
    protected openHelp(): void {
        /** TODO: Implementar modal com informações sobre o site */
    }
}
