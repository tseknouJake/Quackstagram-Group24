package UI.Panels;

import javax.swing.*;
import Logic.Panels.GridPanelLogic;
import UI.Pages.ShowPost;
import ParameterClasses.Post;
import ParameterClasses.User;
import UI.BaseFrame;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This class allows a full display of a certain users post or post display of all users
 */
public class GridPanel extends JPanel {
    private ArrayList<Post> posts;
    private JPanel contentPanel;
    private boolean hasPosts;

    /**
     * The class is being initialized with the wanted user whos posts are for display
     * @param user - can be a specific user or null - which means all users
     */
    public GridPanel(User user) {
        setLayout(new BorderLayout());

        // Use FlowLayout with LEFT alignment to ensure items start from top-left
        contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Remove padding

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set viewport view to start from the very top
        scrollPane.getVerticalScrollBar().setValue(0);

        add(scrollPane, BorderLayout.CENTER);

        posts = GridPanelLogic.fetchAllPosts(user);
        
        loadPosts();
    }

    // Load media files (Images & Videos)

    /**
     * Post loading of corresponding user
     */
    private void loadPosts() {
        if (posts == null || posts.isEmpty()) {
            showNoPostsMessage();
            return;
        }

        hasPosts = false;

        // Calculate panel width to determine how many images fit per row
        int containerWidth = getWidth();
        if (containerWidth <= 0) containerWidth = 300; // Default if not yet sized

        int imageWidth = 80; // Width of each image container
        int imagesPerRow = Math.max(1, containerWidth / imageWidth);

        // Create a wrapper panel with grid layout to ensure proper row organization
        JPanel gridWrapper = new JPanel(new GridLayout(0, imagesPerRow, 5, 5));
        gridWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        gridWrapper.setAlignmentY(Component.TOP_ALIGNMENT);

        for (Post post : posts) {
            if (post != null) {
                // Create a fixed-size container for each post
                JPanel postContainer = new JPanel();
                postContainer.setPreferredSize(new Dimension(90, 90));
                postContainer.setLayout(new BorderLayout());

                JPanel postPanel = new RegularDisplayedPost(post, 80, 80);
                postPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(postPanel);
                        parentFrame.switchPanel(new ShowPost(post));
                    }
                });

                postContainer.add(postPanel, BorderLayout.CENTER);
                gridWrapper.add(postContainer);
                hasPosts = true;
            }
        }

        // Add the grid wrapper to the content panel
        contentPanel.removeAll();
        contentPanel.add(gridWrapper, BorderLayout.NORTH);

        if (!hasPosts) {
            showNoPostsMessage();
        }

        revalidate();
        repaint();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        // Force a reload when the component is added to ensure proper sizing
        SwingUtilities.invokeLater(this::reloadGrid);
    }

    private void reloadGrid() {
        if (hasPosts) {
            // This forces the grid to recalculate with the actual container width
            loadPosts();
        }
    }

    /**
     * Message display in case of no posts to show
      */
    private void showNoPostsMessage() {
        contentPanel.removeAll();
        JLabel message = new JLabel("No posts available", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 16));
        contentPanel.add(message);
        revalidate();
        repaint();
    }
}