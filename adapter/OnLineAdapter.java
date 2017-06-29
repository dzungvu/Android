package vn.edu.topica.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.edu.topica.sendtoweb.R;

/**
 * Created by ZtOg on 4/19/2017.
 */

public class OnLineAdapter extends ArrayAdapter {
    Activity context;
    @LayoutRes int resource;
    @NonNull List objects;
    public OnLineAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource=resource;
        this.objects=objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);
        ImageView imgOnlineAvatar = (ImageView) view.findViewById(R.id.imgOnlineAvatar);
        TextView txtOnlineName = (TextView) view.findViewById(R.id.txtOnlineName);
        
        return view;
    }

}
