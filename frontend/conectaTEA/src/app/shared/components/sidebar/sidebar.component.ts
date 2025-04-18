// Libs
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

// Components
import { AboutConectateaModalComponent } from '../modals/info/about-conectatea-modal/about-conectatea-modal.component';

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
        private dialog: MatDialog,
    ) { }

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
        const modalRef = this.dialog.open(AboutConectateaModalComponent);

        modalRef.componentInstance.close.subscribe(() => {
            this.dialog.closeAll();
        });
    }
}
