package com.bluelead.probchat.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bluelead.probchat.Models.Type;

import java.util.ArrayList;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public class TypesSpinnerAdapter extends ArrayAdapter<Type> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<Type> mTypesArrayList;

    public TypesSpinnerAdapter(Context context, int textViewResourceId,
                       ArrayList<Type> typesArrayList) {
        super(context, textViewResourceId, typesArrayList);
        this.context = context;
        this.mTypesArrayList = typesArrayList;
    }

    public int getCount(){
        return mTypesArrayList.size();
    }

    public Type getItem(int position){
        return mTypesArrayList.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(mTypesArrayList.get(position).getCaption());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // here is when the "chooser" is popped up
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(mTypesArrayList.get(position).getCaption());

        return label;
    }
}
