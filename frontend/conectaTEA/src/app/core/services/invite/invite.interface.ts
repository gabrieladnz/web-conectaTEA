export interface InviteRequest {
    senderId: number;
    recipientUsername: string;
    roomId: number;
}

export interface InviteResponse {
    id: number;
    senderId: number;
    senderName: string;
    recipientId: number;
    recipientName: string;
    roomName: string;
    descriptionRoom: string;
    category: string;
    status: string;
}
