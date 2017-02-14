package escenarios;

import experimentos.Experimento;
import implementacion.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Escenario3Test {

    protected List<Parameters> params;
    protected List<Integer> cantidadAgentes;
    protected List<Integer> cantidadValientes;
    protected List<Integer> cantidadCobardes;

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
    @Ignore
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
    @Ignore
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
    protected class Parameters {
        @Getter
        private double c1, c2, c3Min, c3Max;
    }


}
