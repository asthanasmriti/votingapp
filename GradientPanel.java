package ui;

import java.awt.*;
import javax.swing.*;

public class GradientPanel extends JPanel {
    private Color startColor = new Color(0x004d99);
    private Color endColor = new Color(0x66ccff);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, startColor, 0, h, endColor);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
