/**
 * Created by Роман on 22.01.2017.
 *  {@linkplain IntegerArrayBooleanStorage} provides possibility to store large boolean arrays via using every integer in the array as container of 31 bits/flags.
 * This lets to calculate all prime numbers less than 4294967294 with usage of only ~500MB of RAM.
 */

import java.io.IOException;

public class IntegerArrayBooleanStorage implements BooleanStorage {

    private static final String LOG_MESSAGE_POSITION_OUT_OF_RANGE = "Value of \'position\' argument has to be in range [0..";
    private static final int INT_AVAILABLE_BITS = 31;
    private int[] storage;
    private long limit;
    private int size;

    private int[] masks = new int[INT_AVAILABLE_BITS];

    /**
     *
     */
    public IntegerArrayBooleanStorage() {
    }

    @Override
    public void init(long limit) throws IOException {
        this.limit = limit;

        this.size = (int)(limit/INT_AVAILABLE_BITS);

        storage = new int[size + 1];

        initMasks();

    }

    private void initMasks() {
        for (int i = 0; i < masks.length; i++) {
            masks[i] = (int) Math.pow(2, i);
        }
    }

    @Override
    public void write(long position, boolean value) throws IOException {

        if((position < 0) || (position > limit))
            throw new IllegalArgumentException(LOG_MESSAGE_POSITION_OUT_OF_RANGE+limit+"]");

        int cellPosition = (int)(position/INT_AVAILABLE_BITS);
        int relativeCell = (int)(position % INT_AVAILABLE_BITS);

        storage[cellPosition] = storage[cellPosition] | masks[relativeCell];

        if(!value)
            storage[cellPosition] = storage[cellPosition] ^ masks[relativeCell];
    }

    @Override
    public boolean read(long position) throws IOException {

        if((position < 0) || (position > limit))
            throw new IllegalArgumentException(LOG_MESSAGE_POSITION_OUT_OF_RANGE+limit+"]");

        int cellPosition = (int)(position/INT_AVAILABLE_BITS);
        int relativeCell = (int)(position % INT_AVAILABLE_BITS);

        boolean result = ((storage[cellPosition] & masks[relativeCell]) == masks[relativeCell]);

        return result;
    }

    @Override
    public boolean clean() {
        storage = null;

        return true;
    }

    @Override
    public long getLimit() {
        return limit;
    }

}