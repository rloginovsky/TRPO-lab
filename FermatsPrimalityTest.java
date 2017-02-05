/*
 *  Implements Fermats Primality Test to return whether or not a number is
 *  prime.
 */
import java.math.BigInteger;
import java.util.*;

public class FermatsPrimalityTest
{
    public static boolean fermat(int data)
    {	//creates a random integer to test against the the congruency of
        //1mod the possible prime
        Random rand = new Random();
        int a;
        BigInteger A;
        BigInteger DATA;
        BigInteger isAPRIME;
        //performs a number of tests in case a carmichael number is generated
        for(int i = 0; i < 10; i++)
        {	//generate the a in a^p-1
            a = 2 + rand.nextInt(data - 2);
            //converts the data to BigInteger because numbers generated during
            //the implementation of fermats little theorem a larger than what
            //ordinary primitive data types can handle
            DATA = BigInteger.valueOf(data);
            A = BigInteger.valueOf(a);
            //a^p-1 congruent to 1 mod p
            isAPRIME = A.modPow(DATA.subtract(BigInteger.ONE), DATA);

            if(!isAPRIME.equals(BigInteger.ONE))
                return false;
        }

        return true;
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int input;

        System.out.println("Input the number of test cases you would like to run\n"
                + "followed by that number of integers to be tested.");

        input = sc.nextInt();
        //array to hold test cases created to be the size of the amount of
        //test cases to be ran
        int a[] = new int[input];

        for (int i = 0; i < input; i ++)
        {
            a[i] = sc.nextInt();
        }
        //loop through the test cases
        for(int data : a)
        {	//weeds out any data that is 1, 2, 3 or divisible by 2
            if(data ==1)
            {
                System.out.println("Not prime");
            }
            else if(data == 2 || data == 3)
            {
                System.out.println("Prime");
            }
            else if(data % 2 == 0)
            {
                System.out.println("Not prime");
            }//tests to see whether or not the data can pass fermats primality
            //test to determine whether or not it is probably prime
            else if(fermat(data) == true)
            {
                System.out.println("Prime");
            }
            else
            {	//if data does not pass the primality test then it is composite
                System.out.println("Not prime");
            }
        }
    }
}