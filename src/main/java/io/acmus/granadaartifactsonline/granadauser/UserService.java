package io.acmus.granadaartifactsonline.granadauser;

import io.acmus.granadaartifactsonline.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<GranadaUser> findAll() {
        return this.userRepository.findAll();
    }

    public GranadaUser findById(Integer userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    }

    public GranadaUser save(GranadaUser user) {
        return this.userRepository.save(user);
    }

    public GranadaUser update(Integer userId, GranadaUser update) {
        GranadaUser oldUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("update", userId));

        oldUser.setUsername(update.getUsername());
        oldUser.setEnabled(update.isEnabled());
        oldUser.setRoles(update.getRoles());

        return this.userRepository.save(oldUser);
    }

    public void delete(Integer userId) {
        this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("update", userId));

        this.userRepository.deleteById(userId);

    }


}
