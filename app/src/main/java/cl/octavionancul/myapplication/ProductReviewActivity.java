package cl.octavionancul.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import cl.octavionancul.myapplication.models.Product;
import cl.octavionancul.myapplication.models.Review;

public class ProductReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Product product = (Product) getIntent().getSerializableExtra(ProductsFragment.PRODUCT);

        getSupportActionBar().setTitle(product.getName());
        Log.d("product",product.getName());

       /* Review review = new Review();
       // String key = new Nodes().reviews().push().getKey();
        review.setUid(new CurrentUser().uid());
        review.setContent("conntent 1");
        new Nodes().reviews().child(product.getKey()).push().setValue(review);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This is what you are looking for
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("tag");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DialogFragment dialogFragment = ReviewInputDialogFragment.newInstance();
                dialogFragment.show(ft, "tag");


            }
        });
    }

}
