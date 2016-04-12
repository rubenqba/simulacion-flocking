package metricas;

import core.AmbienteMovil;

public interface ObservadorAmbiente {
    public String getName();

    public void observarAmbiente(AmbienteMovil ambiente);

}
