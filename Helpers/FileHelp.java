/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      FileHelp.java
 * Package:   bloque01.Helpers
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.0
 *
 * Description:
 *     Funciones auxiliares para gestión de archivos y directorios.
 *       - enDirExs(): verifica si existe un directorio, y lo crea si no.
 *       - enFilExs(): verifica si existe un archivo, y lo crea si no.
 *
 *     Ambos métodos lanzan UncheckedIOException en caso de error,
 *     simplificando el manejo de excepciones en otros módulos.
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01.Helpers;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Clase de utilidades para comprobación y creación
 * de archivos y carpetas necesarias en el proyecto.
 *
 * <p>Uso típico:</p>
 * <ul>
 *   <li>Garantizar que existe el directorio "data/".</li>
 *   <li>Crear un archivo CSV vacío si no existe aún.</li>
 * </ul>
 *
 * <p>
 * En caso de error de E/S, se lanza {@link UncheckedIOException}
 * para no obligar a manejar checked exceptions en cada llamada.
 * </p>
 */
public class FileHelp {
    
    /**
    * Asegura que existe el directorio especificado.
    * Si no existe, lo crea.
    *
    * @param dir ruta del directorio a verificar.
    */
    public static void enDirExs(Path dir) {
        try {
            if (dir != null && !Files.exists(dir)) {
                Files.createDirectory(dir);
            }
        } catch (IOException ex) {
            throw new UncheckedIOException("Error al crear la carpeta: " + dir, ex) ;
        }
    }
    
     /**
     * Asegura que existe el archivo especificado.
     * Crea también el directorio padre si es necesario.
     *
     * @param file ruta del archivo a verificar.
     */
    public static void enFilExs(Path file) {
        try {
            enDirExs(file.getParent());
            if (!Files.exists(file))
                Files.createFile(file);
        } catch (IOException ex) {
            throw new UncheckedIOException("Error al crear el archivo: " + file, ex);
        }
        
    }
}
