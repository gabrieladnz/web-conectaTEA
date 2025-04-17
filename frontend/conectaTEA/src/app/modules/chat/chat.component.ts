// Libs
import { Component, ElementRef, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

// Components
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';

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

    protected autoResize(): void {
        const textarea = this.messageInputArea.nativeElement;
        textarea.style.height = 'auto';
        textarea.style.height = `${textarea.scrollHeight}px`;
    }
}
