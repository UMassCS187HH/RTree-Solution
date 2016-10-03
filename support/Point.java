public class Point {
	private double x,y;
	
	/*
	 * A point represented through normalized points (0-1)
	 */
	public Point(double X,double Y) {
		this.x      = X;
		this.y      = Y;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	public String toString(){
		return x+", " + y;
	}
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Point)) {
			return false;
		}
		return this.hashCode() == o.hashCode();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
