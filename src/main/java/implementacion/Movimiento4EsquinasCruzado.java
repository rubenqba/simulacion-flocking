package implementacion;

import core.AgenteMovil;
import core.Vector;

/**
 * Created by ruben.bressler on 9/14/2016.
 */
public class Movimiento4EsquinasCruzado extends MovimientoCuatroEsquinas {

    @Override
    protected Vector calcularVelocidad(AgenteMovil agente) {

        Vector posicion = agente.getPosicion();

        if (posicion.equals(esquinaSupIzq))
            direccion = esquinaInfDer;
        if (posicion.equals(esquinaSupDer))
            direccion = esquinaInfIzq;
        if (posicion.equals(esquinaInfDer))
            direccion = esquinaSupDer;
        if (posicion.equals(esquinaInfIzq))
            direccion = esquinaSupIzq;

        Vector vel = direccion.clonar();

        vel.restar(posicion);
        validarVelocidad(vel, agente);

        return vel;
    }
}
