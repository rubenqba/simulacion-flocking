package Graphics;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;

import core.AgenteMovil;
import core.AmbienteMovil;

public class Grafico implements GLEventListener, KeyListener{
	private final Camera cam=new Camera();
	static GLU glu = new GLU();
	static GLCanvas canvas = new GLCanvas();
	static Frame frame = new Frame("Jogl 3D Shape/Rotation");
	static Animator animator = new Animator(canvas);
	private AmbienteMovil ambiente=new AmbienteMovil();

	public Grafico(AmbienteMovil X ){
		this.ambiente=X;
		canvas.addGLEventListener(this);
		frame.add(canvas);
		frame.setSize(640, 480);
		frame.setUndecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		frame.setVisible(true);
		animator.start();
		canvas.requestFocus();
	}

	public void displayChanged(GLAutoDrawable gLDrawable, 
			boolean modeChanged, boolean deviceChanged) {
	}

	public void display(GLAutoDrawable gLDrawable) {
		final GL gl = GLContext.getCurrent().getGL();
		final GLUquadric quadratic;   // Storage For Our Quadratic Objects
		final GLU glu = new GLU();
		final GLUT glut = new GLUT();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		gl.glMatrixMode(GL.GL_PROJECTION);   //select the Projection matrix
		gl.glPushMatrix();                   //save the current projection matrix
		gl.glLoadIdentity();                 //reset the current projection matrix to creates a new Orthographic projection
		//Creates a new orthographic viewing volume
		gl.glOrtho(0, 1440, 0, 900, -1, 1);

		/*
		 * Select, save and reset the modelview matrix.
		 * Modelview matrix stack store transformation like translation, rotation ...
		 */
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		loadMatrix();


		cam.Update();
		glu.gluLookAt(cam.camara_x, cam.camara_y, cam.camara_z, cam.target_x, cam.target_y, cam.target_z, cam.camaraUP_x, cam.camaraUP_y, cam.camaraUP_z);
		for(AgenteMovil agente:ambiente.getAgentes())
		{			
			agente.draw();
			
		}
		float mat_cylinder[] =
	    { 0.0f, 0.75f, 0.75f, 0.15f };
	    gl.glEnable(GL.GL_BLEND);
	    gl.glDepthMask(false);
	    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_cylinder, 0);
	   
	    glut.glutSolidCube(100.0f);// (1.0, 2.0);
	    float mat_torus[] =
	    { 0.75f, 0.75f, 0.0f, 1.0f };
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_torus, 0);
	    glut.glutWireCube(100.0f);
	    gl.glDepthMask(true);
	    gl.glDisable(GL.GL_BLEND);
	    
	   
	}



	public static float NEAR = -1;
	public static float FAR = 1;

	/**
	 * Switch to orthographic projection<BR>
	 * The current projection and modelview matrix are saved (push).<BR>
	 * You can loads projection and modelview matrices with endOrtho
	 * @see #endOrtho()
	 */
	public static void beginOrtho()
	{
		final GLDrawable glDrawable = GLContext.getCurrent().getGLDrawable();
		final GL gl = GLContext.getCurrent().getGL();

		/*
		 * We save the current projection matrix and we define a viewing volume
		 * in the orthographic mode.
		 * Projection matrix stack defines how the scene is projected to the screen.
		 */
		gl.glMatrixMode(GL.GL_PROJECTION);   //select the Projection matrix
		gl.glPushMatrix();                   //save the current projection matrix
		gl.glLoadIdentity();                 //reset the current projection matrix to creates a new Orthographic projection
		//Creates a new orthographic viewing volume
		gl.glOrtho(0, glDrawable.getWidth(), 0, glDrawable.getHeight(), NEAR, FAR);

		/*
		 * Select, save and reset the modelview matrix.
		 * Modelview matrix stack store transformation like translation, rotation ...
		 */
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
	}

	/**
	 * Load projection and modelview matrices previously saved by the method beginOrtho
	 * @see #beginOrtho()
	 */
	public static void loadMatrix()
	{
		final GL gl = GLContext.getCurrent().getGL();

		//Select the Projection matrix stack
		gl.glMatrixMode(GL.GL_PROJECTION);
		//Load the previous Projection matrix (Generaly, it is a Perspective projection)
		gl.glPopMatrix();

		//Select the Modelview matrix stack
		gl.glMatrixMode(GL.GL_MODELVIEW);
		//Load the previous Modelview matrix
		gl.glPopMatrix();
	}

	public void init(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
	    gl.glDisable ( GL.GL_BLEND );
	    gl.glEnable ( GL.GL_TEXTURE_2D );
	    gl.glEnable ( GL.GL_DEPTH_TEST );
	    gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
		
		
	    gl.glDepthFunc ( GL.GL_LEQUAL );
	    gl.glEnable(GL.GL_ALPHA_TEST);
	    gl.glAlphaFunc ( GL.GL_GREATER, 0.1f );
	    gl.glPolygonMode ( GL.GL_FRONT, GL.GL_FILL );
		
		float position[] = { 0.0f, -3.0f, 10.0f, 0.0f };
		float position2[] = { 0.0f, 3.0f, 10.0f, 0.0f };
	    float local_view[] =   { 0.0f };
	    
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position2, 0);
	    gl.glLightModelfv(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, local_view, 0);
	 
	    
	    gl.glEnable(GL.GL_LIGHTING);
	    gl.glEnable(GL.GL_LIGHT0);
	    gl.glEnable(GL.GL_AUTO_NORMAL);
	    gl.glEnable(GL.GL_NORMALIZE);
	    
	    
	    float mat_ambient[] =
	    { 0.0f, 0.0f, 0.0f, 0.15f };
	    float mat_specular[] =
	    { 1.0f, 1.0f, 1.0f, 0.15f };
	    float mat_shininess[] =
	    { 15.0f };

	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess, 0);
		gLDrawable.addKeyListener(this);
	}

	public void reshape(GLAutoDrawable gLDrawable, int x, 
			int y, int width, int height) {
		GL gl = gLDrawable.getGL();
		if(height <= 0) {
			height = 1;
		}
		float h = (float)width / (float)height;
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(50.0f, h, 1.0, 1000.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			exit();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			cam.Rotate(0.1f);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			cam.Rotate(-0.1f);
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			cam.Zoom(-1);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			cam.Zoom(1);
		}
		if(e.getKeyCode() == KeyEvent.VK_M) {
			cam.Elevate(-.1f);
		}
		if(e.getKeyCode() == KeyEvent.VK_N) {
			cam.Elevate(.1f);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public static void exit(){
		animator.stop();
		frame.dispose();
		System.exit(0);
	}

}
