package metricas;

import core.AgenteMovil;
import core.Vector;
import tiposagentes.Boid;

public class ObservadorObjetivo extends ObservadorVariablesEstado {

    @Override
    protected double[] datosPorAlmacenar(AgenteMovil a) {

        Boid boid = (Boid) a;
        Vector posO = boid.getObjetivo().getPosicion();
        int iteracion = getNumIteracion();

        double datos[] = { posO.get(0), posO.get(1), iteracion };
        iteracion += 1;
        setNumIteracion(iteracion);

        return datos;
    }

    @Override
    public String getName() {
        return "objetivo";
    }

}
