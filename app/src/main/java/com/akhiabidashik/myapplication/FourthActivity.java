package com.akhiabidashik.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
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

import static com.google.android.gms.ads.MobileAds.initialize;

public class FourthActivity extends AppCompatActivity {
    DatabaseReference databaseReferenceTwo;
    private ListView listViewTwo;
    private AutoCompleteTextView textSearchTwo;
    AdView AdViewtwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        databaseReferenceTwo= FirebaseDatabase.getInstance().getReference("pierelationships");
        listViewTwo=findViewById(R.id.listViewTwo);
        textSearchTwo=findViewById(R.id.textSearchTwo);
        AdViewtwo=findViewById(R.id.adViewtwo);
        ValueEventListener eventTwo=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               populateSearchTwo(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReferenceTwo.addListenerForSingleValueEvent(eventTwo);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest=new AdRequest.Builder().build();
        AdViewtwo.loadAd(adRequest);
    }

    private void populateSearchTwo(DataSnapshot snapshot) {

        ArrayList<String> valuesTwo=new ArrayList<>();
        if(snapshot.exists())
        {
            for(DataSnapshot dataSnapshotTwo:snapshot.getChildren()){
                String valueTwo=dataSnapshotTwo.child("name").getValue(String.class);
                valuesTwo.add(valueTwo);
            }
            ArrayAdapter adapterTwo=new ArrayAdapter(this, android.R.layout.simple_list_item_1,valuesTwo);
            textSearchTwo.setAdapter(adapterTwo);
            textSearchTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String nameTWO=textSearchTwo.getText().toString();
                    searchUserTwo(nameTWO);
                }
            });
        }else
        {
            Log.d("pierelationships","No data Found");
        }
    }

    private void searchUserTwo(String nameTWO) {
        Query queryTWO=databaseReferenceTwo.orderByChild("name").equalTo(nameTWO);
        queryTWO.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<String> listUserTWO=new ArrayList<>();
                    for(DataSnapshot dataSnapshotTwo:snapshot.getChildren()){
                       UserTWO userTWO=new UserTWO(dataSnapshotTwo.child("name").getValue(String.class),dataSnapshotTwo.child("value").getValue(String.class));
listUserTWO.add(userTWO.getName()+"\n"+userTWO.getValue()+"\n");
                    }
                    ArrayAdapter adapterTWO=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,listUserTWO);
                    listViewTwo.setAdapter(adapterTWO);
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
    class UserTWO{
        String name,value;

        public UserTWO(String name, String value) {
            this.name = name;
            this.value = value;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setCgs(String value) {
            this.value = value;
        }


    }
}
