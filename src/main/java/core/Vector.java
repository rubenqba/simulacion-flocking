package core;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Vector {
    private double datos[];
    private int dimension = 2;

    public Vector(double... datos) {
        this.datos = new double[datos.length];
        System.arraycopy(datos, 0, this.datos, 0, datos.length);
        dimension = datos.length;
    }

    public Vector(int dimension) {
        this.dimension = dimension;
        datos = new double[dimension];
    }

    public int getDimension() {
        return dimension;
    }

    public void sumar(Vector v) {
        IntStream.range(0, dimension)
                .forEach(i -> datos[i] += v.get(i));
    }

    public void restar(Vector v) {
        IntStream.range(0, dimension)
                .forEach(i -> datos[i] -= v.get(i));
    }

    public void multiplicarEscalar(double escalar) {
        IntStream.range(0, dimension)
                .forEach(i -> datos[i] *= escalar);
    }

    public double norma() {
        return Math.sqrt(Arrays.stream(datos).parallel().map(d -> d * d).sum());
    }

    public double distancia(Vector v) {
        return Math.sqrt(IntStream.range(0, dimension)
                .mapToDouble(i -> (datos[i] - v.get(i)) * (datos[i] - v.get(i)))
                .reduce(0, Double::sum));
    }

    public double productoEscalar(Vector v) {
        return IntStream.range(0, dimension)
                .mapToDouble(i -> datos[i] * v.get(i))
                .reduce(0, Double::sum);
    }

    public Vector clonar() {
        return new Vector(datos);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !Vector.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        Vector v = (Vector) obj;
        if (v.getDimension() != dimension)
            return false;

        return IntStream.range(0, dimension)
                .parallel()
                .filter(i -> datos[i] != v.get(i))
                .findFirst()
                .orElse(-1) == -1;
    }

    public void normalizar(double cota) {
        Double norma = norma();
        if (!norma.isNaN()) {
            this.multiplicarEscalar(cota / norma);
        }
    }

    public Vector unitario() {
        Vector nuevo = clonar();
        nuevo.normalizar(1.0);
        return nuevo;
    }

    public double get(int indice) {
        return datos[indice];
    }

    public void set(Vector v) {
        datos = new double[v.getDimension()];
        dimension = v.getDimension();
        System.arraycopy(v.getDatos(), 0, datos, 0, dimension);
    }

    public void set(double... datos) {
        System.arraycopy(datos, 0, this.datos, 0, dimension);
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("[");
        s.append(StringUtils.join(Arrays.stream(datos).mapToObj(Double::toString).collect(Collectors.toList()), ", "));
        s.append("]");
        return s.toString();
    }

    public double[] getDatos() {
        return datos;
    }

    public static void main(String ar[]) {
        Vector v = new Vector(5, 4);
        Vector u = new Vector(3, 4);

        System.out.println(u);
        System.out.println(v);

        v.unitario();
        System.out.println(v.norma());


        System.out.println(u.norma());

        System.out.println(u.productoEscalar(v));

        System.out.println(u.equals(v));
        System.out.println(u.equals(u));
        System.out.println(v.equals(u));
        System.out.println(v.equals(v));

        System.out.println(v.distancia(u));
        System.out.println(u.distancia(v));
        System.out.println(v.distancia(v));
        System.out.println(u.distancia(u));
    }
}
