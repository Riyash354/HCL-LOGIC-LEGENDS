package Doctor.Doctor.booking.DTOs;

import java.util.Set;
import com.hms.hospital_management_service.Model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String fullname;
    private String password;

    private Set<Role> roles;
}
