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
import metricas.ObservadorMetricas;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;

public class Escenario4 {

    public static void main(String ar[]) throws InterruptedException {

        HashMap<String, Double> parametrosModelo = new HashMap<String, Double>();
        HashMap<String, Double> parametrosAgentes = new HashMap<String, Double>();

        boolean calcularMetricas = true;
        boolean guardarMetricasArchivo = true;
        boolean mostrarSimulacion = true;
        int iteraciones = 400;

        /** par�metros de los agentes **/
        double radioAgente = 3;
        double rangoDeInteraccion = 25;
        double radioObstaculos = 30;
        double cantidadAgentes = 60;
        double cantidadObjetivos = 1;
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
        double zonaVirtual = 15;
        /**************************/
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

        Objetivo objetivo1 = new Objetivo(300, 100, ambiente);

        VecindadObjetivos objetivos = new VecindadObjetivos();
        objetivos.add(objetivo1);

        MovimientoCuatroEsquinas movObjetivo = new MovimientoCuatroEsquinas();
        movObjetivo.setVelMax(velMax);

        objetivo1.setMov(movObjetivo);

        MovimientoBoidMejorado mov = new MovimientoBoidMejorado();
        mov.setC1(c1);
        mov.setC2(c2);
        mov.setC3(c3);
        mov.setParametroObstaculos(parametroObstaculos);
        mov.setVelMax(velMax);
        mov.setZonaVirtual(zonaVirtual);

        Vector infIzq = new Vector(0, 0);
        Vector supDer = new Vector(300, 300);

        for (int i = 0; i < cantidadAgentes; i++) {
            Vector v = Util.aleatorioEnArea(infIzq, supDer);

            Boid boid = new Boid(v.get(0), v.get(1), ambiente);
            boid.setMov(mov);
            boid.setRangoInteraccion(rangoDeInteraccion);
            boid.setRadio(radioAgente);
            boid.setObjetivos(objetivos);
            ambiente.agregarAgente(boid);
        }

        int x = AmbienteMovil.tamx - 100;
        Obstaculo obstaculo1 = new Obstaculo(x - 60, 340, ambiente);
        Obstaculo obstaculo2 = new Obstaculo(x - 60, 430, ambiente);
        Obstaculo obstaculo3 = new Obstaculo(x + 30, 340, ambiente);
        Obstaculo obstaculo4 = new Obstaculo(x + 30, 430, ambiente);

        obstaculo1.setRadio(radioObstaculos);
        obstaculo2.setRadio(radioObstaculos);
        obstaculo3.setRadio(radioObstaculos);
        obstaculo4.setRadio(radioObstaculos);

        ambiente.agregarAgente(objetivo1);

        ambiente.agregarAgente(obstaculo1);
        ambiente.agregarAgente(obstaculo2);
        ambiente.agregarAgente(obstaculo3);
        ambiente.agregarAgente(obstaculo4);

        Simulacion s = new Simulacion(ambiente);

        if (mostrarSimulacion) {
            Thread t = new Thread(s);
            t.start();
        } else {
            ObservadorMetricas oMetricas = null;
            s.setDelaySimulacion(0);
            s.setIteraciones(iteraciones);
            // s.setHabilitarPintado(false);

            if (calcularMetricas) {
                oMetricas = new ObservadorMetricas(maximaExtension, maxPol);
                s.addObservador(oMetricas);
            }

            Thread t = new Thread(s);
            t.start();
            t.join();

            if (calcularMetricas) {
                if (guardarMetricasArchivo) {
                    ManejadorArchivos manejador = new ManejadorArchivos();
                    oMetricas.addPromedios();
                    manejador.guardarEnArchivo("Escenario 4", parametrosAgentes, parametrosModelo, oMetricas);
                }

                oMetricas.mostrarReportesCortos();

            }
        }

    }

}
