/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mandelbrot;

/**
 *
 * @author khoit
 */
public class ComplexNumber {
    private double real;
    private double imaginary;
    
    public ComplexNumber(double r, double i) {
        set(r, i);
    }
    
    public void set(double r, double i) {
        real = r;
        imaginary = i;
    }
    
    public double getReal() {
        return real;
    }
    
    public double getImaginary() {
        return imaginary;
    }
    
    public ComplexNumber add(ComplexNumber c) {
        real += c.getReal();
        imaginary += c.getImaginary();
        
        return this;
    }
    
    public ComplexNumber multiply(ComplexNumber c) {
        double leftReal = real;
        double leftImaginary = imaginary;
        double rightReal = c.getReal();
        double rightImaginary = c.getImaginary();
        
        real = leftReal * rightReal - leftImaginary * rightImaginary;
        imaginary = leftImaginary * rightReal + leftReal * rightImaginary;
        
        return this;
    }
    
    public ComplexNumber square() {
        double initial_real = real;
        double initial_imaginary = imaginary;
        
        real = initial_real * initial_real - initial_imaginary * initial_imaginary;
        imaginary = 2 * initial_real * initial_imaginary;
        
        return this;
    }
    
    public static ComplexNumber getComplexNumberAtCoords(double xmin, double xmax, int x, int screenWidth, double ymin, double ymax, int y, int screenHeight) {
        return new ComplexNumber(getCoordinate(xmin, xmax, x, screenWidth), getCoordinate(ymin, ymax, y, screenHeight));
    }
    
    private static double getCoordinate(double min, double max, int pixelIndex, int maxPixel) {
        double coord = (double) pixelIndex / maxPixel;
        double range = max - min;
        coord *= range;
        coord += min;
        
        return coord;
    }
    
    public int getMandelbrotEscapeCount(int maxIterations) {
        ComplexNumber z = new ComplexNumber(0, 0);
        
        int itr = 0;
        while(itr < maxIterations) {
            if(z.getSumOfSquares() > 4)
                break;
            
            itr++;
            z.square().add(this);
        }
        
        return itr;      
    }
    
    private double getSumOfSquares() {
        return real * real + imaginary * imaginary;
    }
}