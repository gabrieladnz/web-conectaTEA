// Libs
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

// Components
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';

@Component({
    selector: 'app-invite',
    imports: [MatIconModule, SidebarComponent],
    templateUrl: './invite.component.html',
    styleUrl: './invite.component.scss',
})
export class InviteComponent { }
