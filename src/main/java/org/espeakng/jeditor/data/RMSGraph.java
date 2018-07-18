package org.espeakng.jeditor.data;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

import org.espeakng.jeditor.gui.MainWindow;
 
public class RMSGraph extends JPanel {
    double [][] data = MainWindow.rmsArray;
    final int PAD = 20;
 
    protected void paintComponent(Graphics g) {
    	MainWindow.getRMSData();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Ordinate label.
        String s = "rms";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abcissa label.
        s = "frequency";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double)(w - 2*PAD)/(data[0].length-1);
        double scale = (double)(h - 2*PAD);
        g2.setPaint(Color.green.darker());
        for(int i = 0; i < data[0].length-1; i++) {
            double x1 = PAD + i*xInc;
            double y1 = h - PAD - scale*data[1][i];
            double x2 = PAD + (i+1)*xInc;
            double y2 = h - PAD - scale*data[1][i+1];
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < data[0].length; i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*data[1][i];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
        repaint();
    }
 
    
 
}