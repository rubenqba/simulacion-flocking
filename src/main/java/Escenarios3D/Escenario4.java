package Escenarios3D;

import implementacion.MovimientoBoid;
import implementacion.MovimientoNEsquinas;

import java.util.ArrayList;

import Vecindades.VecindadObjetivos;

import tiposagentes.Boid;
import tiposagentes.Objetivo;
import core.*;


public class Escenario4 {
	
	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = 1;
		double radioObjetivos = 2;
		double rangoDeInteraccion =10;
		double radioObstaculos = 1;
		double cantidadAgentes = 50;
		

		/**parametros del modelo*/
		int volumen=100;
		double parametroObstaculos = 1;
		double c1 = 0.001;
		double c2 = 0.2;
		double c3 = 0.2;
		double velMax = 0.5;
		double zonaVirtual = 5;
		/**************************/
		int cantidadObstaculos = 0;
		/************************/
		
		AmbienteMovil ambiente = new AmbienteMovil();
		
		Objetivo objetivo = new Objetivo(-50,-50,-50,ambiente);
		objetivo.setID(1);
		objetivo.setRadio(radioObjetivos);
		objetivo.setColor(0, 1, 1);
		
		double extremo = volumen /2.0;
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
		m.setVelMax(velMax*2);
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
		
		ambiente.agregarAgente(objetivo);
		Simulacion s = new Simulacion(ambiente);	
		s.setDelaySimulacion(50);
		Thread t = new Thread(s);
		t.start();
	}

}
