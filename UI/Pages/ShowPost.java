package UI.Pages;

import Logic.Pages.QuakstagramHomeLogic;
import UI.Panels.RegularDisplayedPost;
import ParameterClasses.Post;
import ParameterClasses.User;
import UI.BaseFrame;
import UI.TemplateUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import Logic.LogicClass;
import Logic.Pages.ShowPostLogic;

/**
 * ShowPost UI is the class that creates the page where you can view a single post with its details.
 * Users can see the image, username, timestamp, caption, and interact with likes.
 * <p>
 *     The page consists of:
 *     <li>A navigation button to return to home</li>
 *     <li>The post image</li>
 *     <li>Post details including username, timestamp, caption, and likes</li>
 * </p>
 */
public class ShowPost extends TemplateUI {
    private static final Color LIKE_BUTTON_COLOR = new Color(255, 90, 95);
    private final Post post;
    private static final int PANEL_WIDTH = 800;
    private static final int BUTTON_HEIGHT = 30;

    /**
     * Default constructor that takes a post for display.
     * Initializes the UI by calling the superclass constructor.
     * @see TemplateUI
     */
    public ShowPost(Post post) {
        this.post = post;
        super();
    }

    /**
     * Returns the name of this UI page for display in the header.
     * @return the name of this page
     */
    @Override
    public String getName() {
        return "post";
    }

    /**
     * Initializes the UI components.
     * This method is called from the TemplateUI parent class during construction.
     */
    @Override
    public void initializeUI() {
        JPanel mainContentPanel = createMainContentPanel();
        add(mainContentPanel);
        revalidate();
        repaint();
    }

    /**
     * Creates the main content panel containing all components
     */
    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add components
        mainPanel.add(createNavigationPanel(), BorderLayout.NORTH);
        mainPanel.add(createImagePanel(), BorderLayout.CENTER);
        mainPanel.add(createDetailsPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    /**
     * Creates a navigation panel with a back button
     */
    private JButton createNavigationPanel() {
        JButton backButton = new JButton("TO HOME");
        backButton.setPreferredSize(new Dimension(PANEL_WIDTH / 2, BUTTON_HEIGHT));
        backButton.addActionListener(e -> navigateToHome());
        return backButton;
    }

    /**
     * Navigates back to the home screen
     */
    private void navigateToHome() {
        BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.switchPanel(new QuakstagramHomeUI());
    }

    /**
     * Creates the panel displaying the post image
     */
    private JPanel createImagePanel() {
        return new RegularDisplayedPost(post, 300, 300);
    }

    /**
     * Creates a panel with all the post details
     */
    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new BorderLayout(5, 5));

        detailsPanel.add(createUserInfoPanel(), BorderLayout.NORTH);
        detailsPanel.add(createCaptionPanel(), BorderLayout.CENTER);
        detailsPanel.add(createLikesPanel(), BorderLayout.SOUTH);

        return detailsPanel;
    }

    /**
     * Creates the top section with username and timestamp
     */
    private JPanel createUserInfoPanel() {
        JPanel topSection = new JPanel(new BorderLayout());

        JButton usernameButton = createUsernameButton();
        JLabel timeLabel = createTimestampLabel();

        topSection.add(usernameButton, BorderLayout.WEST);
        topSection.add(timeLabel, BorderLayout.EAST);

        return topSection;
    }

    /**
     * Creates an interactive username button
     */
    private JButton createUsernameButton() {
        JButton usernameButton = new JButton(post.getUsername());
        usernameButton.setBorderPainted(false);
        usernameButton.setContentAreaFilled(false);
        usernameButton.addActionListener(e -> showUserProfile());
        return usernameButton;
    }

    /**
     * Creates a label showing time since post was created
     */
    private JLabel createTimestampLabel() {
        LocalDateTime postTime = post.getTimePosted();
        JLabel timeLabel = new JLabel(ShowPostLogic.calculateTimeSincePosting(postTime));
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
        return timeLabel;
    }

    /**
     * Creates the center section with post caption
     */
    private JTextArea createCaptionPanel() {
        JTextArea bioArea = new JTextArea(post.getCaption());
        bioArea.setEditable(false);
        bioArea.setWrapStyleWord(true);
        bioArea.setLineWrap(true);
        bioArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return bioArea;
    }

    /**
     * Creates the bottom section with likes count and like button
     */
    private JPanel createLikesPanel() {
        JPanel bottomSection = new JPanel(new BorderLayout(5, 5));
        bottomSection.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel likesLabel = createLikesLabel();
        JButton likeButton = createLikeButton(likesLabel);
        bottomSection.add(likesLabel, BorderLayout.WEST);
        bottomSection.add(likeButton, BorderLayout.EAST);

        return bottomSection;
    }

    /**
     * Creates a label displaying the post's like count
     */
    private JLabel createLikesLabel() {
        JLabel likesLabel = new JLabel("" + post.getLikesCount());
        likesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return likesLabel;
    }

    /**
     * Creates a like button with appropriate styling based on whether the user has liked the post
     */
    private JButton createLikeButton(JLabel likesLabel) {
        boolean isLiked = QuakstagramHomeLogic.didUserAlreadyLike(post);
        JButton likeButton = new JButton("❤");
        likeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        likeButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        updateLikeButtonAppearance(likeButton, isLiked);
        likeButton.addActionListener(new ActionListener() {
            private boolean currentlyLiked = isLiked;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentlyLiked) {
                    QuakstagramHomeLogic.likePost(post);
                    currentlyLiked = true;
                } else {
                    QuakstagramHomeLogic.unLikePost(post);
                    currentlyLiked = false;
                }

                updateLikeButtonAppearance(likeButton, currentlyLiked);
                likesLabel.setText("" + post.getLikesCount());
            }
        });

        return likeButton;
    }

    /**
     * Updates the like button appearance based on like status
     */
    private void updateLikeButtonAppearance(JButton button, boolean isLiked) {
        if (isLiked) {
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBackground(LIKE_BUTTON_COLOR);
            button.setForeground(Color.WHITE);
        } else {
            button.setForeground(LIKE_BUTTON_COLOR);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
        }
    }

    /**
     * Navigates to the profile of the post creator
     */
    private void showUserProfile() {
        BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
        User profileUser = LogicClass.getUser(post.getUsername());
        parentFrame.switchPanel(new InstagramProfileUI(profileUser));
    }
}