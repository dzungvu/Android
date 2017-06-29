package vn.edu.topica.sendtoweb;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    EditText txtUsername, txtPassword;
    CheckBox chkRemember;
    Button btnLogin;
    TextView txtForgot;
    ProgressDialog progressDialog;
    String loginInfo = "Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProcess();
            }
        });
    }

    private void loginProcess() {
        if (haveNetworkConnection()){
            GetDataTask task = new GetDataTask();
            task.execute();
        }
        //// TODO: 4/5/2017 Phone isn't connect to the internet, Open wifi setting or not?

        else{
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.activity_dialog);
            dialog.setTitle("Notification");

            Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
            Button btnNo = (Button) dialog.findViewById(R.id.btnNo);

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "No internet", Toast.LENGTH_LONG).show();
                }
            });
            dialog.show();
        }
    }

    //// TODO: 4/5/2017 Check network exists

    private boolean haveNetworkConnection(){
        boolean haveWifiConnection = false;
        boolean haveMobileConnection = false;

        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo){
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                haveWifiConnection = ni.isConnected();
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                haveMobileConnection = ni.isConnected();
        }
        return haveMobileConnection||haveWifiConnection;
    }


//    StringBuilder data;
    String mWelcome;

    class GetDataTask extends AsyncTask<Void, Void, String>{
        String a = txtUsername.getText().toString();
        String b = txtPassword.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... params) {
            try{
                URL url = new URL("https://courses.uit.edu.vn/login/index.php");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                Connection.Response loginForm = (Connection.Response) Jsoup.connect(url.toString())
                        .method(Connection.Method.GET).timeout(10000).execute();




                Document doc = loginForm.parse();
                String username = doc.select("input[name=username]").attr("value");
                String password = doc.select("input[name=password]").attr("value");
                username = a;
                password = b;
                doc = Jsoup.connect(url.toString())
                        .data("username", username)
                        .data("password", password)
                        .cookies(loginForm.cookies())
                        .post();
                mWelcome = doc.select("div [id=site-news-forum]").html();
                if(mWelcome.length()>0){
                    mWelcome = "Login Successful";
                    Intent intent = new Intent(MainActivity.this, LoginedInActivity.class);
                    intent.putExtra("SOURCE", doc.toString());
                    startActivity(intent);
                }
                else{
                    mWelcome = "Login Failed";
                }



            }catch (Exception ex){
                Log.e("ERROR", ex.toString());
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
            }
            return mWelcome.toString();
        }
    }

    private void addControls() {
        txtForgot = (TextView) findViewById(R.id.txtForgot);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        chkRemember = (CheckBox) findViewById(R.id.chkRemember);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Working");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences(loginInfo, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USERNAME", txtUsername.getText().toString());
        editor.putString("PASSWORD", txtPassword.getText().toString());
        editor.putBoolean("CHECKED", chkRemember.isChecked());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(loginInfo, MODE_PRIVATE);
        String username = preferences.getString("USERNAME","");
        String password = preferences.getString("PASSWORD","");
        boolean save = preferences.getBoolean("CHECKED", false);
        if (save){
            txtUsername.setText(username);
            txtPassword.setText(password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }
}
