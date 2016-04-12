package escenarios;

import java.util.ArrayList;

import core.AmbienteMovil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import metricas.ObservadorAmbiente;

@Builder
@Getter
public class Simulaciones {

    private AmbienteMovil ambiente;
    private int iteraciones = 10000;
    private int delaySimulacion = 100;
    private boolean simulacionActiva = true;
    private boolean habilitarPintado = true;
    private boolean simulacion3D = false;

    @Setter(value = AccessLevel.PRIVATE)
    private ArrayList<ObservadorAmbiente> observadores;

    public SimulacionesBuilder addObservador(ObservadorAmbiente observador) {
        if (observadores == null)
            observadores = new ArrayList<>();
        observadores.add(observador);
        return builder();
    }
}
