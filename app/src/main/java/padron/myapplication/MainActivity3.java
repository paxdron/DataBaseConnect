package padron.myapplication;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity3 extends Activity {
    EditText etNombre, etCorreo, etUsuario, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);
        etNombre= (EditText) findViewById(R.id.etName);
        etCorreo= (EditText) findViewById(R.id.etMail);
        etUsuario= (EditText) findViewById(R.id.etUsuario);
        etPassword= (EditText) findViewById(R.id.etPassword);
    }

    public void registrar(View v){
        Usuario usuario = new Usuario(  etUsuario.getText().toString(),
                                        etPassword.getText().toString(),
                                        etNombre.getText().toString(),
                                        etCorreo.getText().toString());
        registrarUsuario(usuario);

    }

    public void registrarUsuario(Usuario user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.registerDoInBackground(user, new GetUserCallback() {
            @Override
            public void done(Usuario returnedUser) {
                regresar();
            }
        });
    }

    public void regresar(){
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
