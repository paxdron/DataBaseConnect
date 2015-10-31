package padron.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TOSHIBA PC on 30/10/2015.
 */

public class ServerRequest {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT=1000*15;
    public static final String SERVER_ADDRESS = "http://10.0.2.2/login/";
    private Context context;
    public ServerRequest(Context context){
        this.context =context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Por favor, espere");

    }

    public  void loginDoInBackground(Usuario user, GetUserCallback callback){
        progressDialog.show();
        new AsyncTaskLogin(user,context,callback).execute();
    }
    public  void registerDoInBackground(Usuario user, GetUserCallback callback){
        progressDialog.show();
        new AsyncTaskRegistro(user,callback).execute();
    }
//ctivity padron.myapplication.MainActivity has leaked window com.android.internal.policy.impl.PhoneWindow$DecorView{3ff2786 V.E..... R......D 0,0-684,322} that was originally added here

    public class AsyncTaskLogin extends AsyncTask<Void, Void, Usuario>{
        Usuario user;
        Context context;
        GetUserCallback userCallback;
        public AsyncTaskLogin(Usuario user,Context context, GetUserCallback userCallback){
            this.user=user;
            this.context=context;
            this.userCallback=userCallback;
        }

        @Override
        protected Usuario doInBackground(Void... params) {
            String login_name = user.usuario;
            String login_pass = user.contra;
            Usuario returnedUser=null;
            try {

                URL url = new URL(context.getString(R.string.URLLogin));
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode(context.getString(R.string.postUsername), "UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode(context.getString(R.string.postPassword),"UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                System.out.println(data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                System.out.println("Respuesta: "+response);
                JSONObject jObject = new JSONObject(response);
                if(jObject.length()==0){
                    returnedUser=null;
                }else{
                    returnedUser= new Usuario(  login_name,
                            login_pass,
                            jObject.getString("nombre"),
                            jObject.getString("mail")
                    );
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(Usuario returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }

    public class AsyncTaskRegistro extends AsyncTask<Void, Void, Void>{
        Usuario user;
        GetUserCallback userCallback;

        public AsyncTaskRegistro(Usuario user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            /*dataToSend.put(context.getString(R.string.name),user.nombre);
            dataToSend.put(context.getString(R.string.password),user.contra);
            dataToSend.put(context.getString(R.string.username),user.usuario);
            dataToSend.put(context.getString(R.string.mail),user.correo);*/
            String name = user.nombre;
            String user_name = user.usuario;
            String user_pass = user.contra;
            String mail = user.correo;
            try {
                URL url = new URL(context.getString(R.string.URLRegister));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode(context.getString(R.string.name), "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode(context.getString(R.string.mail), "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8") + "&" +
                        URLEncoder.encode(context.getString(R.string.username), "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode(context.getString(R.string.password), "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                System.out.println("info enviada "+data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }
}
