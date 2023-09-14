package sg.edu.nus.iss.miniproject.repos;

public class Constants {
    
    public final static String SQL_LOGIN_USER = "select username, password from users where email = ?";
    public final static String SQL_CHECK_USER = "select * from users where email = ?";
    public final static String SQL_CREATE_USER = "insert into users(userID, userName, userPassword, email, address, phoneNumber, image, calendarID) values (?, ?, ?, ?, ?, ?, ?, ?)";
    public final static String SQL_UPDATE_USER = "update users (userName, userPassword, email, address, phoneNumber, image) values (?, ?, ?, ?, ?, ?, ?, ?) where userID = ?";
    public final static String SQL_GET_EVENT_BY_ID = "select * from events where eventID = ?";
    public final static String SQL_GET_EVENTS_BY_USER_ID = "SELECT * FROM rsvp";
    public final static String SQL_CREATE_RSVP = "insert into rsvp(userID, eventID) values(?, ?)";
    public final static String SQL_CREATE_EVENT = "insert into events(eventID, startDate, endDate, comments, location, departureTime) values(?, ?, ?, ?, ?, ?)";
    public final static String SQL_DELETE_EVENT = "DELETE FROM events where eventID = ?";
    public final static String SQL_UPDATE_EVENT = "update events(startDate, endDate, comments, location, departureTime) values (?,?,?,?,?)";
    public final static String SQL_DELETE_RSVP_BY_EVENT_ID = "DELETE FROM rsvp where eventID = ?";
    public final static String SQL_GET_RSVP_BY_EVENT_ID = "select rsvpID from rsvp where eventID = ?";
    public final static String SQL_DELETE_RSVP = "delete from rsvp where rsvpID = ?";
    public final static String SQL_GET_USER = "select * from users where userID = ?";



}
