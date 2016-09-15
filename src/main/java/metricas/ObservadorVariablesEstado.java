package metricas;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import core.AgenteMovil;
import core.AmbienteMovil;
import core.Vector;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import tiposagentes.Boid;

public class ObservadorVariablesEstado implements ObservadorAmbiente {

    private int indiceAgenteObservado = -1;
    private ArrayList<double[]> datos;
    private int numIteracion = 0;

    @Override
    public void observarAmbiente(AmbienteMovil ambiente) {

        if (indiceAgenteObservado == -1) {
            iniciar(ambiente);
        } else {
            sensarAgente(ambiente.getAgentes().get(indiceAgenteObservado));
        }
    }

    @Override
    public void saveToFile(FileOutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        StringBuffer s = new StringBuffer();
        s.append("[");
        s.append(StringUtils.join(datos.stream()
                .map(array -> {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("[");
                    buffer.append(StringUtils.join(Arrays.stream(array).mapToObj(d -> String.format("%1.4f", d))
                            .collect(Collectors.toList()), ", "));
                    buffer.append("]");
                    return buffer.toString();
                })
                .collect(Collectors.toList()), "; "));
        s.append("]");
        writer.newLine();
        writer.write(String.format("Variables de estado (%s)%n", WordUtils.capitalize(getName())));
        writer.write("====================================\n");
        writer.write(s.toString());
        writer.newLine();
        writer.write("====================================\n");
        writer.flush();
    }

    private void iniciar(AmbienteMovil ambiente) {
        ArrayList<AgenteMovil> agentes = ambiente.getAgentes();

        for (int i = 0; i < agentes.size(); i++) {
            AgenteMovil a = agentes.get(i);

            if (a instanceof Boid) {
                indiceAgenteObservado = i;
                break;
            }
        }

        datos = new ArrayList<double[]>();
    }

    private void sensarAgente(AgenteMovil agenteMovil) {

        datos.add(datosPorAlmacenar(agenteMovil));

    }

    protected double[] datosPorAlmacenar(AgenteMovil movil) {
        Vector pos = movil.getPosicion();
        int iteracion = getNumIteracion();

        double datos[] = {
                pos.get(0), pos.get(1), iteracion
        };
        iteracion = iteracion + 1;
        setNumIteracion(iteracion);

        return datos;
    }

    public void setNumIteracion(int numIteracion) {
        this.numIteracion = numIteracion;
    }

    public int getNumIteracion() {
        return numIteracion;
    }

    public int getIndiceAgenteObservado() {
        return indiceAgenteObservado;
    }

    public void setIndiceAgenteObservado(int indiceAgenteObservado) {
        this.indiceAgenteObservado = indiceAgenteObservado;
    }

    public ArrayList<double[]> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<double[]> datos) {
        this.datos = datos;
    }

    @Override
    public String getName() {
        return "posicion";
    }

}
