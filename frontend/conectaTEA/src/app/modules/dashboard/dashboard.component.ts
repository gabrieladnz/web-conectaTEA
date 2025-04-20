// Libs
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';

// Components
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';
import { CreateRoomModalComponent } from '../../shared/components/modals/create-room/create-room-modal/create-room-modal.component';

// Services
import { RoomService } from '../../core/services/room/room.service';
import { TokenService } from './../../core/services/token/token.service';
import { MessageService } from '../../core/services/message/message.service';

// Interfaces
import { RoomDtoResponse } from '../../core/services/room/room.interface';

@Component({
    selector: 'app-dashboard',
    imports: [SidebarComponent, RouterModule, MatButtonModule, MatIconModule],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
    protected publicRooms!: RoomDtoResponse[];
    protected myRooms!: RoomDtoResponse[];

    constructor(
        private router: Router,
        private dialog: MatDialog,
        private roomService: RoomService,
        private tokenService: TokenService,
        private messageService: MessageService,
    ) {
        this.messageService.connectToWebSocket();
        this.getListPublicRooms();
        this.getMyRooms();
    }

    protected userName = this.tokenService.getName();
    protected userId = this.tokenService.getUserId();

    protected createRoom(): void {
        const modalRef = this.dialog.open(CreateRoomModalComponent);

        modalRef.componentInstance.create.subscribe(() => {
            modalRef.close();
        });
    }

    protected getListPublicRooms(): void {
        this.roomService.listPublicRooms().then((rooms: RoomDtoResponse[]) => {
            this.publicRooms = rooms;
        });
    }

    protected getMyRooms(): void {
        this.roomService
            .listRoomsByUser(this.userId)
            .then((rooms: RoomDtoResponse[]) => {
                this.myRooms = rooms;
            });
    }
}
