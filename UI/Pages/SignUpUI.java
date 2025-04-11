package UI.Pages;

import UI.TemplateUI;
import UI.BaseFrame;
import javax.swing.*;
import ParameterClasses.User;
import Logic.Pages.SignUpLogic;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * SignUpUI is the class that creates the signup page in the login section of the app.
 * Users can sign up with unique username and password they choose, upload a profile picture and add a bio
 * <p>
 *     The page consists of:
 *     <li>Username, password and bio fields</li>
 *     <li>upload picture pane</li>
 *     <li>signup button</li>
 * </p>
 */
public class SignUpUI extends TemplateUI {
    private JLabel imageLabel;
    private File selectedFile;
    private String profilePicPath;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea bioArea;
    private final String destinationFolderPath = "img//uploaded";

    /**
     * Default constructor.
     * Initializes the UI by calling the superclass constructor.
     * @see TemplateUI
     */
    public SignUpUI() {
        super();
        profilePicPath = null;
    }

    /**
     * Returns the name of this UI page for display in the header.
     * @return the name of this page
     */
    @Override
    public String getName(){
        return "Sign Up";
    }

    /**
     * Initializes the UI components.
     * This method is called from the TemplateUI parent class during construction.
     * It adds the main content panel to the UI and ensures proper focus and rendering.
     */
    @Override
    protected void initializeUI() {
        setLayout(null); // Absolute positioning
        setBackground(new Color(240, 240, 240)); // Background color

        // Panel that holds all components
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 20, 260, 410); // Adjusted height to fit 450px frame
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Username Field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 50, 200, 20);
        usernameField = new JTextField();
        usernameField.setBounds(30, 70, 200, 30);

        // Password Field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 110, 200, 20);
        passwordField = new JPasswordField();
        passwordField.setBounds(30, 130, 200, 30);

        // Bio Field
        JLabel bioLabel = new JLabel("Bio:");
        bioLabel.setBounds(30, 170, 200, 20);
        bioArea = new JTextArea();
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        JScrollPane bioScrollPane = new JScrollPane(bioArea);
        bioScrollPane.setBounds(30, 190, 200, 50);

        //TODO - ADD A DEFAULT PIC
        // Upload Button
        JButton uploadButton = new JButton("Upload Profile Picture");
        uploadButton.setBounds(30, 250, 200, 30);
        uploadButton.setBackground(new Color(50, 150, 250));
        uploadButton.setForeground(Color.BLACK);
        uploadButton.setFocusPainted(false);

        // Image Label
        imageLabel = new JLabel("<html><i>No Image Selected</i></html>", SwingConstants.CENTER);
        imageLabel.setBounds(30, 285, 200, 20);
        imageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        imageLabel.setForeground(Color.DARK_GRAY);

        // Sign-Up Button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(30, 310, 200, 35);
        signUpButton.setBackground(new Color(50, 200, 100));
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setFocusPainted(false);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));


        // Upload Image Action
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                imageLabel.setText("<html><i>Selected: " + selectedFile.getName() + "</i></html>");
                profilePicPath = saveImageFile(selectedFile);
            }
        });

        // Sign-Up Button Action
        signUpButton.addActionListener(this::onSignInClicked);


        // Add components to panel
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(bioLabel);
        panel.add(bioScrollPane);
        panel.add(uploadButton);
        panel.add(imageLabel);
        panel.add(signUpButton);

        // Add panel to UI
        add(panel);
    }

    /**
     * Saves the image file to the destination folder.
     *
     * @param selectedFile The source image file to be saved
     * @return The complete file path of the saved image if successful, null otherwise
     * @throws NullPointerException implicitly if the destinationFolderPath is not initialized
     * @see #copyFile(File, File)
     * @see #showError(String)
     */
    private String saveImageFile(File selectedFile) {
        if (selectedFile == null) {
            showError("No file selected!");
            return null;
        }

        File destinationFile = new File(destinationFolderPath, selectedFile.getName());

        if (copyFile(selectedFile, destinationFile)) {
            return destinationFolderPath + "/" + selectedFile.getName();
        } else {
            showError("Error saving the image!");
            return null;
        }
    }

    /**
     * The user inputs are being checked then saved.
     * If all goes well the new user is created and being transported to the homepage.
     * Otherwise, the user get a corresponding error
     * @param event - users click on the button
     */
    protected void onSignInClicked(ActionEvent event) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String bio = bioArea.getText();

        User newUser = new User(username, bio, password, profilePicPath);

        try {
            SignUpLogic.signUpUser(newUser);
            JOptionPane.showMessageDialog(this, "New User created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new QuakstagramHomeUI());
        } catch (Exception e){
            showError(e.getMessage());
        }

    }

    /**
     * Copy the users file into the destination folder
     * @param source - the folder we are copying to
     * @param destination - the path of the file we are copying
     * @return true if the copy was successful, false if it wasn't
     */
    private boolean copyFile(File source, File destination) {
        try {
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
