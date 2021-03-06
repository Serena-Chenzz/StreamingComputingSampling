// InsertL0Sampler.java
// COMP90056 2018s2
// Assignment B
// By Lu Chen. Student Number: 883241
// Based on William Holland's code

import java.util.HashMap;
//import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class InsertL0Sampler implements Sampler<Integer> {
    
    //minPos is to record the smallest position returned by the hash function
    private int minPos = -1;
    
    //intendedIndex is to record the current index, which hashed to the minimum hash value.
    private Integer intendedIndex;

    // length of vector support
    private int n;

    // selected from k-wise independent family of hash functions with k>=s/2
    private Hash hash;

    public InsertL0Sampler(int n) {
        this.n = n;
        //Select a hash function randomly. r is equal to p in order to avoid collision
        hash = new Hash(1073741789,20);
    }

    public void add(Integer index, int value) {
        // record the hash value of this input data
        int k = hash.hash(index);
        // only record the smallest hash value and its corresponding index
        if(minPos < 0 || k < minPos){
            minPos = k;
            intendedIndex = index;
        }
    }

    public Integer output() {
        return intendedIndex;
    }

}
