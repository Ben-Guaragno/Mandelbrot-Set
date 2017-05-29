package voidstudio.cMandelbrotHisto;

public class Complex {
	private final double re;   // the real part
	private final double im;   // the imaginary part

	// create a new object with the given real and imaginary parts
	public Complex(double real, double imag) {
		re = real;
		im = imag;
	}

	// return a string representation of the invoking Complex object
	public String toString() {
		if (im == 0) return re + "";
		if (re == 0) return im + "i";
		if (im <  0) return re + " - " + (-im) + "i";
		return re + " + " + im + "i";
	}

	// return a new Complex object whose value is (this + b)
	public Complex plus(Complex b) {
		Complex a = this;             // invoking object
		double real = a.re + b.re;
		double imag = a.im + b.im;
		return new Complex(real, imag);
	}

	// return a new Complex object whose value is (this - b)
	public Complex minus(Complex b) {
		Complex a = this;
		double real = a.re - b.re;
		double imag = a.im - b.im;
		return new Complex(real, imag);
	}

	// return a new Complex object whose value is (this * b)
	public Complex times(Complex b) {
		Complex a = this;
		double real = a.re * b.re - a.im * b.im;
		double imag = a.re * b.im + a.im * b.re;
		return new Complex(real, imag);
	}
	
    // return a new Complex object whose value is the reciprocal of this
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }

	// return the real or imaginary part
	public double re() { return re; }
	public double im() { return im; }

	// return a / b
	public Complex divides(Complex b) {
		Complex a = this;
		return a.times(b.reciprocal());
	}
	
	public Complex power(int pow){
		if(pow==0) return new Complex(0,0);
		if(pow>1){
			Complex ans=this;
			for(int i=1;i<pow;i++){
				ans=ans.times(this);
			}
			return ans;
		}
		else{
			Complex ans=this;
			for(int i=0;i>pow;i--){
				ans=ans.times(this);
			}
			return new Complex(1,0).divides(ans);
		}
	}
	
    // return a new Complex object whose value is the complex sine of this
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex cosine of this
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex tangent of this
    public Complex tan() {
        return sin().divides(cos());
    }
    
    // return a new Complex object whose value is the complex exponential of this
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

}
