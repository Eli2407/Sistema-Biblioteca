
public class Usuario {

    private String nombre;
    private String identificacion;
    private String tipoUsuario;
    private String correoElectronico;
    private String Contraseña;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setcontraseña(String oontraseña) {
        this.Contraseña = oontraseña;
    }

    @Override
    public String toString() {
        return nombre + "," + identificacion + "," + tipoUsuario + "," + correoElectronico + "," +Contraseña;
        
    }

    
    
  

}
