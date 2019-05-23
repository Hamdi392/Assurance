package com.outsider.lanalaassurance.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.outsider.lanalaassurance.Adapter.ImpayeeAdapter;
import com.outsider.lanalaassurance.Impayee;
import com.outsider.lanalaassurance.R;

import java.util.ArrayList;

public class ImpayeeFragment extends Fragment {


    public ImpayeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_impayee, container, false);

        ArrayList<Impayee> impayeeArrayList = new ArrayList<>();
        impayeeArrayList.add(new Impayee("741258744554", "12/05/2019", "25/01/2018", "14/06/2018", "2500"));
        impayeeArrayList.add(new Impayee("741258744554", "12/05/2019", "25/01/2018", "14/06/2018", "2500"));
        impayeeArrayList.add(new Impayee("741258744554", "12/05/2019", "25/01/2018", "14/06/2018", "2500"));

        ListView listView = view.findViewById(R.id.listimpayee);

        ImpayeeAdapter impayeeAdapter = new ImpayeeAdapter(getContext(), impayeeArrayList);
        listView.setAdapter(impayeeAdapter);

        return view;
    }

}
