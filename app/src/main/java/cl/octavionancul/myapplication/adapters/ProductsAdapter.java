package cl.octavionancul.myapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ProductsAdapter extends FirebaseRecyclerAdapter<Product,ProductsAdapter.ProductHolder> {

    private ProductsCallback callback;

    public ProductsAdapter(@NonNull FirebaseRecyclerOptions<Product> options,ProductsCallback callback) {
        super(options);
        this.callback=callback;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        callback.update();
    }

    @Override
    protected void onBindViewHolder(@NonNull final ProductHolder holder, int position, @NonNull Product model) {
    holder.product.setText(model.getName());
    String currentUser = new CurrentUser().uid();

    if(model.getUid().equals(currentUser)){
        holder.itemView.findViewById(R.id.deleteIv).setVisibility(View.VISIBLE);
    }else{
        holder.itemView.findViewById(R.id.deleteIv).setVisibility(View.GONE);
    }

    holder.itemView.findViewById(R.id.deleteIv).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            
           Product product= getItem(holder.getAdapterPosition());

           new Nodes().products().child(product.getKey()).removeValue();
         //   Log.d("product",product.getKey());

        }
    });

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Product product = getItem(holder.getAdapterPosition());

            callback.clicked(product);
         //   Log.d("product",product.getName());
        }
    });

    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        return new ProductsAdapter.ProductHolder(view);
    }

    public static class ProductHolder extends RecyclerView.ViewHolder{

        private TextView product;

        public ProductHolder(View itemView) {
            super(itemView);

            product = itemView.findViewById(R.id.productTv);
        }
    }
}
