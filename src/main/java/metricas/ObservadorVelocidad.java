package metricas;

import core.AgenteMovil;
import core.Vector;

public class ObservadorVelocidad extends ObservadorVariablesEstado{
	
	protected double[] datosPorAlmacenar(AgenteMovil a)
	{
		Vector vel = a.getVelocidad();
		int iteracion = getNumIteracion();
		
		double datos[] = {vel.get(0), vel.get(1), iteracion };
		iteracion +=1;
		setNumIteracion(iteracion);
		
		return datos;
	}

}
