package implementacion;

import Vecindades.*;
import tiposagentes.*;
import core.*;

public class Main {
	
	public static void main(String arg[])
	{
		
		AmbienteMovil ambiente = new AmbienteMovil();
		
		Boid boid1 = new Boid(100,100,ambiente);
		Boid boid2 = new Boid(105,100,ambiente);
		Boid boid3 = new Boid(600,100,ambiente);
		Boid boid4 = new Boid(700,100,ambiente);
		Boid boid5 = new Boid(700,105,ambiente);
		
		MovimientoBoid mov = new MovimientoBoid();
		
		boid1.setMov(mov);
		boid2.setMov(mov);
		boid3.setMov(mov);
		boid4.setMov(mov);
		boid5.setMov(mov);
		
		Objetivo obj1 = new Objetivo(100,500,ambiente);
		Objetivo obj2 = new Objetivo(700,500,ambiente);
		
		VecindadObjetivos objetivos = new VecindadObjetivos();
		objetivos.add(obj1);
		objetivos.add(obj2);
		
		boid1.setObjetivos(objetivos);
		boid2.setObjetivos(objetivos);
		boid3.setObjetivos(objetivos);
		boid4.setObjetivos(objetivos);
		boid5.setObjetivos(objetivos);
		
		ambiente.agregarAgente(boid1);
		ambiente.agregarAgente(boid2);
		ambiente.agregarAgente(boid3);
		ambiente.agregarAgente(boid4);
		ambiente.agregarAgente(boid5);

		ambiente.agregarAgente(obj1);
		ambiente.agregarAgente(obj2);
		
		Simulacion simulacion = new Simulacion(ambiente);
		simulacion.setDelaySimulacion(200);
		Thread hilo = new Thread(simulacion);
		
		hilo.start();
	}

}
