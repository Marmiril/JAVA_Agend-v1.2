/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      saveMagaz.java
 * Package:   bloque01.operations
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.0
 *
 * Description:
 *     Operación de guardado de una nueva nota (Magazine).
 *
 *     Flujo:
 *       - Carga todas las notas existentes desde CSV.
 *       - Calcula el siguiente ID disponible.
 *       - Pide título y autor con validación:
 *           · no vacío
 *           · no duplicado (título / autor)
 *           · posibilidad de cancelar con "0"
 *       - Crea el objeto Magazine con fecha actual.
 *       - Añade a la lista y persiste en disco.
 *
 *     Detalles:
 *       · Usa NoteRepository para la E/S con CSV.
 *       · Usa prompt() para entrada de usuario.
 *       · La fecha de creación se rellena automáticamente en Magazine.
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01.operations;

import bloque01.Classes.Magazine;
import static bloque01.Helpers.ConHelp.prompt;
import static bloque01.Helpers.ConHelp.printf;
import bloque01.Repository.NoteRepository;
import java.io.IOException;
import java.util.List;

/**
 * Clase responsable de crear y guardar una nueva nota
 * en el repositorio.
 *
 * <p>
 * Esta clase pregunta interactivamente al usuario por
 * los datos mínimos (título, autor) y luego guarda el
 * contenido recibido.
 * </p>
 */
public class saveMagaz {
    
    /** Repositorio encargado de leer/escribir el CSV. */
    private final NoteRepository repo;
    
    /** Constructor por defecto: crea su propio repositorio. */
    public saveMagaz() { this.repo = new NoteRepository(); }
    
     /**
     * Guarda un nuevo texto como Magazine.
     *
     * <p>Pasos:</p>
     * <ol>
     *   <li>Carga la lista actual desde CSV.</li>
     *   <li>Calcula el próximo ID (auto-incremental).</li>
     *   <li>Pide título y autor al usuario (se puede cancelar con "0").</li>
     *   <li>Valida que no haya duplicados por título/autor.</li>
     *   <li>Crea el objeto Magazine y lo persiste en disco.</li>
     * </ol>
     *
     * @param content Contenido de la nota (cuerpo del texto).
     * @throws java.io.IOException
     */ 
   public void saveText(String content) throws IOException {
        
        // 1. Cargar lista guardada..            
        List<Magazine>magaz = repo.loadAll();
       
        // 2. Calcular siguiente ID disponible.
        int nextId = magaz.isEmpty()
                ? 1
                 : magaz.stream().mapToInt(Magazine::getId).max().getAsInt() + 1;
        
       printf("============ GUARDADO ============%n");
       
       // 3. Solicitar título (único y obligatorio).
       String title;
       while (true) { title = prompt("Título: %n");
           if (title == null) return;
           if (!repo.checkEx(magaz, title, Magazine::getTitle)) break;
           printf("Ya existe una nota con tal título.%n"); }             
       
       // 4. Solicitar autor (único y obligatorio).
       String author;
       while (true) { author = prompt("Autor: %n");
           if (author == null) return;
           if (!repo.checkEx(magaz, author, Magazine::getAuthor)) break;
           printf("Ya existe un autor con dicho nombre:"); }
            
       // 5. Crear nueva nota con fecha actual.
       Magazine newMag = new Magazine(nextId, title, author, content);
       magaz.add(newMag);
       
       // 6. Guardar lista completa en CSV.
       repo.saveAll(magaz);
       
       printf("Contenido guardado correctamente.");
       
   
       
   }
}
