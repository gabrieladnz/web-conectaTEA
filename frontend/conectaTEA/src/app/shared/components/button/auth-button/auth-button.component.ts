// Libs
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-auth-button',
    imports: [MatButtonModule],
    templateUrl: './auth-button.component.html',
    styleUrl: './auth-button.component.scss',
})
export class AuthButtonComponent {
    @Input() labelButton: string = '';
    @Output() onClick: EventEmitter<void> = new EventEmitter<void>();

    /**
     * @description Emite um evento quando o botão é clicado.
     */
    public onClickButton(): void {
        this.onClick.emit();
    }
}
