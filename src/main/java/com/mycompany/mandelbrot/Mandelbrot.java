/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.mandelbrot;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;

/**
 *
 * @author khoit
 */
public class Mandelbrot {
    public static void main(String[] args) {
        int WIDTH = 1000;
        int HEIGHT = 1000;
        MandelbrotPanel mb = new MandelbrotPanel(WIDTH, HEIGHT);
        
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(mb);
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("Window resized");
                System.out.println(e.getComponent().getWidth());
                mb.updateSize(e.getComponent().getWidth(), e.getComponent().getHeight());
            }
        });
    }
}