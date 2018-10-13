// Test.java
// Example staff testing code
// COMP90056 2018s2
// Assignment B
// William Holland

import java.util.Scanner;
import java.io.*;


public class Test {

	private final static String[] files = {
	        //"dinner-4.txt"
			//"oneSparseTrue1.txt",
			//"oneSparseTrue2.txt",
            //"oneSparseFalse1.txt",
            //"oneSparseTrue2.txt",
	        "oneSparseZero.txt"
	        //"sSparseS-1.txt"
	    };
            //"sSparseTrue.txt" ,
            //"sSparseFalse.txt",
            

	public static void main(String[] args) {

		OneSparseRec oner = null;
		SSparseRec sparse = null;
		InsertL0Sampler isamp = null;
		DynamicL0Sampler dsamp = null;
		Scanner scan;
		String[] line;

		int index, value;

		for(String file: files)
		    if(file.matches("one(.*)")) {

                try {
                    scan = new Scanner(new File("input/" + file));
                    scan.nextLine();
                    oner = new OneSparseRec();

                    while(scan.hasNext()) {
                        line = scan.nextLine().split(" ");
                        index = Integer.parseInt(line[0]);
                        value = Integer.parseInt(line[1]);

                        oner.add(index, value);
                    }
                    System.out.println(oner.oneSparseTest());

                    scan.close();
                } catch (FileNotFoundException e) {
                    System.out.println(e);
                }


            } else if(file.matches("sS(.*)")) {

                try {
                    scan = new Scanner(new File("input/" + file));
                    int sparsity = Integer.parseInt(scan.nextLine());
                    sparse = new SSparseRec(4, sparsity);

                    while(scan.hasNext()) {
                        line = scan.nextLine().split(" ");
                        index = Integer.parseInt(line[0]);
                        value = Integer.parseInt(line[1]);

                        sparse.add(index, value);
                    }
                    System.out.println(sparse.sSparseTest());

                    scan.close();
                } catch (FileNotFoundException e) {
                    System.out.println(e);
                }

            } else if(file.matches("dinner(.*)")) {
//                // Try to draw a plot to see the uniform distribution of the item.
//                int testNum = 10000;
//                // Altogether 10 items
//                double[] frequency = new double[100];
//                for(int i=0; i< testNum; i++){
//                    try {
//                        scan = new Scanner(new File("input/" + file));
//                        int n = Integer.parseInt(scan.nextLine());
//                        isamp = new InsertL0Sampler(n);
//
//                        while(scan.hasNext()) {
//                            line = scan.nextLine().split(" ");
//                            index = Integer.parseInt(line[0]);
//                            value = Integer.parseInt(line[1]);
//
//                            isamp.add(index, value);
//                        }
//                        System.out.println(isamp.output());
//                        frequency[(int)(isamp.output())-1]++;
//                        scan.close();
//                    } catch (FileNotFoundException e) {
//                        System.out.println(e);
//                    }
//                    
//                }
//                for(int i=0; i< frequency.length; i++){
//                    frequency[i] /= testNum;
//                }
//                StdStats.plotBars(frequency);
                
                int testNum = 10000;
                // Altogether 10 items
                double[] frequency = new double[100];
                for(int i=0; i< testNum; i++){
                    try {
                        scan = new Scanner(new File("input/" + file));
                        int n = Integer.parseInt(scan.nextLine());
                        dsamp = new DynamicL0Sampler(n);

                        while(scan.hasNext()) {
                            line = scan.nextLine().split(" ");
                            index = Integer.parseInt(line[0]);
                            value = Integer.parseInt(line[1]);

                            dsamp.add(index, value);
                        }
                        int r = dsamp.output();
                        System.out.println(r);
                        if(r!=-1){
                            frequency[r-1]++;
                        }
                        scan.close();
                    } catch (FileNotFoundException e) {
                        System.out.println(e);
                    }
                    
                }
                for(int i=0; i< frequency.length; i++){
                    frequency[i] /= 10000;
                }
                StdStats.plotBars(frequency);
                
		        System.out.println(isUniform(isamp, file));
		        System.out.println(isUniform(dsamp, file));
           }

	}

	public static boolean isUniform(Sampler samp, String file) {
        
	    return false;
    }


}

