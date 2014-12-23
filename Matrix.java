
import java.util.*;
import java.text.DecimalFormat;
@SuppressWarnings("unchecked")
/**
 * 
 * @author Jack Christiansen
 */
public class Matrix
{
    private int rows;
    private int columns;
    private double[][] matrix;
    
    public Matrix(int row, int col)
    {
        rows = row;
        columns = col;
        matrix = new double[rows][columns];
    }
    
    /**
     * 
     * @param row
     * @param col
     * @param values 
     */
    public Matrix(int row, int col, String values)
    {
        rows = row;
        columns = col;
        matrix = new double[rows][columns];
        
        Scanner scan = new Scanner(values);
        scan.useDelimiter(" ");
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                matrix[i][j] = scan.nextDouble();
            }
        }
    }
     public Matrix(int rowAndCol String values)
    {
        rows = rowAndCol;
        columns = rowAndCol;
        matrix = new double[rows][columns];
        
        Scanner scan = new Scanner(values);
        scan.useDelimiter(" ");
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                matrix[i][j] = scan.nextDouble();
            }
        }
    }
    public double getValue(int row, int col)
    {
        return matrix[row][col];
    }
    public double getValue(int rowAndCol)
    {
        return matrix[rowAndCol][rowAndCol];
    }
    public int getRowDim()
    {
        return rows;
    }
    
    public int getColumnDim()
    {
        return columns;
    }
    
    /**
     * 
     * @param firstRow
     * @param secondRow
     * @param scalar 
     */
    public void rowOp1(int firstRow, int secondRow, double scalar)
    {
        double[] temp = new double[rows];
        for (int i = 0; i < rows; i++)
        {
            temp[i] = scalar*matrix[firstRow][i];
            matrix[secondRow][i] += temp[i];
        }
    }
    
    /**
     * 
     * @param firstRow
     * @param secondRow 
     */
    public void rowOp2(int firstRow, int secondRow)
    {
        double[] temp = new double[rows];
        for (int i = 0; i < rows; i++)
        {
            temp[i] = matrix[firstRow][i];
            matrix[firstRow][i] = matrix[secondRow][i];
            matrix[secondRow][i] = temp[i];
        }
    }
    
    /**
     * 
     * @param row
     * @param scalar 
     */
    public void rowOp3(int row, double scalar)
    {
        for (int i = 0; i < rows; i++)
        {
            matrix[row][i] *= scalar;
        }
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public Matrix add(Matrix other)
    {
        String values = "";
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                values += (getValue(i, j) + other.getValue(i, j)) + " ";
            }
        }
        Matrix result = new Matrix(getRowDim(), getColumnDim(), values);
        return result;
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public Matrix subtract(Matrix other)
    {
        String values = "";
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                values += (getValue(i, j) - other.getValue(i, j)) + " ";
            }
        }
        Matrix result = new Matrix(getRowDim(), getColumnDim(), values);
        return result;
    }
    
    /**
     * 
     * @param scalar
     * @return 
     */
    public Matrix scalarMult(double scalar)
    {
        String values = "";
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                values += (getValue(i, j)*scalar) + " ";
            }
        }
        Matrix result = new Matrix(getRowDim(), getColumnDim(), values);
        return result;
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public Matrix matrixMult(Matrix other)
    {
        if (getColumnDim() != other.getRowDim())
        {
            System.out.println("Error, rows != columns.");
            return null;
        }
        else
        {
            String values = "";
            for (int i = 0; i < other.getRowDim(); i++)
            {
                for (int j = 0; j < getColumnDim(); j++)
                {
                    double entry = 0;
                    
                    for (int k = 0; k < getColumnDim(); k++)
                    {
                        entry += getValue(i, k)*other.getValue(k, j);
                    }
                    
                    values += entry + " ";
                }
            }
            Matrix result = new Matrix(getRowDim(), other.getRowDim(), values);
            return result;
        }
    }
    
    /**
     * 
     * @return 
     */
    public Matrix rowReduce(Matrix input)
    {   
    	Matrix output = input;
    	
        for (int current = 0; current < output.getRowDim(); current++)
        {
            for (int i = current+1; i < output.getRowDim(); i++)
            {
                if (output.getValue(i, current) != 0 && output.getValue(current, current) != 0)
                {
                    double scalar = (-1 * output.getValue(i, current))/(output.getValue(current, current));
                    output.rowOp1(current, i, scalar);
                }
                else if (output.getValue(current, current) == 0)
                {
                    output.rowOp2(current, i%rows); /** Swaps rows so that the i==j entry is nonzero. */
                }
            }
        }
        
        return output;
        
    }
    
    /**
     * Way faster way of finding the determinant. It row-reduces the matrix,
     * takes the product of the diagonal entries, and then multiplies that product
     * by -1 to the power of however many swaps (rowop2) were done.
     * @return 
     */
    public double experimentalFindDet(Matrix input)
    {
        double det = 1;
        int swaps = 0;
        
        Matrix reduced = input.rowReduce(input);
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                if (i == j)
                {
                    det *= reduced.getValue(i, j);
                }
            }
        }
        det *= Math.pow(-1, swaps);
        
        return det;
    }
    
    /**
     * Returns the determinant of a square matrix.
     * Uses cofactor expansion.
     * This method is recursive, so using it for matrices of dimensions greater
     * than 8x8 is ill-advised.
     * @return 
     */
    public double findDet()
    {
        if (rows != columns)
        {
            System.out.println("Sorry, findDet() works only on square matrices.");
            return 0;
        }
        else if (rows > 9)
        {
            System.out.println("Dimensions are too big, the program will take forever if you do that. ");
            return 0;
        }
        else
        {
            double det = 0;
            if (rows < 2 && columns < 2)
            {
                det = matrix[0][0];
            }
            else if (rows == 2 && columns == 2)
            {
                det = matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
            }
            else
            {
                int i = 0;
                for (int j = 0; j < columns; j++)
                {
                    det += (matrix[i][j]*Math.pow(-1, (i + j))*subMatrix(i, j).findDet());
                }
            }
            return det;
        }
    }
    
    /**
     * Returns the submatrix of a matrix formed by eliminating the ith row and jth column.
     * @param row
     * @param col
     * @return 
     */
    public Matrix subMatrix(int row, int col)
    {
        String values = "";

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                if (i != row && j != col)
                {
                    values += matrix[i][j] + " ";
                }
            }
        }
        
        Matrix sub = new Matrix(rows-1, columns-1, values);
        return sub;
    }
    
    public Matrix identity(int dimension, double entry)
    {
        /**
         * Creates nxn identity matrix
         */
        String values = "";
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (i==j)
                {
                    values += entry + " ";
                }
                else
                {
                    values += entry + " ";
                }
            }
        }
        Matrix identity = new Matrix(getRowDim(), getColumnDim(), values);
        return identity;
    }
    
    
    public String toString()
    {
        String string = "";
        DecimalFormat fmt = new DecimalFormat("0.##");
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                string += fmt.format(matrix[i][j]) + "\t";
            }
            string += "\n";
        }
        
        return string;
    }
}
