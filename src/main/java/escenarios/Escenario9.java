package escenarios;

import implementacion.MovimientoBoid;
import implementacion.MovimientoBoidMejorado;
import implementacion.MovimientoCuatroEsquinas;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import tiposagentes.Obstaculo;
import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;

public class Escenario9 {
	
	public static void main(String ar[])
	{
		
			/**paràmetros de los agentes**/
			double radioAgente = 3;
			double rangoDeInteraccion =100;
			double radioObstaculos = 20;
			double cantidadAgentes = 64;
			int cantObstaculos =10;		
			/************************/
		
			/**parametros del modelo*/
			
			double parametroObstaculos = 1;
			double c1 = 0.05;//inercia
			double c2 = 0.2;//
			double c3 = 0.2;
			double velMax = 5;
			double zonaVirtual = 10;
			/**************************/
		
			AmbienteMovil ambiente = new AmbienteMovil();
		
			Objetivo objetivo1 = new Objetivo(100,100,ambiente);
			Objetivo objetivo2 = new Objetivo(300,300,ambiente);
			//Objetivo objetivo3 = new Objetivo(350,350,ambiente);
			
			objetivo1.setID(1);
			objetivo2.setID(2);
			
			VecindadObjetivos objetivos = new VecindadObjetivos();
			objetivos.add(objetivo1);
			objetivos.add(objetivo2);
			//objetivos.add(objetivo3);
			
			objetivo1.setRadio(radioAgente+1);
			objetivo2.setRadio(radioAgente+1);
		
			MovimientoCuatroEsquinas movObjetivo = new MovimientoCuatroEsquinas();
			movObjetivo.setVelMax(velMax+5);	
			objetivo1.setMov(movObjetivo);
		
			MovimientoCuatroEsquinas movObjetivo2 = new MovimientoCuatroEsquinas();
			movObjetivo2.setEsquinaSupIzq(new Vector(200,200));
			movObjetivo2.setEsquinaSupDer(new Vector(AmbienteMovil.tamx-200,200));
			movObjetivo2.setEsquinaInfIzq(new Vector(300,AmbienteMovil.tamy-200));
			movObjetivo2.setEsquinaInfDer(new Vector(AmbienteMovil.tamx-200, AmbienteMovil.tamy-200));
			movObjetivo2.setVelMax(velMax+5);
			objetivo2.setMov(movObjetivo2);
			
		/*	MovimientoCuatroEsquinas movObjetivo3 = new MovimientoCuatroEsquinas();
			movObjetivo3.setEsquinaSupIzq(new Vector2D(300,300));
			movObjetivo3.setEsquinaSupDer(new Vector2D(AmbienteMovil.tamx-300,300));
			movObjetivo3.setEsquinaInfIzq(new Vector2D(300,AmbienteMovil.tamy-300));
			movObjetivo3.setEsquinaInfDer(new Vector2D(AmbienteMovil.tamx-300, AmbienteMovil.tamy-300));
			movObjetivo3.setVelMax(velMax+5);
			objetivo3.setMov(movObjetivo3);*/
			
			MovimientoBoidMejorado mov = new MovimientoBoidMejorado();
			mov.setC1(c1);
			mov.setC2(c2);
			mov.setC3(c3);
			mov.setParametroObstaculos(parametroObstaculos);
			mov.setVelMax(velMax);
			mov.setZonaVirtual(zonaVirtual);
		
			Vector infIzq = new Vector(0,0);
			Vector supDer = new Vector(700,700);
		
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
		
			infIzq = new Vector(200,200);
			supDer = new Vector(500,500);
			
			for(int j = 0; j<cantObstaculos; j++)
			{
				Vector v = Util.aleatorioEnArea(infIzq, supDer);
				
				Obstaculo obs = new Obstaculo(v.get(0), v.get(1), ambiente);
				obs.setRadio(radioObstaculos);
				ambiente.agregarAgente(obs);
				
			}
			
			ambiente.agregarAgente(objetivo1);
			ambiente.agregarAgente(objetivo2);
			//ambiente.agregarAgente(objetivo3);
			
			Simulacion s = new Simulacion(ambiente);	
			Thread t = new Thread(s);
			t.start();		
	}
}
