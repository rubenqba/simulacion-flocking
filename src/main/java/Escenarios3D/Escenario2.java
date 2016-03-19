package Escenarios3D;

import implementacion.MovimientoBoid;
import implementacion.MovimientoCuatroEsquinas;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;

public class Escenario2 {

	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = .3;
		double rangoDeInteraccion =1;
		double radioObstaculos = 20;
		double cantidadAgentes = 128;
		int cantidadObstaculos = 10;
	
		/************************/
	
		/**parametros del modelo*/
		
		double parametroObstaculos = 1;
		double c1 = 0.05;//inercia
		double c2 = 0.2;//
		double c3 = 0.2;
		double velMax = 5;
		double zonaVirtual = 5;
		/**************************/
	
		AmbienteMovil ambiente = new AmbienteMovil();
	
		Objetivo objetivo1 = new Objetivo(400,0,0,ambiente);
		Objetivo objetivo2 = new Objetivo(400,100,100,ambiente);
		//Objetivo objetivo3 = new Objetivo(350,350,ambiente);
		
		objetivo1.setID(1);
		objetivo2.setID(2);	
		
		VecindadObjetivos objetivos = new VecindadObjetivos();
		objetivos.add(objetivo1);
		objetivos.add(objetivo2);
		//objetivos.add(objetivo3);
	
		MovimientoCuatroEsquinas movObjetivo = new MovimientoCuatroEsquinas();
		movObjetivo.setEsquinaSupIzq(new Vector(0,400,400));
		movObjetivo.setEsquinaSupDer(new Vector(400,600,700));
		movObjetivo.setEsquinaInfDer(new Vector(-700,-200,-400));
		movObjetivo.setEsquinaInfIzq(new Vector(-400,0,0));
		
		movObjetivo.setVelMax(velMax+5);	
		objetivo1.setMov(movObjetivo);
	
		MovimientoCuatroEsquinas movObjetivo2 = new MovimientoCuatroEsquinas();
		movObjetivo2.setEsquinaSupIzq(new Vector(400,100,100));
		movObjetivo2.setEsquinaSupDer(new Vector(100, 300, 400));
		movObjetivo2.setEsquinaInfIzq(new Vector(-300,-500,-600));
		movObjetivo2.setEsquinaInfDer(new Vector(-600, -300, -400));
		
		movObjetivo2.setVelMax(velMax+5);
		objetivo2.setMov(movObjetivo2);
		
		
		MovimientoBoid mov = new MovimientoBoid();
		mov.setC1(c1);
		mov.setC2(c2);
		mov.setC3(c3);
		mov.setParametroObstaculos(parametroObstaculos);
		mov.setVelMax(velMax);
		mov.setZonaVirtual(zonaVirtual);
	
		Vector infIzq = new Vector(-400,-400, -400);
		Vector supDer = new Vector(400, 400, 400);
	
		for(int i = 0; i< cantidadAgentes; i++)
		{
			Vector v = Util.aleatorioEnArea(infIzq, supDer);
		
			Boid boid = new Boid(v.get(0),v.get(1), v.get(2), ambiente);
			boid.setMov(mov);
			boid.setRangoInteraccion(rangoDeInteraccion);
			boid.setRadio(radioAgente);
			boid.setObjetivos(objetivos);
			ambiente.agregarAgente(boid);						
		}
	
		ambiente.agregarAgente(objetivo1);
		ambiente.agregarAgente(objetivo2);
		//ambiente.agregarAgente(objetivo3);
		
		Simulacion s = new Simulacion(ambiente);	
		Thread t = new Thread(s);
		t.start();
	}
}
