import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Роман on 22.01.2017.
 * {@linkplain RandomAccessFileBooleanStorage} provides possibility to store large boolean arrays via using {@link RandomAccessFile}.
 * Might be useful for the computers with low RAM size, but this storage significantly increases execution time due to necessity to access HDD on every operation.

 */

public class RandomAccessFileBooleanStorage implements BooleanStorage{

    private static final String LOG_MESSAGE_POSITION_OUT_OF_RANGE = "Value of \'position\' argument has to be in range [0..";
    private static final String BOOLEAN_STORAGE_FILE_NAME = "booleanStorage.txt";
    private final RandomAccessFile rafStorage;
    private File storageFile;
    private long limit;

    public RandomAccessFileBooleanStorage() throws FileNotFoundException, IOException {
        super();
        this.storageFile = new File(BOOLEAN_STORAGE_FILE_NAME);
        this.rafStorage = new RandomAccessFile(storageFile, "rw");

    }

    @Override
    public void init(long limit) throws IOException {
        this.limit = limit;
        rafStorage.setLength(limit);
    }

    @Override
    public void write(long position, boolean value) throws IOException{

        if((position < 0) || (position > limit))
            throw new IllegalArgumentException(LOG_MESSAGE_POSITION_OUT_OF_RANGE+limit+"]");

        rafStorage.seek(position);
        rafStorage.writeBoolean(value);
    }

    @Override
    public boolean read(long position) throws IOException{

        if((position < 0) || (position > limit))
            throw new IllegalArgumentException(LOG_MESSAGE_POSITION_OUT_OF_RANGE+limit+"]");

        rafStorage.seek(position);
        return rafStorage.readBoolean();
    }

    @Override
    public boolean clean(){

        boolean deletionResult = false;
        try {
            rafStorage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            deletionResult = storageFile.delete();
        }

        return deletionResult;
    }

    @Override
    public long getLimit() {
        return limit;
    }

}
