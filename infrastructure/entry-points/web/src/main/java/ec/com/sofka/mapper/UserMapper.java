package ec.com.sofka.mapper;


import ec.com.sofka.User;
import ec.com.sofka.dto.UserRequestDTO;
import ec.com.sofka.dto.UserResponseDTO;

public class UserMapper {

    public static UserResponseDTO fromEntity(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getDocumentId());
    }

    public static User toEntity(UserRequestDTO userRequestDTO){
        return new User(userRequestDTO.getName(), userRequestDTO.getDocumentId());
    }

}
