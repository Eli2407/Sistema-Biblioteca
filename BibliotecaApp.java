
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class BibliotecaApp {

    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        while (true) {
            JOptionPane.showMessageDialog(null, "Bienvenido a nuestro Sistema Integral de Gestión de Biblioteca");
            String opcion = JOptionPane.showInputDialog(
                    "Seleccione una opción:\n"
                    + "1. Iniciar Sesión\n"
                    + "2. Salir"
            );

            if (opcion == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada.");
                continue;
            }

            switch (opcion) {
                case "1":
                    iniciarSesion();
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Gracias por utilizar nuestro servicio de Sistema de Biblioteca");
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");

            }
        }
    }

    private static void iniciarSesion() {
        Usuario usuario = biblioteca.autenticarUsuario();
        if (usuario != null) {
            if (usuario.getTipoUsuario().equals("Bibliotecario")) {
                JOptionPane.showMessageDialog(null, "Bienvenido Bibliotecario");
                mostrarMenuBibliotecario();
            } else if (usuario.getTipoUsuario().equals("Lector")) {
                JOptionPane.showMessageDialog(null, "Bienvenido Lector");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Autenticación fallida.");
        }
    }

    private static void mostrarMenuBibliotecario() {
        while (true) {
            String opcion = JOptionPane.showInputDialog(
                    "Seleccione una opción:\n"
                    + "1. Gestionar Usuarios\n"
                    + "2. Gestionar Libros\n"
                    + "3. Gestinar Prestamos\n"
                    + "4. Generar Reportes\n"
                    + "5. Cerrar Sesion"
            );

            switch (opcion) {
                case "1":
                    gestionarUsuario();
                    break;

                case "2":
                    GestionLibros();
                    break;
                case "3":
                    GestionPrestamo();

            }
        }
    }

    public static void gestionarUsuario() {
        while (true) {
            String opcion = JOptionPane.showInputDialog(
                    "Seleccione una opción:\n"
                    + "1. RegistrarUsuario\n"
                    + "2. Modificar Usuario\n"
                    + "3. Eliminar Usuario\n"
                    + "4. Cerrar Sesion"
            );

            switch (opcion) {
                case "1":
                    registrarUsuario();
                    break;

                case "2":
                    biblioteca.modificarUsuario();
                    break;
                case "3":
                    biblioteca.eliminarUsuario();
                    break;
                case "4":
                    mostrarMenuBibliotecario();
            }

        }
    }

    private static void registrarUsuario() {
        String nombre = JOptionPane.showInputDialog("Ingrese nombre: ");
        String identificacion = JOptionPane.showInputDialog("Ingrese identificación: ");
        String tipoUsuario = JOptionPane.showInputDialog("Ingrese tipo de usuario (lector/bibliotecario): ");
        String correoElectronico = JOptionPane.showInputDialog("Ingrese correo electrónico: ");
        String contraseña = JOptionPane.showInputDialog("Ingrese contraseña: ");

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setIdentificacion(identificacion);
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setcontraseña(contraseña);

        biblioteca.registrarUsuario(usuario);
        System.out.println("Usuario registrado con éxito.");
    }

    //GESTION LIBRO:
    public static void GestionLibros() {
        while (true) {
            String opcion = JOptionPane.showInputDialog(
                    "Seleccione una opción:\n"
                    + "1. Agregar Libro\n"
                    + "2. Modificar Libro\n"
                    + "3. Eliminar Libro\n"
                    + "4. Cerrar Sesion"
            );

            switch (opcion) {
                case "1":
                    AgregarLibro();
                    break;
                case "2":
                    biblioteca.modificarLibro();
                    break;
                case "3":
                    biblioteca.eliminarLibro();
                case "4":
                    mostrarMenuBibliotecario();

            }

        }
    }

    public static void AgregarLibro() {
        String titulo = JOptionPane.showInputDialog("Ingrese el titulo de el libro a ingresar: ");
        String autor = JOptionPane.showInputDialog("Ingrese el autor del libro a ingresar: ");
        String ISBN = JOptionPane.showInputDialog("Ingrese el ISBN del libro: ");
        String genero = JOptionPane.showInputDialog("Ingrese el genero del libro: ");
        int numeroCopias = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero de copias "));
        boolean disponibilidad = Boolean.parseBoolean(JOptionPane.showInputDialog("Ingrese si se encuentra disponible (true/False)"));

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setISBN(ISBN);
        libro.setGenero(genero);
        libro.setNumeroCopias(numeroCopias);
        libro.setDisponibilidad(disponibilidad);

        biblioteca.registrarLibro(libro);
        JOptionPane.showMessageDialog(null, "Libro registrado exitosamente");
    }

    //GESTION LIBRO:
    public static void GestionPrestamo() {
        while (true) {
            String opcion = JOptionPane.showInputDialog(
                    "Seleccione una opción:\n"
                    + "1. registrarPrestamo\n"
                    + "2. devolver Prestamo\n"
                    + "3. Cerrar Sesion"
            );

            switch (opcion) {
                case "1":
                    biblioteca.realizarPrestamo();
                    break;
                case "2":
                    biblioteca.DevolverLibro();
                case "4":
                    mostrarMenuBibliotecario();

            }

        }
    }

}
