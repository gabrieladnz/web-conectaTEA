// Libs
import { Component, signal } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';

// Components
import { AuthButtonComponent } from '../../shared/components/button/auth-button/auth-button.component';

@Component({
    selector: 'app-register',
    imports: [
        AuthButtonComponent,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
    ],
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss',
})
export class RegisterComponent {
    protected formRegister: FormGroup;
    protected errorMessageEmail = signal('');
    protected errorMessagePassword = signal('');
    protected hide: boolean = true;

    constructor(private formBuilder: FormBuilder) {
        this.formRegister = this.formBuilder.group({
            user: ['', [Validators.required]],
            email: ['', [Validators.email]],
            password: ['', [Validators.minLength(6)]],
        });
    }

    /**
     * @description Função de atualização de mensagens de erro
     */
    protected updateErrorMessage(): void {
        if (this.formRegister.controls?.['email'].hasError('email')) {
            this.errorMessageEmail.set('E-mail inválido.');
        } else {
            this.errorMessageEmail.set('');
        }

        if (this.formRegister.controls?.['password'].hasError('minlength')) {
            this.errorMessagePassword.set(
                'A senha deve ter no mínimo 6 caracteres.',
            );
        } else {
            this.errorMessagePassword.set('');
        }
    }

    /**
     * @description Função de cadastro de usuário
     */
    protected register(): void {
        /** TODO: Implementar funcao de cadastro */
    }
}
