// OneSparseRec.java
// COMP90056 2018s2
// Assignment B
// By Lu Chen, Student Number: 883241
// Based on William Holland's code

public class OneSparseRec {
    //To sum up the square of frequencies.
    private int V;
    private int phi;
    private int iota;
    private int tau;
    // Here we can choose a large p
    private int p = 1073741789; //smaller than 2^30
    private int q;

    public OneSparseRec() {
       //q is a random value between 1 and p
       q = StdRandom.uniform(p+1);
    }

    public void add(int index, int value) {
        V += value*(int)Math.pow(index,2);
        iota += index * value;
        phi += value;
        tau += (value * ((int)Math.pow(q, index)% p))% p;  
    }

    public boolean isOneSparse() {
        // If phi ==0 and V==0, it means there's no non-frequency items in the stream.
        if(phi == 0 && V==0){
            return false;
        }
        // If iota % phi !=0 (iota cannot be divided by phi), it means there are at least two non-frequency items in the stream.
        else if(iota%phi != 0){
            return false;
        }
        else{
            int targetSum = (int)phi * ((int)Math.pow(q, iota/phi) % p) %p;
            //If targetSum == tau, it's highly possible there is only one non-frequency item in the stream.
            if (targetSum == tau){
                return true;
            }
            return false;
        } 
    }

    public String oneSparseTest() {
        if (isOneSparse()){
            // Return the pair
            return iota/phi + " " + phi;
        }
        else if(phi == 0 && V==0){
            return "zero";
        }
        else{
            return "more";
        }
    }

    // getters
    public int getPhi() {
        // phi is F
        return this.phi;
    }

    public int getIota() {
        // iota is U
        return this.iota;
    }

    // this might help
    @Override public boolean equals(Object otherObj) {
        if (!(otherObj instanceof OneSparseRec)) return false;
        else {
            OneSparseRec oner = (OneSparseRec) otherObj;
            return this.getIota() == oner.getIota()
                    && this.getPhi() == oner.getPhi();
        }
    }

    @Override public String toString() {
        if(this.isOneSparse()) {
            return iota/phi + " " + phi;
        } else if(phi == 0) {
            return "zero" ;
        } else return "more";
    }


}
