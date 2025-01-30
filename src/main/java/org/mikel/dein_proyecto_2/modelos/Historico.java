package org.mikel.dein_proyecto_2.modelos;

import java.time.LocalDate;

/**
 * Representa un registro histórico de un préstamo de libro realizado por un alumno.
 */
public class Historico {

    private int id;
    private Alumno alumno;
    private Libro libro;
    private LocalDate fecha_prestamo;
    private LocalDate fecha_devolucion;

    /**
     * Constructor con todos los atributos.
     *
     * @param id              Identificador del registro histórico.
     * @param alumno          Alumno que realizó el préstamo.
     * @param libro           Libro prestado.
     * @param fecha_prestamo  Fecha en que se realizó el préstamo.
     * @param fecha_devolucion Fecha en que se devolvió el libro.
     */
    public Historico(int id, Alumno alumno, Libro libro, LocalDate fecha_prestamo, LocalDate fecha_devolucion) {
        this.id = id;
        this.alumno = alumno;
        this.libro = libro;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    /**
     * Constructor vacío.
     */
    public Historico() {
    }

    /**
     * Obtiene el identificador del registro histórico.
     *
     * @return ID del registro histórico.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del registro histórico.
     *
     * @param id ID del registro histórico.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el alumno que realizó el préstamo.
     *
     * @return Alumno que realizó el préstamo.
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * Establece el alumno que realizó el préstamo.
     *
     * @param alumno Alumno que realizó el préstamo.
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
     * Establece la fecha en que se realizó el préstamo.
     *
     * @param fecha_prestamo Fecha del préstamo.
     */
    public void setFecha_prestamo(LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    /**
     * Obtiene la fecha en que se devolvió el libro.
     *
     * @return Fecha de devolución del libro.
     */
    public LocalDate getFecha_devolucion() {
        return fecha_devolucion;
    }

    /**
     * Establece la fecha en que se devolvió el libro.
     *
     * @param fecha_devolucion Fecha de devolución del libro.
     */
    public void setFecha_devolucion(LocalDate fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    /**
     * Devuelve una representación en cadena del registro histórico.
     *
     * @return Cadena con el formato "ID - Alumno: Nombre - Libro: Título".
     */
    @Override
    public String toString() {
        return id + " - Alumno: " + alumno.getNombre() + " - Libro: " + libro.getTitulo();
    }
}
