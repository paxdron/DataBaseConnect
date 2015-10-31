package padron.myapplication;

/**
 * Created by TOSHIBA PC on 30/10/2015.
 */
public class Usuario {
    public String usuario, contra, nombre, correo;

    public Usuario(String user, String psswd, String name, String mail){
        usuario=user;
        contra=psswd;
        nombre=name;
        correo=mail;
    }

    public Usuario(String user, String psswd){
        usuario=user;
        contra=psswd;
        nombre=null;
        correo=null;
    }


}
