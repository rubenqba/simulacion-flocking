package experimentos;

import Vecindades.VecindadObjetivos;
import core.*;
import implementacion.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Cleanup;
import metricas.ObservadorAmbiente;
import metricas.ObservadorMetricas;
import org.apache.commons.lang3.time.StopWatch;
import reportes.FileManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@AllArgsConstructor
@Builder
public final class Experimento implements Callable<Void> {

    private boolean showSimulation;
    private Integer agentes;
    private Integer valientes;
    private Integer cobardes;
    private Double c1Max, c1Min, c2, c3;
    private TipoMovimiento movimiento;
    private boolean bravery = false;

    @Override
    public Void call() throws Exception {
        test();
        return null;
    }

    public String getName() {
        if (bravery)
            return String.format("Prueba_v%d-c%d_%1.2f_%1.2f_%1.2f_%s", valientes, cobardes,
                    c1Max.doubleValue(), c2.doubleValue(), c3.doubleValue(), movimiento.name());
        else
            return String.format("Prueba_%d_%1.2f_%1.2f_%1.2f_%s", agentes.intValue(),
                    c1Max.doubleValue(), c2.doubleValue(), c3.doubleValue(), movimiento.name());
    }

    public void test() {
        try {
            running();
        } catch (InterruptedException e) {
            System.err.println("Error en test");
        }
    }

    protected void running() throws InterruptedException {

        int simulationDelay = 50;
        if (valientes != null && valientes > 0 && cobardes != null && cobardes > 0)
            bravery = true;

        StopWatch watch = new StopWatch();
        System.out.print(getName());
        Escenarios e = Escenarios.builder()
                .calcularMetricas(true)
                .sensarEstado(false)
                .guardarMetricasArchivo(!showSimulation)
                .mostrarSimulacion(showSimulation)
                .iteraciones(2000)
                .configuracionAgente(ConfiguracionAgente.builder()
                        .cantidadAgentes(bravery ? valientes + cobardes : agentes)
                        .cantidadValientes(valientes)
                        .cantidadCobardes(cobardes)
                        .cantidadObjetivos(1)
                        .radioAgente(3)
                        .rangoDeInteraccion(25)
                        .radioObstaculos(20)
                        .vecindad(new VecindadObjetivos())
                        .build())
                .configuracionModelo(ConfiguracionModelo.builder()
                        .c1(c1Max)
                        .c1Min(c1Min)
                        .c2(c2)
                        .c3(c3)
                        .zonaVirtual(15)
                        .obstaculos(1)
                        .velMax(4)
                        .maxExtension(610)
                        .maxPolarizacion(180)
                        .build())
                .ambiente(new AmbienteMovil())
                .build();

        AgenteMovilBuilder builder = AgenteMovilBuilder.builder()
                .withAmbiente(e.getAmbiente())
                .withCourage(true)
                .withMovimiento(e.getConfiguracionModelo().buildMovimientoCuatroEsquinas())
                .withConfiguracionAgente(e.getConfiguracionAgente());

        for (int i = 0; i < e.getConfiguracionAgente().getCantidadObjetivos(); i++) {
            builder.buildObjetivo(100, 100);
        }

        Vector infIzq = new Vector(0, 0);
        Vector supDer = new Vector(Ambiente.tamx, Ambiente.tamy);
        builder.withMovimiento(e.getConfiguracionModelo().buildMovimiento(movimiento));

        if (bravery) {
            for (int i = 0; i < e.getConfiguracionAgente().getCantidadValientes(); i++) {
                Vector v = Util.aleatorioEnArea(infIzq, supDer);
                builder.buildBoid(v.get(0), v.get(1));
            }
            builder.withCourage(false);
            for (int i = 0; i < e.getConfiguracionAgente().getCantidadCobardes(); i++) {
                Vector v = Util.aleatorioEnArea(infIzq, supDer);
                builder.buildBoid(v.get(0), v.get(1));
            }
        } else {
            for (int i = 0; i < e.getConfiguracionAgente().getCantidadAgentes(); i++) {
                Vector v = Util.aleatorioEnArea(infIzq, supDer);
                builder.buildBoid(v.get(0), v.get(1));
            }
        }

        Simulacion s = Simulacion.builder()
                .name(getName())
                .ambiente(e.getAmbiente())
                .delaySimulacion(e.isMostrarSimulacion() ? simulationDelay : 0)
                .iteraciones(e.getIteraciones())
                .observadores(e.getObservadores())
                .habilitarPintado(e.isMostrarSimulacion())
                .build();

        watch.start();
        Thread t = new Thread(s);
        t.start();
        t.join();
        watch.stop();
        System.out.println(String.format("; time=%d seg", TimeUnit.MILLISECONDS.toSeconds(watch.getTime())));

        if (e.isGuardarMetricasArchivo()) {

            try {
                @Cleanup
                FileOutputStream report = new FileOutputStream(FileManager.crearArchivo(getName(), "metricas"));

                e.getConfiguracionModelo().saveToFile(report);
                report.flush();
                e.getConfiguracionAgente().saveToFile(report);
                report.flush();
                for (ObservadorAmbiente observer : s.getObservadores()) {
                    observer.saveToFile(report);
                    report.flush();
                }
            } catch (IOException ex) {
                System.err.println("No se pudo crear archivo: " + ex.getLocalizedMessage());
            }
        }
        if (e.isMostrarSimulacion()) {
            for (ObservadorAmbiente observer : s.getObservadores()) {
                if (ObservadorMetricas.class.isAssignableFrom(observer.getClass())) {
                    ((ObservadorMetricas) observer).mostrarReportesCortos();
                }
            }
            try {
                System.in.read();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}