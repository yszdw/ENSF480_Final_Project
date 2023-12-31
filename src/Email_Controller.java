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

            message.setText(String.format("Hello,\n\nThis is an email receipt for your flight to %s\n\n"
                            + "Username: %s\n"
                            + "Insurance: %s\n"
                            + "Cost: %s\n",
                    location, username, insurance, price));

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static void sendTicket(String username, String email, String aircraft, String from, String to,
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

            message.setText(String.format("Hello,\n\nThis is your email ticket for your flight to %s\n\n"
                            + "Username: %s\n"
                            + "Flying from: %s\n"
                            + "at: %s\n"
                            + "Flying to: %s\n"
                            + "Arriving at: %s\n"
                            + "Aircraft: %s\n"
                            + "Seat Class: %s\n"
                            + "Seat: %s\n",
                    to, username, from, departure, to, arrive, aircraft, seatClass, seat));

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

            message.setText(String.format("Hello,\n\nYour ticket for your flight to %s has been cancelled\n\n"
                            + "Seat Class: %s\n"
                            + "Seat: %s\n",
                    o.getArrivalLocation(), o.getSeatClass(), o.getSeatNumber()));

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}