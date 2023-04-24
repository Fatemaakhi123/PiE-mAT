package com.akhiabidashik.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import static com.google.android.gms.ads.MobileAds.*;
public class ThirdActivity extends AppCompatActivity {
DatabaseReference databaseReferenceone;
private ListView listViewone;
AdView AdViewOne;
Context context;
private AutoCompleteTextView textSearchone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        if(!isConnected()){

            Toast toast= Toast.makeText(ThirdActivity.this,"No Internet Connection!!",Toast.LENGTH_LONG);

            View view= toast.getView();
            TextView text=view.findViewById(android.R.id.message);
            text.setTextColor(Color.RED);
            toast.show();

        }else
        {
            Toast toast= Toast.makeText(ThirdActivity.this,"Internet Connection On",Toast.LENGTH_LONG);

            View view= toast.getView();
            TextView text=view.findViewById(android.R.id.message);

            toast.show();
            databaseReferenceone= FirebaseDatabase.getInstance().getReference("pieunits");
            listViewone=findViewById(R.id.listViewone);
            textSearchone=findViewById(R.id.textSearchone);
            ValueEventListener eventone=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    populateSearchone(snapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            databaseReferenceone.addListenerForSingleValueEvent(eventone);
            AdViewOne=findViewById(R.id.adViewone);
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            AdRequest adRequest=new AdRequest.Builder().build();
            AdViewOne.loadAd(adRequest);
        }

    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return  connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void populateSearchone(DataSnapshot snapshot) {

        ArrayList<String> valuesone=new ArrayList<>();
        if(snapshot.exists()){
            for(DataSnapshot dataSnapshotone:snapshot.getChildren())
            {
                String valueone= dataSnapshotone.child("name").getValue(String.class);
                valuesone.add(valueone);
            }
            ArrayAdapter adapterone=new ArrayAdapter(this, android.R.layout.simple_list_item_1,valuesone);
            textSearchone.setAdapter(adapterone);
            textSearchone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String nameONE=textSearchone.getText().toString();
                    searchUserOne(nameONE);
                }
            });
        }else{
            Log.d("pieunits","No data available");
        }

    }

    private void searchUserOne(String nameONE) {

        Query queryONE=databaseReferenceone.orderByChild("name").equalTo(nameONE);
        queryONE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
ArrayList<String> listUserONE=new ArrayList<>();
for(DataSnapshot dataSnapshotone:snapshot.getChildren()){
    UserONE userONE=new UserONE(dataSnapshotone.child("name").getValue(String.class),dataSnapshotone.child("cgs").getValue(String.class),dataSnapshotone.child("mks").getValue(String.class),dataSnapshotone.child("fps").getValue(String.class),dataSnapshotone.child("description").getValue(String.class));
    listUserONE.add(userONE.getName()+"\n"+userONE.getCgs()+"\n"+userONE.getMks()+"\n"+userONE.getFps()+"\n"+userONE.getDescription()+"\n");


}
ArrayAdapter adapterONE=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,listUserONE);
               listViewone.setAdapter(adapterONE);
                }else
                {
                    Log.d("pieunits","No data available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    class UserONE{
        String name,cgs,mks,fps,description;

        public UserONE(String name, String cgs, String mks, String fps, String description) {
            this.name = name;
            this.cgs = cgs;
            this.mks = mks;
            this.fps = fps;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCgs() {
            return cgs;
        }

        public void setCgs(String cgs) {
            this.cgs = cgs;
        }

        public String getMks() {
            return mks;
        }

        public void setMks(String mks) {
            this.mks = mks;
        }

        public String getFps() {
            return fps;
        }

        public void setFps(String fps) {
            this.fps = fps;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }




}