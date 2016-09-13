package core;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by ruben.bressler on 9/12/2016.
 */
public class VectorTest {

    @Test
    public void getDimension() throws Exception {
        Vector u = new Vector(4d, 5d, 2d);
        assertThat(u.getDimension(), equalTo(3));

        u = new Vector(7);
        assertThat(u.getDimension(), equalTo(7));
    }

    @Test
    public void sumar() throws Exception {
        Vector u = new Vector(-2d, 5d);
        Vector v = new Vector(3d, -1d);
        u.sumar(v);

        assertThat(u, equalTo(new Vector(1d, 4d)));
    }

    @Test
    public void restar() throws Exception {
        Vector u = new Vector(-2d, 5d);
        Vector v = new Vector(3d, -1d);
        u.restar(v);

        assertThat(u, equalTo(new Vector(-5d, 6d)));
    }

    @Test
    public void multiplicarEscalar() throws Exception {
        Vector u = new Vector(-2d, 5d);
        u.multiplicarEscalar(4);

        assertThat(u, equalTo(new Vector(-8d, 20d)));
    }

    @Test
    public void modulo() throws Exception {
        Vector u = new Vector(3d, 4d);
        assertThat(u.norma(), equalTo(5d));
    }

    @Test
    public void distancia() throws Exception {
        Vector u = new Vector(-2d, 5d);
        Vector v = new Vector(3d, -1d);

        assertThat(u.distancia(v), equalTo(Math.sqrt(61d)));
        assertThat(v.distancia(u), equalTo(Math.sqrt(61d)));
    }

    @Test
    public void productoEscalar() throws Exception {
        Vector u = new Vector(-2d, 5d);
        Vector v = new Vector(3d, -1d);

        assertThat(u.productoEscalar(v), equalTo(-11d));
        assertThat(v.productoEscalar(u), equalTo(-11d));
        assertThat(u.productoEscalar(u), equalTo(29d));
        assertThat(v.productoEscalar(v), equalTo(10d));
    }

    @Test
    public void clonar() throws Exception {
        Vector u = new Vector(-2d, 5d);
        assertThat(u.clonar(), equalTo(new Vector(-2d, 5d)));
    }

    @Test
    public void equals() throws Exception {

    }

    @Test
    public void acotarMagnitud() throws Exception {

    }

    @Test
    public void unitario() throws Exception {
        Vector u = new Vector(2d, 0d);
        assertThat(u.unitario(), equalTo(new Vector(1d, 0d)));
    }

}