package archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jfree.data.xy.XYSeries;

import metricas.ObservadorMetricas;
import metricas.ObservadorVariablesEstado;

public class ManejadorArchivos {

    public void guardarEnArchivo(String nombre, Map<String, Double> parametrosAgente,
            Map<String, Double> parametrosModelo, ObservadorMetricas m) {
        NumberFormat format = new DecimalFormat("#0.0000");

        String tabulado = "\t";

        try {
            FileWriter file = new FileWriter(crearArchivo(nombre, m.getName()));
            BufferedWriter flujo = new BufferedWriter(file);

            flujo.write(StringUtils.capitalize(m.getName().replaceAll("_", " ")));
            flujo.newLine();
            flujo.newLine();

            /** Escribiendo los parametros del agente **/
            flujo.write("Parametros agente");
            flujo.newLine();

            Collection<String> keys = parametrosAgente.keySet();
            for (String key : keys) {
                flujo.write(key);
                flujo.write(tabulado);
                flujo.write("" + parametrosAgente.get(key));
                flujo.newLine();
            }

            flujo.newLine();
            flujo.newLine();

            /** Escribiendo los parametros del modelo **/
            flujo.write("Parametros del modelo");
            flujo.newLine();

            keys = parametrosModelo.keySet();
            for (String key : keys) {
                flujo.write(key);
                flujo.write(tabulado);
                flujo.write("" + parametrosModelo.get(key));
                flujo.newLine();
            }

            flujo.newLine();
            flujo.write("Iteracion");
            flujo.write(tabulado);
            flujo.write("Extension");
            flujo.write(tabulado);
            flujo.write("Polarizacion");
            flujo.write(tabulado);
            flujo.write("Factor colision");
            flujo.write(tabulado);
            flujo.write("Consistencia extension");
            flujo.write(tabulado);
            flujo.write("Consistencia polarizacion");
            flujo.write(tabulado);
            flujo.write("Calidad");
            flujo.newLine();
            flujo.newLine();

            XYSeries extension = m.getExtension();
            XYSeries polarizacion = m.getPolarizacion();
            XYSeries colision = m.getFactorColision();
            XYSeries consExt = m.getConsExtension();
            XYSeries consPol = m.getConsPolarizacion();
            XYSeries calidad = m.getCalidad();

            for (int ind = 0; ind < extension.getItemCount(); ind++) {
                flujo.newLine();
                flujo.write(ind + "");
                flujo.write(tabulado);

                flujo.write(format.format(extension.getY(ind)));
                flujo.write(tabulado);

                flujo.write(format.format(polarizacion.getY(ind)));
                flujo.write(tabulado);

                flujo.write(format.format(colision.getY(ind)));
                flujo.write(tabulado);

                flujo.write(format.format(consExt.getY(ind)));
                flujo.write(tabulado);

                flujo.write(format.format(consPol.getY(ind)));
                flujo.write(tabulado);

                flujo.write(format.format(calidad.getY(ind)));
                flujo.write(tabulado);
            }

            flujo.flush();
            flujo.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println();
        }

    }

    public void guardarEnArchivo(String nombre, Map<String, Double> parametrosAgente,
            Map<String, Double> parametrosModelo, ObservadorVariablesEstado m) {

        NumberFormat format = new DecimalFormat("#0.0000");
        String tabulado = "\t";

        try {
            FileWriter file = new FileWriter(crearArchivo(nombre, m.getName()));
            BufferedWriter flujo = new BufferedWriter(file);

            ArrayList<double[]> data = m.getDatos();
            double datos[] = null;

            flujo.write("[");

            int id = 0;
            for (id = 0; id < data.size() - 1; id++) {

                datos = data.get(id);
                flujo.write("[");

                int i;
                for (i = 0; i < datos.length - 1; i++) {
                    flujo.write(format.format(datos[i]) + ",");
                }
                flujo.write(format.format(datos[i]) + "];");
                flujo.newLine();
            }

            flujo.write("[");
            datos = data.get(id);
            for (id = 0; id < datos.length - 1; id++) {
                flujo.write(format.format(datos[id]) + ",");
            }
            flujo.write(format.format(datos[id]) + "]");
            flujo.write("]");

            flujo.flush();
            flujo.close();
        } catch (IOException e) {

        }

    }

    private File crearArchivo(String directory, String nombre) throws IOException {
        Path root = Paths.get("target", "simulaciones", directory);
        if (Files.notExists(root)) {
            Files.createDirectories(root);
        }
        long runs = Files.list(root)
                .filter(f -> StringUtils.containsIgnoreCase(f.getFileName().toString(), nombre)).count();

        return Files.createFile(root.resolve(nombre + "_" + runs + ".txt")).toFile();
    }

}
