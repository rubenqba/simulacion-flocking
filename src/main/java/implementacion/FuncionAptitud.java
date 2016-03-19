package implementacion;

import core.*;
import Vecindades.VecindadObstaculos;

interface FuncionAptitud {
	
	public double funcionAptitud(double distancia, double pesoObstaculos);
	public double pesoObstaculos(VecindadObstaculos obstaculos, AgenteMovil agente, AgenteMovil objetivo);
	public double getParametroObstaculo();
	public void setParametroObstaculo(double parametroObstaculo);
}
