package io.github.wesmartin17.seglab1;

import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import layout.FragmentEnterNames;
import layout.FragmentEnterRoomDetails;

public class MainActivity extends AppCompatActivity {

    FragmentManager mFragmentManager;
    Fragment mFragment;
    FragmentTransaction mFragmentTransition;

    public static MainActivity mMainActivity;

    LinearLayout layoutStep1,layoutStep2;

    Button newButton,removeButton,nextButton,prevButton;
    LinearLayout layout;

    private int step = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();

        setContentView(R.layout.activity_main);

        final FragmentEnterNames fragmentEnterNames = new FragmentEnterNames();

        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(R.id.frame);
        if(mFragment == null) {
            replaceFragment(fragmentEnterNames, true);
        }

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
                int num = fragmentEnterNames.addName();
                if(num < 12){
                    enableButton(removeButton);
                }
                else if(num == 12){
                    disableButton(newButton);
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = fragmentEnterNames.removeName();

                if(num == 1)
                    disableButton(removeButton);
                if(num <=12)
                    enableButton(newButton);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentEnterNames.validate()) {
                    initializeStep2(fragmentEnterNames.getNames());
                }

            }
        });



    }


    private void initializeStep2(String[] roommates){
        newButton.setVisibility(View.GONE);
        removeButton.setVisibility(View.GONE);
        prevButton.setVisibility(View.VISIBLE);

        FragmentEnterRoomDetails fragmentEnterRoomDetails = new FragmentEnterRoomDetails();

        Bundle bundle = new Bundle();
        bundle.putString("name",roommates[0]);
        replaceFragment(fragmentEnterRoomDetails,true);
        fragmentEnterRoomDetails.setArguments(bundle);


    }


    /**
     * Replaces a given fragment in the fragment frame. should probably go somewhere else eventually where it can be more widely used
     *
     * @param fragment desired fragment within the scope of this class (it must implement its OnFragmentIneractionListener)
     * @param backStack it will add it to the backstack if true
     */
    public void replaceFragment(Fragment fragment, boolean backStack) {
        mFragmentTransition = mFragmentManager.beginTransaction();
        mFragmentTransition.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        if(backStack)
            mFragmentTransition.replace(R.id.frame,fragment).addToBackStack("").commit();
        else
            mFragmentTransition.replace(R.id.frame,fragment).commit();
    }

    private void disableButton(Button b){
        b.setEnabled(false);
        b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void enableButton(Button b){
        b.setEnabled(true);
        b.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }


}
