package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entities.UserInfo;
import org.example.exception.InvalidUserInputException;
import org.example.exception.UserAlreadyExistsException;
import org.example.model.UserInfoDto;
import org.example.repository.UserRepository;
import org.example.utils.ValidateUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserDetailsServiceImplement implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user: " + userName);
        }

        return new CustomUserDetails(user); // assuming you implemented this
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto) {
        return userRepository.findByUserName(userInfoDto.getUserName());
    }

    public Boolean signUpUser(UserInfoDto userInfoDto) {
        if(!ValidateUserDetails.isValidEmail(userInfoDto.getEmail())) {
            throw new InvalidUserInputException("Invalid email format. Example: yourname@example.com");
        }
        if (!ValidateUserDetails.isValidPassword(userInfoDto.getPassword())) {
            throw new InvalidUserInputException("Password must be at least 8 characters and contain uppercase, lowercase, number, and special character.");
        }

        if(Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))) {
            throw new UserAlreadyExistsException("User already exists with username or email.");
        }

        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        String userId = UUID.randomUUID().toString();
        userRepository.save(
                new UserInfo(
                userId,
                userInfoDto.getUserName(),
                userInfoDto.getPassword(),
                new HashSet<>()));
        return true;
    }
}
