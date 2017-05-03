package com.vlayout.wujinli.vlayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.vlayout.wujinli.vlayout.MyItemClickListener;
import com.vlayout.wujinli.vlayout.R;

import java.util.HashMap;
import java.util.List;

/**
 * author: WuJinLi
 * time  : 17/5/2
 * desc  :
 */

public class MyAdapter_DelegateAdapter extends DelegateAdapter.Adapter<MyAdapter_DelegateAdapter
        .MainViewHolder> {
    /**
     * 使用DelegateAdapter首先就是要自定义一个它的内部类Adapter，让LayoutHelper和需要绑定的数据传进去
     * 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法，其他的都是一样的做法
     */

    private Context context;
    private LayoutHelper layoutHelper;
    private int count = 0;
    private List<HashMap<String, Object>> listItem;//存放数据
    private RecyclerView.LayoutParams layoutParams;

    private MyItemClickListener myItemClickListener;
    // 用于设置Item点击事件

    public MyAdapter_DelegateAdapter(Context context, LayoutHelper layoutHelper, int count,
                                     List<HashMap<String, Object>> listItem) {
        this(context, layoutHelper, count, listItem, new RecyclerView.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, 300));

    }

    public MyAdapter_DelegateAdapter(Context context, LayoutHelper layoutHelper, int count,
                                     List<HashMap<String, Object>> listItem, RecyclerView
                                             .LayoutParams layoutParams) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.listItem = listItem;
        this.layoutParams = layoutParams;
    }

    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    // 把ViewHolder绑定Item的布局
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent,
                false));
    }

    // 绑定Item的数据
    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.Item.setText((String) listItem.get(position).get("ItemTitle"));
        holder.Image.setImageResource((Integer) listItem.get(position).get("ItemImage"));
    }

    // 返回Item数目
    @Override
    public int getItemCount() {
        return count;
    }

    //定义Viewholder
    public class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView Item;
        public ImageView Image;

        public MainViewHolder(View itemView) {
            super(itemView);
            Item = (TextView) itemView.findViewById(R.id.Item);
            Image = (ImageView) itemView.findViewById(R.id.Image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myItemClickListener != null) {
                        myItemClickListener.onItemClick(v, getPosition());
                    }
                }
            });
        }

    }

    // 设置Item的点击事件
    // 绑定MainActivity传进来的点击监听器
    public void setOnItemClickListener(MyItemClickListener listener) {
        myItemClickListener = listener;
    }
}
