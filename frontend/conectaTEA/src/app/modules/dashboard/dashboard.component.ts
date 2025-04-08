// Libs
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';

// Components
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';

@Component({
    selector: 'app-dashboard',
    imports: [SidebarComponent, RouterModule, MatButtonModule],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
    constructor(private router: Router) {}

    protected createRoom(): void {
        /** TODO: Implementar função que abre o modal pra criação de sala */
    }
}
