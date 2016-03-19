package escenarios;

import implementacion.MovimientoBoid;
import implementacion.MovimientoBoidMejorado;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;


public class Escenario2_2 {

	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = 3;
		double rangoDeInteraccion = 100;
		double radioObstaculos = 10;
		double cantAgentes = 16;
		/************************/
		
		/**parametros del modelo*/		
		double parametroObstaculos = 1;
		double c1 = 1;
		double c2 = 1;
		double c3 = 1;
		double velMax = 5;
		double zonaVirtual = 5;
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
		
		Boid boid = new Boid(300,300,ambiente);
		Boid boid2 = new Boid(400,300,ambiente);
		Boid boid3 = new Boid(300,400,ambiente);
		Boid boid4 = new Boid(400,400,ambiente);
		
		boid.setMov(mov);
		boid2.setMov(mov);
		boid3.setMov(mov);
		boid4.setMov(mov);
		
		boid.setRadio(radioAgente);
		boid2.setRadio(radioAgente);
		boid3.setRadio(radioAgente);
		boid4.setRadio(radioAgente);
		
		boid.setObjetivos(objetivos);
		boid2.setObjetivos(objetivos);
		boid3.setObjetivos(objetivos);
		boid4.setObjetivos(objetivos);
		
		ambiente.agregarAgente(boid);
		ambiente.agregarAgente(boid2);
		ambiente.agregarAgente(boid3);
		ambiente.agregarAgente(boid4);
		
		ambiente.agregarAgente(objetivo1);
		ambiente.agregarAgente(objetivo2);
		ambiente.agregarAgente(objetivo3);
		ambiente.agregarAgente(objetivo4);
		
		Simulacion s = new Simulacion(ambiente);	
		Thread t = new Thread(s);
		t.start();
	}
	
}
