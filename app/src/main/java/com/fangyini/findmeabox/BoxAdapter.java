package com.fangyini.findmeabox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.BoxViewHolder> {

    private static final String TAG = BoxAdapter.class.getSimpleName();
    private static final String DATE_FORMAT = "dd/MM/yyy";

    private List<Box> mBoxes;
    private Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    final private ListItemClickListener mOnClickListener;

    public void setBoxes(List<Box> boxes) {
        mBoxes = boxes;
        notifyDataSetChanged();
    }

    public List<Box> getBoxes() {
        return mBoxes;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public BoxAdapter(Context context, ListItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
    }


    @Override
    public BoxViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        /*Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.Box_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        BoxViewHolder viewHolder = new BoxViewHolder(view);

        return viewHolder;*/
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.number_list_item, viewGroup, false);

        return new BoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BoxViewHolder holder, int position) {
        // Determine the values of the wanted data
        Box abox = mBoxes.get(position);
        String description = abox.getContent();
        String date = dateFormat.format(abox.getDate());

        //Set values
        holder.boxDescriptionView.setText(description);
        holder.dateView.setText(date);
    }


    @Override
    public int getItemCount() {
        if (mBoxes == null) {
            return 0;
        }
        return mBoxes.size();
    }


    class BoxViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView boxDescriptionView;
        TextView dateView;

        public BoxViewHolder(View itemView) {
            super(itemView);
            boxDescriptionView = itemView.findViewById(R.id.boxDescription);
            dateView = itemView.findViewById(R.id.boxDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mBoxes.get(getAdapterPosition()).getId();
            mOnClickListener.onListItemClick(elementId);
        }
    }
}