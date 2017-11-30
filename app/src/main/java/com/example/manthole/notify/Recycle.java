package com.example.manthole.notify;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manthole on 03 Oct 2017.
 */

public class Recycle extends RecyclerView.Adapter<Recycle.ViewHolder> {


    Context context;
    List<Notifications> notificationsList;

    public Recycle(Context context, List<Notifications> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
    }




    @Override
    public Recycle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Recycle.ViewHolder holder, int position) {

        Notifications notifications;
        notifications = notificationsList.get(position);

        holder.HeadWrapImage.setImageResource(notifications.getImage1());
        holder.txtHeadWrapText.setText(notifications.getUsername());


    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView HeadWrapImage;
        TextView txtHeadWrapText;

        public ViewHolder(View itemView) {
            super(itemView);

            HeadWrapImage = (ImageView) itemView.findViewById(R.id.defaultimage);
            txtHeadWrapText = (TextView) itemView.findViewById(R.id.txtHeadWrapName);
        }
    }
}
