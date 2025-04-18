// Libs
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';

// Components
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';
import { CreateRoomModalComponent } from '../../shared/components/modals/create-room/create-room-modal/create-room-modal.component';

@Component({
    selector: 'app-dashboard',
    imports: [SidebarComponent, RouterModule, MatButtonModule, MatIconModule],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
    constructor(
        private router: Router,
        private dialog: MatDialog,
    ) {}

    protected createRoom(): void {
        const modalRef = this.dialog.open(CreateRoomModalComponent);

        modalRef.componentInstance.create.subscribe(() => {
            modalRef.close();
        });
    }
}
