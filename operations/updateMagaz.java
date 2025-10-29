/*
 * ============================================================
 * Project:   CURSO JAVA · Bloque 1 (Sintaxis / Tipos / Formato)
 * File:      updateMagaz.java
 * Package:   bloque01.operations
 * Author:    Ángel Plata Benítez
 * Created:   2025-10-29
 * Version:   1.0
 *
 * Description:
 *     Operación de consulta / edición de notas guardadas (Magazine).
 *
 *     Flujo general:
 *       - Cargar todas las notas desde el CSV.
 *       - Mostrar una tabla con ID, título, autor y fecha.
 *       - Pedir al usuario el ID de la nota que quiere modificar.
 *       - Permitir cambiar título, autor o contenido.
 *       - Confirmar antes de aplicar cada cambio.
 *       - Guardar todos los cambios en disco.
 *
 *     Detalles:
 *       · Usa NoteRepository para lectura/escritura.
 *       · Usa TabHelp.printHeader() para mostrar la tabla.
 *       · Usa TabHelp.confirm() para confirmar cada cambio.
 *       · Usa prompt() que permite cancelar con "0".
 *
 * License:
 *     This code is released under the MIT License.
 * ============================================================
 */
package bloque01.operations;

import bloque01.Classes.Magazine;
import static bloque01.Helpers.ConHelp.pause;
import static bloque01.Helpers.ConHelp.printf;
import static bloque01.Helpers.ConHelp.prompt;
import static bloque01.Helpers.TabHelp.confirm;
import static bloque01.Helpers.TabHelp.printHeader;
import bloque01.Repository.NoteRepository;
import static bloque01.Repository.NoteRepository.findById;

import java.io.IOException;
import java.util.List;

/**
 * Permite al usuario consultar una nota existente
 * e ir modificando campos concretos (título, autor, contenido).
 *
 * <p>
 * Este módulo trabaja en memoria sobre la lista cargada y,
 * al final del proceso de edición, guarda la lista completa
 * de vuelta al CSV.
 * </p>
 */
public class updateMagaz {
    
    /** Repositorio de acceso a datos. (No estático en diseño general.) */
    private final NoteRepository repo;
    
    /** Referencia estática temporal a la nota que se está editando (si se quiere reutilizar). */
    public static Magazine mag;
    
    /** Lista cargada desde CSV que se va modificando en memoria. */
    public updateMagaz() { this.repo = new NoteRepository(); } 
    
    /** Constructor por defecto, crea su propio repositorio. */
    private static List<Magazine> magaz;

    
     /**
     * Flujo principal de consulta/modificación.
     *
     * Pasos:
     *  1. Carga todas las notas.
     *  2. Muestra una tabla con printHeader().
     *  3. Pide al usuario un ID válido.
     *  4. Llama a modOp() para editar esa nota.
     *
     * El usuario puede cancelar introduciendo "0" cuando se le pide el ID.
     * @throws java.io.IOException
     */
    public static void run() throws IOException {        
     
      // 1. Cargar datos existentes.
      magaz = NoteRepository.loadAll();

       printf("===========CONSULTA============%n");
       
       // 2. Mostrar tabla inicial.
       printHeader(magaz);
       
       // 3. Preguntar qué ID se quiere modificar.
       while (true) {
            String raw = prompt("Indica el ID de la nota que quiere modificar:%n");                       
            if (raw == null) { return; }
            
            raw = raw.trim();
            
            // 3.1 Validar que es un número entero.
            int idNum;
            try { idNum = Integer.parseInt(raw); }
            catch (NumberFormatException ex ){ printf("Debe introducir un número entero.%n"); continue; }                        
            
            // 3.2 Buscar nota con ese ID.
            Magazine found = findById(magaz, idNum);
            if (found == null) { printf("No se han encontrado notas con ese ID: %s.%n", idNum); continue; }          
                      
           //  mag = found;
            modOp(found); 
            return; 
       }                       
    }
    
     /**
     * Menú de modificación de una nota concreta.
     *
     * El usuario puede elegir qué campo tocar:
     *   1 - Título
     *   2 - Autor
     *   3 - Contenido
     *
     * Para cada campo:
     *   - Se muestra el valor actual.
     *   - Se pide el nuevo valor (con cancelación "0").
     *   - Se pide confirmación antes de guardar.
     *
     * Al salir del bucle, se guardan todos los cambios en CSV.
     *
     * @param mag nota Magazine que se está editando
     */
        public static void modOp(Magazine mag) {
                       

            while (true) {
                printf("==========MODIFICACIÓN==========%n");
                printf("1 - Título.%n");
                printf("2 - Autor.%n");
                printf("3 - Contenido.%n");            
            
                //List<Magazine> result = new ArrayList<>();
                
                String opt = prompt("Seleccione el dato a modificar:%n ");
                if (opt == null) { break; }
                            
                switch (opt) {
                    case "1" -> {String newTit;
                                        String tit = mag.getTitle();
                                        printf(tit + "%n"); 
                                        newTit = prompt("Nuevo título: "); 
                                        if (newTit == null) return;
                                        if (confirm("¿Desea guardar el cambio?%n")) {mag.setTitle(newTit);
                                        printf("Contenido guardado%n");  pause(); } }
                    case "2" -> {String newAuth;
                                        String auth = (mag.getAuthor());
                                        printf("%s%n", auth);
                                        newAuth = prompt("Nuevo autor: "); 
                                        if (newAuth == null) return;
                                        if (confirm("¿Desea guardar el cambio?%n")) {mag.setAuthor(newAuth);
                                        printf("Contenido guardado%n");  pause(); } }
                    case "3" -> {String newCont;                                       
                                        String cont = mag.getContent();
                                        printf(cont + "%n");                                         
                                        newCont = prompt("Nuevo contenido: "); 
                                        if (newCont == null) return;
                                        if (confirm("¿Desea guardar el cambio?")) {mag.setContent(newCont); 
                                        printf("Contenido guardado%n");  pause(); } }        
                    default -> { printf("Opción no válida.%n"); continue; }           
                    }
                
          }
            // Al salir del bucle, guarda TODA la lista modificada.
             NoteRepository TmpRepo = new NoteRepository();
             TmpRepo.saveAll(magaz);
      }
}
