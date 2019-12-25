

import java.util.Random;

//This Algorithm is a hybrid between Randomized Quick Sort and Merge Sort
//If we have a large number of elements to sort (>100000), elements are split and fed to 
//multiple thread, each thread sorts a subarray using randomized quick sort
//after all sorts are done, we employ the "merge" helper method of the merge sort algorithm
//to correctly merge the resultant sorted subarrays in a multi-threaded fashion as well
//If the number of points is <= 50000, it is not worth dealing with overhead of multithreading
//so elements are just sorted sequentially using randomized quick sort
public class nMTQuickSort {
    
  
    
	//The merge method, which is used to merge subarrays sorted using randomized quicksort
	//Used only when multi-threading is used
    void merge(int[] arr, int p, int q, int r){
		int n1 = q - p + 1;
		int n2 = r - q - 1;
		int[] L = new int[n1+1];
		int[] R = new int[n2+1];
		for (int i = 0; i < n1; i++){
			L[i] = arr[p + i];			
		}
		for (int j = 0; j < n2; j++){
			R[j] = arr[q + j + 1];
		}
		L[n1] = Integer.MAX_VALUE;
		R[n2] = Integer.MAX_VALUE;

		int i = 0;
		int j = 0;
		for (int k = p; k < r; k++){

			if (L[i] <= R[j]){
				arr[k] = L[i];
				i++;
			}else{
				arr[k] = R[j];
				j++;
			}
		}
	}

	//The quicksort randomizedParition routine
    int randomizedPartition(int[] arr, int p, int r){
		Random rand = new Random();
		int i = p + rand.nextInt(r - p);
		arr[r] = arr[r] ^ arr[i];
		arr[i] = arr[r] ^ arr[i];
		arr[r] = arr[r] ^ arr[i];
		
		return partition(arr, p, r);
    }

	//The partition helper routine
    int partition(int[] arr,int p, int r){

		int x = arr[r];
		int i = p - 1 ;
		for (int j = p; j < r; j++){
			if (arr[j] <= x){
				++i;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		
		int temp = arr[i+1];
		arr[i+1] = arr[r];
		arr[r] = temp;
		
		return i+1;
    }

	//Implementation of Randomized QuickSort
    void quicksort(int[] arr,int p,int r){
    	
		if (p<r){		
			int q = randomizedPartition(arr, p, r);
			quicksort(arr, p, q-1);
			quicksort(arr, q+1, r);
		}

    }

    /* You may define any new methods you want and may change this method */
    void sort(int[] arr) {
		//Get the number of elements we are trying to sort
		int n = arr.length;

		//Find out the maximum number of threads that we can use
		int maxThreads = Runtime.getRuntime().availableProcessors();
		
		//If the number of elements is <= 100000, we use a maximum of 2 threads
		//Experiment showed that going more than 2 threads for that number of points
		//introduces more overhead (for thread synchronization)
		if (n <= 100000){
			maxThreads = 2;
		}
		if (n <= 50000){
			maxThreads = 1;
		}
		
		//If we are going multithreaded, we have sorting threads and merging threads
		//We split the points we have and map each group of points to a dedicated sorting thread 
		//After sorting, we have another set of merging threads to merge the quicksorted subarrays
		//and finally, an outer merge to merge the final two arrays
		if (maxThreads >= 2) {
			int pointsPerThread = n / maxThreads;
			int additionalPoints = n - pointsPerThread * maxThreads;
			int p = 0; 
			int q = pointsPerThread;
				
			Thread[] sortingThreads = new Thread[maxThreads];
			for (int i = 0; i < maxThreads; i++){
				final int p1 = p;
				if (i == (maxThreads-1)){
					q += additionalPoints;
				}
				final int q1 = q;

				sortingThreads[i] = new Thread() { public void run() { quicksort(arr, p1 , q1-1); } };
		
				sortingThreads[i].start();
				
				p = q; 
				q += pointsPerThread;
			}
			
			try{
				for (int i=0;i<maxThreads; i++){
					sortingThreads[i].join();
				}
			}catch (InterruptedException e){
				e.printStackTrace();
		
			}
			
			int numPoints = pointsPerThread;
			int numMergeThreads = maxThreads / 2;
			int r;
			if (maxThreads > 2) {
				while (numMergeThreads > 1) {
				Thread[] mergingThreads = new Thread[numMergeThreads];
				p = 0;
				q = numPoints - 1;
				r = 2 * numPoints;

				for (int i = 0; i< numMergeThreads ; i++){
					final int p2 = p;
					final int q2 = q;
					if ( i == (numMergeThreads - 1)){
						r = n;
					}
					final int r2 = r;
					mergingThreads[i] = new Thread() { public void run() { merge(arr, p2, q2, r2);  } };
					mergingThreads[i].start();
					p += 2* numPoints;
					q += 2* numPoints;
					r += 2* numPoints;
				}
				try {
					for (int i =0 ; i < numMergeThreads; i++) {
						mergingThreads[i].join();
					}
				}catch (InterruptedException e){
					e.printStackTrace();
				}
		
				numMergeThreads = numMergeThreads / 2;
				numPoints = numPoints * 2;
				}
			}
			merge(arr, 0, (n/2)-1, n);
		}
		else {
			//If we can use a single thread (small number of points) we don't need the 
			//added complexity of merge, just quicksort the whole array at once
			quicksort(arr, 0, n - 1);
		}

	}



}

