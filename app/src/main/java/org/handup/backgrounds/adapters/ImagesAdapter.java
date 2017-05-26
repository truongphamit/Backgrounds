package org.handup.backgrounds.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.handup.backgrounds.R;
import org.handup.backgrounds.models.ImageData;

import java.util.List;

/**
 * Created by truongpq on 26/05/2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder>{
    private Context context;
    private List<ImageData> imageDatas;

    public ImagesAdapter(Context context, List<ImageData> imageDatas) {
        this.context = context;
        this.imageDatas = imageDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.item_image, parent, false);

        // Return a new holder instance
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageData imageData = imageDatas.get(position);

        Glide.with(context).load(imageData.getLink()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return imageDatas.size();
    }

    public ImageData getImageData(int position) {
        return imageDatas.get(position);
    }

    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;

        public ViewHolder(final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}
