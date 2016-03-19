package escenarios;

import implementacion.MovimientoBoid;
import implementacion.MovimientoBoidMejorado;
import implementacion.MovimientoCuatroEsquinas;
import tiposagentes.Boid;
import tiposagentes.Objetivo;
import Vecindades.VecindadObjetivos;
import core.AmbienteMovil;
import core.Simulacion;
import core.Util;
import core.Vector;

public class Escenario7 {
	
	public static void main(String ar[])
	{
		/**paràmetros de los agentes**/
		double radioAgente = 3;
		double rangoDeInteraccion =100;
		double radioObstaculos = 20;
		double cantidadAgentes = 20;
		
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
		
		Objetivo objetivo1 = new Objetivo(100,100,ambiente);
		Objetivo objetivo2 = new Objetivo(700,100,ambiente);
		
		objetivo1.setID(1);
		objetivo2.setID(2);
		
		VecindadObjetivos objetivos = new VecindadObjetivos();
		objetivos.add(objetivo1);
		objetivos.add(objetivo2);
		
		Vector SupIzq = new Vector(100,150);
		Vector SupDer = new Vector(700,150);
		Vector InfIzq = new Vector(100,400);
		Vector InfDer = new Vector(700,400);
		
				
		MovimientoCuatroEsquinas movObjetivo1 = new MovimientoCuatroEsquinas();
		movObjetivo1.setEsquinaSupIzq(SupIzq);
		movObjetivo1.setEsquinaSupDer(SupDer);
		movObjetivo1.setEsquinaInfIzq(InfIzq);
		movObjetivo1.setEsquinaInfDer(InfDer);
		movObjetivo1.setVelMax(velMax);
		
		objetivo1.setMov(movObjetivo1);
		
		MovimientoCuatroEsquinas movObjetivo2 = new MovimientoCuatroEsquinas();
		movObjetivo2.setEsquinaSupIzq(SupDer.clonar());
		movObjetivo2.setEsquinaSupDer(SupIzq.clonar());
		movObjetivo2.setEsquinaInfDer(InfIzq.clonar());
		movObjetivo2.setEsquinaInfIzq(InfDer.clonar());
		movObjetivo2.setVelMax(velMax);
		
		objetivo2.setMov(movObjetivo2);
		
		MovimientoBoidMejorado mov = new MovimientoBoidMejorado();
		mov.setC1(c1);
		mov.setC2(c2);
		mov.setC3(c3);
		mov.setParametroObstaculos(parametroObstaculos);
		mov.setVelMax(velMax);
		mov.setZonaVirtual(zonaVirtual);
		
		Vector infIzq = new Vector(10,10);
		Vector supDer = new Vector(80,80);
		
		for(int i = 0; i< cantidadAgentes/2.0; i++)
		{
			Vector v = Util.aleatorioEnArea(infIzq, supDer);
			
			Boid boid = new Boid(v.get(0),v.get(1),ambiente);
			boid.setMov(mov);
			boid.setRangoInteraccion(rangoDeInteraccion);
			boid.setRadio(radioAgente);
			boid.setObjetivos(objetivos);
			ambiente.agregarAgente(boid);
		}
		
		infIzq.set(650,10);
		
		supDer.set(750,80);
		
		
		for(int i = 0; i< cantidadAgentes/2.0; i++)
		{
			Vector v = Util.aleatorioEnArea(infIzq, supDer);
			
			Boid boid = new Boid(v.get(0),v.get(1),ambiente);
			boid.setMov(mov);
			boid.setRangoInteraccion(rangoDeInteraccion);
			boid.setRadio(radioAgente);
			boid.setObjetivos(objetivos);
			ambiente.agregarAgente(boid);
		}
		
		ambiente.agregarAgente(objetivo1);
		ambiente.agregarAgente(objetivo2);
			
		Simulacion s = new Simulacion(ambiente);	
		Thread t = new Thread(s);
		t.start();
	}
	

}
