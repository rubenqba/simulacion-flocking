package escenarios;

import Vecindades.VecindadObjetivos;
import core.AgenteMovil;
import core.AmbienteMovil;
import core.Movimiento;
import core.Simulacion;
import core.Util;
import core.Vector;
import implementacion.MovimientoBoid;
import tiposagentes.Boid;
import tiposagentes.Objetivo;

public class Escenario3_2 {

    public static void main(String ar[]) {
        /** par�metros de los agentes **/
        double radioAgente = 5;
        double rangoDeInteraccion = 100;
        double radioObstaculos = 20;
        double cantidadAgentes = 32;

        double cantidadGrupo = cantidadAgentes / 2;
        /************************/

        /** parametros del modelo */

        double parametroObstaculos = 1;
        double c1 = 0.05;// inercia
        double c2 = 0.2;//
        double c3 = 0.2;
        double velMax = 4;
        double zonaVirtual = 5;
        /**************************/

        AmbienteMovil ambiente = new AmbienteMovil();

        Objetivo objetivo1 = new Objetivo(300, 150, ambiente);
        VecindadObjetivos objetivos = new VecindadObjetivos();
        objetivos.add(objetivo1);

        objetivo1.setID(1);

        Movimiento m = new MovimientoObjetivo();
        objetivo1.setMov(m);
        ambiente.agregarAgente(objetivo1);

        MovimientoBoid mov = new MovimientoBoid();
        mov.setC1(c1);
        mov.setC2(c2);
        mov.setC3(c3);
        mov.setParametroObstaculos(parametroObstaculos);
        mov.setVelMax(velMax);
        mov.setZonaVirtual(zonaVirtual);

        Vector infIzq = new Vector(0, 200);
        Vector supDer = new Vector(50, 100);

        for (int i = 0; i < cantidadGrupo; i++) {
            Vector v = Util.aleatorioEnArea(infIzq, supDer);

            Boid boid = new Boid(v.get(0), v.get(1), ambiente);
            boid.setMov(mov);
            boid.setRangoInteraccion(rangoDeInteraccion);
            boid.setRadio(radioAgente);
            boid.setObjetivos(objetivos);
            ambiente.agregarAgente(boid);
        }

        infIzq = new Vector(200, 200);
        supDer = new Vector(250, 100);

        for (int i = 0; i < cantidadGrupo; i++) {
            Vector v = Util.aleatorioEnArea(infIzq, supDer);

            Boid boid = new Boid(v.get(0), v.get(1), ambiente);
            boid.setMov(mov);
            boid.setRangoInteraccion(rangoDeInteraccion);
            boid.setRadio(radioAgente);
            boid.setObjetivos(objetivos);
            ambiente.agregarAgente(boid);
        }

        Simulacion s = new Simulacion(ambiente);
        Thread t = new Thread(s);
        t.start();
    }

}

class MovimientoObjetivo implements Movimiento {
    Vector meta = new Vector(1000, 25);

    public void mover(AgenteMovil agente) {
        // TODO Auto-generated method stub
        Vector F = meta.clonar();
        F.restar(agente.getPosicion());
        F.acotarMagnitud(agente.getVelMax());

        agente.getPosicion().sumar(F);

    }

}
