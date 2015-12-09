package net.grandpepper.caiena.grandpepper.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.models.EventDAO;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class GrandPepperDetailActivity extends AppCompatActivity {

    private Context context;
    private GrandPepper grandPepper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grand_pepper_detail);
        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (toolbar.getNavigationIcon() != null)
            toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));

        grandPepper = (GrandPepper) getIntent().getExtras().getSerializable("grand_pepper");

        ((TextView) findViewById(R.id.text_description_card_detail)).setText(getIntent().getExtras().getString("title"));

        String nameBackgroundImage = getIntent().getExtras().getString("background_image");
        if (nameBackgroundImage != null && !nameBackgroundImage.isEmpty()) {
            Bitmap backgroundImage = AndroidSystemUtil.getImageExternalStorage(nameBackgroundImage);
            if (backgroundImage != null)
                ((ImageView) findViewById(R.id.image_background_card)).setImageBitmap(backgroundImage);
        }


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getExtras().getString("title"));
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#ffffff"));

        View cardEvents = findViewById(R.id.card_events);
        cardEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailEventActivity.class);
                startActivityDetail(intent, view);
            }
        });

        View cardTalks = findViewById(R.id.card_talks);
        cardTalks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTalkActivity.class);
                startActivityDetail(intent, view);
            }
        });

        View cardLocations = findViewById(R.id.card_locations);
        cardLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailLocationActivity.class);
                startActivityDetail(intent, view);
            }
        });

        View cardCalls = findViewById(R.id.card_calls);
        cardCalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CallForPeppersActivity.class);
                startActivityDetail(intent, view);
            }
        });

        try {
            if (grandPepper != null && grandPepper.eventCollection.isEmpty())
                cardEvents.setVisibility(View.GONE);

            if (grandPepper != null && EventDAO.getInstance(this).getTalks(grandPepper.version).isEmpty())
                cardTalks.setVisibility(View.GONE);

            if (grandPepper != null && grandPepper.locationCollection.isEmpty())
                cardLocations.setVisibility(View.GONE);

            if (grandPepper != null && grandPepper.callForPeppers == null)
                cardCalls.setVisibility(View.GONE);
        } catch (Exception ignore) {
        }

    }

    private void startActivityDetail(Intent intent, View view) {
        intent.putExtra("grand_pepper", grandPepper);
        Pair<View, String> p1 = Pair.create(view.findViewById(R.id.image_background_card), "comum_image_detail");
        Pair<View, String> p2 = Pair.create(view.findViewById(R.id.text_description_card), "comum_text_detail");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, p1, p2);
        context.startActivity(intent, options.toBundle());
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
