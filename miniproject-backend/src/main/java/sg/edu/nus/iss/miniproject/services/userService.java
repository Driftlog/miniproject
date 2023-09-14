package sg.edu.nus.iss.miniproject.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.miniproject.models.Event;
import sg.edu.nus.iss.miniproject.repos.UserRepository;

@Service
public class userService {

    @Autowired
    private UserRepository userRepo;

    @Value("${sendgrid.api.key}")
    private String apiKey;

    
    public List<Event> getEvents(String userID) {

        return this.userRepo.getEventsByUserId(userID);
    }

    public Optional<Event> getEvent(String eventID) {

        return this.userRepo.getEventById(eventID);
    }


    public boolean createEvent(Event event, String userID) {

        if (this.userRepo.createEvent(event) > 0) {
            return this.userRepo.createRSVP(userID, event.getEventID()) > 0;
        }

        return false;
    }

    //my event object is for filling up the emails
    public String sendEmail(String email, Event event){

        try {

        //put api key here
        //my api key is in @value
        SendGrid sg = new SendGrid(apiKey);
        //construct post request to /mail/send
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("/mail/send");


        //set email content, you can use html here
        Content content = new Content();
        content.setType("text/html");
        content.setValue("This is a reminder for your event");

        //put your own email, must be verified on the website, name is optional
        Email sender = new Email("lrxjuly@gmail.com", "Mark");
        //put receipient email here
        Email receipient = new Email(email);

        //construct the mail
        Mail mail = new Mail(sender, "Reminder", receipient, content);


        //attach mail to request
        request.setBody(mail.build());
        //getting json resp from api
        Response response = sg.api(request);
        //api returns only 202 or error code 400+ & 500s for errors
        if (response.getStatusCode() != 202) {
            try (InputStream is = new ByteArrayInputStream(response.getBody().getBytes())) {
                JsonReader reader = Json.createReader(is);
                JsonObject data = reader.readObject();
                // JsonArray jsonArr = data.getJsonArray("errors").toString();
                //return errorMessage in to fill respEntity if it fails
                String errorMessage = data.toString();
                return errorMessage;
            } catch(Exception ex) {
                ex.printStackTrace();
                System.out.println("failed");
                return ex.getMessage();
        }}
    }
    
     catch (Exception ex) {
        ex.printStackTrace();
       System.out.println("failed"); 
        return ex.getMessage();
    }
        
        return "";

  
    }

    public Boolean deleteEvent(String userID) {
        return this.userRepo.deleteEvent(userID);
    }

    public Integer getEventCount(String userID) {
        return this.userRepo.getEventCount(userID);
    }


}
