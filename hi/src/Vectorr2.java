package stuff;

import java.awt.Point;

// My very own personalized 2D Vector class with math

public class Vector2 {
	// Float instead of int for smooth acceleration
	private float x, y;
	
	
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
		this.x = (float) x;
		this.y = (float) y;
	}

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void set(Vector2 other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y);
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
	
	/*// float for x
	public void add(float add) {
		this.x += add;
	}
	
	// int for y
	public void add(int add) {
		this.y += add;
	}*/
	
	public static Vector2 cast(Point from) {
		return new Vector2((int) from.getX(),(int) from.getY());
	}
	
	public static Point cast(Vector2 from) {
		return new Point(Math.round(from.getX()), Math.round(from.getY()));
	}
	
	public static int DotProduct(Vector2 A, Vector2 B) {
		return Math.round((A.getX() * B.getX()) + (A.getY() * B.getY()));
	}
	
}
