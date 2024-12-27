package ec.com.sofka.mapper;

import ec.com.sofka.User;
import ec.com.sofka.data.UserEntity;

public class UserMapperEntity {
    public static User fromEntity(UserEntity userEntity){
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getDocumentId());
    }

    public static UserEntity toEntity(User user){
        return new UserEntity(user.getId(), user.getName(), user.getDocumentId());
    }
}
