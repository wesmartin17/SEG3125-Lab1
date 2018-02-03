package layout;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.github.wesmartin17.seglab1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEnterRoomDetails extends Fragment {

    private int currentRoommate = 0;

    ArrayList<ArrayList<String>> allRooms;
    ArrayList<String> roommates;

    LinearLayout layout;

    TextView topText;

    EditText roomSize;

    TextView txtRoommates;

    String[] names;
    static int totalUnChecked;

    Spinner pickRoomSpinner;

    public FragmentEnterRoomDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //name = getArguments().getString("name");
        names = getArguments().getStringArray("names");
        totalUnChecked = names.length -1;
        roommates = new ArrayList<>();
        allRooms = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_room_details, container, false);

        layout = (LinearLayout)view.findViewById(R.id.roommates);

        topText = (TextView)view.findViewById(R.id.step2Title);
        roomSize = (EditText)view.findViewById(R.id.roomSizeEditText);

        txtRoommates = (TextView)view.findViewById(R.id.roommatesinroom);

        pickRoomSpinner = (Spinner)view.findViewById(R.id.spinner);
        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("Single");
        spinnerList.add("Double");
        spinnerList.add("Triple");
        spinnerList.add("Basement");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickRoomSpinner.setAdapter(adapter);

        topText.setText("Enter the details of "+names[0]+"'s room:");
        if(names.length > 1) {
            for (int i = 1; i < names.length; i++) {
                final CheckBox cb = new CheckBox(getContext());
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            totalUnChecked--;
                        } else
                            totalUnChecked++;
                    }
                });

                cb.setText(names[i]);
                layout.addView(cb);
            }
        }
        else{
            txtRoommates.setVisibility(View.GONE);
        }
        return view;
    }

    public String[] getUncheckedNames() {
        String[] remaining = new String[totalUnChecked];
        int j = 0;
        for (int i = 0; i < totalUnChecked; i++) {
            if (!((CheckBox) layout.getChildAt(i)).isChecked()) {
                remaining[j] = (((CheckBox) layout.getChildAt(i)).getText().toString());
                j++;
            }
        }
        Log.v("WM", "contents of remaining: " + java.util.Arrays.toString(remaining));
        return remaining;
    }

}
