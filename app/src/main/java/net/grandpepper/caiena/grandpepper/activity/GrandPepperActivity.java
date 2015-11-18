package net.grandpepper.caiena.grandpepper.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.adapters.AdapterGrandPepper;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.models.GrandPepperDAO;

import java.util.Collections;
import java.util.List;

public class GrandPepperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Grand Pepper");
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#ffffff"));

        RecyclerView recList = (RecyclerView) findViewById(R.id.recycler_view_card);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        List<GrandPepper> arrayList = null;
        try {
            arrayList = GrandPepperDAO.getInstance(this).findAll();
            Collections.reverse(arrayList);
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage());
        }
        recList.setAdapter(new AdapterGrandPepper(arrayList, this));

    }
}
