public class Vector2 {
    public double x, y;


    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2 minus(Vector2 other) {
        return new Vector2(this.x - other.getX(), this.y - other.getY());
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.getX(), this.y + other.getY());
    }

    public Vector2 div(double d) {
        if (d != 0)
            return new Vector2(Math.round(this.x / d), Math.round(this.x / d));

        return this;

    }

    public Vector2 div(int d) {
        if (d != 0)
            return div((double) d);

        return this;
    }

    // multThis is preferred as it does not create a new Vector2
    public Vector2 multThis(double d) {
        this.setX((int) (getX() * d));
        this.setY((int) (getY() * d));
        return this;
    }

    public Vector2 mult(double d) {
        return new Vector2(Math.round(this.getX() * d),Math.round(this.getY() * d));
    }

    public static int DotProduct(Vector2 A, Vector2 B) {
        return Math.round((A.getX() * B.getX()) + (A.getY() * B.getY()));
    }
    public double length() { return Math.sqrt(x * x + y * y); }

    public static double length(Vector2 of) { return Math.sqrt(of.x * of.x + of.y + of.y); }

    public double DotProduct(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public static double DotProduct(Vector2 one, Vector2 two) { return one.x * two.x + one.y * two.y; }

    public double CrossProduct(Vector2 other) { return x * other.y - y * other.x; }

    public static double CrossProduct(Vector2 a, Vector2 b) { return a.x * b.y - a.y * b.x; }

}
