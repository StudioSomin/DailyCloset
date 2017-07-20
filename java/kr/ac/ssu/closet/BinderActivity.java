package kr.ac.ssu.closet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by soeun on 2017. 7. 21..
 * Binding Tabs: TodayTab, CalenderTab, ClosetTab
 */

public class BinderActivity extends AppCompatActivity {

    private RadioGroup rgTab;
    private RadioButton[] tab = new RadioButton[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);

        rgTab = (RadioGroup) findViewById(R.id.group_tab);

        tab[0] = (RadioButton) findViewById(R.id.tab_today);
        tab[1] = (RadioButton) findViewById(R.id.tab_calendar);
        tab[2] = (RadioButton) findViewById(R.id.tab_closet);

        tab[0].setChecked(true);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, new TodayFragment());
        fragmentTransaction.commit();

        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            Fragment fragment;
            private final int[] tabId = {tab[0].getId(), tab[1].getId(), tab[2].getId()};
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == tabId[1]) fragment = new CalendarFragment();
                else if(checkedId == tabId[2]) fragment = new ClosetFragment();
                else fragment = new TodayFragment();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
            }
        });
    }
}
