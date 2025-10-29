/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      writeText.java
 * Package:   bloque01.operations
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.0
 *
 * Description:
 *     Módulo interactivo para trabajar con una frase de texto.
 *
 *     Flujo general:
 *       1. Pide al usuario una frase.
 *       2. Ofrece acciones sobre esa frase:
 *          - Pasar a mayúsculas/minúsculas.
 *          - Contar letras, dígitos y espacios.
 *          - Invertir palabras o invertir cada palabra.
 *          - Cifrar con desplazamiento César (+3).
 *       3. Permite guardar el texto en disco (saveMagaz).
 *       4. Permite cambiar la frase o salir.
 *
 *     Notas:
 *       · Usa prompt() que permite cancelar con "0".
 *       · Pregunta si se quiere guardar antes y después de las operaciones.
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01.operations;

import static bloque01.Exe.StatsTxt.CntDig;
import static bloque01.Exe.StatsTxt.CntLet;
import static bloque01.Exe.StatsTxt.CntWht;
import static bloque01.Exe.StatsTxt.Cyph;
import static bloque01.Exe.StatsTxt.InvTxt;
import static bloque01.Exe.StatsTxt.InvWrd;
import static bloque01.Exe.StatsTxt.ToLow;
import static bloque01.Exe.StatsTxt.ToUp;
import static bloque01.Helpers.ConHelp.pause;
import static bloque01.Helpers.ConHelp.printf;
import static bloque01.Helpers.ConHelp.prompt;
import static bloque01.Helpers.TabHelp.confirm;
import java.io.IOException;

/**
 * Proporciona el flujo interactivo de "escribir y analizar texto".
 *
 * <p>
 * Este módulo mantiene un bucle donde:
 * - se pide una nueva frase,
 * - se muestran opciones de análisis / transformación,
 * - se permite guardar,
 * - y se puede volver a introducir otra frase.
 * </p>
 */
public class writeText {
    
     /** Última opción elegida en el submenú interno. */
    public static String option;
    
    /** Flag temporal usado tras confirmaciones de guardado. */
    static boolean safv = false;
    
     /**
     * Método principal del módulo.
     *
     * <p>Controla todo el flujo:</p>
     * <ol>
     *   <li>Pedir frase al usuario (con opción a cancelar).</li>
     *   <li>Ofrecer menú de operaciones sobre esa frase.</li>
     *   <li>Permitir guardar el texto en CSV mediante saveMagaz.</li>
     *   <li>Repetir con una nueva frase si elige "9".</li>
     * </ol>
     * @throws java.io.IOException
     */
    public static void run() throws IOException {
          while (true) {
            // 1. Pedir frase base.
            String txt = prompt("Escriba una frase para analizar: %n");           
            
            // 2. Ofrecer guardado inmediato.
            safv = confirm("¿Desea guardar el texto?");            
            if (safv) { saveMagaz op = new saveMagaz(); op.saveText(txt); }
                         
            if (txt == null) { return;} 
            
            // 3. Submenú interno de operaciones sobre 'txt'.
            do {
            printf("%n=========SELECCIONE UNA ACCIÓN========%n");
            printf("1 - Convertir a mayúsculas.%n");
            printf("2 - Convertir a minúsculas.%n");
            printf("3 - Contar letras.%n");
            printf("4 - Contar dígitos.%n");
            printf("5 - Contar espacios en blanco.%n");
            printf("6 - Invertir el orden de las palabras.%n");
            printf("7 - Invertir texto.%n");
            printf("8 - Encriptar (César + 3.%n");
            printf("9 - Cambiar frase.%n");
            printf("0 - Cancelar.%n");
            
            option = prompt("Elija una opción:");
            
            if (option == null) break;
                       
            printf("%s%n", txt);
            
            // 4. Ejecutar acción seleccionada.
            switch (option) {
                case "1" -> { printf("Text en mayúsculas: %n%s%n", ToUp(txt)); }
                case "2" -> { printf("Text en minúsculas: %n%s%n", ToLow(txt)); }
                case "3" -> { printf("Nº de letras:%n%d%n",CntLet(txt)); }
                case "4" -> { printf("Nº de dígitos:%n%d%n",CntDig(txt)); }
                case "5" -> { printf("Nº de espacios en blanco: %n%d%n",CntWht(txt)); }
                case "6" -> { printf("Palabras invertidas:%n%s%n", InvWrd(txt)); }
                case "7" -> { printf("Texto inveritdo:%n%s%n", InvTxt(txt)); }
                case "8" -> { printf("Texto cifrado:%n%s%n", Cyph(txt)); }       
                case "9" -> { printf(">");}
                default  -> { printf("Opción incorrecta.%n");}
                
            }
            
            // 5. Ofrecer guardado tras la operación.
            safv = confirm("¿Desea guardar el texto?");            
            if (safv) { saveMagaz op = new saveMagaz(); op.saveText(txt); }
           pause();   
           
           // 6. Si el usuario canceló dentro del submenú, salimos del módulo entero
          } while (!option.equals("9") && option != null);       
            if (option == null) { return; }       
        }        
    }
}
