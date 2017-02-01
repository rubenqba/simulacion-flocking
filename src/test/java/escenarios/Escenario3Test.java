package escenarios;

import experimentos.Experimento;
import implementacion.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class Escenario3Test {

    private List<Parameters> params;
    private List<Integer> cantidadAgentes;
    private List<Integer> cantidadValientes;
    private List<Integer> cantidadCobardes;

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

        cantidadValientes = Arrays.asList(80, 60, 50, 40, 20);
        cantidadCobardes = Arrays.asList(20, 40, 50, 60, 80);
    }

    @Test
    public void testMejorado() throws InterruptedException {
        for (Integer agentes : cantidadAgentes) {
            for (Parameters p : params) {
                Experimento.builder()
                        .showSimulation(false)
                        .agentes(agentes)
                        .c1Max(p.getC1())
                        .c2(p.getC2())
                        .c3(p.getC3Min())
                        .movimiento(TipoMovimiento.MEJORADO)
                        .build()
                        .test();
                Experimento.builder()
                        .showSimulation(false)
                        .agentes(agentes)
                        .c1Max(p.getC1())
                        .c2(p.getC2())
                        .c3(p.getC3Max())
                        .movimiento(TipoMovimiento.MEJORADO)
                        .build()
                        .test();
            }
        }
    }

    @Test
    public void testIntegral() throws InterruptedException, ExecutionException {
        for (Integer agentes : cantidadAgentes) {
            for (Parameters p : params) {
                Experimento.builder()
                        .showSimulation(false)
                        .agentes(agentes)
                        .c1Max(p.getC1())
                        .c2(p.getC2())
                        .c3(p.getC3Min())
                        .movimiento(TipoMovimiento.INTEGRAL)
                        .build()
                        .test();
                Experimento.builder()
                        .showSimulation(false)
                        .agentes(agentes)
                        .c1Max(p.getC1())
                        .c2(p.getC2())
                        .c3(p.getC3Max())
                        .movimiento(TipoMovimiento.INTEGRAL)
                        .build()
                        .test();
            }
        }
    }

    @Test
    public void testSpline() throws InterruptedException, ExecutionException {
        for (Integer agentes : cantidadAgentes) {
            for (Parameters p : params) {
                Experimento.builder()
                        .showSimulation(false)
                        .agentes(agentes)
                        .c1Max(p.getC1())
                        .c2(p.getC2())
                        .c3(p.getC3Min())
                        .movimiento(TipoMovimiento.SPLINE)
                        .build()
                        .test();
                Experimento.builder()
                        .showSimulation(false)
                        .agentes(agentes)
                        .c1Max(p.getC1())
                        .c2(p.getC2())
                        .c3(p.getC3Max())
                        .movimiento(TipoMovimiento.SPLINE)
                        .build()
                        .test();
            }
        }
    }

    @Test
    public void testValentia() {
        IntStream.range(0, cantidadValientes.size())
                .forEach(i -> {
                    Experimento.builder()
                            .showSimulation(false)
                            .cobardes(cantidadCobardes.get(i))
                            .valientes(cantidadValientes.get(i))
                            .c1Max(.1)
                            .c1Min(-.1)
                            .c2(.2)
                            .c3(-.1)
                            .movimiento(TipoMovimiento.MEJORADO)
                            .build()
                            .test();
                    Experimento.builder()
                            .showSimulation(false)
                            .cobardes(cantidadCobardes.get(i))
                            .valientes(cantidadValientes.get(i))
                            .c1Max(.5)
                            .c1Min(-.5)
                            .c2(.2)
                            .c3(-.1)
                            .movimiento(TipoMovimiento.MEJORADO)
                            .build()
                            .test();
                    Experimento.builder()
                            .showSimulation(false)
                            .cobardes(cantidadCobardes.get(i))
                            .valientes(cantidadValientes.get(i))
                            .c1Max(.9)
                            .c1Min(-.9)
                            .c2(.2)
                            .c3(-.1)
                            .movimiento(TipoMovimiento.MEJORADO)
                            .build()
                            .test();
                    Experimento.builder()
                            .showSimulation(false)
                            .cobardes(cantidadCobardes.get(i))
                            .valientes(cantidadValientes.get(i))
                            .c1Max(.1)
                            .c1Min(-.1)
                            .c2(.2)
                            .c3(-.1)
                            .movimiento(TipoMovimiento.SPLINE)
                            .build()
                            .test();
                    Experimento.builder()
                            .showSimulation(false)
                            .cobardes(cantidadCobardes.get(i))
                            .valientes(cantidadValientes.get(i))
                            .c1Max(.5)
                            .c1Min(-.5)
                            .c2(.2)
                            .c3(-.1)
                            .movimiento(TipoMovimiento.SPLINE)
                            .build()
                            .test();
                    Experimento.builder()
                            .showSimulation(false)
                            .cobardes(cantidadCobardes.get(i))
                            .valientes(cantidadValientes.get(i))
                            .c1Max(.9)
                            .c1Min(-.9)
                            .c2(.2)
                            .c3(-.1)
                            .movimiento(TipoMovimiento.SPLINE)
                            .build()
                            .test();
                });
    }

    @Test
    public void testComparacion() {
        for (Integer agentes : cantidadAgentes) {
            Experimento.builder()
                    .showSimulation(false)
                    .agentes(agentes)
                    .c1Max(.01)
                    .c2(.2)
                    .c3(.2)
                    .movimiento(TipoMovimiento.MEJORADO)
                    .build()
                    .test();
        }
    }

    @Test
    public void testManualSingle() throws InterruptedException, ExecutionException {
        Experimento.builder()
                .showSimulation(false)
                .valientes(5)
                .cobardes(5)
                .c1Max(.9)
                .c1Min(-.9)
                .c2(.2)
                .c3(-.1)
                .movimiento(TipoMovimiento.MEJORADO)
                .build()
                .test();
    }

    @AllArgsConstructor
    private class Parameters {
        @Getter
        private double c1, c2, c3Min, c3Max;
    }


}
