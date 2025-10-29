/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      Magazine.java
 * Package:   bloque01.Classes
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.0
 * 
 * Description:
 *     Clase de modelo para representar una anotación o artículo.
 *     - Contiene información sobre título, autor, contenido y fecha.
 *     - Permite convertir los datos a formato CSV.
 *     - Usa la configuración definida en {@link bloque01.Config}.
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01.Classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import bloque01.Config;

/**
 * Clase de modelo que representa una publicación o anotación
 * dentro del Bloque 1.
 *
 * <p>Flujo general:</p>
 * <ol>
 *   <li>Puede instanciarse desde datos leídos en CSV.</li>
 *   <li>O crearse como nuevo registro generando fecha actual automáticamente.</li>
 *   <li>Proporciona métodos getter/setter para manipular los datos.</li>
 *   <li>Permite exportar su contenido en formato CSV.</li>
 * </ol>
 */
public class Magazine {   
   
    // Atributos.
    private final int id;
    private  String title;
    private  String author;
    private final String createdAt;
    private  String content;     
    
    // Constructores.
    
     /**
     * Constructor usado al reconstruir un objeto desde CSV.
     * 
     * @param id identificador único.
     * @param title título del artículo.
     * @param author autor de la publicación.
     * @param createdAt fecha de creación en formato String.
     * @param content contenido textual del artículo.
     */
    public Magazine(int id, String title, String author, String createdAt, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
        this.content = content;
    }
    
    /**
     * Constructor para crear una nueva publicación desde cero.
     * Genera automáticamente la fecha de creación actual.
     *
     * @param id identificador único.
     * @param title título del artículo.
     * @param author autor de la publicación.
     * @param content contenido textual del artículo.
     */
        public Magazine(int id, String title, String author, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(Config.DATE_PATTERN));
    }
    
    
    // Getters de acceso a campos privados.
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() {return author; }
    public String getCreatedAt() { return createdAt; }
    public String getContent() { return content; }
    
    // Setters de modificación.
    public void setTitle(String title) {this.title = title; }
    public void setAuthor(String author) {this.author = author; }
    public void setContent(String content) {this.content = content; }
    
    // Transformación a línea CSV.
     /**
     * Convierte el objeto a una línea CSV usando el separador
     * definido en {@link bloque01.Config#SEP}.
     *
     * @return línea CSV con los datos del objeto.
     */
    public String toCsv() {
        return String.join(
                Config.SEP,
                String.valueOf(id),
                title,
                author,
                createdAt,
                content); 
    }
}