package com.example.aditya.rentacycle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.aditya.rentacycle.R.array.ride;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner;

private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mListener;
    private GoogleApiClient mGoogleApiClient;
    public  DatabaseReference mDatabase;
    public  DatabaseReference userDatabase;
    public DatabaseReference completeBase;
    private TextView textViewUserEmail;
    public Button requestButton;
    public TextView statusText;
    public  String Email;
   public static TextView history;
    public String pushKey;
    public TextView datet;
    public Button rideComplete;
    public   MyDbHandler dbHandler;
    public TextView dispText;
    public Button DeleteButton;
    private Context context;
    private TextView switchStatus;
    private Switch mySwitch;
public static boolean historyb = Boolean.parseBoolean(null);
    //private TimePicker timePicker1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        requestButton=(Button)findViewById(R.id.request_button);
        rideComplete=(Button)findViewById(R.id.rideComplete);
        rideComplete.setVisibility(View.INVISIBLE);
        statusText=(TextView)findViewById(R.id.cyclestatus);
       // history = (TextView) findViewById(R.id.textView5);
        dbHandler = new MyDbHandler(this, null, null, 1);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Cycle Request");
        completeBase = FirebaseDatabase.getInstance().getReference().child("Ride Completed");
dispText = (TextView)findViewById(R.id.dispText);
        DrawerLayout rlayout = (DrawerLayout) findViewById(R.id.activity_profile);
        printData();
DeleteButton= (Button) findViewById(R.id.DeleteButton);
        Resources res = getResources();
        String[] planets = res.getStringArray(ride);
//timePicker1.setVisibility(View.INVISIBLE);
datet=(TextView)findViewById(R.id.date);
        planets = res.getStringArray(ride);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(ProfileActivity.this,"you got an error",Toast.LENGTH_LONG).show();
                    }
                } )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


mAuth=FirebaseAuth.getInstance();
mListener=new FirebaseAuth.AuthStateListener(){
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser()==null){
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        }
    }
};



        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        FirebaseUser user = mAuth.getCurrentUser();
        String personName = user.getDisplayName();
        textViewUserEmail.setText("Welcome " + personName);
//check();
        rideComplete.setOnClickListener(this);
        requestButton.setOnClickListener(this);
rlayout.setOnClickListener(this);
        DeleteButton.setOnClickListener(this);


        mySwitch = (Switch) findViewById(R.id.switch1);

        mySwitch.setChecked(false);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    historyb=FALSE;
                }else{
                   historyb=TRUE;
                }

            }
        });

        if(mySwitch.isChecked()){
            historyb=FALSE;

        }
        else {                   historyb=TRUE;

        }


printData();
        Email= mAuth.getCurrentUser().getEmail();
    }

    public String id="f2016038@pilani.bits-pilani.ac.in";
    public void check() {
        String h=Email.substring(8);
        String g="@pilani.bits-pilani.ac.in";
        if (h.equals(g)){

        }
        else{

      logoute();
    }}
    boolean showingFirst = true;
    @Override
    public void onClick(View view) {
check();
        if(view== DeleteButton){

            AlertDialog diaBox = AskOption();
            diaBox.show();
        }
            if (view == requestButton) {
                if (showingFirst == true) {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                    Date currentLocalTime = cal.getTime();
                    DateFormat date = new SimpleDateFormat("dd/MM/YYYY  hh:mm:ss a");
                    date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                    String localTime = date.format(currentLocalTime);

                    statusText.setText("Your cycle is out");
                    datet.setText("You requested cycle on " + localTime);
                    DatabaseReference newPost = mDatabase.push();

                    newPost.child("uid").setValue(Email);
                    newPost.child("time").setValue(localTime);
                    pushKey = newPost.getKey();
                    requestButton.setText("Cancel Your request");
                    showingFirst = false;
                    rideComplete.setVisibility(View.INVISIBLE);

                   if(historyb==TRUE){
                    Product product = new Product("Cycle requested on " +localTime);
                    dbHandler.addProduct(product);
                    printData();}
                    else{

                    }
                    check();



                } else {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                    Date currentLocalTime = cal.getTime();
                    DateFormat date = new SimpleDateFormat("dd/MM/YYYY  hh:mm:ss a");
                    date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                    String localTime2 = date.format(currentLocalTime);
                    datet.setText("Last request cancelled on " + localTime2);
                    statusText.setText("No Cycle requested");
                    requestButton.setText("Request a cycle");
                    showingFirst = true;
                    mDatabase.child(pushKey).removeValue();
                    rideComplete.setVisibility(View.INVISIBLE);
                    if(historyb==TRUE){
                        Product product = new Product("request cancelled on " +localTime2);
                        dbHandler.addProduct(product);
                        printData();}
                    else{

                    }
                }
            }

            if (view == rideComplete) {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("dd/MM/YYYY  hh:mm:ss a");
                date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                String localTime2 = date.format(currentLocalTime);
                datet.setText("Last ride completed on " + localTime2);
                rideComplete.setVisibility(View.INVISIBLE);
                statusText.setText("No Cycle requested");
                requestButton.setText("Request a cycle");
                showingFirst = true;
                mDatabase.child(pushKey).removeValue();
                DatabaseReference newPost = completeBase.push();
                newPost.child("uid").setValue(Email);
                newPost.child("time").setValue(localTime2);
                pushKey = newPost.getKey();
                Product product = new Product("request cancelled on " +localTime2);
                dbHandler.addProduct(product);
                printData();
            }
      /*  } else {
            logout1();
            Toast.makeText(ProfileActivity.this,"Login through your BITS account only",Toast.LENGTH_LONG).show();
        }*/
    }
    private void logout1() {FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                      //  Toast.makeText(ProfileActivity.this,"you logged out",Toast.LENGTH_LONG).show();
                    }
                });
        mAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void logout() {FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    Toast.makeText(ProfileActivity.this,"you logged out",Toast.LENGTH_LONG).show();
                    }
                });
        mAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    private void logoute() {FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(ProfileActivity.this,"Login through your BITS mail only",Toast.LENGTH_LONG).show();
                    }
                });
        mAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
public String my="f2016038@pilani.bits-pilani.ac.in";
    @Override
    protected void onStart() {
        super.onStart();



        mAuth.addAuthStateListener(mListener);
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {

        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
           // finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
    public String time="a";
    public void save(View view){


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId()==R.id.action_add){
           logout();
       }

        if(item.getItemId()==R.id.action_about_developer){
            startActivity(new Intent(this, DeveloperActivity.class));
        }
        if(item.getItemId()==R.id.action_about_initiator){
            startActivity(new Intent(this, InitiatorActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    public void printData(){
        String dbString = dbHandler.databaseToString();
dispText.setText(dbString);
    }
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Sure to delete history ?")
                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dbHandler.deleteProduct();
                        printData();
                        Toast.makeText(ProfileActivity.this,"History Deleted",Toast.LENGTH_SHORT).show();

                        dialog.dismiss();

                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }


}