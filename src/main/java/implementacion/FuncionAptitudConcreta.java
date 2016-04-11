package implementacion;

import Vecindades.VecindadObstaculos;
import core.AgenteMovil;
import core.Util;
import core.Vector;
import tiposagentes.Obstaculo;

public class FuncionAptitudConcreta implements FuncionAptitud {

    private double parametroObstaculos = 1;

    public double funcionAptitud(double distancia, double pesoObstaculos) {
        return (pesoObstaculos + 1) * distancia;
    }

    public double pesoObstaculos(VecindadObstaculos obstaculos, AgenteMovil agente, AgenteMovil objetivo) {
        Vector posAgente = agente.getPosicion();
        double distAgenteObstaculo = 0;

        double sumatoria = 0;

        for (Obstaculo o : obstaculos) {
            if (Util.estaEnLineaDeVista(agente, o, objetivo) == false)// si el
                                                                      // agente
                                                                      // no
                                                                      // puede
                                                                      // ver al
                                                                      // objetivo
                                                                      // porque
                                                                      // lo tapa
                                                                      // el
                                                                      // obstaculo
            {
                distAgenteObstaculo = posAgente.distancia(o.getPosicion());

                if (distAgenteObstaculo != 0) {
                    sumatoria = sumatoria + distAgenteObstaculo;
                }
            }
        }

        sumatoria = sumatoria * parametroObstaculos;
        return sumatoria;
    }

    public double getParametroObstaculo() {
        // TODO Auto-generated method stub
        return parametroObstaculos;
    }

    public void setParametroObstaculo(double parametroObstaculo) {
        this.parametroObstaculos = parametroObstaculo;
    }

}
