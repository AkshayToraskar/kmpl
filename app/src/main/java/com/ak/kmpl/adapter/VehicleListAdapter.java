package com.ak.kmpl.adapter;

/**
 * Created by WDIPL44 on 8/4/2016.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ak.kmpl.R;
import com.ak.kmpl.activity.AddVehicleActivity;
import com.ak.kmpl.app.PrefManager;
import com.ak.kmpl.inteface.AddVehicleData;
import com.ak.kmpl.realm_model.Vehicle;
import com.ak.kmpl.realm_model.VehicleRecords;


import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by WDIPL44 on 3/9/2016.
 */
public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.MyViewHolder> {

    private List<Vehicle> mDataset;
    private final static int FADE_DURATION = 1000; // in milliseconds
    private Context mContext;
    public ImageView ivEdit;

    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;

    public Vehicle vehicle;
    AddVehicleData addVehicleData;
    PrefManager prfManager;


    public class MyViewHolder extends RecyclerView.ViewHolder// implements AdapterView.OnItemSelectedListener
    {
        public TextView tvName;
        ImageView ivEdit, ivDelete, ivType;
        RadioButton rbDefault;
        //  public  MaterialSpinner spinner;
        public Spinner spinner;

        private String[] state = {"1000", "5000", "10000", "20000", "50000"};


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvVehName);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            ivType = (ImageView) itemView.findViewById(R.id.ivType);
            rbDefault = (RadioButton) itemView.findViewById(R.id.rbDefault);

            spinner = (Spinner) itemView.findViewById(R.id.spinnerService);

            ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(itemView.getContext(),
                    android.R.layout.simple_spinner_item, state);
            adapter_state
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter_state);




            /*int pos = prfManager.getSpinnerPosition();
            Log.d("","position spinner " + pos);
                spinner.setSelection(pos);
*/

            ///   String spinnerPosition = (String) spinner.getSelectedItem();
            //  prfManager.setSpinnerPosition(Integer.parseInt(spinnerPosition));
            // spinner.setSelection(prfManager.getSpinnerPosition());

            //spinner.setSelection(position);

            // String spinnerPosition = (String) spinner.getSelectedItem();
            //  prfManager.setSpinnerPosition(Integer.parseInt(spinnerPosition));


            //vehicle.setServiceReminder(prfManager.getSpinnerPosition());


            /////  spinner.setOnItemSelectedListener(this);

            /// spinner = (MaterialSpinner) itemView.findViewById(R.id.spinner);
            /// spinner.setItems("1000", "2500", "5000", "8500", "10000", "15000", "20000", "40000", "50000");
         /*   spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                }
            });*/
        }

       /* @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            spinner.setSelection(position);
            String selState = (String) spinner.getSelectedItem();

            int spinnerPosition = spinner.getSelectedItemPosition();
            prfManager.setSpinnerPosition(spinnerPosition);
            spinner.setSelection( prfManager.getSpinnerPosition());

            prfManager.setServiceInterval(Integer.parseInt(selState));

            Snackbar.make(view, "Clicked " + selState, Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }*/
    }


    public VehicleListAdapter(List<Vehicle> mDataset, AddVehicleData addVehicleData) {
        this.mDataset = mDataset;
        this.addVehicleData = addVehicleData;
    }

    @Override
    public VehicleListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle_list, parent, false);


        prfManager = new PrefManager(mContext);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final VehicleListAdapter.MyViewHolder holder, final int position) {


        if (mDataset.size() == 1) {
            holder.ivDelete.setVisibility(View.GONE);
        }


        holder.tvName.setText(" " + mDataset.get(position).getName());

        if (mDataset.get(position).getType() == 0) {
            holder.ivType.setImageResource(R.drawable.car_icon);
        } else {
            holder.ivType.setImageResource(R.drawable.bike_icon);
        }

        holder.spinner.setSelection(mDataset.get(position).getSpinnerPos());


        holder.rbDefault.setTag(new Integer(position));

        int defaultPos = prfManager.getDefaultVehicle();

        //for default check in first item
        if (position == defaultPos) {
            holder.rbDefault.setChecked(true);
            lastChecked = holder.rbDefault;
            lastCheckedPos = defaultPos;
        }

        holder.rbDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RadioButton cb = (RadioButton) v;
                int clickedPos = ((Integer) cb.getTag()).intValue();

                if (lastCheckedPos != clickedPos) {

                    if (cb.isChecked()) {
                        if (lastChecked != null) {
                            lastChecked.setChecked(false);

                            //mDataset.get(lastCheckedPos).setSelected(false);
                        }

                        lastChecked = cb;
                        lastCheckedPos = clickedPos;

                        prfManager.setDefaultVehicle(clickedPos);

                    } else
                        lastChecked = null;

                    //mDataset.get(clickedPos).setSelected(cb.isChecked);
                }
            }
        });


        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(mContext,"Edit Click",Toast.LENGTH_SHORT).show();
                addVehicleData.getVehicleData(mDataset.get(position));

                //AddVehicleActivity.llAddData.animate();

                setAnimation(AddVehicleActivity.llAddData);
            }
        });
        //spinner service reminder

        holder.spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //String selState = (String) holder.spinner.getSelectedItem();

                //Vehicle vehc=mDataset.get(position);
                //vehc.setServiceReminder(holder.spinner.getSelectedItemPosition());
                //vehc.setServiceReminder(Integer.parseInt(selState) + (int)vehc.getLastReading());
                // vehc.setSpinnerPos(holder.spinner.getSelectedItemPosition());
                //vehc.setSpinnerPos(Integer.parseInt(selState));
                //vehc.save();


                //changes
                //prfManager.setServiceInterval((Integer) holder.spinner.getItemAtPosition(pos));

                //  String item = String.valueOf(parent.getSelectedItem());


                // prfManager.setServiceInterval(Integer.parseInt(selState));
                //   Log.d("","spinner position " + item);

                //vehicle = new Vehicle();
                //vehicle.setServiceReminder(Integer.parseInt(item));
                //prfManager.setSpinnerPosition(Integer.parseInt(item));

                // Snackbar.make(view, "Reminder set after every " + selState + " Kms", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

     /*   holder.spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
//
//                vehicle = new Vehicle();
//                vehicle.setServiceReminder(Integer.parseInt(item));
//                vehicle.save();

                prfManager.setServiceInterval(Integer.parseInt(item));

                //addVehicleData.getVehicleData(mDataset.get(position));

                //prfManager = new PrefManager(mContext);
               // prfManager.setServiceIntervalKMS(Integer.parseInt(item));
                Log.d("new Reminder","New Reminder" +  prfManager.getServiceIntervalKMS());

            }
        });*/


        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"Delete Click",Toast.LENGTH_SHORT).show();


                AlertDialog.Builder alert = new AlertDialog.Builder(
                        mContext);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure to delete vehicle it will delete all its records !");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        /*String vid = String.valueOf(mDataset.get(position).getId());
                        List<VehicleRecords> vehiclesRecords = Select.from(VehicleRecords.class)
                                .orderBy("id Desc")
                                .where("V_Id = ?", new String[]{vid}).list();

                        for (int i = 0; i < vehiclesRecords.size(); i++) {
                            vehiclesRecords.get(i).delete();
                        }*/

                        if (position == prfManager.getDefaultVehicle()) {
                            prfManager.setDefaultVehicle(0);
                        }

                        //mDataset.get(position).delete();
                        mDataset.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();


            }
        });


        setAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void setAnimation(View view) {
        /*AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);*/

        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.REVERSE, 0.5f, Animation.REVERSE, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}