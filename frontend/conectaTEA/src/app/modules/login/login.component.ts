// Libs
import { Component, signal } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';

// Components
import { BackgroundParticlesComponent } from '../../shared/components/background-particles/background-particles.component';
import { AuthButtonComponent } from '../../shared/components/buttons/auth-button/auth-button.component';

// Services
import { AuthService } from '../../core/services/auth/auth.service';
import { TokenService } from '../../core/services/token/token.service';

@Component({
    selector: 'app-login',
    imports: [
        AuthButtonComponent,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        BackgroundParticlesComponent,
    ],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss',
})
export class LoginComponent {
    protected formLogin: FormGroup;
    protected errorMessagePassword = signal('Credenciais inválidas.');
    protected hide: boolean = true;

    constructor(
        private formBuilder: FormBuilder,
        private authentication: AuthService,
        private router: Router,
        private tokenService: TokenService,
    ) {
        this.formLogin = this.formBuilder.group({
            username: ['', [Validators.required]],
            password: ['', [Validators.minLength(6)]],
        });
    }

    /**
     * @description Realiza o login do usuário
     */
    protected async login(): Promise<void> {
        if (this.formLogin.valid) {
            try {
                const loginResponse = await this.authentication.login(
                    this.formLogin.value,
                );
                this.tokenService.saveAll(
                    loginResponse.token,
                    loginResponse.name,
                    loginResponse.userId,
                );

                this.router.navigate(['/dashboard']);
            } catch {
                this.formLogin.controls['password'].setErrors({
                    invalidLogin: true,
                });
            }
        } else {
            this.formLogin.controls['password'].setErrors({
                invalidLogin: true,
            });
        }
    }
}
