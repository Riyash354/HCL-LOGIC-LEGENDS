package Doctor.Doctor.booking.Service;

import org.springframework.stereotype.Service;
import com.hms.hospital_management_service.Repository.AppointmentRepo;
import com.hms.hospital_management_service.Repository.DoctorRepo;
import com.hms.hospital_management_service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DashboardService {

    @Autowired
    private DoctorRepo doctorRepository;

    @Autowired
    private AppointmentRepo appointmentRepository;

    @Autowired
    private UserRepository patientRepository;

    public long getTotalDoctors() {
        return doctorRepository.count();
    }

    public long getTotalAppointments() {
        return appointmentRepository.count();
    }

    public long getTotalPatients() {
        return patientRepository.count();
    }
}