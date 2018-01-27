package com.pvanshah.sjsuquizapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;

import java.util.HashMap;

public class LoginSignupActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static View rootView = null;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static FirebaseAuth mAuth = FirebaseConfiguration.getmAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            switch(getArguments().getInt(ARG_SECTION_NUMBER))
            {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_login, container, false);

                    //All declarations
                    final EditText email = (EditText) rootView.findViewById(R.id.loginEditText);
                    final EditText password = (EditText) rootView.findViewById(R.id.passwordEditText);
                    Button login = (Button) rootView.findViewById(R.id.loginButton);

                    //All listeners
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String useremail = email.getText().toString();
                            String userpassword = password.getText().toString();

                            Intent professorIntent = new Intent(getActivity(), ProfessorHomeActivity.class);
                            getActivity().finish(); //to remove login from activity stack of back button
                            startActivity(professorIntent);

                            /*
                            if(!useremail.equals("") && !userpassword.equals(""))
                            {
                                mAuth.signInWithEmailAndPassword(useremail, userpassword)
                                        .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Log.d("firebase", "Sign in success");
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();

                                                    DatabaseReference applicationUsers = FirebaseConfiguration.getApplicationUsers();

                                                    //Check user role
                                                    Query mQuery = applicationUsers.orderByChild("email").equalTo(useremail);
                                                    mQuery.addChildEventListener(new ChildEventListener() {
                                                        @Override
                                                        public void onChildAdded(DataSnapshot dataSnapshot,  String s) {
                                                            HashMap ob = (HashMap) dataSnapshot.getValue();
                                                            String role = (String) ob.get("role");

                                                            if(role.equals("Professor"))
                                                            {
                                                                Intent professorIntent = new Intent(getActivity(), ProfessorHomeActivity.class);
                                                                getActivity().finish(); //to remove login from activity stack of back button
                                                                startActivity(professorIntent);
                                                            }
                                                            else
                                                            {

                                                            }
                                                        }

                                                        @Override
                                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                                        }

                                                        @Override
                                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                                        }

                                                        @Override
                                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });

                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    Log.w("firebase", "signInWithEmail:failure", task.getException());
                                                    Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                                                    //updateUI(null);
                                                }
                                            }
                                        });

                            }
                            else
                            {
                                Toast.makeText(getContext(), "Please enter your credentials", Toast.LENGTH_SHORT).show();
                            }
                            */


                        }
                    });

                    break;

                case 2:
                    rootView = inflater.inflate(R.layout.fragment_signup, container, false);

                    //All declarations
                    final EditText firstName = (EditText) rootView.findViewById(R.id.firstnameEditText);
                    final EditText lastName = (EditText) rootView.findViewById(R.id.lastnameEditText);
                    final EditText signupemail = (EditText) rootView.findViewById(R.id.emailEditText);
                    final EditText uid = (EditText) rootView.findViewById(R.id.useridEditText);
                    final EditText signuppassword = (EditText) rootView.findViewById(R.id.passwordEditText);
                    Button signup = (Button) rootView.findViewById(R.id.signupButton);
                    TextView showPassword = (TextView) rootView.findViewById(R.id.showPasswordTextView);
                    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    //All listeners
                    signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String userFirstName = firstName.getText().toString();
                            String userLastName = lastName.getText().toString();
                            String userEmail = signupemail.getText().toString().trim();
                            String userId = uid.getText().toString();
                            String userPassword = signuppassword.getText().toString();


                            if(!userFirstName.equals("") && !userLastName.equals("") && !userEmail.equals("") && !userPassword.equals("") && !userId.equals(""))
                            {

                                if(userEmail.matches(emailPattern))
                                {
                                    createCredentialsAndProfile(userFirstName, userLastName, userEmail, userPassword, userId);
                                }
                                else
                                {
                                    signupemail.setError(getString(R.string.error_incorrect_email));
                                    signupemail.requestFocus();
                                }

                            }
                            else
                            {
                                Toast.makeText(getContext(), "Please enter all the values", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    break;
            }
            return rootView;
        }



        public void createCredentialsAndProfile(final String userFirstName, final String userLastName, final String userEmail, final String userPassword, final String userId)
        {
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("firebase", "Signup success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getContext(), "Signup successful", Toast.LENGTH_SHORT).show();

                                ApplicationUser applicationUser = new ApplicationUser(userFirstName, userLastName, userEmail, userId);
                                DatabaseReference applicationUsers = FirebaseConfiguration.getApplicationUsers();
                                applicationUsers.push().setValue(applicationUser);

                            } else {
                                // If sign in fails, display a message to the user.

                                try {
                                    throw task.getException();
                                }
                                catch(Exception e) {
                                    Log.d("firebase", "error "+e.getMessage());
                                    //password exception
                                    EditText mypassword = (EditText) rootView.findViewById(R.id.passwordEditText);
                                    mypassword.setError(getString(R.string.error_weak_password));
                                    mypassword.requestFocus();
                                }


                            }
                        }
                    });

        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Login";
                case 1:
                    return "Sign Up";
            }
            return null;
        }
    }
}
