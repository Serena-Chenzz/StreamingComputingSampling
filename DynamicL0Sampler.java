// DynamicL0Sampler.java
// COMP90056 2018s2
// Assignment B
// By Lu Chen, Student Number: 883241
// Based on William Holland's code

import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class DynamicL0Sampler implements Sampler<Integer> {

    // length of vector support
    private int n;
    
    private int p=1073741719;
    
    //We set the max subset number with 30 because p < 2^30
    private final int MAXROW = 30;

    private int sparsity=40;

    // struct of sparse rec. structures
    private SSparseRec[] recovery;

    // selected from k-wise independent family of hash functions with k>=s/2
    private Hash hash;

    public DynamicL0Sampler(int n) {
        this.n = n;
        //Initiate the hash function, range is same as p.
        hash = new Hash(p,(sparsity/2));
        recovery = new SSparseRec[MAXROW];
        //Initialize subsets. Subsets are built on S-Sparse vector.
        for(int i=0; i<MAXROW;i++){
            recovery[i] = new SSparseRec(4,sparsity);
        }
        
    }

    public void add(Integer index, int value) {
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
