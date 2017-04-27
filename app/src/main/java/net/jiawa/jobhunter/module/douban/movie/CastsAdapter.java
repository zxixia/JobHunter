package net.jiawa.jobhunter.module.douban.movie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.adapter.BaseGeneralRecyclerAdapter;
import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.bean.douban.Casts;
import net.jiawa.jobhunter.bean.douban.Directors;

/**
 * Created by zhaoxin5 on 2017/4/27.
 */

public class CastsAdapter extends BaseGeneralRecyclerAdapter<Object> {

    public CastsAdapter(Callback callback) {
        // 没有头尾的列表样式
        super(callback, BaseRecyclerAdapter.NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new CastsViewHolder(mInflater.inflate(R.layout.item_list_douban_casts, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Object item, int position) {
        String image = "";
        String title = "";
        String name = "";
        if (item instanceof Casts) {
            image  = ((Casts) item).getAvatars().getLarge();
            title = "饰演";
            name = ((Casts) item).getName();
        } else if (item instanceof Directors) {
            image  = ((Directors) item).getAvatars().getLarge();
            title = "导演";
            name = ((Directors) item).getName();
        } else {
            throw new RuntimeException("item in CastsAdapter must be Casts or Directors!");
        }
        CastsAdapter.CastsViewHolder h = (CastsAdapter.CastsViewHolder) holder;
        mCallBack.getImgLoader()
                .load(image)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .into(h.avater);
        h.title.setText(title);
        h.name.setText(name);
    }

    private static class CastsViewHolder extends RecyclerView.ViewHolder {

        ImageView avater;
        TextView title;
        TextView name;

        public CastsViewHolder(View itemView) {
            super(itemView);
            avater = (ImageView) itemView.findViewById(R.id.iv_avater);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
