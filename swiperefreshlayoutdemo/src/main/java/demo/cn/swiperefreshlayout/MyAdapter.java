package demo.cn.swiperefreshlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yuch on 2016/4/16.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<ItemBean> mItemBeanList;

    public MyAdapter(Context context,List<ItemBean> itemBeanList){
        mContext=context;
        mItemBeanList=itemBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    RecyclerView.ViewHolder holder = null;
            View v = null;
            switch (viewType) {
                case ItemBean.ITEM_TYPE_LEFT:
                    v = LayoutInflater.from(mContext).inflate(R.layout.item1, parent, false);
                    holder = new MyViewHolder1(v);
                    break;
                case ItemBean.ITEM_TYPE_MIDDLE:
                    v = LayoutInflater.from(mContext).inflate(R.layout.item2, parent, false);
                    holder = new MyViewHolder2(v);
                    break;
                case ItemBean.ITEM_TYPE_RIGHT:
                    v = LayoutInflater.from(mContext).inflate(R.layout.item3, parent, false);
                    holder = new MyViewHolder3(v);
                    break;
                case ItemBean.ITEM_TYPE_MORE:
                    v=LayoutInflater.from(mContext).inflate(R.layout.loadmore,parent,false);
                    holder = new MyViewHolder4(v);
                    break;
            }
            return holder;
   }

    @Override
    public int getItemViewType(int position) {
        return mItemBeanList.get(position).getItemType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder ,int position) {
        TextView textView=null;
        switch (mItemBeanList.get(position).getItemType()){
            case ItemBean.ITEM_TYPE_LEFT:
                textView= ((MyViewHolder1) holder).titleTextView;
                break;
            case ItemBean.ITEM_TYPE_MIDDLE:
                textView= ((MyViewHolder2) holder).titleTextView;
                break;
            case ItemBean.ITEM_TYPE_RIGHT:
                textView= ((MyViewHolder3) holder).titleTextView;
                break;
            case ItemBean.ITEM_TYPE_MORE:
                textView= ((MyViewHolder4) holder).titleTextView;
                break;
        }
        //当数据类型为ItemBean.ITEM_TYPE_MORE时，条目中没有TextView
        if(textView != null) {
            textView.setText(mItemBeanList.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mItemBeanList.size();
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {
            public TextView titleTextView;

            public MyViewHolder1(View view) {
                super(view);
                titleTextView=(TextView)view.findViewById(R.id.textView1);
            }
        }

        class MyViewHolder2 extends RecyclerView.ViewHolder {
            public TextView titleTextView;
            public MyViewHolder2(View view) {
                super(view);
                titleTextView=(TextView)view.findViewById(R.id.textView1);
            }

        }

        class MyViewHolder3 extends RecyclerView.ViewHolder {
            public TextView titleTextView;
            public MyViewHolder3(View view) {
                super(view);
                titleTextView=(TextView)view.findViewById(R.id.textView1);
            }

        }
    class MyViewHolder4 extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public MyViewHolder4(View view) {
            super(view);
            titleTextView=(TextView)view.findViewById(R.id.textView1);
        }

    }
}
