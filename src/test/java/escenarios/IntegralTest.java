package escenarios;

import experimentos.Experimento;
import implementacion.TipoMovimiento;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * Created by ruben.bressler on 14/02/17.
 */
public class IntegralTest extends Escenario3Test {

    @Test
    public void testIntegral() throws InterruptedException, ExecutionException {
        for (Integer agentes : cantidadAgentes) {
            for (Parameters p : params) {
                for (int i = 0; i < 30; i++) {
                    Experimento.builder()
                            .showSimulation(false)
                            .agentes(agentes)
                            .c1Min(p.getC1())
                            .c1Max(p.getC1())
                            .c2(p.getC2())
                            .c3(p.getC3Min())
                            .valientes(0)
                            .cobardes(0)
                            .movimiento(TipoMovimiento.INTEGRAL)
                            .build()
                            .test();
                }
                if (p.getC3Min() != p.getC3Max()) {
                    for (int i = 0; i < 30; i++) {
                        Experimento.builder()
                                .showSimulation(false)
                                .agentes(agentes)
                                .c1Min(p.getC1())
                                .c1Max(p.getC1())
                                .c2(p.getC2())
                                .c3(p.getC3Max())
                                .valientes(0)
                                .cobardes(0)
                                .movimiento(TipoMovimiento.INTEGRAL)
                                .build()
                                .test();
                    }
                }
            }
        }
    }
}
