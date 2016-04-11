package metricas;

import java.util.ArrayList;
import java.util.Hashtable;

import Vecindades.VecindadBoid;
import Vecindades.VecindadObjetivos;
import core.AgenteMovil;
import core.AmbienteMovil;
import core.Vector;
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
        double cantObjetivos = objetivos.size();
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
                promedioDireccion.multiplicarEscalar(1.0 / cantAgentes);
                promedioPosicion.multiplicarEscalar(1.0 / cantAgentes);
            }

            /////////////////////// fin calculo promedios////////////

            //////// Calculando polarización,extension, colisiones y
            //////// consistencias//////////

            double polarizacion = 0.0;
            double extension = 0.0;
            double factorColisiones = 0.0;
            double consPolarizacion = 0.0;
            double consExtension = 0.0;
            double funcionObjetivo = 0.0;

            double menorDistancia = Double.POSITIVE_INFINITY;
            int cantAgentesSinColision = 0;
            int cantAgentesEnColision = 0;

            for (Boid a : agentes) {
                Vector diferencia = promedioPosicion.clonar();
                diferencia.restar(a.getPosicion());
                double normaDiferencia = diferencia.norma();
                double angulo = anguloEntreVectores(a.getVelocidad(), promedioDireccion);
                double distAgenteObjetivo = a.getPosicion().distancia(t.getPosicion());

                if (distAgenteObjetivo < menorDistancia) {
                    menorDistancia = distAgenteObjetivo;
                }

                extension = extension + normaDiferencia;
                polarizacion = polarizacion + angulo;
                factorColisiones = factorColisiones + factorColisiones(a);
                funcionObjetivo = funcionObjetivo + a.getValorFuncionObjetivo();

                if (a.getHaColisionado() == false) {
                    cantAgentesSinColision++;

                    consExtension = consExtension + normaDiferencia;
                    consPolarizacion = consPolarizacion + angulo;
                }

                if (a.isEnEstadoColision()) {
                    cantAgentesEnColision++;
                }

            }

            if (cantAgentes == 0.0) {
                polarizacion = 0;
                extension = 0;
                factorColisiones = 0;
                funcionObjetivo = 0;
            } else {
                polarizacion = polarizacion / cantAgentes;
                extension = extension / cantAgentes;
                factorColisiones = factorColisiones / cantAgentes;
                funcionObjetivo = funcionObjetivo / cantAgentes;

            }

            if (cantAgentesSinColision == 0)// si todos los agentes ya han
                                            // colisionado
            {
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

            polarizacionPromedio += polarizacion * pesoSubsistema;
            extensionPromedio += extension * pesoSubsistema;
            factorColisionesPromedio += factorColisiones * pesoSubsistema;
            consExtPromedio += consExtension * pesoSubsistema;
            ;
            consPolPromedio += consPolarizacion * pesoSubsistema;
            ;
            funcionObjetivoPromedio += funcionObjetivo * pesoSubsistema;
            ;
        }

        this.polarizacion = polarizacionPromedio;
        this.extension = extensionPromedio;
        this.factorColisiones = factorColisionesPromedio;
        this.consExtension = consExtPromedio;
        this.consPolarizacion = consPolPromedio;
        this.calidad = 0.5 * (this.consExtension + this.consPolarizacion);
        this.funcionObjetivo = funcionObjetivoPromedio;

    }

    private double factorColisiones(Boid b)// retorna 1 si el agente est�
                                           // colisionando
    {
        if (b.isEnEstadoColision())
            return 1;
        return 0;
    }

    public static double anguloEntreVectores(Vector A, Vector B) {
        if (A.equals(B)) {
            return 0;
        }

        double normaA = A.norma();
        double normaB = B.norma();
        double temp = 0.0;

        if (normaA == 0.0 || normaB == 0.0) {
            return 0.0;
        }

        double productoPunto = A.productoPunto(B);

        float dotProduct = new Double(productoPunto).floatValue();
        float div = new Double(normaA * normaB).floatValue();

        double a = dotProduct / (div);

        temp = a;
        temp = temp + a;
        a = temp - a; // trato de eliminar algunos errores de redondeo

        double rad = Math.acos(a);

        return Math.toDegrees(rad);
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
            VecindadBoid conjuntoAgentes = conjuntosDeAgentes.get(o.getID());
            conjuntoAgentes.add(boid);
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

    private void determinarColisiones() {
        AgenteMovil a, b = null;

        int size = agentes.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j)
                    continue;

                a = agentes.get(i);
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

    private boolean estanColisionando(AgenteMovil a, AgenteMovil b) {
        double distanciaDeColision = a.getRadio() + b.getRadio();
        double distanciaAgentes = distancia(a, b);

        if (distanciaDeColision >= distanciaAgentes)
            return true;

        return false;
    }

    private double distancia(AgenteMovil a, AgenteMovil b) {
        Vector posa = a.getPosicion();
        Vector posb = b.getPosicion();
        return posa.distancia(posb);
    }

    public static void pruebaMetricas() {
        double radio = 1;
        double maxExtension = 10;
        double penalPolarizacion = 1;
        double penalExtension = 1;

        AmbienteMovil ambiente = new AmbienteMovil();

        Boid boid1 = new Boid(5, 2, ambiente);
        Boid boid2 = new Boid(1, 3, ambiente);
        Boid boid3 = new Boid(1, 0, ambiente);
        Boid boid4 = new Boid(4, 2, ambiente);

        boid1.setVelocidad(new Vector(2, 2));
        boid2.setVelocidad(new Vector(1, 0));
        boid3.setVelocidad(new Vector(3, 3));
        boid4.setVelocidad(new Vector(2, 1));

        Objetivo objetivo = new Objetivo(1, 1, ambiente);
        VecindadObjetivos objetivos = new VecindadObjetivos();
        objetivos.add(objetivo);

        boid1.setObjetivo(objetivo);
        boid2.setObjetivo(objetivo);
        boid3.setObjetivo(objetivo);
        boid4.setObjetivo(objetivo);

        boid1.setObjetivos(objetivos);
        boid2.setObjetivos(objetivos);
        boid3.setObjetivos(objetivos);
        boid4.setObjetivos(objetivos);

        boid1.setRadio(radio);
        boid2.setRadio(radio);
        boid3.setRadio(radio);
        boid4.setRadio(radio);

        ambiente.agregarAgente(boid1);
        ambiente.agregarAgente(boid2);
        ambiente.agregarAgente(boid3);
        ambiente.agregarAgente(boid4);

        Metrica m = new Metrica(ambiente);
        m.setMaxExt(maxExtension);
        m.setPenalEnExtension(penalExtension);
        m.setPenalEnPolarizacion(penalPolarizacion);

        m.calcularMetricas();

        /*
         * VecindadBoid agentes = new VecindadBoid();
         * agentes.add(boid4);
         * agentes.add(boid3);
         * agentes.add(boid2);
         * agentes.add(boid1);
         * for(AgenteMovil a:agentes)
         * {
         * if(a instanceof Boid)
         * {
         * Boid b = (Boid)a;
         * System.out.println();
         * System.out.println("Agente "+ b);
         * System.out.println("Ha colisionado "+b.getHaColisionado());
         * System.out.println("Estado colision "+ b.isEnEstadoColision());
         * }
         * }
         */

    }

    public static void pruebaDeterminarColisiones() {
        double radio = 3;
        AmbienteMovil ambiente = new AmbienteMovil();

        ArrayList<AgenteMovil> agentes = new ArrayList<AgenteMovil>();
        agentes.add(new Boid(0, 0, ambiente));
        agentes.add(new Boid(0, 0, ambiente));
        agentes.add(new Boid(0, 0, ambiente));
        agentes.add(new Boid(16, 0, ambiente));
        agentes.add(new Boid(6.1, 0, ambiente));

        for (AgenteMovil a : agentes) {
            a.setRadio(radio);
            ambiente.agregarAgente(a);
        }

        Metrica m = new Metrica(ambiente);
        m.determinarColisiones();

        for (AgenteMovil a : agentes) {
            if (a instanceof Boid) {
                Boid b = (Boid) a;

                /*
                 * System.out.println();
                 * System.out.println("Agente "+ b);
                 * System.out.println("Ha colisionado "+b.getHaColisionado());
                 * System.out.println("Estado colision "+
                 * b.isEnEstadoColision());
                 */
            }
        }

    }

    public static void pruebaObtenerConjuntos() {
        AmbienteMovil ambiente = new AmbienteMovil();

        Boid a1 = new Boid(1, 1, ambiente);
        Boid a2 = new Boid(2, 2, ambiente);
        Boid a3 = new Boid(3, 3, ambiente);
        Boid a4 = new Boid(4, 4, ambiente);
        Boid a5 = new Boid(5, 5, ambiente);

        Objetivo o1 = new Objetivo(1, 1, ambiente);
        Objetivo o2 = new Objetivo(1, 1, ambiente);
        Objetivo o3 = new Objetivo(1, 1, ambiente);
        Objetivo o4 = new Objetivo(1, 1, ambiente);
        Objetivo o5 = new Objetivo(1, 1, ambiente);

        o1.setID(1);
        o2.setID(2);
        o3.setID(3);
        o4.setID(4);
        o5.setID(5);

        VecindadObjetivos objs = new VecindadObjetivos();

        objs.add(o1);
        objs.add(o2);
        objs.add(o3);
        objs.add(o4);
        objs.add(o5);

        a1.setObjetivos(objs);
        a2.setObjetivos(objs);
        a3.setObjetivos(objs);
        a4.setObjetivos(objs);
        a5.setObjetivos(objs);

        a1.setObjetivo(o1);
        a2.setObjetivo(o1);
        a3.setObjetivo(o1);
        a4.setObjetivo(o1);
        a5.setObjetivo(o1);

        ambiente.agregarAgente(a1);
        ambiente.agregarAgente(a2);
        ambiente.agregarAgente(a3);
        ambiente.agregarAgente(a4);
        ambiente.agregarAgente(a5);

        ambiente.agregarAgente(o1);
        ambiente.agregarAgente(o2);
        ambiente.agregarAgente(o3);
        ambiente.agregarAgente(o4);
        ambiente.agregarAgente(o5);

        Metrica m = new Metrica(ambiente);
        m.calcularConjuntosDeAgentes();
        m.mostrarConjuntos();
    }

    public static void main(String ar[]) {
        // Metrica.pruebaMetricas();

        Vector A = new Vector(3.407953946628199, 3.658667776344455);

        Vector B = new Vector(0.6815907893256399, 0.7317335552688911);

        Metrica.anguloEntreVectores(A, B);
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
