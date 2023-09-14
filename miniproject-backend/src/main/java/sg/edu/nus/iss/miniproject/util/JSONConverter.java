package sg.edu.nus.iss.miniproject.util;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.miniproject.models.Event;
import sg.edu.nus.iss.miniproject.models.User;

public class JSONConverter {
    

    public static JsonObject eventToJson(Event event) {

        JsonObject respObj = Json.createObjectBuilder()
                                    .add("eventID", event.getEventID())
                                    .add("startDate", event.getStartDate().toString())
                                    .add("endDate", event.getEndDate().toString())
                                    .add("comments", event.getComments())
                                    .add("location", event.getLocation())
                                    .add("departureTime", event.getDepartureTime().toString())
                                    .build();

        return respObj;
    }

    public static JsonObject userToJson(User user) {


        JsonObject respObj = Json.createObjectBuilder()
                                .add("userID", user.getUserID())
                                .add("userName", user.getUserName())
                                .add("userPassword", user.getUserPassword())
                                .add("email", user.getEmail())
                                .add("address", user.getAddress())
                                .add("phoneNumber", user.getPhoneNumber())
                                .add("image", user.getImage())
                                .add("calendarID", user.getCalendarID())
                                .build();

        return respObj;
    }


    public static Event jsonToEvent(JsonObject jsonString) {

        Event event = new Event(
            jsonString.getString("eventID"),
            DateConverter.convertDate(jsonString.getString("startDate")),
            DateConverter.convertDate(jsonString.getString("endDate")),
            jsonString.getString("comments"),
            jsonString.getString("location"),
            DateConverter.convertDate(jsonString.getString("departureTime")));
    
        return event;
    }
}
