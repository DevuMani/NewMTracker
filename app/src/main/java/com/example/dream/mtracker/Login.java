package com.example.dream.mtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class  Login extends AppCompatActivity implements View.OnClickListener {

    private String GTAG="Google Activity";
    private String FTAG="Facebook Activity";
    private String PTAG="Phone Activity";

    SignInButton googleButton;
    Button phoneBtn;

    GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 2;
    private static final int REQUEST_CODE=999;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    //Facebook
    private CallbackManager mCallbackManager;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        showKeyHash();

        // Set the dimensions of the sign-in button.
        googleButton =findViewById(R.id.button_google);

        googleButton.setSize(SignInButton.SIZE_STANDARD);

        mAuth = FirebaseAuth.getInstance();


        googleButton.setOnClickListener(this);

        phoneBtn = findViewById(R.id.button_phone);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//Facebook

        mCallbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = findViewById(R.id.button_fb);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(FTAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(FTAG, "facebook:onCancel");
                Toast.makeText(Login.this, "User Canceled it", Toast.LENGTH_SHORT).show();
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(FTAG, "facebook:onError", error);
                Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]



//Account Kit

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLoginPage(LoginType.PHONE);
            }
        });
    }

    private void startLoginPage(LoginType loginType) {

        if(loginType == LoginType.PHONE)
        {
            Intent intent = new Intent(this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(
                            LoginType.PHONE,
                            AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
            // ... perform additional configuration ...
            intent.putExtra(
                    AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                    configurationBuilder.build());
            startActivityForResult(intent, REQUEST_CODE);

        }
    }


    void showKeyHash(){
        //To know our key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.dream.mtracker",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KKKKKKKKKKeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                Toast.makeText(this, "Key Hash"+Base64.encodeToString(md.digest(), Base64.DEFAULT), Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    @Override
    public void onClick(View view) {


        if(view==googleButton)
        {
            signIn();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(GTAG, "Google sign in failed", e);
                Toast.makeText(this, "Something went wrong in google sign in", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
        //Account kit
        else if(requestCode == REQUEST_CODE)
        {


            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String LogMessage;

            if (loginResult.getError() != null) {

                LogMessage = loginResult.getError().getErrorType().getMessage();
                Log.e(PTAG,LogMessage);
                return;
//                showErrorActivity(loginResult.getError());

            } else if (loginResult.wasCancelled()) {

                LogMessage = "Login Cancelled";
                Log.e(PTAG,LogMessage);
                return;

            } else {

                if (loginResult.getAccessToken() != null) {
                    LogMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    Toast.makeText(this,"loginResult.getAccessToken()"+LogMessage,Toast.LENGTH_LONG).show();

                    final String[] phoneNumberString = {""};

                    SharedPreferences sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
                    final SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("Login",true);
                    editor.putString("Type","phone");


                    //To see account kit user information
                        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                            @Override
                            public void onSuccess(final Account account) {
                                // Get Account Kit ID
                                String accountKitId = account.getId();

                                // Get phone number
                                PhoneNumber phoneNumber = account.getPhoneNumber();
                                if (phoneNumber != null) {

                                    phoneNumberString[0] = phoneNumber.toString();



                                }

    //                            // Get email
    //                            String email = account.getEmail();
                            }

                            @Override
                            public void onError(final AccountKitError error) {
                                // Handle Error
                            }
                        });


                        editor.putString("Number", phoneNumberString[0]);
                        editor.putBoolean("Password",false);
                        editor.apply();

                    SharedPreferences sp=getSharedPreferences("User",MODE_PRIVATE);
                    String num=sp.getString("Number","");
                    Log.d("Sharedpreference:::::::",sp.getString("Type","")+" "+sp.getString("Number",""));

//                    insertion();

                    goTo("phone");

//                    Intent intent=new Intent(Login.this,GoogleHome.class);
//                    intent.putExtra("Type","phone");
//                    startActivity(intent);

                } else {

                    LogMessage = String.format("Success:%s...",loginResult.getAuthorizationCode().substring(0,10));
                    Toast.makeText(this,LogMessage,Toast.LENGTH_LONG).show();

                    final String[] phoneNumberString = {""};

                    SharedPreferences sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
                    final SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("Login",true);
                    editor.putString("Type","phone");
                    editor.putBoolean("Password",false);

                    //To see account kit user information
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            // Get Account Kit ID
                            String accountKitId = account.getId();

                            // Get phone number
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            if (phoneNumber != null) {

                                phoneNumberString[0] = phoneNumber.toString();


                            }

                            //                            // Get email
                            //                            String email = account.getEmail();
                        }

                        @Override
                        public void onError(final AccountKitError error) {
                            // Handle Error
                        }
                    });


                    editor.putString("Number", phoneNumberString[0]);
                    editor.putString("Currency","");
                    editor.apply();

                    SharedPreferences sp=getSharedPreferences("User",MODE_PRIVATE);
                    Log.d("Sharedpreference:::::::",sp.getString("Type","")+" "+sp.getString("Number",""));

                    goTo("phone");
//                    Intent intent=new Intent(this,GoogleHome.class);
//                    intent.putExtra("Type","phone");
//                    startActivity(intent);

                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...

            }


        }



    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(GTAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(GTAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(GTAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {

         if(user.getDisplayName() != null) {

             Toast.makeText(this, "User found "+user.getDisplayName(), Toast.LENGTH_SHORT).show();

             SharedPreferences sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
             SharedPreferences.Editor editor=sharedPreferences.edit();
             editor.putBoolean("Login",true);
             editor.putString("Type","firebase");
             editor.putString("Name",user.getDisplayName());
             editor.putString("Email",user.getEmail());
             editor.putString("Phone",user.getPhoneNumber());
             editor.putString("Currency","");
             editor.putBoolean("Password",false);
             editor.apply();

//             insertion();

             SharedPreferences sp=getSharedPreferences("User",MODE_PRIVATE);
             Log.d("Sharedpreference:::::::",sp.getString("Type","")+" "+sp.getString("Name","null")+" "+sp.getString("Email","null")+" "+sp.getString("Phone","null"));

             goTo("firebase");
//             Intent intent = new Intent(this, GoogleHome.class);
//             intent.putExtra("Type","Firebase");
//             startActivity(intent);
            }


        } else {

            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();

        }
    }


//DB common insertion not successful
//    private void insertion() {
//
//        String type="";
//        String name="";
//        String email="";
//        String phone="";
//
//        DBFunction dbFunction=new DBFunction(Login.this);
//
//        SharedPreferences sp=getSharedPreferences("User",MODE_PRIVATE);
//        type=sp.getString("Type","");
//        if(type.equalsIgnoreCase("phone"))
//        {
//                phone=sp.getString("Number","null");
//        }
//        else if(type.equalsIgnoreCase("firebase"))
//        {
//            name=sp.getString("Name","null");
//            email=sp.getString("Email","null");
//            phone=sp.getString("Phone","null");
//        }
//
//
//        if(name.equalsIgnoreCase("null"))
//        {
//            name=null;
//        }
//        if(email.equalsIgnoreCase("null"))
//        {
//            email=null;
//        }
//        if(phone.equalsIgnoreCase("null"))
//        {
//            phone=null;
//        }
//
//
//        dbFunction.common_insert(name,email,phone);
//    }


    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {

        Log.d(FTAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]

        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(FTAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {

                            // If sign in fails, display a message to the user.
                            Log.w(FTAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);

                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]




    private void goTo(String type) {


        Intent intent = new Intent(this, Profile_Settings.class);
        intent.putExtra("Type",type);
        startActivity(intent);
    }
}
