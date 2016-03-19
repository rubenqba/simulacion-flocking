package escenarios;

import implementacion.MovimientoBoid;
import implementacion.MovimientoBoidMejorado;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;
import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;

public class Escenario8 {
	
	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = 3;
		double rangoDeInteraccion = 100;
		double radioObstaculos = 30;
		double cantAgentes = 60;
		/************************/
		
		/**parametros del modelo*/
		
		double parametroObstaculos = 1;
		double c1 = 0.01;//inercia
		double c2 = 0.02;//mejor posicion
		double c3 = 0.02;//mejor agente
		double velMax = 4;
		double zonaVirtual = 15;
		/**************************/
		
		AmbienteMovil ambiente = new AmbienteMovil();				
		Objetivo objetivo1 = new Objetivo(100,100,ambiente);				
		VecindadObjetivos objetivos = new VecindadObjetivos();
		objetivo1.setRadio(radioAgente);
	
		objetivos.add(objetivo1);
	
		
		MovimientoBoidMejorado mov = new MovimientoBoidMejorado();
		mov.setC1(c1);
		mov.setC2(c2);
		mov.setC3(c3);
		mov.setParametroObstaculos(parametroObstaculos);
		mov.setVelMax(velMax);
		mov.setZonaVirtual(zonaVirtual);
		
		Vector infIzq = new Vector(400,400);
		Vector supDer = new Vector(500,500);
		
		
		for(int i = 0; i< cantAgentes; i++)
		{
			Vector v = Util.aleatorioEnArea(infIzq, supDer);
			
			Boid boid = new Boid(v.get(0),v.get(1),ambiente);
			boid.setMov(mov);
			boid.setRangoInteraccion(rangoDeInteraccion);
			boid.setRadio(radioAgente);
			boid.setObjetivos(objetivos);
			ambiente.agregarAgente(boid);
		}
		
		Obstaculo obstaculo = new Obstaculo(300,300,ambiente);	
		obstaculo.setRadio(radioObstaculos);
					
		ambiente.agregarAgente(objetivo1);				
		ambiente.agregarAgente(obstaculo);	
		
		Simulacion s = new Simulacion(ambiente);	
		Thread t = new Thread(s);
		t.start();
	}	


}
