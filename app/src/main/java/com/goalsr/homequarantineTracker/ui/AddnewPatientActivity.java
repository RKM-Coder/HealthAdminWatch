package com.goalsr.homequarantineTracker.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.adapter.SpinAdapter;
import com.goalsr.homequarantineTracker.adapter.SymptomListAdapter;
import com.goalsr.homequarantineTracker.apiservice.ApiBackGround;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.dialog.CustomDialogGeneric;
import com.goalsr.homequarantineTracker.fragment.DatePickerFragment;
import com.goalsr.homequarantineTracker.gpsenable.GpsUtils;
import com.goalsr.homequarantineTracker.resposemodel.ModelSymptomGVT;
import com.goalsr.homequarantineTracker.resposemodel.ResStaticMasterDistric;
import com.goalsr.homequarantineTracker.resposemodel.ResStaticMasterDistricDB;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientFamilyDetailsItem;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.goalsr.homequarantineTracker.Utils.AppConstants.myPermissionsForLoac;

public class AddnewPatientActivity extends BaseActivity implements SymptomListAdapter.CheckedListener, DatePickerFragment.OnFragmentInteractionListener {


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
    @BindView(R.id.et_customer_name)
    EditText etCustomerName;
    @BindView(R.id.sp_gender)
    Spinner spGender;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_customer_mobile)
    EditText etCustomerMobile;
    @BindView(R.id.et_customer_email)
    EditText etCustomerEmail;
    @BindView(R.id.txt_DOA)
    TextView txtDOA;
    @BindView(R.id.txt_qurantinedate)
    TextView txtQurantinedate;
    @BindView(R.id.et_poorigin)
    EditText etPoorigin;
    @BindView(R.id.et_poarrival)
    EditText etPoarrival;
    @BindView(R.id.sp_areatype)
    Spinner spAreatype;
    @BindView(R.id.et_houseno)
    EditText etHouseno;
    @BindView(R.id.et_building)
    EditText etBuilding;
    @BindView(R.id.et_street)
    EditText etStreet;

    @BindView(R.id.llurban)
    LinearLayout llurban;

    @BindView(R.id.ll_rural)
    LinearLayout llRural;
    /*@BindView(R.id.chk_box_txt1)
    TextView chkBoxTxt1;
    @BindView(R.id.chk_box1)
    CheckBox chkBox1;
    @BindView(R.id.chk_box_txt2)
    TextView chkBoxTxt2;
    @BindView(R.id.chk_box2)
    CheckBox chkBox2;
    @BindView(R.id.chk_box_txt3)
    TextView chkBoxTxt3;
    @BindView(R.id.chk_box3)
    CheckBox chkBox3;
    @BindView(R.id.chk_box_txt4)
    TextView chkBoxTxt4;
    @BindView(R.id.chk_box4)
    CheckBox chkBox4;
    @BindView(R.id.chk_box_txt5)
    TextView chkBoxTxt5;
    @BindView(R.id.chk_box5)
    CheckBox chkBox5;
    @BindView(R.id.chk_box_txt6)
    TextView chkBoxTxt6;
    @BindView(R.id.chk_box6)
    CheckBox chkBox6;
    @BindView(R.id.imageicon7)
    ImageView imageicon7;
    @BindView(R.id.chk_box_txt7)
    TextView chkBoxTxt7;
    @BindView(R.id.chk_box7)
    CheckBox chkBox7;
    @BindView(R.id.imageicon)
    ImageView imageicon;
    @BindView(R.id.chk_box_txt8)
    TextView chkBoxTxt8;*/
   /* @BindView(R.id.ll_chk)
    LinearLayout llChk;
    @BindView(R.id.chk_box8)
    CheckBox chkBox8;*/

    /*@BindView(R.id.txt_104)
    TextView txt104;*/
   /* @BindView(R.id.txt_08046848600)
    TextView txt08046848600;
    @BindView(R.id.txt_9745697456)
    TextView txt9745697456;
    @BindView(R.id.txt_08066692000)
    TextView txt08066692000;
    @BindView(R.id.ll_call)
    LinearLayout llCall;
    @BindView(R.id.helptechnical)
    CardView helptechnical;
    @BindView(R.id.ll_lelp)
    LinearLayout llLelp;
    @BindView(R.id.helpcovid)
    CardView helpcovid;*/
    @BindView(R.id.activity_main_rl)
    RelativeLayout activityMainRl;


    @BindView(R.id.txt_dist_spin)
    TextView txtDistSpin;
    @BindView(R.id.lladdress)
    LinearLayout lladdress;
    @BindView(R.id.txt_city_spin)
    TextView txtCitySpin;
    @BindView(R.id.txt_word_spin)
    TextView txtWordSpin;
    @BindView(R.id.txt_taluk_spin)
    TextView txtTalukSpin;
    @BindView(R.id.txt_grampanchyate_spin)
    TextView txtGrampanchyateSpin;
    @BindView(R.id.txt_village_spin)
    TextView txtVillageSpin;
    @BindView(R.id.txt_startdate_heading)
    TextView txtStartdateHeading;
    @BindView(R.id.txt_end_heading)
    TextView txtEndHeading;
    @BindView(R.id.chk_box_txt11)
    TextView chkBoxTxt11;
    @BindView(R.id.chk_box12)
    CheckBox chkBox12;
    @BindView(R.id.chk_box_txt13)
    TextView chkBoxTxt13;
    @BindView(R.id.chk_box13)
    CheckBox chkBox13;
    @BindView(R.id.chk_box_txt14)
    TextView chkBoxTxt14;
    @BindView(R.id.chk_box14)
    CheckBox chkBox14;
    @BindView(R.id.txt_DOA11)
    TextView txtDOA11;
    @BindView(R.id.rv_view)
    RecyclerView rvView;
    @BindView(R.id.simpleCheckedTextView)
    CheckedTextView simpleCheckedTextView;
    @BindView(R.id.txt_bbmpzone)
    TextView txtBbmpzone;
    @BindView(R.id.txt_bbmpword_spin)
    TextView txtBbmpwordSpin;
    @BindView(R.id.llbbmp)
    LinearLayout llbbmp;
    @BindView(R.id.radioButton)
    RadioButton radioButton;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.llpoorigin)
    LinearLayout llpoorigin;
    @BindView(R.id.llpoarrival)
    LinearLayout llpoarrival;
    @BindView(R.id.sp_relation)
    Spinner spRelation;
    @BindView(R.id.llrelation)
    LinearLayout llrelation;
    @BindView(R.id.lladdresstag)
    LinearLayout lladdresstag;
    @BindView(R.id.llTypelocation)
    LinearLayout llTypelocation;



    private String selctedDistId = "-1";
    private String selctedCityId = "-1";
    private String selctedWordId = "-1";
    private String selctedpanchyateId = "-1";
    private String selctedvillageId = "-1";
    private String selctedtalukid = "-1";
    private String selctedbbmpzoneid = "-1";
    private String selctedbbmpwordId = "-1";


    boolean isHospitalised=false;
    boolean isHistoryContactLab=false;

    public static int RES_DIST = 5951;
    public static int RES_TOWN = 5952;
    public static int RES_WORD = 5953;
    public static int RES_TALUK = 5954;
    public static int RES_PANCHYATE = 5955;
    public static int RES_VILLAGE = 5956;
    public static int RES_BBMPZONE = 5957;
    public static int RES_BBMPWARD = 5958;


    ArrayList<ResStaticMasterDistric> listOfurbandata;
    private SymptomListAdapter adapter;
    private ArrayList<String> selectedString;

    String startdate = "", enddate = "", dateofsymtom = "", daovvalue = "";
    private NetworkService networkService;

    PatientListDataItem resPatientInfo = new PatientListDataItem();
    PatientFamilyDetailsItem resPatientFamilyInfo = new PatientFamilyDetailsItem();


    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private static final String TAG = "Addnewpatient";

    /**
     * The current location.
     */
    private Location mLocation;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationCallback mLocationCallback;
    private int selectedgender = 0;

    String key = "", keytype = "", keytypefamilyloclId = "";
    private boolean isForignTip = false;
    private int selectedrelation = 0;
    private int selctedriskarea = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_patient);
        ButterKnife.bind(this);
        initMvp();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mayRequestContacts();
            checkPermission();
        } else {
            // continueLoading();//TODO
            getCurrentLOc();
        }

        //  Log.e("UUID", UUID.randomUUID().toString());
        Bundle bundle = getIntent().getExtras();
        tvHeaderFac.setText("Person Details");
        if (bundle != null) {
            key = bundle.getString("key");
            keytype = bundle.getString("keytype");
            keytypefamilyloclId = bundle.getString("keytype_family_localid");
            /*if (bundle.getString("key").equalsIgnoreCase("self")) {
                //tvHeaderFac.setText("Person Details");

            } else if (bundle.getString("key").equalsIgnoreCase("family")) {
                //tvHeaderFac.setText("Family 1 Info");
                //getPatientFamilyByid();
            }*/
        }
        String areaytypearray[] = getResources().getStringArray(R.array.areatype);
        List<String> areatypelist = new ArrayList<>();
        areatypelist = new ArrayList<>(Arrays.asList(areaytypearray));
        makeSpinnerAreaType((ArrayList<String>) areatypelist);


        String relationship[] = getResources().getStringArray(R.array.relation);
        List<String> relationlist = new ArrayList<>();
        relationlist = new ArrayList<>(Arrays.asList(relationship));
        makeSpinnerRElationMethod((ArrayList<String>) relationlist);


        listOfurbandata = new ArrayList<>();

        initSymptomView();


        getAddressUrbanViewmodel().getListOfDistLivedata().observe(this, new Observer<List<ResStaticMasterDistricDB>>() {
            @Override
            public void onChanged(List<ResStaticMasterDistricDB> listOfurbandataDistrict) {
                //makeSpinnerDistrictMethod((ArrayList<ResStaticMasterDistricDB>) listOfurbandataDistrict);
            }
        });

        if (!PreferenceStore.getPrefernceHelperInstace().getFlag(YelligoApplication.getContext(), PreferenceStore.PERSIONTYPE)) {

            txtStartdateHeading.setText("Start Date of Observation *");
            txtEndHeading.setText("End Date of Observation");
        } else {
            txtStartdateHeading.setText("Start Date of Quarntine *");
            txtEndHeading.setText("End Date of Quarntine *");
        }


        String genderarray[] = getResources().getStringArray(R.array.gender);
        List<String> genderlist = new ArrayList<>();
        genderlist = new ArrayList<>(Arrays.asList(genderarray));

       /* String genderarray[] = getResources().getStringArray(R.array.gender);
        ArrayList<String> genderlist=new ArrayList<>();
        genderlist= (ArrayList<String>) Arrays.asList(genderarray);*/
        makeSpinnerGenderMethod((ArrayList<String>) genderlist);


        if (keytype.equalsIgnoreCase("self")) {
            lladdresstag.setVisibility(View.VISIBLE);
            llTypelocation.setVisibility(View.VISIBLE);

            spAreatype.setSelection(0);

            llrelation.setVisibility(View.GONE);
            if (key.equalsIgnoreCase("newpatient")) {
                maketosetAllValue();

            } else {
                setInfoMainpatient();
            }

        } else if (keytype.equalsIgnoreCase("family")) {
            lladdresstag.setVisibility(View.GONE);
            llTypelocation.setVisibility(View.GONE);
            spAreatype.setSelection(0);
            llRural.setVisibility(View.GONE);
            llurban.setVisibility(View.GONE);
            lladdress.setVisibility(View.GONE);
            llbbmp.setVisibility(View.GONE);

            int ciD = PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID);
            resPatientInfo = getHwPatientinfoRepository().getPatientInfo(ciD);
            llrelation.setVisibility(View.VISIBLE);
            if (key.equalsIgnoreCase("newpatient")) {
                maketosetAllValue();

            } else {
                setInfoMainpatientFamilyDetails();
            }
        }


    }

    private void makeSpinnerRElationMethod(final ArrayList<String> list) {
        SpinAdapter<String> adapter =
                new SpinAdapter(getApplicationContext(), 0, 0, list, "");
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spRelation.setAdapter(adapter);
        spRelation.setSelection(0);
        adapter.notifyDataSetChanged();
        spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedrelation = position;
                /*if (position != 0) {
                    selectedgender = list.get(position).toString();
                } else {
                    selectedgender = "";
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        selctedriskarea = 0;
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked)
                    selctedriskarea = 1;
                break;
            case R.id.radioButton2:
                if (checked)
                    selctedriskarea = 2;

                break;
        }
    }

    private void initMvp() {

        networkService = new NetworkService();
        networkService.inject(AddnewPatientActivity.this);
    }

    private void makeSpinnerGenderMethod(final ArrayList<String> list) {
        SpinAdapter<String> adapter =
                new SpinAdapter(getApplicationContext(), 0, 0, list, "");
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);
        spGender.setSelection(0);
        adapter.notifyDataSetChanged();
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedgender = position;
                /*if (position != 0) {
                    selectedgender = list.get(position).toString();
                } else {
                    selectedgender = "";
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initSymptomView() {
        adapter = new SymptomListAdapter(this, new ArrayList<ModelSymptomGVT>());
        adapter.setListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvView.setLayoutManager(manager);
        rvView.setAdapter(adapter);
        selectedString = new ArrayList<>();
        adapter.setValue(getList());
        adapter.setcheckitem("");
    }

    private ArrayList<ModelSymptomGVT> getList() {
      /*  ArrayList<String> listString = new ArrayList<>();
        listString.add("Fever");
        listString.add("cough");
        listString.add("Cold");
        listString.add("Tiredness");
        listString.add("Other");*/
        return getCommonApi().getListOdSymtomByGVT();
    }

    public static int selectaretype = 0;

    private void makeSpinnerAreaType(final ArrayList<String> list) {
        SpinAdapter<String> adapter =
                new SpinAdapter(getApplicationContext(), 0, 0, list, "");
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spAreatype.setAdapter(adapter);
        spAreatype.setSelection(0);
        adapter.notifyDataSetChanged();
        spAreatype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedgender = position;
                switch (position) {
                    case 0:
                        llRural.setVisibility(View.GONE);
                        llurban.setVisibility(View.GONE);
                        lladdress.setVisibility(View.GONE);
                        llbbmp.setVisibility(View.GONE);
                        resetViewForAreaType();
                        selectaretype = 0;

                        break;
                    case 1:
                        llRural.setVisibility(View.GONE);
                        llbbmp.setVisibility(View.GONE);
                        llurban.setVisibility(View.VISIBLE);
                        lladdress.setVisibility(View.VISIBLE);
                        if (selectaretype != 1) {
                            resetViewForAreaType();
                            selectaretype = 1;
                        }
                        break;
                    case 2:
                        llRural.setVisibility(View.VISIBLE);
                        llbbmp.setVisibility(View.GONE);
                        llurban.setVisibility(View.GONE);
                        lladdress.setVisibility(View.VISIBLE);
                        if (selectaretype != 2) {
                            resetViewForAreaType();
                            selectaretype = 2;
                        }
                        break;

                    case 3://BBMP
                        llRural.setVisibility(View.GONE);
                        llbbmp.setVisibility(View.VISIBLE);
                        llurban.setVisibility(View.GONE);
                        lladdress.setVisibility(View.VISIBLE);
                        if (selectaretype != 3) {

                            resetViewForAreaType();

                            selctedDistId = "1502";
                            txtDistSpin.setText("Bengaluru");
                            selectaretype = 3;
                        }
                        break;
                    default:
                        llRural.setVisibility(View.GONE);
                        llurban.setVisibility(View.GONE);
                        llbbmp.setVisibility(View.GONE);
                        lladdress.setVisibility(View.GONE);

                        resetViewForAreaType();
                        selectaretype = 0;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void resetViewForAreaType() {
        txtDistSpin.setText("Select District");
        txtCitySpin.setText("Select City");
        txtWordSpin.setText("Select Word");

        txtBbmpzone.setText("Select BBMP Zone");
        txtBbmpwordSpin.setText("Select BBMP Word");

        txtTalukSpin.setText("Select Taluk/Block");
        txtGrampanchyateSpin.setText("Select Panchyate");
        txtVillageSpin.setText("Select Village");
        selctedDistId = "-1";
        selctedCityId = "-1";
        selctedWordId = "-1";
        selctedpanchyateId = "-1";
        selctedvillageId = "-1";
        selctedtalukid = "-1";
        selctedbbmpzoneid = "-1";
        selctedbbmpwordId = "-1";
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    @OnClick({R.id.chk_box12, R.id.chk_box13, R.id.chk_box14, R.id.simpleCheckedTextView, R.id.txt_bbmpzone, R.id.txt_bbmpword_spin, R.id.llbbmp, R.id.txt_dist_spin, R.id.txt_city_spin, R.id.txt_word_spin, R.id.txt_taluk_spin, R.id.txt_grampanchyate_spin, R.id.txt_village_spin, R.id.submit_btn, R.id.tv_logout, R.id.txt_DOA, R.id.txt_qurantinedate, R.id.txt_DOA11})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_dist_spin:
                if (selectaretype!=3) {
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "dist");
                    bundle.putString("key_id", "0");
                    //resetViewForAreaType();
                    Intent intent = new Intent(getApplicationContext(), AddressGenericListActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, RES_DIST);
                }

                break;
            case R.id.txt_city_spin:

                if (!selctedDistId.equalsIgnoreCase("-1")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("key", "town");
                    bundle1.putString("key_id", selctedDistId);

               /* txtCitySpin.setText("Selct City");
                txtWordSpin.setText("Selct Word");

                txtTalukSpin.setText("Selct Taluk/Block");
                txtGrampanchyateSpin.setText("Selct Panchyate");
                txtVillageSpin.setText("Selct Village");*/
                    Intent intent1 = new Intent(getApplicationContext(), AddressGenericListActivity.class);
                    intent1.putExtras(bundle1);
                    startActivityForResult(intent1, RES_TOWN);
                } else {
                    Toast.makeText(YelligoApplication.getContext(), "Please select district", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.txt_word_spin:
                if (!selctedCityId.equalsIgnoreCase("-1")) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("key", "ward");
                    bundle2.putString("key_id", selctedCityId);

              /*  txtWordSpin.setText("Selct Word");

                txtTalukSpin.setText("Selct Taluk/Block");
                txtGrampanchyateSpin.setText("Selct Panchyate");
                txtVillageSpin.setText("Selct Village");*/
                    Intent intent2 = new Intent(getApplicationContext(), AddressGenericListActivity.class);
                    intent2.putExtras(bundle2);
                    startActivityForResult(intent2, RES_WORD);
                } else {
                    Toast.makeText(YelligoApplication.getContext(), "Please select City/Town", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.txt_taluk_spin:
                if (!selctedDistId.equalsIgnoreCase("-1")) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("key", "taluk");

               /* txtTalukSpin.setText("Selct Taluk/Block");
                txtGrampanchyateSpin.setText("Selct Panchyate");
                txtVillageSpin.setText("Selct Village");*/
                    bundle3.putString("key_id", selctedDistId);
                    Intent intent3 = new Intent(getApplicationContext(), TalukListActivity.class);
                    intent3.putExtras(bundle3);
                    startActivityForResult(intent3, RES_TALUK);
                } else {
                    Toast.makeText(YelligoApplication.getContext(), "Please select district", Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.txt_grampanchyate_spin:
                if (!selctedtalukid.equalsIgnoreCase("-1")) {
 /* txtGrampanchyateSpin.setText("Selct Panchyate");
                txtVillageSpin.setText("Selct Village");*/
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("key", "panchyate");
                    bundle4.putString("key_id", "" + selctedtalukid);
                    Intent intent4 = new Intent(getApplicationContext(), PanchyateListActivity.class);
                    intent4.putExtras(bundle4);
                    startActivityForResult(intent4, RES_PANCHYATE);
                } else {
                    Toast.makeText(YelligoApplication.getContext(), "Please select Taluk", Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.txt_village_spin:
                if (!selctedpanchyateId.equalsIgnoreCase("-1")) {
                    // txtVillageSpin.setText("Selct Village");
                    Bundle bundle5 = new Bundle();
                    bundle5.putString("key", "village");
                    bundle5.putString("key_id", "" + selctedpanchyateId);
                    Intent intent5 = new Intent(getApplicationContext(), VillageGenericListActivity.class);
                    intent5.putExtras(bundle5);
                    startActivityForResult(intent5, RES_VILLAGE);
                } else {
                    Toast.makeText(YelligoApplication.getContext(), "Please select Panchyate", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.tv_logout:
                if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    checkPermission();
                } else {

                    new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
                        @Override
                        public void gpsStatus(boolean isGPSEnable) {
                            // turn on GPS
                            AppConstants.isGPS = isGPSEnable;
                            getCurrentLOc();
                            //if (getCommonApi().isInternetAvailable(AddnewPatientActivity.this)) {

                            if (keytype.equalsIgnoreCase("self")) {
                                submitDatapatientself();
                            } else if (keytype.equalsIgnoreCase("family")) {
                                submitDatapatientfamily();
                            }

                            /*} else {
                                Toast.makeText(YelligoApplication.getContext(), "Please enable internet connection", Toast.LENGTH_LONG).show();
                            }*/
                        }
                    });

                }
                break;

            case R.id.txt_DOA:
                DatePickerFragment newFragment = new DatePickerFragment();
                daovvalue = "dao";
                Bundle args = new Bundle();

                args.putString("datefrom", "");
                newFragment.setArguments(args);
                // set the targetFragment to receive the results, specifying the request code
                //  newFragment.setTargetFragment(newFragment, REQUEST_CODE);
                // show the datePicker
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.txt_DOA11:
                DatePickerFragment newFragment3 = new DatePickerFragment();
                daovvalue = "dfs";
                Bundle args3 = new Bundle();

                args3.putString("datefrom", "");
                newFragment3.setArguments(args3);
                // set the targetFragment to receive the results, specifying the request code
                //  newFragment.setTargetFragment(newFragment, REQUEST_CODE);
                // show the datePicker
                newFragment3.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.txt_qurantinedate:
                if (startdate.equalsIgnoreCase("")) {
                    showDialogInMsg("Please add start date");
                } else {
                    daovvalue = "qao";
                    DatePickerFragment newFragment2 = new DatePickerFragment();
                    Bundle args1 = new Bundle();

                    args1.putString("datefrom", startdate);
                    newFragment2.setArguments(args1);
                    // set the targetFragment to receive the results, specifying the request code
                    //  newFragment.setTargetFragment(newFragment, REQUEST_CODE);
                    // show the datePicker
                    newFragment2.show(getSupportFragmentManager(), "datePicker");
                }
                break;

            case R.id.simpleCheckedTextView:

                if (simpleCheckedTextView.isChecked()) {
                    isForignTip = false;
                    etPoarrival.setText("");
                    etPoorigin.setText("");
                    simpleCheckedTextView.setChecked(false);
                    llpoarrival.setVisibility(View.GONE);
                    llpoorigin.setVisibility(View.GONE);


                } else {
                    isForignTip = true;
                    simpleCheckedTextView.setChecked(true);
                    llpoarrival.setVisibility(View.VISIBLE);
                    llpoorigin.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.chk_box12:
                isHistoryContactLab=chkBox12.isChecked();
                break;
            case R.id.chk_box13:
                isHospitalised=chkBox13.isChecked();
                break;
            case R.id.chk_box14:
                break;
            case R.id.txt_bbmpzone:
                if (!selctedDistId.equalsIgnoreCase("-1")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("key", "zone");
                    bundle1.putString("key_id", selctedDistId);
                    Intent intent1 = new Intent(getApplicationContext(), BBMPZoneWardListActivity.class);
                    intent1.putExtras(bundle1);
                    startActivityForResult(intent1, RES_BBMPZONE);
                } else {
                    Toast.makeText(YelligoApplication.getContext(), "Please select district", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.txt_bbmpword_spin:
                if (!selctedbbmpzoneid.equalsIgnoreCase("-1")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("key", "ward");
                    bundle1.putString("key_id", selctedbbmpzoneid);
                    Intent intent1 = new Intent(getApplicationContext(), BBMPZoneWardListActivity.class);
                    intent1.putExtras(bundle1);
                    startActivityForResult(intent1, RES_BBMPWARD);
                } else {
                    Toast.makeText(YelligoApplication.getContext(), "Please select Zone", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.llbbmp:
                break;

          /*  case R.id.simpleCheckedTextView:
                if (simpleCheckedTextView.isChecked()){
                    simpleCheckedTextView.setChecked(false);
                }else {
                    simpleCheckedTextView.setChecked(true);
                }
                break;*/
        }
    }

    private void showDialogInMsg(String message) {
        if (!isFinishing()) {
            CustomDialogGeneric dialog = new CustomDialogGeneric(AddnewPatientActivity.this, "",
                    new CustomDialogGeneric.OnButtonClickListener() {
                        @Override
                        public void onLeftButtonClick(CustomDialogGeneric dialog) {
                            dialog.dismiss();
                            // Toast.makeText(getActivity(),"Customer created successfully",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                        @Override
                        public void onRightButtonClick(CustomDialogGeneric dialog, String notes) {
                            dialog.dismiss();

                        }


                    });
            dialog.setCancelable(false);
            dialog.setRightButtonText("Retry");
            dialog.setRightButtonVisibility(View.GONE);
            dialog.setLeftButtonVisibility(View.VISIBLE);
            dialog.setLeftButtonText("OK");
            dialog.setDialogType(CustomDialogGeneric.TYPE_ALERT);
            dialog.setDescription("" + message);
            dialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RES_DIST) {
            String name = data.getStringExtra("name");
            if (!data.getStringExtra("name_id").equalsIgnoreCase("")) {
                if (!data.getStringExtra("name_id").equalsIgnoreCase(selctedDistId)) {


                    txtCitySpin.setText("Select City");
                    txtWordSpin.setText("Select Word");

                    txtTalukSpin.setText("Select Taluk/Block");
                    txtGrampanchyateSpin.setText("Select Panchyate");
                    txtVillageSpin.setText("Select Village");

                    //selctedDistId = "-1";
                    selctedCityId = "-1";
                    selctedWordId = "-1";
                    selctedpanchyateId = "-1";
                    selctedvillageId = "-1";
                    selctedtalukid = "-1";

                    selctedDistId = data.getStringExtra("name_id");
                    txtDistSpin.setText(name);
                    txtDistSpin.requestFocus();
                }
            }

        } else if (requestCode == RES_TOWN) {
            if (!data.getStringExtra("name_id").equalsIgnoreCase("")) {
                if (!data.getStringExtra("name_id").equalsIgnoreCase(selctedCityId)) {


                    //txtCitySpin.setText("Selct City");
                    txtWordSpin.setText("Select Word");

                    txtTalukSpin.setText("Select Taluk/Block");
                    txtGrampanchyateSpin.setText("Select Panchyate");
                    txtVillageSpin.setText("Select Village");
                    // selctedDistId = "-1";
                    //selctedCityId = "-1";
                    selctedWordId = "-1";
                    selctedpanchyateId = "-1";
                    selctedvillageId = "-1";
                    selctedtalukid = "-1";
                    String name = data.getStringExtra("name");
                    selctedCityId = data.getStringExtra("name_id");
                    txtCitySpin.setText(name);
                    txtCitySpin.requestFocus();
                }
            }

        } else if (requestCode == RES_WORD) {

            if (!data.getStringExtra("name_id").equalsIgnoreCase("")) {


                txtTalukSpin.setText("Select Taluk/Block");
                txtGrampanchyateSpin.setText("Select Panchyate");
                txtVillageSpin.setText("Select Village");
                // selctedDistId = "-1";
                // selctedCityId = "-1";
                // selctedWordId = "-1";
                selctedpanchyateId = "-1";
                selctedvillageId = "-1";
                selctedtalukid = "-1";
                String name = data.getStringExtra("name");
                selctedWordId = data.getStringExtra("name_id");
                txtWordSpin.setText(name);
                txtWordSpin.requestFocus();
            }
        } else if (requestCode == RES_TALUK) {

            if (!data.getStringExtra("name_id").equalsIgnoreCase("")) {

               /* txtCitySpin.setText("Selct City");
                txtWordSpin.setText("Selct Word");

                txtTalukSpin.setText("Selct Taluk/Block");*/
                txtGrampanchyateSpin.setText("Select Panchyate");
                txtVillageSpin.setText("Select Village");
                // selctedDistId = "-1";
                // selctedCityId = "-1";
                //selctedWordId = "-1";
                selctedpanchyateId = "-1";
                selctedvillageId = "-1";
                // selctedtalukid = "-1";
                String name = data.getStringExtra("name");
                selctedtalukid = data.getStringExtra("name_id");
                txtTalukSpin.setText(name);
            }
            //txtWordSpin.requestFocus();
        } else if (requestCode == RES_PANCHYATE) {
            if (!data.getStringExtra("name_id").equalsIgnoreCase("")) {

               /* txtCitySpin.setText("Selct City");
                txtWordSpin.setText("Selct Word");

                txtTalukSpin.setText("Selct Taluk/Block");*/
                //txtGrampanchyateSpin.setText("Selct Panchyate");
                txtVillageSpin.setText("Select Village");
                // selctedDistId = "-1";
                // selctedCityId = "-1";
                // selctedWordId = "-1";
                // selctedpanchyateId = "-1";
                selctedvillageId = "-1";
                //selctedtalukid = "-1";
                String name = data.getStringExtra("name");
                selctedpanchyateId = data.getStringExtra("name_id");
                txtGrampanchyateSpin.setText(name);
            }
            //txtWordSpin.requestFocus();
        } else if (requestCode == RES_VILLAGE) {
            if (!data.getStringExtra("name_id").equalsIgnoreCase("")) {
                String name = data.getStringExtra("name");
                selctedvillageId = data.getStringExtra("name_id");
                txtVillageSpin.setText(name);
            }
            //txtWordSpin.requestFocus();
        } else if (requestCode == RES_BBMPZONE) {

            if (!data.getStringExtra("name_id").equalsIgnoreCase("")) {

                //txtGrampanchyateSpin.setText("Select Panchyate");
                txtBbmpwordSpin.setText("Select BBMP Ward");
                // selctedDistId = "-1";
                // selctedCityId = "-1";
                //selctedWordId = "-1";
                selctedbbmpwordId = "-1";
                // selctedvillageId = "-1";
                // selctedtalukid = "-1";
                String name = data.getStringExtra("name");
                selctedbbmpzoneid = data.getStringExtra("name_id");
                txtBbmpzone.setText(name);
            }
            //txtWordSpin.requestFocus();
        } else if (requestCode == RES_BBMPWARD) {
            if (!data.getStringExtra("name_id").equalsIgnoreCase("")) {
                String name = data.getStringExtra("name");
                selctedbbmpwordId = data.getStringExtra("name_id");
                txtBbmpwordSpin.setText(name);
            }
            //txtWordSpin.requestFocus();
        }
    }

    @Override
    public void onItemChecked(View v, int position, ModelSymptomGVT item, ArrayList<ModelSymptomGVT> listString, boolean isChecked) {
        if (isChecked) {
           /* if (position == listString.size()-1 && item.getStrname().equals("Others")){
               // etView.setVisibility(View.VISIBLE);
            }*/
            addOption(item.getId());
        } else {

            removeOption(item.getId());
        }
    }

    private void removeOption(String item) {
        for (int i = 0; i < selectedString.size(); i++) {
            if (selectedString.get(i).equals(item)) {
                selectedString.remove(i);
            }
        }
    }

    private void addOption(String item) {
        selectedString.add(item);
    }

    @Override
    public void onFragmentInteraction(String uri) {
        if (daovvalue.equalsIgnoreCase("dao")) {
            startdate = uri;
            txtDOA.setText("" + AppConstants.dateFormatChangerGVT(uri));
            if (!startdate.equalsIgnoreCase("")) {
                enddate = AppConstants.addDay(startdate, 14);
                txtQurantinedate.setText("" + AppConstants.dateFormatChangerGVT(enddate));
            }

        } else if (daovvalue.equalsIgnoreCase("qao")) {
            enddate = uri;
            txtQurantinedate.setText("" + AppConstants.dateFormatChangerGVT(uri));

        } else if (daovvalue.equalsIgnoreCase("dfs")) {
            dateofsymtom = uri;
            txtDOA11.setText("" + AppConstants.dateFormatChangerGVT(uri));

        }

    }


    private void submitDatapatientself() {
        if (validation()) {


            showProgressDialogStatic();
            PatientListDataItem resPatientInfoupdate = new PatientListDataItem();
            if (key.equalsIgnoreCase("newpatient")) {
                String localid = UUID.randomUUID().toString().toUpperCase();
                resPatientInfoupdate.setLocalID(localid);

            } else {
                resPatientInfoupdate = resPatientInfo;
            }

            resPatientInfoupdate.setLatitude(mLocation.getLatitude());
            resPatientInfoupdate.setLongitude(mLocation.getLongitude());

            resPatientInfoupdate.setName(etCustomerName.getText().toString());
            resPatientInfoupdate.setMobileNo(etCustomerMobile.getText().toString());
            resPatientInfoupdate.setEmail(etCustomerEmail.getText().toString());
            resPatientInfoupdate.setAge(Integer.parseInt(etAge.getText().toString()));
            resPatientInfoupdate.setGenderCode(selectedgender);
            resPatientInfoupdate.setStartDateOfQuarantine(startdate);
            resPatientInfoupdate.setEndDateOfQuarantine(enddate);
            resPatientInfoupdate.setDateOfFirstSymptom(dateofsymtom);
            resPatientInfoupdate.setPortOfOrigin(etPoorigin.getText().toString());
            resPatientInfoupdate.setPortOfArrival(etPoarrival.getText().toString());
            resPatientInfoupdate.setHouseNo(etHouseno.getText().toString());
            resPatientInfoupdate.setBuilding(etBuilding.getText().toString());
            resPatientInfoupdate.setStreet(etStreet.getText().toString());


            resPatientInfoupdate.setHospitalized(isHospitalised);
            resPatientInfoupdate.setHisOfLabCaseConfirmed(isHistoryContactLab);
            resPatientInfoupdate.setRiskArea(selctedriskarea);
            resPatientInfoupdate.setAreaType(selectaretype);

            resPatientInfoupdate.setHavingTravelHistory(isForignTip);


            //info symtom
          /*  resPatientInfo.setFever(isfever);
            resPatientInfo.setCoughSourThroat(iscoughandsour);
            resPatientInfo.setBreathingProblem(isbreathing);
            resPatientInfo.setDiarrhea(isdiarria);
            resPatientInfo.setDiabetes(isdiabaties);
            resPatientInfo.setHypertension(ishypertense);
            resPatientInfo.setHeartIssue(isheartdisses);
            resPatientInfo.setHIV(ishiv);*/

            resPatientInfoupdate.setDistCode(Integer.parseInt(selctedDistId));
            resPatientInfoupdate.setCityCode(Integer.parseInt(selctedCityId));
            resPatientInfoupdate.setWardCode(Integer.parseInt(selctedWordId));
            resPatientInfoupdate.setTalukCode(Integer.parseInt(selctedtalukid));
            resPatientInfoupdate.setGramPanchayatCode(Long.parseLong(selctedpanchyateId));
            resPatientInfoupdate.setVillageCode(Long.parseLong(selctedvillageId));
            resPatientInfoupdate.setWardBBMPID(Integer.parseInt(selctedbbmpwordId));
            resPatientInfoupdate.setZoneBBMPID(Integer.parseInt(selctedbbmpzoneid));

            resPatientInfoupdate.setAdditionalInfo("Android");
            String s_symptom = TextUtils.join(",", selectedString);
            resPatientInfoupdate.setSymptoms(s_symptom);
            if (key.equalsIgnoreCase("newpatient")) {
                resPatientInfoupdate.setProfileCreatedBy(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
                resPatientInfoupdate.setProfileUpdated(false);
                resPatientInfoupdate.setProfileUpdatedBy(0);
            } else {
                if (resPatientInfo != null) {
                    resPatientInfoupdate.setProfileUpdatedBy(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
                    resPatientInfoupdate.setProfileUpdated(true);
                }
            }

            if (PreferenceStore.getPrefernceHelperInstace().getFlag(YelligoApplication.getContext(), PreferenceStore.PERSIONTYPE)) {
                resPatientInfoupdate.setPatientQuarantineStatus(1);
            } else {
                resPatientInfoupdate.setPatientQuarantineStatus(2);
            }


            resPatientInfoupdate.setSyncstatus(false);

            //String s=new Gson().toJson(resPatientInfoupdate);
            //Log.e("ttttttt----",s);

            getHwPatientinfoRepository().insert(resPatientInfoupdate);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                    hideProgressDialogStatic();

                    showDialogAlert("Successfully added", true);

                }
            }, 1000);

        }
    }

    private void submitDatapatientfamily() {
        if (validation()) {


            showProgressDialogStatic();
            PatientFamilyDetailsItem resPatientfamilyInfoupdate = new PatientFamilyDetailsItem();
            if (key.equalsIgnoreCase("newpatient")) {
                String localid = UUID.randomUUID().toString().toUpperCase();
                resPatientfamilyInfoupdate.setFamilyLocalID(localid);

            } else {
                resPatientfamilyInfoupdate = resPatientFamilyInfo;
            }
            resPatientfamilyInfoupdate.setCitizenID(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
            resPatientfamilyInfoupdate.setCitizenIDLocalId(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.CITIZEN_LOCALID));
            resPatientfamilyInfoupdate.setLatitude(mLocation.getLatitude());
            resPatientfamilyInfoupdate.setLongitude(mLocation.getLongitude());

            resPatientfamilyInfoupdate.setName(etCustomerName.getText().toString());
            resPatientfamilyInfoupdate.setMobileNo(etCustomerMobile.getText().toString());
            resPatientfamilyInfoupdate.setEmail(etCustomerEmail.getText().toString());
            resPatientfamilyInfoupdate.setAge(Integer.parseInt(etAge.getText().toString()));
            resPatientfamilyInfoupdate.setGenderCode(selectedgender);
            resPatientfamilyInfoupdate.setRelationShipCode(selectedrelation);//
            resPatientfamilyInfoupdate.setStartDateOfQuarantine(startdate);
            resPatientfamilyInfoupdate.setEndDateOfQuarantine(enddate);
            resPatientfamilyInfoupdate.setDateOfFirstSymptom(dateofsymtom);
            resPatientfamilyInfoupdate.setPortOfOrigin(etPoorigin.getText().toString());
            resPatientfamilyInfoupdate.setPortOfArrival(etPoarrival.getText().toString());

            resPatientfamilyInfoupdate.setHavingTravelHistory(isForignTip);

            resPatientfamilyInfoupdate.setHospitalized(isHospitalised);
            resPatientfamilyInfoupdate.setHisOfLabCaseConfirmed(isHistoryContactLab);
            resPatientfamilyInfoupdate.setRiskArea(selctedriskarea);
            //resPatientfamilyInfoupdate.setAreaType(selectaretype);

           /* resPatientInfoupdate.setHouseNo(etHouseno.getText().toString());
            resPatientInfoupdate.setBuilding(etBuilding.getText().toString());
            resPatientInfoupdate.setStreet(etStreet.getText().toString());*/

            /*resPatientInfoupdate.setDistCode(Integer.parseInt(selctedDistId));
            resPatientInfoupdate.setCityCode(Integer.parseInt(selctedCityId));
            resPatientInfoupdate.setWardCode(Integer.parseInt(selctedWordId));
            resPatientInfoupdate.setTalukCode(Integer.parseInt(selctedtalukid));
            resPatientInfoupdate.setGramPanchayatCode(Integer.parseInt(selctedpanchyateId));
            resPatientInfoupdate.setVillageCode(Integer.parseInt(selctedvillageId));*/


            resPatientfamilyInfoupdate.setAdditionalInfo("Android");
            String s_symptom = TextUtils.join(",", selectedString);
            resPatientfamilyInfoupdate.setSymptoms(s_symptom);
            if (key.equalsIgnoreCase("newpatient")) {
                resPatientfamilyInfoupdate.setProfileCreatedBy(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));//TODO USER ID DYNAMIC
                resPatientfamilyInfoupdate.setProfileUpdated(false);
                resPatientfamilyInfoupdate.setProfileUpdatedBy(0);
            } else {
                if (resPatientFamilyInfo != null) {
                    resPatientfamilyInfoupdate.setProfileUpdatedBy(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));//TODO USER ID DYNAMIC
                    resPatientfamilyInfoupdate.setProfileUpdated(true);
                }
            }

            if (PreferenceStore.getPrefernceHelperInstace().getFlag(YelligoApplication.getContext(), PreferenceStore.PERSIONTYPE)) {
                resPatientfamilyInfoupdate.setPatientQuarantineStatus(1);
            } else {
                resPatientfamilyInfoupdate.setPatientQuarantineStatus(2);
            }


            resPatientfamilyInfoupdate.setSyncstatus(false);

            //String s=new Gson().toJson(resPatientInfoupdate);
            //Log.e("ttttttt----",s);

            getHwPatientFamilyinfoRepository().insertitem(resPatientfamilyInfoupdate);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                    hideProgressDialogStatic();

                    showDialogAlert("Successfully added", true);

                }
            }, 1000);

        }
    }

    private void showDialogAlert(String message, boolean b) {
        if (!isFinishing()) {
            CustomDialogGeneric dialog = new CustomDialogGeneric(AddnewPatientActivity.this, "",
                    new CustomDialogGeneric.OnButtonClickListener() {
                        @Override
                        public void onLeftButtonClick(CustomDialogGeneric dialog) {
                            dialog.dismiss();
                            if (b) {
                                if (keytype.equalsIgnoreCase("self")) {
                                    ApiBackGround apiBackGround = new ApiBackGround(YelligoApplication.getContext());
//                                    apiBackGround.makePatientAdded();
                                    apiBackGround.makesyncCall();
                                } else if (keytype.equalsIgnoreCase("family")) {
                                    ApiBackGround apiBackGround = new ApiBackGround(YelligoApplication.getContext());
                                    apiBackGround.makesyncCall();
                                }
                                finish();
                            }
                            //dialog.dismiss();
                        }

                        @Override
                        public void onRightButtonClick(CustomDialogGeneric dialog, String notes) {
                            dialog.dismiss();

                        }


                    });
            dialog.setCancelable(false);
            dialog.setRightButtonText("Retry");
            dialog.setRightButtonVisibility(View.GONE);
            dialog.setLeftButtonVisibility(View.VISIBLE);
            dialog.setLeftButtonText("OK");
            dialog.setDialogType(CustomDialogGeneric.TYPE_ALERT);
            dialog.setDescription("" + message);
            dialog.show();
        }
    }

    private boolean validation() {
        if (mLocation == null) {
            Toast.makeText(YelligoApplication.getContext(), "Please try again later", Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (mLocation.getLongitude() == 0.0) {
                Toast.makeText(YelligoApplication.getContext(), "Please try again later", Toast.LENGTH_LONG).show();
                return false;
            }

        }



        if (keytype.equalsIgnoreCase("family")) {
            if (selectedrelation == 0) {
                Toast.makeText(YelligoApplication.getContext(), "Please select relation", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (selectedgender == 0) {
            Toast.makeText(YelligoApplication.getContext(), "Please select gender", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(etAge.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Age", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!TextUtils.isDigitsOnly(etAge.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Age", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(etCustomerMobile.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Mobile Number", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!etCustomerMobile.getText().toString().equalsIgnoreCase("")) {
            if (getHwPatientinfoRepository().checkIsExist(etCustomerMobile.getText().toString()) != null) {
                showDialogAlert("This mobile number already exist");
                return false;
            }else if (getHwPatientFamilyinfoRepository().checkIsExist(etCustomerMobile.getText().toString()) != null) {
                showDialogAlert("This mobile number already exist");
                return false;
            }


        }
        if (etCustomerMobile.getText().toString().length() != 10) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter valid Mobile Number", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!TextUtils.isDigitsOnly(etCustomerMobile.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Mobile Number", Toast.LENGTH_LONG).show();
            return false;
        }
        if (startdate.equalsIgnoreCase("")) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter start date", Toast.LENGTH_LONG).show();
            return false;
        }

        if (PreferenceStore.getPrefernceHelperInstace().getFlag(YelligoApplication.getContext(), PreferenceStore.PERSIONTYPE)) {

            /*if (startdate.equalsIgnoreCase("")) {
                Toast.makeText(YelligoApplication.getContext(), "Please enter start date", Toast.LENGTH_LONG).show();
                return false;
            }*/
            if (enddate.equalsIgnoreCase("")) {
                Toast.makeText(YelligoApplication.getContext(), "Please enter end date", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (isForignTip){
            if (TextUtils.isEmpty(etPoorigin.getText().toString())) {
                Toast.makeText(YelligoApplication.getContext(), "Please enter Port of Origin", Toast.LENGTH_LONG).show();
                return false;
            }

            if (TextUtils.isEmpty(etPoarrival.getText().toString())) {
                Toast.makeText(YelligoApplication.getContext(), "Please enter Port Of Arrival", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (keytype.equalsIgnoreCase("self")) {
            if (selectaretype == 0) {
                Toast.makeText(YelligoApplication.getContext(), "Please select Location type", Toast.LENGTH_LONG).show();
                return false;
            }

            if (selectaretype == 1) {
                if (selctedDistId.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select District", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (selctedCityId.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select City", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (selctedWordId.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select Word", Toast.LENGTH_LONG).show();
                    return false;
                }


            } else if (selectaretype == 2) {
                if (selctedDistId.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select District", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (selctedtalukid.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select Taluk", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (selctedpanchyateId.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select Gram Panchyate", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (selctedvillageId.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select village", Toast.LENGTH_LONG).show();
                    return false;
                }
            }else if (selectaretype==3){
                if (selctedDistId.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select District", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (selctedbbmpzoneid.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select BBMP Zone", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (selctedbbmpwordId.equalsIgnoreCase("-1")) {
                    Toast.makeText(YelligoApplication.getContext(), "Please select BBMP Ward", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }



       /* if (district_code == 0) {
            Toast.makeText(YelligoApplication.getContext(), "Please select District", Toast.LENGTH_LONG).show();
            return false;
        }
        if (taluk_code == 0) {
            Toast.makeText(YelligoApplication.getContext(), "Please select Taluka", Toast.LENGTH_LONG).show();
            return false;
        }*/

       /* if (TextUtils.isEmpty(etPoorigin.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Port of Origin", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(etPoarrival.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Port Of Arrival", Toast.LENGTH_LONG).show();
            return false;
        }*/

        /*if (TextUtils.isEmpty(etStreet.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Street Name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(etBuilding.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Building Name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(etHouseno.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter House Number", Toast.LENGTH_LONG).show();
            return false;
        }*/

        /*if (TextUtils.isEmpty(etCity.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter City Name", Toast.LENGTH_LONG).show();
            return false;
        }*/

        return true;
    }

    /*Permission checker*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 199) {
            int flag = 0;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {
                    flag = -1;
                    break;
                }
            }
            if (flag == -1)
                new AlertDialog.Builder(this)
                        .setTitle("Permissions needed.")
                        .setMessage("Please allow "
                                + getResources().getString(R.string.app_name)
                                + " to access location, storage, phone call and camera.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .create()
                        .show();

            if (flag == 0) {

                getCurrentLOc();
                //continueLoading(); TODO AFTER SUCCESS
            }
        }
    }

    public void checkPermission() {
        int myPermFlag = 0;
        for (String myPermission : myPermissionsForLoac) {
            if ((ActivityCompat.checkSelfPermission(this, myPermission) != PackageManager.PERMISSION_GRANTED)) {
                myPermFlag = 1;
                break;
            }
        }
        switch (myPermFlag) {
            case 0:
                //continueLoading();TODO IF PERMISSION SUCCESS
                getCurrentLOc();
                break;
            case 1:
                getCommonApi().checkPermissions(myPermissionsForLoac);
                break;
        }
    }

    private void getCurrentLOc() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(YelligoApplication.getContext());
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
            }
        };

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        try {
            mFusedLocationClient
                    .getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLocation = task.getResult();
                                PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_LATITUDE, mLocation.getLatitude() + "");
                                PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_LONGITUDE, mLocation.getLongitude() + "");
                                Log.d(TAG, "LocationWORK : " + mLocation.getLongitude() + "----" + mLocation.getLatitude() + "---" + AppConstants.getCurrentDateTime());


                                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                            } else {
                                Log.w(TAG, "Failed to get location.");
                            }
                        }
                    });
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }

        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, null);
        } catch (SecurityException unlikely) {
            //Utils.setRequestingLocationUpdates(this, false);
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
			/*} else {
				Log.d(TAG, "Time up to get location. Your time is : " + DEFAULT_START_TIME + " to " + DEFAULT_END_TIME);
			}*/
    }

    public void maketosetAllValue() {
        etCustomerName.setEnabled(true);
        etCustomerMobile.setEnabled(true);
        etCustomerEmail.setEnabled(true);
        txtDOA.setEnabled(true);
        txtQurantinedate.setEnabled(true);
        etPoorigin.setEnabled(true);
        etPoarrival.setEnabled(true);
        etHouseno.setEnabled(true);
        etBuilding.setEnabled(true);
        etStreet.setEnabled(true);
        etAge.setEnabled(true);
        spGender.setEnabled(true);

    }

    public void setInfoMainpatient() {
        int ciD = PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID);
        resPatientInfo = getHwPatientinfoRepository().getPatientInfo(ciD);

        if (resPatientInfo != null) {

            spAreatype.setSelection(resPatientInfo.getAreaType());

            selectaretype = resPatientInfo.getAreaType();

            if (resPatientInfo.getName() != null) {
                etCustomerName.setText("" + resPatientInfo.getName());
                etCustomerName.setEnabled(false);
            }
            if (resPatientInfo.getMobileNo() != null) {
                etCustomerMobile.setText("" + resPatientInfo.getMobileNo());
            }


            if (resPatientInfo.getEmail() != null) {
                etCustomerEmail.setText("" + resPatientInfo.getEmail());
            }
            if (resPatientInfo.getStartDateOfQuarantine() != null) {
                startdate = resPatientInfo.getStartDateOfQuarantine();
                txtDOA.setText("" + AppConstants.dateFormatChangerGVT(resPatientInfo.getStartDateOfQuarantine()));
            }
            if (resPatientInfo.getEndDateOfQuarantine() != null) {
                enddate = resPatientInfo.getEndDateOfQuarantine();
                txtQurantinedate.setText("" + AppConstants.dateFormatChangerGVT(resPatientInfo.getEndDateOfQuarantine()));
            }
            if (resPatientInfo.getPortOfOrigin() != null) {
                etPoorigin.setText("" + resPatientInfo.getPortOfOrigin());
            }
            if (resPatientInfo.getPortOfArrival() != null) {
                etPoarrival.setText("" + resPatientInfo.getPortOfArrival());
            }
            if (resPatientInfo.getHouseNo() != null) {
                etHouseno.setText("" + resPatientInfo.getHouseNo());
            }
            if (resPatientInfo.getBuilding() != null) {
                etBuilding.setText("" + resPatientInfo.getBuilding());
            }
            if (resPatientInfo.getStreet() != null) {
                etStreet.setText("" + resPatientInfo.getStreet());
            }


            if (resPatientInfo.getAge() != 0) {
                etAge.setText("" + resPatientInfo.getAge());
            }


            if (resPatientInfo.getGenderCode() != -1) {
                spGender.setSelection(resPatientInfo.getGenderCode());
            }

            if(resPatientInfo.getDateOfFirstSymptom()!=null){
                if (!resPatientInfo.getDateOfFirstSymptom().equalsIgnoreCase("")){
                    dateofsymtom = resPatientInfo.getDateOfFirstSymptom();
                    txtDOA11.setText("" + AppConstants.dateFormatChangerGVT(resPatientInfo.getDateOfFirstSymptom()));
                }
            }

            if (resPatientInfo.isHospitalized()){
                chkBox13.setChecked(true);
            }
            if (resPatientInfo.isHisOfLabCaseConfirmed()){
                chkBox12.setChecked(true);
            }

            if (!resPatientInfo.getSymptoms().equalsIgnoreCase("")){
                selectedString= new ArrayList<>( Arrays.asList(resPatientInfo.getSymptoms().split(",")));
                adapter.setcheckitem(resPatientInfo.getSymptoms());
//                setinlist();
            }

            if (resPatientInfo.isHavingTravelHistory()){
                isForignTip=resPatientInfo.isHavingTravelHistory();
                simpleCheckedTextView.setChecked(isForignTip);
                llpoarrival.setVisibility(View.VISIBLE);
                llpoorigin.setVisibility(View.VISIBLE);
                etPoorigin.setText(""+resPatientInfo.getPortOfOrigin());
                etPoarrival.setText(""+resPatientInfo.getPortOfArrival());
            }






            selctedriskarea = resPatientInfo.getRiskArea();
            if (resPatientInfo.getRiskArea() == 1) {
                radioButton.setChecked(true);

            }
            if (resPatientInfo.getRiskArea() == 2) {
                radioButton2.setChecked(true);

            }
            if (resPatientInfo.getRiskArea() == 0) {
                radioButton.setChecked(false);
                radioButton2.setChecked(false);

            }

            setAddress();

            if (resPatientInfo.isProfileUpdated()) {
                tvLogout.setVisibility(View.GONE);
            } else {
                tvLogout.setVisibility(View.VISIBLE);
            }


           /* if (resPatientInfo.getCityCode()!=-1){

            }else {
                spAreatype.setSelection(1);
            }*/


//            if (resPatientInfo.get)
        }

    }

    public void setInfoMainpatientFamilyDetails() {

        resPatientFamilyInfo = getHwPatientFamilyinfoRepository().getPatientFamilyInfoByFamilyLocalId(keytypefamilyloclId);

        if (resPatientFamilyInfo != null) {

            /*spAreatype.setSelection(1);


            selectaretype = 1;*/

            if (resPatientFamilyInfo.getName() != null) {
                etCustomerName.setText("" + resPatientFamilyInfo.getName());
                etCustomerName.setEnabled(false);
            }
            if (resPatientFamilyInfo.getMobileNo() != null) {
                etCustomerMobile.setText("" + resPatientFamilyInfo.getMobileNo());
            }

            if (resPatientFamilyInfo.getEmail() != null) {
                etCustomerEmail.setText("" + resPatientFamilyInfo.getEmail());
            }
            if (resPatientFamilyInfo.getStartDateOfQuarantine() != null) {
                startdate = resPatientFamilyInfo.getStartDateOfQuarantine();
                txtDOA.setText("" + AppConstants.dateFormatChangerGVT(resPatientFamilyInfo.getStartDateOfQuarantine()));
            }
            if (resPatientFamilyInfo.getEndDateOfQuarantine() != null) {
                enddate = resPatientFamilyInfo.getEndDateOfQuarantine();
                txtQurantinedate.setText("" + AppConstants.dateFormatChangerGVT(resPatientFamilyInfo.getEndDateOfQuarantine()));
            }
            if (resPatientFamilyInfo.getPortOfOrigin() != null) {
                etPoorigin.setText("" + resPatientFamilyInfo.getPortOfOrigin());
            }
            if (resPatientFamilyInfo.getPortOfArrival() != null) {
                etPoarrival.setText("" + resPatientFamilyInfo.getPortOfArrival());
            }

//            TODO IF REQUIRED

            /*if (resPatientInfo.getHouseNo() != null) {
                etHouseno.setText("" + resPatientInfo.getHouseNo());
            }
            if (resPatientInfo.getBuilding() != null) {
                etBuilding.setText("" + resPatientInfo.getBuilding());
            }
            if (resPatientInfo.getStreet() != null) {
                etStreet.setText("" + resPatientInfo.getStreet());
            }*/
            if (resPatientFamilyInfo.isHavingTravelHistory()){
                isForignTip=resPatientFamilyInfo.isHavingTravelHistory();
                simpleCheckedTextView.setChecked(isForignTip);
                llpoarrival.setVisibility(View.VISIBLE);
                llpoorigin.setVisibility(View.VISIBLE);
                etPoorigin.setText(""+resPatientFamilyInfo.getPortOfOrigin());
                etPoarrival.setText(""+resPatientFamilyInfo.getPortOfArrival());
            }

            if (resPatientFamilyInfo.getAge() != 0) {
                etAge.setText("" + resPatientFamilyInfo.getAge());
            }


            if (resPatientFamilyInfo.getGenderCode() != -1) {
                spGender.setSelection(resPatientFamilyInfo.getGenderCode());
            }
            if (resPatientFamilyInfo.getRelationShipCode() != -1) {
                spRelation.setSelection(resPatientFamilyInfo.getRelationShipCode());
            }

            if (resPatientFamilyInfo.isProfileUpdated()) {
                tvLogout.setVisibility(View.GONE);
            } else {
                tvLogout.setVisibility(View.VISIBLE);
            }

            if(resPatientFamilyInfo.getDateOfFirstSymptom()!=null){
                if (!resPatientFamilyInfo.getDateOfFirstSymptom().equalsIgnoreCase("")){
                    dateofsymtom = resPatientFamilyInfo.getDateOfFirstSymptom();
                    txtDOA11.setText("" + AppConstants.dateFormatChangerGVT(resPatientFamilyInfo.getDateOfFirstSymptom()));
                }
            }

            if (resPatientFamilyInfo.isHospitalized()){
                chkBox13.setChecked(true);
            }
            if (resPatientFamilyInfo.isHisOfLabCaseConfirmed()){
                chkBox12.setChecked(true);
            }

            if (!resPatientFamilyInfo.getSymptoms().equalsIgnoreCase("")){
                selectedString= new ArrayList<>( Arrays.asList(resPatientFamilyInfo.getSymptoms().split(",")));
                adapter.setcheckitem(resPatientFamilyInfo.getSymptoms());
//                setinlist();
            }






            selctedriskarea = resPatientFamilyInfo.getRiskArea();
            if (resPatientFamilyInfo.getRiskArea() == 1) {
                radioButton.setChecked(true);

            }
            if (resPatientFamilyInfo.getRiskArea() == 2) {
                radioButton2.setChecked(true);

            }
            if (resPatientFamilyInfo.getRiskArea() == 0) {
                radioButton.setChecked(false);
                radioButton2.setChecked(false);

            }

            //TODO CHECK ADDRESS REQUIRED
            //setAddress();

        }

    }

    private void setAddress() {
        if (resPatientInfo != null) {
            if (resPatientInfo.getDistCode() != -1) {
                ResStaticMasterDistricDB resStaticMasterDistricDB = getAddressUrbaninfoRepository().getDistricNameByDistID(resPatientInfo.getDistCode());
                if (resStaticMasterDistricDB != null) {
                    txtDistSpin.setText(resStaticMasterDistricDB.getDist_name());
                    selctedDistId = "" + resPatientInfo.getDistCode();
                }
            }


            if (resPatientInfo.getCityCode() != -1) {
                ResStaticMasterDistricDB resStaticMasterDistricDB = getAddressUrbaninfoRepository().getCityByCityID(resPatientInfo.getCityCode());
                if (resStaticMasterDistricDB != null) {
                    txtCitySpin.setText(resStaticMasterDistricDB.getTown_name());
                    selctedCityId = "" + resPatientInfo.getCityCode();
                }
            }

            if (resPatientInfo.getWardCode() != -1) {
                ResStaticMasterDistricDB resStaticMasterDistricDB = getAddressUrbaninfoRepository().getWordBiWID(resPatientInfo.getWardCode());
                if (resStaticMasterDistricDB != null) {
                    txtWordSpin.setText(resStaticMasterDistricDB.getWord_name());
                    selctedWordId = "" + resPatientInfo.getWardCode();
                }
            }
            if (resPatientInfo.getTalukCode() != -1) {
                String tName = getCommonApi().getTalukName(getApplicationContext(), "" + resPatientInfo.getTalukCode(), "");
                if (!tName.equalsIgnoreCase("")) {
                    txtTalukSpin.setText(tName);
                    selctedtalukid = "" + resPatientInfo.getTalukCode();
                }
            }

            if (resPatientInfo.getGramPanchayatCode() != -1) {
                String tName = getCommonApi().getPanchyateName(getApplicationContext(), "" + resPatientInfo.getGramPanchayatCode());
                if (!tName.equalsIgnoreCase("")) {
                    txtGrampanchyateSpin.setText(tName);
                    selctedpanchyateId = "" + resPatientInfo.getGramPanchayatCode();
                }
            }

            if (resPatientInfo.getVillageCode() != -1) {
                String tName = getVillageinfoRepository().getVillageName("" + resPatientInfo.getVillageCode());
                if (tName != null) {
                    txtVillageSpin.setText(tName);
                    selctedvillageId = "" + resPatientInfo.getVillageCode();
                }
            }

            if (resPatientInfo.getZoneBBMPID() != -1) {
                String tName = getCommonApi().getBBMPZONEName(getApplicationContext(), "" + resPatientInfo.getZoneBBMPID(), "");
                if (!tName.equalsIgnoreCase("")) {
                    txtBbmpzone.setText(tName);
                    selctedbbmpzoneid = "" + resPatientInfo.getZoneBBMPID();
                }
            }
            if (resPatientInfo.getWardBBMPID() != -1) {
                String tName = getCommonApi().getBBMPWARDName(getApplicationContext(), "" + resPatientInfo.getWardBBMPID(), "");
                if (!tName.equalsIgnoreCase("")) {
                    txtBbmpwordSpin.setText(tName);
                    selctedbbmpwordId = "" + resPatientInfo.getWardBBMPID();
                }
            }

        }
    }

    private void showDialogAlert(String message) {
        if (!isFinishing()) {
            CustomDialogGeneric dialog = new CustomDialogGeneric(AddnewPatientActivity.this, "",
                    new CustomDialogGeneric.OnButtonClickListener() {
                        @Override
                        public void onLeftButtonClick(CustomDialogGeneric dialog) {
                            dialog.dismiss();
                            // Toast.makeText(getActivity(),"Customer created successfully",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                        @Override
                        public void onRightButtonClick(CustomDialogGeneric dialog, String notes) {
                            dialog.dismiss();

                        }


                    });
            dialog.setCancelable(false);
            dialog.setRightButtonText("Retry");
            dialog.setRightButtonVisibility(View.GONE);
            dialog.setLeftButtonVisibility(View.VISIBLE);
            dialog.setLeftButtonText("OK");
            dialog.setDialogType(CustomDialogGeneric.TYPE_ALERT);
            dialog.setDescription("" + message);
            dialog.show();
        }
    }


    /*@OnClick(R.id.simpleCheckedTextView)
    public void onViewClicked() {
    }*/
}
