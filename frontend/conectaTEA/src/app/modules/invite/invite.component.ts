// Libs
import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

// Components
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';

// Services
import { InviteService } from '../../core/services/invite/invite.service';
import { TokenService } from '../../core/services/token/token.service';

// Interfaces
import { InviteResponse } from '../../core/services/invite/invite.interface';

@Component({
    selector: 'app-invite',
    imports: [MatIconModule, SidebarComponent],
    templateUrl: './invite.component.html',
    styleUrl: './invite.component.scss',
})
export class InviteComponent implements OnInit {
    protected invites: InviteResponse[] = [];
    protected pendingInvitesCount: number = 0;

    constructor(
        private inviteService: InviteService,
        private tokenService: TokenService,
    ) { }

    protected userId = this.tokenService.getUserId();

    ngOnInit(): void {
        this.getInvitesList();
    }

    protected getInvitesList(): void {
        this.inviteService
            .getReceivedInvites(this.userId)
            .then((invites) => {
                this.invites = invites.filter((i) => i.status === 'PENDING');
                this.pendingInvitesCount = this.invites.length;
            })
            .catch((error) => {
                console.error('Erro ao buscar convites:', error);
            });
    }

    protected acceptInvite(inviteId: number): void {
        this.inviteService
            .acceptInvite(inviteId)
            .then(() => {
                this.getInvitesList();
            })
            .catch((error) => {
                /** TODO:: Corrigir isso, erro ocasionado por retorno do back */
                this.getInvitesList();
                console.error('Erro ao aceitar convite:', error);
            });
    }

    protected declineInvite(inviteId: number): void {
        this.inviteService
            .declineInvite(inviteId)
            .then(() => {
                this.getInvitesList();
            })
            .catch((error) => {
                /** TODO:: Corrigir isso, erro ocasionado por retorno do back */

                this.getInvitesList();
                console.error('Erro ao recusar convite:', error);
            });
    }
}
