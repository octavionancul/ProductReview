package cl.octavionancul.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import cl.octavionancul.myapplication.models.Product;

public class ProductInputDialogFragment extends DialogFragment {

    public static ProductInputDialogFragment newInstance() {
        return new ProductInputDialogFragment();
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
        return inflater.inflate(R.layout.dialog_fragment_product_input, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editText = view.findViewById(R.id.productEt);
        ImageButton imageButton = view.findViewById(R.id.saveBtn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editText.getText().toString();

                if(name.trim().length()>0) {
                    Product product = new Product();
                    String key = new Nodes().products().push().getKey();
                    product.setName(name);
                    product.setUid(new CurrentUser().uid());
                    product.setKey(key);
                    //    new Nodes().products().push().setValue(product);
                    new Nodes().products().child(key).setValue(product);
                    dismiss();
                }else{
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
