package UI.Pages;

import ParameterClasses.Post;
import UI.BaseFrame;
import UI.Panels.RegularDisplayedPost;
import UI.TemplateUI;

import javax.swing.*;

import Logic.Pages.QuakstagramHomeLogic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Predicate;

import static TableManaging.DB.POSTS;

/**
 * Explore UI is the class that creates the explore page in the main app.
 * Users can lookup other users or view posts of all users
 * <p>
 * The page consists of:
 * <li>A search bar at the top to find users</li>
 * <li>A grid panel that displays content (presumably posts from users)</li>
 * </p>
 */
public class QuakstagramHomeUI extends TemplateUI {
    private static final int WIDTH = 300;
    private static final int IMAGE_WIDTH = WIDTH - 100; // Width for the image posts
    private static final int IMAGE_HEIGHT = 150; // Height for the image posts
    private static final Color LIKE_BUTTON_COLOR = new Color(255, 90, 95); // Color for the like button


    /**
     * Default constructor.
     * Initializes the UI by calling the superclass constructor.
     *
     * @see TemplateUI
     */
    public QuakstagramHomeUI() {
        super();
    }

    /**
     * Initializes the UI components.
     * This method is called from the TemplateUI parent class during construction.
     * It adds the main content panel to the UI and ensures proper focus and rendering.
     */
    @Override
    public void initializeUI() {
        // Content Scroll Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Set layout ONCE before adding content

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Fetch data and populate content
        ArrayList<Post> followingPosts = QuakstagramHomeLogic.getAllFollowingPosts();

        // Populate after setting layout but before adding to parent
        populateContentPanel(contentPanel, followingPosts);

        // Add scrollPane to parent component ONCE
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Returns the name of this UI page for display in the header.
     *
     * @return the name of this page
     */
    @Override
    public String getName() {
        return "home page";
    }

    /**
     * Populates the content panel with posts or shows a message if no posts exist
     */
    private void populateContentPanel(JPanel panel, ArrayList<Post> followingPosts) {
        if (followingPosts.isEmpty()) {
            showNoPostsMessage(panel);
            return;
        }

        for (Post post : followingPosts) {
            panel.add(createPostPanel(post));
            panel.add(createSpacingPanel());
        }
    }

    /**
     * Creates a panel to display a single post with username, image, caption, and like functionality
     */
    private JPanel createPostPanel(Post post) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(Color.WHITE);
        postPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        postPanel.setAlignmentX(CENTER_ALIGNMENT);

        // Add post components
        postPanel.add(createUsernameLabel(post));
        postPanel.add(createPostImage(post));
        postPanel.add(createCaptionLabel(post));

        // Add like components
        JLabel likesLabel = createLikesLabel(post);
        JButton likeButton = createLikeButton(post, likesLabel);

        postPanel.add(likesLabel);
        postPanel.add(likeButton);

        return postPanel;
    }

    /**
     * Creates a label displaying the post's username
     */
    private JLabel createUsernameLabel(Post post) {
        JLabel nameLabel = new JLabel(post.getUsername());
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return nameLabel;
    }

    /**
     * Creates a visual component for displaying the post image
     */
    private RegularDisplayedPost createPostImage(Post post) {
        return new RegularDisplayedPost(post, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    /**
     * Creates a label displaying the post's caption
     */
    private JLabel createCaptionLabel(Post post) {
        JLabel descriptionLabel = new JLabel(post.getCaption());
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return descriptionLabel;
    }

    /**
     * Creates a label displaying the post's like count
     */
    private JLabel createLikesLabel(Post post) {
        JLabel likesLabel = new JLabel("" + post.getLikesCount());
        likesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return likesLabel;
    }

    /**
     * Creates a like button with appropriate styling based on whether the user has liked the post
     */
    private JButton createLikeButton(Post post, JLabel likesLabel) {
        boolean isLiked = QuakstagramHomeLogic.didUserAlreadyLike(post);
        JButton likeButton = new JButton("❤");
        likeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        likeButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        // Set initial button appearance based on like status
        updateLikeButtonAppearance(likeButton, isLiked);

        // Add action listener for like/unlike functionality
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
                updateLikesCount(likesLabel, post);
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
     * Updates the displayed like count
     */
    private void updateLikesCount(JLabel likesLabel, Post post) {
        likesLabel.setText("" + post.getLikesCount());
    }

    /**
     * Creates a spacing panel between posts
     */
    private JPanel createSpacingPanel() {
        JPanel spacing = new JPanel();
        spacing.setPreferredSize(new Dimension(WIDTH - 10, 5));
        spacing.setBackground(new Color(230, 230, 230));
        return spacing;
    }

    /**
     * In case there are no posts to display there will be a "no posts available" label
     * @param panel - The reference to the content panel
     */
    private void showNoPostsMessage(JPanel panel) {
        JLabel message = new JLabel("No posts available", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(message);
        revalidate();
        repaint();
    }
}

