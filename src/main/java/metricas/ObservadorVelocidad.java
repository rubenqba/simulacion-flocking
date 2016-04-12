package metricas;

import core.AgenteMovil;
import core.Vector;

public class ObservadorVelocidad extends ObservadorVariablesEstado {

    @Override
    protected double[] datosPorAlmacenar(AgenteMovil a) {
        Vector vel = a.getVelocidad();
        int iteracion = getNumIteracion();

        double datos[] = { vel.get(0), vel.get(1), iteracion };
        iteracion += 1;
        setNumIteracion(iteracion);

        return datos;
    }

    @Override
    public String getName() {
        return "velocidad";
    }

}
