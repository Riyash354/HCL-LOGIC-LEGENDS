package Doctor.Doctor.booking.Controller;

import java.util.HashMap;
import java.util.Map;
import Doctor.Doctor.booking.Repository.AppointmentRepo;
import Doctor.Doctor.booking.Repository.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/doctor")
public class DoctorDashboardController {

    @Autowired
    private AppointmentRepo appointmentRepository;

    @Autowired
    private DoctorRepo doctorRepository;

    @GetMapping("/dashboard/{docid}")
    public ResponseEntity<Map<String, Object>> getDoctorDashboardData(@PathVariable String docid) {
        Map<String, Object> response = new HashMap<>();

        long totalAppointments = appointmentRepository.countByDoctorId(docid);

        long totalPatients = appointmentRepository.countDistinctUsersByDoctorId(docid);

        Doctor doctor = doctorRepository.findById(docid).orElse(null);
        double totalEarnings = (doctor != null) ? totalAppointments * doctor.getFees() : 0;

        response.put("totalAppointments", totalAppointments);
        response.put("totalPatients", totalPatients);
        response.put("totalEarnings", totalEarnings);

        return ResponseEntity.ok(response);
    }
}
