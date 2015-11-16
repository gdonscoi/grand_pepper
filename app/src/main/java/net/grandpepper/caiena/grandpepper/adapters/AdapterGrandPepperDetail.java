package net.grandpepper.caiena.grandpepper.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.activity.DetailEventActivity;
import net.grandpepper.caiena.grandpepper.activity.DetailTalkActivity;
import net.grandpepper.caiena.grandpepper.activity.GrandPepperActivity;
import net.grandpepper.caiena.grandpepper.activity.GrandPepperDetailActivity;
import net.grandpepper.caiena.grandpepper.beans.CallForPeppers;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.models.EventDAO;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;

import java.util.ArrayList;
import java.util.List;

public class AdapterGrandPepperDetail extends RecyclerView.Adapter<AdapterGrandPepperDetail.ViewHolder> {

    private GrandPepper grandPepper;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView descriptionCard;
        public ImageView backgroundImage;
        public RelativeLayout container;
        public RelativeLayout disabledView;
        private Context context;
        private GrandPepper grandPepper;

        public ViewHolder(View v, Context context, GrandPepper grandPepper) {
            super(v);
            container = (RelativeLayout) v.findViewById(R.id.container_grand_pepper_detail_card);
            descriptionCard = (TextView) container.findViewById(R.id.text_description_card);
            backgroundImage = (ImageView) container.findViewById(R.id.image_background_card);
            disabledView = (RelativeLayout) v.findViewById(R.id.content_background_disabled);
            this.context = context;
            this.grandPepper = grandPepper;
            v.setOnClickListener(this);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (getAdapterPosition()) {
                case DetailEventActivity.EVENT_DETAIL:
                    intent = new Intent(context, DetailEventActivity.class);
                    intent.putExtra("background_image", grandPepper.backgroundImagePath);
                    break;
                case DetailTalkActivity.TALK_DETAIL:
                    intent = new Intent(context, DetailTalkActivity.class);
                    intent.putExtra("background_image", grandPepper.talksBackgroundImagePath);
                    break;
                case 2:
                    intent = new Intent(context, GrandPepperDetailActivity.class);
                    break;
                case 3:
                    intent = new Intent(context, GrandPepperDetailActivity.class);
                    break;
                default:
                    if (intent == null)
                        intent = new Intent(context, GrandPepperActivity.class);
                    Log.e("AdapterGrandPepper", "tela erro");
                    break;
            }

            intent.putExtra("grand_pepper", grandPepper);
            intent.putExtra("title", ((TextView) view.findViewById(R.id.text_description_card)).getText());

            Pair<View, String> p1 = Pair.create(view.findViewById(R.id.image_background_card), "comum_image_detail");
            Pair<View, String> p2 = Pair.create(view.findViewById(R.id.text_description_card), "comum_text_detail");
//            Pair<View, String> p3 = Pair.create(view.findViewById(R.id.content_background_text), "comum_background_text_detail");

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, p1, p2);//, p3);
            context.startActivity(intent, options.toBundle());
        }
    }

    public AdapterGrandPepperDetail(GrandPepper grandPepper, Context context) {
        this.context = context;
        this.grandPepper = grandPepper;
    }

    @Override
    public AdapterGrandPepperDetail.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grand_pepper_detail_card_view, parent, false);

        return new ViewHolder(v, context, grandPepper);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (position) {
            case DetailEventActivity.EVENT_DETAIL:
                holder.descriptionCard.setText("Programação");
                if (grandPepper.backgroundImagePath != null && !grandPepper.backgroundImagePath.isEmpty()) {
                    holder.backgroundImage.setImageBitmap(AndroidSystemUtil.getImageExternalStorage(grandPepper.backgroundImagePath));
                } else {
                    holder.itemView.setEnabled(false);
                    holder.disabledView.setVisibility(View.VISIBLE);
                }
                break;
            case DetailTalkActivity.TALK_DETAIL:
                holder.descriptionCard.setText("Palestras");
                if (grandPepper.talksBackgroundImagePath != null && !grandPepper.talksBackgroundImagePath.isEmpty())
                    holder.backgroundImage.setImageBitmap(AndroidSystemUtil.getImageExternalStorage(grandPepper.talksBackgroundImagePath));
                try {
                    if (EventDAO.getInstance(context).getTalks(grandPepper.version).isEmpty()) {
                        holder.itemView.setEnabled(false);
                        holder.disabledView.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ignore) {
                    holder.itemView.setEnabled(false);
                    holder.disabledView.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                holder.descriptionCard.setText("Localização");
                if (grandPepper.locationBackgroundImagePath != null && !grandPepper.locationBackgroundImagePath.isEmpty())
                    holder.backgroundImage.setImageBitmap(AndroidSystemUtil.getImageExternalStorage(grandPepper.locationBackgroundImagePath));
                if (grandPepper.locationCollection.isEmpty()) {
                    holder.itemView.setEnabled(false);
                    holder.disabledView.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                holder.descriptionCard.setText("Call for Peppers");
                if (grandPepper.callForPeppersCollection != null && !grandPepper.callForPeppersCollection.isEmpty()) {
                    List<CallForPeppers> callForPepperses = new ArrayList<>(grandPepper.callForPeppersCollection);
                    if (!callForPepperses.isEmpty() && callForPepperses.get(0).backgroundImagePath != null && !callForPepperses.get(0).backgroundImagePath.isEmpty()) {
                        holder.backgroundImage.setImageBitmap(AndroidSystemUtil.getImageExternalStorage(callForPepperses.get(0).backgroundImagePath));
                    }
                } else {
                    holder.itemView.setEnabled(false);
                    holder.disabledView.setVisibility(View.VISIBLE);
                }
                break;
        }


    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
