package cl.octavionancul.myapplication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cl.octavionancul.myapplication.adapters.ProductsAdapter;
import cl.octavionancul.myapplication.adapters.ReviewsAdapter;
import cl.octavionancul.myapplication.adapters.ReviewsCallback;
import cl.octavionancul.myapplication.models.Product;
import cl.octavionancul.myapplication.models.Review;

import static android.content.Intent.getIntent;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment implements ReviewsCallback {

    private RecyclerView recyclerView;
    private ReviewsAdapter adapter;
    private FloatingActionButton fab;
    private TextView textView;

    public ReviewFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Product product = (Product) getActivity().getIntent().getSerializableExtra(ProductsFragment.PRODUCT);

        recyclerView = view.findViewById(R.id.reviewRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Review>()
                .setQuery(new Nodes().reviewsByProductId(product.getKey()),Review.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new ReviewsAdapter(options,this);
        recyclerView.setAdapter(adapter);

       textView = view.findViewById(R.id.noReviews);



    }

    @Override
    public void update(boolean reviewUser) {
        fab = getActivity().findViewById(R.id.fab);
        if(adapter.getItemCount()<1){
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.GONE);
        }
     recyclerView.scrollToPosition(adapter.getItemCount()-1);
        Log.d("fab", String.valueOf(reviewUser));
     if(reviewUser){
         if(fab!=null)
         fab.setVisibility(View.INVISIBLE);
     }
    }

 /*   @Override
    public void hideFab() {
        if(fab!=null) {
            if (fab.getVisibility() != View.INVISIBLE) {
                fab.setVisibility(View.INVISIBLE);
            }
        }
    }*/
}
