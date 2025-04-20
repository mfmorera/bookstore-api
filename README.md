# bookstore-api

Una API para gestionar una librería, permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los libros.

## Endpoints

### Obtener todos los libros
- **Descripción**: Obtiene una lista de libros con paginación.
- **Parámetros**:
  - `page` (opcional): Número de página.
  - `size` (opcional): Número de elementos por página.

    ### Crear un nuevo libro
    ### Obtener un libro por ID
- **Descripción**: Obtiene un libro por su ID.
- **Descripción**: Crea un nuevo libro.
- **Cuerpo de la solicitud**:
```json
{
  "title": "El gran libro",
  "author": "Autor Famoso"
}
PUT /books/{id}
 ### Eliminar un libro por ID
