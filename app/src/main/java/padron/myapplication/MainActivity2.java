package padron.myapplication;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity2 extends Activity {

    TextView tvName, tvUser, tvMail;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        tvUser=(TextView) findViewById(R.id.tvUsuario);
        tvName=(TextView) findViewById(R.id.tvNombre);
        tvMail=(TextView) findViewById(R.id.tvMail);
        userLocalStore = new UserLocalStore(this);
        muestraInfo();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    private void muestraInfo(){
        Usuario user = userLocalStore.getLoggedInUser();
        tvName.setText(user.nombre);
        tvMail.setText(user.correo);
        tvUser.setText(user.usuario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
