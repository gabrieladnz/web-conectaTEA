// Libs
import { Component, ElementRef, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';

// Components
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';
import { CreateRoomModalComponent } from '../../shared/components/modals/create-room/create-room-modal/create-room-modal.component';

// Services
import { MessageService } from '../../core/services/message/message.service';
import { RoomService } from '../../core/services/room/room.service';
import { TokenService } from '../../core/services/token/token.service';

// Interfaces
import { RoomDtoResponse } from '../../core/services/room/room.interface';
import { SendInviteModalComponent } from '../../shared/components/modals/invites/send-invite-modal/send-invite-modal.component';

@Component({
    selector: 'app-chat',
    imports: [
        SidebarComponent,
        MatButtonModule,
        MatIconModule,
        ReactiveFormsModule,
        FormsModule,
    ],
    templateUrl: './chat.component.html',
    styleUrl: './chat.component.scss',
})
export class ChatComponent {
    @ViewChild('messageInputArea')
    protected messageInputArea!: ElementRef<HTMLTextAreaElement>;
    protected listConversations!: RoomDtoResponse[];
    protected selectedConversationId: number | null = null;
    protected selectedConversation: RoomDtoResponse | null = null;
    protected messageForm: FormGroup;

    constructor(
        private dialog: MatDialog,
        private messageService: MessageService,
        private roomService: RoomService,
        private tokenService: TokenService,
        private fb: FormBuilder,
    ) {
        this.messageService.connectToWebSocket();
        this.getMyRooms();

        this.messageForm = this.fb.group({
            type: ['MESSAGE'],
            roomId: [this.selectedConversationId],
            sender: [this.userName],
            content: ['', Validators.required],
        });
    }

    protected userId = this.tokenService.getUserId();
    protected userName = this.tokenService.getUserName();

    protected autoResize(): void {
        const textarea = this.messageInputArea.nativeElement;
        textarea.style.height = 'auto';
        textarea.style.height = `${textarea.scrollHeight}px`;
    }

    protected onEnterKeydown(event: Event): void {
        const keyboardEvent = event as KeyboardEvent;

        if (!keyboardEvent.shiftKey) {
            keyboardEvent.preventDefault();
            this.sendMessage();
        }
    }

    protected selectConversation(conversationId: number): void {
        this.selectedConversationId = conversationId;
        this.selectedConversation =
            this.listConversations.find(
                (conversation) => conversation.id === conversationId,
            ) || null;

        this.messageForm.patchValue({
            roomId: this.selectedConversationId,
        });
    }

    protected createRoom(): void {
        const modalRef = this.dialog.open(CreateRoomModalComponent);

        modalRef.componentInstance.create.subscribe(() => {
            modalRef.close();
        });
    }

    protected getMyRooms(): void {
        this.roomService
            .listRoomsByUser(this.userId)
            .then((rooms: RoomDtoResponse[]) => {
                this.listConversations = rooms;
            });
    }

    protected listMessagePerConversation() {
        // TODO: Conectar endpoint que lista mensagens da conversa
    }

    protected sendMessage(): void {
        this.messageService.sendMessage(this.messageForm.value);
        this.messageForm.patchValue({ content: '' });
        this.autoResize();
    }

    protected openInviteUserModal(): void {
        // TODO: Implementar endpoint de enviar convite
        const dialogRef = this.dialog.open(SendInviteModalComponent, {
            //   data: { roomId }
        });
    }
}
