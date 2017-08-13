package kr.ac.ssu.closet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by soeun on 2017. 7. 21..
 * Binding Tabs: TodayTab, CalenderTab, ClosetTab
 */

public class BinderActivity extends AppCompatActivity {

    private RadioGroup rgTab;
    private RadioButton[] tab = new RadioButton[3];
    private FloatingActionButton fab;
//    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        Toast.makeText(BinderActivity.this, user, Toast.LENGTH_SHORT).show();
        rgTab = (RadioGroup) findViewById(R.id.group_tab);
        tab[0] = (RadioButton) findViewById(R.id.tab_today);
        tab[1] = (RadioButton) findViewById(R.id.tab_calendar);
        tab[2] = (RadioButton) findViewById(R.id.tab_closet);
        fab = (FloatingActionButton) findViewById(R.id.fab);
//        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
//
//        floatingActionButton.setImageDrawable(AppCompatDrawableManager.get()
//                .getDrawable(getApplicationContext(), R.drawable.ic_menu_white));
//
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.frame, new TodayFragment());
//        fragmentTransaction.commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("prefName", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                
                startActivity(new Intent(BinderActivity.this, LoginActivity.class));
                finish();
            }
        });

        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            Fragment fragment;
            LinearLayout.LayoutParams wide = new LinearLayout.LayoutParams(0, 130, 3);
            LinearLayout.LayoutParams narrow = new LinearLayout.LayoutParams(0, 130, 2);
            private final int[] tabId = {tab[0].getId(), tab[1].getId(), tab[2].getId()};

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tab[1].setLayoutParams(narrow);
                tab[2].setLayoutParams(narrow);
                tab[0].setLayoutParams(narrow);

                if(checkedId == tabId[1]) {
                    fragment = new CalendarFragment();
                    tab[1].setLayoutParams(wide);
                } else if(checkedId == tabId[2]) {
                    fragment = new ClosetFragment();
                    tab[2].setLayoutParams(wide);
                } else {
                    fragment = new TodayFragment();
                    tab[0].setLayoutParams(wide);
                }

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
            }
        });
        tab[0].setChecked(true);
    }
}
