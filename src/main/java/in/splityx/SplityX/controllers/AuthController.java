package in.splityx.SplityX.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import in.splityx.SplityX.Entity.User;
import in.splityx.SplityX.dto.Auth;
import in.splityx.SplityX.repo.UserRepo;
import in.splityx.SplityX.services.JwtService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value("${google.client-id}")
    private String googleClientId;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepository;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Auth data) {
        data.setToken(data.getToken().replace("\"", "").trim());
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken googleIdToken = verifier.verify(data.getToken());
            if (googleIdToken == null) {
                return ResponseEntity.status(401).body("Invalid token");
            }

            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String googleId = payload.getSubject();

            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setGoogleId(googleId);
                newUser.setRegisterDate(Instant.now().toString());
                newUser.setLastLogin(System.currentTimeMillis());
                newUser.setBalance(0);
                return userRepository.save(newUser);
            });

            String tokenId = jwtService.generateToken(email);
            return ResponseEntity.ok()
                    .header("Set-Cookie", String.format(
                            "sid=%s; Path=/; Max-Age=360000; HttpOnly; Secure; SameSite=None", tokenId
                    ))
                    .body(Map.of("token", tokenId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Error: " + e.getMessage());        }
    }
}
