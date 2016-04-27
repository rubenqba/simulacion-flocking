package escenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import Vecindades.VecindadObjetivos;
import archivos.ManejadorArchivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;
import metricas.ObservadorAmbiente;
import metricas.ObservadorMetricas;
import metricas.ObservadorVariablesEstado;

public class Escenario3Test {

    // @Test
    public void experimento1() throws InterruptedException {

        Escenarios e = Escenarios.builder()
                .calcularMetricas(true)
                .sensarEstado(false)
                .guardarMetricasArchivo(true)
                .mostrarSimulacion(false)
                .iteraciones(2000)
                .configuracionAgente(ConfiguracionAgente.builder()
                        .cantidadAgentes(300)
                        .cantidadObjetivos(1)
                        .radioAgente(3)
                        .rangoDeInteraccion(25)
                        .radioObstaculos(20)
                        .vecindad(new VecindadObjetivos())
                        .build())
                .configuracionModelo(ConfiguracionModelo.builder()
                        .c1(0.01)
                        .c2(0.2)
                        .c3(0.2)
                        .zonaVirtual(15)
                        .parametroObstaculos(1)
                        .velMax(4)
                        .maximaExtension(610)
                        .maxPolarizacion(180)
                        .build())
                .ambiente(new AmbienteMovil())
                .build();

        assertThat(e, notNullValue());
        assertThat(e.getConfiguracionAgente(), notNullValue());
        assertThat(e.getConfiguracionAgente().getCantidadAgentes(), equalTo(300d));
        assertThat(e.getConfiguracionAgente().getRadioAgente(), equalTo(3d));
        assertThat(e.getConfiguracionModelo(), notNullValue());
        assertThat(e.getConfiguracionModelo().getC1(), equalTo(0.01));
        assertThat(e.getConfiguracionModelo().getC3(), equalTo(0.2));
        assertThat(e.getConfiguracionModelo().getVelMax(), equalTo(4d));

        for (int i = 0; i < e.getConfiguracionAgente().getCantidadObjetivos(); i++) {
            AgenteMovilBuilder.builder()
                    .withAmbiente(e.getAmbiente())
                    .withX(300)
                    .withY(100)
                    .withConfiguracionAgente(e.getConfiguracionAgente())
                    .withMovimiento(e.getConfiguracionModelo().buildMovimientoCuatroEsquinas())
                    .buildObjetivo();
        }

        assertThat(e.getAmbiente(), notNullValue());
        assertThat(e.getAmbiente().getAgentes(), hasSize(1));

        Vector infIzq = new Vector(0, 0);
        Vector supDer = new Vector(700, 700);

        for (int i = 0; i < e.getConfiguracionAgente().getCantidadAgentes(); i++) {
            Vector v = Util.aleatorioEnArea(infIzq, supDer);
            AgenteMovilBuilder.builder()
                    .withAmbiente(e.getAmbiente())
                    .withX(v.get(0))
                    .withY(v.get(1))
                    .withMovimiento(e.getConfiguracionModelo().buildMovimientoBoidMejorado())
                    .withConfiguracionAgente(e.getConfiguracionAgente())
                    .buildBoid();
        }

        assertThat(e.getAmbiente().getAgentes(), hasSize(new Double(
                e.getConfiguracionAgente().getCantidadAgentes() + e.getConfiguracionAgente().getCantidadObjetivos())
                        .intValue()));

        Simulacion s = Simulacion.builder()
                .ambiente(e.getAmbiente())
                .delaySimulacion(0)
                .iteraciones(e.getIteraciones())
                .observadores(e.getObservadores())
                .simulacionActiva(true)
                .build();

        assertThat(e.getAmbiente().getAgentes(), hasSize(new Double(
                e.getConfiguracionAgente().getCantidadAgentes() + e.getConfiguracionAgente().getCantidadObjetivos())
                        .intValue()));
        assertThat(s, notNullValue());
        assertThat(s.getAmbiente().getAgentes(), hasSize(new Double(
                e.getConfiguracionAgente().getCantidadAgentes() + e.getConfiguracionAgente().getCantidadObjetivos())
                        .intValue()));

        Thread t = new Thread(s);
        t.start();
        t.join();

        ManejadorArchivos manejador = new ManejadorArchivos();
        for (ObservadorAmbiente observer : s.getObservadores()) {
            if (ObservadorMetricas.class.isAssignableFrom(observer.getClass())) {
                manejador.guardarEnArchivo("Escenario 3", e.getConfiguracionAgente().getAsMap(),
                        e.getConfiguracionModelo().getAsMap(), (ObservadorMetricas) observer);
            } else if (ObservadorVariablesEstado.class.isAssignableFrom(observer.getClass())) {
                manejador.guardarEnArchivo("Escenario 3", e.getConfiguracionAgente().getAsMap(),
                        e.getConfiguracionModelo().getAsMap(), (ObservadorVariablesEstado) observer);
            }
        }
    }

    public void experimento(String name, Integer agentes, Double c1, Double c2, Double c3) throws InterruptedException {

        System.out.println(name);
        Escenarios e = Escenarios.builder()
                .calcularMetricas(true)
                .sensarEstado(false)
                .guardarMetricasArchivo(true)
                .mostrarSimulacion(false)
                .iteraciones(2000)
                .configuracionAgente(ConfiguracionAgente.builder()
                        .cantidadAgentes(agentes)
                        .cantidadObjetivos(1)
                        .radioAgente(3)
                        .rangoDeInteraccion(25)
                        .radioObstaculos(20)
                        .vecindad(new VecindadObjetivos())
                        .build())
                .configuracionModelo(ConfiguracionModelo.builder()
                        .c1(c1)
                        .c2(c2)
                        .c3(c3)
                        .zonaVirtual(15)
                        .parametroObstaculos(1)
                        .velMax(4)
                        .maximaExtension(610)
                        .maxPolarizacion(180)
                        .build())
                .ambiente(new AmbienteMovil())
                .build();

        assertThat(e, notNullValue());
        assertThat(e.getConfiguracionAgente(), notNullValue());
        assertThat(e.getConfiguracionAgente().getCantidadAgentes(), equalTo(agentes.doubleValue()));
        assertThat(e.getConfiguracionAgente().getRadioAgente(), equalTo(3d));
        assertThat(e.getConfiguracionModelo(), notNullValue());
        assertThat(e.getConfiguracionModelo().getC1(), equalTo(c1));
        assertThat(e.getConfiguracionModelo().getC3(), equalTo(c2));
        assertThat(e.getConfiguracionModelo().getVelMax(), equalTo(4d));

        for (int i = 0; i < e.getConfiguracionAgente().getCantidadObjetivos(); i++) {
            AgenteMovilBuilder.builder()
                    .withAmbiente(e.getAmbiente())
                    .withX(300)
                    .withY(100)
                    .withConfiguracionAgente(e.getConfiguracionAgente())
                    .withMovimiento(e.getConfiguracionModelo().buildMovimientoCuatroEsquinas())
                    .buildObjetivo();
        }

        assertThat(e.getAmbiente(), notNullValue());
        assertThat(e.getAmbiente().getAgentes(), hasSize(1));

        Vector infIzq = new Vector(0, 0);
        Vector supDer = new Vector(700, 700);

        for (int i = 0; i < e.getConfiguracionAgente().getCantidadAgentes(); i++) {
            Vector v = Util.aleatorioEnArea(infIzq, supDer);
            AgenteMovilBuilder.builder()
                    .withAmbiente(e.getAmbiente())
                    .withX(v.get(0))
                    .withY(v.get(1))
                    .withMovimiento(e.getConfiguracionModelo().buildMovimientoBoidMejorado())
                    .withConfiguracionAgente(e.getConfiguracionAgente())
                    .buildBoid();
        }

        assertThat(e.getAmbiente().getAgentes(), hasSize(new Double(
                e.getConfiguracionAgente().getCantidadAgentes() + e.getConfiguracionAgente().getCantidadObjetivos())
                        .intValue()));

        Simulacion s = Simulacion.builder()
                .ambiente(e.getAmbiente())
                .delaySimulacion(0)
                .iteraciones(e.getIteraciones())
                .observadores(e.getObservadores())
                .simulacionActiva(true)
                .build();

        assertThat(e.getAmbiente().getAgentes(), hasSize(new Double(
                e.getConfiguracionAgente().getCantidadAgentes() + e.getConfiguracionAgente().getCantidadObjetivos())
                        .intValue()));
        assertThat(s, notNullValue());
        assertThat(s.getAmbiente().getAgentes(), hasSize(new Double(
                e.getConfiguracionAgente().getCantidadAgentes() + e.getConfiguracionAgente().getCantidadObjetivos())
                        .intValue()));

        Thread t = new Thread(s);
        t.start();
        t.join();

        ManejadorArchivos manejador = new ManejadorArchivos();
        for (ObservadorAmbiente observer : s.getObservadores()) {
            if (ObservadorMetricas.class.isAssignableFrom(observer.getClass())) {
                manejador.guardarEnArchivo(name, e.getConfiguracionAgente().getAsMap(),
                        e.getConfiguracionModelo().getAsMap(), (ObservadorMetricas) observer);
            } else if (ObservadorVariablesEstado.class.isAssignableFrom(observer.getClass())) {
                manejador.guardarEnArchivo(name, e.getConfiguracionAgente().getAsMap(),
                        e.getConfiguracionModelo().getAsMap(), (ObservadorVariablesEstado) observer);
            }
        }
    }

    @Test
    public void testAurelio() throws InterruptedException {
        List<Integer> cantidadAgentes = Arrays.asList(100, 150, 200, 250, 300, 350, 400, 450, 500);
        List<Double> valoresC1 = Arrays.asList(-.9, -.8, -.7, -.6, -.5, -.4, -.3, -.2, -.1, 0d, .1, .2, .3, .4, .5, .6,
                .7, .8, .9);
        List<Double> valoresC3Min = IntStream.range(1, 20).mapToObj(i -> Integer.valueOf(i).doubleValue())
                .collect(Collectors.toList());
        List<Double> valoresC3Max = Arrays.asList(-.1, .1, .3, .5, .7, .9, 1.1, 1.3, 1.5, 1.7, 1.9, 2.1, 2.3, 2.5, 2.7,
                2.9, 3.1, 3.3, 3.5);

        for (int j = 0; j < 10; j++) {
            for (Integer agentes : cantidadAgentes) {
                for (int i = 0; i < valoresC1.size(); i++) {
                    Double c1 = valoresC1.get(i);
                    Double c2 = .2;
                    Double c3 = valoresC3Max.get(i);
                    if (c3 >= (2 + 2 * c1 + c2)) {
                        c3 = (2 + 2 * c1 + c2) / 2;
                    }
                    experimento(String.format("Prueba_%d_%1.2f_%1.2f_%1.2f", agentes.intValue(),
                            c1.doubleValue(), c2.doubleValue(), c3.doubleValue()), agentes, c1, c2, c3);
                }
            }
        }
    }

}
