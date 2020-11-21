package com.example.mvoting.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvoting.PublicActivity;
import com.example.mvoting.R;
import com.example.mvoting.model.CandidateModel;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class CandidateAdapter extends BaseAdapter {

    private Context context;
    private static ArrayList<CandidateModel> aList;
    private static ArrayList<Integer> aCount;
    boolean[] checkBoxState;

    LayoutInflater mInflater;
    public CandidateAdapter(Context context, ArrayList<CandidateModel> aList, ArrayList<Integer> aCount){
        this.context = context;
        this.aList  = aList;
        this.aCount = aCount;
        checkBoxState = new boolean [aList.size()];
    }

    public static ArrayList<Integer> getaCount() {
        return aCount;
    }

    public static void setaCount(ArrayList<Integer> aCount) {
        CandidateAdapter.aCount = aCount;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return aList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final ViewHolder holder;
        convertView=null;
        if (convertView == null) {
            holder = new ViewHolder();
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.candidate_adapter, null);

            //Set Candidate name
            holder.name = (TextView) convertView
                    .findViewById(R.id.candidate_name);
            holder.name.setTag(position);
            holder.name.setText(aList.get(position).getName());

            //Set Candidate Party name
            holder.party = (TextView) convertView
                    .findViewById(R.id.party);
            holder.party.setTag(position);
            holder.party.setText(aList.get(position).getParty());

            holder.vote = convertView.findViewById(R.id.chkVote);
            //holder.vote.setChecked(aList.get(position).isVote());
            //holder.vote.setTag(position);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.vote.setChecked(checkBoxState[position]);
        holder.vote.setOnClickListener(new View.OnClickListener() {

            int i = 0;
            public void onClick(View v) {

                if(((CheckBox)v).isChecked()) {
                    checkBoxState[position] = true;
                    if(checkBoxState[position] == true) {
                        i++;
                        aCount.add(i);
                    }
                    PublicActivity x = new PublicActivity();
                    x.getCheckedVote(aCount.size()+"");

                    aList.add(new CandidateModel(aList.get(position).getName(), aList.get(position).getParty(), 1, 0));
                    Log.e("Check candidate", aList.get(position).getName());
                    Log.e("Check", ((CheckBox) v).isChecked()+"");
                }else {
                    checkBoxState[position] = false;
                    aList.add(new CandidateModel(aList.get(position).getName(), aList.get(position).getParty(), 1, 1));
                    Log.e("position", position+"");
                }

            }
        });
        return convertView;
    }

}

class ViewHolder {
    CheckBox vote;
    TextView name, party;
}
