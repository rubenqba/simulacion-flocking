package implementacion;

import core.AgenteMovil;
import core.AmbienteMovil;
import core.Movimiento;
import core.Vector;

public class MovimientoCuatroEsquinas implements Movimiento {

    protected Vector esquinaSupIzq = new Vector(100, 100);
    protected Vector esquinaSupDer = new Vector(AmbienteMovil.tamx - 100, 100);
    protected Vector esquinaInfIzq = new Vector(100, AmbienteMovil.tamy - 100);
    protected Vector esquinaInfDer = new Vector(AmbienteMovil.tamx - 100, AmbienteMovil.tamy - 100);
    protected Vector direccion = esquinaSupIzq;

    public double getVelMax() {
        return velMax;
    }

    public void setVelMax(double velMax) {
        this.velMax = velMax;
    }

    private double velMax = 0;

    @Override
    public void mover(AgenteMovil agente) {
        Vector velocidad = calcularVelocidad(agente);
        Vector posicion = agente.getPosicion().clonar();
        posicion.sumar(velocidad);
        agente.setPosicion(posicion);
    }

    protected Vector calcularVelocidad(AgenteMovil agente) {

        Vector posicion = agente.getPosicion();
        if (posicion.equals(esquinaSupIzq))
            direccion = esquinaSupDer;
        if (posicion.equals(esquinaSupDer))
            direccion = esquinaInfDer;
        if (posicion.equals(esquinaInfDer))
            direccion = esquinaInfIzq;
        if (posicion.equals(esquinaInfIzq))
            direccion = esquinaSupIzq;

        Vector vel = direccion.clonar();

        vel.restar(posicion);
        validarVelocidad(vel, agente);

        return vel;
    }

    protected void validarVelocidad(Vector velocidad, AgenteMovil agente) {
        double vel = velocidad.norma();

        if (vel > velMax) {
            velocidad.multiplicarEscalar(velMax / vel);
        }
    }

    public void setEsquinaSupIzq(Vector esquinaSupIzq) {
        this.esquinaSupIzq.set(esquinaSupIzq);
    }

    public void setEsquinaSupDer(Vector esquinaSupDer) {
        this.esquinaSupDer.set(esquinaSupDer);
    }

    public void setEsquinaInfIzq(Vector esquinaInfIzq) {
        this.esquinaInfIzq.set(esquinaInfIzq);
    }

    public void setEsquinaInfDer(Vector esquinaInfDer) {
        this.esquinaInfDer.set(esquinaInfDer);
    }
}
