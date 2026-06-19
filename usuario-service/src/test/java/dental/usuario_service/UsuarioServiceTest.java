package dental.usuario_service;

import dental.usuario_service.exception.RecursoNoEncontradoException;
import dental.usuario_service.model.Usuario;
import dental.usuario_service.repository.UsuarioRepository;
import dental.usuario_service.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deberiaListarUsuarios() {
        Usuario usuario = new Usuario(1, "Debora", "Bravo", "debora@dental.cl", "ADMIN", true);

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.listarUsuarios();

        assertEquals(1, resultado.size());
        assertEquals("Debora", resultado.get(0).getNombre());
        verify(usuarioRepository).findAll();
    }

    @Test
    void deberiaBuscarUsuarioPorId() {
        Usuario usuario = new Usuario(1, "Debora", "Bravo", "debora@dental.cl", "ADMIN", true);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1);

        assertEquals(1, resultado.getIdUsuario());
        assertEquals("ADMIN", resultado.getRol());
        verify(usuarioRepository).findById(1);
    }

    @Test
    void deberiaBuscarUsuarioPorEmail() {
        Usuario usuario = new Usuario(1, "Debora", "Bravo", "debora@dental.cl", "ADMIN", true);

        when(usuarioRepository.findByEmail("debora@dental.cl")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorEmail("debora@dental.cl");

        assertEquals("debora@dental.cl", resultado.getEmail());
        verify(usuarioRepository).findByEmail("debora@dental.cl");
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            usuarioService.buscarPorId(99);
        });

        verify(usuarioRepository).findById(99);
    }

    @Test
    void deberiaBuscarUsuariosPorRol() {
        Usuario usuario = new Usuario(1, "Diego", "Rojas", "diego@dental.cl", "BODEGUERO", true);

        when(usuarioRepository.findByRol("BODEGUERO")).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.buscarPorRol("BODEGUERO");

        assertEquals(1, resultado.size());
        assertEquals("BODEGUERO", resultado.get(0).getRol());
        verify(usuarioRepository).findByRol("BODEGUERO");
    }

    @Test
    void deberiaListarUsuariosActivos() {
        Usuario usuario = new Usuario(1, "Angela", "Hernandez", "angela@dental.cl", "SOLICITANTE", true);

        when(usuarioRepository.findByActivo(true)).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.listarActivos();

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getActivo());
        verify(usuarioRepository).findByActivo(true);
    }

    @Test
    void deberiaGuardarUsuario() {
        Usuario usuario = new Usuario(null, "Nuevo", "Usuario", "nuevo@dental.cl", "ADMIN", true);
        Usuario guardado = new Usuario(1, "Nuevo", "Usuario", "nuevo@dental.cl", "ADMIN", true);

        when(usuarioRepository.save(usuario)).thenReturn(guardado);

        Usuario resultado = usuarioService.guardarUsuario(usuario);

        assertEquals(1, resultado.getIdUsuario());
        assertEquals("nuevo@dental.cl", resultado.getEmail());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void deberiaActualizarUsuario() {
        Usuario existente = new Usuario(1, "Nombre", "Antiguo", "viejo@dental.cl", "SOLICITANTE", true);
        Usuario actualizado = new Usuario(1, "Nombre", "Nuevo", "nuevo@dental.cl", "ADMIN", false);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(existente)).thenReturn(actualizado);

        Usuario resultado = usuarioService.actualizarUsuario(1, actualizado);

        assertEquals("Nuevo", resultado.getApellido());
        assertEquals("ADMIN", resultado.getRol());
        assertFalse(resultado.getActivo());

        verify(usuarioRepository).findById(1);
        verify(usuarioRepository).save(existente);
    }

    @Test
    void deberiaEliminarUsuario() {
        Usuario usuario = new Usuario(1, "Debora", "Bravo", "debora@dental.cl", "ADMIN", true);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        usuarioService.eliminarUsuario(1);

        verify(usuarioRepository).findById(1);
        verify(usuarioRepository).delete(usuario);
    }
}