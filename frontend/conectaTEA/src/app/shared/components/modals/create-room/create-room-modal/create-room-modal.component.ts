import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
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

    roomForm: FormGroup;
    formSubmitted = false;
    selectedCategory: number | null = null;

    categories = [
        { id: 1, name: 'Educação' },
        { id: 2, name: 'Saúde' },
        { id: 3, name: 'Tecnologia' },
        { id: 4, name: 'Parentalidade' },
        { id: 5, name: 'Lazer' },
        { id: 6, name: 'Esportes' },
        { id: 7, name: 'Cultura' },
        { id: 8, name: 'Artes' },
    ];

    constructor(
        private fb: FormBuilder,
        private dialogRef: MatDialogRef<CreateRoomModalComponent>,
    ) {
        this.roomForm = this.fb.group({
            name: ['', [Validators.required, Validators.maxLength(50)]],
            description: ['', [Validators.required, Validators.maxLength(200)]],
            type: ['public', Validators.required],
        });
    }

    ngOnInit(): void {}

    protected selectCategory(categoryId: number): void {
        this.selectedCategory = categoryId;
    }

    protected closeModal(): void {
        setTimeout(() => {
            this.dialogRef.close();
        }, 200);
    }

    protected onSubmit(): void {
        this.formSubmitted = true;

        if (this.roomForm.valid && this.selectedCategory) {
            const roomData = {
                ...this.roomForm.value,
                categoryId: this.selectedCategory,
            };

            this.create.emit(roomData);
            this.closeModal();
        }
    }
}
