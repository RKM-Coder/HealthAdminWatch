package com.goalsr.homequarantineTracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.adapter.HWPatientListAdapter;
import com.goalsr.homequarantineTracker.apiservice.ApiBackGround;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.resposemodel.ReqPAtientInfoByAdmin;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfoByAdmin;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ReqGetPatientinfobody;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ResPatientData;
import com.goalsr.homequarantineTracker.view.edittext.CustomEditText;
import com.goalsr.homequarantineTracker.view.edittext.DrawableClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminPatientLsitActivity extends BaseActivity implements HWPatientListAdapter.CheckedListener {

    @BindView(R.id.tv_header_fcl)
    TextView tvHeaderFcl;
    @BindView(R.id.iv_back_fcl)
    ImageButton ivBackFcl;
    @BindView(R.id.iv_dropdown_fcl)
    ImageView ivDropdownFcl;
    @BindView(R.id.toolbar_fcl)
    Toolbar toolbarFcl;
    @BindView(R.id.divider1)
    View divider1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_view_patient_list)
    RecyclerView rvViewPatientList;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.et_searchview)
    CustomEditText etSearchview;
    @BindView(R.id.img_calender)
    ImageView imgCalender;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.txtdistrictname)
    TextView txtdistrictname;
    @BindView(R.id.txtrelationChange)
    TextView txtrelationChange;
    @BindView(R.id.txt_logout)
    TextView txtLogout;
    @BindView(R.id.txtnameHeading)
    TextView txtnameHeading;
    @BindView(R.id.ll_main_familly)
    LinearLayout llMainFamilly;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.pbprogress)
    ProgressBar pbprogress;
    private NetworkService networkService;
    private HWPatientListAdapter adapter;
    private String key;

    private int patienttype = 0;
    ApiBackGround apiBackGround;
    boolean checkDataLoad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_patient_lsit);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiBackGround=new ApiBackGround(YelligoApplication.getContext());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            key = bundle.getString("key");
            // key_id = bundle.getString("key_id");
        }



        if (PreferenceStore.getPrefernceHelperInstace().getFlag(YelligoApplication.getContext(), PreferenceStore.PERSIONTYPE)) {
            tvHeaderFcl.setText("Person Under Quarantine");
            //txtLogout.setVisibility(View.GONE);
            patienttype = 1;
        } else {
            tvHeaderFcl.setText("Person Under Observation");
            //txtLogout.setVisibility(View.VISIBLE);
            patienttype = 2;
        }
        initMvp();
        initrecyclerView();
        // getPatientInfo();

        updateUI();
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        if (PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.DISTRICT_ID) != 0) {
            txtdistrictname.setText("" + PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.DISTRICT_NAME));
        }

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

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);

                getPatientInfo();

            }
        });

        rvViewPatientList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (manager.findFirstCompletelyVisibleItemPosition() == 0) {
                    swipeContainer.setEnabled(true);
                } else {
                    swipeContainer.setEnabled(false);
                }

            }
        });
    }

    LinearLayoutManager manager;

    private void initrecyclerView() {
        adapter = new HWPatientListAdapter(this, new ArrayList<PatientListDataItem>());
        adapter.setListener(this);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvViewPatientList.setLayoutManager(manager);
        rvViewPatientList.setAdapter(adapter);
    }

    private void initMvp() {
        networkService = new NetworkService();
        networkService.inject(AdminPatientLsitActivity.this);
    }

    private void getPatientInfo() {
        //TODO check sync done or not
       // setOnSyncResponse.
        showProgressDialogStatic();
        apiBackGround.setOnSyncResponse(new ApiBackGround.OnSyncResponse() {
            @Override
            public void onSyncSccess() {
                hideProgressDialogStatic();
                ReqGetPatientinfobody reqPatient = new ReqGetPatientinfobody();
                reqPatient.setDistrict_code(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.DISTRICT_ID));
                reqPatient.setUser_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
                reqPatient.setCity_code(-1);
                reqPatient.setGram_Panchayat_code(-1);
                reqPatient.setTaluk_code(-1);
                reqPatient.setWard_code(-1);
                reqPatient.setRole_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID));
                reqPatient.setP_security(AppConstants.getHealthWatchSecurityObjectupdated());
                networkService.getHWPatientFamillyInfo(reqPatient, new NetworkService.NetworkServiceListener() {
                    @Override
                    public void onFailure(Object response) {
                        if (swipeContainer != null)
                            swipeContainer.setRefreshing(false);
                        if (response instanceof String) {
                            Toast.makeText(YelligoApplication.getContext(), "" + response, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onAuthFail(Object error) {
                        hideProgressDialogStatic();
                    }

                    @Override
                    public void onSuccess(Object response, Boolean cancelFlag) {

                        //requestPermissions();
                        if (swipeContainer != null)
                            swipeContainer.setRefreshing(false);
                        if (response instanceof ResPatientData) {

                        }
                    }
                });
            }

            @Override
            public void onFail() {
                hideProgressDialogStatic();
                if (swipeContainer != null)
                    swipeContainer.setRefreshing(false);
                Toast.makeText(YelligoApplication.getContext(),"Try Again",Toast.LENGTH_LONG).show();
            }
        });
        apiBackGround.makesyncCall();

    }

    private void updateUI() {
        // List<ResPatientInfo> patientList = new ArrayList<>();
        pbprogress.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pbprogress!=null)
                    pbprogress.setVisibility(View.GONE);
            }
        }, 8000);
        getPatientViewmodel().getLivedatPAtient(patienttype).observe(this, new Observer<List<PatientListDataItem>>() {
            @Override
            public void onChanged(List<PatientListDataItem> resPatientInfos) {
                //Log.e("DataChange",)
                if (resPatientInfos.size()>0){
                    if (pbprogress!=null)
                        pbprogress.setVisibility(View.GONE);
                }

                /*if (resPatientInfos.size()>0){
                    if (pbprogress!=null)
                    pbprogress.setVisibility(View.GONE);
                }*//*else if (resPatientInfos.size()==0){
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (pbprogress!=null)
                            pbprogress.setVisibility(View.VISIBLE);
                        }
                    }, 4000);

                }*/
                adapter.setValue((ArrayList<PatientListDataItem>) resPatientInfos);
            }

        });
     /*   patientList = getPatientinfoRepository().getListAllItemByAdmin();
        Log.e("PatientList--", patientList.size() + "");*/


    }


    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    @Override
    public void onItemCheckedFamilly(int position, PatientListDataItem item) {
       /* PreferenceStore.getPrefernceHelperInstace().setIntValue(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID, 123456);
        Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
        *//*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);*//*
        startActivity(intent);*/
        if (!item.getLocalID().equalsIgnoreCase("")) {
            /* getPatientFamillyinfoRepository().clear();*/
            PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.CITIZEN_LOCALID, item.getLocalID());
            PreferenceStore.getPrefernceHelperInstace().setIntValue(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID, item.getCitizenID());
            Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

   /* @OnClick(R.id.txtrelationChange)
    public void onViewClicked() {

    }*/

    @Override
    protected void onResume() {
        super.onResume();

        /*if (!PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_DATE).equalsIgnoreCase(AppConstants.getCurrentDate())) {

            PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(),PreferenceStore.USER_DATE,AppConstants.getCurrentDate());
            callLogout();
        }*/
    }

    private void callLogout() {
        PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.LOGIN, false);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
    }

    @OnClick({R.id.txt_logout, R.id.txtrelationChange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_logout:
                Bundle bundle = new Bundle();
                bundle.putString("key", "newpatient");
                bundle.putString("keytype", "self");
                bundle.putString("keytype_family_localid", "");
                getCommonApi().openNewScreen(AddnewPatientActivity.class, bundle);
                // getCommonApi().openNewScreen(AddnewPatientActivity.class);
                break;
            case R.id.txtrelationChange:
                Intent intent = new Intent(getApplicationContext(), DistrictListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

      /*  new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AdminPatientLsitActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();*/

    }
}
