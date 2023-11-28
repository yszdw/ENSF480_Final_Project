package src;

import src.LoginFrame.WelcomeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class AirlineFrame extends JFrame {

    private static final Font MAIN_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font GROUP_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Color BUTTON_COLOR = new Color(100, 149, 237); // Cornflower Blue
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    private static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Light Gray
    private static final Color INPUT_COLOR = new Color(255, 255, 255); // White

    public AirlineFrame(String username) {
        setTitle("Airline Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel welcomeLabel = new JLabel("Welcome to our Flight Reservation System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;

        JButton flightButton = createStyledButton("Flight Management System");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(flightButton, gbc);

        flightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame(); // Create an instance of LoginFrame
                WelcomeFrame welcomeFrame = loginFrame.new WelcomeFrame(username); // Pass the username to WelcomeFrame
                welcomeFrame.setVisible(true); // Show the welcome window
            }
        });

        JButton displayPassButton = createStyledButton("Display Passenger List");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(displayPassButton, gbc);

        displayPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PassListFrame passListFrame = new PassListFrame(username);
                passListFrame.setVisible(true);
            }
        });

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(MAIN_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminFrame adminFrame = new AdminFrame();
            adminFrame.setVisible(true);
        });
    }
}
