package metricas;

import java.util.ArrayList;
import java.util.Hashtable;

import Vecindades.VecindadBoid;
import Vecindades.VecindadObjetivos;
import core.AgenteMovil;
import core.AmbienteMovil;
import core.Util;
import core.Vector;
import lombok.Getter;
import tiposagentes.Boid;
import tiposagentes.Objetivo;

public class Metrica {

    private ArrayList<AgenteMovil> agentes;
    private VecindadBoid boids;
    private VecindadObjetivos objetivos;
    private Hashtable<Integer, VecindadBoid> conjuntosDeAgentes;
    private double penalEnPolarizacion = 1;
    private double penalEnExtension = 1;
    private double maxExt = 10;
    private double maxAngulo = 180;

    private double polarizacion = 0.0;
    private double extension = 0.0;
    private double factorColisiones = 0.0;
    private double consExtension = 0.0;
    private double consPolarizacion = 0.0;
    private double calidad = 0.0;
    private double funcionObjetivo = 0.0;
    private double seguimiento = 0.0;
    @Getter
    private int cantAgentesEnColision;

    public double getPenalEnPolarizacion() {
        return penalEnPolarizacion;
    }

    public void setPenalEnPolarizacion(double penalEnPolarizacion) {
        this.penalEnPolarizacion = penalEnPolarizacion;
    }

    public double getPenalEnExtension() {
        return penalEnExtension;
    }

    public void setPenalEnExtension(double penalEnExtension) {
        this.penalEnExtension = penalEnExtension;
    }

    public double getMaxExt() {
        return maxExt;
    }

    public void setMaxExt(double maxExt) {
        this.maxExt = maxExt;
    }

    public Metrica(AmbienteMovil ambiente) {
        agentes = ambiente.getAgentes();
        boids = extraerBoids(ambiente);
        objetivos = extraerObjetivos(boids);
    }

    public void calcularMetricas() {
        double polarizacionPromedio = 0.0;
        double extensionPromedio = 0.0;
        double factorColisionesPromedio = 0.0;
        double consPolPromedio = 0.0;
        double consExtPromedio = 0.0;
        double funcionObjetivoPromedio = 0.0;

        double pesoSubsistema = 0.0;

        double totalAgentes = boids.size();
        calcularConjuntosDeAgentes();
        determinarColisiones();

        for (Objetivo t : objetivos) {
            ArrayList<Boid> agentes = conjuntosDeAgentes.get(t.getID());

            double cantAgentes = agentes.size();

            int dimension = t.getPosicion().getDimension();
            Vector promedioDireccion = new Vector(dimension);
            Vector promedioPosicion = new Vector(dimension);// centro del
            // conjunto de
            // agentes

            // Calculando promedios de direccion y posicion
            for (Boid a : agentes) {
                promedioDireccion.sumar(a.getVelocidad().unitario());
                promedioPosicion.sumar(a.getPosicion());
            }

            if (cantAgentes > 0) {
                promedioDireccion.multiplicarEscalar(1d / cantAgentes);
                promedioPosicion.multiplicarEscalar(1d / cantAgentes);
            }

            /////////////////////// fin calculo promedios////////////

            //////// Calculando polarización,extension, colisiones y
            //////// consistencias//////////

            double polarizacionLocal = 0.0;
            double extension = 0.0;
            double factorColisiones = 0.0;
            double consPolarizacion = 0.0;
            double consExtension = 0.0;
            double funcionObjetivo = 0.0;

            double menorDistancia = Double.POSITIVE_INFINITY;
            int cantAgentesSinColision = 0;
            cantAgentesEnColision = 0;

            for (Boid a : agentes) {
                Vector diferencia = promedioPosicion.clonar();
                diferencia.restar(a.getPosicion());
                double normaDiferencia = diferencia.norma();
                double angulo = Util.anguloEntreVectores(a.getVelocidad(), promedioDireccion);
                double distAgenteObjetivo = a.getPosicion().distancia(t.getPosicion());

                if (distAgenteObjetivo < menorDistancia) {
                    menorDistancia = distAgenteObjetivo;
                }

                extension += normaDiferencia;
                polarizacionLocal += angulo;
                if (a.isEnEstadoColision()) {
                    factorColisiones++;
                    cantAgentesEnColision++;
                }
                funcionObjetivo = funcionObjetivo + a.getValorFuncionObjetivo();

                if (!a.isHaColisionado()) {
                    cantAgentesSinColision++;
                    consExtension += normaDiferencia;
                    consPolarizacion += angulo;
                }
            }

            if (cantAgentes == 0) {
                polarizacionLocal = 0;
                extension = 0;
                factorColisiones = 0;
                funcionObjetivo = 0;
            } else {
                polarizacionLocal = polarizacionLocal / cantAgentes;
                extension = extension / cantAgentes;
                factorColisiones = factorColisiones / cantAgentes;
                funcionObjetivo = funcionObjetivo / cantAgentes;
            }

            if (cantAgentesSinColision == 0) {
                // si todos los agentes ya han colisionado
                consExtension = 0.0;
                consPolarizacion = 0.0;
            } else {
                consExtension = consExtension + (penalEnExtension * cantAgentesEnColision);
                consExtension = consExtension / (maxExt * (cantAgentesEnColision + cantAgentesSinColision));
                consExtension = 1 - consExtension;

                consPolarizacion = consPolarizacion + (penalEnPolarizacion * cantAgentesEnColision);
                consPolarizacion = consPolarizacion / (maxAngulo * (cantAgentesEnColision + cantAgentesSinColision));
                consPolarizacion = 1 - consPolarizacion;
            }

            pesoSubsistema = cantAgentes / totalAgentes;

            polarizacionPromedio += polarizacionLocal * pesoSubsistema;
            extensionPromedio += extension * pesoSubsistema;
            factorColisionesPromedio += factorColisiones * pesoSubsistema;
            consExtPromedio += consExtension * pesoSubsistema;
            consPolPromedio += consPolarizacion * pesoSubsistema;
            funcionObjetivoPromedio += funcionObjetivo * pesoSubsistema;

        }

        this.polarizacion = polarizacionPromedio;
        this.extension = extensionPromedio;
        this.factorColisiones = factorColisionesPromedio;
        this.consExtension = consExtPromedio;
        this.consPolarizacion = consPolPromedio;
        this.calidad = 0.5 * (this.consExtension + this.consPolarizacion);
        this.funcionObjetivo = funcionObjetivoPromedio;
    }

    private VecindadObjetivos extraerObjetivos(VecindadBoid boids2) {

        if (boids.isEmpty()) {
            objetivos = new VecindadObjetivos();
        } else {
            objetivos = boids.get(0).getObjetivos();
        }

        return objetivos;
    }

    private VecindadBoid extraerBoids(AmbienteMovil ambiente) {

        VecindadBoid boids = new VecindadBoid();
        for (AgenteMovil a : ambiente.getAgentes()) {
            if (a instanceof Boid) {
                boids.add((Boid) a);
            }
        }

        return boids;
    }

    private void calcularConjuntosDeAgentes() {
        conjuntosDeAgentes = new Hashtable<Integer, VecindadBoid>();

        // inicializo las vecindades por objetivo
        for (Objetivo o : objetivos) {
            conjuntosDeAgentes.put(o.getID(), new VecindadBoid());
        }

        // lleno las vecindades por objetivo
        for (Boid boid : boids) {
            Objetivo o = boid.getObjetivo();
            conjuntosDeAgentes.get(o.getID()).add(boid);
        }
    }

    public void mostrarConjuntos() {
        for (Objetivo o : objetivos) {
            VecindadBoid conjuntoAgentes = conjuntosDeAgentes.get(o.getID());

            System.out.println();
            System.out.println("Agentes que siguen al Objetivo " + o.getID());

            for (Boid boid : conjuntoAgentes) {
                System.out.println(boid);
            }
        }
    }

    public void determinarColisiones() {
        AgenteMovil a, b = null;

        int size = agentes.size();
        for (int i = 0; i < size; i++) {
            a = agentes.get(i);
            for (int j = 0; j < size; j++) {
                if (i == j)
                    continue;
                b = agentes.get(j);

                if (estanColisionando(a, b)) {
                    informarColision(a, true);
                    break;
                } else {
                    informarColision(a, false);
                }
            }
        }
    }

    private void informarColision(AgenteMovil a, boolean colision) {
        if (a instanceof Boid) {
            Boid aboid = (Boid) a;
            if (colision) {
                aboid.setHaColisionado(colision);
                aboid.setEnEstadoColision(colision);
            } else {
                aboid.setEnEstadoColision(colision);
            }
        }
    }

    public boolean estanColisionando(AgenteMovil a, AgenteMovil b) {
        double distanciaDeColision = a.getRadio() + b.getRadio();
        double distanciaAgentes = distancia(a, b);

        return (distanciaDeColision >= distanciaAgentes);
    }

    private double distancia(AgenteMovil a, AgenteMovil b) {
        return a.getPosicion().distancia(b.getPosicion());
    }

    public double getPolarizacion() {
        return polarizacion;
    }

    public double getExtension() {
        return extension;
    }

    public double getFactorColisiones() {
        return factorColisiones;
    }

    public double getConsExtension() {
        return consExtension;
    }

    public double getConsPolarizacion() {
        return consPolarizacion;
    }

    public double getCalidad() {
        return calidad;
    }

    public double getMaxAngulo() {
        return maxAngulo;
    }

    public void setMaxAngulo(double maxAngulo) {
        this.maxAngulo = maxAngulo;
    }

    public double getFuncionObjetivo() {

        return this.funcionObjetivo;
    }

}
