package cl.octavionancul.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;

import cl.octavionancul.myapplication.models.User;

public class LoginActivity extends AppCompatActivity {
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(new CurrentUser().getCurrentUser()!=null){
            logged();
        }else{
            signUp();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (resultCode == RESULT_OK) {
            logged();

        }else{

            // Sign in failed
            if (response == null) {
                // User pressed back button
               // showSnackbar(R.string.sign_in_cancelled);
                Toast.makeText(this, "cancelado", Toast.LENGTH_SHORT).show();
//                finish();
                return;
            }

            if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
              //  showSnackbar(R.string.no_internet_connection);
                Toast.makeText(this, "sin internet", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "error desconocido", Toast.LENGTH_SHORT).show();
            //showSnackbar(R.string.unknown_error);
           // Log.e(TAG, "Sign-in error: ", response.getError());

        }
    }

    private void signUp(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.FacebookBuilder().build()/*,
                                new AuthUI.IdpConfig.TwitterBuilder().build()*/))
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.mipmap.reviewlogo)
                        .build(),
                RC_SIGN_IN);

    }

private void logged(){
    CurrentUser currentUser = new CurrentUser();

    String url = "";
    User user = new User();
    user.setEmail(currentUser.email());
    user.setName(currentUser.getCurrentUser().getDisplayName());
    user.setPhoto(url);
    user.setUid(currentUser.uid());
    String key = currentUser.email().replace("@","AT").replace(".","DOT");
    //   FirebaseDatabase.getInstance().getReference().push().setValue(user)
    // FirebaseDatabase.getInstance().getReference().child("users").child(key).setValue(user);
    //new Nodes().users().child(key).setValue(user);

    new Nodes().user(key).setValue(user);

    Intent intent = new Intent(this,MainActivity.class);
    startActivity(intent);
    finish();

}

}
