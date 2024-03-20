package io.acmus.granadaartifactsonline.granadauser.converter;

import io.acmus.granadaartifactsonline.granadauser.GranadaUser;
import io.acmus.granadaartifactsonline.granadauser.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, GranadaUser> {
    @Override
    public GranadaUser convert(UserDto source) {

        GranadaUser user = new GranadaUser();

        user.setUsername(source.username());
        user.setEnabled(source.enabled());
        user.setRoles(source.roles());

        return user;
    }
}
