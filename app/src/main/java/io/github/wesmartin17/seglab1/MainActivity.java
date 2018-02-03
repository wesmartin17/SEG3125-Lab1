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
    boolean initializedStep2 = false;
    FragmentEnterRoomDetails fragmentEnterRoomDetails;
    FragmentEnterNames fragmentEnterNames;

    String[] saveRoommatesForPreviousButton; //too long of name or not?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();

        setContentView(R.layout.activity_main);

        fragmentEnterNames = new FragmentEnterNames();

        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(R.id.frame);
        if(mFragment == null) {
            replaceFragment(fragmentEnterNames, true);
        }

        newButton = (Button)findViewById(R.id.btnAdd);
        removeButton = (Button)findViewById(R.id.btnRemove);
        nextButton = (Button)findViewById(R.id.btnNext);
        //nextButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        prevButton = (Button)findViewById(R.id.btnPrev);
        //prevButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
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

        View.OnTouchListener touchListener2 = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(getResources().getColor(R.color.colorAccentDark),PorterDuff.Mode.SRC_ATOP);
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
        nextButton.setOnTouchListener(touchListener2);
        prevButton.setOnTouchListener(touchListener2);


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
                if(step == 1) {
                    if (fragmentEnterNames.validate()) {
                        initializedStep2 = initializeStep2(fragmentEnterNames.getNames());
                        step++;
                    }
                }
                else{
                    String [] namesForNextStep = fragmentEnterRoomDetails.getUncheckedNames();
                    if(namesForNextStep.length > 0) {
                        initializeStep2(fragmentEnterRoomDetails.getUncheckedNames());
                        step++;
                    }
                    else{
                        //MAC CALL YOUR ACTIVITY HERE
                    }
                }

            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(step-1 == 1) {
                    //fragmentEnterNames.restore();
                    prevButton.setVisibility(View.GONE);
                    newButton.setVisibility(View.VISIBLE);
                    removeButton.setVisibility(View.VISIBLE);
                    mFragmentManager.popBackStack();
                    Toast.makeText(getBaseContext(),"popping backstack and reloading names",Toast.LENGTH_LONG).show();
                    //replaceFragment(fragmentEnterNames,true);
                    /*for(int i = 0; i < saveRoommatesForPreviousButton.length; i++){

                        int x = fragmentEnterNames.addName(saveRoommatesForPreviousButton[i]);
                        Log.v("WM","restoring "+saveRoommatesForPreviousButton[i]+" at "+x);
                    }
*/
                }
                else {
                    mFragmentManager.popBackStack();
                }
                step--;
            }
        });


    }


    private boolean initializeStep2(String[] roommates){
        if(roommates.length>0) {
            this.saveRoommatesForPreviousButton = roommates;
            fragmentEnterRoomDetails = new FragmentEnterRoomDetails();
            newButton.setVisibility(View.GONE);
            removeButton.setVisibility(View.GONE);
            prevButton.setVisibility(View.VISIBLE);


            Bundle bundle = new Bundle();
            bundle.putStringArray("names", roommates);
            replaceFragment(fragmentEnterRoomDetails, true);
            fragmentEnterRoomDetails.setArguments(bundle);

            return true;
        }
        else
            return false;

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
            mFragmentTransition.add(R.id.frame,fragment).addToBackStack("").commit();
        else
            mFragmentTransition.add(R.id.frame,fragment).commit();
    }

    private void disableButton(Button b){
        b.setEnabled(false);
        b.setBackgroundColor(getResources().getColor(R.color.colorGrey));
    }

    private void enableButton(Button b){
        b.setEnabled(true);
        b.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }


}
