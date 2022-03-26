package com.cst2335.li000750;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class DetailsFragment extends Fragment {
    int position = 0;
    //boolean isTablet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                position = getArguments().getInt("position", 0);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, parent, false);
        TextView textMessage=(TextView) view.findViewById(R.id.textView);
        TextView messageID=(TextView)view.findViewById(R.id.textView2);
        CheckBox checkBox=(CheckBox) view.findViewById(R.id.checkBox);
        Button hide=(Button) view.findViewById(R.id.button3);
        Bundle bundle=getArguments();
        String message=bundle.getString("Text");
        boolean isSend=bundle.getBoolean("IsSend");
        long textMessageID=bundle.getLong("id");

        textMessage.setText(message);
        messageID.setText(Long.toString(textMessageID));

        if (isSend){checkBox.setChecked(true);}
        else {checkBox.setChecked(false);}

        hide.setOnClickListener(e->{
            if (ChatRoomActivity.isTablet) {
             //   getActivity().onBackPressed();
                //ChatRoomActivity.fragmentManager.beginTransaction().remove(this).commit();
                ((ChatRoomActivity)getActivity()).clearFragment();
            } else {
                getActivity().finish();

            }
        });
        // Inflate the xml file for the fragment
        //return inflater.inflate(R.layout.fragment_details, parent, false);
        return view;
    }

    }

