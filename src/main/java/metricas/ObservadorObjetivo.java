package metricas;

import tiposagentes.Boid;
import core.AgenteMovil;
import core.Vector;

public class ObservadorObjetivo extends ObservadorVariablesEstado{

	protected double[] datosPorAlmacenar(AgenteMovil a)
	{
		
		Boid boid = (Boid)a;
		Vector posO = boid.getObjetivo().getPosicion();
		int iteracion = getNumIteracion();
		
		double datos[] = {posO.get(0), posO.get(1), iteracion };
		iteracion +=1;
		setNumIteracion(iteracion);
		
		return datos;
	}
	
}
