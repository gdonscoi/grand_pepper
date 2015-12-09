package net.grandpepper.caiena.grandpepper.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.adapters.AdapterDetailTalk;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.models.EventDAO;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DetailTalkActivity extends AppCompatActivity {

    final public static int TALK_DETAIL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_talk_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (toolbar.getNavigationIcon() != null)
            toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));

        GrandPepper grandPepper = (GrandPepper) getIntent().getExtras().getSerializable("grand_pepper");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.text_card_talks));
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#121212"));
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#ffffff"));

        RecyclerView recList = (RecyclerView) findViewById(R.id.recycler_view_card);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        try {
            recList.setAdapter(new AdapterDetailTalk(EventDAO.getInstance(this).getTalks(grandPepper.version)));
        } catch (Exception e) {
            Log.e("DetailTalkActivity", "Erro on load Events type for talks");
            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
