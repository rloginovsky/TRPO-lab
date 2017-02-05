/**
 * Created by Роман on 22.01.2017.
 */

import java.io.IOException;

/**
 * General interface of Boolean Storages, which might be used in the program.
 * @author Vyacheslav Yankovyi
 *
 */
public interface BooleanStorage {

    public void init(long limit) throws IOException;

    public long getLimit();

    public void write(long position, boolean value) throws IOException;

    public boolean read(long position) throws IOException;

    public boolean clean();
}