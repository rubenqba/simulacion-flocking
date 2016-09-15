package metricas;

import core.AgenteMovil;
import core.Vector;
import tiposagentes.Boid;

public class ObservadorMejorAgente extends ObservadorVariablesEstado {

    @Override
    protected double[] datosPorAlmacenar(AgenteMovil a) {
        Boid boid = (Boid) a;
        Vector mA = boid.getMejorDelGrupo().getPosicion();
        int iteracion = getNumIteracion();

        double datos[] = { mA.get(0), mA.get(1), iteracion };
        iteracion += 1;
        setNumIteracion(iteracion);

        return datos;
    }

    @Override
    public String getName() {
        return "mejor agente";
    }

}
