package tusmart.ekaruztech.com.tusmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import tusmart.ekaruztech.com.tusmart.FacebookSharedPrefernce.PrefUtils;
import tusmart.ekaruztech.com.tusmart.FacebookSharedPrefernce.User;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView btnLogin;
    private ProgressDialog progressDialog;

    Bundle userData = new Bundle();
    Intent newIntent;

    String facebookID;
    String email;
    String gender;
    String name;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///
        FacebookSdk.sdkInitialize(getApplicationContext());
        ///
        setContentView(R.layout.activity_sign_in);

        loginButton = (LoginButton) findViewById(R.id.login_button);
//        LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this, Arrays.asList("public_profile", "email"));
//
        if(PrefUtils.getCurrentUser(SignInActivity.this) != null){

            Intent homeIntent = new Intent(SignInActivity.this, LogoutActivity.class);

            startActivity(homeIntent);

            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        callbackManager = CallbackManager.Factory.create();

        btnLogin = (TextView) findViewById(R.id.btnLogin);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
//        btnLogin.setOnClickListener(new View.OnClickListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(SignInActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

//                loginButton.performClick();
//
//                loginButton.setPressed(true);
//
//                loginButton.invalidate();

                loginButton.registerCallback(callbackManager, mCallBack);

//                loginButton.setPressed(false);
//
//                loginButton.invalidate();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Msg:" + requestCode);
    }


    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            progressDialog.dismiss();

            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            Log.e("response: ", response + "");
                            JSONObject json = response.getJSONObject();
                            try {
//                                facebookID = json.getString("id");
//                                email = json.getString("email");
//                                name = json.getString("name");
//                                gender = json.getString("gender");
//                                userData.putString("facebookID", facebookID);
//                                userData.putString("email", email);
//                                userData.putString("name", name);
//                                userData.putString("gender", gender);

                                user = new User();
                                user.facebookID =json.getString("id");
                                user.email = json.getString("email");
                                user.name = json.getString("name");
                                user.gender = json.getString("gender");
                                PrefUtils.setCurrentUser(user,SignInActivity.this);


//                            } catch (Exception e) {
                            } catch (JSONException e) {
                                Log.d(TAG, "Failure");
                                e.printStackTrace();
                            }
                            newIntent = new Intent(SignInActivity.this, LogoutActivity.class);
//                            newIntent.putExtras(userData);
                            startActivity(newIntent);
//                            finish();

                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);

            request.executeAsync();
        }

        @Override
        public void onCancel() {
            progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {
            progressDialog.dismiss();
            Log.d(TAG, "Error:" + e);
        }
    };

}

