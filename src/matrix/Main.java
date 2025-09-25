package matrix;

import java.util.Scanner;
import matrix.Matrix;

public class Main {
    
    static Scanner sc1 = new Scanner(System.in);
    static Scanner sc2 = new Scanner(System.in);
    static Matrix matrix_1;
    static Matrix matrix_2;

    public static void main(String[] args) {
        while(true){
            System.out.print("\n===== Matrix Cal [Any Dimention] =====\n\n1. Add\n2. Subtract\n3. Multiply\n4. Determinent\n5. Define matrix\n6. Exit\n\nChoice: ");
            int choice = sc1.nextInt();
            
            if(choice==1){
                if(checkMatrix()){
                    Matrix result_matrix = matrix_1.add(matrix_2);
                    if(result_matrix!=null){
                        result_matrix.display();
                    }
                }
            }else if(choice==2){
                if(checkMatrix()){
                    Matrix result_matrix = matrix_1.subtract(matrix_2);
                    if(result_matrix!=null){
                        result_matrix.display();
                    }
                }
            }else if(choice==3){
                if(checkMatrix()){
                    Matrix result_matrix = matrix_1.multiplication(matrix_2);
                    if(result_matrix!=null){
                        result_matrix.display();
                    }
                }
            }else if(choice==4){
                if(checkMatrix()){
                    if(isSquare()){
                        System.out.print("\nEnter the matrix number (1 or 2): ");
                        int num = sc1.nextInt();
                        float det;
                        if(num==1){
                            det = matrix_1.calculateDet();
                        }else{
                            det = matrix_2.calculateDet();
                        }
                        
                        System.out.printf("\nDeterminent: %.2f\n", det);
                    }else{
                        System.out.println("\nDeterminent can be calculated only for Square matrices");
                    }
                }
            }else if(choice==5){
                saveMatrix();
            }else{
                System.out.println("\nProgram End!!");
                break;
            }
        }
        
    }

    static void saveMatrix(){
        System.out.print("\nEnter the dimention of matrix 1 (axb): ");
        String inputVal[] = sc2.nextLine().split("x");
        int R = Integer.parseInt(inputVal[0]);
        int C = Integer.parseInt(inputVal[1]);

        matrix_1 = new Matrix(R, C);

        for(int i=0;i<R;i++){
            System.out.printf("Enter row %d values: ", i+1);
            String row[] = sc2.nextLine().split(" ");
            for(int j=0; j<C; j++){
                matrix_1.setValue(i, j, Integer.parseInt(row[j]));
            }
        }

        System.out.print("\nEnter the dimention of matrix 2 (axb): ");
        String inputVal2[] = sc2.nextLine().split("x");

        R = Integer.parseInt(inputVal2[0]);
        C = Integer.parseInt(inputVal2[1]);

        matrix_2 = new Matrix(R, C);

        for(int i=0;i<R;i++){
            System.out.printf("Enter row %d values: ", i+1);
            String row[] = sc2.nextLine().split(" ");
            for(int j=0; j<C; j++){
                matrix_2.setValue(i, j, Integer.parseInt(row[j]));
            }
        }

    }

    static boolean checkMatrix(){
        if(matrix_1==null || matrix_2==null){
            System.out.println("\nPlease define matrices first");
            return false;
        }else{
            return true;
        }
    }

    static boolean isSquare(){
        if(matrix_1.rows == matrix_1.cols){
            return true;
        }else{
            return false;
        }
    }

}

