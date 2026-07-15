package com.ebank.backend.security;

import com.ebank.backend.entities.AppUser;
import com.ebank.backend.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("Utilisateur introuvable : " + username);
        }

        List<GrantedAuthority> authorities = appUser.getRoles().stream()
                .<GrantedAuthority>map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .toList();

        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
