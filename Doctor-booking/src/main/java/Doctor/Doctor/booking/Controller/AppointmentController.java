package Doctor.Doctor.booking.Controller;

import java.util.List;

import Doctor.Doctor.booking.DTOs.AppointmentDTO;
import Doctor.Doctor.booking.Models.Appointment;
import Doctor.Doctor.booking.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/user/createappointment")
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        Appointment savedAppointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    @GetMapping("/getall/user/{userId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByUser(@PathVariable Long userId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByUser(userId);
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/delete/user/{userId}/appointment/{appointmentId}")
    public ResponseEntity<String> deleteAppointmentForUser(@PathVariable Long userId, @PathVariable Long appointmentId) {
        appointmentService.deleteAppointmentForUser(userId, appointmentId);
        return ResponseEntity.ok("Appointment with ID " + appointmentId + " for user ID " + userId + " has been deleted.");
    }

    @DeleteMapping("/delete/appointment/{appointmentId}")
    public ResponseEntity<String> deleteAppointmentbyId(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
        return ResponseEntity.ok("Appointment with ID " + appointmentId + " has been deleted.");
    }

    @GetMapping("/getall/doctor/{docId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable String docId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(docId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{app_id}/roomid/{room_id}")
    public ResponseEntity<String> setRoomId(@PathVariable Long app_id,@PathVariable String room_id){
        appointmentService.setRoomId( app_id, room_id);
        return ResponseEntity.ok("Room id is set");
    }

    @PutMapping("/setstatus/{appt_id}")
    public ResponseEntity<String> setStatus(@PathVariable Long appt_id){
        appointmentService.setStatus( appt_id);
        return ResponseEntity.ok("Status is set");
    }
    @PutMapping("/setpaymentstatus/{appt_id}")
    public ResponseEntity<String> setPaymentStatus(@PathVariable Long appt_id){
        appointmentService.setPaymentStatus( appt_id);
        return ResponseEntity.ok("payment done");
    }
}
