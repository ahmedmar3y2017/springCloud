package com.auth.services;

import com.auth.domains.User;
import com.auth.dtos.UserDto;
import com.auth.mappers.UserMapper;
import com.auth.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto save(User user) {
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public void deleteUser(long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
    }
}