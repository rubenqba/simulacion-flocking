package core;

import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by ruben.bressler on 9/19/2016.
 */
public class UtilTest {
    @Test
    public void anguloEntreVectores() throws Exception {
        Vector A = new Vector(3, 4);
        Vector B = new Vector(-8, 6);

        assertThat(Util.anguloEntreVectores(A, B), equalTo(90d));

        A = new Vector(3, 0);
        B = new Vector(5, 5);

        assertThat(Util.anguloEntreVectores(A, B), closeTo(45, 10E-10));
    }

}