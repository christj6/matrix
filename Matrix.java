
import java.util.*;
import java.text.DecimalFormat;

@SuppressWarnings("unchecked")

/**
 * 
 * @author Jack Christiansen
 */


public class Matrix

{
    private int rows; //number of rows
    private int columns; //number of columns
    private double[][] matrix; //the array holding the values
    
    
    
    /**
     * Creates an empty Matrix that is row x col in size.
     * @param row   the first dimension of the Matrix
     * @param col   the second dimension of the Matrix
     */
    public Matrix(int row, int col)
    {
        rows = row;
        columns = col;
        matrix = new double[rows][columns];
    }
    
    /**
     * Matrix constructor that creates and adds values to the Matrix
     * @param row       the first dimension of the Matrix (the amount of rows)
     * @param col       the second dimension of the Matrix (the amount of columns)
     * @param values    the string of values to add to the Matrix, the values are separated by a space
     */
    public Matrix(int row, int col, String values)
    {
        rows = row;
        columns = col;
        matrix = new double[rows][columns];
        
        Scanner scan = new Scanner(values);
        scan.useDelimiter(" "); //the string values are separated by spaces
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                matrix[i][j] = scan.nextDouble(); //adding them to the Matrix
            }
        }
    }
     
    
    /** Constructor for a square (NxN) matrix and values to add to it.
     * @param rowAndCol     The size dimension of the Matrix
     * @param values        The values to be added to the Matrix
     */
    public Matrix(int rowAndCol, String values)
    {
        rows = rowAndCol;
        columns = rowAndCol;
        matrix = new double[rows][columns];
        
        Scanner scan = new Scanner(values);
        scan.useDelimiter(" "); //the values are separated by spaces 
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                matrix[i][j] = scan.nextDouble();
            }
        }
    }
    
    /**Gets the value in the Matrix at [row][col]
     * @param row       the row we want the value at
     * @param col       the column we want the value at
     * @return          double that is the value at the inputed location
     */
    public double getValue(int row, int col)
    {
        return matrix[row][col];
    }
    
    /**Gets the value in the Matrix at the same location in the row as the column
     * @param rowAndCol     the location in both the row and column 
     * @return              double that is the value in the Matrix at [rowAndCol][rowAndCol]
     */
    public double getValue(int rowAndCol)
    {
        return matrix[rowAndCol][rowAndCol];
    }
    
    /**Gets the amount of rows
     * @return      int that is the amount of rows in the Matrix
     */
    public int getRowDim()
    {
        return rows;
    }
    
    /**Gets the amount of columns
     * @return      int that is the amount of columns in the Matrix
     */
    public int getColumnDim()
    {
        return columns;
    }
    
    /**
     * Multiplying a row by a scalar and then adding that result to another row. One of the Matrix row operations (needed for finding determinants)
     * @param firstRow      the row to multiply by the scalar (double)
     * @param secondRow     the row in which we add the result from the first row * the scalar
     * @param scalar        a double in which to multiply all the values from the first row.
     */
    public void rowOp1(int firstRow, int secondRow, double scalar)
    {   //only 1 for loop so it minimizes the run time 
        double[] temp = new double[rows];
        for (int i = 0; i < rows; i++)
        {
            temp[i] = scalar*matrix[firstRow][i];       //multiplying the scalar by the first row
            matrix[secondRow][i] += temp[i];            //adding that value stored in temp to the same location in the second row
        }
    }
    
    /**
     * Swapping of 2 rows in the Matrix (used while finding determinants)
     * @param firstRow      row to swap
     * @param secondRow     other row to swap with firstRow
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
     * Multiplying all the values in a row by a double
     * @param row       the row to multiply 
     * @param scalar    the scalar double to multiply all the values by
     */
    public void rowOp3(int row, double scalar)
    {
        for (int i = 0; i < rows; i++)
        {
            matrix[row][i] *= scalar;
        }
    }
    
    
    /**
     * Adds one Matrix to another and returns the resulting Matrix
     * @param other     the other Matrix we want to add
     * @return          the resulting Matrix
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
     * Subtracts a Matrix from this Matrix
     * @param other     the other Matrix to subtract from this one
     * @return          the resulting Matrix
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
     * Multiplies a Matrix by the double
     * @param scalar    the scalar to multiply the Matrix by
     * @return          the resulting Matrix
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
     * Multiplies a Matrix by another Matrix
     * @param other     the other matrix to multiply by
     * @return          the resulting Matrix
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
     * @return      double that is the value of the determinant
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
     * @return      the double determinant value
     */
    public double findDet()
    {
        if (rows != columns) //checks if it is a SQUARE matrix
        {
            System.out.println("Sorry, findDet() works only on square matrices.");
            return 0;
        }
        else if (rows > 9) //checks if the the amount of rows is too high that the recursive function will take too long
        {
            System.out.println("Dimensions are too big, the program will take forever if you do that. ");
            return 0;
        }
        else
        {
            double det = 0;
            if (rows < 2 && columns < 2)
            {
                det = matrix[0][0]; //getting the determinant (base case)
            }
            else if (rows == 2 && columns == 2)
            {
                det = matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0]; //getting the determinant
            }
            else
            {
                int i = 0;
                for (int j = 0; j < columns; j++)
                {
                    det += (matrix[i][j]*Math.pow(-1, (i + j))*subMatrix(i, j).findDet()); //the recursive call 
                }
            }
            return det;
        }
    }
    
    /**
     * Returns the submatrix of a matrix formed by eliminating the ith row and jth column.
     * @param row       the row we want to eliminate
     * @param col       the column we want to eliminate
     * @return          the resulting Matrix
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
    
    /**
     * Returns the identity Matrix that is the size dimension x dimension
     * @param dimension     the size we want the Matrix
     * @param entry         value we add to the specific diagonals across the Matrix
     * @return              the identity Matrix
     */
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
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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
