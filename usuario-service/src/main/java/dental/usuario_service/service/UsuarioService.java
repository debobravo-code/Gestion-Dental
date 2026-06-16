package dental.usuario_service.service;

import dental.usuario_service.exception.RecursoNoEncontradoException;
import dental.usuario_service.model.Usuario;
import dental.usuario_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con email: " + email));
    }

    public List<Usuario> buscarPorRol(String rol) {
        List<Usuario> usuarios = usuarioRepository.findByRol(rol);
        if (usuarios.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen usuarios con rol: " + rol);
        }
        return usuarios;
    }

    public List<Usuario> listarActivos() {
        return usuarioRepository.findByActivo(true);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el usuario: " + e.getMessage());
        }
    }

    public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado) {
        try {
            Usuario usuario = buscarPorId(id);
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setActivo(usuarioActualizado.getActivo());
            return usuarioRepository.save(usuario);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    public void eliminarUsuario(Integer id) {
        try {
            Usuario usuario = buscarPorId(id);
            usuarioRepository.delete(usuario);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
