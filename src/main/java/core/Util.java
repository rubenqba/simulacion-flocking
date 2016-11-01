package core;

import java.awt.*;

import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;

public class Util {

    /*
     * retorna verdadero si el agente A puede ver al agente C;
     * es decir, el agente B no obstruye la visibilidad del agente A
     */
    public static boolean estaEnLineaDeVista(AgenteMovil A, AgenteMovil B, AgenteMovil C) {
        double radioB = B.getRadio();
        Vector BA = B.getPosicion().clonar();
        Vector CA = C.getPosicion().clonar();

        BA.restar(A.getPosicion());
        CA.restar(A.getPosicion());

        double normaBA = BA.norma();
        double normaCA = CA.norma();

        if (normaBA == 0)
            return false;

        if (normaCA == 0)
            return true;

        double anguloNecesario = Math.atan(radioB / normaBA);
        double productoBACA = BA.productoEscalar(CA);


        double angulo = Math.acos(productoBACA / (normaBA * normaCA));

        if (angulo > anguloNecesario)
            return true;

        return false;
    }

    /*
     * Recibe un vector de posicion y
     * el centro y radio de una esfera.
     * La esfera representa un obst�culo
     * Retorna el punto m�s cercano al agente y que pertenece a la esfera
     */
    public static Vector calcularPuntoMasCercanoAlObstaculo(Vector posicionAgente, Vector posObstaculo, double
            radioObstaculo) {
        Vector diferencia = posObstaculo.clonar();
        diferencia.restar(posicionAgente);

        double distancia = posicionAgente.distancia(posObstaculo);
        double r = 1 - (radioObstaculo / distancia);

        diferencia.multiplicarEscalar(r);

        Vector puntoMasCercano = posicionAgente.clonar();

        puntoMasCercano.sumar(diferencia);

        return puntoMasCercano;
    }

    public static Vector aleatorioEnArea(Vector esquinaInferiorIzq, Vector esquinaSupDer) {
        int dimension = esquinaInferiorIzq.getDimension();

        double cord[] = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            double transi = esquinaInferiorIzq.get(i);
            double maxi = esquinaSupDer.get(i) - transi;
            cord[i] = Random.getInstance().getRandom().nextDouble() * maxi;
            cord[i] += transi;
        }

        return new Vector(cord);
    }

    public static Vector aleatorioEnVolumen(Vector vol) {
        int dimension = vol.getDimension();

        double cord[] = new double[dimension];
        for (int i = 0; i < dimension; i++) {

            double maxi = vol.get(i);
            cord[i] = Random.getInstance().getRandom().nextDouble() * maxi;
            cord[i] = cord[i] - maxi / 2;
        }

        return new Vector(cord);
    }

    public static void pintarCirculo(Graphics g, int cordx, int cordy, int radio, Color color) {
        Graphics2D g2 = (Graphics2D) g;

        int x = cordx - radio;
        int y = cordy - radio;
        int ancho, alto;

        ancho = alto = radio * 2;
        g2.setColor(color);
        g2.fillOval(x, y, ancho, alto);
    }

    public static void pintarRombo(Graphics g, int cordx, int cordy, int ancho, int alto, Color color) {
        int deltaX = (int) (ancho / 2.0);
        int deltaY = (int) (alto / 2.0);


        int X[] = {cordx, cordx + deltaX, cordx, cordx - deltaX};
        int Y[] = {cordy - deltaY, cordy, cordy + deltaY, cordy};

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.fillPolygon(X, Y, 4);
    }


    public static double anguloEntreVectores(Vector A, Vector B) {
        if (A.equals(B)) return 0.0;

        double normaA = A.norma();
        double normaB = B.norma();
        double temp = 0.0;

        if (normaA == 0.0 || normaB == 0.0) {
            return 0.0;
        }

        double a = A.productoEscalar(B) / (normaA * normaB);

        double rad = Math.acos(a);

        return Math.toDegrees(rad);
    }

    public static void main(String ar[]) {
        AmbienteMovil ambiente = new AmbienteMovil();
        Boid boid = new Boid(100, 100, ambiente);
        Obstaculo obs = new Obstaculo(150, 100, ambiente);
        Objetivo objetivo = new Objetivo(200, 120, ambiente);

        boid.setRadio(10);
        obs.setRadio(10);
        objetivo.setRadio(10);

        boolean verdad = Util.estaEnLineaDeVista(boid, obs, objetivo);
        System.out.println(verdad);

        ambiente.agregarAgente(boid);
        ambiente.agregarAgente(obs);
        ambiente.agregarAgente(objetivo);

        Simulacion s = new Simulacion(ambiente);
        Thread t = new Thread(s);
        t.start();

    }
}
