package layout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.wesmartin17.seglab1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEnterRoomDetails extends Fragment {

    TextView topText;

    String name;

    public FragmentEnterRoomDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_room_details, container, false);

        topText = (TextView)view.findViewById(R.id.step2Title);
        topText.setText("Enter the details of "+name+"'s room:");
        return view;
    }

}
