package rocks.shumyk.photo.app.api.users.ui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocks.shumyk.photo.app.api.users.service.UsersService;
import rocks.shumyk.photo.app.api.users.shared.UserDTO;
import rocks.shumyk.photo.app.api.users.ui.model.CreateUserRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

	private final Environment env;
	private final UsersService usersService;
	private final ModelMapper converter;

	@GetMapping("/status/check")
	public String status() {
		return String.format(
				"Working on port: %s, with secret token: %s",
				env.getProperty("local.server.port"),
				env.getProperty("jwt.token.secret")
		);
	}

	@GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserDTO> getUser(@PathVariable final String userId) {
		final UserDTO user = usersService.getUser(userId);
		return ResponseEntity.ok(user);
	}

	@PostMapping(
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<UserDTO> createUser(@RequestBody @Valid final CreateUserRequest userDetails) {
		final UserDTO userDto = converter.map(userDetails, UserDTO.class);
		final UserDTO createdUser = usersService.createUser(userDto);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createdUser);
	}
}
