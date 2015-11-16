package net.grandpepper.caiena.grandpepper.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.beans.Event;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;

import java.util.List;

public class AdapterDetailTalk extends RecyclerView.Adapter<AdapterDetailTalk.ViewHolder> {

    private List<Event> talks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView startTime;
        public TextView talkName;
        public TextView talkAuthor;
        public ImageView imageAuthor;
        public TextView talkDescription;

        public ViewHolder(View v) {
            super(v);
            startTime = (TextView) v.findViewById(R.id.start_time);
            talkName = (TextView) v.findViewById(R.id.talk_name);
            talkAuthor = (TextView) v.findViewById(R.id.talk_author);
            talkDescription = (TextView) v.findViewById(R.id.talk_description);
            imageAuthor = (ImageView) v.findViewById(R.id.image_talk_author);
        }
    }

    public AdapterDetailTalk(List<Event> talks) {
        this.talks = talks;
    }

    @Override
    public AdapterDetailTalk.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_talk_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.startTime.setText(talks.get(position).startTime);
        holder.talkName.setText(talks.get(position).title);
        holder.talkAuthor.setText(talks.get(position).authorName);
        holder.talkDescription.setText(talks.get(position).summary);

        Bitmap backgroundImage = AndroidSystemUtil.getImageExternalStorage(talks.get(position).authorAvatarPath);
        if (backgroundImage != null)
            holder.imageAuthor.setImageBitmap(AndroidSystemUtil.getCircularAvatar(backgroundImage));
    }


    @Override
    public int getItemCount() {
        return talks.size();
    }

}
