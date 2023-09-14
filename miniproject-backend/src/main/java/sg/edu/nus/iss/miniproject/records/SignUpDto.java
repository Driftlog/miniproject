package sg.edu.nus.iss.miniproject.records;

public record SignUpDto(String userID, String userName, char[] userPassword,
                        String email, String address, int phoneNumber, String image,
                        String calendarID) {

}
