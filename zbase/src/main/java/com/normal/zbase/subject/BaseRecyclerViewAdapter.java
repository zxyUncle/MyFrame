package com.normal.zbase.subject;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.normal.zbase.BR;

public class BaseRecyclerViewAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private OnDataListener onDataListener;

    public void setOnDataListener(OnDataListener<T> onDataListener) {
        this.onDataListener = onDataListener;
    }

    public BaseRecyclerViewAdapter(@LayoutRes int layoutResId, @IdRes int... viewIds) {
        super(layoutResId);
        addChildClickViewIds(viewIds);
    }

    @Override
    protected void convert(BaseViewHolder helper, T info) {
        ViewDataBinding dataBinding = DataBindingUtil.bind(helper.itemView);
        dataBinding.setVariable(BR.itemInfo, info);
        dataBinding.executePendingBindings();
        //二级内容扩展
        if (onDataListener!=null){
            onDataListener.onResult(helper,info);
        }
    }

    public int getItemIndex(T item) {
        return item != null && getData() != null && !getData().isEmpty() ? getData().indexOf(item) : -1;
    }

   public interface OnDataListener<T>{
        void onResult(BaseViewHolder helper, T info);
    }

}