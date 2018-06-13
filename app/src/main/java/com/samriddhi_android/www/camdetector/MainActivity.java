package com.samriddhi_android.www.camdetector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String mytext;
    ImageView imv;
    Button detect, snap;
    Bitmap bmv;
    private int CODE=5;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detect=findViewById(R.id.detect);
        snap=findViewById(R.id.snap);
        tv=findViewById(R.id.text);
        imv=findViewById(R.id.image);
        snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Picture Snapped Successfully.", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,CODE);

            }
        });

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseVisionImage image=FirebaseVisionImage.fromBitmap(bmv);
                FirebaseVisionTextDetector detector= FirebaseVision.getInstance().getVisionTextDetector();
                detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        List<FirebaseVisionText.Block>mytexts=firebaseVisionText.getBlocks();
                        if(mytexts.size()>0){
                            String displaydata="";
                            for (FirebaseVisionText.Block myblock:firebaseVisionText.getBlocks()){
                                displaydata=displaydata+myblock.getText()+"\n";
                            }
                            mytext=displaydata;
                            Intent i=new Intent(MainActivity.this,TTS.class);
                            i.putExtra("data",mytext);
                            Toast.makeText(MainActivity.this, "Text Detected Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(i);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "No text detected.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE && resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            bmv=(Bitmap) bundle.get("data");
            imv.setImageBitmap(bmv);
        }
    }

}
