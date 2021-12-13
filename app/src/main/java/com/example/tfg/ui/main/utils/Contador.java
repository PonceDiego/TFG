package com.example.tfg.ui.main.utils;

public class Contador {
    private static Contador instanceOf;
    public static int total = 0;

    private Contador (int suma){
        total=suma;
    }

    public static void actualizarContador(int suma){
        if (instanceOf == null){
            instanceOf = new Contador(suma);
        }else {
            total+=suma;
        }
    }
    public static void reiniciarContador(){
        total = 0;
    }

}
