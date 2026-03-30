package Doctor.Doctor.booking.Controller;

import java.util.Map;
import Doctor.Doctor.booking.Models.UserDetails;
import Doctor.Doctor.booking.Service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable Long id) {
        return ResponseEntity.ok(userDetailsService.getUserDetailsById(id));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<UserDetails> updateUserDetails(@PathVariable Long id, @RequestBody UserDetails userDetails) {
        return ResponseEntity.ok(userDetailsService.updateUserDetails(id, userDetails));
    }

    @DeleteMapping("delete-user/{id}")
    public ResponseEntity<Map<String, String>> deleteUserDetails(@PathVariable Long id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.ok(Map.of("message", "User details deleted successfully."));
    }

    @GetMapping("/getimageurl/{id}")
    public ResponseEntity<?> getImageURL(@PathVariable Long id) throws Exception {
        String imageurl = userDetailsService.getImageURL(id);

        if (imageurl == null || imageurl.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "Image URL not found for user ID: " + id
            ));
        }
        return ResponseEntity.ok(Map.of("imageURL", imageurl));
    }
}
