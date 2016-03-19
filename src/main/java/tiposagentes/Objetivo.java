package tiposagentes;

import java.awt.Graphics;

import Graphics.Forma;
import core.AgenteMovil;
import core.AmbienteMovil;
import core.Percepcion;
import core.Util;
import core.Vector;

public class Objetivo extends AgenteMovil{

	private Vector color;
	private int ID = 1;
	
	public Objetivo(double x, double y, AmbienteMovil ambiente) {
		super(x, y, ambiente);
		
	}
	
	public Objetivo(double x, double y, double z, AmbienteMovil ambiente)
	{
		super(x,y,z,ambiente);
	}

	@Override
	public void percibirme(Percepcion p) {
	//	p.percibir(this);		
	}

	public String toString()
	{
		Vector pos = this.getPosicion();
		return "Objetivo("+ pos.get(0)+","+pos.get(1)+")";
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
		
		Util.pintarRomboSinRelleno(g, cordx, cordy, radio*2, radio*2);
	}


	public void setColor(double r, double g, double b) {
		color=new Vector(r,g,b);
	}

	public Vector getColor() {
		return color;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getID() {
		return ID;
	}
}
