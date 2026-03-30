package Doctor.Doctor.booking.Controller;

import Doctor.Doctor.booking.Models.Appointment;
import Doctor.Doctor.booking.Service.AppointmentService;
import Doctor.Doctor.booking.Service.DashboardService;
import Doctor.Doctor.booking.Service.DoctorService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/dashboard/counts")
    public Map<String, Long> getCounts() {
        long totalDoctors = dashboardService.getTotalDoctors();
        long totalAppointments = dashboardService.getTotalAppointments();
        long totalPatients = dashboardService.getTotalPatients();

        return Map.of(
                "totalDoctors", totalDoctors,
                "totalAppointments", totalAppointments,
                "totalPatients", totalPatients
        );
    }

    @PostMapping("/doctor/add")
    public <Doctor> ResponseEntity<Doctor> createUser(@RequestBody Doctor doctor, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception{
        System.out.println("Authorization Header: " + authHeader);
        Doctor savedDoctor=doctorService.createDoctor(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    @GetMapping("/getalldoctors")
    public ResponseEntity<List<Doctor>> getAllDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @DeleteMapping("/deletedoctor/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable String id){
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/all-appointments")
    public ResponseEntity<List<Appointment>> getallAppointments(){
        List<Appointment> appointments=appointmentService.getallAppointments();
        return ResponseEntity.ok(appointments);
    }

}
