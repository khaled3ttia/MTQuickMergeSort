import java.io.*;
import java.util.*;

class testClass {


public static void main(String[] args){
	nMTQuickSort qs = new nMTQuickSort();
	ArrayList<Integer> temp = new ArrayList<Integer>();
	try {
		Scanner scn = new Scanner(new File("unsortedData.txt"));
		while (scn.hasNext()){
			temp.add(scn.nextInt());
		}
		scn.close();

	} catch (FileNotFoundException e){
		System.out.println("Input Data File Not Found");
		return;
	}

	int[] data = new int[temp.size()];

	for (int i=0;i<temp.size(); i++){
		data[i] = temp.get(i);
	}

	long startTime = System.nanoTime();
	qs.sort(data);
	long endTime = System.nanoTime();

	long duration = endTime - startTime;
	try {


		File outFile = new File("sortedData.txt");
		FileOutputStream fos = 	new FileOutputStream(outFile);
		BufferedWriter wrt = new BufferedWriter(new OutputStreamWriter(fos));

	
		for (int i=0; i<data.length; i++){
			wrt.write(String.valueOf(data[i]));
			wrt.newLine();
		}
		wrt.close();
	}catch (IOException ex) {
		System.out.println("IO Exception");

	}
	System.out.println("Sorted data has been written to sortedData.txt");
	System.out.println("Sorting Time: " + duration + " nanoseconds");
}

}
