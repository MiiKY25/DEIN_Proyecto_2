package org.mikel.dein_proyecto_2.modelos;

import java.time.LocalDate;

/**
 * Representa un préstamo de un libro a un alumno.
 */
public class Prestamo {

    private int id;
    private Alumno alumno;
    private Libro libro;
    private LocalDate fecha_prestamo;

    /**
     * Constructor con todos los atributos.
     *
     * @param id             Identificador único del préstamo.
     * @param alumno         Alumno al que se le presta el libro.
     * @param libro          Libro prestado.
     * @param fecha_prestamo Fecha en que se realiza el préstamo.
     */
    public Prestamo(int id, Alumno alumno, Libro libro, LocalDate fecha_prestamo) {
        this.id = id;
        this.alumno = alumno;
        this.libro = libro;
        this.fecha_prestamo = fecha_prestamo;
    }

    /**
     * Constructor vacío.
     */
    public Prestamo() {
    }

    /**
     * Obtiene el identificador del préstamo.
     *
     * @return Identificador del préstamo.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del préstamo.
     *
     * @param id Identificador del préstamo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el alumno al que se le ha prestado el libro.
     *
     * @return Alumno del préstamo.
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * Establece el alumno al que se le ha prestado el libro.
     *
     * @param alumno Alumno del préstamo.
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    /**
     * Obtiene el libro prestado.
     *
     * @return Libro prestado.
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * Establece el libro prestado.
     *
     * @param libro Libro prestado.
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /**
     * Obtiene la fecha en que se realizó el préstamo.
     *
     * @return Fecha del préstamo.
     */
    public LocalDate getFecha_prestamo() {
        return fecha_prestamo;
    }

    /**
     * Establece la fecha del préstamo.
     *
     * @param fecha_prestamo Fecha del préstamo.
     */
    public void setFecha_prestamo(LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    /**
     * Devuelve una representación en cadena del préstamo.
     *
     * @return Cadena con el formato "ID - Alumno: Nombre - Libro: Título".
     */
    @Override
    public String toString() {
        return id + " - Alumno: " + alumno.getNombre() + " - Libro: " + libro.getTitulo();
    }
}
