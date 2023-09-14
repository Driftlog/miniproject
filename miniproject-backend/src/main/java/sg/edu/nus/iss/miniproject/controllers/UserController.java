package sg.edu.nus.iss.miniproject.controllers;

 
// import java.time.Instant;
// import java.util.Date;
// import java.util.Optional;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.miniproject.models.Email;
import sg.edu.nus.iss.miniproject.models.Event;
import sg.edu.nus.iss.miniproject.models.User;
import sg.edu.nus.iss.miniproject.services.userService;
import sg.edu.nus.iss.miniproject.util.JSONConverter;

@RestController
@RequestMapping(path = "/user")
public class UserController {


    @Autowired
    private userService userSvc;

    //implement getUser
    @GetMapping(path = "/getUser")
    public ResponseEntity<String> getUser(@RequestParam String userID) {

        Optional<User> user = this.userSvc.getUser(userID);

        if (user.isPresent() ) {
            return ResponseEntity.ok(JSONConverter.userToJson(user.get()).toString());
        }

    
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ok");


    }


    @GetMapping(path = "/getEvents")
    public ResponseEntity<String> getEvents(@RequestParam String userID) {

        List<Event> events = this.userSvc.getEvents(userID);
        
        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        events.stream()
                .map((event) -> JSONConverter.eventToJson(event))
                .forEach(event -> jsonArrayBuilder.add(event));
         

        
        JsonArray respArr = jsonArrayBuilder.build();

        return ResponseEntity.ok(respArr.toString());
    }

    @PostMapping(path = "/postEvent/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postEvent(@RequestBody Event event, @PathVariable String userID) {

        Boolean status = this.userSvc.createEvent(event, userID);

        if (status) {
            return ResponseEntity.ok(event.getEventID());
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to insert event");

    }

    @GetMapping(path = "/getEvent/{eventID}")
    public ResponseEntity<String> getEvent(@PathVariable String eventID) {

        Event event = this.userSvc.getEvent(eventID).get();

        if (this.userSvc.getEvent(eventID).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("event not found");
        }
        
        JsonObject respObj = JSONConverter.eventToJson(event);

        return ResponseEntity.ok(respObj.toString());
    }

    @PostMapping(path="/sendMail", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendEmail(@RequestBody Email emailObj) {
        String email = emailObj.getEmail();
        Event event = emailObj.getEvent();

            String resp = this.userSvc.sendEmail(email, event);
            if (resp.length() == 0) {
                return ResponseEntity.ok("ok");
            }

            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(resp);
            
        }

    @DeleteMapping(path="/deleteEvent")
    public ResponseEntity<String> deleteEvent(@RequestParam String eventID) {

        Boolean status = this.userSvc.deleteEvent(eventID);
        if (status) {
            return ResponseEntity.ok("Delete success");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsuccessful");

    }

    @GetMapping(path="/countEvent")
    public ResponseEntity<String> getEventCount(@RequestParam String userID) {

        return ResponseEntity.ok(this.userSvc.getEventCount(userID).toString());
    }
    

    }

