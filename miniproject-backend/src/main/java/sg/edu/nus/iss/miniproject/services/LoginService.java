package sg.edu.nus.iss.miniproject.services;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import sg.edu.nus.iss.miniproject.exception.AppException;
import sg.edu.nus.iss.miniproject.models.User;
import sg.edu.nus.iss.miniproject.records.LoginDto;
import sg.edu.nus.iss.miniproject.records.SignUpDto;
import sg.edu.nus.iss.miniproject.records.Login;
import sg.edu.nus.iss.miniproject.repos.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Login login(LoginDto loginDto) {
        User user = userRepository.getUserByEmail(loginDto.email())
                                    .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        
        System.out.println(loginDto.password());
        System.out.println(user.getUserPassword());

        if (passwordEncoder.matches(CharBuffer.wrap(loginDto.password()),
            user.getUserPassword())) {
                return new Login(user.getUserID(), user.getEmail(), "");
        } 
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public User register(SignUpDto signUpDto) {
        Optional<User> optionalUser = userRepository.getUserByEmail(signUpDto.email());

        // You were implement the above repo's method
        if (optionalUser.isPresent()) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        System.out.println("Here lies the value" + signUpDto);

        User user = new User(signUpDto.userID(), signUpDto.userName(), signUpDto.userPassword().toString(), signUpDto.email(), signUpDto.address(), signUpDto.phoneNumber(), signUpDto.image(), signUpDto.calendarID());
        user.setUserPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.userPassword())));

        if (userRepository.createUser(user) > 0) {
            System.out.println(user);
            return user;
        }
        else {
            throw new AppException("User " + user.getEmail() + " was not successfully created", HttpStatus.BAD_REQUEST);
        }
    }
    
    
}