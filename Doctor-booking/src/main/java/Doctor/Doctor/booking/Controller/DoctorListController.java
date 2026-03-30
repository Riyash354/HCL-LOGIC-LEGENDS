package Doctor.Doctor.booking.Controller;


import java.util.List;
import Doctor.Doctor.booking.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/doctors")
public class DoctorsListController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getalldoctors")
    public ResponseEntity<List<Doctor>> getAllDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

}
