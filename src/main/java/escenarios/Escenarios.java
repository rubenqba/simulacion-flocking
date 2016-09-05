package escenarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Movimiento;
import implementacion.MovimientoBoidIntegral;
import implementacion.MovimientoBoidMejorado;
import implementacion.MovimientoCuatroEsquinas;
import implementacion.TipoMovimiento;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import metricas.ObservadorAmbiente;
import metricas.ObservadorMejorAgente;
import metricas.ObservadorMetricas;
import metricas.ObservadorObjetivo;
import metricas.ObservadorVariablesEstado;
import metricas.ObservadorVelocidad;
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
            ObservadorMetricas o = new ObservadorMetricas(getConfiguracionModelo().getMaximaExtension(),
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
class ConfiguracionAgente {
    /** parámetros de los agentes **/
    private double cantidadObjetivos = 1;
    private double radioAgente = 3;
    private double rangoDeInteraccion = 25;
    private double radioObstaculos = 20;
    private double cantidadAgentes = 300;

    private VecindadObjetivos vecindad;

    public Map<String, Double> getAsMap() {
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("cantidadObjetivos", cantidadObjetivos);
        map.put("radioAgente", radioAgente);
        map.put("rangoDeInteraccion", rangoDeInteraccion);
        map.put("radioObstaculos", radioObstaculos);
        map.put("cantidadAgentes", cantidadAgentes);

        return map;
    }
}

@Builder
@Getter
class ConfiguracionModelo {
    private double parametroObstaculos = 1;
    private double c1 = 0.01;
    private double c2 = 0.2;
    private double c3 = 0.2;
    private double velMax = 4;
    private double zonaVirtual = 15;// separacion

    private double maximaExtension = 610;
    private double maxPolarizacion = 180;

    public Map<String, Double> getAsMap() {
        Map<String, Double> parametrosModelo = new LinkedHashMap<>();
        parametrosModelo.put("parametroObstaculos", parametroObstaculos);
        parametrosModelo.put("c1", c1);
        parametrosModelo.put("c2", c2);
        parametrosModelo.put("c3", c3);
        parametrosModelo.put("velMax", velMax);
        parametrosModelo.put("zonaVirtual", zonaVirtual);
        parametrosModelo.put("MaxExt", maximaExtension);
        parametrosModelo.put("MaxPol", maxPolarizacion);

        return parametrosModelo;
    }

    public MovimientoBoidMejorado buildMovimientoBoidMejorado() {
        MovimientoBoidMejorado mov = new MovimientoBoidMejorado();
        mov.setC1(c1);
        mov.setC2(c2);
        mov.setC3(c3);
        mov.setParametroObstaculos(parametroObstaculos);
        mov.setVelMax(velMax);
        mov.setZonaVirtual(zonaVirtual);
        return mov;
    }

    public MovimientoBoidIntegral buildMovimientoBoidIntegral(double epsilon) {
        MovimientoBoidIntegral mov = new MovimientoBoidIntegral(epsilon);
        mov.setC1(c1);
        mov.setC2(c2);
        mov.setC3(c3);
        mov.setParametroObstaculos(parametroObstaculos);
        mov.setVelMax(velMax);
        mov.setZonaVirtual(zonaVirtual);
        return mov;
    }

    public Movimiento buildMovimientoCuatroEsquinas() {
        MovimientoCuatroEsquinas movObjetivo = new MovimientoCuatroEsquinas();
        movObjetivo.setVelMax(velMax + 5);
        return movObjetivo;
    }

    public Movimiento buildMovimiento(TipoMovimiento m, Object ...params) {
        switch(m){
            case INTEGRAL:
                return buildMovimientoBoidIntegral((Double) params[0]);
            case MEJORADO:
                return buildMovimientoBoidMejorado();
            case CUATRO_ESQUINAS:
                return buildMovimientoCuatroEsquinas();
            default:
                return null;
        }
    }
}

class MovimientoBuilder {
    private static MovimientoBuilder builder;

    private double c1, c2, c3, velMax, parametroObstaculos, zonaVirtual;

    private MovimientoBuilder() {

    }

    public static MovimientoBuilder builder() {
        if (builder == null) {
            builder = new MovimientoBuilder();
        }
        return builder;
    }

    public MovimientoBuilder withC1(double c1) {
        this.c1 = c1;
        return this;
    }

    public MovimientoBuilder withC2(double c2) {
        this.c2 = c2;
        return this;
    }

    public MovimientoBuilder withC3(double c3) {
        this.c3 = c3;
        return this;
    }

    public MovimientoBuilder withVelocidadMaxima(double velocidad) {
        this.velMax = velocidad;
        return this;
    }

    public MovimientoBuilder withObstaculos(double obstaculo) {
        this.parametroObstaculos = obstaculo;
        return this;
    }

    public MovimientoBuilder withZonaVirtual(double zonaVirtual) {
        this.zonaVirtual = zonaVirtual;
        return this;
    }
}

class AgenteMovilBuilder {

    private static AgenteMovilBuilder builder;

    private ConfiguracionAgente configuracion;
    private AmbienteMovil ambiente;
    private Movimiento moviento;
    private double x;
    private double y;

    private AgenteMovilBuilder() {
    }

    public static AgenteMovilBuilder builder() {
        if (builder == null) {
            builder = new AgenteMovilBuilder();
        }
        return builder;
    }

    public Objetivo buildObjetivo() {
        Objetivo obj = new Objetivo(x, y, ambiente, moviento);
        if (configuracion.getVecindad() != null) {
            configuracion.getVecindad().add(obj);
        }
        return obj;
    }

    public AgenteMovilBuilder withX(double x) {
        this.x = x;
        return this;
    }

    public AgenteMovilBuilder withY(double y) {
        this.y = y;
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

    public Boid buildBoid() {
        Boid boid = new Boid(x, y, ambiente, moviento);
        if (configuracion != null) {
            boid.setObjetivos(configuracion.getVecindad());
            boid.setRangoInteraccion(configuracion.getRangoDeInteraccion());
            boid.setRadio(configuracion.getRadioAgente());
        }
        return boid;
    }
}
