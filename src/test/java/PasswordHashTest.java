import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordHashTest {
    @Test
    public void testPasswordMatchesHash() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "password";  // <-- Use the exact raw password you hashed
        String hashedPassword = "$2a$12$b2Xfk.N7eiOUWmH9P64VluMclaLqCCdWixmOIZVX1LMiFn3Mhb50m";

        boolean matches = encoder.matches(rawPassword, hashedPassword);

        System.out.println("Password matches? " + matches);

        assertTrue(matches, "Raw password should match the hashed password");
    }
}
