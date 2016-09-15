package reportes;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ruben.bressler on 9/14/2016.
 */
public interface IReportFile {
    void saveToFile(FileOutputStream out) throws IOException;
}
