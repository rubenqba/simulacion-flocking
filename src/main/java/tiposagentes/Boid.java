package tiposagentes;

import java.awt.*;

import Graphics.Forma;
import Vecindades.VecindadObjetivos;
import core.AgenteMovil;
import core.AmbienteMovil;
import core.Movimiento;
import core.Percepcion;
import core.Util;
import core.Vector;
import lombok.Getter;
import lombok.Setter;

public class Boid extends AgenteMovil {

    public Objetivo objetivo;// :PpP
    private VecindadObjetivos objetivos;
    private Boid mejorDelGrupo;
    private double valorFuncionObjetivo = 0.0;
    @Getter
    @Setter
    private boolean courage;
    private boolean haColisionado = false;
    private boolean enEstadoColision = false;

    public Boid(double x, double y, AmbienteMovil ambiente) {
        super(x, y, ambiente);
        courage = true;
    }

    public Boid(double x, double y, AmbienteMovil ambiente, Movimiento movimiento) {
        super(x, y, ambiente, movimiento);
        courage = true;
    }

    public Boid(double x, double y, double z, AmbienteMovil ambiente) {
        super(x, y, z, ambiente);
        courage = true;
    }

    @Override
    public void percibirme(Percepcion p) {
        p.percibir(this);

    }

    @Override
    public String toString() {
        Vector pos = this.getPosicion();
        return "Boid(" + pos.get(0) + "," + pos.get(1) + ")";
    }

    public VecindadObjetivos getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(VecindadObjetivos objetivos) {
        this.objetivos = objetivos;
        objetivo = objetivos.get(0);
    }

    @Override
    public void draw() {
        Forma form = new Forma();
        form.Dibujar_esfera(this);
    }

    @Override
    public void pintar(Graphics g) {

        int cordx = (int) Math.round(this.getPosicion().get(0));
        int cordy = (int) Math.round(getPosicion().get(1));
        int radio = (int) Math.round(getRadio());
        Util.pintarCirculo(g, cordx, cordy, radio, courage ? Color.BLUE : Color.RED);
    }

    public Objetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    public boolean isHaColisionado() {
        return haColisionado;
    }

    public void setHaColisionado(boolean haColisionado) {
        this.haColisionado = haColisionado;
    }

    public boolean isEnEstadoColision() {
        return enEstadoColision;
    }

    public void setEnEstadoColision(boolean enEstadoColision) {
        this.enEstadoColision = enEstadoColision;
    }

    public Boid getMejorDelGrupo() {
        return mejorDelGrupo;
    }

    public void setMejorDelGrupo(Boid mejorDelGrupo) {
        this.mejorDelGrupo = mejorDelGrupo;
    }

    public double getValorFuncionObjetivo() {
        return valorFuncionObjetivo;
    }

    public void setValorFuncionObjetivo(double valorFuncionObjetivo) {
        this.valorFuncionObjetivo = valorFuncionObjetivo;
    }
}
