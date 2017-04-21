public class Vector2 {
    public double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
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
