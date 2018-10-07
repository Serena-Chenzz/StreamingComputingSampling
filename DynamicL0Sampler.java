// DynamicL0Sampler.java
// COMP90056 2018s2
// Assignment B
// William Holland

import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class DynamicL0Sampler implements Sampler<Integer> {
    // you may need to add additional class attributes

    // length of vector support
    private int n;
    
    private int p=1073741719;
    
    private final int MAXROW = 30;

    private int sparsity=40;

    // struct of sparse rec. structures
    private SSparseRec[] recovery;

    // selected from k-wise independent family of hash functions with k>=s/2
    private Hash hash;

    public DynamicL0Sampler(int n) {
        this.n = n;
        //Initiate the hash function, range is 2^30+1 (we can see it as 2^30)
        hash = new Hash(p);
        recovery = new SSparseRec[MAXROW];
        for(int i=0; i<MAXROW;i++){
            recovery[i] = new SSparseRec(4,sparsity);
        }
        
    }

    public void add(Integer index, int value) {
        //The value is not important in l0-sampler. In order to avoid negative frequency, we transfer them into a dense structure
        value = Math.abs(value)+1;
        //Add this pair into each SSparseRec
        //First check the hash value of this pair
        int hashValue = hash.hash(index);
        for(int i=0; i< MAXROW; i++){
            //Then check if it belongs to the smallest 2^(30-i) items
            if(hashValue <= p/((int)Math.pow(2, i))){
                recovery[i].add(index, value);
            }
        }
    }

    public Integer output() {
        //First, find out the longest S-Sparse vector
        int longestRow = -1;
        for(int i=0; i<MAXROW; i++){
            String currResult = recovery[i].getFlag();
            if(currResult.equals("isSSparse")){
                longestRow = i;
                break;
            }
        }
        if(longestRow == -1) return -1;
        
        //Randomly select a value from this vector
        return recovery[longestRow].randomSelect();
    }

    // this may help
    @Override public String toString() {
        StringBuilder sb = new StringBuilder("L0_samp: s-sparse strucs: %n");
        for(int i=0; i<recovery.length; i++) {
            sb.append("struc: " + i + " " + recovery[i] + "%n");
        }
        return sb.toString();
    }

}
