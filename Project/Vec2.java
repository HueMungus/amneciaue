
public class Vec2 {
	// Yo mama
	//memes
	
	/*dank memes from outer space*/
	
	public double x, y;
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y);
	}
	
	public Vec2 minus(Vec2 other) {
		return new Vec2(this.x - other.x, this.y - other.y);
	}
	
	public Vec2 add(Vec2 other) {
		return new Vec2(this.x + other.x, this.y + other.y);
	}
	
	public Vec2 div(double d) {
		if (d != 0) 
			return new Vec2(Math.round(this.x / d), Math.round(this.x / d));
		
		return this;
		
	}
	
	public Vec2 div(int d) {
		if (d != 0)
			return div((double) d);
		
		return this;
	}
	
	public Vec2 multThis(double d) {
		this.x = this.x * d;
		this.y = this.y * d;
		return this;
	}
	
	public Vec2 mult(double d) {
		return new Vec2(Math.round(this.x * d),Math.round(this.y * d));
	}
	
	public static long DotProduct(Vec2 A, Vec2 B) {
		return Math.round((A.x * B.x) + (A.y * B.y));
	}
	
}
