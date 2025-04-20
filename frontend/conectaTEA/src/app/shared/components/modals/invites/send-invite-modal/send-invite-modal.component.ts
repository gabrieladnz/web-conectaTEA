import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

@Component({
    selector: 'app-send-invite-modal',
    imports: [MatIconModule],
    templateUrl: './send-invite-modal.component.html',
    styleUrl: './send-invite-modal.component.scss',
})
export class SendInviteModalComponent {
    inviteForm!: FormGroup;

    constructor(
        private fb: FormBuilder,
        private dialogRef: MatDialogRef<SendInviteModalComponent>,
    ) { }

    protected ngOnInit(): void {
        this.initForm();
    }

    protected initForm(): void {
        this.inviteForm = this.fb.group({
            username: ['', [Validators.required]],
            message: [''],
        });
    }

    protected onSubmit(): void {
        if (this.inviteForm.invalid) {
            this.inviteForm.markAllAsTouched();
            return;
        }

        const inviteData = {
            username: this.inviteForm.value.username,
            message: this.inviteForm.value.message,
        };

        this.dialogRef.close(inviteData);
    }

    protected closeModal(): void {
        this.dialogRef.close();
    }
}
