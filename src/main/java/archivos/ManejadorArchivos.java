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

import lombok.Cleanup;
import org.apache.commons.lang3.StringUtils;
import org.jfree.data.xy.XYSeries;

import metricas.ObservadorMetricas;
import metricas.ObservadorVariablesEstado;

public class ManejadorArchivos {

    public void guardarEnArchivo(String nombre, Map<String, Double> parametrosAgente,
            Map<String, Double> parametrosModelo, ObservadorMetricas m) {

        String tabulado = "\t";

        try {
            @Cleanup
            BufferedWriter flujo = new BufferedWriter(new FileWriter(crearArchivo(nombre, m.getName())));

            flujo.write(StringUtils.capitalize(m.getName().replaceAll("_", " ")));
            flujo.newLine();
            flujo.newLine();

            /** Escribiendo los parametros del agente **/
            flujo.write("Parametros agente");
            flujo.newLine();

            for (String key : parametrosAgente.keySet()) {
                flujo.write(String.format("%s\t%s", key, parametrosAgente.get(key)));
                flujo.newLine();
            }

            flujo.newLine();
            flujo.newLine();

            /** Escribiendo los parametros del modelo **/
            flujo.write("Parametros del modelo");
            flujo.newLine();

            for (String key : parametrosModelo.keySet()) {
                flujo.write(String.format("%s\t%s", key, parametrosModelo.get(key)));
                flujo.newLine();
            }

            flujo.newLine();
            flujo.write(String.format("%9s\t%9s\t%12s\t%10s\t%15s\t%20s\t%25s\t%8s",
                    "Iteracion", "Extension", "Polarizacion", "Colisiones", "Factor-Colision", "Consistencia-Extension",
                    "Consistencia-Polarizacion", "Calidad"));
            flujo.newLine();

            XYSeries extension = m.getExtension();
            XYSeries polarizacion = m.getPolarizacion();
            XYSeries colisiones = m.getColisiones();
            XYSeries factorColision = m.getFactorColision();
            XYSeries consExt = m.getConsExtension();
            XYSeries consPol = m.getConsPolarizacion();
            XYSeries calidad = m.getCalidad();

            NumberFormat format = new DecimalFormat("#0.0000");
            for (int ind = 0; ind < extension.getItemCount(); ind++) {
                flujo.write(String.format("%9d\t%9.4f\t%12.4f\t%10d\t%15.4f\t%20.4f\t%25.4f\t%8.4f", ind, extension.getY(ind).doubleValue(),
                        polarizacion.getY(ind).doubleValue(), colisiones.getY(ind), factorColision.getY(ind).doubleValue(),
                        consExt.getY(ind).doubleValue(), consPol.getY(ind), calidad.getY(ind)));
                flujo.newLine();
            }
            flujo.flush();
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
