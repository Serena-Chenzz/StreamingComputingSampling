// SSparseRec.java
// COMP90056 2018s2
// Assignment B
// William Holland

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SSparseRec {
    //"zero" means |T| = 0, "isSSparse" means 1<=|T|<=S, "more" means |T| > S
    private String flag= "";
    private int SSparseColumn = -1;

    private int r, s;

    // selected from pairwise independent family of hash functions
    private Hash[] hashFamily;
    private OneSparseRec[][] net;

    public SSparseRec(int r, int sparsity){
        //We need to have altogether r hash functions. Each hash function is to project items into 2s buckets
        this.r = r;
        this.s = sparsity;
        initialise();
    }

    private void initialise() {
        //range of the hash function is 2s
        hashFamily = new Hash[r];
        //Initialize the hash functions
        for(int i=0; i< hashFamily.length; i++){
            hashFamily[i] = new Hash(2*s);
        }
        //Initialze buckets
        net = new OneSparseRec[2*s][r];
        for(int i=0; i<2*s; i++){
            for(int j=0; j<r; j++){
                net[i][j] = new OneSparseRec();
            }
        }
    }

    public void add(int index, int value) {
       //Every time you add a pair. this item will be added to one bucket in each column.
        for (int j=0; j< hashFamily.length; j++){
            //Calculate the hash value
            int bucketNum = hashFamily[j].hash(index);
            //This item will be added to this bucket
            net[bucketNum][j].add(index, value);
        }
    }
    
    public void writeFlag(){
        //Examine all columns and find out the smallest item count
        for(int i=0; i< r; i++){
            int countZero = 0;
            int countOne = 0;
            int countMultiple = 0;
            for(int j=0; j< 2*s; j++){
                if(net[j][i].oneSparseTest().equals("zero")) countZero++;
                else if(net[j][i].isOneSparse()){
                    countOne++;
                }
                else{
                    countMultiple++;
                    break;
                }
            }
            //Check if it is the sparsest distribution
            String currFlag = "";
            if(countMultiple > 0) currFlag = "more";
            else if(countOne > s) currFlag = "more";
            else if(countOne <= s && countOne > 0){
                currFlag = "isSSparse";
            }
            else currFlag = "zero";
            
            //Compare
            if(flag.equals("")) {
                flag = currFlag;
                if(flag.equals("isSSparse")){
                    SSparseColumn = i;
                }
            }
            else if(flag.equals("more")&&currFlag.equals("isSSparse")){
                flag = currFlag;
                SSparseColumn = i;
            }
            else if((flag.equals("more") ||flag.equals("isSSparse"))&&currFlag.equals("zero")){
                flag = currFlag;
                SSparseColumn = -1;
            }
        }
    }
    
    public String getFlag(){
        writeFlag();
        return flag;
    }
    
    public boolean isSSparse(){
        writeFlag();
        if(flag.equals("isSSparse") || flag.equals("zero")){
            return true;
        }
        return false;
    }
    
    public String sparseRecTest() {
        writeFlag();
        if(flag.equals("zero")){
            return "zero";
        }
        else if (flag.equals("more")){
            return "more";
        }
        else{
            String s = "";
            HashMap<Integer, Integer> result = recover();
            Iterator<Map.Entry<Integer,Integer>> it = result.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Integer,Integer> currItem = it.next();
                s += currItem.getKey() + " " + currItem.getValue() + "\n";
            }
            return s;
        }
    }

    
    public int randomSelect(){
        int columnIndex = SSparseColumn;
        //System.out.println(columnIndex);
        Hash newHash = new Hash(2*s);
        int smallestIndex = -1;
        int smallestHashedValue = -1;
        for(int i=0; i< 2*s; i++){
            OneSparseRec currPair = net[i][columnIndex];
            if(currPair.isOneSparse()){
                int currHashedValue = newHash.hash(currPair.getIota()/currPair.getPhi());
                if(smallestHashedValue == -1 || currHashedValue<smallestHashedValue){
                    smallestHashedValue = currHashedValue;
                    //System.out.println(currPair.getIota()+" "+currPair.getPhi());
                    smallestIndex = currPair.getIota()/currPair.getPhi();
                }
                
            }
        }
        //System.out.println(smallestIndex);
        //System.out.println(smallestHashedValue);
        return smallestIndex;
    }
    
    // Only when the flag is isSSparse
    public HashMap<Integer, Integer> recover(){
        int column = SSparseColumn;
        HashMap<Integer,Integer> resultMap = new HashMap<Integer,Integer>();
        for(int j=0; j< 2*s; j++){
            OneSparseRec os = net[j][column]; 
            if(os.isOneSparse()){
                resultMap.put(os.getIota()/os.getPhi(), os.getPhi());
            }
        }
        return resultMap;
    }

    // this can help debug
    @Override public String toString() {
        StringBuilder sb = new StringBuilder("SSparseRecoveryEstimator{array=[");
        int numCols = 2*s;

        for (int i=0; i<r; i++)
            for (int j=0; j<numCols; j++)
                sb.append(String.format("(%d,%d)=%s, ", i, j, net[i][j].toString()));
        sb.append("]}");

        return sb.toString();
    }




}
