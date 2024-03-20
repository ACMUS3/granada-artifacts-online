package io.acmus.granadaartifactsonline.granadauser.converter;

import io.acmus.granadaartifactsonline.granadauser.GranadaUser;
import io.acmus.granadaartifactsonline.granadauser.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<GranadaUser, UserDto> {
    @Override
    public UserDto convert(GranadaUser source) {
        final UserDto user = new UserDto(

                source.getId(),
                source.getUsername(),
                source.isEnabled(),
                source.getRoles()
        );

        return user;
    }
}
