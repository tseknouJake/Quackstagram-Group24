package UI.Panels;

import UI.TemplateUI;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a JPanel that is the header depending on the ui panel that is being displayed
 */
public class HeaderPanel extends JPanel {

    /**
     * initializes the header creation
     * @param ui - the reference to the current panel that is being displayed
     */
    public HeaderPanel(TemplateUI ui) {
        createHeaderPanel(ui);
    }

    /**
     * Creates the actual header panel with the information from the panel the is being displayed
     * @param ui - the reference to the current panel that is being displayed
     */
    private void createHeaderPanel(TemplateUI ui) {
        String name = ui.getName();

        if(!name.equals("null")) {
            setBackground(new Color(51, 51, 51)); // Set a darker background for the header
            JLabel lblRegister = new JLabel(name,SwingConstants.CENTER);
            lblRegister.setFont(new Font("Arial", Font.BOLD, 20));
            lblRegister.setForeground(Color.WHITE); // Set the text color to white
            add(lblRegister);
            setPreferredSize(new Dimension(WIDTH, 40)); // Give the header a fixed height
            setOpaque(true);
            repaint();
        }

        add(ui.getHeaderPanel());
    }
}
