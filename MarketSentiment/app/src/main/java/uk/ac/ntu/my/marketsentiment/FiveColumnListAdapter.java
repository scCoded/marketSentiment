package uk.ac.ntu.my.marketsentiment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FiveColumnListAdapter extends ArrayAdapter<User> {
    private LayoutInflater mInflater;
    private ArrayList<User>users;
    private int mViewResourceId;

    public FiveColumnListAdapter(Context context,int textViewResourceId, ArrayList<User>users){
        super(context,textViewResourceId, users);
        this.users = users;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(mViewResourceId, null);

        User user = users.get(position);

        if (user != null) {
            TextView userName = (TextView) convertView.findViewById(R.id.textViewUsername);
            TextView password = (TextView) convertView.findViewById(R.id.textViewPassword);
            TextView type = (TextView) convertView.findViewById(R.id.textViewType);
            TextView testTaken = (TextView) convertView.findViewById(R.id.textViewTestTaken);
            TextView score = (TextView) convertView.findViewById(R.id.textViewScore);

            if (userName != null) {
                userName.setText(user.getUsername());
            }
            if (password != null) {
                password.setText((user.getPassword()));
            }
            if (type != null) {
                type.setText((user.getType()));
            }
            if (testTaken != null) {
                testTaken.setText((String.valueOf(user.getTestTaken())));
            }
            if (score != null) {
                score.setText((String.valueOf(user.getScore())));
            }


        }
        return convertView;

    }

}
