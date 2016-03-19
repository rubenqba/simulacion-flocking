package Escenarios3D;

import implementacion.MovimientoBoid;
import implementacion.MovimientoCuatroEsquinas;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;
import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;

public class Escenario1 {

	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = .3;
		double rangoDeInteraccion =100;
		double radioObstaculos = 20;
		double cantidadAgentes = 100;
		
		/************************/
		
		/**parametros del modelo*/
		int cantidadObstaculos = 1;
		double parametroObstaculos = 1;
		double c1 = 0.001;
		double c2 = 0.2;
		double c3 = 0.2;
		double velMax = .5;
		double zonaVirtual = .1;
		/**************************/
		
		AmbienteMovil ambiente = new AmbienteMovil();
		
		Objetivo objetivo1 = new Objetivo(0,0,0,ambiente);
		
		
		VecindadObjetivos objetivos = new VecindadObjetivos();
		objetivos.add(objetivo1);
		
		MovimientoCuatroEsquinas movObjetivo = new MovimientoCuatroEsquinas();
		movObjetivo.setEsquinaSupIzq(new Vector(0,0,0));
		movObjetivo.setEsquinaSupDer(new Vector(0,0,0));
		movObjetivo.setEsquinaInfDer(new Vector(0,0,0));
		movObjetivo.setEsquinaInfIzq(new Vector(0,0,0));
		
		movObjetivo.setVelMax(velMax+5);
		
		objetivo1.setMov(movObjetivo);
		
		MovimientoBoid mov = new MovimientoBoid();
		mov.setC1(c1);
		mov.setC2(c2);
		mov.setC3(c3);
		mov.setParametroObstaculos(parametroObstaculos);
		mov.setVelMax(velMax);
		mov.setZonaVirtual(zonaVirtual);
		
		Vector infIzq = new Vector(-400,-400,0);
		Vector supDer = new Vector(400,400,0);
		
		for(int i = 0; i< cantidadAgentes; i++)
		{
			Vector v = Util.aleatorioEnArea(infIzq, supDer);
			
			Boid boid = new Boid(v.get(0),v.get(1), v.get(2),ambiente);
			boid.setMov(mov);
			boid.setRangoInteraccion(rangoDeInteraccion);
			boid.setRadio(radioAgente);
			boid.setObjetivos(objetivos);
			ambiente.agregarAgente(boid);
						
		}
infIzq = new Vector(0,0,0);

		
		for( int i = 0; i < cantidadObstaculos; i++)
		{
			Vector v = Util.aleatorioEnVolumen(infIzq);			
			Obstaculo obs = new Obstaculo(v.get(0), v.get(1) , v.get(2), ambiente);			
			obs.setRadio(radioObstaculos);
			ambiente.agregarAgente(obs);
		}
		ambiente.agregarAgente(objetivo1);
			
		Simulacion s = new Simulacion(ambiente);	
		Thread t = new Thread(s);
		t.start();
	}
}
