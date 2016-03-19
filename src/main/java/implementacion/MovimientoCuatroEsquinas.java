package implementacion;

import core.AgenteMovil;
import core.AmbienteMovil;
import core.Movimiento;
import core.Vector;

public class MovimientoCuatroEsquinas implements Movimiento {

    private Vector esquinaSupIzq = new Vector(new double[] {
            100, 100
    });
    private Vector esquinaSupDer = new Vector(new double[] {
            AmbienteMovil.tamx - 100, 100
    });
    private Vector esquinaInfIzq = new Vector(new double[] {
            100, AmbienteMovil.tamy - 100
    });
    private Vector esquinaInfDer = new Vector(new double[] {
            AmbienteMovil.tamx - 100, AmbienteMovil.tamy - 100
    });
    private Vector direccion = esquinaSupIzq;

    public double getVelMax() {
        return velMax;
    }

    public void setVelMax(double velMax) {
        this.velMax = velMax;
    }

    private double velMax = 0;

    public void mover(AgenteMovil agente) {
        // TODO Auto-generated method stub

        Vector velocidad = calcularVelocidad(agente);
        Vector posicion = agente.getPosicion().clonar();
        posicion.sumar(velocidad);
        agente.setPosicion(posicion);
    }

    private Vector calcularVelocidad(AgenteMovil agente) {

        Vector posicion = agente.getPosicion();

        if (posicion.equals(esquinaSupIzq)) direccion = esquinaSupDer;
        if (posicion.equals(esquinaSupDer)) direccion = esquinaInfDer;
        if (posicion.equals(esquinaInfDer)) direccion = esquinaInfIzq;
        if (posicion.equals(esquinaInfIzq)) direccion = esquinaSupIzq;

        Vector vel = direccion.clonar();

        vel.restar(posicion);
        validarVelocidad(vel, agente);

        return vel;
    }

    private void validarVelocidad(Vector velocidad, AgenteMovil agente) {
        double vel = velocidad.norma();

        if (vel > velMax) {
            velocidad.multiplicarEscalar(velMax / vel);
        }
    }

    public Vector getEsquinaSupIzq() {
        return esquinaSupIzq;
    }

    public void setEsquinaSupIzq(Vector esquinaSupIzq) {
        this.esquinaSupIzq.set(esquinaSupIzq);
    }

    public Vector getEsquinaSupDer() {
        return esquinaSupDer;
    }

    public void setEsquinaSupDer(Vector esquinaSupDer) {
        this.esquinaSupDer.set(esquinaSupDer);
    }

    public Vector getEsquinaInfIzq() {
        return esquinaInfIzq;
    }

    public void setEsquinaInfIzq(Vector esquinaInfIzq) {
        this.esquinaInfIzq.set(esquinaInfIzq);
    }

    public Vector getEsquinaInfDer() {
        return esquinaInfDer;
    }

    public void setEsquinaInfDer(Vector esquinaInfDer) {
        this.esquinaInfDer.set(esquinaInfDer);
    }
}
