// Hash.java
// k-wise Hash class
// By Lu Chen, Student Number 883241
// based on awirth for COMP90056, Aug 2017,8 -- version 2

public class Hash{
	private int p = 1073741789; //smaller than 2^30
	//A series of random number a
	private int[] a;	
	private int b;
	private int range;
	private int k;
	
	public Hash(int range, int k){
	    this.k = k;
	    //Generate k-1 random "a"
	    a = new int[k-1];
	    for(int i=0; i<(k-1); i++){
	        a[i]=StdRandom.uniform(p-1)+1;
	    }
		b=StdRandom.uniform(p+1);
		//this.domain = domain;
		this.range = range;
		//System.out.format("a %16d b %12d p %12d %n", a,b,p);
	}
	public int hash(int key){
		// int x = key.hashCode() & 0x0fffffff;
        int count = 0;
        long prod = 0;
        while(count <k-1){
            prod *= (long)key;
            prod += (long)a[count];
            prod %= (long)p;
            count++;
        }
        prod = prod*key+(long)b;
        long y = prod % (long) p;
        int r = (int) y % range;
		//System.out.format("x %12d y %12d r %12d %n", x,y,r);
		return r;
	}

	public int getN() { return this.range; }

}
