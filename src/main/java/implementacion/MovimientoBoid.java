package implementacion;

import Vecindades.VecindadBoid;
import Vecindades.VecindadObjetivos;
import Vecindades.VecindadObstaculos;
import core.*;
import lombok.Getter;
import lombok.Setter;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;

@Setter
@Getter
public class MovimientoBoid implements Movimiento {

    private double c1 = 1;// peso inercial
    private double c1Min = 1;
    private double c2 = 1;// peso mejor posicion
    private double c3 = 1;// peso mejor agente del grupo
    private double velMax = 3;
    private double zonaVirtual = 5;
    private FuncionAptitud F = new FuncionAptitudConcreta();
    private double parametroObstaculos = 1;
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

        primerTermino.multiplicarEscalar(boid.isCourage()? c1 : c1Min);
        segundoTermino.restar(boid.getPosicion());
        segundoTermino.multiplicarEscalar(c2 * RandomGenerator.getInstance().getRandom().nextDouble());
        tercerTermino.restar(boid.getPosicion());
        tercerTermino.multiplicarEscalar(c3 * RandomGenerator.getInstance().getRandom().nextDouble());

        Vector velocidad = new Vector(primerTermino.getDimension());

        velocidad.sumar(primerTermino);
        velocidad.sumar(segundoTermino);
        velocidad.sumar(tercerTermino);

        acotarVelocidad(boid, velocidad);

        return velocidad;
    }

    public Objetivo calcularObjetivo(VecindadObjetivos objetivos, Boid boid, VecindadObstaculos obstaculos) {
        mejorAptitud = Double.POSITIVE_INFINITY;// la mejor aptitud es la mas
        // pequeña
        Objetivo o = null;

        double distancia;
        double aptitud;
        double pesoObstaculo;
        F.setParametroObstaculo(parametroObstaculos);

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

        double distancia;
        double aptitud;
        double pesoObstaculos;
        F.setParametroObstaculo(parametroObstaculos);

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
}
