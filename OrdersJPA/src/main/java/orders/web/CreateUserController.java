package orders.web;

import lombok.extern.slf4j.Slf4j;
import orders.data.UserRepositoryInterface;
import orders.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.rmi.runtime.Log;

@Slf4j
@Controller
@RequestMapping("/createUser")
public class CreateUserController {
    private UserRepositoryInterface userRepo;
    private PasswordEncoder passwordEncoder;

    public CreateUserController(UserRepositoryInterface userRepo, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String createUserForm(Model model) {
        model.addAttribute(new User());
        return "/createUser";
    }

    @PostMapping
    public String processCreateUser(User user) {
        log.info("User is" + user);
        User savedUser = new User();
        savedUser.setId(user.getId());
        savedUser.setUsername(user.getUsername());
        savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        savedUser.setFullName(user.getFullName());
        userRepo.save(savedUser);
        return "redirect:/login";
    }
}
