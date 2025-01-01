import java.util.Arrays;

public class Assignment02Q05 {

	public static void main(String[] args) {
		// do not change this part below
		int N = Integer.parseInt(args[0]);
		int[][] matrix = new int[N][N]; // the input matrix to be
		for(int i=0;i < N; i++){
			for(int j=0; j < N; j++){
				matrix[i][j] = Integer.parseInt(args[1+(i*N)+j]); // the value at [i][j] is the i*N+j item in args (without considering args[0])
			}
		}
		for(int i=0;i < N; i++)
			System.out.println(Arrays.toString(matrix[i]));
		System.out.println("");
		int[][] rotatedMatrix; // the rotated matrix
		
		//Rotating the matrix using another matrix in the memory:
		/*
		rotatedMatrix = new int[N][N];
		
		for(int i = 0; i< N; i++)
		{
			for(int j = 0; j < N; j++)
			{
				rotatedMatrix[j][(N-1)-i] = matrix[i][j];
			}
		}
		*/
		
		// Rotating the matrix without another matrix in the memory:
		rotatedMatrix = matrix;
		
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < N; j++)
			{
				//inserting the values from the input in different order into the matrix, in a way that the matrix is rotated
				rotatedMatrix[j][(N-1)-i] = Integer.parseInt(args[1+(i*N)+j]);
			}
		}

		
		
		// do not change this part below
		for(int i=0;i < N; i++){ 
			System.out.println(Arrays.toString(rotatedMatrix[i])); // this would compile after you would put value in transposedMatrix
		}
	}
}
