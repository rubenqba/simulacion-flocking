package escenarios;

import experimentos.Experimento;
import implementacion.TipoMovimiento;
import org.junit.Test;

/**
 * Created by ruben.bressler on 14/02/17.
 */
public class MejoradoTest extends Escenario3Test {

    @Test
    public void testMejorado() throws InterruptedException {
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
                            .movimiento(TipoMovimiento.MEJORADO)
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
                                .movimiento(TipoMovimiento.MEJORADO)
                                .build()
                                .test();
                    }
                }
            }
        }
    }
}
