package sg.edu.nus.se26pt03.photolearn.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.se26pt03.photolearn.R;
/**
 * Created by MyatMin on 08/3/18.
 */
public class LearnigItemDetailFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learnig_item_detail, container, false);
    }

}
