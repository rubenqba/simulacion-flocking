package escenarios;

import java.util.HashMap;

import Vecindades.VecindadObjetivos;
import archivos.ManejadorArchivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;
import implementacion.MovimientoBoidMejorado;
import implementacion.MovimientoCuatroEsquinas;
import metricas.ObservadorMejorAgente;
import metricas.ObservadorMetricas;
import metricas.ObservadorObjetivo;
import metricas.ObservadorVariablesEstado;
import metricas.ObservadorVelocidad;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;

public class Escenario3_1_1 {

    public static void main(String ar[]) throws InterruptedException {
        HashMap<String, Double> parametrosModelo = new HashMap<String, Double>();
        HashMap<String, Double> parametrosAgentes = new HashMap<String, Double>();

        boolean sensarEstado = false;
        boolean calcularMetricas = true;
        boolean guardarMetricasArchivo = true;
        boolean mostrarSimulacion = false;
        int iteraciones = 1000;

        /** par�metros de los agentes **/
        double cantidadObjetivos = 2;
        double radioAgente = 3;
        double rangoDeInteraccion = 25;
        double radioObstaculos = 30;
        double cantidadAgentes = 400;

        /************************/

        parametrosAgentes.put("cantidadObjetivos", cantidadObjetivos);
        parametrosAgentes.put("radioAgente", radioAgente);
        parametrosAgentes.put("rangoDeInteraccion", rangoDeInteraccion);
        parametrosAgentes.put("radioObstaculos", radioObstaculos);
        parametrosAgentes.put("cantidadAgentes", cantidadAgentes);

        /** parametros del modelo */

        double parametroObstaculos = 1;
        double c1 = 0.01;// inercia
        double c2 = 0.2;//
        double c3 = 0.2;
        double velMax = 4;
        double zonaVirtual = 15;

        double maximaExtension = 610;
        double maxPol = 180;
        /**************************/

        parametrosModelo.put("parametroObstaculos", parametroObstaculos);
        parametrosModelo.put("c1", c1);
        parametrosModelo.put("c2", c2);
        parametrosModelo.put("c3", c3);
        parametrosModelo.put("velMax", velMax);
        parametrosModelo.put("zonaVirtual", zonaVirtual);
        parametrosModelo.put("MaxExt", maximaExtension);
        parametrosModelo.put("MaxPol", maxPol);

        AmbienteMovil ambiente = new AmbienteMovil();

        int delta = 100;
        Obstaculo obs1 = new Obstaculo(delta, AmbienteMovil.tamy / 2, ambiente);
        Obstaculo obs2 = new Obstaculo(AmbienteMovil.tamx / 2, delta, ambiente);
        Obstaculo obs3 = new Obstaculo(AmbienteMovil.tamx / 2, AmbienteMovil.tamy - delta, ambiente);
        Obstaculo obs4 = new Obstaculo(AmbienteMovil.tamx - delta, AmbienteMovil.tamy / 2, ambiente);

        obs1.setRadio(radioObstaculos);
        obs2.setRadio(radioObstaculos);
        obs3.setRadio(radioObstaculos);
        obs4.setRadio(radioObstaculos);

        ambiente.agregarAgente(obs1);
        ambiente.agregarAgente(obs2);
        ambiente.agregarAgente(obs3);
        ambiente.agregarAgente(obs4);

        Objetivo objetivo1 = new Objetivo(100, 100, ambiente);
        Objetivo objetivo2 = new Objetivo(300, 300, ambiente);
        // Objetivo objetivo3 = new Objetivo(350,350,ambiente);

        objetivo1.setID(1);
        objetivo2.setID(2);

        VecindadObjetivos objetivos = new VecindadObjetivos();
        objetivos.add(objetivo1);
        objetivos.add(objetivo2);
        // objetivos.add(objetivo3);

        MovimientoCuatroEsquinas movObjetivo = new MovimientoCuatroEsquinas();
        movObjetivo.setVelMax(velMax + 5);
        objetivo1.setMov(movObjetivo);

        MovimientoCuatroEsquinas movObjetivo2 = new MovimientoCuatroEsquinas();
        movObjetivo2.setEsquinaSupIzq(new Vector(200, 200));
        movObjetivo2.setEsquinaSupDer(new Vector(AmbienteMovil.tamx - 200, 200));
        movObjetivo2.setEsquinaInfIzq(new Vector(300, AmbienteMovil.tamy - 200));
        movObjetivo2.setEsquinaInfDer(new Vector(AmbienteMovil.tamx - 200, AmbienteMovil.tamy - 200));
        movObjetivo2.setVelMax(velMax + 5);
        objetivo2.setMov(movObjetivo2);

        /*
         * MovimientoCuatroEsquinas movObjetivo3 = new
         * MovimientoCuatroEsquinas();
         * movObjetivo3.setEsquinaSupIzq(new Vector2D(300,300));
         * movObjetivo3.setEsquinaSupDer(new
         * Vector2D(AmbienteMovil.tamx-300,300));
         * movObjetivo3.setEsquinaInfIzq(new
         * Vector2D(300,AmbienteMovil.tamy-300));
         * movObjetivo3.setEsquinaInfDer(new Vector2D(AmbienteMovil.tamx-300,
         * AmbienteMovil.tamy-300));
         * movObjetivo3.setVelMax(velMax+5);
         * objetivo3.setMov(movObjetivo3);
         */

        MovimientoBoidMejorado mov = new MovimientoBoidMejorado();
        mov.setC1(c1);
        mov.setC2(c2);
        mov.setC3(c3);
        mov.setParametroObstaculos(parametroObstaculos);
        mov.setVelMax(velMax);
        mov.setZonaVirtual(zonaVirtual);

        Vector infIzq = new Vector(0, 0);
        Vector supDer = new Vector(700, 700);

        for (int i = 0; i < cantidadAgentes; i++) {
            boolean estaBien;
            Vector v;
            do {// validando que la posicion no este ocupada por un obstaculo
                v = Util.aleatorioEnArea(infIzq, supDer);
                boolean b1 = posicionValida(v, obs1);
                boolean b2 = posicionValida(v, obs2);
                boolean b3 = posicionValida(v, obs3);
                boolean b4 = posicionValida(v, obs4);

                estaBien = b1 && b2 && b3 && b4;
            } while (!(estaBien));

            Boid boid = new Boid(v.get(0), v.get(1), ambiente);
            boid.setMov(mov);
            boid.setRangoInteraccion(rangoDeInteraccion);
            boid.setRadio(radioAgente);
            boid.setObjetivos(objetivos);
            ambiente.agregarAgente(boid);
        }

        ambiente.agregarAgente(objetivo1);
        ambiente.agregarAgente(objetivo2);
        // ambiente.agregarAgente(objetivo3);

        Simulacion s = new Simulacion(ambiente);

        if (mostrarSimulacion) {

            Thread t = new Thread(s);
            t.start();

        } else {
            s.setDelaySimulacion(0);
            s.setIteraciones(iteraciones);
            s.setHabilitarPintado(false);

            ObservadorMetricas oMetricas = null;
            ObservadorVariablesEstado oEstado = null;
            ObservadorVelocidad oVel = null;
            ObservadorObjetivo oO = null;
            ObservadorMejorAgente mA = null;

            if (calcularMetricas) {
                oMetricas = new ObservadorMetricas(maximaExtension, maxPol);

                s.addObservador(oMetricas);
            }

            if (sensarEstado) {
                oEstado = new ObservadorVariablesEstado();
                oVel = new ObservadorVelocidad();
                oO = new ObservadorObjetivo();
                mA = new ObservadorMejorAgente();

                s.addObservador(oEstado);
                s.addObservador(oVel);
                s.addObservador(oO);
                s.addObservador(mA);
            }

            Thread t = new Thread(s);
            t.start();
            t.join();

            if (calcularMetricas) {
                if (guardarMetricasArchivo) {
                    ManejadorArchivos manejador = new ManejadorArchivos();
                    oMetricas.addPromedios();
                    manejador.guardarEnArchivo("Escenario 3_1_1", parametrosAgentes, parametrosModelo, oMetricas);
                }

                oMetricas.mostrarReportesCortos();

            }

            if (sensarEstado) {
                ManejadorArchivos manejador = new ManejadorArchivos();
                manejador.guardarEnArchivo("Posicion ", parametrosAgentes, parametrosModelo, oEstado);
                manejador.guardarEnArchivo("Velocidad ", parametrosAgentes, parametrosModelo, oVel);
                manejador.guardarEnArchivo("Mejor agente ", parametrosAgentes, parametrosModelo, mA);
                manejador.guardarEnArchivo("Objetivo  ", parametrosAgentes, parametrosModelo, oO);
            }
        }
    }

    public static boolean posicionValida(Vector pos, Obstaculo b) {
        double distancia = pos.distancia(b.getPosicion());
        double distanciaMenor = b.getRadio();

        if (distancia > distanciaMenor) {
            return true;
        }

        return false;
    }

}
