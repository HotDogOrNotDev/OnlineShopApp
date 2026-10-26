package ru.skypro.homework.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.controller.dto.enums.Role;
import ru.skypro.homework.db.entity.User;
import ru.skypro.homework.db.repository.UserRepository;

import java.util.Optional;

/**
 * Класс AdsUserDetailsService реализует интерфейс UserDetailsManager, предоставляя методы для
 * работы с пользовательскими данными в рамках Spring Security. Он включает в себя методы для
 * загрузки, создания, обновления, удаления пользователей, а также для проверки существования
 * пользователя в системе.
 */
@Service
@AllArgsConstructor
public class AdsUserDetailsService implements UserDetailsManager {

    private final UserRepository repository;

    /**
     * Загружает пользователя по его имени (в данном случае email).
     *
     * @param username имя пользователя, используемое для аутентификации
     * @return объект UserDetails, представляющий данные пользователя
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(username);

        // Преобразует найденного пользователя в AdsUserDetails или выбрасывает исключение, если пользователь не найден
        return user.map(AdsUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
    }

    /**
     * Создает нового пользователя в системе.
     *
     * @param user объект UserDetails, представляющий данные нового пользователя
     */
    @Override
    public void createUser(UserDetails userDetails) {
        User user = new User();
        user.setEmail(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setFirstName("User");
        user.setLastName("User");
        user.setPhone("+70000000000");
        user.setRole(Role.USER);
        repository.save(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException("Профиль обновляется через UserService");
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException("Удаление пользователя не поддерживается");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Пароль меняется через UserService");
    }

    /**
     * Проверяет, существует ли пользователь с указанным именем.
     *
     * @param username имя пользователя для проверки
     * @return true, если пользователь существует; false в противном случае
     */
    @Override
    public boolean userExists(String username) {
        // Проверяет наличие пользователя с указанным email в базе данных
        return repository.existByEmail(username);
    }

}
