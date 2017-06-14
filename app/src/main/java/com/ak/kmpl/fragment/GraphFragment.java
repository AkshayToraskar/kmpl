package com.ak.kmpl.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ak.kmpl.R;
import com.ak.kmpl.inteface.FilterData;
import com.ak.kmpl.realm_model.Vehicle;
import com.ak.kmpl.realm_model.VehicleRecords;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment implements FilterData {

    View view;
    //BarSet barSet;
    //BarChartView barChartView;
    public Context context;
    private String[] mLabels;// = {"A", "B", "C", "D", "A", "B", "C", "D", "A", "B", "C", "D"};
    private float[] mValues;// = {17.5f, 8.5f, 2.5f, 10f, 6.5f, 8.5f, 2.5f, 10f, 6.5f, 8.5f, 2.5f, 10f};
    int[] order;
    List<VehicleRecords> vehiclesRecordList;
    public static boolean FIRSTRUN = false;

    protected BarChart mChart;
    Realm realm;
    ArrayList<BarEntry> yVals1;


    public GraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_graph, container, false);
        realm = Realm.getDefaultInstance();
        mChart = (BarChart) view.findViewById(R.id.chart1);


        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        //mChart.setDescription();
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setFitBars(false);

        yVals1 = new ArrayList<BarEntry>();


        //barChartView = (BarChartView) view.findViewById(R.id.barchart);
        vehiclesRecordList = new ArrayList<>();
        //show();

        Log.v("Graph Fragment", " " + RecordsFragment.vId);


        return view;
    }


    private void setData(int count, float range) {

        float start = 0f;

        mChart.getXAxis().setAxisMinValue(start);
        mChart.getXAxis().setAxisMaxValue(start + count + 2);



        /*for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(i + 1f, val));
        }*/

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Avg");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            mChart.setData(data);
            mChart.notifyDataSetChanged();
        }
    }






    /*public void showChart() {


        //if (FIRSTRUN == false) {
        // Data
        barSet = new BarSet(mLabels, mValues);
        barSet.setColor(Color.parseColor("#fc2a53"));
        barChartView.addData(barSet);

        barChartView.setBarSpacing(Tools.fromDpToPx(40));
        barChartView.setRoundCorners(Tools.fromDpToPx(2));
        barChartView.setBarBackgroundColor(Color.parseColor("#592932"));

        // Chart
        barChartView.setXAxis(false)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"));

        // = {1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3};

        barChartView.show(new Animation()
                .setOverlap(.7f, order));

        FIRSTRUN = true;
        //} else {


        //barSet.updateValues(mValues);

        // FIRSTRUN=false;
        // showChart();


        //barChartView.notifyDataUpdate();
        //}

        //.setEndAction(chartOneAction));
    }*/

    public void getDataFromDB(final String vid) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                if (vehiclesRecordList.size() > 0) {
                    //    vehiclesRecordList.clear();
                    // barChartView.dismiss(new Animation()
                    //        .setOverlap(.7f, order));

                }

                yVals1.clear();

                Vehicle vehicle = realm.where(Vehicle.class).equalTo("id", Long.parseLong(vid)).findFirst();

                vehiclesRecordList = vehicle.getVehicleRecords();


               // RealmBarDataSet<VehicleRecords> dataSet = new RealmBarDataSet<VehicleRecords>(vehiclesRecordList, "xValue", "yValue");

                mLabels = new String[vehiclesRecordList.size()];
                mValues = new float[vehiclesRecordList.size()];
                order = new int[vehiclesRecordList.size()];


                for (int i = 0; i < vehiclesRecordList.size() - 1; i++) {
                    mLabels[i] = String.valueOf(vehiclesRecordList.get(i).getAverage());
                    mValues[i] = vehiclesRecordList.get(i).getAverage();
                    //order[i] = i;

                    yVals1.add(new BarEntry(i + 1f, vehiclesRecordList.get(i + 1).getAverage()));

                }

                setData(vehiclesRecordList.size() - 1, 20);


                // showChart();


            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();
        RecordsFragment.filterDataRecordFragment = this;
    }


    @Override
    public void updateData(String vid) {
        Log.v("Graph Fragment", "asdf" + vid);

        getDataFromDB(vid);
    }
}
