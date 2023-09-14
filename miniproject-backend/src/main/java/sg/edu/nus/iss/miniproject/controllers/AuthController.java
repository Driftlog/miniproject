package sg.edu.nus.iss.miniproject.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.miniproject.configs.UserAuthProvider;
import sg.edu.nus.iss.miniproject.models.User;
import sg.edu.nus.iss.miniproject.records.LoginDto;
import sg.edu.nus.iss.miniproject.records.SignUpDto;
import sg.edu.nus.iss.miniproject.records.Login;
import sg.edu.nus.iss.miniproject.services.ImageService;
import sg.edu.nus.iss.miniproject.services.LoginService;


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private LoginService loginSvc;

    @Autowired
    private UserAuthProvider userAuthProvider;

    @Autowired
    private ImageService imageSvc;
    
    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody LoginDto loginDto) {

        Login user = new Login();

    try {
        user = loginSvc.login(loginDto);}
    catch(Exception ex) {
        Login error = new Login();
        error.setUserID((ex.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    // @PostMapping("/register")
    // public ResponseEntity<Login> register(@RequestBody SignUpDto signUpDto) {
    //     //implement display picture here
        
    //     User user = loginSvc.register(signUpDto);
        
    //     Login createdUser = new Login();
    //     createdUser.setToken(userAuthProvider.createToken(new Login(user.getUserID(), user.getEmail(), "")));
    //     createdUser.setEmail(user.getEmail());
    //     createdUser.setUserID(user.getUserID());
    //     return ResponseEntity.created(URI.create("/users/" + createdUser.getEmail())).body(createdUser);
    // }

    @PostMapping(path="/register", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Login> register(@RequestPart MultipartFile file, @RequestPart String user) {

        if (file == null) {
            throw new Error("image not found", new FileNotFoundException());
        }
        
        String imageID = imageSvc.getUrl(imageSvc.uploadFile(file));
        User registeredUser = new User();

        try (InputStream is = new ByteArrayInputStream(user.getBytes())) {

            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
            SignUpDto signUpDto = new SignUpDto(data.getString("userID"),
                                                data.getString("userName"),
                                                data.getString("userPassword").toCharArray(),
                                                data.getString("email"),
                                                data.getString("address"),
                                                data.getInt("phoneNumber"),
                                                imageID,
                                                data.getString("calendarID"));
            
            registeredUser = loginSvc.register(signUpDto);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Login createdUser = new Login();
        createdUser.setToken(userAuthProvider.createToken(new Login(registeredUser.getUserID(), registeredUser.getEmail(), "")));
        createdUser.setEmail(registeredUser.getEmail());
        createdUser.setUserID(registeredUser.getUserID());
        return ResponseEntity.created(URI.create("/users/" + createdUser.getEmail())).body(createdUser);
    }

}