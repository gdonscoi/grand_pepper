package net.grandpepper.caiena.grandpepper.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.beans.Event;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;

import java.util.Date;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DetailEventActivity extends AppCompatActivity {

    final public static int EVENT_DETAIL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_event_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (toolbar.getNavigationIcon() != null)
            toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));

        GrandPepper grandPepper = (GrandPepper) getIntent().getExtras().getSerializable("grand_pepper");

        assert grandPepper != null;
        ((TextView) findViewById(R.id.text_date_events)).setText(grandPepper.date);


        findViewById(R.id.text_date_events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, new Date().getTime());
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                startActivity(intent);
            }
        });

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container_events);
        for (Event event : grandPepper.eventCollection) {
            TextView textView = new TextView(this);
            textView.setText(event.startTime.concat(" - ").concat(event.endTime).concat(" ").concat(event.title));
            textView.setTextSize(18);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 10, 15, 0); // llp.setMargins(left, top, right, bottom);
            textView.setLayoutParams(llp);
            linearLayout.addView(textView);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.text_card_events));
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#121212"));
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#ffffff"));

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
