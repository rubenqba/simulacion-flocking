package Graphics;

public class Vector3D {
	private double datos[];
	private int dimension = 2;
	
	public Vector3D(double ...datos)
	{
		this.datos = new double[datos.length];		
		System.arraycopy(datos,0,this.datos,0,datos.length);
		dimension = datos.length;		
	}
			
	public Vector3D(int dimension)
	{
		this.dimension = dimension;
		datos = new double[dimension];
	}
	
	public int getDimension()
	{
		return dimension;
	}
	
	
	public Vector3D Cross(Vector3D A, Vector3D B)
	{
				
		double x = A.get(1) * B.get(2) - B.get(1) * A.get(2);
		double y = A.get(2) * B.get(0) - B.get(2) * A.get(0);
		double z = A.get(0) * B.get(1) - B.get(0) * A.get(1);
		Vector3D C=new Vector3D(x,y,z);
		
		return C;
	}

	public double Prod(Vector3D A, Vector3D B)
	{
		double ret;
		ret=(A.get(0) * B.get(0)+A.get(1) * B.get(1)+ A.get(2) * B.get(2));
		
		return ret;
	}
	
	public void Normalize()
	{
		double lenght = Math.sqrt(this.get(0)*this.get(0)+this.get(1)*this.get(1)+this.get(2)*this.get(2));
		this.set(this.get(0)/lenght,this.get(1)/lenght,this.get(2)/lenght);
	}
	
	public Vector3D clonar()
	{
		return new Vector3D(datos);
	}
	
	
	
	public double get(int indice)
	{
		return datos[indice];
	}
	
	
	public void set(Vector3D v)
	{
		datos = new double[v.getDimension()];
		dimension = v.getDimension();
		System.arraycopy(v.getDatos(),0,datos,0,dimension);
	}
	
	public void set(double ...datos)
	{
		System.arraycopy(datos,0,this.datos,0,dimension);
	}
	
	public double [] getDatos()
	{
		return datos;
	}
	

}
