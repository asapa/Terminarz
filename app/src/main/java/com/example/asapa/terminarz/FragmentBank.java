package com.example.asapa.terminarz;


import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBank extends Fragment {


    public FragmentBank() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View retView=inflater.inflate(R.layout.fragment__bank, container, false);
        Button btnCancel=(Button) retView.findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frRek=new FragmentReklama();
                FragmentTransaction  frTrans= getFragmentManager().beginTransaction();
                frTrans.replace(R.id.frameLayoutReklama,frRek);
                frTrans.commit();
            }

        });
        return retView;
    }

}
