
public class Vector2 {
	// Yo mama
	//memes
	
	/*dank memes from outer space*/
	
	public double x, y;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y);
	}
	
	public Vector2 minus(Vector2 other) {
		return new Vector2(this.x - other.x, this.y - other.y);
	}
	
	public Vector2 add(Vector2 other) {
		return new Vector2(this.x + other.x, this.y + other.y);
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
		this.x = this.x * d;
		this.y = this.y * d;
		return this;
	}
	
	public Vector2 mult(double d) {
		return new Vector2(Math.round(this.x * d),Math.round(this.y * d));
	}
	
	/*// float for x
	public void add(float add) {
		this.x += add;
	}
	
	// int for y
	public void add(int add) {
		this.y += add;
	}*/
	
	public static long DotProduct(Vector2 A, Vector2 B) {
		return Math.round((A.x * B.x) + (A.y * B.y));
	}
	
}
