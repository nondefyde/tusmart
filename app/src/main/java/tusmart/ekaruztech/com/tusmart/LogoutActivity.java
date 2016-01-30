package tusmart.ekaruztech.com.tusmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import tusmart.ekaruztech.com.tusmart.FacebookSharedPrefernce.PrefUtils;
import tusmart.ekaruztech.com.tusmart.FacebookSharedPrefernce.User;

public class LogoutActivity extends AppCompatActivity {

    private TextView btnLogout;
    private TextView email;
    private TextView name;
    private TextView gender;
    ProfilePictureView profile;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        profile = (ProfilePictureView)findViewById(R.id.picture);
        email = (TextView) findViewById(R.id.email);
        name = (TextView) findViewById(R.id.name);
        gender = (TextView) findViewById(R.id.gender);

        user= PrefUtils.getCurrentUser(LogoutActivity.this);


//        Intent intent = getIntent();
//        String name1 = intent.getStringExtra("name");
//        String gender1 = intent.getStringExtra("gender");
//        String email1 = intent.getStringExtra("email");
//        String facebookID = intent.getStringExtra("facebookID");
//
//        email.setText(email1);
//        name.setText(name1);
//        gender.setText(gender1);
//        profile.setProfileId(facebookID);

        email.setText(user.email);
        name.setText(user.name);
        gender.setText(user.gender);
        profile.setProfileId(user.facebookID);

        btnLogout = (TextView) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.clearCurrentUser(LogoutActivity.this);
                // We can logout from facebook by calling following method
                LoginManager.getInstance().logOut();

                Intent i= new Intent(LogoutActivity.this,SignInActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
