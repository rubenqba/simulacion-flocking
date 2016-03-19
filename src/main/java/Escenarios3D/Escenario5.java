package Escenarios3D;

import implementacion.MovimientoBoid;
import implementacion.MovimientoNEsquinas;

import java.util.ArrayList;

import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;
import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;

public class Escenario5 {
	
	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = 1;
		double radioObjetivos = 2;
		double rangoDeInteraccion =10;
		double radioObstaculos =10;
		double cantidadAgentes = 50;
		

		/**parametros del modelo*/
		int volumen=100;
		double parametroObstaculos = 1;
		double c1 = 0.01;
		double c2 = 0.2;
		double c3 = 0.2;
		double velMax = 0.5;
		double zonaVirtual = 5;
		/**************************/
		int cantidadObstaculos = 0;
		/************************/
		
		AmbienteMovil ambiente = new AmbienteMovil();
		
		double extremo = volumen /2.0;
		
		Objetivo objetivo = new Objetivo(-extremo,-extremo,-extremo,ambiente);
		objetivo.setID(1);
		objetivo.setRadio(radioObjetivos);
		objetivo.setColor(0, 1, 1);
		
		
		ArrayList<Vector> puntos = new ArrayList<Vector>();
		puntos.add(new Vector(-extremo,-extremo,-extremo));
		puntos.add(new Vector(extremo,-extremo,-extremo));
		puntos.add(new Vector(extremo,-extremo,extremo));
		puntos.add(new Vector(-extremo,-extremo,extremo));
		puntos.add(new Vector(-extremo,extremo,extremo));
		puntos.add(new Vector(-extremo,extremo,-extremo));
		puntos.add(new Vector(extremo,extremo,-extremo));
		puntos.add(new Vector(extremo,extremo,extremo));
		
		MovimientoNEsquinas m = new MovimientoNEsquinas(puntos);
		m.setVelMax(velMax*3);
		objetivo.setMov(m);
		
		VecindadObjetivos objetivos = new VecindadObjetivos();
		objetivos.add(objetivo);
	
		Vector infIzq = new Vector(volumen,volumen,volumen);
		
		for(int i = 0; i< cantidadAgentes; i++)
		{
			Vector v = Util.aleatorioEnVolumen(infIzq);

			Boid boid = new Boid(v.get(0),v.get(1), v.get(2), ambiente);
			MovimientoBoid mov = new MovimientoBoid();
			mov.setC1(c1);
			mov.setC2(c2);
			mov.setC3(c3);
			mov.setParametroObstaculos(parametroObstaculos);
			mov.setZonaVirtual(zonaVirtual);
			boid.setMov(mov);
			boid.setRangoInteraccion(rangoDeInteraccion);
			boid.setRadio(radioAgente);
			boid.setObjetivos(objetivos);
			boid.setVelMax(velMax);
			ambiente.agregarAgente(boid);						
		}
		
		Obstaculo o1 = new Obstaculo(0, -extremo, -extremo, ambiente);
		Obstaculo o2 = new Obstaculo(0, -extremo, extremo, ambiente);
		Obstaculo o3 = new Obstaculo(extremo, extremo, 0, ambiente);
		Obstaculo o4 = new Obstaculo( -extremo, extremo, 0, ambiente);
		
		o1.setRadio(radioObstaculos);
		o2.setRadio(radioObstaculos);
		o3.setRadio(radioObstaculos);
		o4.setRadio(radioObstaculos);
		
		ambiente.agregarAgente(o1);
		ambiente.agregarAgente(o2);
		ambiente.agregarAgente(o3);
		ambiente.agregarAgente(o4);
		
		
		
		ambiente.agregarAgente(objetivo);
		Simulacion s = new Simulacion(ambiente);	
		s.setDelaySimulacion(50);
		Thread t = new Thread(s);
		t.start();
	}

}
