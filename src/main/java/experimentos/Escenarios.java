package experimentos;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Movimiento;
import implementacion.*;
import implementacion.functions.ExponentialFunction;
import implementacion.functions.IntegralFunction;
import implementacion.functions.SplineFunction;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import metricas.ObservadorAmbiente;
import metricas.ObservadorMejorAgente;
import metricas.ObservadorMetricas;
import metricas.ObservadorObjetivo;
import metricas.ObservadorVariablesEstado;
import metricas.ObservadorVelocidad;
import org.apache.commons.math3.analysis.UnivariateFunction;
import reportes.IReportFile;
import tiposagentes.Boid;
import tiposagentes.Objetivo;

@Data
@Builder
public class Escenarios {

    private boolean sensarEstado = false;
    private boolean calcularMetricas = true;
    private boolean guardarMetricasArchivo = true;
    private boolean mostrarSimulacion = false;
    private int iteraciones = 2000;

    private AmbienteMovil ambiente;

    private ConfiguracionAgente configuracionAgente;
    private ConfiguracionModelo configuracionModelo;

    public List<ObservadorAmbiente> getObservadores() {
        List<ObservadorAmbiente> observadores = new ArrayList<>();
        if (calcularMetricas) {
            ObservadorMetricas o = new ObservadorMetricas(getConfiguracionModelo().getMaxExtension(),
                    getConfiguracionModelo().getMaxPolarizacion());
            o.addPromedios();
            observadores.add(o);
        }

        if (sensarEstado) {
            observadores.addAll(Arrays.asList(new ObservadorVariablesEstado(), new ObservadorVelocidad(),
                    new ObservadorObjetivo(), new ObservadorMejorAgente()));
        }

        return observadores;
    }

}

@Builder
@Getter
class ConfiguracionAgente implements IReportFile {
    /**
     * parámetros de los agentes
     **/
    private double cantidadObjetivos = 1;
    private double radioAgente = 3;
    private double rangoDeInteraccion = 25;
    private double radioObstaculos = 20;
    private double cantidadAgentes = 300;
    private int cantidadValientes;
    private int cantidadCobardes;
    private VecindadObjetivos vecindad;

    @Override
    public void saveToFile(FileOutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        writer.write("Parámetros de Agentes");
        writer.newLine();
        writer.write("====================================");
        writer.newLine();
        writer.write(String.format("%-21s: %s%n", "Cantidad de Objetivos", cantidadObjetivos));
        writer.write(String.format("%-21s: %s%n", "Cantidad de Agentes", cantidadAgentes));
        writer.write(String.format("%-21s: %s%n", "Cantidad de Valientes", cantidadValientes));
        writer.write(String.format("%-21s: %s%n", "Cantidad de Cobardes", cantidadCobardes));
        writer.write(String.format("%-21s: %s%n", "Radio de Agente", radioAgente));
        writer.write(String.format("%-21s: %s%n", "Rango de Interacción", rangoDeInteraccion));
        writer.write(String.format("%-21s: %s%n", "Radio de Obstaculos", radioObstaculos));
        writer.write("====================================");
        writer.newLine();
        writer.newLine();
        writer.flush();
    }
}

@Builder
@Getter
class ConfiguracionModelo implements IReportFile {
    private double obstaculos = 1;
    private double c1 = 0.01;
    private double c1Min = 0.01;
    private double c2 = 0.2;
    private double c3 = 0.2;
    private double velMax = 4;
    private double zonaVirtual = 15;// separacion

    private double maxExtension = 610;
    private double maxPolarizacion = 180;

    @Override
    public void saveToFile(FileOutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        writer.write("Parámetros del Modelo");
        writer.newLine();
        writer.write("====================================");
        writer.newLine();
        writer.write(String.format("%-18s: %4.4f%n", "C1", c1));
        writer.write(String.format("%-18s: %4.4f%n", "C1Min", c1Min));
        writer.write(String.format("%-18s: %4.4f%n", "C2", c2));
        writer.write(String.format("%-18s: %4.4f%n", "C3", c3));
        writer.write(String.format("%-18s: %4.4f%n", "Velocidad Máx.", velMax));
        writer.write(String.format("%-18s: %4.4f%n", "Zona virtual", zonaVirtual));
        writer.write(String.format("%-18s: %4.4f%n", "Obstáculos", obstaculos));
        writer.write(String.format("%-18s: %4.4f%n", "Extensión Máx.", maxExtension));
        writer.write(String.format("%-18s: %4.4f%n", "Polarización Máx.", maxPolarizacion));
        writer.write("====================================");
        writer.newLine();
        writer.flush();
    }

    public MovimientoBoidMejorado buildMovimientoBoidMejorado(UnivariateFunction function) {
        MovimientoBoidMejorado mov = new MovimientoBoidMejorado(function);
        mov.setC1(c1);
        mov.setC1Min(c1Min);
        mov.setC2(c2);
        mov.setC3(c3);
        mov.setParametroObstaculos(obstaculos);
        mov.setVelMax(velMax);
        mov.setZonaVirtual(zonaVirtual);
        return mov;
    }


    public Movimiento buildMovimientoCuatroEsquinas() {
        MovimientoCuatroEsquinas movObjetivo = new MovimientoCuatroEsquinas();
        movObjetivo.setVelMax(velMax + 1);
        return movObjetivo;
    }

    public Movimiento buildMovimiento(TipoMovimiento m) {
        switch (m) {
            case INTEGRAL:
                return buildMovimientoBoidMejorado(new IntegralFunction());
            case MEJORADO:
                return buildMovimientoBoidMejorado(new ExponentialFunction());
            case CUATRO_ESQUINAS:
                return buildMovimientoCuatroEsquinas();
            case SPLINE:
                return buildMovimientoBoidMejorado(new SplineFunction());
            default:
                return null;
        }
    }
}


class AgenteMovilBuilder {

    private static AgenteMovilBuilder builder;

    private ConfiguracionAgente configuracion;
    private AmbienteMovil ambiente;
    private Movimiento moviento;
    private boolean courage;

    public static AgenteMovilBuilder builder() {
        if (builder == null) {
            builder = new AgenteMovilBuilder();
        }
        return builder;
    }

    public Objetivo buildObjetivo(double x, double y) {
        Objetivo obj = new Objetivo(x, y, ambiente, moviento);
        if (configuracion.getVecindad() != null) {
            configuracion.getVecindad().add(obj);
        }
        return obj;
    }

    public AgenteMovilBuilder withCourage(boolean courage) {
        this.courage = courage;
        return this;
    }

    public AgenteMovilBuilder withConfiguracionAgente(ConfiguracionAgente config) {
        this.configuracion = config;
        return this;
    }

    public AgenteMovilBuilder withAmbiente(AmbienteMovil ambiente) {
        this.ambiente = ambiente;
        return this;
    }

    public AgenteMovilBuilder withMovimiento(Movimiento movimiento) {
        this.moviento = movimiento;
        return this;
    }

    public Boid buildBoid(double x, double y) {
        Boid boid = new Boid(x, y, ambiente, moviento);
        if (configuracion != null) {
            boid.setObjetivos(configuracion.getVecindad());
            boid.setRangoInteraccion(configuracion.getRangoDeInteraccion());
            boid.setRadio(configuracion.getRadioAgente());
            boid.setCourage(courage);
        }
        return boid;
    }
}
