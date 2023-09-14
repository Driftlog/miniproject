package sg.edu.nus.iss.miniproject.util;

import java.text.SimpleDateFormat;
import java.util.Date;
// import java.util.TimeZone;

public class DateConverter {
    
    public static final Date convertDate(String date){
        Date javaDate = new Date();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        // formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
       try {javaDate = formatter.parse(date);}
        catch(Exception ex) {
            ex.printStackTrace();;
        }

        return javaDate;
    }
}
