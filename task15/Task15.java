package task15;

interface Shape {
    double calculateArea();
    double calculatePerimeter();
}


abstract class AbstractShape implements Shape {
    protected String color;
    protected double length;
    protected double width;

  
    public AbstractShape(String color, double length, double width) {
        this.color = color;
        this.length = length;
        this.width = width;
    }

    
    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
}


class Circle extends AbstractShape {
    private double radius;

    public Circle(String color, double radius) {
        
        super(color, 0, 0);      
        this.radius = radius;
    }

   
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }


    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}


class Rectangle extends AbstractShape {

    public Rectangle(String color, double length, double width) {
        super(color, length, width);
    }

  
}

public class Task15 {
    public static void main(String[] args) {
        // Create instances of Circle and Rectangle
        Shape circle = new Circle("Red", 5.0);
        Shape rectangle = new Rectangle("Blue", 4.0, 6.0);

        // Display results
        System.out.println("--- Circle Details ---");
        System.out.printf("Area: %.2f%n", circle.calculateArea());
        System.out.printf("Perimeter: %.2f%n", circle.calculatePerimeter());

        System.out.println("\n--- Rectangle Details ---");
        System.out.printf("Area: %.2f%n", rectangle.calculateArea());
        System.out.printf("Perimeter: %.2f%n", rectangle.calculatePerimeter());
    }
}
