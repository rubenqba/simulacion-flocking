package metricas;

import core.AgenteMovil;
import core.AmbienteMovil;
import tiposagentes.Boid;

public class ObservadorEstadoAmbiente implements ObservadorAmbiente {

    public void observarAmbiente(AmbienteMovil ambiente) {

        System.out.println();

        int idAgente = 1;
        for (AgenteMovil a : ambiente.getAgentes()) {
            if (a instanceof Boid) {

                Boid boid = (Boid) a;
                System.out.println("Agente " + idAgente + " " + boid);
                idAgente++;
            }
        }

    }

}
