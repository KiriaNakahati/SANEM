package com.sanem.share.ong.models;

import com.sanem.share.ong.dtos.auth.RegisterDTO;
import com.sanem.share.ong.enums.User_Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "TB_Funcionario")
@EqualsAndHashCode(of = "id")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotBlank
    private String firstName;

    @Column(nullable = false)
    @NotBlank
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String cpf;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private User_Role userRole = User_Role.USER;

    @Column(nullable = false)
    private boolean emailConfirmed = false;

    public User(RegisterDTO registerDTO) {
        this.firstName = registerDTO.first_name();
        this.lastName = registerDTO.last_name();
        this.email = registerDTO.email();
        this.cpf = registerDTO.cpf();
        this.password = registerDTO.password();
    }

    public User() {}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRole == User_Role.ADMIN) {
            return Stream.of(User_Role.values())
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .collect(Collectors.toList());
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_" + this.userRole.name()));
        }
    }

    @Override
    public String getUsername() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
