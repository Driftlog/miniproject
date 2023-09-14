package sg.edu.nus.iss.miniproject.repos;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.miniproject.exception.AppException;
import sg.edu.nus.iss.miniproject.models.Event;
import sg.edu.nus.iss.miniproject.models.RSVP;
import sg.edu.nus.iss.miniproject.models.User;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

  
    public Optional<User> getUserByEmail(String email) {
        
        List<User> user = jdbcTemplate.query(Constants.SQL_CHECK_USER, BeanPropertyRowMapper.newInstance(User.class), email);

       if (user.isEmpty()) {
        return Optional.empty();
       }

       return Optional.of(user.get(0));
    }

    public List<Event> getEventsByUserId(String userID) {

        List<RSVP> rsvp = jdbcTemplate.query(Constants.SQL_GET_EVENTS_BY_USER_ID, BeanPropertyRowMapper.newInstance(RSVP.class));
                
        if (rsvp.isEmpty()) {
            return new LinkedList<>();
        }

        List<String> eventIDList = rsvp.stream()
                    .filter(rsvps -> rsvps.getUserID().equals(userID))
                    .map(filteredRsvp -> filteredRsvp.getEventID())
                    .collect(Collectors.toList());

        List<Event> events = new LinkedList<>();

        for (String eventID : eventIDList) {
            if (this.getEventById(eventID).isEmpty()) {
                return new LinkedList<>();
            }
            Event event = this.getEventById(eventID).get();
            events.add(event);
        }


        return events;
    }

    @Transactional(rollbackFor = AppException.class)
    public Boolean deleteEvent(String eventID) {

        List<RSVP> rsvpList = jdbcTemplate.query(Constants.SQL_GET_EVENTS_BY_USER_ID, BeanPropertyRowMapper.newInstance(RSVP.class));

        if (rsvpList.isEmpty()) {
            return false;
        }

        List<Integer> filtered = rsvpList.stream()
                .filter(rsvp -> rsvp.getEventID().equals(eventID))
                .map(rsvps -> rsvps.getRsvpID())
                .collect(Collectors.toList());
        

        int rsvpStatus = jdbcTemplate.update(Constants.SQL_DELETE_RSVP, filtered.get(0));
    


        try {
        int eventStatus = jdbcTemplate.update(Constants.SQL_DELETE_EVENT, eventID);

          if (eventStatus == 0) {
            throw new AppException("error deleting event", HttpStatus.BAD_REQUEST);
        } }
        catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return rsvpStatus > 0;
    }
 

    public Optional<Event> getEventById(String eventID) {
        List<Event> event = jdbcTemplate.query(Constants.SQL_GET_EVENT_BY_ID, BeanPropertyRowMapper.newInstance(Event.class), eventID);

        if (event.isEmpty()) {
        return Optional.empty();
       }

       return Optional.of(event.get(0));
    }
    

    public Integer createUser(User user) {
        
        return jdbcTemplate.update(Constants.SQL_CREATE_USER,
        
        user.getUserID(),
        user.getUserName(),
        user.getUserPassword(),
        user.getEmail(),
        user.getAddress(),
        user.getPhoneNumber(),
        user.getImage(),
        user.getCalendarID()
        );
    }

    public Integer createEvent(Event event) {

        return jdbcTemplate.update(Constants.SQL_CREATE_EVENT, 
        event.getEventID(),
        event.getStartDate(),
        event.getEndDate(),
        event.getComments(),
        event.getLocation(),
        event.getDepartureTime()
        );

    }

    public Integer createRSVP( String userID, String eventID) {

        return jdbcTemplate.update(Constants.SQL_CREATE_RSVP, userID, eventID);
    }

    public Integer getEventCount(String userID) {

        return this.getEventsByUserId(userID).size();
    }

    
    
}

    

