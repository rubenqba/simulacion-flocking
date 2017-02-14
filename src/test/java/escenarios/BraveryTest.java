package escenarios;

import experimentos.Experimento;
import implementacion.TipoMovimiento;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Created by ruben.bressler on 14/02/17.
 */
public class BraveryTest extends Escenario3Test {

    @Test
    public void testValentia() {
        IntStream.range(0, cantidadValientes.size())
                .forEach(i -> {
                    for (int j = 0; j < 30; j++) {
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
                    }
                });
    }
}
