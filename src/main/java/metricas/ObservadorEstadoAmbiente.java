package metricas;

import core.AgenteMovil;
import core.AmbienteMovil;
import tiposagentes.Boid;

import java.io.FileOutputStream;
import java.io.IOException;

public class ObservadorEstadoAmbiente implements ObservadorAmbiente {

    @Override
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

    @Override
    public String getName() {
        return "ambiente";
    }

    @Override
    public void saveToFile(FileOutputStream out) throws IOException {

    }
}
