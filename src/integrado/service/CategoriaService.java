package integrado.service;

import integrado.entities.Categoria;
import integrado.entities.Producto;
import integrado.exception.EntidadNoEncontradaException;
import integrado.exception.ValidacionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();

    public List<Categoria> listar() {
        return categorias.stream()
                .filter(c -> !c.isEliminado())
                .toList();
    }

    public Categoria crear(String nombre, String descripcion){

            if ( nombre == null || nombre.isBlank()){
                throw new ValidacionException("El nombre no puede estar vacio.");
            }

            boolean existe = categorias.stream()
                    .anyMatch(c -> !c.isEliminado() && c.getNombre().equalsIgnoreCase(nombre));

            if (existe){
                throw new ValidacionException("Ya existe una categoria con ese nombre.");
            }
            Categoria nueva = new Categoria(nombre,descripcion);
            categorias.add(nueva);
            return nueva;
    }

    private Categoria buscarPorId(Long id){
        Optional<Categoria> opt = categorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (opt.isEmpty() || opt.get().isEliminado()){
            throw new EntidadNoEncontradaException("La categoria no existe o esta eliminada.");
        }
        return opt.get();
    }

    public Categoria editar(Long id, String nuevoNombre, String nuevaDescripcion){
        Categoria cat = buscarPorId(id);
        if ( nuevoNombre != null && nuevoNombre.isBlank()){
            boolean existe = categorias.stream()
                    .anyMatch(c -> !c.isEliminado()
                    && !c.getId().equals(id)
                    && c.getNombre().equalsIgnoreCase(nuevoNombre));
            if (existe){
                throw new ValidacionException("Ya existe otra categoria con ese nombre.");
            }
            cat.setNombre(nuevoNombre);
        }
        if (nuevaDescripcion != null){
            cat.setDescripcion(nuevaDescripcion);
        }
        return cat;
    }


    public void eliminar(Long id){
        Categoria cat = buscarPorId(id);
        if (!cat.getProductos().isEmpty()){
            throw new ValidacionException("No se puede eliminar la categoria porque tiene productos asociados");
        }
        cat.setEliminado(true);
    }

    public void asociarProducto(Categoria categoria, Producto producto){
        categoria.getProductos().add(producto);
    }
}
