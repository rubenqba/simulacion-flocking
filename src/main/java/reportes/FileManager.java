package reportes;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ruben.bressler on 9/14/2016.
 */
public class FileManager {
    public static File crearArchivo(String directory, String nombre) throws IOException {
        Path root = Paths.get("target", "simulaciones", directory);
        if (Files.notExists(root)) {
            Files.createDirectories(root);
        }
        long runs = Files.list(root)
                .filter(f -> StringUtils.containsIgnoreCase(f.getFileName().toString(), nombre)).count();

        return Files.createFile(root.resolve(nombre + "_" + runs + ".txt")).toFile();
    }
}
