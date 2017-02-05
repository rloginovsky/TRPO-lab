import java.io.IOException;
/**
 * Created by Роман on 22.01.2017.
 */

class AtkinSieve {

    private final long limit;
    private final long sqrtLimit;
    private final BooleanStorage booleanStorage;

    public AtkinSieve(long limit) throws IOException {
        super();
        this.limit = limit;
        this.sqrtLimit = (int) Math.sqrt(limit) + 1;

        this.booleanStorage = new IntegerArrayBooleanStorage();
        this.booleanStorage.init(limit);
    }

    public void filterPrimes() throws IOException {

        double startTime = System.currentTimeMillis();

        // Поиск простых
        int[] sequence = { 2, 4 };
        int index = 0;
        long k1 = 0, k = 0;

        double xUpper = Math.sqrt(limit / 4) + 1;
        long x = 1;
        long y = 0;

        while (x < xUpper) {
            index = 0;
            k1 = 4 * x * x;
            y = 1;
            if (x % 3 == 0) {
                while (true) {
                    k = k1 + y * y;
                    if (k >= limit) {
                        break;
                    }
                    booleanStorage.write(k, !booleanStorage.read(k));
                    y += sequence[(++index & 1)];
                }
            } else {
                while (true) {
                    k = k1 + y * y;
                    if (k >= limit) {
                        break;
                    }
                    booleanStorage.write(k, !booleanStorage.read(k));
                    y += 2;
                }
            }
            x++;
        }

        xUpper = Math.sqrt(limit / 3) + 1;
        x = 1;
        y = 0;

        while (x < xUpper) {
            index = 1;
            k1 = 3 * x * x;
            y = 2;
            while (true) {
                k = k1 + y * y;
                if (k >= limit) {
                    break;
                }
                booleanStorage.write(k, !booleanStorage.read(k));
                y += sequence[(++index & 1)];
            }
            x += 2;
        }

        xUpper = (int) Math.sqrt(limit);
        x = 1;
        y = 0;

        while (x < xUpper) {
            k1 = 3 * x * x;
            if ((x & 1) == 0) {
                y = 1;
                index = 0;
            } else {
                y = 2;
                index = 1;
            }
            while (y < x) {
                k = k1 - y * y;
                if (k < limit) {
                    booleanStorage.write(k, !booleanStorage.read(k));
                }
                y += sequence[(++index & 1)];
            }
            x++;
        }

        booleanStorage.write(2, true);
        booleanStorage.write(3, true);

        for (long n = 5; n <= sqrtLimit; n++) {
            if (booleanStorage.read(n)) {
                long n2 = n * n;
                for (k = n2; k < limit; k += n2) {
                    booleanStorage.write(k, false);
                }
            }
        }

        double finishTime = System.currentTimeMillis();

//		System.out.println("Предел: " + limit + "; Время выполнения: " + (finishTime - startTime) / 1000d);

    }

    public boolean isPrime(long number) throws IOException{
        return booleanStorage.read(number);
    }

    public void close() {
        this.booleanStorage.clean();
    }
}