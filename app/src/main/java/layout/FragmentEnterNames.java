package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import io.github.wesmartin17.seglab1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEnterNames extends Fragment {

    int counter = 1;

    LinearLayout layout;

    public FragmentEnterNames() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_enter_names, container, false);
        layout = (LinearLayout)view.findViewById(R.id.nameLayout);

        return view;
    }


    public int addName(){
        counter++;
        //removeButton.setEnabled(true);

        EditText editText = (EditText)getActivity().getLayoutInflater().inflate(R.layout.edittext,null);
            /*EditText editText = new EditText(getBaseContext());
            editText.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            editText.setSingleLine();
            editText.setHighlightColor(getResources().getColor(R.color.colorPrimary));
            */
        editText.setHint("Roommate " + counter);
        editText.requestFocus();


        layout.addView(editText);


        return counter;

    }

    public int removeName(){
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

        return counter;

    }

    public boolean validate(){

        int locNotEntered = -1;
        boolean canMoveOn = true;
        for(int i = 0; i <counter; i++){
            if(((EditText)layout.getChildAt(i)).getText().toString().length() == 0){
                //Log.v("WM","TEXT: "+(((EditText) layout.getChildAt(i)).getText().toString()));
                canMoveOn = false;
                locNotEntered = i;
            }
        }

        if(!canMoveOn){
            Toast.makeText(getActivity(), "You must enter a name!", Toast.LENGTH_LONG).show();
            layout.getChildAt(locNotEntered).requestFocus();
        }

        return canMoveOn;
    }

    public String[] getNames(){

        String[] roommates = new String[layout.getChildCount()];
        for(int i = 0; i < layout.getChildCount(); i++) {
            roommates[i] = ((EditText) layout.getChildAt(i)).getText().toString();
        }

        return  roommates;
    }

}
