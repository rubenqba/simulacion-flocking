package tiposagentes;
import java.awt.Graphics;



import Graphics.Forma;
import Vecindades.VecindadObjetivos;
import core.*;
public class Boid extends AgenteMovil {

	private VecindadObjetivos objetivos;
	public Objetivo objetivo;//:PpP
	private Boid mejorDelGrupo;
	private double valorFuncionObjetivo = 0.0;
	
	private boolean haColisionado = false;
	private boolean enEstadoColision = false;
	
	public Boid(double x, double y, AmbienteMovil ambiente) {
		super(x, y, ambiente);		
	}
	
	public Boid(double x, double y, double z, AmbienteMovil ambiente)
	{
		super(x,y,z,ambiente);
	}

	@Override
	public void percibirme(Percepcion p) {
		p.percibir(this);
		
	}

	public String toString()
	{
		Vector pos = this.getPosicion();
		return "Boid("+ pos.get(0)+","+pos.get(1)+")";
	}

	public void setObjetivos(VecindadObjetivos objetivos) {
		this.objetivos = objetivos;
		objetivo = objetivos.get(0);
	}

	public VecindadObjetivos getObjetivos() {
		return objetivos;
	}
		
	public void draw ()
	{			
		Forma form=new Forma();
		form.Dibujar_esfera(this);		
	}
	
	public void pintar(Graphics g)
	{
		
		int cordx = (int) Math.round(this.getPosicion().get(0));
		int cordy = (int) Math.round(getPosicion().get(1));
		int radio = (int) Math.round(getRadio());
		Util.rellenarCirculo(g, cordx, cordy, radio);
	}


	public void setObjetivo(Objetivo objetivo) {
		this.objetivo = objetivo;
	}

	public Objetivo getObjetivo() {
		return objetivo;
	}

	public void setHaColisionado(boolean haColisionado) {
		this.haColisionado = haColisionado;
	}

	public boolean getHaColisionado() {
		return haColisionado;
	}

	public void setEnEstadoColision(boolean enEstadoColision) {
		this.enEstadoColision = enEstadoColision;
	}

	public boolean isEnEstadoColision() {
		return enEstadoColision;
	}

	public void setMejorDelGrupo(Boid mejorDelGrupo) {
		this.mejorDelGrupo = mejorDelGrupo;
	}

	public Boid getMejorDelGrupo() {
		return mejorDelGrupo;
	}

	public void setValorFuncionObjetivo(double valorFuncionObjetivo) {
		this.valorFuncionObjetivo = valorFuncionObjetivo;
	}

	public double getValorFuncionObjetivo() {
		return valorFuncionObjetivo;
	}
}
