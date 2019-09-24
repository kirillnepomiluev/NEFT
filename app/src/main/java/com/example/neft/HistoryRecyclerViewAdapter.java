package com.example.neft;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.CustomViewHolder> {
    private List<Operation> feedItemList;
    private Context mContext;
    private OnItem2ClickListener onItemClickListener;
    private DateFormat df;

    public HistoryRecyclerViewAdapter(Context context, List<Operation> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        df=new SimpleDateFormat("HH:mm d-M-yy");
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Operation feedItem = feedItemList.get(i);


        //Render image using Picasso library
        //if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
        String sr = feedItem.getId();

        Picasso.get().load(Buffer.photourlpart1+sr+Buffer.photourlpart2)
                .error(R.drawable.placeholderim)
                .placeholder(R.drawable.placeholderim)
                .transform( new CropCircleTransformation())
                .into(customViewHolder.imageView);

        final int n = i;


        //Setting text view title
        Date date= new Date(feedItem.getTime());
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getName()));
        customViewHolder.textView2.setText((feedItem.getPlus())?"Доход +"+feedItem.getAmount()+"      "+df.format(date):"Расход -"+feedItem.getAmount()+"      "+df.format(date));
        customViewHolder.textView2.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        customViewHolder.textView2.setBackgroundResource(feedItem.getPlus()? R.color.buttons_recom_color:R.color.colorAccent);

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
        protected TextView textView2;
        protected RelativeLayout layout;

        public CustomViewHolder(View view) {
            super(view);
            this.layout= (RelativeLayout) view.findViewById(R.id.itemlayout);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.textView2 = (TextView) view.findViewById(R.id.phone);
        }
    }
}
