/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mandelbrot;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author khoit
 */
public class MandelbrotMultithreading extends Thread {
    int w, width, height, iterations;
    double xmin, xmax, ymin, ymax;
    ArrayList<Color> colors;
    
    public MandelbrotMultithreading(int w, double xmin, double xmax, double ymin, double ymax, int width, int height, int iterations, ArrayList<Color> colors) {
        super();
        this.w = w;
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.width = width;
        this.height = height;
        this.iterations = iterations;
        this.colors = colors;
    }
    
    @Override
    public void run() {
        for(int h = 0; h < height; h++) {
            colors.add(getPixelColor(w, h));
        }
    }
    
    private Color getPixelColor(int w, int h) {
        //ComplexNumber c = getValueAtCoordinate(w, h);
        ComplexNumber c = ComplexNumber.getComplexNumberAtCoords(xmin, xmax, w, width, ymin, ymax, h, height);
        
        int itr = c.getMandelbrotEscapeCount(iterations);
        return colorAlgorithm2(itr);

    }
    
    private Color colorAlgorithm1(int itr) {
        double value = iterations / 255.0;
        value = itr / value;
        value = 255 - value;

        int r = (int) value;
        int g = (int) value;
        int b = (int) value;
        
        return new Color(r, g, b, 255);
    }
    
    private Color colorAlgorithm2(int itr) {
        double power = 0.75;
        double value = Math.pow(iterations, power) / 255.0;
        value = Math.pow(itr, power) / value;
        value = 255 - value;
        value = Math.max(0, value);

        int r = (int) value;
        int g = (int) value;
        int b = (int) value;
        
        return new Color(r, g, b, 255);
    }
}
