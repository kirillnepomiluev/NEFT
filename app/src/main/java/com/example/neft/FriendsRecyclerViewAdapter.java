package com.example.neft;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsRecyclerViewAdapter.CustomViewHolder> {
    private List<Invite> feedItemList;
    private Context mContext;
    private OnItem2ClickListener onItemClickListener;

    public FriendsRecyclerViewAdapter(Context context, List<Invite> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Invite feedItem = feedItemList.get(i);

        //Render image using Picasso library
        //if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
        String sr = feedItem.getUser1uid();


        Picasso.get().load(Buffer.photourlpart1+sr+Buffer.photourlpart2)
                .error(R.drawable.placeholderim)
                .placeholder(R.drawable.placeholderim)
                .transform( new CropCircleTransformation())
                .into(customViewHolder.imageView);

        final int n = i;


        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getUsername()));


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(feedItemList.get(n));
            }
        };
        customViewHolder.imageView.setOnClickListener(listener);
        customViewHolder.textView.setOnClickListener(listener);
        customViewHolder.layout.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public OnItem2ClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItem2ClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        protected RelativeLayout layout;

        public CustomViewHolder(View view) {
            super(view);
            this.layout= (RelativeLayout) view.findViewById(R.id.itemlayout);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);

        }
    }
}
