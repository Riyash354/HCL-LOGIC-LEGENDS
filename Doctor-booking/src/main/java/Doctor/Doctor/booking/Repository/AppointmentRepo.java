package Doctor.Doctor.booking.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hms.hospital_management_service.Model.Appointment;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {

    long count();

    List<Appointment> findByUserId(Long userId);

    Optional<Appointment> findByIdAndUserId(Long appointmentId, Long userId);
    @Query("SELECT a FROM Appointment a WHERE a.doctor._id = :doctorId")
    List<Appointment> findByDoctorId(@Param("doctorId") String doctorId);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.doctor._id = :doctorId")
    long countByDoctorId(@Param("doctorId") String doctorId);

    @Query("SELECT COUNT(DISTINCT a.user.id) FROM Appointment a WHERE a.doctor.id = :doctorId")
    long countDistinctUsersByDoctorId(@Param("doctorId") String doctorId);
}
