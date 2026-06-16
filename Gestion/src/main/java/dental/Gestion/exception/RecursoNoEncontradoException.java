package dental.Gestion.exception;

// Creo mi propia excepción personalizada que extiende RuntimeException
// Esto me permite lanzarla en cualquier parte del código sin obligar a capturarla con try-catch
// La uso cada vez que busco un egreso por ID y no existe en la base de datos
public class RecursoNoEncontradoException extends RuntimeException {

    // Recibo un mensaje descriptivo (ej: "Egreso no encontrado con id: 5")
    // y se lo paso al constructor de RuntimeException para que quede guardado en la excepción
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
