package main;

public class retangulo {

	// (x,y) represents top-left corner of rectangle
	double x;
	double y;
	double width;
	double height;

	public retangulo() {
		this.setPosition(0, 0);
		this.setSize(1, 1);

	}

	public retangulo(double x, double y, double w, double h) {
		this.setPosition(x, y);
		this.setSize(w, h);

	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(double w, double h) {
		this.height = h;
		this.width = w;
	}
	
	public boolean overlaps(retangulo outro) {
		
		// Four cases where these is no overlap:
		// 1: this to the left of other
		// 2: this to the right of other
		// 3: this is above other
		// 4: other is above this
		boolean noOverlap = this.x + this.width < outro.x || 
				outro.x + outro.width <  this.x ||
				this.y + this.height < outro.y ||
				outro.y + outro.height < this.y;
				
		return !noOverlap;
	}
}
