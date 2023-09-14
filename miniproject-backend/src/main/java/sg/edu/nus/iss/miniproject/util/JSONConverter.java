package sg.edu.nus.iss.miniproject.util;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.miniproject.models.Event;

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
