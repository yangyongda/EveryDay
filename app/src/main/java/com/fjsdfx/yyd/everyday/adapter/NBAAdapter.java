package com.fjsdfx.yyd.everyday.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fjsdfx.yyd.everyday.R;
import com.fjsdfx.yyd.everyday.activity.PlayerDescribeActivity;
import com.fjsdfx.yyd.everyday.bean.TrBean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */

public class NBAAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<TrBean> trBeen = new ArrayList<>();
    public NBAAdapter(Context context){
        mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NBAViewHolder(LayoutInflater.from(mContext).inflate(R.layout.nba_layout_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((NBAViewHolder)holder).player1.setText(trBeen.get(position).getPlayer1());
        ((NBAViewHolder)holder).player2.setText(trBeen.get(position).getPlayer2());
        ((NBAViewHolder)holder).score.setText(trBeen.get(position).getScore());
        ((NBAViewHolder)holder).date.setText(trBeen.get(position).getTime());
        Glide.with(mContext)
                .load(trBeen.get(position).getPlayer1logo())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(((NBAViewHolder)holder).player1_logo);
        Glide.with(mContext)
                .load(trBeen.get(position).getPlayer2logo())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(((NBAViewHolder)holder).player2_logo);
        //Log.v("TAG",trBeen.get(position).getPlayer1url());
        ((NBAViewHolder) holder).player1_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDescribeActivity(trBeen.get(position).getPlayer1url());
            }
        });
        ((NBAViewHolder) holder).player2_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDescribeActivity(trBeen.get(position).getPlayer2url());
            }
        });
        ((NBAViewHolder) holder).video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDescribeActivity(trBeen.get(position).getLink1url());
            }
        });
        ((NBAViewHolder) holder).statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDescribeActivity(trBeen.get(position).getLink2url());
            }
        });

    }

    @Override
    public int getItemCount() {
        return trBeen.size();
    }

    class NBAViewHolder extends RecyclerView.ViewHolder{
        ImageView player1_logo;
        ImageView player2_logo;
        TextView player1;
        TextView player2;
        TextView score;
        TextView date;
        TextView video;
        TextView statistics;
        public NBAViewHolder(View itemView) {
            super(itemView);
            player1_logo = (ImageView) itemView.findViewById(R.id.player1_logo);
            player2_logo = (ImageView) itemView.findViewById(R.id.player2_logo);
            player1 = (TextView)itemView.findViewById(R.id.player1);
            player2 = (TextView)itemView.findViewById(R.id.player2);
            score = (TextView)itemView.findViewById(R.id.score);
            date = (TextView)itemView.findViewById(R.id.date);
            video = (TextView)itemView.findViewById(R.id.video);
            statistics = (TextView)itemView.findViewById(R.id.statistics);

        }
    }

    //增加item
    public void addItems(List<TrBean> list) {
        trBeen.addAll(list);
        notifyDataSetChanged();
    }
    //清除所有的数据
    public void clearData() {
        trBeen.clear();
        notifyDataSetChanged();
    }

    private void goDescribeActivity(String playerUrl){
        Intent intent = new Intent(mContext, PlayerDescribeActivity.class);
        intent.putExtra("playerUrl",playerUrl+"&cid=100000");
        mContext.startActivity(intent);
    }
}
