
package UI.Panels;

import UI.TemplateUI;

import UI.Pages.SignInUI;
import UI.Pages.SignUpUI;
import UI.Pages.*;
import UI.BaseFrame;
import javax.swing.*;

import Logic.LogicClass;

import java.awt.*;

/**
 * Navigation bar component for the application user interface.
 * This class provides navigation functionality between different pages of the application.
 * It creates either a login/signup switch button or a full navigation panel depending on the current UI context.
 */
public class NavBar extends JPanel {

    private static final int NAV_ICON_SIZE = 20; // Size for navigation icons

    /**
     * Constructs a NavBar component based on the current UI template.
     * Creates either a login/signup toggle button or a full navigation panel.
     *
     * @param ui The current template UI that this navigation bar will be associated with
     */
    public NavBar(TemplateUI ui) {
        String name = ui.getName();
        if(name.equals("Sign In")||name.equals("Sign Up")) {
            createSwitchButtonLogin(ui);
        }
        else {
            createNavigationPanel(ui);
        }
    }

    /**
     * Creates a button that toggles between sign-in and sign-up pages.
     * The button's text will be the opposite of the current page
     *
     * @param ui The current template UI that this button will be associated with
     */
    private void createSwitchButtonLogin(TemplateUI ui) {

        JButton switchButton = new JButton(ui.getName().equals("Sign Up") ? "Sign In" : "Sign Up");
        switchButton.setBounds(30, 250, 200, 35);
        switchButton.setBackground(Color.LIGHT_GRAY);
        switchButton.setForeground(Color.BLACK);
        switchButton.setFocusPainted(false);
        switchButton.setFont(new Font("Arial", Font.BOLD, 12));

        switchButton.addActionListener(e -> {
            if(ui.getName().equals("Sign Up")){
                BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
                parentFrame.switchPanel(new SignInUI());
            }
            else{
                BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
                parentFrame.switchPanel(new SignUpUI());
            }
        });

        add(switchButton);

    }

    /**
     * Creates the main navigation panel with buttons for home, explore, image upload,
     * notifications, and profile.
     * This panel is used for all pages except the sign-in and sign-up pages.
     *
     * @param ui The current template UI that this navigation panel will be associated with
     */
    private void createNavigationPanel(TemplateUI ui) {

        // Create and return the navigation panel
        // Navigation Bar
        setBackground(new Color(249, 249, 249));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(createHomeButton(ui));
        add(Box.createHorizontalGlue());

        add(createExploreButton(ui));
        add(Box.createHorizontalGlue());

        add(createImageUploadButton(ui));
        add(Box.createHorizontalGlue());

        add(createNotificationButton(ui));
        add(Box.createHorizontalGlue());

        add(createProfileButton(ui));
    }

    /**
     * Creates a button that navigates to the home page when clicked.
     *
     * @param ui The current template UI
     * @return A configured JButton for the home navigation
     */
    private JButton createHomeButton(TemplateUI ui) {
        JButton homeButton = new JButton("Home");
        homeButton = createIconButton("img/icons/home.png", "explore", ui);
        homeButton.addActionListener(e -> {
            BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new QuakstagramHomeUI());
        });
        return homeButton;
    }

    /**
     * Creates a button that navigates to the explore page when clicked.
     *
     * @param ui The current template UI
     * @return A configured JButton for the explore navigation
     */
    private JButton createExploreButton(TemplateUI ui) {
        JButton exploreButton = new JButton("Home");
        exploreButton = createIconButton("img/icons/search.png", "explore", ui);
        exploreButton.addActionListener(e -> {
            BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new ExploreUI());
        });
        return exploreButton;

    }

    /**
     * Creates a button that navigates to the image upload page when clicked.
     *
     * @param ui The current template UI
     * @return A configured JButton for the image upload navigation
     */
    private JButton createImageUploadButton(TemplateUI ui) {
        JButton imageUploadButton = new JButton("Home");
        imageUploadButton = createIconButton("img/icons/add.png", "explore", ui);
        imageUploadButton.addActionListener(e -> {
            BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new ImageUploadUI());
        });
        return imageUploadButton;
    }

    /**
     * Creates a button that navigates to the notifications page when clicked.
     *
     * @param ui The current template UI
     * @return A configured JButton for the notifications navigation
     */
    private JButton createNotificationButton(TemplateUI ui) {
        JButton notificationButton = new JButton("Home");
        notificationButton = createIconButton("img/icons/heart.png", "explore", ui);
        notificationButton.addActionListener(e -> {
            BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new NotificationsUI());
        });
        return notificationButton;
    }

    /**
     * Creates a button that navigates to the current user's profile page when clicked.
     * Uses LogicClass.getCurrUser() to get the current user information.
     *
     * @param ui The current template UI
     * @return A configured JButton for the profile navigation
     */
    private JButton createProfileButton(TemplateUI ui) {
        JButton profileButton = new JButton("Home");
        profileButton = createIconButton("img/icons/profile.png", "explore", ui);
        profileButton.addActionListener(e -> {
            BaseFrame parentFrame = (BaseFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new InstagramProfileUI(LogicClass.getCurrUser()));
        });
        return profileButton;
    }

    /**
     * Creates a button with an icon from the specified path.
     * The icon is scaled to the standard navigation icon size.
     *
     * @param iconPath The file path to the icon image
     * @param buttonType The type of button being created (for action configuration)
     * @param ui The current template UI
     * @return A configured JButton with the specified icon
     */
    private JButton createIconButton(String iconPath, String buttonType, TemplateUI ui) {
        ImageIcon iconOriginal = new ImageIcon(iconPath);
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);

        // Define actions based on button type
        return button;
    }
}
