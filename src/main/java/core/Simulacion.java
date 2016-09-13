package core;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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

    private static final long serialVersionUID = 1L;
    private AmbienteMovil ambiente;
    private int iteraciones = 10000;
    private int delaySimulacion = 100;
    private boolean simulacionActiva = true;
    private boolean habilitarPintado = true;
    private boolean simulacion3D = false;
    private List<ObservadorAmbiente> observadores;
    Grafico graph;

    public AmbienteMovil getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(AmbienteMovil ambiente) {
        this.ambiente = ambiente;
    }

    public Simulacion(AmbienteMovil ambiente) {
        this.ambiente = ambiente;
        observadores = new ArrayList<ObservadorAmbiente>();

        habilitarGraficos3D(ambiente);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(AmbienteMovil.tamx, AmbienteMovil.tamy);
        setVisible(true);
    }

    private void habilitarGraficos3D(AmbienteMovil ambiente2) {
        ArrayList<AgenteMovil> agentes = ambiente2.getAgentes();

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
//        StopWatch w = new StopWatch();
        try {
//            w.start();
            while (tiempo <= iteraciones && simulacionActiva) {

                if (delaySimulacion > 0)
                    Thread.sleep(delaySimulacion);
//                if(w.isSuspended())
//                    w.resume();
                actuar();
                notificarObservadores();
//                w.split();
//                System.out.println(String.format("it(%d): %s", tiempo, w.toSplitString()));
//                w.suspend();
                if (habilitarPintado)
                    repaint();
                tiempo++;
            }
//            w.stop();
//            System.out.println(String.format("Time: %dms", w.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    protected void actuar() {
//        StopWatch w = new StopWatch();
//        w.start();
        for (AgenteMovil agente : ambiente.getAgentes()) {
            agente.actuar();
        }
//        w.stop();
//        System.out.println(String.format("actuar: %dms", w.getTime()));
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

    public void removeObservador(ObservadorAmbiente obs) {
        observadores.remove(obs);
    }

    public void setSimulacionActiva(boolean simulacionActiva) {
        this.simulacionActiva = simulacionActiva;
    }

    public boolean isSimulacionActiva() {
        return simulacionActiva;
    }

    public void setDelaySimulacion(int delay) {
        this.delaySimulacion = delay;
    }

    public int getDelaySimulacion() {
        return this.delaySimulacion;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public void setHabilitarPintado(boolean habilitarPintado) {
        this.habilitarPintado = habilitarPintado;
    }

    public boolean isHabilitarPintado() {
        return habilitarPintado;
    }

    public List<ObservadorAmbiente> getObservadores() {
        return observadores;
    }

}
