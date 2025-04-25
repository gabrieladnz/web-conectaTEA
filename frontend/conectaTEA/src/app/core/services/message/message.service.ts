// Libs
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { lastValueFrom, Observable, Subject } from 'rxjs';

// Services
import { ApiService } from './../../api/api.service';

// Interfaces
import { ContentMessage } from './message.interface';

@Injectable({
    providedIn: 'root',
})
export class MessageService extends ApiService {
    private newMessageSubject = new Subject<ContentMessage>();
    private stompClient!: Client;

    constructor(protected override http: HttpClient) {
        super(http);
    }

    public onNewMessage(): Observable<ContentMessage> {
        return this.newMessageSubject.asObservable();
    }

    public connectToWebSocket() {
        this.stompClient = new Client({
            webSocketFactory: () =>
                new WebSocket(
                    'wss://conectatea.squareweb.app/api/websocket-chat/websocket',
                ),
        });

        this.stompClient.onConnect = () => {
            console.log('WebSocket conectou');

            this.stompClient.subscribe('/topic/room.*', (message) => {
                const receivedMessage = JSON.parse(
                    message.body,
                ) as ContentMessage;

                console.log('Mensagem recebida:', JSON.parse(message.body));
                this.newMessageSubject.next(receivedMessage);
            });
        };

        this.stompClient.onStompError = (frame) => {
            console.error('Erro na conexão Stomp:', frame);
        };

        this.stompClient.activate();
    }

    public sendMessage(body: ContentMessage): void {
        if (this.stompClient && this.stompClient.connected) {
            this.stompClient.publish({
                destination: '/app/chat.sendMessage',
                body: JSON.stringify(body),
            });

            console.log('Mensagem enviada:', body);
        } else {
            console.error(
                'Cliente STOMP não está conectado. Mensagem não enviada.',
            );
        }
    }

    public disconnect(): void {
        if (this.stompClient) {
            this.stompClient.deactivate();
            console.log('Desconectado do WebSocket');
        }
    }

    public async listMessagesByRoom(
        roomId: number | null,
    ): Promise<ContentMessage[]> {
        try {
            const token: string | null = localStorage.getItem('auth_token');
            if (!token) throw new Error('Token de autenticação não encontrado');

            return await lastValueFrom(
                this.get<ContentMessage[]>(`rooms/${roomId}/messages`, token),
            );
        } catch (error) {
            const errorResponse = {
                success: false,
                message: error,
            };

            throw errorResponse;
        }
    }
}
