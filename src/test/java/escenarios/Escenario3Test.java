package escenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import implementacion.TipoMovimiento;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Vecindades.VecindadObjetivos;
import archivos.ManejadorArchivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;
import lombok.AllArgsConstructor;
import metricas.ObservadorAmbiente;
import metricas.ObservadorMetricas;
import metricas.ObservadorVariablesEstado;
import reportes.FileManager;

public class Escenario3Test {

    @AllArgsConstructor
    private class Parameters {
        @Getter
        private double c1, c2, c3Min, c3Max;
    }

    private List<Parameters> params;
    private List<Integer> cantidadAgentes;

    @Before
    public void setup() {
        params = Arrays.asList(
                new Parameters(-0.9, 0.2, -0.1, -0.1),
                new Parameters(-0.8, 0.2, -0.1, 0.1),
                new Parameters(-0.7, 0.2, -0.1, 0.3),
                new Parameters(-0.6, 0.2, -0.1, 0.5),
                new Parameters(-0.5, 0.2, -0.1, 0.7),
                new Parameters(-0.4, 0.2, -0.1, 0.9),
                new Parameters(-0.3, 0.2, -0.1, 1.1),
                new Parameters(-0.2, 0.2, -0.1, 1.3),
                new Parameters(-0.1, 0.2, -0.1, 1.5),
                new Parameters(0, 0.2, -0.1, 1.7),
                new Parameters(0.1, 0.2, -0.1, 1.9),
                new Parameters(0.2, 0.2, -0.1, 2.1),
                new Parameters(0.3, 0.2, -0.1, 2.3),
                new Parameters(0.4, 0.2, -0.1, 2.5),
                new Parameters(0.5, 0.2, -0.1, 2.7),
                new Parameters(0.6, 0.2, -0.1, 2.9),
                new Parameters(0.7, 0.2, -0.1, 3.1),
                new Parameters(0.8, 0.2, -0.1, 3.3),
                new Parameters(0.9, 0.2, -0.1, 3.5)
        );

        cantidadAgentes = Arrays.asList(100, 150, 200, 250, 300, 350, 400, 450, 500);
    }

    @Test
    public void testAurelio() throws InterruptedException {
        for (Integer agentes : cantidadAgentes) {
            for (Parameters p : params) {
                new Experimento(false, agentes, p.getC1(), p.getC2(), p.getC3Min(), TipoMovimiento.MEJORADO, null).test();
                new Experimento(false, agentes, p.getC1(), p.getC2(), p.getC3Max(), TipoMovimiento.MEJORADO, null).test();
            }
        }
    }

    @Test
    public void testGustavo() throws InterruptedException, ExecutionException {
        for (Integer agentes : cantidadAgentes) {
            for (Parameters p : params) {
                new Experimento(false, agentes, p.getC1(), p.getC2(), p.getC3Min(), TipoMovimiento.INTEGRAL, new Object[]{0.01d}).test();
                new Experimento(false, agentes, p.getC1(), p.getC2(), p.getC3Max(), TipoMovimiento.INTEGRAL, new Object[]{0.01d}).test();
            }
        }
    }

    @Test
    public void testManualSingle() throws InterruptedException, ExecutionException {
        new Experimento(false, 400, .1, .2, .2, TipoMovimiento.MEJORADO, new Object[]{0.01d}).test();
    }

    @AllArgsConstructor
    private final class Experimento implements Callable<Void> {

        private boolean showSimulation;
        private Integer agentes;
        private Double c1, c2, c3;
        private TipoMovimiento movimiento;
        private Object[] movParams;

        @Override
        public Void call() throws Exception {
            test();
            return null;
        }

        public void test() {
            try {
                running(String.format("Prueba_%d_%1.2f_%1.2f_%1.2f_%s", agentes.intValue(),
                        c1.doubleValue(), c2.doubleValue(), c3.doubleValue(), movimiento.name()));
            } catch (InterruptedException e) {
                System.err.println("Error en test");
            }
        }

        protected void running(String name) throws InterruptedException {

            int simulationDelay = 50;

            StopWatch watch = new StopWatch();
            System.out.print(name);
            Escenarios e = Escenarios.builder()
                    .calcularMetricas(true)
                    .sensarEstado(false)
                    .guardarMetricasArchivo(!showSimulation)
                    .mostrarSimulacion(showSimulation)
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
                            .obstaculos(1)
                            .velMax(4)
                            .maxExtension(610)
                            .maxPolarizacion(180)
                            .build())
                    .ambiente(new AmbienteMovil())
                    .build();

            for (int i = 0; i < e.getConfiguracionAgente().getCantidadObjetivos(); i++) {
                AgenteMovilBuilder.builder()
                        .withAmbiente(e.getAmbiente())
                        .withX(100)
                        .withY(100)
                        .withConfiguracionAgente(e.getConfiguracionAgente())
                        .withMovimiento(e.getConfiguracionModelo().buildMovimientoCuatroEsquinas())
                        .buildObjetivo();
            }

            Vector infIzq = new Vector(0, 0);
            Vector supDer = new Vector(700, 700);

            for (int i = 0; i < e.getConfiguracionAgente().getCantidadAgentes(); i++) {
                Vector v = Util.aleatorioEnArea(infIzq, supDer);
                AgenteMovilBuilder.builder()
                        .withAmbiente(e.getAmbiente())
                        .withX(v.get(0))
                        .withY(v.get(1))
                        .withMovimiento(e.getConfiguracionModelo().buildMovimiento(movimiento, movParams))
                        .withConfiguracionAgente(e.getConfiguracionAgente())
                        .buildBoid();
            }

            assertThat(e.getAmbiente().getAgentes(), hasSize(new Double(
                    e.getConfiguracionAgente().getCantidadAgentes() + e.getConfiguracionAgente().getCantidadObjetivos())
                    .intValue()));

            Simulacion s = Simulacion.builder()
                    .ambiente(e.getAmbiente())
                    .delaySimulacion(e.isMostrarSimulacion() ? simulationDelay : 0)
                    .iteraciones(e.getIteraciones())
                    .observadores(e.getObservadores())
                    .habilitarPintado(e.isMostrarSimulacion())
                    .build();

            watch.start();
            Thread t = new Thread(s);
            t.start();
            t.join();
            watch.stop();
            System.out.println(String.format("; time=%d seg", TimeUnit.MILLISECONDS.toSeconds(watch.getTime())));

            if (e.isGuardarMetricasArchivo()) {
                try {
                    FileOutputStream report = new FileOutputStream(FileManager.crearArchivo(name, "metricas"));

                    e.getConfiguracionModelo().saveToFile(report);
                    report.flush();
                    e.getConfiguracionAgente().saveToFile(report);
                    report.flush();
                    for (ObservadorAmbiente observer : s.getObservadores()) {
                        observer.saveToFile(report);
                        report.flush();
                    }
                } catch (IOException ex) {
                    System.err.println("No se pudo crear archivo: " + ex.getLocalizedMessage());
                }
            }
            if (e.isMostrarSimulacion()) {
                for (ObservadorAmbiente observer : s.getObservadores()) {
                    if (ObservadorMetricas.class.isAssignableFrom(observer.getClass())) {
                        ((ObservadorMetricas)observer).mostrarReportesCortos();
                    }
                }
                try {
                    System.in.read();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
