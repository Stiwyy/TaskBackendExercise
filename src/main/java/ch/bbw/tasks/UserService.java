package ch.bbw.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, UpdateUserDTO updateUserDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (updateUserDTO.getUsername() != null) {
                user.setUsername(updateUserDTO.getUsername());
            }
            if (updateUserDTO.getPassword() != null) {
                user.setPassword(updateUserDTO.getPassword());
            }
            if (updateUserDTO.getEmail() != null) {
                user.setEmail(updateUserDTO.getEmail());
            }
            return Optional.of(userRepository.save(user));
        } else {
            return Optional.empty();
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(String rawPassword, String storedPassword) {
        return rawPassword.equals(storedPassword);
    }
}
