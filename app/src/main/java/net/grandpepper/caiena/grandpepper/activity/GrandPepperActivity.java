package net.grandpepper.caiena.grandpepper.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.adapters.AdapterGrandPepper;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.models.GrandPepperDAO;

import java.util.Collections;
import java.util.List;

public class GrandPepperActivity extends AppCompatActivity {

    private Context context;
    private boolean flagExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_grand_pepper, menu);

        Drawable drawable = menu.findItem(R.id.refreshGrandPepper).getIcon();

        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.parseColor("#ffffff"));
        menu.findItem(R.id.refreshGrandPepper).setIcon(drawable);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.refreshGrandPepper) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.refresh_message)
                    .setPositiveButton(R.string.refresh_message_afirmative, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intentWebView = new Intent(GrandPepperActivity.this, SplashScreenActivity.class);
                            intentWebView.putExtra("refresh", true);
                            startActivity(intentWebView);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.refresh_message_negative, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        if (!flagExit) {
            flagExit = true;
            Toast.makeText(context, "Toque novamente para sair.", Toast.LENGTH_SHORT).show();
            return;
        }

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        flagExit = false;
    }

}
