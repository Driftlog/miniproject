package sg.edu.nus.iss.miniproject.records;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Login{

    @NotBlank
    private String userID; 

    @NotBlank
    private String email;

    private String token;
}