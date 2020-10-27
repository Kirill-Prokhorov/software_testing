package ru.stqa.software_testing.sandbox;

public class Point {

    double x;
    double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double distance(Point p){

        return Math.sqrt((p.x - this.x)*(p.x - this.x) + (p.y - this.y)*(p.y - this.y));
    }
}