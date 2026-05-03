package in.splityx.SplityX.controllers;
import in.splityx.SplityX.Entity.User;
import in.splityx.SplityX.repo.UserRepo;
import in.splityx.SplityX.services.JwtService;
import in.splityx.SplityX.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {
@Autowired
private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/user")
    public ResponseEntity<?> user(@CookieValue(value = "sid", required = false) String sid) {

        if (sid == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        String email = jwtService.extractEmail(sid);
        return ResponseEntity.ok(userRepo.findByEmail(email).orElse(null));
    }
}
