package com.digital.linearequationsolver;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {
    public static String LINK ="XYZ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();
        double[] values = (double[])intent.getDoubleArrayExtra(LINK);
        LinearLayout lay =  findViewById(R.id.answer_layout);
        for(int i =0;i< values.length;i++){
            ViewGroup view1 = (ViewGroup) getLayoutInflater().inflate(R.layout.answer_layout,lay,false);
            TextView text  = (TextView) view1.getChildAt(0);
            int j =  i+1;
            text.setText("x"+j);
            TextView text1  = (TextView) view1.getChildAt(1);
            if(Double.isNaN(values[i])){
            text1.setText("    ");
            }else {
                text1.setText(String.format("%.5f", values[i]));
            }
            lay.addView(view1);
        }
    }
}