package metricas;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

import Reporte.Grafica;
import core.AmbienteMovil;

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
    private boolean promedioAgregado = false;

    private XYSeries polarizacion, extension, factorColision, consPolarizacion, consExtension, calidad, funcionObjetivo;

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
        consExtension = new XYSeries(" Consistencia extensión");
        consPolarizacion = new XYSeries(" Consistencia polarización");
        calidad = new XYSeries("Calidad");
        funcionObjetivo = new XYSeries("Función objetivo");

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

            polarizacion.add(iteraciones, metricas.getPolarizacion());
            extension.add(iteraciones, metricas.getExtension());
            factorColision.add(iteraciones, metricas.getFactorColisiones());
            consExtension.add(iteraciones, metricas.getConsExtension());
            consPolarizacion.add(iteraciones, metricas.getConsPolarizacion());
            calidad.add(iteraciones, metricas.getCalidad());
            funcionObjetivo.add(iteraciones, metricas.getFuncionObjetivo());
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

        gPolarizacion = new Grafica("Polarización");
        gExtension = new Grafica("Extensión");
        gFactorColision = new Grafica("Factor Colisiones");
        gConsExtension = new Grafica("Consistencia en la Extensión");
        gConsPolarizacion = new Grafica("Consistencia en la Polarización");
        gCalidad = new Grafica("Calidad");
        gFuncionObjetivo = new Grafica("función objetivo");

        gPolarizacion.setEjeX("Iteraciones");
        gExtension.setEjeX("Iteraciones");
        gFactorColision.setEjeX("Iteraciones");
        gConsExtension.setEjeX("Iteraciones");
        gConsPolarizacion.setEjeX("Iteraciones");
        gCalidad.setEjeX("iteraciones");
        gFuncionObjetivo.setEjeX("Iteraciones");

        gPolarizacion.setEjeY("Polarización");
        gExtension.setEjeY("Extensión");
        gFactorColision.setEjeY("FactorColisión");
        gConsExtension.setEjeY("Consistencia Extension");
        gConsPolarizacion.setEjeY("Consistencia Polarización");
        gCalidad.setEjeY("Calidad");
        gFuncionObjetivo.setEjeY("Función objetivo");

        gPolarizacion.agregarPuntos(polarizacion);
        gExtension.agregarPuntos(extension);
        gFactorColision.agregarPuntos(factorColision);
        gConsExtension.agregarPuntos(consExtension);
        gConsPolarizacion.agregarPuntos(consPolarizacion);
        gCalidad.agregarPuntos(calidad);
        gFuncionObjetivo.agregarPuntos(funcionObjetivo);

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

        Grafica graf1 = new Grafica("Métricas");
        Grafica graf2 = new Grafica("Métricas");

        graf1.setEjeX("Iteraciones");
        graf2.setEjeX("Iteraciones");

        graf1.setEjeY("Métricas");
        graf2.setEjeY("Métricas");

        graf1.agregarPuntos(polarizacion);
        graf1.agregarPuntos(extension);
        graf1.agregarPuntos(funcionObjetivo);

        graf2.agregarPuntos(consPolarizacion);
        graf2.agregarPuntos(consExtension);
        graf2.agregarPuntos(factorColision);
        graf2.agregarPuntos(calidad);

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

    public XYSeries getPolarizacion() {
        return polarizacion;
    }

    public XYSeries getExtension() {
        return extension;
    }

    public XYSeries getFactorColision() {
        return factorColision;
    }

    public XYSeries getConsPolarizacion() {
        return consPolarizacion;
    }

    public XYSeries getConsExtension() {
        return consExtension;
    }

    public XYSeries getCalidad() {
        return calidad;
    }

    public boolean isPromedioAgregado() {
        return promedioAgregado;
    }

    @Override
    public String getName() {
        return "metricas";
    }

}
