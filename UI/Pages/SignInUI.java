package UI.Pages;

import Logic.Pages.SignInLogic;
import UI.BaseFrame;
import UI.TemplateUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * SignInUI is the class that creates the signin page in the login section of the app.
 * Users can sign in with their username and password if they already created an account
 * <p>
 *     The page consists of:
 *     <li>Username and password fields</li>
 *     <li>Sign in button</li>
 * </p>
 */
public class SignInUI extends TemplateUI {
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Default constructor.
     * Initializes the UI by calling the superclass constructor.
     * @see TemplateUI
     */
    public SignInUI() {
        super();
    }

    /**
     * Returns the name of this UI page for display in the header.
     * @return the name of this page
     */
    @Override
    public String getName() {
        return "Sign In";
    }

    /**
     * Initializes the UI components.
     * This method is called from the TemplateUI parent class during construction.
     * It adds the main content panel to the UI and ensures proper focus and rendering.
     */
    @Override
    protected void initializeUI() {
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        // Panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 20, 260, 350);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Username Label & Field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 70, 80, 25);
        usernameField = new JTextField();
        usernameField.setBounds(30, 95, 200, 30);

        // Password Label & Field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 135, 80, 25);
        passwordField = new JPasswordField();
        passwordField.setBounds(30, 160, 200, 30);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(30, 210, 200, 35);
        loginButton.setBackground(new Color(50, 150, 250));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Button Actions
        loginButton.addActionListener(this::onSignInClicked);


        // Add components to panel
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        // Add panel to main UI
        add(panel);
    }

    /**
     * When a user sign in, the inputted details are being checked.
     * The user either enters the homepage or gets an error if something went wrong
     * @param event
     */
    protected void onSignInClicked(ActionEvent event) {
        String enteredUsername = usernameField.getText();
        String enteredPassword = new String(passwordField.getPassword());

        try {
            SignInLogic.signInUser(enteredUsername, enteredPassword);
            BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new QuakstagramHomeUI());
        } catch (Exception e){
            showError(e.getMessage());
        }
    }
}
