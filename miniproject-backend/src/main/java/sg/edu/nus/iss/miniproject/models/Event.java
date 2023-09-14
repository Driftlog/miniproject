package sg.edu.nus.iss.miniproject.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    
    private String eventID;
    private Date startDate;
    private Date endDate;
    private String comments;
    private String location; 
    private Date departureTime;
}
