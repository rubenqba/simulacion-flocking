package Reporte;

import java.awt.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class Grafica extends ApplicationFrame {

    private XYSeriesCollection series;
    private String nameX = "x";
    private String nameY = "y";

    public Grafica(String nombre, String nameX, String nameY) {
        super(nombre);
        this.nameX = nameX;
        this.nameY = nameY;
        series = new XYSeriesCollection();
    }

    public void agregarSerie(XYSeries serie) {
        series.addSeries(serie);
    }

    public void crearReporte() {
        JFreeChart chart = ChartFactory.createXYLineChart(this.getTitle(), nameX, nameY, series,
                PlotOrientation.VERTICAL, true, true, true);

        XYPlot plot = chart.getXYPlot();
        for (int i = 0; i < plot.getSeriesCount(); i++) {
            plot.getRenderer().setSeriesStroke(i, new BasicStroke(2));
        }

        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setPreferredSize(new Dimension(600, 800));

        setContentPane(chartpanel);
        setSize(800, 600);
        setVisible(true);
    }

}
