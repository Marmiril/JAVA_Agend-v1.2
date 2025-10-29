/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      Main.java
 * Package:   bloque01
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.2
 *
 * Description:
 *     Punto de entrada del Bloque 1.
 *     Muestra un menú interactivo en consola que permite
 *     lanzar los ejercicios prácticos:
 *       1) StatsDeTexto
 *       2) ConversorUnidades
 *       3) MiniCalculadoraCientifica
 *
 * License:
 *     MIT License
 * ============================================================
 */
package bloque01;
import bloque01.Exe.StatsTxt;
import static bloque01.Helpers.ConHelp.*; 
import java.io.IOException;

/**
 * Clase responsable de inicializar el Bloque 1 y ofrecer
 * un menú para ejecutar cada ejercicio práctico.
 *
 * <p>Flujo general:</p>
 * <ol>
 *   <li>Mostrar menú principal con opciones disponibles.</li>
 *   <li>Leer la opción del usuario con validación.</li>
 *   <li>Lanzar el ejercicio correspondiente llamando a su método run().</li>
 *   <li>Repetir hasta que el usuario elija salir (opción 0).</li>
 * </ol>
 *
 * <p>
 * Usa ConsoleHelper para manejar la consola con:
 * printf(), readLine() y pause().
 * </p>
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        
        String option;
        
        do {
            // Menú principal.
            printf("%n=============================%n");
            printf("BLOQUE 1 - Ejercicios JAVA%n");
            printf("=============================%n");
            printf("1. Stats de texto%n");
            printf("2. Conversor de unidades%n");
            printf("3. Mini calculadora científica%n");
            printf("0. Salir%n");
            printf("-----------------------------%n");
            option =  readLine("Elija una opción: ");
            
            // Control de flujo según opción elegida.
            switch(option) {
                case "1" -> StatsTxt.run();
                case "2" -> printf("ConversorUnidades por implementar");
                case "3" -> printf("Minicalculadora por implementar");
                case "0" -> printf("Saliendo del programa...");
                default -> printf("Opción no válida. Inténtelo de nuevo");                
            }                     
        } while (!option.equals("0"));
    }
}
