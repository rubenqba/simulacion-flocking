package escenarios;

import implementacion.MovimientoBoidMejorado;
import tiposagentes.*;

import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;

public class Escenario2_1 {
	
	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = 3;
		double rangoDeInteraccion = 70;
		double radioObstaculos = 50;
		double cantAgentes = 32;
		/************************/
		
		/**parametros del modelo*/
		
		double parametroObstaculos = 1;
		double c1 = 0.001;//inercia
		double c2 = 0.01;//mejor posicion
		double c3 = 0.01;//mejor agente
		double velMax = 3;
		double zonaVirtual = 20;
		/**************************/
		
		AmbienteMovil ambiente = new AmbienteMovil();
		
		Objetivo objetivo1 = new Objetivo(100,600,ambiente);
		Objetivo objetivo2 = new Objetivo(600,600,ambiente);
		Objetivo objetivo3 = new Objetivo(100,100,ambiente);
		Objetivo objetivo4 = new Objetivo(600,100,ambiente);
		
		objetivo1.setID(1);
		objetivo2.setID(2);
		objetivo3.setID(3);
		objetivo4.setID(4);
		
		VecindadObjetivos objetivos = new VecindadObjetivos();
		objetivos.add(objetivo1);
		objetivos.add(objetivo2);
		objetivos.add(objetivo3);
		objetivos.add(objetivo4);
		
		MovimientoBoidMejorado mov = new MovimientoBoidMejorado();
		mov.setC1(c1);
		mov.setC2(c2);
		mov.setC3(c3);
		mov.setParametroObstaculos(parametroObstaculos);
		mov.setVelMax(velMax);
		mov.setZonaVirtual(zonaVirtual);
		
		Vector infIzq = new Vector(300,300);
		Vector supDer = new Vector(400,400);
		
		
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
		
		Obstaculo obstaculo = new Obstaculo(195,205,ambiente);
		Obstaculo obstaculo2 = new Obstaculo(205,195,ambiente);
		obstaculo.setRadio(radioObstaculos);
		obstaculo2.setRadio(radioObstaculos);
		
		ambiente.agregarAgente(objetivo1);
		ambiente.agregarAgente(objetivo2);
		ambiente.agregarAgente(objetivo3);
		ambiente.agregarAgente(objetivo4);
		
		ambiente.agregarAgente(obstaculo);
		ambiente.agregarAgente(obstaculo2);
		
		Simulacion s = new Simulacion(ambiente);		
		Thread t = new Thread(s);
		t.start();
	}	

}
