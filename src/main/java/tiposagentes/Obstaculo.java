package tiposagentes;

import Graphics.Forma;
import core.AgenteMovil;
import core.AmbienteMovil;
import core.Percepcion;
import core.Vector;

public class Obstaculo extends AgenteMovil{

	public Obstaculo(double x, double y, AmbienteMovil ambiente) {
		super(x, y, ambiente);
		// TODO Auto-generated constructor stub
	}
	
	public Obstaculo(double x, double y , double z, AmbienteMovil ambiente)
	{
		super(x,y,z, ambiente);
	}

	@Override
	public void percibirme(Percepcion p) {
		p.percibir(this);
		
	}

	public String toString()
	{
		Vector pos = this.getPosicion();
		return "Obstaculo("+ pos.get(0)+","+pos.get(1)+")";
	}
	
	public void draw ()
	{				
		Forma form=new Forma();
		form.Dibujar_esfera(this);

	}
}
