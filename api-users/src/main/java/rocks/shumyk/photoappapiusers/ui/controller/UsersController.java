package rocks.shumyk.photoappapiusers.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocks.shumyk.photoappapiusers.ui.model.CreateUserRequestModel;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

	private final Environment env;

	@GetMapping("/status/check")
	public String status() {
		return "Working on port: " + env.getProperty("local.server.port");
	}

	@PostMapping
	public String createUser(@RequestBody final CreateUserRequestModel userDetails) {
		return "create user method is called";
	}
}
