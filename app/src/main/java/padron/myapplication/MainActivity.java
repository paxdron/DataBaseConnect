package padron.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    EditText etUser, etPassword;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUser=(EditText) findViewById(R.id.etUser);
        etPassword=(EditText) findViewById(R.id.etPswd);
        userLocalStore = new UserLocalStore(this);
    }


    public void Login(View view)
    {
        Usuario user = new Usuario( etUser.getText().toString(),
                                    etPassword.getText().toString());
        authenticate(user);
    }

    public void registrarLayout(View v){
        startActivity(new Intent(this,MainActivity3.class));
    }

    private void authenticate(Usuario user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.loginDoInBackground(user, new GetUserCallback() {
            @Override
            public void done(Usuario returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();

                } else {

                    logUserIn(returnedUser);
                }

            }
        });
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setMessage(getString(R.string.error));
        dialogBuilder.setPositiveButton(getString(R.string.ok),null);
        dialogBuilder.show();
    }

    private void logUserIn(Usuario returnedUser){
        //TODO
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this,MainActivity2.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
