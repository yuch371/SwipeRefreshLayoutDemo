package demo.cn.swiperefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ItemBean> mItemDataList;
    private LinearLayoutManager mLinearLayoutManager;
    //是否正在加载一页数据
    private boolean mIsLoadPageDataIng=false;
    //列表最后一行的显示更多或者是脚注视图
    public ItemBean mItemMore=new ItemBean(ItemBean.ITEM_TYPE_MORE);

    public  MyAdapter mRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 读初始数据
        readItemData();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout1);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        mLinearLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerAdapter = new MyAdapter(this,mItemDataList);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        initSwipeRefreshLayoutEvent();
        initRecyclerViewEvent();
    }

    private void readItemData(){
        mItemDataList = new ArrayList<ItemBean>();
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_LEFT));
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_LEFT));
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_LEFT));
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_LEFT));

        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_MIDDLE));
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_MIDDLE));
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_MIDDLE));
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_MIDDLE));

        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_RIGHT));
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_RIGHT));
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_RIGHT));
        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_RIGHT));

        mItemMore = new ItemBean(ItemBean.ITEM_TYPE_MORE);
    }
    private void initSwipeRefreshLayoutEvent() {
        //设置mSwipeRefreshLayout的下拉事件监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //利用一个延迟，仿真下拉加载一页数据的过程
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载一页下拉数据
                        mItemDataList.add(0,new ItemBean(ItemBean.ITEM_TYPE_LEFT));
                        mItemDataList.add(1,new ItemBean(ItemBean.ITEM_TYPE_MIDDLE));
                        mItemDataList.add(2,new ItemBean(ItemBean.ITEM_TYPE_RIGHT));
                        mRecyclerAdapter.notifyDataSetChanged();
                        // 停止刷新动画
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000); // 5秒后发送消息，停止刷新
            }
        });
    }
    private void initRecyclerViewEvent(){
        //API23 才有的新方法
//        mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//            }
//        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //在Y轴上是否正在滚动的标记
            boolean  isYScrolling = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //如果当前滑动空闲并并且不是加载数据状态，则继续判断是否是最后一行
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&  mIsLoadPageDataIng==false ) {
                    int lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //如果是最后一行，则加载新的分页数据
                        loadMoreWithOnePage();
                    }
                }
            }
            //dx：水平方向滚动距离；dy:垂直方向滚动距离。
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isYScrolling = true;
                } else {
                    isYScrolling = false;
                }
            }

            //----------上拉刷新时更新数据并重新绘制界面-------------------------------------------

            //开始一个下拉加载数据的过程： 先显示脚注视图=》然后利用一个延迟模拟动态加载过程=》最后移除脚注视图
            public void loadMoreWithOnePage(){
                //
                mIsLoadPageDataIng =true;
                //先显示脚注视图
                onDispLayFootViewByLoadMore();
                //然后利用一个延迟模拟动态加载过程
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_LEFT));
                        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_LEFT));
                        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_MIDDLE));
                        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_MIDDLE));
                        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_RIGHT));
                        mItemDataList.add(new ItemBean(ItemBean.ITEM_TYPE_RIGHT));
                        //最后移除脚注视图
                        onUnDispLayFootViewByLoadMore();
                    }
                }, 3000);

            }

            public void onDispLayFootViewByLoadMore() {
                mItemDataList.add(mItemMore);
                mRecyclerAdapter.notifyDataSetChanged();
            }

            public void onUnDispLayFootViewByLoadMore() {
                mItemDataList.remove(mItemMore);
                mIsLoadPageDataIng=false;
                mRecyclerAdapter.notifyDataSetChanged();
            }
        });

    }
}
