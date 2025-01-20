package org.mikel.dein_proyecto_2.modelos;

import java.time.LocalDateTime;

public class Prestamo {

    private int id;
    private Alumno alumno;
    private Libro libro;
    private LocalDateTime fecha_prestamo;

    public Prestamo(int id, Alumno alumno, Libro libro, LocalDateTime fecha_prestamo) {
        this.id = id;
        this.alumno = alumno;
        this.libro = libro;
        this.fecha_prestamo = fecha_prestamo;
    }

    public Prestamo() {
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

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", alumno=" + alumno +
                ", libro=" + libro +
                ", fecha_prestamo=" + fecha_prestamo +
                '}';
    }
}
