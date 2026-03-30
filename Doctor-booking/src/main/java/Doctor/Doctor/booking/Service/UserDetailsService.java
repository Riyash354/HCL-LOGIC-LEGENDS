package Doctor.Doctor.booking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hms.hospital_management_service.Model.User;
import com.hms.hospital_management_service.Model.UserDetails;
import com.hms.hospital_management_service.Repository.UserDetailsRepository;
import com.hms.hospital_management_service.Repository.UserRepository;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    public UserDetails createUserDetails(Long userId,UserDetails userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        userDetails.setUser(user);

        return userDetailsRepository.save(userDetails);
    }

    public UserDetails getUserDetailsById(Long userId) {
        return userDetailsRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User details not found"));
    }

    public UserDetails updateUserDetails(Long userId, UserDetails newDetails) {
        UserDetails existingDetails = userDetailsRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User details not found"));

        existingDetails.setAddress(newDetails.getAddress());
        existingDetails.setPhone(newDetails.getPhone());
        existingDetails.setGender(newDetails.getGender());
        existingDetails.setDob(newDetails.getDob());
        existingDetails.setImageurl(newDetails.getImageurl());

        return userDetailsRepository.save(existingDetails);
    }

    public void deleteUserDetails(Long userId) {
        userDetailsRepository.deleteById(userId);
    }

    public String getImageURL(Long id) throws Exception {
        UserDetails user = userDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        String imageurl = user.getImageurl();
        return imageurl;
    }
}
