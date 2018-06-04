package cl.octavionancul.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

import cl.octavionancul.myapplication.adapters.ProductsAdapter;
import cl.octavionancul.myapplication.adapters.ProductsCallback;
import cl.octavionancul.myapplication.models.Product;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements ProductsCallback {

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    public static final String PRODUCT = "cl.octavionancul.flash.views.main.chats.KEY.PRODUCT";
    private  FirebaseRecyclerOptions<Product> options;
   // private FloatingActionButton fab;
    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       //  fab = view.findViewById(R.id.fab);

        recyclerView = view.findViewById(R.id.productRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(new Nodes().productsByUid(new CurrentUser().uid()),Product.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new ProductsAdapter(options,this);
        recyclerView.setAdapter(adapter);

        final RadioGroup radioGroup = view.findViewById(R.id.ownerRg);
        RadioButton radioButton = view.findViewById(R.id.myRb);
        radioButton.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton1 = radioGroup.findViewById(selectedId);
                String owner = radioButton1.getText().toString();
                Query query;

                if(owner.equals("Mis productos")){
                    query = new Nodes().productsByUid(new CurrentUser().uid());
                }else {
                    query = new Nodes().products();
                }

                FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .setLifecycleOwner(getActivity())
                        .build();

                adapter = new ProductsAdapter(options, ProductsFragment.this);
                recyclerView.setAdapter(adapter);

            }
        });

    }

    @Override
    public void update() {
        recyclerView.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    public void clicked(Product product) {

        Intent intent = new Intent(getActivity(),ProductReviewActivity.class);
        intent.putExtra(PRODUCT,product);
        startActivity(intent);

    }

   /* @Override
    public void hide() {
    //  fab.setVisibility(View.GONE);
    }*/
}
