package com.akhiabidashik.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
Button buttonone,buttontwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
buttonone=findViewById(R.id.buttonone);
buttontwo=findViewById(R.id.buttontwo);
buttonone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intentone=new Intent(SecondActivity.this,ThirdActivity.class);
        startActivity(intentone);
    }
});
buttontwo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intenttwo=new Intent(SecondActivity.this,FourthActivity.class);
        startActivity(intenttwo);
    }
});



    }
    public void onBackPressed(){

AlertDialog.Builder builder=new AlertDialog.Builder(this);
builder.setMessage("Do you really want to exit?")
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
AlertDialog alertDialog=builder.create();
alertDialog.show();




    }
}