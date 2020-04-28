package com.goalsr.homequarantineTracker.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.ApiBackGround;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;
import com.goalsr.homequarantineTracker.service.MysyncServiceWorkmanager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.goalsr.homequarantineTracker.Utils.AppConstants.getHealthWatchSecurityObjectupdated;

public class DasboardPType extends BaseActivity {

    @BindView(R.id.btn_qurantine)
    Button btnQurantine;
    @BindView(R.id.btn_observer)
    Button btnObserver;
    @BindView(R.id.tv_header_fcl)
    TextView tvHeaderFcl;
    @BindView(R.id.iv_back_fcl)
    ImageButton ivBackFcl;
    @BindView(R.id.txt_logout)
    TextView txtLogout;
    @BindView(R.id.iv_dropdown_fcl)
    ImageView ivDropdownFcl;
    @BindView(R.id.toolbar_fcl)
    Toolbar toolbarFcl;
    @BindView(R.id.divider1)
    View divider1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtnameHeading)
    TextView txtnameHeading;
    @BindView(R.id.txtdistrictname)
    TextView txtdistrictname;
    @BindView(R.id.txtrelationChange)
    TextView txtrelationChange;
    @BindView(R.id.ll_main_familly)
    LinearLayout llMainFamilly;
    @BindView(R.id.img_1)
    ImageView img1;
    @BindView(R.id.img_2)
    ImageView img2;
    @BindView(R.id.img_3)
    ImageView img3;
    @BindView(R.id.img_4)
    ImageView img4;
    private ApiBackGround apiBackGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_type);
        ButterKnife.bind(this);
        showProgressDialogStatic();
        apiBackGround=new ApiBackGround(YelligoApplication.getContext());
        if (PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.DISTRICT_ID) != 0) {
            txtdistrictname.setText("" + PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.DISTRICT_NAME));
        }
        startWorkManager();


        getPatientViewmodel().getLivedatPAtientTest().observe(this, new Observer<List<PatientListDataItem>>() {
            @Override
            public void onChanged(List<PatientListDataItem> list) {
                if (list.size()>0){
                    hideProgressDialogStatic();
                }

            }
        });
    }

    private void startWorkManager() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(MysyncServiceWorkmanager.class, 30, TimeUnit.MINUTES)
                 .setConstraints(constraints)
                .addTag(AppConstants.TRACKTAG)
                .build();
        //WorkManager.getInstance().enqueueUniquePeriodicWork("Location", ExistingPeriodicWorkPolicy.REPLACE, periodicWork);
        WorkManager.getInstance(DasboardPType.this).enqueue(periodicWork);
    }

    @OnClick({R.id.btn_qurantine, R.id.btn_observer,R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.txtrelationChange})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {

            case R.id.btn_qurantine:

                bundle.putString("key", "a");
                //bundle.putString("key_id","0");
              // PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(),PreferenceStore.PERSIONTYPE,true);
              //  getCommonApi().openNewScreen(AdminPatientLsitActivity.class, bundle);
                break;
            case R.id.btn_observer:

                bundle.putString("key", "b");
                //bundle.putString("key_id","0");
               // PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(),PreferenceStore.PERSIONTYPE,false);
               // getCommonApi().openNewScreen(AdminPatientLsitActivity.class, bundle);
                break;

            case R.id.img_1:
                PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(),PreferenceStore.PERSIONTYPE,true);
                bundle.putString("key", "a");
                //bundle.putString("key_id","0");
                getCommonApi().openNewScreen(AdminPatientLsitActivity.class, bundle);
                break;
            case R.id.img_2:
                bundle.putString("key", "b");
                //bundle.putString("key_id","0");
                PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(),PreferenceStore.PERSIONTYPE,false);
                getCommonApi().openNewScreen(AdminPatientLsitActivity.class, bundle);
                break;
            case R.id.img_3:
                makesyncthenSync();
                break;
            case R.id.img_4:
                //getHealthWatchSecurityObjectupdated();
                makesyncthenLogout();
                break;
            case R.id.txtrelationChange:
                makesyncDistrictChange();

                break;
        }
    }
    private void makesyncthenLogout() {
        showProgressDialogStatic();
        apiBackGround.setOnSyncResponse(new ApiBackGround.OnSyncResponse() {
            @Override
            public void onSyncSccess() {
                hideProgressDialogStatic();
                new AlertDialog.Builder(DasboardPType.this)
                        .setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Toast.makeText(YelligoApplication.getContext(),"Successfully data sync",Toast.LENGTH_LONG).show();
                                PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(),PreferenceStore.LOGIN,false);
                                Intent intent = new Intent(getApplicationContext(), SplashMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }

            @Override
            public void onFail() {
                hideProgressDialogStatic();

            }
        });
        apiBackGround.makesyncCall();
    }

    private void makesyncDistrictChange() {
        showProgressDialogStatic();
        apiBackGround.setOnSyncResponse(new ApiBackGround.OnSyncResponse() {
            @Override
            public void onSyncSccess() {
                hideProgressDialogStatic();
                new AlertDialog.Builder(DasboardPType.this)
                        .setMessage("Are you sure you want to Change District?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               //Toast.makeText(YelligoApplication.getContext(),"Successfully data sync",Toast.LENGTH_LONG).show();
                               // PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(),PreferenceStore.LOGIN,false);

                                Intent intent = new Intent(getApplicationContext(), DistrictListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }

            @Override
            public void onFail() {
                hideProgressDialogStatic();
            }
        });
        apiBackGround.makesyncCall();
    }

    private void makesyncthenSync() {
        showProgressDialogStatic();
        apiBackGround.setOnSyncResponse(new ApiBackGround.OnSyncResponse() {
            @Override
            public void onSyncSccess() {
                hideProgressDialogStatic();

                Toast.makeText(YelligoApplication.getContext(),"Successfully data sync",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFail() {
                hideProgressDialogStatic();
                Toast.makeText(YelligoApplication.getContext(),"Please try again",Toast.LENGTH_LONG).show();

            }
        });
        apiBackGround.makesyncCall();
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DasboardPType.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


}
