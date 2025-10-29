/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      ConHelp.java
 * Package:   bloque01.Helpers
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.0
 *
 * Description:
 *     Utilidades básicas para interacción en consola:
 *       - printf():  alias de System.out.printf()
 *       - println(): printf() + salto de línea automático
 *       - pause():   espera a que el usuario pulse ENTER
 *       - readLine(): lee texto no vacío
 *       - prompt():  pide texto con opción de cancelar con "0"
 *
 *     Este helper centraliza toda la E/S de consola del bloque,
 *     para mantener estilo uniforme y evitar repetir lógica de validación.
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01.Helpers;

import java.util.Scanner;

/**
 * Métodos de ayuda para imprimir en consola y leer entrada del usuario.
 *
 * <p>Notas:</p>
 * <ul>
 *   <li>Usa siempre System.out.printf() para salida formateada.</li>
 *   <li>Valida que el usuario no meta cadenas vacías.</li>
 *   <li>La función prompt() permite cancelar escribiendo "0".</li>
 * </ul>
 */
public class ConHelp {
    
    /** Scanner único compartido por toda la app. */
    private static final Scanner SC = new Scanner (System.in, "UTF-8");  

     /**
     * Imprime texto formateado en consola.
     * Atajo para System.out.printf().
     *
     * @param format Formato tipo printf (ej: "%s", "%n", "%.2f"...).
     * @param args   Valores a inyectar en el formato.
     */
    public static void printf(String format, Object... args) { System.out.printf(format, args); }
    
     /**
     * Igual que printf() pero añade salto de línea al final.
     */
    public static void println(String format, Object... args) { System.out.printf(format +"%n" , args); }
        
     /**
     * Pausa la ejecución hasta que el usuario pulse ENTER.
     * Útil para que pueda leer resultados antes de continuar.
     */
    public static void pause() { System.out.printf("Pulse ENTER para continuar..."); SC.nextLine(); }       
    
     /**
     * Solicita una línea de texto obligatoria (no puede ir vacía).
     * Si el usuario mete una cadena vacía, vuelve a pedirla.
     *
     * @param prompt Mensaje mostrado al usuario.
     * @return Texto introducido (garantizado no vacío).
     */
    public static String readLine(String prompt) {
        String input;
        while (true) { System.out.printf(prompt + " "); input = SC.nextLine();
            if(!input.isEmpty()) { return input; }
            printf("El campo no puede estar vacío. Inténtelo de nuevo: %n");
        }
    }
    
    /**
     * Solicita una entrada de texto al usuario con posibilidad de cancelar.
     *
     * Muestra el mensaje seguido de "(0 para cancelar)".  
     * - Si el usuario escribe "0": devuelve null y muestra "Operación cancelada".
     * - Si el usuario deja vacío: insiste.
     * - Si escribe algo válido: lo devuelve tal cual.
     *
     * @param message Mensaje base del prompt.
     * @return Texto introducido por el usuario, o null si cancela.
     */
    public static String prompt(String message) {
        String input;        
        while(true) {
            printf("%s (0 para cancelar): %n", message);
            input = SC.nextLine();
            if (input == null || input.isBlank()) {  printf("El campo no puede estar vacío.%n"); }       
            
            String confirm = input.trim().toLowerCase();
            if (confirm.equals("0")) { printf("Operación cancelada.%n"); return null; }
            if (!confirm.isEmpty()) { return input; }                     
            }
        }
    }    


