package net.grandpepper.caiena.grandpepper.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.adapters.AdapterDetailLocation;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.beans.Location;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;

import java.util.ArrayList;

public class DetailLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    final public static int LOCATION_DETAIL = 2;
    private Context context;
    private GrandPepper grandPepper;
    private RelativeLayout containerMap;
    private RelativeLayout containerInfo;
    private GoogleMap mMap;
    private Button buttonMaps;
    private boolean readyBackAnimation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.detail_location_activity);

        this.context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (toolbar.getNavigationIcon() != null)
            toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));

        containerInfo = (RelativeLayout) findViewById(R.id.container_info);
        containerMap = (RelativeLayout) findViewById(R.id.container_map);
        ((TextView) findViewById(R.id.text_description_card_detail)).setText(getIntent().getExtras().getString("title"));

        String nameBackgroundImage = getIntent().getExtras().getString("background_image");
        if (nameBackgroundImage != null && !nameBackgroundImage.isEmpty()) {
            Bitmap backgroundImage = AndroidSystemUtil.getImageExternalStorage(nameBackgroundImage);
            if (backgroundImage != null)
                ((ImageView) findViewById(R.id.image_background_detail)).setImageBitmap(backgroundImage);
        }

        grandPepper = (GrandPepper) getIntent().getExtras().getSerializable("grand_pepper");

        buttonMaps = (Button) findViewById(R.id.location_maps);
        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circularShowMap();
            }
        });

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getExtras().getString("title"));
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
            if (grandPepper != null)
                recList.setAdapter(new AdapterDetailLocation(new ArrayList<>(grandPepper.locationCollection)));
        } catch (Exception e) {
            Log.e("DetailTalkActivity", "Erro on load Events type for talks");
            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        if (!readyBackAnimation)
            return;

        if (containerMap.getVisibility() == View.VISIBLE) {
            circularHideMap();
            return;
        }

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

    private void circularShowMap() {

        int cx = (findViewById(R.id.location_maps).getLeft() + findViewById(R.id.location_maps).getRight()) / 2;
        int cy = (findViewById(R.id.location_maps).getTop() + findViewById(R.id.location_maps).getBottom()) / 2;

        float finalRadius = Math.max(containerMap.getWidth(), containerMap.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(containerMap, cx, cy, 0, finalRadius);
        circularReveal.setDuration(1000);

        containerMap.setVisibility(View.VISIBLE);
        readyBackAnimation = false;
        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                readyBackAnimation = true;
            }
        });
        circularReveal.start();
    }

    private void circularHideMap() {
        int cx = (findViewById(R.id.location_maps).getLeft() + findViewById(R.id.location_maps).getRight()) / 2;
        int cy = (findViewById(R.id.location_maps).getTop() + findViewById(R.id.location_maps).getBottom()) / 2;

        int initialRadius = containerMap.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(containerMap, cx, cy,
                initialRadius, 0);
        anim.setDuration(1000);

        readyBackAnimation = false;
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                containerMap.setVisibility(View.INVISIBLE);
                readyBackAnimation = true;
            }
        });

        anim.start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Location location : grandPepper.locationCollection) {
            String[] latlong = location.latlong.split(",");
            LatLng local = new LatLng(Double.parseDouble(latlong[0]), Double.parseDouble(latlong[1]));
            Marker marker = mMap.addMarker(new MarkerOptions().position(local).title(location.action.concat(": ").concat(location.name)));
            marker.showInfoWindow();
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();
        int padding = 18;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, size.x, size.y, padding);
        googleMap.moveCamera(cameraUpdate);
        googleMap.animateCamera(cameraUpdate);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0F));
    }

}
