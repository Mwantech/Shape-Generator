import javax.swing.*;  // Importing javax.swing for GUI components
import java.awt.*;     // Importing java.awt for drawing shapes
import java.util.Scanner;  // Importing java.util.Scanner for taking user input

// Abstract base class Shape that defines an abstract method draw
abstract class Shape {
    // Abstract method to draw the shape
    abstract void draw(Graphics g);
}

// Circle class extending Shape
class Circle extends Shape {
    double radius;  // Radius of the circle

    // Constructor to initialize radius
    Circle(double radius) {
        this.radius = radius;
    }

    // Override draw method to draw a circle
    @Override
    void draw(Graphics g) {
        // Draw an oval which is a circle if width and height are the same
        g.drawOval(50, 50, (int) radius * 2, (int) radius * 2);
    }
}

// Triangle class extending Shape
class Triangle extends Shape {
    double base;    // Base of the triangle
    double height;  // Height of the triangle

    // Constructor to initialize base and height
    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    // Override draw method to draw a triangle
    @Override
    void draw(Graphics g) {
        // Define x and y points for the vertices of the triangle
        int[] xPoints = {150, 150 + (int) base / 2, 150 - (int) base / 2};
        int[] yPoints = {50, 50 + (int) height, 50 + (int) height};
        // Draw a polygon with the specified vertices
        g.drawPolygon(xPoints, yPoints, 3);
    }
}

// Rectangle class extending Shape
class Rectangle extends Shape {
    double length;  // Length of the rectangle
    double width;   // Width of the rectangle

    // Constructor to initialize length and width
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // Override draw method to draw a rectangle
    @Override
    void draw(Graphics g) {
        // Draw a rectangle with the specified length and width
        g.drawRect(50, 50, (int) length, (int) width);
    }
}

// ShapeGenerator class extending JPanel for custom drawing
public class ShapeGenerator extends JPanel {
    // Constants for minimum and maximum dimensions
    static final double MIN_DIMENSION = 1.0;
    static final double MAX_DIMENSION = 100.0;
    private Shape currentShape;  // Variable to hold the current shape

    // Constructor to initialize the current shape
    public ShapeGenerator(Shape shape) {
        this.currentShape = shape;
    }

    // Override paintComponent to perform custom drawing
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // If a shape is set, draw it
        if (currentShape != null) {
            currentShape.draw(g);
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Scanner for user input
        boolean continueGenerating = true;  // Flag to control the loop

        // Loop to continuously prompt the user for shapes until they choose to quit
        while (continueGenerating) {
            // Display min and max dimensions
            System.out.println("Minimum dimension: " + MIN_DIMENSION + " cm, Maximum dimension: " + MAX_DIMENSION + " cm");
            // Prompt user to select a shape
            System.out.println("Select shape to draw: (C)ircle, (T)riangle, (R)ectangle, (Q)uit:");
            char choice = scanner.next().toUpperCase().charAt(0);  // Read user choice and convert to uppercase

            Shape shape = null;  // Variable to hold the selected shape

            // Switch statement to handle different shape choices
            switch (choice) {
                case 'C':
                    System.out.print("Enter radius of the circle(cm): ");
                    double radius = getValidDimension(scanner);  // Get valid radius from user
                    shape = new Circle(radius);  // Create a new Circle object
                    break;
                case 'T':
                    System.out.print("Enter base of the triangle (cm): ");
                    double base = getValidDimension(scanner);  // Get valid base from user
                    System.out.print("Enter height of the triangle (cm): ");
                    double height = getValidDimension(scanner);  // Get valid height from user
                    shape = new Triangle(base, height);  // Create a new Triangle object
                    break;
                case 'R':
                    System.out.print("Enter length of the rectangle (cm): ");
                    double length = getValidDimension(scanner);  // Get valid length from user
                    System.out.print("Enter width of the rectangle (cm): ");
                    double width = getValidDimension(scanner);  // Get valid width from user
                    shape = new Rectangle(length, width);  // Create a new Rectangle object
                    break;
                case 'Q':
                    continueGenerating = false;  // Set flag to false to exit loop
                    continue;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }

            // If a shape is created, display it in a JFrame
            if (shape != null) {
                JFrame frame = new JFrame("Shape Drawer");  // Create a new JFrame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set default close operation
                frame.setSize(800, 800);  // Set size of the frame
                frame.add(new ShapeGenerator(shape));  // Add ShapeGenerator panel to the frame
                frame.setVisible(true);  // Make the frame visible

                // Prompt user if they want to draw another shape
                System.out.println("Do you want to draw another shape? (Y/N):");
                char continueChoice = scanner.next().toUpperCase().charAt(0);  // Read user choice
                if (continueChoice != 'Y') {
                    continueGenerating = false;  // Set flag to false to exit loop
                } else {
                    frame.dispose();  // Close the previous frame to draw a new shape
                }
            }
        }

        scanner.close();  // Close the scanner
        System.out.println("Shape generation finished.");
    }

    // Method to get a valid dimension within the specified range
    private static double getValidDimension(Scanner scanner) {
        double dimension;
        while (true) {
            dimension = scanner.nextDouble();  // Read user input
            if (dimension >= MIN_DIMENSION && dimension <= MAX_DIMENSION) {
                break;  // Break loop if dimension is valid
            } else {
                // Prompt user for valid input if dimension is out of range
                System.out.println("Invalid dimension. Enter a value between " + MIN_DIMENSION + " cm and " + MAX_DIMENSION + " cm:");
            }
        }
        return dimension;  // Return the valid dimension
    }
}
