package com.vicheak.onlinestore.api.auth;

import com.vicheak.onlinestore.api.auth.web.RegisterDto;
import com.vicheak.onlinestore.api.user.web.NewUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    NewUserDto mapRegisterDtoToNewUserDto(RegisterDto registerDto);

}
