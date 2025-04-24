// Libs
import { Component, Inject } from '@angular/core';
import {
    FormGroup,
    FormBuilder,
    Validators,
    ReactiveFormsModule,
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

// Services
import { InviteService } from '../../../../../core/services/invite/invite.service';

@Component({
    selector: 'app-send-invite-modal',
    imports: [MatIconModule, ReactiveFormsModule],
    templateUrl: './send-invite-modal.component.html',
    styleUrl: './send-invite-modal.component.scss',
})
export class SendInviteModalComponent {
    protected inviteForm!: FormGroup;

    constructor(
        private fb: FormBuilder,
        private dialogRef: MatDialogRef<SendInviteModalComponent>,
        @Inject(MAT_DIALOG_DATA)
        public data: { roomId: number; senderId: number },
        private inviteService: InviteService,
    ) { }

    protected ngOnInit(): void {
        this.initForm();
    }

    protected initForm(): void {
        this.inviteForm = this.fb.group({
            username: ['', [Validators.required]],
        });
    }

    protected async sendInvite(): Promise<void> {
        const inviteData = {
            roomId: this.data.roomId,
            senderId: this.data.senderId,
            recipientUsername: this.inviteForm.value.username,
        };

        try {
            await this.inviteService.sendInvite(inviteData);
            this.dialogRef.close();
        } catch (error) {
            console.error('Erro ao enviar convite:', error);
        }
    }

    protected closeModal(): void {
        this.dialogRef.close();
    }
}
