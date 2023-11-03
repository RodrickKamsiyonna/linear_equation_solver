package com.digital.linearequationsolver;

import android.content.Intent;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {
    private double[][] dob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler  han  = new Handler();
        han.post(new Runnable() {
            @Override
            public void run() {
                MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                    }
                });
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        LinearLayout lay = findViewById(R.id.input_holder);
        for(int i =0; i<2;i++) {
            LinearLayout lay1  = (LinearLayout) lay.getChildAt(i);
            TextView text1  =  (TextView)lay1.getChildAt(0);
            int j =  i+1;
            text1.setText("Eq" +j);
            EditText text = (EditText) lay1.getChildAt(1);
            text.setShowSoftInputOnFocus(false);
            if(i == 0){
                text.setHint(R.string.eq_hint);
            }
        }
    }

    public void onClickNumber(View view){
        if(getCurrentFocus() instanceof EditText) {
            EditText text = (EditText)getCurrentFocus();
            int  position=  text.getSelectionEnd();
            switch (view.getId()) {
                case R.id.one:
                    text.getText().insert(position,"1");
                    break;
                case R.id.two:
                    text.getText().insert(position,"2");
                    break;
                case R.id.three:
                    text.getText().insert(position,"3");
                    break;
                case R.id.four:
                    text.getText().insert(position,"4");
                    break;
                case R.id.five:
                    text.getText().insert(position,"5");
                    break;
                case R.id.six:
                    text.getText().insert(position,"6");
                    break;
                case R.id.seven:
                    text.getText().insert(position,"7");
                    break;
                case R.id.eight:
                    text.getText().insert(position,"8");
                    break;
                case R.id.nine:
                    text.getText().insert(position,"9");
                    break;
                case R.id.zero:
                    text.getText().insert(position,"0");
                    break;
                case R.id.back:
                    text.getText().insert(position,"(");
                    break;
                case R.id.forword:
                    text.getText().insert(position,")");
                    break;
                case R.id.plus:
                    text.getText().insert(position,"+");
                    break;
                case R.id.subtract:
                    text.getText().insert(position,"-");
                    break;
                case R.id.divide:
                    text.getText().insert(position,"/");
                    break;
                case R.id.multiply:
                    text.getText().insert(position,"*");
                    break;
                case R.id.floating_point:
                    text.getText().insert(position,".");
                    break;
                case R.id.camma:
                    text.getText().insert(position,",");
                    break;
                case R.id.Del:
                    if(position != 0) {
                        text.getText().delete(position - 1,position);
                    }
                    break;
                case R.id.equal:
                    if(Solve()){
                        Intent intent = new Intent(this,AnswerActivity.class);
                        Calculate  c =  new Calculate();
                        c.setRow(dob.length);
                        intent.putExtra(AnswerActivity.LINK,c.solver(dob));
                        startActivity(intent);
                    }
                    break;
            }
        }
    }
    public void addEquation(View view){
        Slide slideAnimation = new Slide(Gravity.END);
        slideAnimation.setDuration(500);
        LinearLayout lay = findViewById(R.id.input_holder);

        ViewGroup view1 = (ViewGroup) getLayoutInflater().inflate(R.layout.input_layout,lay,false);
        TextView  text  = (TextView) view1.getChildAt(0);
        int j =  lay.getChildCount() +1;
        text.setText("Eq"+j);
        EditText   text1 = (EditText)view1.getChildAt(1);
        text1.setShowSoftInputOnFocus(false);
        TransitionManager.beginDelayedTransition(lay,slideAnimation);
        lay.addView(view1);
        text1.requestFocus();

    }
    public void removeLayout(View view){
        Slide slideAnimation = new Slide(Gravity.END);
        slideAnimation.setDuration(500);

        LinearLayout lay = findViewById(R.id.input_holder);
        if(lay.getChildCount()>2) {
            TransitionManager.beginDelayedTransition((ViewGroup) lay.getParent(),slideAnimation);
            lay.removeViewAt(lay.getChildCount() - 1);
        }
    }
    public boolean Solve(){
        LinearLayout lay = findViewById(R.id.input_holder);
        double[][] values  = new double[lay.getChildCount()][lay.getChildCount()+1];
        for(int i =0; i<lay.getChildCount();i++) {
            LinearLayout lay1  = (LinearLayout) lay.getChildAt(i);
            EditText text = (EditText) lay1.getChildAt(1);
            String[] values2  = text.getText().toString().split(",");
            if(values2.length-1 != lay.getChildCount()){
                Toast.makeText(this,"Please complete the equation",Toast.LENGTH_SHORT).show();
                return false;
            }
            for(int j=0;j< values2.length;j++){
                double  v =  new Expression(values2[j]).calculate();
                if(Double.isNaN(v)){
                    Toast.makeText(this,"Please enter the correct text",Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    values[i][j] = v;
                }
            }
        }

        dob = values;
        return true;
    }
}