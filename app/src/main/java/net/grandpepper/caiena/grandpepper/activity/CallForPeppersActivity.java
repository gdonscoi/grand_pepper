package net.grandpepper.caiena.grandpepper.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.beans.Contact;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;

public class CallForPeppersActivity extends AppCompatActivity {

    final public static int CALL_FOR_PEPPERS_DETAIL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_for_peppers_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (toolbar.getNavigationIcon() != null)
            toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));


        ((TextView) findViewById(R.id.text_description_card_detail)).setText(getIntent().getExtras().getString("title"));

        String nameBackgroundImage = getIntent().getExtras().getString("background_image");
        if (nameBackgroundImage != null && !nameBackgroundImage.isEmpty()) {
            Bitmap backgroundImage = AndroidSystemUtil.getImageExternalStorage(nameBackgroundImage);
            if (backgroundImage != null)
                ((ImageView) findViewById(R.id.image_background_detail)).setImageBitmap(backgroundImage);
        }

        GrandPepper grandPepper = (GrandPepper) getIntent().getExtras().getSerializable("grand_pepper");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getExtras().getString("title"));
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#121212"));
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#ffffff"));

        ((TextView) findViewById(R.id.call_for_peppers_description)).setText(grandPepper.callForPeppers.subtitle);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container_contact);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 30);

        for(Contact contact :grandPepper.callForPeppers.contactCollection){
            TextView textViewName = new TextView(this);
            textViewName.setText(contact.name);

            TextView textViewEmail = new TextView(this);
            textViewEmail.setText(contact.email);
            textViewEmail.setLayoutParams(layoutParams);

            linearLayout.addView(textViewName);
            linearLayout.addView(textViewEmail);
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
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}