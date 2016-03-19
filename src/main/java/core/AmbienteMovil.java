package core;


import java.util.ArrayList;

public class AmbienteMovil extends Ambiente{
	
	ArrayList<AgenteMovil> agentes;
	
	public static int tamx = 1000;
	public static int tamy = 700;

	public AmbienteMovil()
	{
		agentes = new ArrayList<AgenteMovil>();
	}
	
	public void agregarAgente(AgenteMovil ag)
	{
		agentes.add(ag);
	}
	
	public void quitarAgente(AgenteMovil ag)
	{
		agentes.remove(ag);
	}

	public ArrayList<AgenteMovil> getAgentes()
	{
		return agentes;
	}
	
	
}
