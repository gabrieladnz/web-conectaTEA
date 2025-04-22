// Libs
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { trigger, transition, style, animate } from '@angular/animations';
import { MatOptionModule } from '@angular/material/core';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';

// Services
import { RoomService } from '../../../../../core/services/room/room.service';

@Component({
    selector: 'app-create-room-modal',
    imports: [
        MatOptionModule,
        MatSelectModule,
        MatDialogModule,
        MatIconModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        CommonModule,
        FormsModule,
    ],
    templateUrl: './create-room-modal.component.html',
    styleUrls: ['./create-room-modal.component.scss'],
    animations: [
        trigger('fadeInUp', [
            transition(':enter', [
                style({ opacity: 0, transform: 'translateY(20px)' }),
                animate(
                    '0.3s ease-out',
                    style({ opacity: 1, transform: 'translateY(0)' }),
                ),
            ]),
            transition(':leave', [
                animate(
                    '0.2s ease-in',
                    style({ opacity: 0, transform: 'translateY(20px)' }),
                ),
            ]),
        ]),
    ],
})
export class CreateRoomModalComponent implements OnInit {
    @Output() close = new EventEmitter<void>();
    @Output() create = new EventEmitter<unknown>();

    protected roomForm: FormGroup;
    protected formSubmitted = false;
    protected selectedCategory!: string;
    protected invitedUsers: string[] = [];
    protected newUserToInvite: string = '';

    protected categories = [
        { id: 1, name: 'Educação' },
        { id: 2, name: 'Saúde' },
        { id: 3, name: 'Bate-papo' },
        { id: 4, name: 'Parentalidade' },
        { id: 5, name: 'Lazer' },
        { id: 6, name: 'Esportes' },
        { id: 7, name: 'Cultura' },
        { id: 8, name: 'Artes' },
    ];

    constructor(
        private fb: FormBuilder,
        private dialogRef: MatDialogRef<CreateRoomModalComponent>,
        private roomService: RoomService,
    ) {
        this.roomForm = this.fb.group({
            name: ['', [Validators.required, Validators.maxLength(50)]],
            description: ['', [Validators.required, Validators.maxLength(200)]],
            roomType: ['public', Validators.required],
            usernames: [[], Validators.required],
        });
    }

    ngOnInit(): void {}

    protected selectCategory(categoryId: number): void {
        const foundCategory = this.categories.find(
            (category) => category.id === categoryId,
        );

        this.selectedCategory = foundCategory?.name || '';
    }

    protected closeModal(): void {
        setTimeout(() => {
            this.dialogRef.close();
        }, 200);
    }

    protected addUserToInviteList(): void {
        if (
            this.newUserToInvite.trim() !== '' &&
            !this.invitedUsers.includes(this.newUserToInvite.trim())
        ) {
            this.invitedUsers.push(this.newUserToInvite.trim());
            this.newUserToInvite = '';
            this.updateFormUsers();
        }
    }

    protected removeUserFromList(index: number): void {
        this.invitedUsers.splice(index, 1);
        this.updateFormUsers();
    }

    private updateFormUsers(): void {
        this.roomForm.patchValue({
            usernames: this.invitedUsers,
        });
    }

    protected onSubmit(): void {
        this.formSubmitted = true;

        if (this.roomForm.valid && this.selectedCategory) {
            const roomData = {
                ...this.roomForm.value,
                roomType: this.roomForm.value.roomType.toUpperCase(),
                category: this.selectedCategory,
            };

            this.roomService.createRoom(roomData).then(() => {
                this.create.emit(roomData);
                this.closeModal();
            });
        }
    }
}
