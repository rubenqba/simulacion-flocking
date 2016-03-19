package Reporte;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;


public class Grafica extends ApplicationFrame{
	

	private XYSeriesCollection coleccion;
	private String ejeX = "Eje x";
	private String ejeY = "Eje y";
	
	public Grafica()
	{
		super("Gráfica");
		iniciar();
	}
	
	public Grafica(String nombre)
	{
		super(nombre);
		iniciar();
	}
	
	public void agregarPuntos(XYSeries puntos)
	{
		coleccion.addSeries(puntos);
	}
	
	private void iniciar()
	{		
			coleccion = new XYSeriesCollection();			
	}
	
	public void crearReporte()
	{
		JFreeChart chart = ChartFactory.createXYLineChart(this.getName(), getEjeX(), getEjeY(), coleccion, PlotOrientation.VERTICAL, true, true, true);					
		ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setPreferredSize(new Dimension(360, 500));
        setContentPane(chartpanel);
        setSize(500,300);
        setVisible(true);
	}
	
	public void actualizar()
	{
		this.repaint();
	}
	public String getEjeX() {
		return ejeX;
	}

	public void setEjeX(String ejeX) {
		this.ejeX = ejeX;
	}

	public String getEjeY() {
		return ejeY;
	}

	public void setEjeY(String ejeY) {
		this.ejeY = ejeY;
	}
}
