package cl.octavionancul.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cl.octavionancul.myapplication.models.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        Product product = new Product();
        String key = new Nodes().products().push().getKey();
        product.setName("product 3");
        product.setUid(new CurrentUser().uid());
        product.setKey(key);
    //    new Nodes().products().push().setValue(product);

        new Nodes().products().child(key).setValue(product);

        Product product1 = new Product();
        String key1 = new Nodes().products().push().getKey();
        product1.setName("product 4");
        product1.setUid(new CurrentUser().uid());
        product1.setKey(key1);
        //    new Nodes().products().push().setValue(product);

        new Nodes().products().child(key1).setValue(product1);

    */
        ImageView logout = findViewById(R.id.logoutIv);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This is what you are looking for
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("yourTag");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DialogFragment dialogFragment = ProductInputDialogFragment.newInstance();
                dialogFragment.show(ft, "yourTag");
            }
        });
    }

}
