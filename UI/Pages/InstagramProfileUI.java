package UI.Pages;

import UI.Panels.GridPanel;
import UI.BaseFrame;
import UI.TemplateUI;
import ParameterClasses.User;

import javax.swing.*;

import Logic.Pages.InstagramProfileLogic;

import java.awt.*;

/**
 * InstagramProfileUI is the class that creates the profile page in the main app.
 * Users can view their own profile or other users profile and interact with it
 * <p>
 * The page consists of:
 * <li>A specialized header that displays the users stats</li>
 * <li>A grid panel that displays the users posts</li>
 * <li>LogOut button if it is the current users profile or a follow button if it is some other users profile</li>
 * </p>
 */
public class InstagramProfileUI extends TemplateUI {

    private static final int PROFILE_IMAGE_SIZE = 80; // Adjusted size for the profile image to match UI
    private JPanel contentPanel; // Panel to display the image grid or the clicked image
    private JPanel headerPanel;   // Panel for the header
    private User profileUser;

    /**
     * Default constructor.
     * Initializes the UI by calling the superclass constructor.
     * Saves the user whos profile is being displayed and creates corresponding header.
     *
     * @see TemplateUI
     */
    public InstagramProfileUI(User profileUser) {
        this.profileUser = profileUser;
        super();
        contentPanel = new JPanel();
        headerPanel = createHeaderPanel();       // Initialize header panel
    }


    /**
     * Returns the name of this UI page for display in the header.
     *
     * @return the name of this page
     */
    @Override
    public String getName() {
        return "null";
    }

    /**
     * Initializes the UI components.
     * This method is called from the TemplateUI parent class during construction.
     * It adds the main content panel to the UI and ensures proper focus and rendering.
     */
    @Override
    public void initializeUI() {
        // Initialize the image grid
        GridPanel gridPanel = new GridPanel(profileUser);
        add(gridPanel);
        revalidate();
        repaint();
    }

    /**
     * Creates the final header panel that is being displayed on the page
     * @return the header panel with the stats bar and corresponding buttons
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.GRAY);

        // Add components to the header panel
        headerPanel.add(createTopHeaderPanel());
        headerPanel.add(createProfileInfoPanel());

        return headerPanel;
    }

    /**
     * Creates the top section of the header containing profile image, stats, and action buttons
     */
    private JPanel createTopHeaderPanel() {
        JPanel topHeaderPanel = new JPanel(new BorderLayout(10, 0));
        topHeaderPanel.setBackground(new Color(249, 249, 249));

        // Add profile image to the left side
        topHeaderPanel.add(createProfileImageComponent(), BorderLayout.WEST);

        // Add stats and buttons to the center
        topHeaderPanel.add(createStatsAndButtonsPanel(), BorderLayout.CENTER);

        return topHeaderPanel;
    }

    /**
     * Creates the profile image component
     */
    private JLabel createProfileImageComponent() {
        ImageIcon profileIcon = new ImageIcon(
                new ImageIcon(profileUser.getProfilePicPath()).getImage()
                        .getScaledInstance(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, Image.SCALE_SMOOTH)
        );
        JLabel profileImage = new JLabel(profileIcon);
        profileImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return profileImage;
    }

    /**
     * Creates the panel containing stats and action buttons
     */
    private JPanel createStatsAndButtonsPanel() {
        JPanel statsFollowPanel = new JPanel();
        statsFollowPanel.setLayout(new BoxLayout(statsFollowPanel, BoxLayout.Y_AXIS));

        // Add stats display
        statsFollowPanel.add(createStatsPanel());

        // Add appropriate action buttons based on user state
        if (InstagramProfileLogic.isLoggedInUser(profileUser)) {
            statsFollowPanel.add(createLogoutButton());
        } else {
            statsFollowPanel.add(createFollowButton());
        }

        return statsFollowPanel;
    }

    /**
     * Creates the stats panel displaying posts, followers, and following counts
     */
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        statsPanel.setBackground(new Color(249, 249, 249));

        statsPanel.add(createStatLabel(Integer.toString(profileUser.getPostsCount()), "Posts"));
        statsPanel.add(createStatLabel(Integer.toString(profileUser.getFollowersCount()), "Followers"));
        statsPanel.add(createStatLabel(Integer.toString(profileUser.getFollowingCount()), "Following"));

        statsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        return statsPanel;
    }

    /**
     * Creates a follow/following button with appropriate styling and behavior
     */
    private JButton createFollowButton() {
        JButton followButton = new JButton(InstagramProfileLogic.isCurrUsersFollowing(profileUser) ?
                "Following" : "Follow");

        styleActionButton(followButton, new Color(225, 228, 232), Color.BLACK);

        followButton.addActionListener(e -> {
            if (!InstagramProfileLogic.isCurrUsersFollowing(profileUser)) {
                InstagramProfileLogic.addFollower(profileUser);
                followButton.setText("Following");
            } else {
                InstagramProfileLogic.removeFollower(profileUser);
                followButton.setText("Follow");
            }
            refreshPanel();
        });

        return followButton;
    }

    /**
     * Creates a logout button with appropriate styling and behavior
     */
    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("Logout");

        styleActionButton(logoutButton, new Color(225, 80, 80), Color.WHITE);

        logoutButton.addActionListener(e -> {
            BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new SignInUI());
        });

        return logoutButton;
    }

    /**
     * Applies common styling to action buttons
     */
    private void styleActionButton(JButton button, Color background, Color foreground) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    }

    /**
     * Creates the panel containing username and bio
     */
    private JPanel createProfileInfoPanel() {
        JPanel profileNameAndBioPanel = new JPanel();
        profileNameAndBioPanel.setLayout(new BorderLayout());
        profileNameAndBioPanel.setBackground(new Color(249, 249, 249));

        // Username label
        JLabel profileNameLabel = new JLabel(profileUser.getUsername());
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profileNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        profileNameAndBioPanel.add(profileNameLabel, BorderLayout.NORTH);

        // Bio text area
        JTextArea profileBio = new JTextArea(profileUser.getBio());
        profileBio.setEditable(false);
        profileBio.setFont(new Font("Arial", Font.PLAIN, 12));
        profileBio.setBackground(new Color(249, 249, 249));
        profileBio.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        profileNameAndBioPanel.add(profileBio, BorderLayout.CENTER);

        return profileNameAndBioPanel;
    }

    /**
     * refreshes the page whenever a triggering action occurs
     */
    private void refreshPanel() {
        BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.switchPanel(new InstagramProfileUI(profileUser));
    }

    /**
     * Creates formatted display for followers, following and posts
     *
     * @param number - the number to be displayed
     */
    private JLabel createStatLabel(String number, String text) {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + number + "<br/>" + text + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        return label;
    }

    /**
     * @return the final version of a customized panel
     */
    @Override
    public JPanel getHeaderPanel() {
        return createHeaderPanel();
    }

}
