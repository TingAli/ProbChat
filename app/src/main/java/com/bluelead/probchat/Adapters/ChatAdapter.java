package com.bluelead.probchat.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelead.probchat.Models.Message;
import com.bluelead.probchat.R;

import java.util.ArrayList;

/**
 * Created by Imdad Ali on 12/02/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private int mNumberItems, mViewHolderCount;
    final private ListItemClickListener mOnClickListener;
    private static final String TAG = ChatAdapter.class.getSimpleName();
    private static ArrayList<Message> mAllMessages;
    private Context mContext;

    public ChatAdapter(Context context, int numberOfItems, ListItemClickListener listener,
                       ArrayList<Message> allMessages) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        mViewHolderCount = 0;
        mAllMessages = allMessages;
        mContext = context;
    }

    public void addMessage(Message message) {
        mAllMessages.add(message);
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.message_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ChatViewHolder viewHolder = new ChatViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ChatViewHolder holder, int position) {
        holder.bind(mAllMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return mAllMessages.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView messageTextView;
        TextView messageDateTextView;

        public ChatViewHolder(View itemView) {
            super(itemView);
            messageTextView = (TextView) itemView.findViewById(R.id.message_content_tv);
            messageDateTextView = (TextView) itemView.findViewById(R.id.message_date_tv);

            itemView.setOnClickListener(this);
        }

        void bind(Message message) {
            messageTextView.setText(message.getMessage());
            messageDateTextView.setText(message.getDate().toString());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
