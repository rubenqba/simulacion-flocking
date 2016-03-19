package implementacion;

import java.util.ArrayList;

import tiposagentes.Obstaculo;
import tiposagentes.Boid;
import core.AgenteMovil;
import core.Percepcion;
import core.Util;
import core.Vector;

public class MovimientoBoidMejorado extends MovimientoBoid{
	
	public void acotarVelocidad(Boid boid, Vector vel )
	{
		double velMax = this.getVelMax();
		
		AgenteMovil agenteMasProbableColision = agenteMasProbableColision(boid);
		
		if(agenteMasProbableColision != null)
		{
			double distancia = boid.getPosicion().distancia(agenteMasProbableColision.getPosicion());		
			double z = (distancia - this.getZonaVirtual())/this.getZonaVirtual();		
		
			
			velMax = velMax*f(z);
		
		}
				
		vel.acotarMagnitud(velMax);
		
	}

	private AgenteMovil agenteMasProbableColision(Boid boid)
	{
		Percepcion p = boid.getPercepcion();
		
		double menorDistancia = Double.POSITIVE_INFINITY;
		
		double dist = 0.0;
		
		AgenteMovil agenteMasProbable = null;
		
		Vector pos = boid.getPosicion();
		Vector vel = boid.getVelocidad();
		double rad = boid.getRadio();
		
		
		
		for(AgenteMovil a: p.getVecindadBoid())
		{						
			Vector posA = a.getPosicion();
			if(habraColision(pos, posA, vel, rad, a.getRadio()))
			{
				dist = pos.distancia(posA);
				
				if(dist < menorDistancia)
				{
					menorDistancia = dist;
					agenteMasProbable = a;
				}
			}
		}
		
		for(AgenteMovil a: p.getVecindadObstaculo())
		{
			Vector posA = a.getPosicion();
			if(habraColision(pos, posA, vel, rad, a.getRadio()))
			{
				dist = pos.distancia(posA);
				
				if(dist < menorDistancia)
				{
					menorDistancia = dist;
					agenteMasProbable = a;
				}
			}
		}
		
		for(AgenteMovil a: boid.getObjetivos())
		{
			Vector posA = a.getPosicion();
			if(habraColision(pos, posA, vel, rad, a.getRadio()))
			{
				dist = pos.distancia(posA);
				
				if(dist < menorDistancia)
				{
					menorDistancia = dist;
					agenteMasProbable = a;
				}
			}
		}
		
	
		return agenteMasProbable;
	}		
	
	/**
	 * Retorna verdadero si al desplazar una esfera (o circunferencia en 2D), con centro en centrA y radio radioA, 
	 * en la direccion direccionA, podría colisionar con otra esfera con centroB y radioB
	 * @param centroA
	 * @param centroB
	 * @param direccionA
	 * @param radioA
	 * @param radioB
	 * @return
	 */
	private boolean habraColision(Vector centrA, Vector centrB, Vector direccionA, double radioA, double radioB)
	{
		double anguloMinimo = 0.0;
		double distancia = centrA.distancia(centrB);
		double separacion = radioA + radioB;
		
		double temp = 0;
		double var = separacion / distancia;
		
		
		temp = var;
		temp = temp + var;
		var = temp - var;//Eliminando errores de redondeo
		
		anguloMinimo = Math.atan(var);
		anguloMinimo = Math.toDegrees(anguloMinimo);
		
		
		Vector BmenosA = centrB.clonar();
		BmenosA.restar(centrA);
		double anguloReal = Util.anguloEntreVectores(direccionA, BmenosA);
				
		
		if(anguloReal < anguloMinimo)
			return true;
		
		return false;
	}
	
	/**
	 * Está función permite obtener la cota adecuada para la velocidad
	 * @param z
	 * @return
	 */
	private double f(double z)
	{
		if(z < 0)
			return 0.0001;
		if(z > 1)
			return 1;
		
		double temp = 0;
		double a = 0.4;
		double f = 1 + Math.exp(-10*(z-a));
		f = 1 / f;
		
		temp = f;
		temp = temp + f;
		f = temp - f;
		
		return f;
	}
	
	public void evasionColisiones(Boid boid)
	{
		//super.evasionColisiones(boid);
		calcularNuevaPosicion2(boid);
	}
	
	private void calcularNuevaPosicion2(Boid boid)
	{
		Vector posicion = boid.getPosicion().clonar();
		Percepcion p = boid.getPercepcion();
		
		ArrayList<AgenteMovil> agentes = new ArrayList<AgenteMovil>();		
		agentes.addAll(p.getVecindadBoid());
		agentes.addAll(p.getVecindadObstaculo());
		agentes.addAll(boid.getObjetivos());
		
		for(AgenteMovil agente: agentes)
		{
			if(agente instanceof Obstaculo)
			{
				Vector puntoMasCercanoObstaculo = Util.calcularPuntoMasCercanoAlObstaculo(boid.getPosicion(), agente.getPosicion(), agente.getRadio());			
				
				Vector separacion= null;
				double distanciaDeseada = 0;
				double distAgentPunto = posicion.distancia(puntoMasCercanoObstaculo);
				double distAgentObs = boid.getPosicion().distancia(agente.getPosicion());
				
				
				if(distAgentObs < agente.getRadio())//el agente está dentro del obstáculo
				{//se aplica igual que en kim y shin 2006
					distanciaDeseada = agente.getRadio() + boid.getRadio()+ this.getZonaVirtual();
					separacion = this.separarPuntos(boid.getPosicion(), agente.getPosicion(), distanciaDeseada, distAgentObs);					
					boid.getPosicion().sumar(separacion);
				}
				else
				{//el agente está fuera del obstáculo
					distanciaDeseada =this.getZonaVirtual() + boid.getRadio();
					if(distAgentPunto < distanciaDeseada)//el agente está fuera del obstáculo, pero demasiado cercan a él
					{			
						//se aplica igual que en kim y shin, pero la distancia deseada no considera la zona virtual
						separacion = this.separarPuntos(boid.getPosicion(), puntoMasCercanoObstaculo, distanciaDeseada, distAgentPunto );						
						boid.getPosicion().sumar(separacion);
					}
				}
				
			}
			else
			{
				double distancia = posicion.distancia(agente.getPosicion());//
				double distanciaDeseada = this.getZonaVirtual() + agente.getRadio() + boid.getRadio();
				
				if(distancia < distanciaDeseada)
				{
					Vector separacion = separarPuntos(boid.getPosicion(), agente.getPosicion(), distanciaDeseada, distancia);
					boid.getPosicion().sumar(separacion);
				}
			}
			
		}
	}
	
	private void calcularNuevaPosicion(Boid boid)
	{
		Vector posicion = boid.getPosicion();
		Percepcion p = boid.getPercepcion();
		
		ArrayList<AgenteMovil> agentes = new ArrayList<AgenteMovil>();		
		agentes.addAll(p.getVecindadBoid());
		agentes.addAll(p.getVecindadObstaculo());
		agentes.addAll(boid.getObjetivos());

		Vector sumatoria = new Vector(boid.getPosicion().getDimension());
		for(AgenteMovil agente: agentes)
		{
			
			double distancia = posicion.distancia(agente.getPosicion());
			double distanciaDeseada = this.getZonaVirtual() + agente.getRadio() + boid.getRadio();
			
			if(distancia < distanciaDeseada)
			{
				Vector s = separarPuntosParaObstaculos(posicion, agente.getPosicion(), distanciaDeseada);
				sumatoria.sumar(s);
			}
		}
		
		if(agentes.size() > 0)
		{
			sumatoria.multiplicarEscalar(1/agentes.size());
			posicion.sumar(sumatoria);
		}
				
	}
	
	/**
	 * Retorna R
	 * R = r(U) - (X-Y)
	 * donde U es el vector unitario de X-Y
	 * y r es la separación deseada entre el vector X y Y
	 * 
	 * Al sumar X + R el punto X tendrá distancia r al punto Y
	 * @param x
	 * @param y
	 * @param separacion
	 * @return
	 */

	
	private Vector separarPuntos(Vector x, Vector y, double separacion, double distanciaXY)
	{
		Vector resultado = new Vector(x.getDimension());
		double distReal = x.distancia(y);
		
		double umbral = separacion*distanciaXY/(separacion-distanciaXY);
		
		if(distReal > umbral)
		{
			return resultado;
		}
		
		Vector XmenosY = x.clonar();
		XmenosY.restar(y);
				
		Vector rXmenosY = XmenosY.clonar();
				
		rXmenosY.multiplicarEscalar(separacion/distanciaXY);
		
		resultado.sumar(rXmenosY);
		resultado.restar(XmenosY);
		
		
		return resultado;
	}
	
	private Vector separarPuntosParaObstaculos(Vector x, Vector y, double separacion)
	{
		Vector diferencia = x.clonar();
		x.restar(y);
		
		Vector uniDiferencia = diferencia.unitario();
		uniDiferencia.multiplicarEscalar(separacion);
		
		uniDiferencia.restar(diferencia);
		
		return uniDiferencia;
	}
	public static void main(String ar[])
	{
		/*double ra = 2;
		double ro = 2;
		
		Vector posAgente = new Vector(0,0);
		Vector posObs = new Vector(7,0);
		Vector vel = new Vector(0,1);
		Vector vel2 = new Vector(2,1);
		Vector vel3 = new Vector(2,2);
		
		MovimientoBoidMejorado mov = new MovimientoBoidMejorado();
		
		System.out.println(mov.habraColision(posAgente, posObs, vel, ra, ro));
		System.out.println(mov.habraColision(posAgente, posObs, vel2, ra, ro));
		System.out.println(mov.habraColision(posAgente, posObs, vel3, ra, ro));
		
		
		System.out.println(mov.f(0.4));*/
		/*
		Vector x = new Vector(2,1);
		Vector y = new Vector(3,2);
		
		double r = 2;
		double distancia = x.distancia(y);
		Vector s = MovimientoBoidMejorado.separarPuntos(x, y, r, distancia);
		
		System.out.println(s);*/
	}
}
