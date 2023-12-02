package src;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {

    private static final Font MAIN_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Light Gray
    private static final Color INPUT_COLOR = new Color(255, 255, 255); // White

    // Input fields
    private final JTextField userTextField;
    private final JPasswordField passField;
    private final JTextField emailTextField;

    private final JTextField addressTextField;

    public RegisterFrame() {
        setTitle("User Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("User Registration");
        titleLabel.setFont(MAIN_FONT);
        titlePanel.add(titleLabel);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(INPUT_FONT);
        emailTextField = new JTextField(20);
        emailTextField.setFont(INPUT_FONT);
        emailTextField.setBackground(INPUT_COLOR);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(INPUT_FONT);
        addressTextField = new JTextField(20);
        addressTextField.setFont(INPUT_FONT);
        addressTextField.setBackground(INPUT_COLOR);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(INPUT_FONT);
        userTextField = new JTextField(20);
        userTextField.setFont(INPUT_FONT);
        userTextField.setBackground(INPUT_COLOR);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(INPUT_FONT);
        passField = new JPasswordField(20);
        passField.setFont(INPUT_FONT);
        passField.setBackground(INPUT_COLOR);

        // Adding components to inputPanel in order of email, address, username, and
        // password
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(emailTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(addressTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(userTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(passField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // back button to log in
        JButton backButton = new JButton("Back");
        backButton.setFont(INPUT_FONT);
        backButton.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            dispose();
        });
        buttonPanel.add(backButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(INPUT_FONT);
        registerButton.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        buttonPanel.add(registerButton);

        // Action listener for the register button
        registerButton.addActionListener(e -> registerUser());

        // Add panels to frame
        add(titlePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void registerUser() {
        String username = userTextField.getText();
        String password = new String(passField.getPassword()); // In real application, hash the password
        String email = emailTextField.getText();
        String address = addressTextField.getText();

        // Input validation
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database operation
        try {

            DBMS dbms = DBMS.getDBMS();
            if (dbms.registerUser(username, password, email, address)) {

                JOptionPane.showMessageDialog(this, "Registration successful!");
                this.dispose();
                // Optionally, open the login window or main app window here
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                // Or if there's a main application window that should be shown after
                // registration, instantiate and display it here instead.
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + sqlException.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
