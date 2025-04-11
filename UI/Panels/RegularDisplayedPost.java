package UI.Panels;

import ParameterClasses.Post;
import UI.BaseFrame;
import UI.Pages.InstagramProfileUI;
import UI.Pages.ShowPost;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Regular post display panel with clickable image

/**
 * RegularDisplayPost is widely used to create JPanels to displayed clickable scaled posts - in grid panels or in the home page
 */
public class RegularDisplayedPost extends JPanel {
    private Post post;
    private BufferedImage image;

    /**
     * Initializing the class with the post to be displayed and the demanded size
     * @param post - the post to be displayed
     * @param width - desirable width
     * @param height - desirable height
     */
    public RegularDisplayedPost(Post post, int width, int height) {
        this.post = post;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.LIGHT_GRAY);

        // Load the post image
        loadPostImage(post.getPath());

        // Add mouse listener to detect clicks on the panel (image area)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Perform action when image is clicked
                BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(RegularDisplayedPost.this);
                parentFrame.switchPanel(new ShowPost(post));
            }
        });
    }

    /**
     * Loads the posts image or pops up corresponding error
     * @param imagePath
     */
    private void loadPostImage(String imagePath) {
        try {
            // Check if path is null or empty
            if (imagePath == null || imagePath.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Image path is null or empty", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                image = ImageIO.read(imgFile);
                if (image == null) {
                    JOptionPane.showMessageDialog(this, "Failed to read image: " + imagePath, "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Image file does not exist: " + imagePath, "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        } finally {
            repaint(); // Always repaint to show at least the placeholder
        }
    }

    /**
     * The paint component to draw the image in the desirable size
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("Image Not Available", getWidth() / 2 - 50, getHeight() / 2);
        }
    }
}
