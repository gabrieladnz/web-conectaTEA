package conectaTEA.conectaTEA.services.rest;

import conectaTEA.conectaTEA.repositories.RoomUsersRepository;

public class RoomUserService {

    private RoomUsersRepository roomUsersRepository;

    public RoomUserService(RoomUsersRepository roomUsersRepository){
        this.roomUsersRepository = roomUsersRepository;
    }

}
