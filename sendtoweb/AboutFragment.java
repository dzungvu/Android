package vn.edu.topica.sendtoweb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by ZtOg on 4/12/2017.
 */

public class AboutFragment extends Fragment {
    TextView txtName;
    TextView txtInfo;
    String user_info_url;
    ProgressDialog progress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Getting information");
        progress.setMessage("Please wait");
        progress.setCanceledOnTouchOutside(false);



        Bundle bundle=getArguments();
        String data = bundle.getString("DATA");

        Document doc = Jsoup.parse(data);
        String inclued_name = doc.select("div [class=logininfo]").html();
        int firstPosition = inclued_name.indexOf("sơ\">");
        int secondPosition = inclued_name.indexOf("</a> (");
        String user_real_name = inclued_name.substring(firstPosition + 4, secondPosition);
        if (inclued_name.length()>0) {
            txtName.setText(user_real_name.toString());
        }

        user_info_url = inclued_name.substring(inclued_name.indexOf("user")-27, inclued_name.indexOf("\" title"));

        String all_info;
        try{
            URL url = new URL(user_info_url);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            Connection.Response userInfoForm = Jsoup.connect(url.toString())
                    .method(Connection.Method.GET).timeout(10000).execute();
            Document doc2 = userInfoForm.parse();
            all_info = doc2.select("div [class=descriptionbox]").html();

            //doc2 = Jsoup.connect(user_info_url.toString());


        }catch(Exception ex){
            Log.e("Error", ex.toString());
            all_info="error";
        }
        txtInfo.setText(all_info);

        return view;
    }




//    class getUserinfoTask extends AsyncTask<Void, Void, String>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            txtInfo.setText("");
//            progress.show();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            txtInfo.setText(s);
//            progress.dismiss();
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            String all_info;
//            try{
//                URL url = new URL(user_info_url);
//                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//                Connection.Response userInfoForm = Jsoup.connect(url.toString())
//                        .method(Connection.Method.GET).timeout(10000).execute();
//                Document doc = userInfoForm.parse();
//                all_info = doc.select("div [class=descriptionbox]").html();
//
//
//
//            }catch(Exception ex){
//                Log.e("Error", ex.toString());
//                all_info="";
//            }
//            return all_info.toString();
//        }
//    }



}
