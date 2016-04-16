package demo.cn.swiperefreshlayout;

public class ItemBean {
    public static final  int ITEM_TYPE_LEFT=1;
    public static final  int ITEM_TYPE_MIDDLE=2;
    public static final  int ITEM_TYPE_RIGHT=3;
    public static final  int ITEM_TYPE_MORE=4;
    //条目的类型，类似QQ中支持多种条目类型
    private int mItemType;
    private String mTitle;
    public ItemBean(int itemType) {

        this.mItemType = itemType;
        if(itemType==ITEM_TYPE_LEFT){
            mTitle="左对齐";
        }else if(itemType==ITEM_TYPE_MIDDLE){
            mTitle="居中对齐";
        }else if(itemType==ITEM_TYPE_RIGHT){
            mTitle="右对齐";
        }else{
            mTitle="加载更多";
        }
    }

    public int getItemType(){
        return mItemType;
    }
    public String getTitle(){
        return mTitle;
    }
}
