package escenarios;

import implementacion.MovimientoBoid;
import implementacion.MovimientoBoidMejorado;
import tiposagentes.*;
import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;

public class escenario1_1 {
	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = 3;
		double rangoDeInteraccion = 200;
		double radioObstaculos = 20;
		
		/************************/
		
		/**parametros del modelo*/
		
		double parametroObstaculos = 10;
		double c1 = 1;
		double c2 = 1;
		double c3 = 1;
		double velMax = 5;
		double zonaVirtual = 5;
		/**************************/
		
		AmbienteMovil ambiente = new AmbienteMovil();
		
		Objetivo objetivo1 = new Objetivo(100,650,ambiente);
		Objetivo objetivo2 = new Objetivo(800,650,ambiente);
		Objetivo objetivo3 = new Objetivo(100,50,ambiente);
		Objetivo objetivo4 = new Objetivo(800,50,ambiente);
		
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
		
		Boid boid = new Boid(300,350,ambiente);
		boid.setMov(mov);
		boid.setRangoInteraccion(rangoDeInteraccion);
		boid.setRadio(radioAgente);
		boid.setObjetivos(objetivos);
		
		Obstaculo obstaculo1 = new Obstaculo(200,500,ambiente);
		Obstaculo obstaculo2 = new Obstaculo(250,550,ambiente);
		obstaculo1.setRadio(radioObstaculos);
		obstaculo2.setRadio(radioObstaculos);
		
		ambiente.agregarAgente(objetivo1);
		ambiente.agregarAgente(objetivo2);
		ambiente.agregarAgente(objetivo3);
		ambiente.agregarAgente(objetivo4);
		
		ambiente.agregarAgente(boid);
		
		ambiente.agregarAgente(obstaculo1);
		ambiente.agregarAgente(obstaculo2);
	
		
		Simulacion s = new Simulacion(ambiente);	
		Thread t = new Thread(s);
		t.start();
	}

}
