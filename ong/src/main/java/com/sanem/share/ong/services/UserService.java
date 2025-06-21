package com.sanem.share.ong.services;

import com.sanem.share.ong.dtos.auth.AuthenticationDTO;
import com.sanem.share.ong.dtos.auth.RegisterDTO;
import com.sanem.share.ong.dtos.auth.ResetPasswordDTO;
import com.sanem.share.ong.dtos.auth.UpdateDTO;
import com.sanem.share.ong.infra.security.JwtUtils;
import com.sanem.share.ong.models.User;
import com.sanem.share.ong.repositories.UserRepository;
import com.sanem.share.ong.user_cases.interfaces.UserEmailValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final List<UserEmailValidator> userEmailValidators;

    public UserService(UserRepository userRepository,
                       @Lazy AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils,
                       List<UserEmailValidator> userEmailValidators) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userEmailValidators = userEmailValidators;
    }

    @Transactional
    public void Create(@Valid RegisterDTO dto) {
        userEmailValidators.forEach(v -> v.validate(dto));
        User newUser = new User(dto);
        String encrypted = new BCryptPasswordEncoder().encode(dto.password());
        newUser.setPassword(encrypted);
        userRepository.save(newUser);
    }

    @Transactional
    public void Update(UpdateDTO dto, UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found."));
        if (dto.first_name() != null) user.setFirstName(dto.first_name());
        if (dto.last_name()  != null) user.setLastName(dto.last_name());
        if (dto.email()      != null) user.setEmail(dto.email());
        if (dto.password()   != null) user.setPassword(dto.password());
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void confirmAccount(String token) {
        String email = jwtUtils.validate(token);
        User user = Optional.ofNullable(findUserByEmail(email))
                .orElseThrow(() -> new IllegalArgumentException("E-mail does not exist"));
        if (user.isEmailConfirmed()) {
            throw new IllegalStateException("E-mail has already been confirmed");
        }
        user.setEmailConfirmed(true);
        userRepository.save(user);
    }

    public String login(AuthenticationDTO dto) throws AuthenticationException {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            var auth = authenticationManager.authenticate(authToken);
            User user = (User) auth.getPrincipal();
            // Usando nova assinatura: subject = email, userId = id.toString()
            return jwtUtils.generateJwt(user.getEmail(), user.getId().toString());
        } catch (Exception e) {
            throw new AuthenticationException("Auth failed");
        }
    }

    @Transactional
    public Boolean resetPassword(String token, ResetPasswordDTO dto) {
        String newPass = dto.newPassword();
        if (newPass == null || newPass.isBlank()) return false;

        String email = jwtUtils.validate(token);
        User user = findUserByEmail(email);
        if (user == null) return false;

        user.setPassword(new BCryptPasswordEncoder().encode(newPass));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public void Delete(UUID id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
