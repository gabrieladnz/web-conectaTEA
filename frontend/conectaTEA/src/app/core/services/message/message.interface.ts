export interface ContentMessage {
    type: string;
    roomId: number;
    sender: string;
    content: string;
}
