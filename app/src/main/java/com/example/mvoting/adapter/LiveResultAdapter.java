package com.example.mvoting.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvoting.AdminActivity;
import com.example.mvoting.R;
import com.example.mvoting.model.CandidateModel;
import com.example.mvoting.model.VotingModel;

import java.util.ArrayList;

public class LiveResultAdapter extends BaseAdapter {

    private Context context;
    private static ArrayList<VotingModel> vList;
    private Button btnDel;

    LayoutInflater mInflater;
    public LiveResultAdapter(Context context, ArrayList<VotingModel> vList){
        this.context = context;
        this.vList  = vList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return vList.size();
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
        final ViewHolderResult holder;
        convertView=null;
        if (convertView == null) {
            holder = new ViewHolderResult();
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.candidate_result_adapter, null);

            //Set Candidate name
            holder.name = (TextView) convertView
                    .findViewById(R.id.candidate_name);
            holder.name.setTag(position);
            holder.name.setText(vList.get(position).getName());

            //Set Candidate Party name
            holder.party = (TextView) convertView
                    .findViewById(R.id.party);
            holder.party.setTag(position);
            holder.party.setText(vList.get(position).getParty());

            holder.vote = (TextView) convertView
                    .findViewById(R.id.textView21);
            holder.vote.setTag(position);
            holder.vote.setText(vList.get(position).getVote()+"");

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderResult) convertView.getTag();
        }

        return convertView;
    }

}

class ViewHolderResult {
    TextView name, party, vote;
}
