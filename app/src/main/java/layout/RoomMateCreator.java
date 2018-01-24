package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.wesmartin17.seglab1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomMateCreator extends Fragment {


    public RoomMateCreator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_mate_creator, container, false);

        return view;
    }

}
