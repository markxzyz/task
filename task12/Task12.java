package Task12;
class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void print() {
        System.out.println("(" + x + "," + y + ")");
    }

    // Inserted Code:
    public void scale() {
        this.x /= 2;
        this.y /= 2;
    }
}
