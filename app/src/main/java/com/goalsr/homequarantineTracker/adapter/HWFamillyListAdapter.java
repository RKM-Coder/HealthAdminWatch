package com.goalsr.homequarantineTracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientFamilyDetailsItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Murugeshwaran M on 31-03-2020
 */
public class HWFamillyListAdapter extends RecyclerView.Adapter<HWFamillyListAdapter.ListViewHolder> {

    private ArrayList<PatientFamilyDetailsItem> listString;
    private Context context;
    private CheckedListener listener;
    List<String> famillyrelation = new ArrayList<>();
    public HWFamillyListAdapter(Context context, ArrayList<PatientFamilyDetailsItem> listString){
        this.context = context;
        this.listString = listString;
        String genderarray[] = context.getResources().getStringArray(R.array.relation);

        famillyrelation = new ArrayList<>(Arrays.asList(genderarray));
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.list_item_familly, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        holder.tvViewNAme.setText(listString.get(position).getName());
        if (listString.get(position).getRelationShipCode()==0){
            holder.tvRelation.setText("No relation available");
        }else {
            holder.tvRelation.setText(famillyrelation.get(listString.get(position).getRelationShipCode()));
        }




        holder.ll_main_familly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemCheckedFamilly(position,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return 4;
        if (listString != null) {
            return listString.size();
        } else {
            return 0;
        }
    }

    public void setListener(CheckedListener listener) {
        this.listener = listener;
    }

   public class ListViewHolder extends RecyclerView.ViewHolder {


        TextView tvViewNAme,tvRelation;
        LinearLayout ll_main_familly;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvViewNAme = itemView.findViewById(R.id.txtname);
            tvRelation = itemView.findViewById(R.id.txtrelation);
            ll_main_familly = itemView.findViewById(R.id.ll_main_familly);


        }
    }

    public interface CheckedListener{
        void onItemCheckedFamilly(int position, PatientFamilyDetailsItem item);
    }

    public void setValue(ArrayList<PatientFamilyDetailsItem> list){
        this.listString.clear();
        this.listString.addAll(list);
        notifyDataSetChanged();
    }
}
