package vn.edu.topica.sendtoweb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

import vn.edu.topica.model.CoursesInfo;

/**
 * Created by ZtOg on 4/17/2017.
 */

public class CoursesFragment extends Fragment {
    TextView txtNumberOfSubject;
    ListView lvSubjects;
    ArrayList<String>lstSubjects;
    ArrayAdapter<String>adapterSubjects;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.courses_fragment, container, false);
        txtNumberOfSubject = (TextView) view.findViewById(R.id.txtNumberOfSubjects);
        lvSubjects = (ListView) view.findViewById(R.id.lvSubjects);
        lstSubjects = new ArrayList<>();

        Bundle bundle=getArguments();
        String data = bundle.getString("DATA");
        Document doc = Jsoup.parse(data);

        String []split_list_subjects;
        String include_subjects1 = doc.select("div [class=coursebox clearfix odd]").html();
        String include_subjects2 = doc.select("div [class=coursebox clearfix even]").html();
        String include_subjects = include_subjects1+include_subjects2;
        split_list_subjects=include_subjects.split("<div class=\"info\">");

        txtNumberOfSubject.setText("You already learned " + (split_list_subjects.length-1) + " subjects");

        for (int i = 1 ; i < split_list_subjects.length; i++){
            String subject_name = split_list_subjects[i].substring(split_list_subjects[i].indexOf("course/view.")+25
            , split_list_subjects[i].indexOf("</a></h3> "));
            lstSubjects.add(subject_name);
        }
        adapterSubjects=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lstSubjects);
        lvSubjects.setAdapter(adapterSubjects);



        return view;
    }
}
