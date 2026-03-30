package Doctor.Doctor.booking.Service;

import java.util.List;

import Doctor.Doctor.booking.Models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hms.hospital_management_service.DTOs.AppointmentDTO;
import com.hms.hospital_management_service.Model.Appointment;
import com.hms.hospital_management_service.Model.Doctor;
import com.hms.hospital_management_service.Model.User;
import com.hms.hospital_management_service.Model.UserDetails;
import com.hms.hospital_management_service.Repository.AppointmentRepo;
import com.hms.hospital_management_service.Repository.DoctorRepo;
import com.hms.hospital_management_service.Repository.UserDetailsRepository;
import com.hms.hospital_management_service.Repository.UserRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private DoctorRepo doctorRepository;

    public <AppointmentDTO> Appointment createAppointment(AppointmentDTO appointmentDTO) {
        // Find user and doctor by ID
        User user = userRepository.findById(appointmentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        UserDetails userDetails = userDetailsRepository.findById(appointmentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("user details not found"));

        // Create new appointment
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setUserDetails(userDetails);
        appointment.setPaymentStatus(false);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByUser(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public void deleteAppointmentForUser(Long userId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findByIdAndUserId(appointmentId, userId)
                .orElseThrow(() -> new RuntimeException("No appointment found with ID " + appointmentId + " for user ID " + userId));
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> getallAppointments(){
        return appointmentRepository.findAll();
    }

    public void deleteAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("No appointment found with ID " + appointmentId));
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> getAppointmentsByDoctor(String docId) {
        return appointmentRepository.findByDoctorId(docId);
    }

    public void setRoomId(Long app_id, String room_id) {
        Appointment appointment= appointmentRepository.findById(app_id).orElseThrow(()->new RuntimeException("appointment not found with id"+app_id));
        appointment.setRoomId(room_id);
        appointmentRepository.save(appointment);
    }

    public void setStatus(Long appt_id) {
        Appointment appointment= appointmentRepository.findById(appt_id).orElseThrow(()->new RuntimeException("appointment not found with id"+appt_id));
        appointment.setStatus(true);
        appointmentRepository.save(appointment);
    }

    public void setPaymentStatus(Long appt_Id) {
        Appointment appointment= appointmentRepository.findById(appt_Id).orElseThrow(()->new RuntimeException("appointment not found with id"+appt_Id));
        appointment.setPaymentStatus(true);
        appointmentRepository.save(appointment);
    }
}
