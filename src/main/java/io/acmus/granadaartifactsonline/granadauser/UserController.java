package io.acmus.granadaartifactsonline.granadauser;

import io.acmus.granadaartifactsonline.granadauser.converter.UserDtoToUserConverter;
import io.acmus.granadaartifactsonline.granadauser.converter.UserToUserDtoConverter;
import io.acmus.granadaartifactsonline.granadauser.dto.UserDto;
import io.acmus.granadaartifactsonline.system.Result;
import io.acmus.granadaartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {
    private final UserService userService;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final UserDtoToUserConverter userDtoToUserConverter;

    public UserController(UserService userService,
                          UserToUserDtoConverter userToUserDtoConverter,
                          UserDtoToUserConverter userDtoToUserConverter) {
        this.userService = userService;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
    }

    @GetMapping
    public Result findAllUsers() {
        List<GranadaUser> users = this.userService.findAll();
        List<UserDto> userDtos = users.stream().map(this.userToUserDtoConverter::convert)
                .toList();

        return new Result(true, StatusCode.SUCCESS, "Find All SUCCESS", userDtos);
    }

    @GetMapping("/{userId}")
    public Result findUserById(@PathVariable Integer userId) {
        GranadaUser foundUser = this.userService.findById(userId);
        UserDto userDto = this.userToUserDtoConverter.convert(foundUser);
        return new Result(true, StatusCode.SUCCESS, "Find One SUCCESS", userDto);
    }

    @PostMapping
    public Result addUser(@Valid @RequestBody GranadaUser user) {
        GranadaUser savedUser = this.userService.save(user);
        UserDto userDto = this.userToUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add SUCCESS", userDto);
    }

    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto update) {
        GranadaUser user = this.userDtoToUserConverter.convert(update);
        GranadaUser updated = this.userService.update(userId, user);
        UserDto updatedUserDto  = this.userToUserDtoConverter.convert(updated);
        return new Result(true, StatusCode.SUCCESS, "Update SUCCESS", updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable Integer userId) {
        this.userService.delete(userId);
        return new Result(true, StatusCode.SUCCESS, "Delete SUCCESS");
    }

}
