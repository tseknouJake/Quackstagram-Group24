package UI;

import javax.swing.*;
import Logic.Pages.QuakstagramHomeLogic;
import ParameterClasses.User;
import java.awt.*;

/**
 * An abstract template class that serves as the foundation for all UI panels in the app.
 * This class defines the common structure and behavior that all UI screens should implement.
 * It handles basic panel setup, current user tracking, and standard UI initialization.
 */
public abstract class TemplateUI extends JPanel {
    /**
     * The current user of the application
     */
    protected User user;

    /**
     * Constructs a new TemplateUI with standard constructor.
     * Sets up the panel with BorderLayout, retrieves the current user,
     * configures visual properties, and calls the abstract initializeUI method
     * which must be implemented by subclasses.
     */
    public TemplateUI() {
        this.user = QuakstagramHomeLogic.getCurrUser();
        setLayout(new BorderLayout());
        setOpaque(true);
        setPreferredSize(new Dimension(300, 400));
        initializeUI();
    }

    /**
     * Abstract method that must be implemented by all subclasses to initialize
     * their specific UI components and layout.
     */
    protected abstract void initializeUI();

    /**
     * Returns the name of this UI panel. Used for identification and navigation purposes.
     *
     * @return A string representing the name of the UI panel
     */
    public abstract String getName();

    /**
     * Creates and returns a header panel for the UI.
     * Default implementation returns an empty JPanel. Subclasses can override
     * to provide custom header implementations.
     *
     * @return A JPanel to be used as the header for this UI
     */
    public JPanel getHeaderPanel() {
        return new JPanel();
    }

    /**
     * Displays an error dialog with the specified message.
     * Used for showing error notifications to the user.
     *
     * @param message The error message to display
     */
    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}