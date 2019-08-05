package com.example.travelmantics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DealActivity extends AppCompatActivity {
    private FirebaseDatabase  mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtTitle;
    EditText txtDescription;
    EditText txtPrice;
    TravelDeal deal;
    ImageView imageView;
    public static final  int PICTURE_RESULT = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseUtility.openFbReference("traveldeals",this);
        mFirebaseDatabase = FirebaseUtility.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtility.mDatabaseReference;

        txtTitle       = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtPrice       = (EditText) findViewById(R.id.txtPrice);

        final Intent intent  = getIntent();
        TravelDeal deal      = (TravelDeal) intent.getSerializableExtra("Deal");
        if(deal==null){ deal = new TravelDeal();}

        this.deal = deal;

        txtTitle.setText(deal.getTitle());
        txtDescription.setText(deal.getDescription());
        txtPrice.setText(deal.getPrice());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id){

            case R.id.save_menu:
                saveDeal();
                clean();
                backToList();
                return true;

            case R.id.delete_menu:
                deleteDeal();
                backToList();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void saveDeal(){

        deal.setTitle(txtTitle.getText().toString());
        deal.setDescription(txtDescription.getText().toString());
        deal.setPrice(txtPrice.getText().toString());

        if(deal.getId()==null){
            mDatabaseReference.push().setValue(deal);
        }
        else {mDatabaseReference.child(deal.getId()).setValue(deal); }



    }
    private void deleteDeal(){

        if(deal==null){  return; }

        mDatabaseReference.child(deal.getId()).removeValue();
    }
    private void backToList(){

        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }
    private void clean(){

        txtTitle.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtTitle.requestFocus();
    }





}
