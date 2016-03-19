package Escenarios3D;

import implementacion.MovimientoBoid;
import implementacion.MovimientoCuatroEsquinas;
import tiposagentes.*;
import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;

public class Escenario3 {
	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = .5;
		double radioObjetivos = 1;
		double rangoDeInteraccion =10;
		double radioObstaculos = 1;
		double cantidadAgentes = 100;
		

		/**parametros del modelo*/
		int volumen=100;
		double parametroObstaculos = 1;
		double c1 = 0.001;
		double c2 = 0.2;
		double c3 = 0.2;
		double velMax = 0.8;
		double zonaVirtual = 5;
		/**************************/
		int cantidadObstaculos = 0;
		/************************/

		AmbienteMovil ambiente = new AmbienteMovil();

		Objetivo objetivo1 = new Objetivo(0,0,0,ambiente);
	/*	Objetivo objetivo2 = new Objetivo(0,0,0,ambiente);
		Objetivo objetivo3 = new Objetivo(0,0,0,ambiente);
		Objetivo objetivo4 = new Objetivo(0,0,0,ambiente);*/

		objetivo1.setID(1);
		objetivo1.setColor(0, 0, 1);
		objetivo1.setRadio(radioObjetivos);
		/*objetivo2.setID(2);	
		objetivo2.setColor(1,0,0);
		objetivo2.setRadio(radioObjetivos);*/

		/*objetivo3.setID(1);
		objetivo3.setColor(1, 1, 1);
		objetivo3.setRadio(radioObjetivos);
		objetivo4.setID(1);
		objetivo4.setColor(1, 0, 1);
		objetivo4.setRadio(radioObjetivos);*/

		VecindadObjetivos objetivos = new VecindadObjetivos();
		objetivos.add(objetivo1);
	/*	objetivos.add(objetivo2);
		objetivos.add(objetivo3);
		objetivos.add(objetivo4);*/

		MovimientoCuatroEsquinas movObjetivo = new MovimientoCuatroEsquinas();
		movObjetivo.setEsquinaSupIzq(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo.setEsquinaSupDer(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo.setEsquinaInfDer(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo.setEsquinaInfIzq(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));

		movObjetivo.setVelMax(velMax+(velMax*.1));	
		objetivo1.setMov(movObjetivo);

		MovimientoCuatroEsquinas movObjetivo2 = new MovimientoCuatroEsquinas();
		movObjetivo2.setEsquinaSupIzq(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo2.setEsquinaSupDer(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo2.setEsquinaInfDer(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo2.setEsquinaInfIzq(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));

		movObjetivo2.setVelMax(velMax+(velMax*.1));
		//objetivo2.setMov(movObjetivo2);

		MovimientoCuatroEsquinas movObjetivo3 = new MovimientoCuatroEsquinas();
		movObjetivo3.setEsquinaSupIzq(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo3.setEsquinaSupDer(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo3.setEsquinaInfDer(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo3.setEsquinaInfIzq(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));

		movObjetivo3.setVelMax(velMax+(velMax*.1));
		//objetivo3.setMov(movObjetivo3);

		MovimientoCuatroEsquinas movObjetivo4 = new MovimientoCuatroEsquinas();
		movObjetivo4.setEsquinaSupIzq(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo4.setEsquinaSupDer(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo4.setEsquinaInfDer(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));
		movObjetivo4.setEsquinaInfIzq(new Vector((Math.random()*volumen)-50,(Math.random()*volumen)-50,(Math.random()*volumen)-50));

		movObjetivo4.setVelMax(velMax+(velMax*.1));
		//objetivo4.setMov(movObjetivo4);

		MovimientoBoid mov = new MovimientoBoid();
		mov.setC1(c1);
		mov.setC2(c2);
		mov.setC3(c3);
		mov.setParametroObstaculos(parametroObstaculos);
		mov.setVelMax(velMax);
		mov.setZonaVirtual(zonaVirtual);

		Vector infIzq = new Vector(volumen,volumen,volumen);

		for(int i = 0; i< cantidadAgentes; i++)
		{
			Vector v = Util.aleatorioEnVolumen(infIzq);

			Boid boid = new Boid(v.get(0),v.get(1), v.get(2), ambiente);
			boid.setMov(mov);
			boid.setRangoInteraccion(rangoDeInteraccion);
			boid.setRadio(radioAgente);
			boid.setObjetivos(objetivos);
			ambiente.agregarAgente(boid);						
		}

		infIzq = new Vector(volumen,volumen,volumen);

		for( int i = 0; i < cantidadObstaculos; i++)
		{
			Vector v = Util.aleatorioEnVolumen(infIzq);			
			Obstaculo obs = new Obstaculo(v.get(0),v.get(1), v.get(2), ambiente);			
			obs.setRadio(radioObstaculos);
			ambiente.agregarAgente(obs);
		}

		ambiente.agregarAgente(objetivo1);
		//ambiente.agregarAgente(objetivo2);
		//ambiente.agregarAgente(objetivo3);
		//ambiente.agregarAgente(objetivo4);

		Simulacion s = new Simulacion(ambiente);	
		Thread t = new Thread(s);
		t.start();
	}

}
