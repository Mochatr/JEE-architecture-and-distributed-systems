package com.ebank.backend.web;

import com.ebank.backend.dtos.ChangePasswordDTO;
import com.ebank.backend.entities.AppUser;
import com.ebank.backend.repositories.AppUserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@Tag(name = "Users", description = "Gestion du compte utilisateur")
public class UserController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @PutMapping("/users/me/password")
    public Map<String, String> changePassword(@Valid @RequestBody ChangePasswordDTO request, Authentication authentication) {
        AppUser user = appUserRepository.findByUsername(authentication.getName());

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Mot de passe actuel incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        appUserRepository.save(user);

        return Map.of("message", "Mot de passe modifié avec succès");
    }
}
