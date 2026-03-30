package Doctor.Doctor.booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hms.hospital_management_service.Model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    long count();

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
