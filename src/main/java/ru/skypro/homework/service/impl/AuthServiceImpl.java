package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.controller.dto.RegisterDTO;
import ru.skypro.homework.controller.dto.enums.Role;
import ru.skypro.homework.db.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

/**
 * Реализация сервиса для аутентификации и регистрации пользователей.
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Выполняет аутентификацию пользователя.
     *
     * @param userName имя пользователя
     * @param password пароль пользователя
     * @return true, если имя пользователя существует и пароль совпадает, иначе false
     */
    @Override
    public boolean login(String userName, String password) {
        // Проверяем, существует ли пользователь
        if (!manager.userExists(userName)) {
            return false;
        }
        // Загружаем детали пользователя и проверяем пароль
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    /**
     * Регистрирует нового пользователя.
     *
     * @param register данные для регистрации пользователя
     * @return true, если регистрация успешна, иначе false
     */
    @Override
    @Transactional
    public boolean register(RegisterDTO register) {
        // Проверяем, существует ли уже пользователь с таким именем
        if (manager.userExists(register.getUserName())) {
            return false;
        }

        User user = userMapper.registerDTOToUser(register);
        user.setPassword(encoder.encode(register.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        userService.saveUser(user);
        return true;
    }

}
