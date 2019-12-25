# Multi-Threaded Hybrid Randomized Quick-Merge Sort

This is a sorting implementation for integers, that uses both randomized quicksort and the merge subroutine from the merge sort. It is a multithreaded version that tries to maximize speedup by independlty sorting splits of the original input data and merging them (when the input size is large). Otherwise, we stick to the original, sequential implementation of the quicksort.

## Input data format

This implementation operates on signed integers. Data format should be a text file named "unsortedData.txt". The file should be in the same directory as the test program executable "testClass.class". File consists of lines, each line is simply an integer. A utility is also provided to generate random integers for the specified format. Utility exists in the "util" directory. Just "make" it and then run ./gen.exe with the required number of lines (integers). For example, ./gen.exe 100 would generate unsortedData.txt with 100 lines, each containing an integer. unsortedData.txt would be automatically placed in the home directory of this project.

### Compiling and running

To compile the main program, you need jvm. Just run the command "javac *.java" in the main directory. Then to run, run: "java testClass". This will run the test program and generate "sortedData.txt" in the same directory. You will also get a print of the execution time in nanoseconds
