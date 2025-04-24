// Libs
import {
    Component,
    ElementRef,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
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
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

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
import { ContentMessage } from '../../core/services/message/message.interface';

@Component({
    selector: 'app-chat',
    imports: [
        SidebarComponent,
        MatButtonModule,
        MatIconModule,
        ReactiveFormsModule,
        FormsModule,
        CommonModule,
    ],
    templateUrl: './chat.component.html',
    styleUrl: './chat.component.scss',
})
export class ChatComponent implements OnInit, OnDestroy {
    @ViewChild('messageInputArea')
    protected messageInputArea!: ElementRef<HTMLTextAreaElement>;
    protected listConversations!: RoomDtoResponse[];
    protected selectedConversationId: number | null = null;
    protected selectedConversation: RoomDtoResponse | null = null;
    protected messageForm: FormGroup;
    protected listMessages: ContentMessage[] = [];
    private messageSubscription: Subscription | null = null;
    protected searchTerm: string = '';

    protected get filteredConversations() {
        return this.listConversations.filter((conversation) =>
            conversation.name
                .toLowerCase()
                .includes(this.searchTerm.toLowerCase()),
        );
    }

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

    ngOnInit() {
        this.messageSubscription = this.messageService
            .onNewMessage()
            .subscribe((message) => {
                this.getMyRooms();

                if (message.roomId === this.selectedConversationId) {
                    this.listMessagePerConversation();
                }
            });
    }

    ngOnDestroy(): void {
        if (this.messageSubscription) {
            this.messageSubscription.unsubscribe();
        }
    }

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

        this.listMessagePerConversation();
    }

    protected createRoom(): void {
        const modalRef = this.dialog.open(CreateRoomModalComponent);

        modalRef.componentInstance.create.subscribe(() => {
            modalRef.close();
            this.getMyRooms();
        });
    }

    protected getMyRooms(): void {
        this.roomService
            .listRoomsByUser(this.userId)
            .then((rooms: RoomDtoResponse[]) => {
                this.listConversations = rooms;
            });
    }

    protected async listMessagePerConversation(): Promise<void> {
        this.listMessages = await this.messageService.listMessagesByRoom(
            this.selectedConversationId,
        );
    }

    protected sendMessage(): void {
        this.messageService.sendMessage(this.messageForm.value);
        this.messageForm.patchValue({ content: '' });
        this.autoResize();
    }

    protected openInviteUserModal(): void {
        this.dialog.open(SendInviteModalComponent, {
            data: {
                roomId: this.selectedConversationId,
                senderId: this.userId,
            },
        });
    }

    private senderColorMap = new Map<string, string>();
    private availableColors = [
        'color-a',
        'color-b',
        'color-c',
        'color-d',
        'color-e',
    ];

    protected getColorClass(sender: string): string {
        if (!this.senderColorMap.has(sender)) {
            const color =
                this.availableColors[
                    this.senderColorMap.size % this.availableColors.length
                ];
            this.senderColorMap.set(sender, color);
        }
        return this.senderColorMap.get(sender)!;
    }

    protected getMessageClasses(message: ContentMessage): string[] {
        const classes = ['message'];

        if (message.sender === this.userName) {
            classes.push('message--sent');
        } else {
            classes.push(
                'message--received',
                this.getColorClass(message.sender),
            );
        }

        return classes;
    }
}
