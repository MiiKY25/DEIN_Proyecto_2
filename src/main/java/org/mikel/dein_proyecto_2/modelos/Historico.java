package org.mikel.dein_proyecto_2.modelos;

import java.time.LocalDateTime;

public class Historico {

    private int id;
    private Alumno alumno;
    private Libro libro;
    private LocalDateTime fecha_prestamo;
    private LocalDateTime fecha_devolucion;

    public Historico(int id, Alumno alumno, Libro libro, LocalDateTime fecha_devolucion, LocalDateTime fecha_prestamo) {
        this.id = id;
        this.alumno = alumno;
        this.libro = libro;
        this.fecha_devolucion = fecha_devolucion;
        this.fecha_prestamo = fecha_prestamo;
    }

    public Historico() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDateTime getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(LocalDateTime fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public LocalDateTime getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(LocalDateTime fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    @Override
    public String toString() {
        return id + " - Alumno: " + alumno.getNombre() + " - Libro: " + libro.getTitulo();
    }
}
