package com.cst2335.li000750;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class DetailsFragment extends Fragment {

    boolean isTablet = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the xml file for the fragment
        View view= inflater.inflate(R.layout.fragment_details, parent, false);
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
            if (isTablet) {
                ChatRoomActivity chat = (ChatRoomActivity) getActivity();

                chat.getSupportFragmentManager()
                        .beginTransaction()
                        .remove(getParentFragment())
                        .commit();
            } else {
                getActivity().finish();

            }
        });
    return view;
    }

}