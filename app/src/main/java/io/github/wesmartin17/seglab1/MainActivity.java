package io.github.wesmartin17.seglab1;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    LinearLayout layoutStep1,layoutStep2;

    Button newButton,removeButton,nextButton,prevButton;
    LinearLayout layout;

    private int counter = 1;
    private int step = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutStep1 = (LinearLayout)findViewById(R.id.layoutStepOne);
        layoutStep2 = (LinearLayout)findViewById(R.id.layoutStep2);

        layoutStep1.setVisibility(View.VISIBLE);
        layoutStep2.setVisibility(View.GONE);


        newButton = (Button)findViewById(R.id.btnAdd);
        removeButton = (Button)findViewById(R.id.btnRemove);
        nextButton = (Button)findViewById(R.id.btnNext);
        prevButton = (Button)findViewById(R.id.btnPrev);
        prevButton.setVisibility(View.GONE);
        layout = (LinearLayout)findViewById(R.id.nameLayout);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(toolbar);

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDark),PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        };

        newButton.setOnTouchListener(touchListener);
        removeButton.setOnTouchListener(touchListener);
        nextButton.setOnTouchListener(touchListener);
        prevButton.setOnTouchListener(touchListener);


        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(counter < 12) {
                counter++;
                removeButton.setEnabled(true);

                EditText editText = (EditText)getLayoutInflater().inflate(R.layout.edittext,null);
                /*EditText editText = new EditText(getBaseContext());
                editText.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                editText.setSingleLine();
                editText.setHighlightColor(getResources().getColor(R.color.colorPrimary));
                */
                editText.setHint("Roommate " + counter);
                editText.requestFocus();


                layout.addView(editText);
            }
            if(counter == 12){
                newButton.setEnabled(false);
            }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter > 1) {
                    Log.v("WM", layout.getChildCount() + "");
                    counter--;
                    try {
                        layout.removeView(layout.getChildAt(counter));
                    } catch (NullPointerException e) {
                        Log.v("WM", "NAH FAM DIDN'T WORK");
                    }
                    (layout.getChildAt(counter-1)).requestFocus();
                }
                if(counter == 1)
                    removeButton.setEnabled(false);
                if(counter <=12)
                    newButton.setEnabled(true);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int locNotEntered = -1;
                boolean canMoveOn = true;
                for(int i = 0; i <counter; i++){
                    if(((EditText)layout.getChildAt(i)).getText().toString().length() == 0){
                        Log.v("WM","TEXT: "+(((EditText) layout.getChildAt(i)).getText().toString()));
                        canMoveOn = false;
                        locNotEntered = i;
                    }
                }

                if(canMoveOn) {
                    initializeStep2(layout.getChildCount() - 1);
                }
                else {
                    Toast.makeText(getBaseContext(), "You must enter a name!", Toast.LENGTH_LONG).show();
                    layout.getChildAt(locNotEntered).requestFocus();

                }

            }
        });



    }


    private void initializeStep2(int roommates){

        layoutStep1.setVisibility(View.GONE);
        newButton.setVisibility(View.GONE);
        removeButton.setVisibility(View.GONE);
        prevButton.setVisibility(View.VISIBLE);
        layoutStep2.setVisibility(View.VISIBLE);


    }

}
