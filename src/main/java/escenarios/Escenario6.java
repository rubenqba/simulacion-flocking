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

public class Escenario6 {
	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = 3;
		double rangoDeInteraccion =100;
		double radioObstaculos = 10;
		double cantidadAgentes = 8;
		
		/************************/
		
		/**parametros del modelo*/
		
		double parametroObstaculos = 1;
		double c1 = 0.1;
		double c2 = 0.01;
		double c3 = 0.1;
		double velMax = 5;
		double zonaVirtual = 10;
		/**************************/
		
		AmbienteMovil ambiente = new AmbienteMovil();
		
		Objetivo objetivo1 = new Objetivo(500,500,ambiente);
		
		VecindadObjetivos objetivos = new VecindadObjetivos();
		objetivos.add(objetivo1);
			
		MovimientoBoidMejorado mov = new MovimientoBoidMejorado();
		mov.setC1(c1);
		mov.setC2(c2);
		mov.setC3(c3);
		mov.setParametroObstaculos(parametroObstaculos);
		mov.setVelMax(velMax);
		mov.setZonaVirtual(zonaVirtual);
		
		int x_ = 250;
		Vector infIzq = new Vector(x_,485);
		Vector supDer = new Vector(x_+30,515);
		
		for(int i = 0; i< cantidadAgentes; i++)
		{
			Vector v = Util.aleatorioEnArea(infIzq, supDer);
			
			Boid boid = new Boid(v.get(0),v.get(1),ambiente);
			boid.setMov(mov);
			boid.setRangoInteraccion(rangoDeInteraccion);
			boid.setRadio(radioAgente);
			boid.setObjetivos(objetivos);
			ambiente.agregarAgente(boid);
		}
		
	
		
		
		Obstaculo obstaculo1 = new Obstaculo(x_,450,ambiente);
		Obstaculo obstaculo2 = new Obstaculo(x_+20,450,ambiente);
		Obstaculo obstaculo3 = new Obstaculo(x_+45,450,ambiente);
		Obstaculo obstaculo4 = new Obstaculo(x_+45,470,ambiente);
		Obstaculo obstaculo5 = new Obstaculo(x_+50,490,ambiente);
		Obstaculo obstaculo6 = new Obstaculo(x_+45,510,ambiente);
		Obstaculo obstaculo7 = new Obstaculo(x_+40,525,ambiente);
		Obstaculo obstaculo8 = new Obstaculo(x_+20,525,ambiente);
		Obstaculo obstaculo9 = new Obstaculo(x_,525,ambiente);
		
		obstaculo1.setRadio(radioObstaculos);
		obstaculo2.setRadio(radioObstaculos);
		obstaculo3.setRadio(radioObstaculos);
		obstaculo4.setRadio(radioObstaculos);
		obstaculo5.setRadio(radioObstaculos);
		obstaculo6.setRadio(radioObstaculos);
		obstaculo7.setRadio(radioObstaculos);
		obstaculo8.setRadio(radioObstaculos);
		obstaculo9.setRadio(radioObstaculos);
		
		
		
		ambiente.agregarAgente(objetivo1);
		
		ambiente.agregarAgente(obstaculo1);
		ambiente.agregarAgente(obstaculo2);
		ambiente.agregarAgente(obstaculo3);
		ambiente.agregarAgente(obstaculo4);
		ambiente.agregarAgente(obstaculo5);
		ambiente.agregarAgente(obstaculo6);
		ambiente.agregarAgente(obstaculo7);
		ambiente.agregarAgente(obstaculo8);
		ambiente.agregarAgente(obstaculo9);
		
			
		Simulacion s = new Simulacion(ambiente);	
		Thread t = new Thread(s);
		t.start();
	}

}
