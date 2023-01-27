/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author khoit
 */
public class MandelbrotPanel extends JPanel implements MouseListener {
    int width;
    int height;
    private double xmin, xmax, ymin, ymax;
    private BufferedImage image;
    private int iterations;
    
    public MandelbrotPanel(int w, int h) {
        width = w;
        height = h;
        
        xmin = -1.5;
        xmax = 0.5;
        ymin = -1;
        ymax = 1;
        
        iterations = 100;
        
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        drawMandelbrot();
        addMouseListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, null, null);
    }
    
    private void drawMandelbrot() {
        ArrayList<MandelbrotMultithreading> threads = new ArrayList<>();
        ArrayList<ArrayList<Color>> colors = new ArrayList<>();
        
        for(int w = 0; w < width; w++) {
            ArrayList<Color> c = new ArrayList<>();
            colors.add(c);
            MandelbrotMultithreading thread = new MandelbrotMultithreading(w, xmin, xmax, ymin, ymax, width, height, iterations, c);
            threads.add(thread);
            thread.start();
        }
        
        for(int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            }
            catch(InterruptedException e) {
                System.out.println(e);
                System.exit(1);
            }
        }
        
        for(int w = 0; w < width; w++) {
            for(int h = 0; h < height; h++) {
                image.setRGB(w, h, colors.get(w).get(h).getRGB());
            }
        }
        repaint();
    }
    
    private void zoom(int x, int y, double zoomMultiplier) {
        ComplexNumber c = ComplexNumber.getComplexNumberAtCoords(xmin, xmax, x, width, ymin, ymax, y, height);
        double rangeX = (xmax - xmin) * zoomMultiplier;
        double rangeY = (ymax - ymin) * zoomMultiplier;
        double center_x = c.getReal();
        double center_y = c.getImaginary();
        
        xmin = center_x - rangeX / 2;
        xmax = center_x + rangeX / 2;
        ymin = center_y - rangeY / 2;
        ymax = center_y + rangeY / 2;
    }
    
    public void updateSize(int w, int h) {
        double rangeX = (xmax - xmin) * ((double) w / width);
        double rangeY = (ymax - ymin) * ((double) h / height);
        xmax = xmin + rangeX;
        ymax = ymin + rangeY;
        width = w;
        height = h;
        
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        drawMandelbrot();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Button " + e.getButton() + " clicked at pixel " + e.getX() + ", " + e.getY());
        if(e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Zooming in.");
            zoom(e.getX(), e.getY(), 0.5);
            iterations *= 1.3;
        }
        else if(e.getButton() == MouseEvent.BUTTON3) {
            System.out.println("Zooming out.");
            zoom(e.getX(), e.getY(), 2);
            iterations /= 1.3;
        }
        drawMandelbrot();
    }

    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}
