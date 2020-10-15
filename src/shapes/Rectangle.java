package shapes;

public class Rectangle extends Shape {
    public Rectangle(int x, int y) {
        super(x, y);

        points[3] = new Point(x+1, y);
        points[0] = new Point(x, y);
        points[1] = new Point(x-1, y);
        points[2] = new Point(x-2, y);
    }
}
