package sg.edu.nus.iss.miniproject.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @NotBlank
    private String userID;

    @NotBlank
    private String userName;

    @NotBlank
    private String userPassword;

    @Email
    private String email;

    @NotBlank
    private String address;

    @Min(value=8, message="Please enter a valid phone number")
    private int phoneNumber;

    @NotBlank
    private String image;

    private String calendarID;
    // private String roles;
    
   
}
