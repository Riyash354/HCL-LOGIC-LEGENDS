package Doctor.Doctor.booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hms.hospital_management_service.Model.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,String> {
    long count();
}