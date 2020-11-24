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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvoting.AdminActivity;
import com.example.mvoting.R;
import com.example.mvoting.model.CandidateModel;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class CandidateAdminAdapter extends BaseAdapter {

    private Context context;
    private static ArrayList<CandidateModel> aList;
    private Button btnDel;

    LayoutInflater mInflater;
    public CandidateAdminAdapter(Context context, ArrayList<CandidateModel> aList){
        this.context = context;
        this.aList  = aList;
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
        final ViewHolderAdmin holder;
        convertView=null;
        if (convertView == null) {
            holder = new ViewHolderAdmin();
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.candidate_admin_adapter, null);

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

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderAdmin) convertView.getTag();
        }

        btnDel = convertView.findViewById(R.id.btnDelete);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CreateInvoiceLinesActivity.delete(position);
                AdminActivity x = new AdminActivity();
                Log.e("D", position+"");
                x.delete(context, position);

            }
        });

        return convertView;
    }

}

class ViewHolderAdmin {
    TextView name, party;
}
