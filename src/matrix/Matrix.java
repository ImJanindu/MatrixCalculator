package matrix;

public class Matrix {
    int rows, cols;
    float values[][];

    Matrix(int r, int c) {
        rows = r;
        cols = c;
        values = new float[r][c];
    }

    public void setValue(int r, int c, float value) {
        values[r][c] = value;
    }

    public float getValue(int r, int c) {
        return values[r][c];
    }

    public void display() {
        System.out.println();
        
        for (int i = 0; i < rows; i++) {
            System.out.print("|\t");
            for (int j = 0; j < cols; j++) {
                System.out.printf("%.2f", values[i][j] + "\t");
            }
            System.out.println("|");
        }
    }

    public Matrix add(Matrix m) {
        if(this.rows != m.rows || this.cols != m.cols) {
            System.out.println("\nMatrices dimensions do not match for addition.");
            return null;
        }
        Matrix result_matrix = new Matrix(this.rows, this.cols);
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                result_matrix.setValue(j, i, this.values[i][j] + m.values[i][j]);
            }
        }
        return result_matrix;
    }

    public Matrix subtract(Matrix m) {
        if(this.rows != m.rows || this.cols != m.cols) {
            System.out.println("\nMatrices dimensions do not match for subtraction.");
            return null;
        }
        Matrix result_matrix = new Matrix(this.rows, this.cols);
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                result_matrix.setValue(j, i, this.values[i][j] - m.values[i][j]);
            }
        }
        return result_matrix;
    }

    public Matrix multiplication(Matrix m) {
        if(this.cols != m.rows) {
            System.out.println("\nMatrix dimensions do not match for multiplication");
            return null;
        }

        Matrix result_matrix = new Matrix(this.rows, m.cols);

        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<m.cols; j++) {
                float sum=0, temp=0;
                for(int z=0; z<this.cols;z++){
                    temp = this.getValue(i, z) * m.getValue(z, j); 
                    sum += temp;                   
                }
                
                result_matrix.setValue(i, j, sum);
            }
        }
        return result_matrix;

    }

    float calculateDet(){
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0;i<this.rows;i++){
            for(int j=0;j<this.cols;j++){
                result.setValue(i, j, values[i][j]);
            }
        }
        
        // Used Gaussian elimination method

        // Convert matrix to triangular matrix
        for(int i=0;i<this.rows-1;i++){
            float pivot = this.getValue(i, i);

                
            for(int j=i+1;j<this.rows;j++){
                float[] currentRow = new float[this.cols];
                System.arraycopy(values[i], 0, currentRow, 0, values[i].length);
                
                float[] nextRow = new float[this.cols];
                System.arraycopy(values[j], 0, nextRow, 0, values[j].length);

                float mul = values[j][i]/pivot;

                for(int x=0;x<this.cols;x++){
                    currentRow[x] = currentRow[x] * mul;
                    nextRow[x] = nextRow[x] - currentRow[x];

                    this.setValue(j, x, nextRow[x]);
                }   

            }
        }

        // Multiply diagonal values in the triangular matrix to calculate determinent
        float det = 1;
        for(int i=0;i<this.rows;i++){
            det *= this.getValue(i, i);
        }

        for(int i=0;i<this.rows;i++){
            for(int j=0;j<this.cols;j++){
                this.setValue(i, j, result.getValue(i, j));
            }
        }

        return det;
    }

}