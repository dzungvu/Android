package vn.edu.topica.sendtoweb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Created by ZtOg on 4/16/2017.
 */

public class CalendarFragment extends Fragment{
    ListView lvDeadline;
    TextView txtNumberOfDealines;
    ArrayList<String>listDeadline;
    ArrayAdapter<String>adapterDeadline;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        lvDeadline = (ListView) view.findViewById(R.id.lvDeadline);
        txtNumberOfDealines = (TextView) view.findViewById(R.id.txtNumberOfDealine);
        listDeadline = new ArrayList<>();

        Bundle bundle=getArguments();
        String data = bundle.getString("DATA");

        Document doc = Jsoup.parse(data);
        String include_calendars = doc.select("div [class=day hasevent calendar_event_course calendar_event_course]").html();
        String include_calendars2 = doc.select("div [class=weekend day hasevent calendar_event_course calendar_event_course]").html();
        String[] listDate;
        include_calendars=include_calendars+include_calendars2;
        listDate = include_calendars.split("<a");
        //int num_of_deadline = (listDate.length);
        txtNumberOfDealines.setText("You have " + (listDate.length - 1) + " deadlines this month");
        if (include_calendars.length()>0){
            for (int i = 1; i < listDate.length; i++) {
                int start = listDate[i].indexOf("#event");
                int stop = listDate[i].indexOf("</a>");
                listDeadline.add(listDate[i].substring(start+14, stop).toString());
            }
            adapterDeadline = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listDeadline);
            lvDeadline.setAdapter(adapterDeadline);
        }
        else
            Toast.makeText(getActivity(), "can't", Toast.LENGTH_LONG).show();


        return view;

    }
}
