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

public class Escenario3 {

    public static void main(String ar[]) throws InterruptedException {
        HashMap<String, Double> parametrosModelo = new HashMap<String, Double>();
        HashMap<String, Double> parametrosAgentes = new HashMap<String, Double>();

        boolean sensarEstado = false;
        boolean calcularMetricas = true;
        boolean guardarMetricasArchivo = true;
        boolean mostrarSimulacion = false;
        int iteraciones = 2000;

        /** par�metros de los agentes **/
        double cantidadObjetivos = 1;
        double radioAgente = 3;
        double rangoDeInteraccion = 25;
        double radioObstaculos = 20;
        double cantidadAgentes = 300;
        /************************/

        parametrosAgentes.put("cantidadObjetivos", cantidadObjetivos);
        parametrosAgentes.put("radioAgente", radioAgente);
        parametrosAgentes.put("rangoDeInteraccion", rangoDeInteraccion);
        parametrosAgentes.put("radioObstaculos", radioObstaculos);
        parametrosAgentes.put("cantidadAgentes", cantidadAgentes);

        /** parametros del modelo */
        double parametroObstaculos = 1;
        double c1 = 0.01;
        double c2 = 0.2;
        double c3 = 0.2;
        double velMax = 4;
        double zonaVirtual = 15;// separacion

        double maximaExtension = 610;
        double maxPolarizacion = 180;
        /**************************/

        parametrosModelo.put("parametroObstaculos", parametroObstaculos);
        parametrosModelo.put("c1", c1);
        parametrosModelo.put("c2", c2);
        parametrosModelo.put("c3", c3);
        parametrosModelo.put("velMax", velMax);
        parametrosModelo.put("zonaVirtual", zonaVirtual);
        parametrosModelo.put("MaxExt", maximaExtension);
        parametrosModelo.put("MaxPol", maxPolarizacion);

        AmbienteMovil ambiente = new AmbienteMovil();

        Objetivo objetivo1 = new Objetivo(300, 100, ambiente);

        VecindadObjetivos objetivos = new VecindadObjetivos();
        objetivos.add(objetivo1);

        MovimientoCuatroEsquinas movObjetivo = new MovimientoCuatroEsquinas();
        movObjetivo.setVelMax(velMax + 5);

        objetivo1.setMov(movObjetivo);

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
            Vector v = Util.aleatorioEnArea(infIzq, supDer);

            Boid boid = new Boid(v.get(0), v.get(1), ambiente);
            boid.setMov(mov);
            boid.setRangoInteraccion(rangoDeInteraccion);
            boid.setRadio(radioAgente);
            boid.setObjetivos(objetivos);
            ambiente.agregarAgente(boid);
        }

        ambiente.agregarAgente(objetivo1);

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
                oMetricas = new ObservadorMetricas(maximaExtension, maxPolarizacion);

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
                    manejador.guardarEnArchivo("Escenario 3", parametrosAgentes, parametrosModelo, oMetricas);
                }

                oMetricas.mostrarReportesCortos();
                // oMetricas.actualizar();
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

}
