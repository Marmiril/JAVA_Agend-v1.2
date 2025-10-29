/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      Config.java
 * Package:   bloque01
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.0
 * 
 * Description:
 *     Clase de configuración general del Bloque 1.
 *     - Define rutas de archivos y separadores CSV.
 *     - Establece el patrón de fecha estándar.
 *     - Centraliza constantes usadas por otras clases.
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01;

/**
 * Clase que contiene las constantes globales de configuración
 * para el Bloque 1.
 *
 * <p>Incluye las rutas de archivos, el separador CSV y
 * el patrón de formato de fecha.</p>
 */

public class Config {
    
    /** Ruta del archivo CSV principal. */
    public static final String DATA_FILE = "data/bloque01/magaz.csv";
    
    /** Separador usado en los archivos CSV. */
    public static final String SEP = ";";
    
    /** Patrón de formato de fecha (compatible con {@link java.time.format.DateTimeFormatter}). */
    public static final String DATE_PATTERN =  "yyyy-MM-dd HH:mm";   
    
}
