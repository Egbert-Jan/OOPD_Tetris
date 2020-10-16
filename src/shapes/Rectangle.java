package shapes;

public class Rectangle extends Shape {
    public Rectangle() {
        super();

        type = 0;

        points[3] = new Point(startX+1, startY);
        points[0] = new Point(startX, startY);
        points[1] = new Point(startX-1, startY);
        points[2] = new Point(startX-2, startY);
    }
}
