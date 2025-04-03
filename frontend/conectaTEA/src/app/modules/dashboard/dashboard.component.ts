// Libs
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

// Components
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';

@Component({
    selector: 'app-dashboard',
    imports: [SidebarComponent, RouterModule],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
    constructor(private router: Router) {}
}
