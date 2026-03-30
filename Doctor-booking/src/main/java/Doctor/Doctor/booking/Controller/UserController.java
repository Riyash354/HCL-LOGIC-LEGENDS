package Doctor.Doctor.booking.Controller;

import java.util.List;
import Doctor.Doctor.booking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getallusers")
    public ResponseEntity<List<User>> getMethodName(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/getuserbyid/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) throws Exception{
        return ResponseEntity.ok(userService.updateUser(user, id));
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK);
    }
}