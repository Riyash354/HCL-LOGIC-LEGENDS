package Doctor.Doctor.booking.Controller;

import Doctor.Doctor.booking.DTOs.AuthRequest;
import Doctor.Doctor.booking.DTOs.AuthResponse;
import Doctor.Doctor.booking.DTOs.RegisterRequest;
import Doctor.Doctor.booking.JWT.JwtUtils;
import Doctor.Doctor.booking.Models.Role;
import Doctor.Doctor.booking.Models.UserDetails;
import Doctor.Doctor.booking.Repository.UserRepository;
import Doctor.Doctor.booking.Service.UserDetailsService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailsService userDetailsService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                    AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        User user = new User();
        user.setFullName(request.getFullname());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // user.setRoles(request.getRoles() != null ? Collections.singleton(Role.ROLE_ADMIN) : Collections.singleton(Role.ROLE_USER));

        Set<Role> roles = request.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = Collections.singleton(Role.ROLE_USER);
        }
        user.getRoles(roles);

        User savedUser=userRepository.save(user);

        // if the user has the ROLE_User role
        boolean isUser = savedUser.getRoles().forEachRemaining()
                .anyMatch(role -> role.equals(Role.ROLE_USER));

        if(isUser){
            UserDetails userDetails=new UserDetails();
            userDetails.setId(savedUser.getId());
            userDetailsService.createUserDetails(savedUser.getId(),userDetails);
        }

        // if the user has the ROLE_DOCTOR role
        boolean isDoctor = savedUser.getRoles().forEachRemaining()
                .anyMatch(role -> role.equals(Role.ROLE_DOCTOR));

        if (isDoctor) {
            return ResponseEntity.ok(savedUser.getId());
        }

        return ResponseEntity.ok(savedUser.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtUtils.generateToken(request.getUsername());

        Cookie cookie=new  Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600 *3);

        response.addCookie(cookie);

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("user not found"));
        return ResponseEntity.ok(new AuthResponse(user.getId()));
    }

}
