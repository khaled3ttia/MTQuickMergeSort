#include <iostream>
#include <fstream>
#include <random>
#include <limits.h>
using namespace std;

//A helper utility to generate random data of a specific length
//If no argument specified, it generates 1000000 integers

//Example Usage: ./gen.exe 20
//generates 20 random integers in unsortedData.txt
int main(int argc, char** argv) {
	int lines = 1000000;
	if (argc > 1){
		lines = atoi(argv[1]);
	}
	printf("Number of lines to be generated is : %d", lines);
	
	ofstream file;
	file.open("../unsortedData.txt");
	
	for (int i = 0; i<lines ; i++){
		std::random_device someSeed;
		std::mt19937 gen(someSeed());
		std::uniform_int_distribution<int> dis(INT_MIN, INT_MAX);
		file << dis(gen) << '\n';
	}
	file.close();
	return 0;

}
