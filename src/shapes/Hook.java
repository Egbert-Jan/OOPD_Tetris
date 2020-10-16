package shapes;

public class Hook extends Shape {
    public Hook() {
        super();

        type = 1;

        points[3] = new Point(startX, startY-1);
        points[0] = new Point(startX, startY);
        points[1] = new Point(startX+1, startY);
        points[2] = new Point(startX+2, startY);
    }
}
