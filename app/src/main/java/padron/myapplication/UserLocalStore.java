package padron.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TOSHIBA PC on 31/10/2015.
 */

public class UserLocalStore {
    SharedPreferences userLocalDatase;
    Context context;
    public UserLocalStore(Context context) {
        this.context = context;
        userLocalDatase = context.getSharedPreferences(context.getString(R.string.SP_NAME),0);
    }
    public void storeUserData(Usuario user){
        SharedPreferences.Editor spEditor = userLocalDatase.edit();
        spEditor.putString(context.getString(R.string.username), user.usuario);
        spEditor.putString(context.getString(R.string.password), user.contra);
        spEditor.putString(context.getString(R.string.name), user.nombre);
        spEditor.putString(context.getString(R.string.mail), user.correo);
        spEditor.commit();
    }

    public  Usuario getLoggedInUser(){
        String name = userLocalDatase.getString(context.getString(R.string.name), "");
        String mail = userLocalDatase.getString(context.getString(R.string.mail),"");
        String user = userLocalDatase.getString(context.getString(R.string.username),"");
        String password = userLocalDatase.getString(context.getString(R.string.password),"");
        Usuario usuarioAlmacenado= new Usuario(user,password,name,mail);
        return usuarioAlmacenado;
    }

    public void setUserLoggedIn(boolean loogedIn){
        SharedPreferences.Editor spEditor = userLocalDatase.edit();
        spEditor.putBoolean(context.getString(R.string.loggedin),loogedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        return userLocalDatase.getBoolean(context.getString(R.string.loggedin),false);
    }
    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
