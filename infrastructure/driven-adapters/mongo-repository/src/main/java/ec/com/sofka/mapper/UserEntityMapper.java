package ec.com.sofka.mapper;

import ec.com.sofka.User;
import ec.com.sofka.data.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public static UserEntity mapToEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getCi(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus());
    }

    public static User mapToModel(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getCi(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getStatus()
        );
    }

}