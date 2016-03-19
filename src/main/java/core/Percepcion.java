package core;
import Vecindades.*;
import tiposagentes.*;

public class Percepcion {
	private VecindadBoid vecindad;
	private VecindadObstaculos obstaculos;
	//private VecindadObjetivos objetivos;
	private AgenteMovil agente;
	
	
	public void calcularVecindad(AgenteMovil agente)
	{
		vecindad = new VecindadBoid();
		obstaculos = new VecindadObstaculos();
		
		this.agente = agente;
		AmbienteMovil ambiente = agente.getAmbiente();
		
		for(AgenteMovil vecino: ambiente.getAgentes())
		{
			if(vecino == agente)
				continue;
						
				vecino.percibirme(this);			
		}						
	}

	public void percibir(Boid boid)
	{
		double rango = agente.getRangoInteraccion();
		double distancia =  boid.getPosicion().distancia(agente.getPosicion());
		
		if(distancia < rango)
		{
			vecindad.add(boid);
		}
		
	}
	
	public void percibir(Obstaculo obstaculo)
	{
		double rango = agente.getRangoInteraccion();
		Vector puntoMasCercanoAlObstaculo = Util.calcularPuntoMasCercanoAlObstaculo(agente.getPosicion(), obstaculo.getPosicion(),obstaculo.getRadio());
		double distancia = agente.getPosicion().distancia(puntoMasCercanoAlObstaculo);
		
		if(distancia < rango)
		{
			obstaculos.add(obstaculo);
		}
		
	}
	
	public void percibir(Objetivo objetivo)
	{
		
	}
	
	
	
	public VecindadBoid getVecindadBoid()
	{
		return vecindad;
	}
	
	public VecindadObstaculos getVecindadObstaculo()
	{
		return obstaculos;
	}
	
	public static void main(String ar[])
	{
		AmbienteMovil ambiente = new AmbienteMovil();
		
		AgenteMovil boid = new Boid(0,0,ambiente);
		boid.setRangoInteraccion(2);
		
		AgenteMovil obs = new Obstaculo(2.5,0,ambiente);
		AgenteMovil obs2 = new Obstaculo(-2.9,0,ambiente);
		AgenteMovil obs3 = new Obstaculo(3.5,0,ambiente);
		
		obs.setRadio(1);
		obs2.setRadio(1);
		obs3.setRadio(1);
		
		
		ambiente.agregarAgente(boid);
		ambiente.agregarAgente(obs);
		ambiente.agregarAgente(obs2);
		ambiente.agregarAgente(obs3);
		
		Percepcion p = boid.calcularPercepcion();				
	
	}
}
