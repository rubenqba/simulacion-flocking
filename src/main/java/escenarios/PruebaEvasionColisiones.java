package escenarios;

import metricas.ObservadorEstadoAmbiente;
import core.Vector;

import Vecindades.VecindadObjetivos;
import implementacion.MovimientoBoid;
import implementacion.MovimientoBoidMejorado;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;

public class PruebaEvasionColisiones {
	
	public static void main(String ar[])
	{
		int cantAgentes = 2;
		double posx = 200;
		double posy = 200;
		double delta =150;
		
		double c1 = 0.001;//inercia
		double c2 = 0.01;//
		double c3 = 1;
		double velMax = 1;
		double zonaVirtual = 4;
		
		double radioAgente = 2.0;
		double radioObstaculo = 30;
		
		double radioPercepcion = 70; 
		AmbienteMovil ambiente = new AmbienteMovil();
		
		Obstaculo obs = new Obstaculo(posx, posy, ambiente);
		obs.setRadio(radioObstaculo);
		
		Objetivo o = new Objetivo(posx,posy,ambiente);
		VecindadObjetivos os = new VecindadObjetivos();
		os.add(o);
		
		MovimientoBoidMejorado m = new MovimientoBoidMejorado();
		m.setC1(c1);
		m.setC2(c2);
		m.setC3(c3);
		m.setVelMax(velMax);
		m.setZonaVirtual(zonaVirtual);				
					
		System.out.println("Estado inicial ");
		for(int i = 0; i < cantAgentes; i++)
		{			
			Vector pos = Util.aleatorioEnArea(new Vector(posx - delta, posy - delta), new Vector(posx + delta, posy + delta));
			Boid boid = new Boid(pos.get(0), pos.get(1),ambiente);
			boid.setRadio(radioAgente);
			boid.setRangoInteraccion(radioPercepcion);
			
			boid.setObjetivos(os);
			ambiente.agregarAgente(boid);
			boid.setMov(m);
			
			
			System.out.println(boid);
		}				
	
		ambiente.agregarAgente(obs);
		ambiente.agregarAgente(o);
		Simulacion s = new Simulacion(ambiente);
		//s.addObservador(new ObservadorEstadoAmbiente());
		new Thread(s).start();
	}

}
