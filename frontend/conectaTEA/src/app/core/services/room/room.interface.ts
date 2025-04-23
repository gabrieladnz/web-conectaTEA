export interface CreateRoomRequest {
    name: string;
    description: string;
    roomType: string;
    category: string;
    usernames: [];
}

export interface CreateRoomResponse {
    success: boolean;
    message: string;
}

export interface RoomDtoResponse {
    id: number;
    name: string;
    description: string;
    roomType: string;
    category: string | null;
    users: UserDtoResponse[];
}

export interface UserDtoResponse {
    id: number;
    name: string;
    username: string;
    email: string;
}
