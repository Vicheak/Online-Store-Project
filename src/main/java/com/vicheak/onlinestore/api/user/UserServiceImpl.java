package com.vicheak.onlinestore.api.user;

import com.vicheak.onlinestore.api.user.web.NewUserDto;
import com.vicheak.onlinestore.api.user.web.UpdateUserDto;
import com.vicheak.onlinestore.api.user.web.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public void createNewUser(NewUserDto newUserDto) {
        //Check username if exist
        if (userRepository.existsByUsernameAndIsDeletedFalse(newUserDto.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists...!");
        }

        //Check email if exist
        if (userRepository.existsByEmailAndAndIsDeletedFalse(newUserDto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists...!");
        }

        User newUser = userMapper.fromNewUserDto(newUserDto);

        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setIsVerified(false);
        newUser.setIsDeleted(false);

        boolean isNotFound = newUserDto.roleIds().stream()
                .anyMatch(roleId -> !roleRepository.existsById(roleId));

        if (isNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Role ID does not exist...!");
        }

        Set<Role> roles = newUserDto.roleIds().stream()
                .map(roleId -> Role.builder().id(roleId).build())
                .collect(Collectors.toSet());

        newUser.setRoles(roles);

        userRepository.save(newUser);
    }

    @Override
    public void updateByUuid(String uuid, UpdateUserDto updateUserDto) {
        //Check email if exist
        if (userRepository.existsByEmailAndAndIsDeletedFalse(updateUserDto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists...!");
        }

        User foundUser = userRepository.selectUserByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User is not found!")
                );

        userMapper.fromUpdateUserDto(foundUser, updateUserDto);

        userRepository.save(foundUser);
    }

    @Override
    public UserDto findByUuid(String uuid) {
        User foundUser = userRepository.selectUserByUuidAndIsDeleted(uuid, false)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("User UUID = %s doesn't exist in db!", uuid))
                );
        return userMapper.toUserDto(foundUser);
    }

    @Transactional
    @Override
    public void deleteByUuid(String uuid) {
        User foundUser = userRepository.selectUserByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("User UUID = %s doesn't exist in db!", uuid))
                );
        userRepository.delete(foundUser);
    }

    @Transactional
    @Override
    public void updateIsDeletedByUuid(String uuid, Boolean isDeleted) {

        Boolean isFound = userRepository.checkUserByUuid(uuid);

        //System.out.println("isFound => " + isFound);

        if (isFound) {
            userRepository.updateIsDeletedStatusByUuid(uuid, isDeleted);
            return;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User UUID = %s doesn't exist in db!", uuid));

    }

}
