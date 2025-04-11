package UI.Pages;

import Logic.Pages.ExploreUILogic;
import UI.Panels.GridPanel;
import UI.BaseFrame;
import UI.TemplateUI;
import ParameterClasses.User;
import javax.swing.*;
import java.awt.*;

/**
 * Explore UI is the class that creates the explore page in the main app.
 * Users can lookup other users or view posts of all users
 * <p>
 *     The page consists of:
 *     <li>A search bar at the top to find users</li>
 *     <li>A grid panel that displays content (presumably posts from users)</li>
 * </p>
 */
public class ExploreUI extends TemplateUI {

    /**
     * Default constructor.
     * Initializes the UI by calling the superclass constructor.
     * @see TemplateUI
     */
    public ExploreUI() {
        super();
    }

    /**
     * Initializes the UI components.
     * This method is called from the TemplateUI parent class during construction.
     * It adds the main content panel to the UI and ensures proper focus and rendering.
     */
    @Override
    protected void initializeUI() {

        add(createMainContentPanel());

        SwingUtilities.invokeLater(() -> requestFocusInWindow());
        // Add panels to the frame
        revalidate();
        repaint();
    }

    /**
     * Returns the name of this UI page for display in the header.
     * @return the name of this page
     */
    @Override
    public String getName(){
        return "explore";
    }

    /**
     * Creates and returns the main content panel for the explore page.
     * This panel contains:
     * <ul>
     *   <li>A search panel with a text field for searching users</li>
     *   <li>A grid panel that displays content</li>
     * </ul>
     *
     * <p>The search functionality allows users to find other profiles by username.
     * When a search is submitted, it calls the ExploreUILogic to perform the search
     * and navigates to the corresponding profile if found.
     *
     * @return JPanel containing the main content components
     * @see GridPanel
     * @see ExploreUILogic#performSearch(String)
     * @see InstagramProfileUI
     */
    private JPanel createMainContentPanel() {
        // Create the main content panel with search and image grid
        // Search bar at the top
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField(" Search Users");
        searchField.setForeground(Color.GRAY); // Placeholder text color

        searchField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals(" Search Users")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK); // Normal text color
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText(" Search Users");
                    searchField.setForeground(Color.GRAY); // Reset placeholder color
                }
            }
        });

        searchField.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                try {
                    user = ExploreUILogic.performSearch(searchText);
                    BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
                    parentFrame.switchPanel(new InstagramProfileUI(user));
                } catch (Exception exception){
                    showError(exception.getMessage());
                }
            }
        });
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchField.getPreferredSize().height)); // Limit the height

        // Post Grid
        User nullUser = null;
        GridPanel postGridPanel = new GridPanel(nullUser);


        // Main content panel that holds both the search bar and the image grid
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.add(searchPanel);
        mainContentPanel.add(postGridPanel);
        return mainContentPanel;
    }
}