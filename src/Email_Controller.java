package src;

import javax.mail.*;
import javax.mail.internet.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Properties;

public class Email_Controller {

    private static ArrayList<User> promoUsers = new ArrayList<>();

    private static final String programEmail = "ensf480flightsystem@gmail.com";
    private static final String emailPassword = "ggcw dwhn hjvd cdpr";

    public Email_Controller() {}

    public void registerForPromotion(User user) {
        promoUsers.add(user);
    }

    public void notifyAboutPromotions(String promotion) {
        // Only send promo if it's the first of the month
        for (User currentUser : promoUsers) {

            // Get next user
            String userEmail = currentUser.getEmail();

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
                message.setSubject(String.format("Promotion for %s", currentUser.getUsername()));

                message.setText(promotion);

                // Send the email
                Transport.send(message);

                System.out.println("Email sent successfully!");

            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }

    }

    public static void sendReceipt(String username, String email, String insurance, String location, double price) {

        // For testing, place your own email in the line below
        // String userEmail = "aaron.dalbroi2002@gmail.com";

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
            message.setFrom(new InternetAddress(email));

            // Set the recipient's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Set the email subject and body
            message.setSubject(String.format("Receipt for your flight to %s", location));

            message.setText(String.format("""
                            Hello,

                            This is an email receipt for your flight to %s

                            Username: %s
                            Insurance: %s
                            Cost: %s
                            """,
                    location, username, insurance, price));

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static void sendTicket(String username, String email, String aircraft, String to, String from,
            LocalTime departure, LocalTime arrive, String seatClass, String seat) {

        // For testing, place your own email in the line below
        // String userEmail = "aaron.dalbroi2002@gmail.com";

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
            message.setFrom(new InternetAddress(email));

            // Set the recipient's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Set the email subject and body
            message.setSubject(String.format("Ticket for your flight to %s", to));

            message.setText(String.format("""
                            Hello,

                            This is your email ticket for your flight to %s

                            Username: %s
                            Flying from: %s
                            at: %s
                            Flying to: %s
                            Arriving at: %s
                            Aircraft: %s
                            Seat Class: %s
                            Seat: %s
                            """,
                    arrive, username, from, departure, to, arrive, aircraft, seatClass, seat));

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static void sendCancellationEmail(int orderID, String email) {
        // For testing, place your own email in the line below
        // String userEmail = "aaron.dalbroi2002@gmail.com";

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
            Order o = DBMS.getDBMS().getOrder(orderID);
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender's email address
            message.setFrom(new InternetAddress(email));

            // Set the recipient's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Set the email subject and body
            message.setSubject("Ticket Cancellation");

            message.setText(String.format("""
                            Hello,

                            Your ticket for your flight to %s has been cancelled

                            Seat Class: %s
                            Seat: %s
                            """,
                    o.getArrivalLocation(), o.getSeatClass(), o.getSeatNumber()));

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
