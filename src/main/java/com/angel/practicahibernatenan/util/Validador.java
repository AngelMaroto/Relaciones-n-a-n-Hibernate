package com.angel.practicahibernatenan.util;

public class Validador {
    public static boolean esEdadValida(int edad) {
        return edad >= 16 && edad <= 50;
    }
    public static boolean esDorsalValido(int dorsal) {
        return dorsal >= 0 && dorsal <= 99;
    }
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}