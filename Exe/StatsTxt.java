/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      StatsTxt.java
 * Package:   bloque01.Exe
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.1
 *
 * Description:
 *     Módulo interactivo de utilidades de texto.
 *
 *     Funcionalidades principales:
 *       - Menú propio para trabajar con texto guardado.
 *       - Escribir nuevo texto (writeText.run()).
 *       - Consultar / modificar texto ya guardado (updateMagaz.run()).
 *       - Pequeñas utilidades de análisis/manipulación:
 *           · Conversión a mayúsculas / minúsculas.
 *           · Contar caracteres, dígitos y espacios en blanco.
 *           · Invertir palabras o invertir el texto letra a letra.
 *           · Cifrado César (+3 posiciones).
 *           · Guardar el texto con confirmación.
 *
 *     Notas técnicas:
 *       · Usa ConHelp.printf(), ConHelp.prompt() para I/O en consola.
 *       · Usa TabHelp.confirm() para confirmaciones tipo "¿Seguro?".
 *       · Usa saveMagaz para persistir.
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01.Exe;
import bloque01.operations.writeText;
import static bloque01.Helpers.ConHelp.*;
import static bloque01.Helpers.TabHelp.confirm;
import bloque01.operations.saveMagaz;
import bloque01.operations.updateMagaz;
import java.io.IOException;

/**
 * Clase/módulo de operaciones de texto del Bloque 1.
 *
 * <p>Flujo general de uso:</p>
 * <ol>
 *   <li>Mostrar un pequeño menú local con opciones de texto.</li>
 *   <li>"1": escribir texto nuevo (alta contenido).</li>
 *   <li>"2": consultar o modificar texto existente (updateMagaz).</li>
 *   <li>"0": salir del módulo y volver al menú principal (Main).</li>
 * </ol>
 *
 * <p>
 * Importante: Este módulo se llama desde el menú principal (Main),
 * pero tiene su propio flujo interno y llama a otras operaciones
 * que también tienen interacción con el usuario.
 * </p>
 */
public class StatsTxt {
    
     /**
     * Punto de entrada del módulo de texto.
     *
     * Muestra el submenú y deriva en la operación elegida.
     * Devuelve true si el usuario pidió salir explícitamente,
     * false si no.
     *
     * @return boolean indicando si el usuario ha elegido "salir".
     */
    public static boolean run() throws IOException {
        
        String option;        

        printf("%n=================================%n");
        printf("======MÓDULO DE ANÁLISIS DE FRASES======%n");
        printf("===================================%n");
        printf("1- ESCRIBIR NUEVO TEXTO   ================%n");
        printf("2- CONSULTAR/MODIFICAR TEXTO  ===========%n");
        
        option = prompt("Elija una opción");
         switch(option) {
                case "1" -> writeText.run(); 
                case "2" -> updateMagaz.run();
                case "0" -> { printf("Saliendo del programa..."); return true; }
                default -> printf("Opción no válida. Inténtelo de nuevo");                
            }               
        return false;               
    }
        
    // Utilidades
    /** Convierte todo el texto a MAYÚSCULAS. */
    public static String ToUp(String text) { return text.toUpperCase(); }
    
    /** Convierte todo el texto a minúsculas. */
    public static String ToLow(String text) { return text.toLowerCase(); }
    
    /** Cuenta el número total de caracteres (incluye espacios). */
    public static int CntLet(String text) { return  text.length(); }
    
     /**
     * Cuenta cuántos dígitos [0-9] hay en el texto.
     * Recorre char a char y usa Character.isDigit().
     */
    public static int CntDig(String text) { int cont = 0; for (char c : text.toCharArray()) { if (Character.isDigit(c)) cont++; }
        return cont;
    }
    
     /**
     * Cuenta cuántos espacios en blanco hay en el texto.
     * Usa Character.isWhitespace() para capturar espacio,
     * tabulador, salto de línea, etc.
     */
    public static int CntWht(String text) { int cont = 0; for (char c : text.toCharArray()) { if(Character.isWhitespace(c)) cont++; }
        return  cont;
    }
    
      /**
     * Invierte el orden de las palabras.
     * Ej: "hola qué tal" -> "tal qué hola"
     */
    public static String InvWrd(String text) {        
        String[] words = text.split("\\s+");
        StringBuilder inv = new StringBuilder();        
        for (int i = words.length -1; i >= 0; i--) { inv.append(words[i]); if (i > 0) inv.append(" "); }
        return  inv.toString();
    }
    
     /**
     * Invierte internamente cada palabra pero mantiene el orden.
     * Ej: "hola qué tal" -> "aloh éuq lat"
     */
    public static String InvTxt(String text) {
        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();
        for(String w : words) { result.append(new StringBuilder(w).reverse()).append(" "); }
        return  result.toString();
    }
    
     /**
     * Cifra el texto con un desplazamiento César de +3.
     * Solo rota letras A-Z / a-z. Dígitos, espacios, etc., se respetan.
     */
    public static String Cyph(String text) {
        StringBuilder ciphy = new StringBuilder();
        
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) { 
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + 3) % 26 + base);
            }
            ciphy.append(c);
        }
        return  ciphy.toString();
    }
    
     /**
     * Pide confirmación y, si el usuario acepta, guarda el texto.
     *
     * Llama internamente a saveMagaz para persistir en CSV.
     */
    public static void Safv(String text) throws IOException {        
        boolean safv = false;
        safv = confirm("¿Desea guardar el texto?");        
        if (!safv) {printf("Operación cancelada.%n");}
        saveMagaz op = new saveMagaz();
        op.saveText(text);
    }
           
}   


