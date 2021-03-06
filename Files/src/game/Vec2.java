package game;

public class Vec2 {
	// Yo mama
	//memes
	
	/*dank memes from outer space*/
	
	public float x, y;
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2() {
		this.x = 0;
		this.y = 0;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public Vec2 minus(Vec2 other) {
		return new Vec2(this.x - other.x, this.y - other.y);
	}
	
	public Vec2 add(Vec2 other) {
		return new Vec2(this.x + other.x, this.y + other.y);
	}
	
	public Vec2 div(float d) {
		if (d != 0) 
			return new Vec2(Math.round(this.x / d), Math.round(this.x / d));
		
		return this;
		
	}
	
	public Vec2 div(int d) {
		if (d != 0)
			return div((float) d);
		
		return this;
	}
	
	public Vec2 multThis(float d) {
		this.x = this.x * d;
		this.y = this.y * d;
		return this;
	}
	
	public Vec2 multRound(float d) {
		return new Vec2(Math.round(this.x * d),Math.round(this.y * d));
	}
	
	public Vec2 mult(float d) {
		return new Vec2(this.x * d, this.y * d);
	}
	
	public boolean isWithin(Vec2 A, Vec2 B) {
		// When B is bigger than A
		if (this.x < B.x && this.x > A.x && this.y < B.y && this.y > A.y)
			return true;
		// When A is bigger than B
		if (this.x < A.x && this.x > B.x && this.y < A.y && this.y > B.y)
			return true;
		
		return false;
	}
	
	public static long DotProduct(Vec2 A, Vec2 B) {
		return Math.round((A.x * B.x) + (A.y * B.y));
	}
	
	@Override
	public String toString() {
		return this.x + ", " + this.y;
	}
	
}
