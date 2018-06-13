package com.samriddhi_android.www.camdetector;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class TTS extends AppCompatActivity{
    TextView text;
    Button b1, h1, e1;
    TextToSpeech tt;
    String mydata="Subash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tts);
        mydata=getIntent().getExtras().getString("data");
        text=findViewById(R.id.inputtext);
        b1=findViewById(R.id.btnspeak);
        h1=findViewById(R.id.btnhome);
        e1=findViewById(R.id.btnexit);
        text.setText(mydata);

        tt=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!= TextToSpeech.ERROR)
                    tt.setLanguage(Locale.UK);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tt.speak(mydata, TextToSpeech.QUEUE_FLUSH,null);

            }
        });

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TTS.this, MainActivity.class);
                startActivity(i);

            }
        });

        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.exit(0);
            }
        });
    }

    public void onPause(){
        if(tt!=null){
            tt.stop();
            tt.shutdown();
        }
        super.onPause();
    }

}
