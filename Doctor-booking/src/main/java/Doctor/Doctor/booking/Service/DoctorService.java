package Doctor.Doctor.booking.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hms.hospital_management_service.Model.Doctor;
import com.hms.hospital_management_service.Repository.DoctorRepo;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;

    public Doctor createDoctor(Doctor doctor){
        return doctorRepo.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor getDoctorById(String docId) throws Exception{
        Optional<Doctor> doctorbox=doctorRepo.findById(docId);
        if(doctorbox.isPresent()){
            Doctor doctor=doctorbox.get();
            return doctor;
        }else{
            throw new Exception("Doctor not found with id "+docId);
        }
    }

    public Doctor updateDoctor(Doctor doctor, String id) throws Exception {

        Doctor oldDoctor = this.getDoctorById(id);

        oldDoctor.setName(doctor.getName());
        oldDoctor.setSpeciality(doctor.getSpeciality());
        oldDoctor.setDegree(doctor.getDegree());
        oldDoctor.setExperience(doctor.getExperience());
        oldDoctor.setAbout(doctor.getAbout());
        oldDoctor.setFees(doctor.getFees());
        oldDoctor.setAddress(doctor.getAddress());
        oldDoctor.setPhone(doctor.getPhone());
        oldDoctor.setImageurl(doctor.getImageurl());

        return doctorRepo.save(oldDoctor);
    }

    public void deleteDoctor(String docId){
        doctorRepo.deleteById(docId);
    }

}