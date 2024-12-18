import java.awt.*;
import javax.swing.*;

public class GameMap {

    public static void displayMap() {
        // Create the map window
        JFrame frame = new JFrame("Bank Heist Map");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        // Add the map panel to the frame
        frame.add(new MapPanel());
        frame.setVisible(true);
    }
}

// Inner class for drawing the map
class MapPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set background color
        this.setBackground(Color.WHITE);

        // Set font and draw the rooms
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.BLACK);

        // Entrance
        g.drawRect(150, 50, 100, 50);
        g.drawString("Entrance", 175, 80);

        // Lobby
        g.drawRect(150, 150, 100, 50);
        g.drawString("Lobby", 180, 180);

        // Manager's Office
        g.drawRect(50, 150, 100, 50);
        g.drawString("Manager's Office", 60, 180);

        // Puzzle Room
        g.drawRect(50, 250, 100, 50);
        g.drawString("Puzzle Room", 60, 280);

        // Vault Room
        g.drawRect(250, 250, 100, 50);
        g.drawString("Vault", 260, 280);

        // Draw connections between rooms
        g.drawLine(200, 100, 200, 150); // Entrance to Lobby
        g.drawLine(150, 200, 100, 250); // Lobby to Puzzle Room
        g.drawLine(200, 200, 300, 250); // Lobby to Vault
        g.drawLine(100, 180, 100, 250); // Lobby to Manager's Office (straight line)
        g.drawLine(150, 180, 150, 200); // Manager's Office to Lobby
    }
}
