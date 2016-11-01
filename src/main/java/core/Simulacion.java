package core;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Graphics.Grafico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import metricas.ObservadorAmbiente;
import org.apache.commons.lang3.time.StopWatch;

@Builder
@AllArgsConstructor
public class Simulacion extends JFrame implements Runnable {

    private String name;
    private AmbienteMovil ambiente;
    private int iteraciones = 10000;
    private int delaySimulacion = 100;
    private boolean simulacionActiva = true;
    private boolean habilitarPintado = true;
    private boolean simulacion3D = false;
    private List<ObservadorAmbiente> observadores;
    private Grafico graph;

    public Simulacion(AmbienteMovil ambiente) {
        this.ambiente = ambiente;
        observadores = new ArrayList<>();

        habilitarGraficos3D(ambiente);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(AmbienteMovil.tamx, AmbienteMovil.tamy);
        setVisible(true);
    }

    public AmbienteMovil getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(AmbienteMovil ambiente) {
        this.ambiente = ambiente;
    }

    protected void initGraphics() {
        habilitarGraficos3D(ambiente);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(AmbienteMovil.tamx, AmbienteMovil.tamy);
        setVisible(true);
    }

    private void habilitarGraficos3D(AmbienteMovil ambiente2) {
        List<AgenteMovil> agentes = ambiente2.getAgentes();

        if (agentes.size() > 0) {
            Vector posicion = agentes.get(0).getPosicion();

            if (posicion.getDimension() == 3) {
                graph = new Grafico(ambiente2);
                simulacion3D = true;
            }
        }
    }

    @Override
    public void run() {
        int tiempo = 0;
        simulacionActiva = true;
        if (habilitarPintado)
            initGraphics();
        try {
            Thread.sleep(5000);
            while (tiempo <= iteraciones && simulacionActiva) {

                if (delaySimulacion > 0)
                    Thread.sleep(delaySimulacion);
                actuar();
                notificarObservadores();
                tiempo++;
                if (habilitarPintado) {
                    repaint();
                    setTitle(String.format("%s: iteración %d de %d", name, tiempo, iteraciones));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    protected void actuar() {
        for (AgenteMovil agente : ambiente.getAgentes()) {
            agente.actuar();
        }
    }

    @Override
    public void paint(Graphics g) {
        if (simulacion3D)// Este metodo solo es para 2D
            return;

        if (habilitarPintado) {
            super.paint(g);

            for (AgenteMovil agente : ambiente.getAgentes()) {
                agente.pintar(g);
            }
        }
    }

    private void notificarObservadores() {
        for (ObservadorAmbiente o : observadores) {
            o.observarAmbiente(ambiente);
        }
    }

    public void addObservador(ObservadorAmbiente obs) {
        observadores.add(obs);
    }

    public void setDelaySimulacion(int delay) {
        this.delaySimulacion = delay;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

    public void setHabilitarPintado(boolean habilitarPintado) {
        this.habilitarPintado = habilitarPintado;
    }

    public List<ObservadorAmbiente> getObservadores() {
        return observadores;
    }
}
