package core;

import java.awt.*;

public abstract class AgenteMovil extends Agente {

    private Vector posicion;
    private Vector velocidad;
    private AmbienteMovil ambiente;
    private Movimiento mov;
    private Percepcion percepcion;
    private double velMax = 2;
    private double rangoDeInteraccion = Double.POSITIVE_INFINITY;
    private double radio = 4;

    public AgenteMovil(AmbienteMovil ambiente) {
        this(new Vector(2), ambiente);
    }

    public AgenteMovil(double x, double y, AmbienteMovil ambiente) {
        this(new Vector(x, y), ambiente);
    }

    public AgenteMovil(double x, double y, AmbienteMovil ambiente, Movimiento mov) {
        this(new Vector(x, y), ambiente, mov);
    }

    public AgenteMovil(double x, double y, double z, AmbienteMovil ambiente) {
        this(new Vector(x, y, z), ambiente);
    }

    public AgenteMovil(double x, double y, double z, AmbienteMovil ambiente, Movimiento mov) {
        this(new Vector(x, y, z), ambiente, mov);
    }

    public AgenteMovil(Vector vectorPosicion, AmbienteMovil ambiente) {
        this(vectorPosicion, ambiente, null);
    }

    public AgenteMovil(Vector vectorPosicion, AmbienteMovil ambiente, Movimiento mov) {
        this.posicion = vectorPosicion;
        this.ambiente = ambiente;
        this.ambiente.agregarAgente(this);

        this.velocidad = new Vector(posicion.getDimension());
        percepcion = new Percepcion();

        this.setMov(mov);
    }

    public void setPosicion(Vector vectorPosicion) {
        this.posicion = vectorPosicion;
    }

    public Vector getPosicion() {
        return posicion;
    }

    public void setVelocidad(Vector velocidad) {
        this.velocidad = velocidad;
    }

    public Vector getVelocidad() {
        return velocidad;
    }

    public void setAmbiente(AmbienteMovil ambiente) {
        this.ambiente = ambiente;
        ambiente.agregarAgente(this);
    }

    public AmbienteMovil getAmbiente() {
        return ambiente;
    }

    public void setMov(Movimiento mov) {
        this.mov = mov;
    }

    public Movimiento getMov() {
        return mov;
    }

    @Override
    public void actuar() {
        mover();

    }

    public void mover() {
        mov.mover(this);
    }

    public void setVelMax(double velMax) {
        this.velMax = velMax;
    }

    public double getVelMax() {
        return velMax;
    }

    public void setRangoInteraccion(double rangoInteraccion) {
        this.rangoDeInteraccion = rangoInteraccion;
    }

    public double getRangoInteraccion() {
        return this.rangoDeInteraccion;
    }

    public void draw()// Este metodo lo utiliza el paquete que me hizo Patrick
                      // para la simulación en 3D, cada subclase lo sobreescribe
    {

    }

    public void pintar(Graphics g) {
        int x = (int) getPosicion().get(0);
        int y = (int) getPosicion().get(1);
        int radio = (int) this.radio;
        Util.pintarCirculo(g, x, y, radio, Color.DARK_GRAY);
    }

    public abstract void percibirme(Percepcion p);


    public void setRadio(double radio) {
        this.radio = radio;
    }

    public double getRadio() {
        return radio;
    }

    public void setPercepcion(Percepcion percepcion) {
        this.percepcion = percepcion;
    }

    public Percepcion getPercepcion() {
        return percepcion;
    }

    public Percepcion calcularPercepcion() {
        percepcion.calcularVecindad(this);
        return percepcion;
    }

}
