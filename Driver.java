

/**
 * 
 * @author Jack Christiansen
 */
public class Driver
{
    public static void main(String[] args)
    {   
        
        Matrix mat = new Matrix(3, 3, "3 5 9 2 8 4 1 3 2");
        
        System.out.println(mat);
        
        System.out.println("det: " + mat.experimentalFindDet());
        System.out.println("det: " + mat.findDet());

	//mat.rowReduce();
	System.out.println(mat);
    }
}
