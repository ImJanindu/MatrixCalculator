package matrix;

public class Matrix {
    public int rows, cols;
    float values[][];

    public Matrix(int r, int c) {
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
            System.out.print("|    ");
            for (int j = 0; j < cols; j++) {
                System.out.printf("%.2f\t", values[i][j]);
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

    public float calculateDet(){
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0;i<this.rows;i++){
            for(int j=0;j<this.cols;j++){
                result.setValue(i, j, values[i][j]);
            }
        }
        
        // Used Gaussian elimination method

        // Convert matrix to triangular matrix
        for(int i=0;i<result.rows-1;i++){
            float pivot = result.getValue(i, i);

            for(int j=i+1;j<this.rows;j++){
                float[] currentRow = new float[this.cols];
                System.arraycopy(result.values[i], 0, currentRow, 0, result.values[i].length);
                
                float[] nextRow = new float[this.cols];
                System.arraycopy(result.values[j], 0, nextRow, 0, result.values[j].length);

                float mul = result.values[j][i]/pivot;

                for(int x=0;x<result.cols;x++){
                    currentRow[x] = currentRow[x] * mul;
                    nextRow[x] = nextRow[x] - currentRow[x];

                    result.setValue(j, x, nextRow[x]);
                }   
            }
        }

        // Multiply diagonal values in the triangular matrix to calculate determinant
        float det = 1;
        for(int i=0;i<this.rows;i++){
            det *= result.getValue(i, i);
        }

        return det;
    }

    public Matrix inverse() {
        int n = rows;
        Matrix a = new Matrix(n, n);
        // Copy original matrix
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a.setValue(i, j, this.getValue(i, j));

        Matrix inv = identityMatrix(n);

        for (int i = 0; i < n; i++) {
            float pivot = a.getValue(i, i);
            if (Math.abs(pivot) < 1e-6) {
                // Find a row to swap
                boolean swapped = false;
                for (int k = i + 1; k < n; k++) {
                    if (Math.abs(a.getValue(k, i)) > 1e-6) {
                        swapRows(a, i, k);
                        swapRows(inv, i, k);
                        pivot = a.getValue(i, i);
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    System.out.println("Matrix is singular and cannot be inverted.");
                    return null;
                }
            }
            // Normalize pivot row
            for (int j = 0; j < n; j++) {
                a.setValue(i, j, a.getValue(i, j) / pivot);
                inv.setValue(i, j, inv.getValue(i, j) / pivot);
            }
            // Eliminate other rows
            for (int k = 0; k < n; k++) {
                if (k == i) continue;
                float factor = a.getValue(k, i);
                for (int j = 0; j < n; j++) {
                    a.setValue(k, j, a.getValue(k, j) - factor * a.getValue(i, j));
                    inv.setValue(k, j, inv.getValue(k, j) - factor * inv.getValue(i, j));
                }
            }
        }
        return inv;
    }

    private static Matrix identityMatrix(int n) {
        Matrix id = new Matrix(n, n);
        for (int i = 0; i < n; i++)
            id.setValue(i, i, 1.0f);
        return id;
    }

    private static void swapRows(Matrix m, int row1, int row2) {
        float[] temp = m.values[row1];
        m.values[row1] = m.values[row2];
        m.values[row2] = temp;
    }

}