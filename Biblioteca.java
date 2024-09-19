
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class Biblioteca {

    public static final String FILE_NAME = "Usuarios.txt";
    private static final String CATALOG_FILE_NAME = "catalogo.txt";
    private static final String LOG_FILE_NAME = "catalogoLog.txt";
    private static final String PRESTAMOS_FILE_NAME = "prestamos.txt";
    private static final String REPORTES_FILE_NAME = "reportes.txt";
    private static final String CSV_REPORT_FILE_NAME = "reportes.csv";
    private static final String BACKUP_DIR = "respaldo/";
    private static final String AUDITORIA_FILE_NAME = "auditoria.txt";
    private List<Libro> libros;
    private List<Usuario> usuarios;
    private List<Prestamo> prestamos;

    private Fichero fichero;

    public Biblioteca() {
        fichero = new Fichero();
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.prestamos = new ArrayList<>();
    }

    //GESTION DE USUARIOS:
    public void registrarUsuario(Usuario usuario) {
        List<String> lineas = fichero.leerArchivo(FILE_NAME);
        lineas.add(usuario.toString());
        fichero.escribirArchivo(FILE_NAME, lineas);
    }

    public Usuario autenticarUsuario() {
        Fichero ficheros = new Fichero();
        String nombre = JOptionPane.showInputDialog("Ingrese su nombre de Usuario");
        String contraseña = JOptionPane.showInputDialog("Ingrese su contraseña");

        if (nombre == null || contraseña == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            return null;
        }
        List<String> lineas = fichero.leerArchivo(FILE_NAME);

        for (String linea : lineas) {
            Usuario usuario = convertirLineaUsuario(linea);
            System.out.println("Usuario ID: " + usuario.getNombre() + "Contraseña: " + usuario.getContraseña());

            if (usuario.getNombre().equals(nombre) && usuario.getContraseña().equals(contraseña)) {
                JOptionPane.showMessageDialog(null, "Ingreso Concedido");
                return usuario;
            }

        }
        JOptionPane.showMessageDialog(null, "nombre o contraseña incorrectas");
        return null;

    }

    public static void modificarUsuario() {
        String identificacion = JOptionPane.showInputDialog("Ingrese identificación del usuario a modificar:");

        List<Usuario> usuarios = cargarUsuarios();
        Usuario usuario = buscarUsuarioEnLista(usuarios, identificacion);

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            return;
        }

        String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo nombre (dejar en blanco para no modificar):");
        String nuevoTipoUsuario = JOptionPane.showInputDialog("Ingrese nuevo tipo de usuario (dejar en blanco para no modificar):");
        String nuevoCorreoElectronico = JOptionPane.showInputDialog("Ingrese nuevo correo electrónico (dejar en blanco para no modificar):");
        String nuevaContraseña = JOptionPane.showInputDialog("Ingrese nueva contraseña (dejar en blanco para no modificar):");

        if (nuevoNombre != null && !nuevoNombre.isBlank()) {
            usuario.setNombre(nuevoNombre);
        }
        if (nuevoTipoUsuario != null && !nuevoTipoUsuario.isBlank()) {
            usuario.setTipoUsuario(nuevoTipoUsuario);
        }
        if (nuevoCorreoElectronico != null && !nuevoCorreoElectronico.isBlank()) {
            usuario.setCorreoElectronico(nuevoCorreoElectronico);
        }
        if (nuevaContraseña != null && !nuevaContraseña.isBlank()) {
            usuario.setcontraseña(nuevaContraseña);
        }

        if (guardarUsuarios(usuarios)) {
            JOptionPane.showMessageDialog(null, "Usuario modificado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al modificar el usuario.");
        }
    }

    private static boolean guardarUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Usuario usuario : usuarios) {
                writer.write(usuario.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void eliminarUsuario() {
        String identificacion = JOptionPane.showInputDialog("Ingrese identificación del usuario a eliminar:");
        List<Usuario> usuarios = cargarUsuarios();

        Usuario usuarioAEliminar = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getIdentificacion().equals(identificacion)) {
                usuarioAEliminar = usuario;
                break;
            }
        }

        if (usuarioAEliminar == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            return;
        }

        usuarios.remove(usuarioAEliminar);

        if (guardarUsuariosActualizar(usuarios)) {
            JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar el usuario.");
        }
    }

    private static boolean guardarUsuariosActualizar(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Usuario usuario : usuarios) {
                writer.write(usuario.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Usuario buscarUsuario(String identificacion) {
        List<Usuario> usuarios = cargarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getIdentificacion().equals(identificacion)) {
                return usuario;
            }
        }
        return null;
    }

    private static Usuario buscarUsuarioEnLista(List<Usuario> usuarios, String identificacion) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdentificacion().equals(identificacion)) {
                return usuario;
            }
        }
        return null;
    }

    private static List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line != null && !line.trim().isEmpty()) {
                    try {
                        Usuario usuario = convertirLineaUsuario(line);
                        usuarios.add(usuario);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Advertencia: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private static Usuario convertirLineaUsuario(String linea) {
        String[] parts = linea.split(",");

        Usuario usuario = new Usuario();
        usuario.setNombre(parts[0]);
        usuario.setIdentificacion(parts[1]);
        usuario.setTipoUsuario(parts[2]);
        usuario.setCorreoElectronico(parts[3]);
        usuario.setcontraseña(parts[4]);
        return usuario;
    }

    public void eliminarUsuario(String identificacion) {
        List<String> lineas = fichero.leerArchivo(FILE_NAME);
        List<String> nuevasLineas = new ArrayList<>();
        for (String linea : lineas) {
            Usuario usuario = convertirLineaUsuario(linea);
            if (!usuario.getIdentificacion().equals(identificacion)) {
                nuevasLineas.add(linea);
            }
        }
        fichero.escribirArchivo(FILE_NAME, nuevasLineas);
    }

    // GESTIÓN DE LIBROS:
    public void registrarLibro(Libro libro) {
        List<String> lineas = fichero.leerArchivo(CATALOG_FILE_NAME);
        lineas.add(libro.toString());
        fichero.escribirArchivo(CATALOG_FILE_NAME, lineas);
    }

    public static void modificarLibro() {
        String isbn = JOptionPane.showInputDialog("Ingrese el ISBN del libro a modificar:");

        List<Libro> libros = cargarLibros();
        Libro libro = buscarLibroEnLista(libros, isbn);

        if (libro == null) {
            JOptionPane.showMessageDialog(null, "Libro no encontrado.");
            return;
        }

        String nuevoTitulo = JOptionPane.showInputDialog("Ingrese nuevo título (dejar en blanco para no modificar):");
        String nuevoAutor = JOptionPane.showInputDialog("Ingrese nuevo autor (dejar en blanco para no modificar):");
        String nuevoGenero = JOptionPane.showInputDialog("Ingrese nuevo género (dejar en blanco para no modificar):");
        String nuevoNumeroCopiasStr = JOptionPane.showInputDialog("Ingrese nuevo número de copias (dejar en blanco para no modificar):");

        if (nuevoTitulo != null && !nuevoTitulo.isBlank()) {
            libro.setTitulo(nuevoTitulo);
        }
        if (nuevoAutor != null && !nuevoAutor.isBlank()) {
            libro.setAutor(nuevoAutor);
        }
        if (nuevoGenero != null && !nuevoGenero.isBlank()) {
            libro.setGenero(nuevoGenero);
        }
        if (nuevoNumeroCopiasStr != null && !nuevoNumeroCopiasStr.isBlank()) {
            int nuevoNumeroCopias = Integer.parseInt(nuevoNumeroCopiasStr);
            libro.setNumeroCopias(nuevoNumeroCopias);
        }

        if (guardarLibros(libros)) {
            JOptionPane.showMessageDialog(null, "Libro modificado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al modificar el libro.");
        }
    }

    private static boolean guardarLibros(List<Libro> libros) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CATALOG_FILE_NAME))) {
            for (Libro libro : libros) {
                writer.write(libro.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void eliminarLibro() {
        String isbn = JOptionPane.showInputDialog("Ingrese ISBN del libro a eliminar:");
        List<Libro> libros = cargarLibros();

        Libro libroAEliminar = null;
        for (Libro libro : libros) {
            if (libro.getISBN().equals(isbn)) {
                libroAEliminar = libro;
                break;
            }
        }

        if (libroAEliminar == null) {
            JOptionPane.showMessageDialog(null, "Libro no encontrado.");
            return;
        }

        libros.remove(libroAEliminar);

        if (guardarLibros(libros)) {
            JOptionPane.showMessageDialog(null, "Libro eliminado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar el libro.");
        }
    }

    private static Libro buscarLibroEnLista(List<Libro> libros, String isbn) {
        for (Libro libro : libros) {
            if (libro.getISBN().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    private static List<Libro> cargarLibros() {
        List<Libro> libros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CATALOG_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Libro libro = convertirLineaLibro(line);
                    libros.add(libro);
                } catch (IllegalArgumentException e) {
                    System.err.println("Advertencia: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return libros;
    }

    private static Libro convertirLineaLibro(String linea) {
        String[] parts = linea.split(",");

        Libro libro = new Libro();
        libro.setTitulo(parts[0]);
        libro.setAutor(parts[1]);
        libro.setISBN(parts[2]);
        libro.setGenero(parts[3]);
        libro.setNumeroCopias(Integer.parseInt(parts[4]));
        libro.setDisponibilidad(true);
        return libro;
    }

    //Gestion Prestamo:
    public void realizarPrestamo() {
        Fichero fichero = new Fichero();
        String nombreP = JOptionPane.showInputDialog("Ingrese el nombre del lector:");
        String isbn = JOptionPane.showInputDialog("Ingrese el ISBN del libro a prestar:");

        System.out.println("Nombre del lector: " + nombreP);
        System.out.println("ISBN del libro: " + isbn);

        List<String> lineasU = fichero.leerArchivo(FILE_NAME);
        List<String> lineasL = fichero.leerArchivo(CATALOG_FILE_NAME);
        boolean usuarioEncontrado = false;
        boolean libroEncontrado = false;
        Usuario u = null;
        Libro libroSolicitado = null;

        for (String linea : lineasU) {
            Usuario usuario = convertirLineaUsuario(linea);
            if (usuario.getNombre().equalsIgnoreCase(nombreP)) {
                usuarioEncontrado = true;
                u = usuario;
                break;
            }
        }

        for (String linea : lineasL) {
            Libro libro = convertirLineaLibro(linea);
            if (libro.getISBN().equals(isbn) && libro.isDisponible()) {
                libroEncontrado = true;
                libroSolicitado = libro;
                break;
            }
        }

        List<Libro> librosActualizados = new ArrayList<>();
        for (String linea : lineasL) {
            Libro libro = convertirLineaLibro(linea);
            if (libro.getISBN().equals(isbn)) {
                if (libro.isDisponible()) {
                    libroEncontrado = true;
                    libroSolicitado = libro;
                    libroSolicitado.setNumeroCopias(libroSolicitado.getNumeroCopias() - 1);
                } else {
                    JOptionPane.showMessageDialog(null, "No hay libros Disponibles");
                }
            }
            librosActualizados.add(libro);
        }

        if (usuarioEncontrado && libroEncontrado) {
            LocalDate fechaInicio = LocalDate.now();
            LocalDate fechaDevolucion = fechaInicio.plusWeeks(2);
            String estado = "Prestado";

            Prestamo prestamo = new Prestamo(libroSolicitado, u, fechaInicio, fechaDevolucion, estado);
            prestamos.add(prestamo);

            fichero.guardarLibroEnArchivo(librosActualizados);

            JOptionPane.showMessageDialog(null, "Préstamo realizado exitosamente.");

            if (LocalDate.now().isAfter(fechaDevolucion)) {
                prestamo.setEstado("atrasado");
                JOptionPane.showMessageDialog(null, "El préstamo está atrasado.");
            }

            fichero.guardarArchivo(PRESTAMOS_FILE_NAME, prestamo.toString());

        } else {
            if (!libroEncontrado) {
                JOptionPane.showMessageDialog(null, "No se encontró un libro con el ISBN proporcionado.");
            }
            if (!usuarioEncontrado) {
                JOptionPane.showMessageDialog(null, "No se encontró un usuario con el nombre proporcionado.");
            }
        }

    }

    public Prestamo convertirLineaPrestamo(String linea) {
        String[] partes = linea.split(",");

        String isbn = partes[0].trim();
        String nombreUsuario = partes[1].trim();
        LocalDate fechaInicio = LocalDate.parse(partes[2].trim());
        LocalDate fechaDevolucion = LocalDate.parse(partes[3].trim());
        String estado = partes[4].trim();

        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario == null) {
            System.out.println("No se pudo asociar el usuario con el nombre: " + nombreUsuario);
        }
        Libro libro = buscarLibroPorISBN(isbn);

        return new Prestamo(libro, usuario, fechaInicio, fechaDevolucion, estado);
    }

    public void DevolverLibro() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del usuario que solicitó el préstamo:");
        String ISBN = JOptionPane.showInputDialog("Ingrese el ISBN del libro:");

        List<String> lineasPrestamos = fichero.leerArchivo(PRESTAMOS_FILE_NAME);
        List<String> nuevosPrestamos = new ArrayList<>();
        boolean prestamoEncontrado = false;

        for (String linea : lineasPrestamos) {
            Prestamo prestamo = convertirLineaPrestamo(linea);
            Usuario usuario = prestamo != null ? prestamo.getUsuario() : null;

            if (prestamo != null) {
                if (usuario != null) {
                    String nombreUsuario = usuario.getNombre();

                    if (nombreUsuario.equalsIgnoreCase(nombre) && prestamo.getLibro().getTitulo().equalsIgnoreCase(ISBN)) {
                        prestamoEncontrado = true;
                        JOptionPane.showMessageDialog(null, "El libro ha sido devuelto exitosamente.");
                        continue;
                    }
                } else {
                    System.out.println("El préstamo no tiene un usuario asociado.");
                }
                nuevosPrestamos.add(linea);
            } else {
                System.out.println("Préstamo no válido: " + linea);
            }
        }

        if (prestamoEncontrado) {
            fichero.escribirArchivo(PRESTAMOS_FILE_NAME, nuevosPrestamos);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un préstamo para el usuario y libro proporcionados.");
        }
    }

    private Libro buscarLibroPorISBN(String isbn) {
        List<Libro> libros = cargarLibros();
        for (Libro libro : libros) {
            if (libro.getISBN().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    private void guardarLibro(Libro libro) {
        List<Libro> libros = cargarLibros();
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getISBN().equals(libro.getISBN())) {
                libros.set(i, libro);
                break;
            }
        }
        guardarLibros(libros);
    }

}
