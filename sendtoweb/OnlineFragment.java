package vn.edu.topica.sendtoweb;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import vn.edu.topica.adapter.OnLineAdapter;
import vn.edu.topica.model.OnlineInfo;

/**
 * Created by ZtOg on 4/19/2017.
 */

public class OnlineFragment extends Fragment {
    ListView lvOnline;
    ArrayList<String>arrayListOnline;
    ArrayAdapter<String> onLineAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.online_fragment, container, false);
        lvOnline = (ListView) view.findViewById(R.id.lvOnline);
        arrayListOnline=new ArrayList<>();
        Bundle bundle=getArguments();
        String data = bundle.getString("DATA");

        Document doc = Jsoup.parse(data);
        String include_URL_avatar = doc.select("div [class=user]").html();
        String[] listURLAvatar;
        listURLAvatar = include_URL_avatar.split("</a");
        for (int i = 0; i < listURLAvatar.length-1;i++) {
            String subURL = listURLAvatar[i];
            int start = subURL.indexOf("src=")+5;
            int end = subURL.indexOf("\" alt");
            String temp1 = ((subURL.substring(start, end)));
            String temp2 = (subURL.substring(subURL.indexOf("\"16\">")+5, subURL.length()));
            arrayListOnline.add(temp2);
        }
        onLineAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayListOnline);
        lvOnline.setAdapter(onLineAdapter);
        return view;
    }



}
