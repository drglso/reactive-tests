package edu.reactive.demo.infraestructure.entrypoints.login;

import edu.reactive.demo.infraestructure.entrypoints.security.ReactiveJwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MapReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ReactiveJwtManager reactiveJwtManager;
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";
    private static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Invalid credentials";

    @PostMapping("/login")
    public Mono<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get(USER);
        String password = credentials.get(PASSWORD);

        return userDetailsService.findByUsername(username)
                .filter(userDetails -> userDetails != null &&
                        passwordEncoder.matches(password, userDetails.getPassword()))
                .flatMap(isAuthenticated -> {
                    String jwt = reactiveJwtManager.generateJwtToken(username);
                    Map<String, String> response = new HashMap<>();
                    response.put(TOKEN, jwt);
                    return Mono.just(response);
                })
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        INVALID_CREDENTIALS_ERROR_MESSAGE).getMostSpecificCause())))
                .doOnError(throwable -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
                        .getMostSpecificCause());
    }

}
