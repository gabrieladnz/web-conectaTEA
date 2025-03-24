package conectaTEA.conectaTEA.dtos;

import conectaTEA.conectaTEA.models.User;
import lombok.*;



@Builder
public record UserDTOResponse(
        Long id,
        String name,
        String username,
        String email){

    public static UserDTOResponse fromEntity(User user){
        return UserDTOResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
