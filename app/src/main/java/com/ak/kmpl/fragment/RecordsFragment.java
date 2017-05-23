package com.ak.kmpl.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.ak.kmpl.R;

import com.ak.kmpl.activity.ShowRecorsActivity;
import com.ak.kmpl.adapter.VehicleRecordAdapter;

import com.ak.kmpl.app.MyRecyclerScroll;
import com.ak.kmpl.inteface.FilterData;
import com.ak.kmpl.realm_model.Vehicle;
import com.ak.kmpl.realm_model.VehicleRecords;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;
import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsFragment extends Fragment implements FilterData {

    View view;
    public Context context;
    public RecyclerView rvVehicleRecord;
    private RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView.Adapter mAdapter;
    // ImageView ivNoRecord;
    public static ArrayList<VehicleRecords> vehicleRecordses;
    LinearLayout llNoRecord;
    public static FilterData filterDataRecordFragment = null;

    public static long vId;
    Realm realm;

    public RecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // LayoutInflater mInflater = getActivity().getLayoutInflater();
        //View view = mInflater.inflate(R.layout.fragment_records, null);
        view = inflater.inflate(R.layout.fragment_records, container, false);
        realm = Realm.getDefaultInstance();
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont((Activity) getContext(), "CircularAir-Light.otf", true);

        rvVehicleRecord = (RecyclerView) view.findViewById(R.id.rvVehicleRecord);
        //  ivNoRecord = (ImageView) view.findViewById(R.id.ivNoRecord);

        vehicleRecordses = new ArrayList<>();
        llNoRecord = (LinearLayout) view.findViewById(R.id.llNoRecord);

        // filterDataRecordFragment = this;

        mLayoutManager = new LinearLayoutManager(getActivity());
        rvVehicleRecord.setLayoutManager(mLayoutManager);
        mAdapter = new VehicleRecordAdapter(vehicleRecordses);
        rvVehicleRecord.setAdapter(mAdapter);
        rvVehicleRecord.setNestedScrollingEnabled(true);
        //vehicleRecordses.addAll(vehiclesRecords);


        rvVehicleRecord.setOnScrollListener(new MyRecyclerScroll() {
            @Override
            public void show() {
                ShowRecorsActivity.fabAddNewRecord.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                ShowRecorsActivity.fabAddNewRecord.animate().translationY(ShowRecorsActivity.fabAddNewRecord.getHeight() + 50).setInterpolator(new AccelerateInterpolator(2)).start();
            }
        });


        return view;
    }


    public void notifyDataRecords(long vid) {
        //List<VehicleRecords> vehiclesRecords = VehicleRecords.find(VehicleRecords.class, "V_Id = ?", String.valueOf(vehiclesNameList.get(spnVehName.getSelectedItemPosition()).getId()));

        //List<VehicleRecords> vehiclesRecords = VehicleRecords.find(VehicleRecords.class, "V_Id = ?", String.valueOf(vehiclesNameList.get(spnVehName.getSelectedItemPosition()).getId()),"Id DESC",null,null);

        Vehicle vehicle = realm.where(Vehicle.class).equalTo("id", vid).findFirst();

        RealmList<VehicleRecords> vehiclesRecords = vehicle.getVehicleRecords();
        vehiclesRecords.sort("date", Sort.DESCENDING);




       /* List<VehicleRecords> vehiclesRecords = Select.from(VehicleRecords.class)
                .orderBy("id Desc")
                .where("V_Id = ?", new String[]{vid}).list();*/

        if (vehiclesRecords.size() > 0) {
            llNoRecord.setVisibility(View.GONE);
        } else {
            llNoRecord.setVisibility(View.VISIBLE);
        }


        RecordsFragment.vehicleRecordses.clear();
        RecordsFragment.vehicleRecordses.addAll(vehiclesRecords);

        vId = vid;

        RecordsFragment.mAdapter.notifyDataSetChanged();
    }


    @Override
    public void updateData(String vid) {
        Log.v("Records Fragment", " " + vid);
        notifyDataRecords(Long.parseLong(vid));

        if (filterDataRecordFragment != null) {
            filterDataRecordFragment.updateData(vid);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();

        ((ShowRecorsActivity) context).filterData = this;
    }


}
