package metricas;

import java.util.ArrayList;

import core.AgenteMovil;
import core.AmbienteMovil;
import core.Vector;
import tiposagentes.Boid;

public class ObservadorVariablesEstado implements ObservadorAmbiente {

    private int indiceAgenteObservado = -1;
    private ArrayList<double[]> datos;
    private int numIteracion = 0;

    public void observarAmbiente(AmbienteMovil ambiente) {

        if (indiceAgenteObservado == -1) {
            iniciar(ambiente);
        } else {
            sensarAgente(ambiente.getAgentes().get(indiceAgenteObservado));
        }
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

}
