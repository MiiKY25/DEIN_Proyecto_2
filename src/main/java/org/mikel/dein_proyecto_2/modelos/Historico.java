package org.mikel.dein_proyecto_2.modelos;

import java.time.LocalDate;

public class Historico {

    private int id;
    private Alumno alumno;
    private Libro libro;
    private LocalDate fecha_prestamo;
    private LocalDate fecha_devolucion;

    public Historico(int id, Alumno alumno, Libro libro,LocalDate fecha_prestamo,LocalDate fecha_devolucion) {
        this.id = id;
        this.alumno = alumno;
        this.libro = libro;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
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

    public LocalDate getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public LocalDate getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(LocalDate fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    @Override
    public String toString() {
        return id + " - Alumno: " + alumno.getNombre() + " - Libro: " + libro.getTitulo();
    }
}
