package metricas;

import lombok.Getter;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

import reportes.Grafica;
import core.AmbienteMovil;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ObservadorMetricas implements ObservadorAmbiente {

    private Metrica metricas;
    private int iteraciones = 0;
    private double sumPolarizacion = 0.0;
    private double sumExtension = 0.0;
    private double sumConsPolarizacion = 0.0;
    private double sumConsExtension = 0.0;
    private double sumFactorColisiones = 0.0;
    private double sumCalidad = 0.0;
    private double sumFuncionObjetivo = 0.0;
    private int sumColisiones = 0;
    private boolean promedioAgregado = false;

    @Getter
    private XYSeries polarizacion, extension, factorColision, consPolarizacion, consExtension, calidad, funcionObjetivo, colisiones;

    private Grafica gPolarizacion;
    private Grafica gExtension;
    private Grafica gFactorColision;
    private Grafica gConsExtension;
    private Grafica gConsPolarizacion;
    private Grafica gCalidad;
    private Grafica gFuncionObjetivo;

    private double paramMaxExt = 500;
    private double paramMaxPol = 180;

    public ObservadorMetricas() {
        this(500, 180);
    }

    public ObservadorMetricas(double maximaExtension, double maxPolarizacion) {
        polarizacion = new XYSeries("Polarización");
        extension = new XYSeries("Extensión");
        factorColision = new XYSeries("Colisiones");
        consExtension = new XYSeries(" Consistencia-Extensión");
        consPolarizacion = new XYSeries(" Consistencia-Polarización");
        calidad = new XYSeries("Calidad");
        funcionObjetivo = new XYSeries("Función-Objetivo");
        colisiones = new XYSeries("Coliciones");

        paramMaxExt = maximaExtension;
        paramMaxPol = maxPolarizacion;
    }

    @Override
    public void observarAmbiente(AmbienteMovil ambiente) {
        if (metricas != null) {
            iteraciones++;
            metricas.calcularMetricas();

            sumPolarizacion += metricas.getPolarizacion();
            sumExtension += metricas.getExtension();
            sumConsExtension += metricas.getConsExtension();
            sumConsPolarizacion += metricas.getConsPolarizacion();
            sumFactorColisiones += metricas.getFactorColisiones();
            sumCalidad += metricas.getCalidad();
            sumFuncionObjetivo += metricas.getFuncionObjetivo();
            sumColisiones += metricas.getCantAgentesEnColision();

            polarizacion.add(iteraciones, metricas.getPolarizacion());
            extension.add(iteraciones, metricas.getExtension());
            factorColision.add(iteraciones, metricas.getFactorColisiones());
            consExtension.add(iteraciones, metricas.getConsExtension());
            consPolarizacion.add(iteraciones, metricas.getConsPolarizacion());
            calidad.add(iteraciones, metricas.getCalidad());
            funcionObjetivo.add(iteraciones, metricas.getFuncionObjetivo());
            colisiones.add(iteraciones, Integer.valueOf(metricas.getCantAgentesEnColision()));
        } else {
            iniciar(ambiente);
        }

    }

    public void addPromedios() {
        if (iteraciones > 0) {
            this.promedioAgregado = true;
            polarizacion.add(iteraciones + 1, sumPolarizacion / iteraciones);
            extension.add(iteraciones + 1, sumExtension / iteraciones);
            factorColision.add(iteraciones + 1, sumFactorColisiones / iteraciones);
            consExtension.add(iteraciones + 1, sumConsExtension / iteraciones);
            consPolarizacion.add(iteraciones + 1, sumConsPolarizacion / iteraciones);
            calidad.add(iteraciones + 1, sumCalidad / iteraciones);
            funcionObjetivo.add(iteraciones + 1, sumFuncionObjetivo / iteraciones);
        }
    }

    private void iniciar(AmbienteMovil ambiente) {
        metricas = new Metrica(ambiente);
        metricas.setMaxExt(paramMaxExt);
        metricas.setMaxAngulo(paramMaxPol);
        metricas.setPenalEnPolarizacion(paramMaxPol);
        metricas.setPenalEnExtension(paramMaxExt);

        iteraciones = 1;
        sumPolarizacion = 0.0;
        sumExtension = 0.0;
        sumConsPolarizacion = 0.0;
        sumConsExtension = 0.0;
        sumFactorColisiones = 0.0;
        sumCalidad = 0.0;
        sumFuncionObjetivo = 0.0;

    }

    public void mostrarReportes() {
        eliminarPromedio();

        gPolarizacion = new Grafica("Polarización", "Iteraciones", "Polarización");
        gExtension = new Grafica("Extensión", "Iteraciones", "Extensión");
        gFactorColision = new Grafica("Factor Colisiones", "Iteraciones", "FactorColisión");
        gConsExtension = new Grafica("Consistencia en la Extensión", "Iteraciones", "Consistencia Extension");
        gConsPolarizacion = new Grafica("Consistencia en la Polarización", "Iteraciones", "Consistencia Polarización");
        gCalidad = new Grafica("Calidad", "Iteraciones", "Calidad");
        gFuncionObjetivo = new Grafica("función objetivo", "Iteraciones", "Función objetivo");

        gPolarizacion.agregarSerie(polarizacion);
        gExtension.agregarSerie(extension);
        gFactorColision.agregarSerie(factorColision);
        gConsExtension.agregarSerie(consExtension);
        gConsPolarizacion.agregarSerie(consPolarizacion);
        gCalidad.agregarSerie(calidad);
        gFuncionObjetivo.agregarSerie(funcionObjetivo);

        gPolarizacion.crearReporte();
        gExtension.crearReporte();
        gFactorColision.crearReporte();
        gConsExtension.crearReporte();
        gConsPolarizacion.crearReporte();
        gCalidad.crearReporte();
        gFuncionObjetivo.crearReporte();
    }

    private void eliminarPromedio() {
        if (this.isPromedioAgregado()) {
            int ultimoIndice = polarizacion.getItemCount() - 1;

            polarizacion.remove(ultimoIndice);
            extension.remove(ultimoIndice);
            factorColision.remove(ultimoIndice);
            consExtension.remove(ultimoIndice);
            consPolarizacion.remove(ultimoIndice);
            calidad.remove(ultimoIndice);
            funcionObjetivo.remove(ultimoIndice);

            this.promedioAgregado = false;
        }

    }

    public void mostrarReportesCortos() {
        eliminarPromedio();

        Grafica graf1 = new Grafica("Métricas", "Iteraciones", "Métricas");
        Grafica graf2 = new Grafica("Métricas", "Iteraciones", "Métricas");

        graf1.agregarSerie(polarizacion);
        graf1.agregarSerie(extension);
        graf1.agregarSerie(funcionObjetivo);

        graf2.agregarSerie(consPolarizacion);
        graf2.agregarSerie(consExtension);
        graf2.agregarSerie(factorColision);
        graf2.agregarSerie(calidad);

        graf1.crearReporte();
        graf2.crearReporte();
    }

    public void mostrarDatos() {
        mostrarDatos(polarizacion, "Polarización");
        mostrarDatos(extension, "Extension");
        mostrarDatos(factorColision, "Colisiones ");
        mostrarDatos(consPolarizacion, "Consistencia Polarización");
        mostrarDatos(consExtension, "Consistencia Extensión");
        mostrarDatos(calidad, "Calidad");
        mostrarDatos(funcionObjetivo, "Función objetivo");
    }

    private void mostrarDatos(XYSeries datos, String nombre) {
        System.out.println();
        System.out.println(nombre);
        for (int i = 0; i < datos.getItemCount(); i++) {
            XYDataItem t = datos.getDataItem(i);

            System.out.println("X " + t.getXValue() + " Y " + t.getYValue());
        }
    }

    public boolean isPromedioAgregado() {
        return promedioAgregado;
    }

    @Override
    public String getName() {
        return "metricas";
    }

    @Override
    public void saveToFile(FileOutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.newLine();
        writer.write("Métricas de ejecución");
        writer.newLine();
        writer.write("====================================");
        writer.newLine();
        int iterations = polarizacion.getItemCount();
        writer.write(String.format("%-18s: %4d%n", "Cant. Iteraciones", iterations));
        writer.write(String.format("%-18s: %4.4f%n", "Avg. Extensión", sumExtension / iterations));
        writer.write(String.format("%-18s: %4.4f%n", "Avg. Polarización", getAvgWithoutNan(polarizacion)));
        writer.write(String.format("%-18s: %4d%n", "Cant. Colisiones", sumColisiones));
        writer.write(String.format("%-18s: %4.4f%n", "Avg. Factor Col.", sumFactorColisiones / iterations));
        writer.write(String.format("%-18s: %4.4f%n", "Avg. Cons. Extensión", sumConsExtension / iterations));
        writer.write(String.format("%-18s: %4.4f%n", "Avg. Cons. Polarización", sumConsPolarizacion / iterations));
        writer.write(String.format("%-18s: %4.4f%n", "Avg. Calidad", sumCalidad / iterations));
        writer.newLine();


        writer.write(String.format("%9s\t%9s\t%12s\t%10s\t%15s\t%20s\t%25s\t%8s",
                "Iteracion", "Extension", "Polarizacion", "Colisiones", "Factor-Colision", "Consistencia-Extension",
                "Consistencia-Polarizacion", "Calidad"));
        writer.newLine();

        for (int ind = 0; ind < extension.getItemCount(); ind++) {
            writer.write(String.format("%9d\t%9.4f\t%12.4f\t%10d\t%15.4f\t%20.4f\t%25.4f\t%8.4f", ind, extension.getY(ind).doubleValue(),
                    polarizacion.getY(ind).doubleValue(), colisiones.getY(ind), factorColision.getY(ind).doubleValue(),
                    consExtension.getY(ind).doubleValue(), consPolarizacion.getY(ind), calidad.getY(ind)));
            writer.newLine();
        }
        writer.write("====================================\n");
        writer.flush();
    }

    private double getAvgWithoutNan(XYSeries serie) {
        return serie.getItems().stream()
                .filter(d -> !Double.isNaN(((XYDataItem) d).getYValue()))
                .mapToDouble(d -> ((XYDataItem) d).getYValue())
                .average()
                .orElse(0);
    }
}
