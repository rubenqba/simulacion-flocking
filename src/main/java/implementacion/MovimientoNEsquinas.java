package implementacion;

import java.util.ArrayList;

import core.AgenteMovil;
import core.Movimiento;
import core.Vector;

public class MovimientoNEsquinas implements Movimiento {

    private ArrayList<Vector> puntos;
    private Vector puntoASeguir;
    private int indicePunto = 0;
    private double velMax = 0;

    public double getVelMax() {
        return velMax;
    }

    public void setVelMax(double velMax) {
        this.velMax = velMax;
    }

    public MovimientoNEsquinas(ArrayList<Vector> puntos) {
        this.puntos = puntos;
        puntoASeguir = puntos.get(0);
    }

    public void mover(AgenteMovil agente) {
        Vector pos = agente.getPosicion().clonar();

        if (pos.equals(puntoASeguir))// ya lleg� al punto
        {
            if (indicePunto == puntos.size() - 1) {
                indicePunto = 0;
            } else {
                indicePunto++;
            }
        }

        puntoASeguir = puntos.get(indicePunto);
        Vector vel = puntoASeguir.clonar();
        vel.restar(pos);
        validarVelocidad(vel);

        agente.setVelocidad(vel);
        agente.getPosicion().sumar(vel);
    }

    private void validarVelocidad(Vector velocidad) {
        double vel = velocidad.norma();

        if (vel > velMax) {
            velocidad.multiplicarEscalar(velMax / vel);
        }
    }
}
