package cl.octavionancul.myapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cl.octavionancul.myapplication.CurrentUser;
import cl.octavionancul.myapplication.Nodes;
import cl.octavionancul.myapplication.R;
import cl.octavionancul.myapplication.models.Product;
import cl.octavionancul.myapplication.models.Review;

public class ReviewsAdapter extends FirebaseRecyclerAdapter<Review,ReviewsAdapter.ReviewHolder> {

  private ReviewsCallback callback;
  private boolean reviewUser;
    public ReviewsAdapter(@NonNull FirebaseRecyclerOptions<Review> options,ReviewsCallback callback) {
        super(options);
        this.callback=callback;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        callback.update(reviewUser);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ReviewHolder holder, int position, @NonNull Review model) {

        String currentUser = new CurrentUser().uid();

        if(model.getUid().equals(currentUser)){
            //callback.hide();
        }else{
            holder.itemView.findViewById(R.id.deleterIv).setVisibility(View.GONE);
        }

        holder.itemView.findViewById(R.id.deleterIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Review review= getItem(holder.getAdapterPosition());

                new Nodes().reviews().child(review.getProductid()).child(review.getKey()).removeValue();
                //   Log.d("product",product.getKey());

            }
        });

        reviewUser=false;
        if(model.getUid().equals(new CurrentUser().uid())){
            reviewUser=true;
        }
        callback.update(reviewUser);
        holder.user.setText(model.getUser());
        holder.content.setText(model.getContent());
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ReviewsAdapter.ReviewHolder(view);
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder{
        TextView user, content;

        public ReviewHolder(View itemView) {
            super(itemView);
            user= itemView.findViewById(R.id.userTv);
            content= itemView.findViewById(R.id.contentTv);
        }
    }
}
