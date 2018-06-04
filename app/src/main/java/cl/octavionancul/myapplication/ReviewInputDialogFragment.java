package cl.octavionancul.myapplication;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import cl.octavionancul.myapplication.models.Product;
import cl.octavionancul.myapplication.models.Review;

public class ReviewInputDialogFragment extends android.support.v4.app.DialogFragment {

    public static ReviewInputDialogFragment newInstance() {
        return new ReviewInputDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_review_input, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editText = view.findViewById(R.id.productEt);
        ImageButton imageButton = view.findViewById(R.id.saveBtn);
        editText.setHint("Escribe un review");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editText.getText().toString();

                if (name.trim().length() > 0) {

                    /*Product product = new Product();
                    String key = new Nodes().products().push().getKey();
                    product.setName(name);
                    product.setUid(new CurrentUser().uid());
                    product.setKey(key);
                    //    new Nodes().products().push().setValue(product);
                    new Nodes().products().child(key).setValue(product);
                    dismiss();*/

                    Product product = (Product) getActivity().getIntent().getSerializableExtra(ProductsFragment.PRODUCT);

                    Review review = new Review();

                    String key = new Nodes().reviews().push().getKey();
                    review.setUid(new CurrentUser().uid());
                    review.setContent(name);
                    review.setProductid(product.getKey());
                    review.setUser(new CurrentUser().userName());
                    review.setKey(key);


                    new Nodes().reviews().child(product.getKey()).child(key).setValue(review);
                    dismiss();

                 Log.d("message",name);
                } else {
                    //  editText.setError("");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );

    }
}