package ec.com.sofka.mapper;

import ec.com.sofka.User;
import ec.com.sofka.data.UserRequestDTO;
import ec.com.sofka.data.UserResponseDTO;

public class UserModelMapper {

    public static User mapToModel(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setCi(userRequestDTO.getCi());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setStatus(userRequestDTO.getStatus());
        return user;
    }

    public static UserResponseDTO mapToDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getCi(),
                user.getEmail(),
                user.getStatus()
        );
    }

}