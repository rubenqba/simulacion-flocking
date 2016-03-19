package implementacion;

import Vecindades.VecindadBoid;
import Vecindades.VecindadObjetivos;
import Vecindades.VecindadObstaculos;
import core.AgenteMovil;
import core.Movimiento;
import core.Percepcion;
import core.Vector;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;

public class MovimientoBoid implements Movimiento {

    private double c1 = 1;// peso inercial
    private double c2 = 1;// peso mejor posicion
    private double c3 = 1;// peso mejor agente del grupo
    private double velMax = 3;
    private double zonaVirtual = 5;
    private FuncionAptitud F = new FuncionAptitudConcreta();
    private double parametroObstaculo = 1;

    private double mejorAptitudVecino = Double.POSITIVE_INFINITY;
    private double mejorAptitud = Double.POSITIVE_INFINITY;

    public void mover(AgenteMovil agente) {

        if (agente instanceof Boid) {

            desplazarAgente((Boid) agente);
        }
    }

    public void desplazarAgente(Boid boid) {
        Vector vel = calcularVelocidad(boid);

        Vector posicionAgente = boid.getPosicion();

        posicionAgente.sumar(vel);
        boid.setVelocidad(vel);
        evasionColisiones(boid);
    }

    public Vector calcularVelocidad(Boid boid) {
        Vector primerTermino;
        Vector segundoTermino;
        Vector tercerTermino;

        Percepcion percepcion = boid.calcularPercepcion();
        VecindadObjetivos objetivos = boid.getObjetivos();
        VecindadObstaculos obstaculos = percepcion.getVecindadObstaculo();
        VecindadBoid vecinos = percepcion.getVecindadBoid();

        Objetivo o = calcularObjetivo(objetivos, boid, obstaculos);
        boid.setObjetivo(o);

        Boid mejorAgente = calcularMejorVecino(vecinos, boid, o, obstaculos);

        if (this.mejorAptitud < this.mejorAptitudVecino) {
            mejorAgente = boid;
        }

        boid.setValorFuncionObjetivo(this.mejorAptitud);
        boid.setMejorDelGrupo(mejorAgente);

        primerTermino = boid.getVelocidad().clonar();
        segundoTermino = o.getPosicion().clonar();
        tercerTermino = mejorAgente.getPosicion().clonar();// seguir al mejor
                                                           // agente

        primerTermino.multiplicarEscalar(c1);
        segundoTermino.restar(boid.getPosicion());
        segundoTermino.multiplicarEscalar(c2 * Math.random());
        tercerTermino.restar(boid.getPosicion());
        tercerTermino.multiplicarEscalar(c3 * Math.random());

        Vector velocidad = new Vector(primerTermino.getDimension());

        velocidad.sumar(primerTermino);
        velocidad.sumar(segundoTermino);
        velocidad.sumar(tercerTermino);

        acotarVelocidad(boid, velocidad);

        return velocidad;
    }

    public Objetivo calcularObjetivo(VecindadObjetivos objetivos, Boid boid, VecindadObstaculos obstaculos) {
        mejorAptitud = Double.POSITIVE_INFINITY;// la mejor aptitud es la mas
                                                // peque�a
        Objetivo o = null;

        double distancia = 0;
        double aptitud = 0;
        double pesoObstaculo = 0;
        F.setParametroObstaculo(parametroObstaculo);

        for (Objetivo obj : objetivos) {
            distancia = boid.getPosicion().distancia(obj.getPosicion());
            pesoObstaculo = F.pesoObstaculos(obstaculos, boid, obj);
            aptitud = F.funcionAptitud(distancia, pesoObstaculo);

            if (aptitud <= mejorAptitud) {
                o = obj;
                mejorAptitud = aptitud;
            }
        }

        return o;
    }

    // este metodo es muy similar a calcularObjetivo
    public Boid calcularMejorVecino(VecindadBoid boids, Boid Boid, Objetivo objetivo, VecindadObstaculos obstaculos) {
        this.mejorAptitudVecino = Double.POSITIVE_INFINITY;
        Boid mejorVecino = null;

        double distancia = 0;
        double aptitud = 0;
        double pesoObstaculos = 0;
        F.setParametroObstaculo(parametroObstaculo);

        for (Boid vecino : boids) {
            distancia = objetivo.getPosicion().distancia(vecino.getPosicion());
            pesoObstaculos = F.pesoObstaculos(obstaculos, vecino, objetivo);
            aptitud = F.funcionAptitud(distancia, pesoObstaculos);

            if (aptitud <= this.mejorAptitudVecino) {
                this.mejorAptitudVecino = aptitud;
                mejorVecino = vecino;
            }
        }

        return mejorVecino;
    }

    public Vector obtenerMejorPosicion(Boid boid) {
        return boid.getPosicion().clonar();
    }

    public void acotarVelocidad(Boid boid, Vector velocidad) {
        double magnitud = velocidad.norma();

        if (magnitud > velMax) {
            velocidad.multiplicarEscalar(velMax / magnitud);

        }
    }

    public void evasionColisiones(Boid boid) {

        Percepcion percepcion = boid.getPercepcion();
        VecindadObstaculos obstaculos = percepcion.getVecindadObstaculo();
        VecindadBoid vecinos = percepcion.getVecindadBoid();

        double distanciaDeseada = 0;
        double distancia = 0;

        for (Boid vecino : vecinos) {
            Vector pos = boid.getPosicion();
            distanciaDeseada = boid.getRadio() + zonaVirtual + vecino.getRadio();
            distancia = pos.distancia(vecino.getPosicion());// esto ya lo habia
                                                            // calculado antes

            if (distancia < distanciaDeseada) {
                recalcularPosicion(boid, vecino, distancia);
            }
        }

        // lo mismo con los obstaculos
        for (Obstaculo obstaculo : obstaculos) {
            Vector pos = boid.getPosicion();
            distanciaDeseada = boid.getRadio() + zonaVirtual + obstaculo.getRadio();
            distancia = pos.distancia(obstaculo.getPosicion());

            if (distancia < distanciaDeseada) {
                recalcularPosicion(boid, obstaculo, distancia);
            }
        }

        for (Objetivo o : boid.getObjetivos()) {
            Vector pos = boid.getPosicion();
            distanciaDeseada = boid.getRadio() + zonaVirtual + o.getRadio();
            distancia = pos.distancia(o.getPosicion());

            if (distancia < distanciaDeseada) {
                recalcularPosicion(boid, o, distancia);
            }
        }
    }

    public void recalcularPosicion(Boid boid, AgenteMovil agentePorEvadir, double distancia) {
        double distanciaDeseada = boid.getRadio() + zonaVirtual + agentePorEvadir.getRadio();
        Vector posi = boid.getPosicion();
        Vector posj = agentePorEvadir.getPosicion();

        Vector diferencia = posi.clonar();
        diferencia.restar(posj);
        Vector tempDiferencia = diferencia.clonar();

        diferencia = diferencia.unitario();
        diferencia.multiplicarEscalar(distanciaDeseada);

        Vector nuevaPosicion = new Vector(posi.getDimension());

        nuevaPosicion.sumar(posi);
        nuevaPosicion.sumar(diferencia);
        nuevaPosicion.restar(tempDiferencia);

        boid.setPosicion(nuevaPosicion);
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getC2() {
        return c2;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public double getC3() {
        return c3;
    }

    public void setC3(double c3) {
        this.c3 = c3;
    }

    public double getVelMax() {
        return velMax;
    }

    public void setVelMax(double velMax) {
        this.velMax = velMax;
    }

    public double getZonaVirtual() {
        return zonaVirtual;
    }

    public void setZonaVirtual(double zonaVirtual) {
        this.zonaVirtual = zonaVirtual;
    }

    public void setParametroObstaculos(double parametroObstaculos) {
        this.parametroObstaculo = parametroObstaculos;
    }

    public double getParametroObstaculos() {
        return parametroObstaculo;
    }
}
