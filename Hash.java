
// Hash.java
// Hash class
// awirth for COMP90056
// Aug 2017,8 -- version 2

public class Hash{
	private int p = 1073741789; //smaller than 2^30
	private int a,b;	
	private int range;
	
	public Hash(int range){
		a=StdRandom.uniform(p-1)+1;
		b=StdRandom.uniform(p+1);
		//this.domain = domain;
		this.range = range;
		//System.out.format("a %16d b %12d p %12d %n", a,b,p);
	}
	public int hash(int key){
		// int x = key.hashCode() & 0x0fffffff;

		long prod = (long)a*(long)key;
		prod += (long)b;
		long y = prod % (long) p;
		int r = (int) y % range;
		//System.out.format("x %12d y %12d r %12d %n", x,y,r);
		return r;
	}

	public int getN() { return this.range; }

}
