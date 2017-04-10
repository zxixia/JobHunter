package net.jiawa.jobhunter.module.git.ProjectDetail2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.bean.git.projectdetail.File;

/**
 * Created by lenovo on 2017/4/6.
 */

public class ProjectDetaiCodeTreeAdapter extends BaseRecyclerAdapter<File> {

    public ProjectDetaiCodeTreeAdapter(Context context) {
        super(context, NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new TreeViewHolder(mInflater.inflate(R.layout.item_list_tree, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, File item, int position) {
        TreeViewHolder h = (TreeViewHolder) holder;
        h.mImageType.setImageResource(item.getType().equals("file") ? R.mipmap.ic_file : R.mipmap.ic_folder);
        h.mTextName.setText(item.getName());
    }

    private static class TreeViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageType;
        TextView mTextName;

        TreeViewHolder(View itemView) {
            super(itemView);
            mImageType = (ImageView) itemView.findViewById(R.id.iv_tree_type);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
