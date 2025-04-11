package UI;

import UI.Pages.SignInUI;
import UI.Panels.HeaderPanel;
import UI.Panels.NavBar;
import java.awt.*;
import javax.swing.*;

/**
 * The main frame for the Quackstagram application.
 * This class serves as the container for all UI components and manages
 * the layout and switching between different panels.
 */
public class BaseFrame extends JFrame {

    /**
     * The default width of the frame.
     */
    private static final int WIDTH = 300;
    /**
     * The default height of the frame.
     */
    private static final int HEIGHT = 560;

    /**
     * The header panel displayed at the top of the frame.
     */
    private HeaderPanel headerPanel;
    /**
     * The navigation bar displayed at the bottom of the frame.
     */
    private NavBar navBar;
    /**
     * The current central panel being displayed.
     */
    private TemplateUI centralPanel;

    /**
     * Constructs a new BaseFrame and initializes the UI components.
     * Sets up the frame properties and adds the initial panels.
     */
    public BaseFrame() {
        setTitle("Quackstagram");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initializeFrame();

        pack();
        setVisible(true);

        revalidate();
        repaint();

    }

    /**
     * Initializes the frame by creating and adding the initial UI components.
     * Sets up the header panel, central panel, and navigation bar.
     */
    private void initializeFrame() {
        centralPanel = new SignInUI();
        headerPanel = new HeaderPanel(centralPanel);
        navBar = new NavBar(centralPanel);

        add(headerPanel, BorderLayout.NORTH);
        add(centralPanel, BorderLayout.CENTER);
        add(navBar, BorderLayout.SOUTH);
    }

    /**
     * Switches the current central panel to a new panel and updates related components.
     * Removes the existing panels and replaces them with new ones configured for the new central panel.
     *
     * @param newPanel The new panel to display in the center of the frame
     */
    public void switchPanel(TemplateUI newPanel) {

        remove(centralPanel);
        centralPanel = newPanel;
        add(centralPanel, BorderLayout.CENTER);

        remove(headerPanel);
        headerPanel = new HeaderPanel(centralPanel);
        add(headerPanel, BorderLayout.NORTH);

        remove(navBar);
        //we get error when creating the nav bar
        navBar = new NavBar(centralPanel);
        add(navBar, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    /**
     * The entry point for the application.
     * Creates and displays the main application frame.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BaseFrame frame = new BaseFrame();
            frame.setVisible(true);
        });
    }
}
