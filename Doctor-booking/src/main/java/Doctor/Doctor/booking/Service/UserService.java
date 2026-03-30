package Doctor.Doctor.booking.Service;

import Doctor.Doctor.booking.Repository.UserRepository;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) throws Exception {

        Optional<User> doctorbox = userRepo.findById(id);
        if (doctorbox.isPresent()) {
            User user = doctorbox.get();
            return user;
        } else {
            throw new Exception("user not found with id "+id);
        }
    }

    public User updateUser(User user, Long id) throws Exception {

        User oldUser = this.getUserById(id);

        oldUser.setFullname(user.getFullname());

        return userRepo.save(oldUser);
    }


    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

}