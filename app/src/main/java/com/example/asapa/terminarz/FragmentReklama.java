package com.example.asapa.terminarz;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentReklama extends Fragment {


    public FragmentReklama() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View retView=inflater.inflate(R.layout.fragment_reklama, null);
        Button btnSend=(Button) retView.findViewById(R.id.button_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frBank=new FragmentBank();
                FragmentTransaction frTrans= getFragmentManager().beginTransaction();
                frTrans.replace(R.id.frameLayoutReklama,frBank);
                frTrans.commit();
            }
        });
        return retView;
    }

}
