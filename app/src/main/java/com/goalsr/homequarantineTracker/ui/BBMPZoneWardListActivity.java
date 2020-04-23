package com.goalsr.homequarantineTracker.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.adapter.BBMPWardListAdapter;
import com.goalsr.homequarantineTracker.adapter.BBMPZoneListAdapter;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.resposemodel.ModelBBMPWard;
import com.goalsr.homequarantineTracker.resposemodel.ModelBBMPZone;
import com.goalsr.homequarantineTracker.view.edittext.CustomEditText;
import com.goalsr.homequarantineTracker.view.edittext.DrawableClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.goalsr.homequarantineTracker.ui.AddnewPatientActivity.RES_BBMPWARD;
import static com.goalsr.homequarantineTracker.ui.AddnewPatientActivity.RES_BBMPZONE;

//import com.amitshekhar.DebugDB;

public class BBMPZoneWardListActivity extends BaseActivity implements BBMPZoneListAdapter.OnClickMainView, BBMPWardListAdapter.OnClickMainView {

    @BindView(R.id.tv_header_fac)
    TextView tvHeaderFac;
    @BindView(R.id.iv_back_fac)
    ImageButton ivBackFac;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.toolbar_fac)
    Toolbar toolbarFac;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.et_searchview)
    CustomEditText etSearchview;
    @BindView(R.id.img_calender)
    ImageView imgCalender;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.rv_view_famillly)
    RecyclerView rvViewFamillly;
    private ArrayList<ModelBBMPZone> listofBBMPZone;
    private ArrayList<ModelBBMPWard> listofBBMPWARD;
    private BBMPZoneListAdapter adapter;
    private BBMPWardListAdapter adapterward;
    private NetworkService networkService;
    private String key;
    private String key_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_list);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            key = bundle.getString("key");
            key_id = bundle.getString("key_id");
        }


        initMvp();
        if (key.equalsIgnoreCase("zone")) {
            initrecyclerView();
        }else if (key.equalsIgnoreCase("ward")) {
            initrecyclerViewWARD();
        }
        getListItem();
       // Log.e("DBaddress", DebugDB.getAddressLog());
        etSearchview.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        etSearchview.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        etSearchview.setText("");
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void initrecyclerView() {
        adapter = new BBMPZoneListAdapter(this, new ArrayList<ModelBBMPZone>());
        adapter.setOnclickListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvViewFamillly.setLayoutManager(manager);
        rvViewFamillly.setAdapter(adapter);
    }
    private void initrecyclerViewWARD() {
        adapterward = new BBMPWardListAdapter(this, new ArrayList<ModelBBMPWard>());
        adapterward.setOnclickListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvViewFamillly.setLayoutManager(manager);
        rvViewFamillly.setAdapter(adapterward);
    }

    private void getListItem() {


        if (key.equalsIgnoreCase("zone")) {
            String jsonFileString = getCommonApi().getJsonFromAssets(getApplicationContext(), "bbmp_zone.json");
            Log.i("data", jsonFileString);

            Gson gson = new Gson();
            Type listUserType = new TypeToken<List<ModelBBMPZone>>() {
            }.getType();
            listofBBMPZone = new ArrayList<>();
            listofBBMPZone = gson.fromJson(jsonFileString, listUserType);

            Collections.sort(listofBBMPZone, new Comparator<ModelBBMPZone>() {
                @Override
                public int compare(ModelBBMPZone s1, ModelBBMPZone s2) {
                    return s1.getZone_name().compareTo(s2.getZone_name());
                }
            });
            tvHeaderFac.setText("List Of BBMP ONE");
            adapter.addall(listofBBMPZone);
            //adapter.setType("z");

        }else if (key.equalsIgnoreCase("ward")) {
            //tvHeaderFac.setText("List Of Town/City");
           // adapter.addall((ArrayList<DistrictModel>) getAddressUrbaninfoRepository().getListAllItemByDistId(key_id));
//            adapter.setType("town");

            String jsonFileString = getCommonApi().getJsonFromAssets(getApplicationContext(), "bbmp_ward.json");
            Log.i("data", jsonFileString);

            Gson gson = new Gson();
            Type listUserType = new TypeToken<List<ModelBBMPWard>>() {
            }.getType();

            ArrayList<ModelBBMPWard> list = new ArrayList<>();
            list = gson.fromJson(jsonFileString, listUserType);

            getFilterList(list);


        }


    }

    private void getFilterList(ArrayList<ModelBBMPWard> list) {
        listofBBMPWARD = new ArrayList<>();
        if (list.size()>0){
            for (ModelBBMPWard modelBBMPWard:list){
                if (modelBBMPWard.getBbmp_zone_no_ksrsac().equalsIgnoreCase(key_id)){
                    listofBBMPWARD.add(modelBBMPWard);
                }
            }
        }

        //listofBBMPWARD = gson.fromJson(jsonFileString, listUserType);

        Collections.sort(listofBBMPWARD, new Comparator<ModelBBMPWard>() {
            @Override
            public int compare(ModelBBMPWard s1, ModelBBMPWard s2) {
                return s1.getWard_name().compareTo(s2.getWard_name());
            }
        });
        tvHeaderFac.setText("List Of BBMP WARD");
        adapterward.addall(listofBBMPWARD);
    }

    private void initMvp() {
        networkService = new NetworkService();
        networkService.inject(BBMPZoneWardListActivity.this);
    }



    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    @Override
    public void onClickMainZONE(int position, ModelBBMPZone item) {
        hideSoftKeyboard(BBMPZoneWardListActivity.this);
        Intent intent = new Intent();
        String message = item.getZone_name();
        intent.putExtra("name", message);

        intent.putExtra("name_id",""+ item.getBbmp_zone_no_ksrsac());
        setResult(RES_BBMPZONE, intent);
        finish();//finishing activity



    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        hideSoftKeyboard(BBMPZoneWardListActivity.this);
        Intent intent = new Intent();
        if (key.equalsIgnoreCase("zone")) {
            String message = "";
            intent.putExtra("name", message);
            intent.putExtra("name_id", "");
            setResult(RES_BBMPZONE, intent);
        }else if (key.equalsIgnoreCase("ward")) {
            String message = "";
            intent.putExtra("name", message);
            intent.putExtra("name_id", "");
            setResult(RES_BBMPWARD, intent);
        }/*else if (key.equalsIgnoreCase("ward")) {
            String message = "";
            intent.putExtra("name", message);
            intent.putExtra("name_id", "");
            setResult(RES_WORD, intent);
        }*/
       // finish();
        super.onBackPressed();
    }

    @Override
    public void onClickMain(int position, ModelBBMPWard item) {
        hideSoftKeyboard(BBMPZoneWardListActivity.this);
        Intent intent = new Intent();
        String message = item.getWard_name();
        intent.putExtra("name", message);

        intent.putExtra("name_id",""+ item.getWard_no_ksrac());
        setResult(RES_BBMPWARD, intent);
        finish();//finishing activity
    }
}
