package UI.Pages;

import Logic.LogicClass;
import Logic.Pages.ImageUploadLogic;
import ParameterClasses.Post;
import UI.BaseFrame;
import UI.TemplateUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

/**
 * ImageUpload UI is the class that creates the upload page in the main app.
 * Users can upload pictures to their profiles and add a description to it
 * <p>
 *     The page consists of:
 *     <li>Image display</li>
 *     <li>image upload button</li>
 *     <li>text field for description</li>
 *     <li>post upload button</li>
 * </p>
 */
public class ImageUploadUI extends TemplateUI {
    private JButton uploadButton, saveImageButton;
    private JLabel imagePreviewLabel;
    private JTextArea captionArea;
    private JScrollPane captionScrollPane;
    private File selectedFile;
    private JLabel captionLabel;
    private final String destinationFolderPath = "img//uploaded";
    private String caption = "Enter your caption here...";

    /**
     * Default constructor for the ImageUploadUI class.
     * Initializes the UI by calling the superclass constructor.
     * @see TemplateUI
     */
    public ImageUploadUI() {
        super();
    }

    /**
     * Returns the name of this UI page for display in the header.
     * @return the name of this page
     */
    @Override
    public String getName() {
        return "Upload Image";
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

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 20, 260, 450);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        imagePreviewLabel = new JLabel("No Image Selected", SwingConstants.CENTER);
        imagePreviewLabel.setBounds(30, 40, 200, 150);
        imagePreviewLabel.setOpaque(true);
        imagePreviewLabel.setBackground(new Color(220, 220, 220));
        panel.add(imagePreviewLabel);

        uploadButton = new JButton("Upload Image");
        uploadButton.setBounds(30, 200, 200, 35);
        uploadButton.setBackground(new Color(50, 150, 250));
        uploadButton.setForeground(Color.BLACK);
        uploadButton.setFont(new Font("Arial", Font.BOLD, 14));
        uploadButton.addActionListener(this::onUploadClicked);
        panel.add(uploadButton);

        captionLabel = new JLabel("Caption:");
        captionLabel.setBounds(30, 250, 80, 25);
        panel.add(captionLabel);

        captionArea = new JTextArea(caption, 3, 20);
        captionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        captionArea.setLineWrap(true);
        captionArea.setWrapStyleWord(true);
        captionArea.setForeground(Color.GRAY);


        captionScrollPane = new JScrollPane(captionArea);
        captionScrollPane.setBounds(30, 280, 200, 60);
        panel.add(captionScrollPane);

        saveImageButton = new JButton("Post Image");
        saveImageButton.setBounds(30, 350, 200, 35);
        saveImageButton.setBackground(new Color(0, 180, 80));
        saveImageButton.setForeground(Color.BLACK);
        saveImageButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveImageButton.addActionListener(this::onSaveImageClicked);
        panel.add(saveImageButton);

        add(panel);
    }

    /**
     * Lets the user choose a file from its local files. if the file is valid the picture is being displayed and the picture path is being saved
     * @param event - users click
     */
    private void onUploadClicked(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg"));

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            changeShownImage(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Takes the image path and displays a scaled version of it for the user to preview
     * @param imagePath
     */
    private void changeShownImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imagePreviewLabel.setIcon(new ImageIcon(image));
        imagePreviewLabel.setText("");
    }

    /**
     * Saves the picture and the description of the post
     * @param event
     */
    private void onSaveImageClicked(ActionEvent event) {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please upload an image before saving.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String captionText = null;
        try {
            captionText = captionArea.getDocument().getText(0,captionArea.getDocument().getLength()).trim();
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
        if (captionText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid caption before saving.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String imagePath = saveImageFile(selectedFile);
        Post post = new Post(user.getUsername(), imagePath, captionText);
        ImageUploadLogic.saveImage(post);
        JOptionPane.showMessageDialog(this, "Image successfully saved!");
        BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.switchPanel(new InstagramProfileUI(LogicClass.getCurrUser()));
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
