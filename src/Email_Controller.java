package src;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;


public class Email_Controller {

    private ArrayList<User> promoUsers;

    private static String programEmail = "ensf480flightsystem@gmail.com";
    private static String emailPassword = "ggcw dwhn hjvd cdpr";

    public Email_Controller(){
        promoUsers = new ArrayList<User>();
    }


    public void registerForPromotion(User user){
        this.promoUsers.add(user);

    }
    public void notifyAboutPromotions(String promotion){

        for(int i = 0; i < promoUsers.size(); i++){
            //Get next user
            User currentUser = promoUsers.get(i);

            //String userEmail = currentUser.getEmail();
            String userEmail = "aaron.dalbroi2002@gmail.com";

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            // Create a session with the email server
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(programEmail, emailPassword);
                }
            });

            try {
                // Create a MimeMessage object
                Message message = new MimeMessage(session);

                // Set the sender's email address
                message.setFrom(new InternetAddress(userEmail));

                // Set the recipient's email address
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));

                // Set the email subject and body
                message.setSubject(String.format("Promotion for %s",currentUser.getUsername()));

                message.setText(promotion);

                // Send the email
                Transport.send(message);

                System.out.println("Email sent successfully!");

            } catch (MessagingException e) {
                e.printStackTrace();
            }



        }

    }

    public static void sendReceipt(User user, Flight flight){

        String userEmail = user.getEmail();

        //For testing, place your own email in the line below
        //String userEmail = "aaron.dalbroi2002@gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Create a session with the email server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(programEmail, emailPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender's email address
            message.setFrom(new InternetAddress(userEmail));

            // Set the recipient's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));

            // Set the email subject and body
            message.setSubject(String.format("Receipt for your flight to %s!",flight.getArrivalLocation()));






            message.setText(String.format("Hello,\n\nThis is an email receipt for your flight on %s at %s\n\n"
                           +"User name: %s\n"
                           +"Departure: %s\n"
                           +"Arrival: %s\n"
                           +"Cost: %s\n",
                           flight.getDepartureDate(),flight.getDepartureTime(),user.getUsername(),flight.getDepartureLocation(),flight.getArrivalLocation(),"100"));

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }



    }

    public static void main(String args[]) throws SQLException {

        DBMS db = DBMS.getDBMS();

        User user = db.getUsers().get(0);
        Flight flight = db.getFlights().get(0);
        Email_Controller emailController = new Email_Controller();

        emailController.registerForPromotion(user);
        emailController.notifyAboutPromotions(db.getCurrentPromotion());



    }

}
