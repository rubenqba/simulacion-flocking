package metricas;

import Vecindades.VecindadObjetivos;
import core.AgenteMovil;
import core.AmbienteMovil;
import core.Vector;
import org.junit.Before;
import org.junit.Test;
import tiposagentes.Boid;
import tiposagentes.Objetivo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by ruben.bressler on 9/12/2016.
 */
public class MetricaTest {

    private Metrica metrica;
    private AmbienteMovil ambiente;

    @Before
    public void setUp() throws Exception {
        ambiente = new AmbienteMovil();
        metrica = new Metrica(ambiente);
    }

    @Test
    public void determinarColisionesTest() throws Exception {
        double radio = 3;
        AmbienteMovil ambiente = new AmbienteMovil();

        ArrayList<AgenteMovil> agentes = new ArrayList<AgenteMovil>();
        agentes.add(new Boid(0, 0, ambiente));
        agentes.add(new Boid(0, 0, ambiente));
        agentes.add(new Boid(0, 0, ambiente));
        agentes.add(new Boid(16, 0, ambiente));
        agentes.add(new Boid(6.1, 0, ambiente));

        agentes.stream().forEach(a -> {
            a.setRadio(radio);
            ambiente.agregarAgente(a);
        });


        metrica.determinarColisiones();

        for (AgenteMovil a : agentes) {
            if (a instanceof Boid) {
                Boid b = (Boid) a;
                System.out.println();
                System.out.println("Agente " + b);
                System.out.println("Ha colisionado " + b.isHaColisionado());
                System.out.println("Estado colision " +
                        b.isEnEstadoColision());
            }
        }
    }

    @Test
    public void estanColisionandoTest() throws Exception {
        AgenteMovil a = new Boid(0, 0, ambiente);
        AgenteMovil b = new Boid(0, 0, ambiente);

        assertThat(Arrays.asList(a, b).stream()
                .map(e -> e.getRadio())
                .collect(Collectors.toList()), everyItem(equalTo(4d)));
        assertThat(metrica.estanColisionando(a, b), equalTo(true));

        a = new Boid(0, 0, ambiente);
        b = new Boid(8, 0, ambiente);
        assertThat(a.getPosicion().distancia(b.getPosicion()), equalTo(a.getRadio() + b.getRadio()));
        assertThat(metrica.estanColisionando(a, b), equalTo(true));

        a = new Boid(0, 0, ambiente);
        b = new Boid(10, 0, ambiente);
        assertThat(a.getPosicion().distancia(b.getPosicion()), greaterThan(a.getRadio() + b.getRadio()));
        assertThat(metrica.estanColisionando(a, b), equalTo(false));
    }

    @Test
    public void calcularMetricasTest() {
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

        assertThat(m.getFactorColisiones(), greaterThan(0d));
    }
}