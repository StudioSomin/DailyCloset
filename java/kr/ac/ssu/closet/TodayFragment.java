package kr.ac.ssu.closet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by soeun on 2017. 7. 21..
 */

public class TodayFragment extends Fragment {
    public TodayFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, container, false);
    }
}

