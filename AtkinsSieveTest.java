import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * тест решета Аткина
 * Implementation of filtering of Primes according to Sieve of Atkin algorithm.<br>
 * Calculation time of Sieve of Atkin is faster than N*(log(N)).<br>
 * <i>"Sieve of Atkin can theoretically compute primes up to N using O(N/log log N) operations"</i><br>
 */
public class AtkinsSieveTest {

    private static final long MAX_POSSIBLE_NUMBER = 4294967295L;
    private static final String COMMA = ",";

    public static void main(String[] args) {

        AtkinSieve atkinSieve = null;

//        FileInputStream fis = null;
        Scanner input = null;
        PrintStream ps = null;

//        if(args.length == 0)
//            throw new IllegalArgumentException("Input file name must be provided as App argument");
//
//        String inputFileName = args[0];

        try {


            System.out.print("Введите число:");
            PollardOptimised pollard = new PollardOptimised();
            Kattio io = new Kattio(System.in, System.out);
            String t  = io.getWord();
                BigInteger N = new BigInteger(t);
                pollard.factor(N, io);
                io.println();
                io.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


            long maxLimit = Long.valueOf(t);
            if((maxLimit <= 0) || (maxLimit >= MAX_POSSIBLE_NUMBER))
                throw new IllegalArgumentException("N must be positive and less than 4294967295");

            atkinSieve = new AtkinSieve(maxLimit);
            atkinSieve.filterPrimes();




            //Write primes
            ps = System.out;
            ps.println("Простые числа до заданного:");
            for (long i = 2; i < maxLimit; i++) {
                if (atkinSieve.isPrime(i)) {
                    ps.print(i + COMMA);
                }
            }

            ps.println();
            System.out.println();

            FermatsPrimalityTest fpt = new FermatsPrimalityTest();
            int data = Integer.valueOf(t);
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
                System.out.println("НЕ простое");
            }//tests to see whether or not the data can pass fermats primality
            //test to determine whether or not it is probably prime
            else if(fpt.fermat(data) == true)
            {
                System.out.println(t + " простое чило");
            }
            else
            {	//if data does not pass the primality test then it is composite
                System.out.println(t + " это НЕ простое число");
            }

//            Читать из файла

            /*
            fis = new FileInputStream(inputFileName);
            input = new Scanner(fis);

            ps = System.out;

            while(input.hasNextLong()){
                long maxLimit = input.nextLong();

                if((maxLimit <= 0) || (maxLimit >= MAX_POSSIBLE_NUMBER))
                    throw new IllegalArgumentException("N must be positive and less than 4294967295");

                atkinSieve = new AtkinSieve(maxLimit);
                atkinSieve.filterPrimes();

                //Write primes
                for (long i = 2; i < maxLimit; i++) {
                    if (atkinSieve.isPrime(i)) {
                        ps.print(i + COMMA);
                    }
                }

                ps.println();
            } */


        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally{
//            if(input != null){
//                input.close();
//            }
//
//            if(fis != null){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if(atkinSieve != null){
//                atkinSieve.close();
//            }
//        }

    }

}