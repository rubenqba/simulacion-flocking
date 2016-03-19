package Graphics;

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.GLUT;

import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;

public class Forma {

    public Forma() {

    }

    public void Dibujar_esfera(Boid X) {
        Vector3D n = new Vector3D(X.getVelocidad().get(0), X.getVelocidad().get(1), X.getVelocidad().get(2));
        Vector3D up = new Vector3D(0, 1, 0);
        up.Normalize();
        n.Normalize();
        Vector3D u = up.Cross(up, n);
        u.Normalize();
        Vector3D v = n.Cross(n, u);
        v.Normalize();

        // create the viewmatrix
        double curmat[] = new double[16];

        curmat[0] = u.get(0);
        curmat[1] = u.get(1);
        curmat[2] = u.get(2);
        curmat[3] = 0;

        curmat[4] = v.get(0);
        curmat[5] = v.get(1);
        curmat[6] = v.get(2);
        curmat[7] = 0;

        curmat[8] = n.get(0);
        curmat[9] = n.get(1);
        curmat[10] = n.get(2);
        curmat[11] = 0;

        curmat[12] = X.getPosicion().get(0);
        curmat[13] = X.getPosicion().get(1);
        curmat[14] = X.getPosicion().get(2);
        curmat[15] = 1;

        // rotation.invert();
        final GL gl = GLContext.getCurrent().getGL();
        final GLUquadric quadratic; // Storage For Our Quadratic Objects
        final GLU glu = new GLU();
        final GLUT glut = new GLUT();
        quadratic = glu.gluNewQuadric();
        glu.gluQuadricNormals(quadratic, GLU.GLU_SMOOTH); // Create Smooth
                                                          // Normals
        glu.gluQuadricTexture(quadratic, false); // Create Texture Coords

        gl.glColor3d(X.objetivo.getColor().get(0), X.objetivo.getColor().get(1), X.objetivo.getColor().get(2));

        float mat_cylinder[] = {
                (float) X.objetivo.getColor().get(0), (float) X.objetivo.getColor().get(1),
                (float) X.objetivo.getColor().get(2), 1.0f
        };

        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_cylinder, 0);
        gl.glPushMatrix();
        gl.glMultMatrixd(curmat, 0);
        // gl.glRotated(90, 0, 0, 1);
        glut.glutSolidCone(X.getRadio(), X.getRadio() * 3, 10, 1);
        glut.glutSolidSphere(X.getRadio(), 10, 10);

        gl.glPopMatrix();

    };

    public void Dibujar_esfera(Objetivo X) {
        Vector3D n = new Vector3D(1, 1, 1);
        Vector3D up = new Vector3D(0, 1, 0);
        up.Normalize();
        n.Normalize();
        Vector3D u = up.Cross(up, n);
        u.Normalize();
        Vector3D v = n.Cross(n, u);
        v.Normalize();

        // create the viewmatrix
        double curmat[] = new double[16];

        curmat[0] = u.get(0);
        curmat[1] = u.get(1);
        curmat[2] = u.get(2);
        curmat[3] = 0;

        curmat[4] = v.get(0);
        curmat[5] = v.get(1);
        curmat[6] = v.get(2);
        curmat[7] = 0;

        curmat[8] = n.get(0);
        curmat[9] = n.get(1);
        curmat[10] = n.get(2);
        curmat[11] = 0;

        curmat[12] = X.getPosicion().get(0);
        curmat[13] = X.getPosicion().get(1);
        curmat[14] = X.getPosicion().get(2);
        curmat[15] = 1;

        // rotation.invert();
        final GL gl = GLContext.getCurrent().getGL();
        final GLUquadric quadratic; // Storage For Our Quadratic Objects
        final GLU glu = new GLU();
        final GLUT glut = new GLUT();
        quadratic = glu.gluNewQuadric();
        glu.gluQuadricNormals(quadratic, GLU.GLU_SMOOTH); // Create Smooth
                                                          // Normals
        glu.gluQuadricTexture(quadratic, false); // Create Texture Coords

        float mat_cylinder[] = {
                (float) X.getColor().get(0), (float) X.getColor().get(1), (float) X.getColor().get(2), 1.0f
        };

        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_cylinder, 0);
        gl.glPushMatrix();
        gl.glMultMatrixd(curmat, 0);
        glut.glutSolidSphere(X.getRadio(), 20, 20);

        gl.glPopMatrix();
    }

    public void Dibujar_esfera(Obstaculo X) {
        Vector3D n = new Vector3D(1, 1, 1);
        Vector3D up = new Vector3D(0, 1, 0);
        up.Normalize();
        n.Normalize();
        Vector3D u = up.Cross(up, n);
        u.Normalize();
        Vector3D v = n.Cross(n, u);
        v.Normalize();

        // create the viewmatrix
        double curmat[] = new double[16];

        curmat[0] = u.get(0);
        curmat[1] = u.get(1);
        curmat[2] = u.get(2);
        curmat[3] = 0;

        curmat[4] = v.get(0);
        curmat[5] = v.get(1);
        curmat[6] = v.get(2);
        curmat[7] = 0;

        curmat[8] = n.get(0);
        curmat[9] = n.get(1);
        curmat[10] = n.get(2);
        curmat[11] = 0;

        curmat[12] = X.getPosicion().get(0);
        curmat[13] = X.getPosicion().get(1);
        curmat[14] = X.getPosicion().get(2);
        curmat[15] = 1;

        // rotation.invert();
        final GL gl = GLContext.getCurrent().getGL();
        final GLUquadric quadratic; // Storage For Our Quadratic Objects
        final GLU glu = new GLU();
        final GLUT glut = new GLUT();
        quadratic = glu.gluNewQuadric();
        glu.gluQuadricNormals(quadratic, GLU.GLU_SMOOTH); // Create Smooth
                                                          // Normals
        glu.gluQuadricTexture(quadratic, false); // Create Texture Coords

        float mat_torus[] = {
                0.75f, 0.75f, 0.0f, 0.5f
        };

        gl.glEnable(GL.GL_BLEND);
        gl.glDepthMask(false);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_torus, 0);
        gl.glPushMatrix();
        gl.glMultMatrixd(curmat, 0);
        glut.glutSolidSphere(X.getRadio(), 20, 20);
        gl.glPopMatrix();
        gl.glDepthMask(true);
        gl.glDisable(GL.GL_BLEND);

    }

}
