// Libs
import { Component, ElementRef, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';

// Components
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';
import { CreateRoomModalComponent } from '../../shared/components/modals/create-room/create-room-modal/create-room-modal.component';

@Component({
    selector: 'app-chat',
    imports: [SidebarComponent, MatButtonModule, MatIconModule],
    templateUrl: './chat.component.html',
    styleUrl: './chat.component.scss',
})
export class ChatComponent {
    @ViewChild('messageInputArea')
    // eslint-disable-next-line indent
    messageInputArea!: ElementRef<HTMLTextAreaElement>;

    constructor(private dialog: MatDialog) {}

    protected autoResize(): void {
        const textarea = this.messageInputArea.nativeElement;
        textarea.style.height = 'auto';
        textarea.style.height = `${textarea.scrollHeight}px`;
    }

    protected createRoom(): void {
        const modalRef = this.dialog.open(CreateRoomModalComponent);

        modalRef.componentInstance.create.subscribe(() => {
            modalRef.close();
        });
    }
}
