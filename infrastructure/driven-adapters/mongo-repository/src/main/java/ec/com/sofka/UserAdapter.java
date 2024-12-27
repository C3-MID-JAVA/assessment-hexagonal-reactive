package ec.com.sofka;

import ec.com.sofka.config.IUserRepository;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserAdapter implements UserRepository {
    private final IUserRepository userRepository;

    public UserAdapter(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(UserEntityMapper.mapToEntity(user))
                .map(UserEntityMapper::mapToModel);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll()
                .map(UserEntityMapper::mapToModel);
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .map(UserEntityMapper::mapToModel);
    }

    @Override
    public Mono<User> findByCi(String ci) {
        return userRepository.findByCi(ci)
                .map(UserEntityMapper::mapToModel);
    }

}