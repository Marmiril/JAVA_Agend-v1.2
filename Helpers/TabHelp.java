/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      TabHelp.java
 * Package:   bloque01.Helpers
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.0
 *
 * Description:
 *     Funciones auxiliares para mostrar tablas y confirmar acciones
 *     en la consola del Bloque 1.
 *
 *     - confirm(): solicita confirmación (S/N o 0 para cancelar)
 *     - printHeader(): imprime una tabla con listado de revistas (Magazine)
 *
 *     Mejora la legibilidad y evita repetir lógica en los módulos
 *     de lectura, guardado y actualización de datos.
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01.Helpers;

import bloque01.Classes.Magazine;
import static bloque01.Helpers.ConHelp.*;
import java.util.List;

/**
 * Clase de utilidades para impresión tabular y confirmaciones.
 *
 * <p>
 * Contiene métodos comunes para mostrar listas en formato tabla
 * y pedir confirmaciones S/N al usuario.
 * </p>
 */
public class TabHelp {
    
     /**
     * Solicita confirmación tipo S/N (sí/no) al usuario.
     *
     * <p>Reglas:</p>
     * <ul>
     *   <li>"s", "si" o "sí" → devuelve true</li>
     *   <li>"n" o "no" → devuelve false</li>
     *   <li>"0" → se considera cancelación → devuelve false</li>
     *   <li>Repite mientras la entrada sea inválida.</li>
     * </ul>
     *
     * @param phrase Mensaje que se mostrará como prompt.
     * @return true si confirma, false si cancela o responde "no".
     */
    public static boolean confirm (String phrase) {
        while (true) {
        String answer = prompt(phrase);     
        if (answer== null) return false;
        
        String answ = answer.trim().toLowerCase();   
        answ = answ.trim().toLowerCase();
        if (answ.equals("s") || answ.equals("sí") || answ.equals("si")) return true;
        if (answ.equals("no") || answ.equals("n")) return false;
        
        printf("Respuesta incorrecta. Indique S-N (0 para cancelar): %n"); 
        }
    }
    
     /**
     * Imprime un encabezado de tabla y las revistas (Magazine) recibidas.
     
     * <p>Si la lista está vacía o es nula, muestra mensajes informativos.</p>
     *
     * @param magaz lista de revistas a mostrar en consola.
     */
    public static void printHeader(List<Magazine> magaz) {
               
        if (magaz == null) { printf("No hay anotaciones que mostrar."); }
        if (magaz.isEmpty()) { printf("No se han encontrado datos con ese criterio."); }
        
        
        printf("%n%-4s %-20s %-20s %-16s",
                "ID", "Título", "Autor", "Fecha");
        printf("%n---------------------------------------------------------------------------------------------------------%n");
        
        for (Magazine m : magaz) {
            printf("%n%-4d %-20s %-20s %-16s%n",
                    m.getId(),
                    m.getTitle(),
                    m.getAuthor(),
                    m.getCreatedAt());
            
        }
    }
    
}
