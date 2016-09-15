package metricas;

import core.AmbienteMovil;
import reportes.IReportFile;

import java.io.FileOutputStream;
import java.io.IOException;

public interface ObservadorAmbiente extends IReportFile {
    String getName();

    void observarAmbiente(AmbienteMovil ambiente);
}
