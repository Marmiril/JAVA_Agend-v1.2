/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      NoteRepository.java
 * Package:   bloque01.Repository
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.0
 *
 * Description:
 *     Acceso a datos de Magazine en CSV.
 *
 *     Funciones principales:
 *       - loadAll(): lee el CSV y devuelve la lista de Magazine.
 *       - saveAll(): guarda toda la lista en el CSV.
 *       - findById(): busca una nota por ID.
 *       - checkEx(): comprueba existencia de un valor (por título, autor, etc.).
 *
 *     Detalles técnicos:
 *       · El archivo se guarda en UTF-8.
 *       · El separador de campos viene de Config.SEP.
 *       · El formato de fecha viene de Config.DATE_PATTERN.
 *       · Usa FileHelp.enFilExs() para crear fichero/directorio si no existe.
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01.Repository;


import bloque01.Classes.Magazine;
import bloque01.Config;
import static bloque01.Helpers.ConHelp.printf;
import bloque01.Helpers.FileHelp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Repositorio de datos para las notas (Magazine).
 *
 * <p>
 * Esta clase se encarga de leer y escribir el listado completo
 * de anotaciones en un fichero CSV.
 * </p>
 *
 * <p>Flujo típico:</p>
 * <ol>
 *   <li>Llamar a loadAll() para obtener la lista actual.</li>
 *   <li>Modificar la lista en memoria (add, edit, delete).</li>
 *   <li>Guardar los cambios con saveAll().</li>
 * </ol>
 */
public class NoteRepository {
        
    /** Lista interna opcionalmente utilizable si se requiere cachear en memoria. */
    ArrayList<Magazine> magaz = new ArrayList<>();
    
    /** Ruta física del CSV donde se guardan las notas. */
    private static final Path csvFile = Path.of("data", "magaz.csv");
    
    /** Separador de columnas usado en CSV. */
    private static final String SEP = Config.SEP;
    
    /** Formato de fecha/hora estándar del proyecto. */
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern(Config.DATE_PATTERN);
          
     /**
     * Carga todas las anotaciones desde el CSV.
     *
     * @return Lista de Magazine. Si el archivo no existe, devuelve lista vacía.
     */
    public static  List<Magazine>loadAll() throws IOException {
        List<Magazine>result = new ArrayList<>();
        if (!Files.exists(csvFile)) {
            return result;
        }
        
        try (BufferedReader br = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8)) {
            String line;
            int nLine = 0;
            
            while((line = br.readLine()) != null) {
                nLine++;
                if(line.isBlank()) continue;
                
                Optional<Magazine>maybe = parseLine(line);
                if (maybe.isPresent()) {
                    result.add(maybe.get());
                } else {
                    printf("Línea no válida: %s%n", nLine);
                }
            }            
        } catch (IOException ex) {
            printf("Error leyendo el archivo: %s%n", ex.getMessage());
        }
        return result;
    }
    
    
     /**
     * Intenta convertir una línea CSV en un objeto Magazine.
     *
     * Estructura esperada:
     *   id;title;author;createdAt;content
     *
     * @param line línea leída del CSV.
     * @return Optional con Magazine si el parseo fue correcto.
     */
    private static Optional<Magazine> parseLine(String line) {
        String[] parts = line.split(Pattern.quote(SEP), -1);
        if (parts.length != 5) return Optional.empty();
        
        try {
            int id = Integer.parseInt(parts[0].trim());
            String title = parts[1].trim();
            String author = parts[2].trim();
            String createdAt = parts[3].isBlank()
                    ? LocalDateTime.now().format(FMT)
                    : parts[3].trim();
            String content = parts[4].trim();
            
            return Optional.of(new Magazine(id, title, author,  createdAt, content));
        } catch (NumberFormatException ex) {
            printf("Error: ID no válido en línea: %s%n", line);
        } catch (Exception ex) {
            printf("Error al parsear el CSV -> %s%n", line);
        }
        return Optional.empty();
    }
    
     /**
     * Guarda TODAS las notas en el CSV, sobrescribiendo el archivo.
     *
     * @param magaz Lista completa de Magazine a persistir.
     */
    public void saveAll(List<Magazine> magaz) {
        try {
            FileHelp.enFilExs(csvFile);
            try (BufferedWriter bw = Files.newBufferedWriter(csvFile, StandardCharsets.UTF_8)) { 
                for (Magazine m : magaz) { bw.write(toCsv(m)); bw.newLine();}
            }
        } catch (IOException ex) {
            printf("Error escribiendo el CSV: %s%n", ex.getMessage());
        }
    }
    
     /**
     * Convierte una instancia de Magazine a una línea lista para CSV.
     *
     * @param m objeto Magazine.
     * @return línea CSV saneada.
     */
    private String toCsv(Magazine m) {
        return String.join(SEP, 
                String.valueOf(m.getId()),
                sanitize(m.getTitle()),
                sanitize(m.getAuthor()),
                sanitize(m.getCreatedAt()),
                sanitize(m.getContent())
        );
    }
    
     /**
     * Limpia los campos de texto para que no rompan el CSV.
     * Reemplaza el propio separador si aparece en el contenido.
     *
     * @param s texto original (puede ser null).
     * @return texto seguro para CSV.
     */
    private String sanitize(String s) {
        if (s == null) return "";
        return s.replace(SEP, " ");
                
    }    
    
    /**
     * Comprueba si existe ya un valor concreto dentro de la lista.
     *
     * Ejemplo de uso:
     *   checkEx(lista, "título buscado", Magazine::getTitle)
     *
     * @param magaz        Lista en la que buscar.
     * @param value        Valor a buscar (por ejemplo, "Mi título").
     * @param keyExtractor Función que extrae el campo a comparar.
     * @return true si encuentra coincidencia exacta (ignorando mayúsculas/minúsculas).
     */
    public boolean checkEx(
            List<Magazine> magaz,
            String value,
            Function<Magazine, String> keyExtractor) {
        
        String needle = normalize(value);
        
        for (int i = 0; i < magaz.size(); i++) {

            Magazine m = magaz.get(i);
            String current = normalize(keyExtractor.apply(m));
            
            if (current.equals(needle)) {
                return true;
            }
        }
        return false;
    }
    
      /**
     * Normaliza cadenas para comparación:
     * trim + toLowerCase(Locale.ROOT).
     *
     * @param s texto a normalizar.
     * @return versión limpia en minúsculas (o " " si s es null).
     */
    public static String normalize(String s) {
        return (s == null) ? " " : s.trim().toLowerCase(Locale.ROOT);
    }
    
     /**
     * Busca una nota por su ID.
     *
     * @param magaz lista de Magazine donde buscar.
     * @param id    identificador numérico.
     * @return el Magazine con ese ID o null si no existe.
     */
    public static Magazine findById (List<Magazine> magaz, int id) {
        for (Magazine m : magaz) {
            if(m.getId() == id) { return m; }
        }
        return null;
    }
    


}
