// Libs
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';

// Services
import { ApiService } from './../../api/api.service';

// Interfaces
import { ContentMessage } from './message.interface';

@Injectable({
    providedIn: 'root',
})
export class MessageService extends ApiService {
    private stompClient!: Client;

    constructor(protected override http: HttpClient) {
        super(http);
    }

    public connectToWebSocket() {
        this.stompClient = new Client({
            webSocketFactory: () =>
                new WebSocket(
                    'ws://localhost:8080/api/websocket-chat/websocket',
                ),
        });

        this.stompClient.onConnect = (frame) => {
            console.log('WebSocket conectou');

            this.stompClient.subscribe('/topic/public', (message) => {
                console.log('Mensagem recebida:', JSON.parse(message.body));
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
}
