
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fichero {

    // Método para crear un archivo
    public void crearArchivo(String fileName, String contenido) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para leer el archivo y devolver el contenido como una lista de cadenas
    public List<String> leerArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile(); // Crea el archivo si no existe
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineas;
    }

// Método para escribir el contenido en un archivo
    public void escribirArchivo(String fileName, List<String> lineas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un archivo
    public void eliminarArchivo(String fileName) {
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("Archivo eliminado con éxito.");
        } else {
            System.out.println("Ocurrió un error al eliminar el archivo.");
        }
    }

    public void guardarPrestamosEnArchivo(List<Prestamo> prestamos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("prestamos.txt"))) {
            for (Prestamo prestamo : prestamos) {
                writer.write(prestamo.getLibro().getTitulo() + ","
                        + prestamo.getUsuario().getNombre() + ","
                        + prestamo.getFechaInicio() + ","
                        + prestamo.getFechaDevolucion() + ","
                        + prestamo.getEstado());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarLibroEnArchivo(List<Libro> catalogos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("catalogo.txt"))) {
            for (Libro catalogo : catalogos) {
                writer.write(catalogo.getTitulo() + ","
                        + catalogo.getAutor() + ","
                        + catalogo.getISBN() + ","
                        + catalogo.getGenero() + ","
                        + catalogo.getNumeroCopias() + ","
                        + catalogo.isDisponible());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public static void guardarArchivo(String nombreArchivo, String contenido) {
    File archivo = new File(nombreArchivo);
    try {
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(contenido);
            bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
