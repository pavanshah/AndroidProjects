package com.cmpe277labs.amipa.facebookclone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import layout.EditProfile;
import layout.MessageFragment;
import layout.PostsFragment;
import layout.RequestsFragment;
import layout.SearchFragment;
import layout.SettingsFragment;
import layout.TimelineFragment;
import layout.UploadFragment;

import static com.cmpe277labs.amipa.facebookclone.R.id.container;
import static com.cmpe277labs.amipa.facebookclone.R.id.main_content;
import static com.cmpe277labs.amipa.facebookclone.R.id.search_mag_icon;
import static com.cmpe277labs.amipa.facebookclone.R.id.up;

public class MainActivity extends AppCompatActivity implements UploadFragment.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public String mn;
    public BottomNavigationView bmv;
    private View viewLayout;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_IMAGE_CHOOSE = 100;
    private static final String IMAGE_LOCATION = "fbclone277";
    static int REQUEST_IMAGE_UPLOAD = 0;
    private Uri selectedImage;
    private String imgPath;
    private byte[] ba;
    Button btnJumpToCam;
    Button btnUpload, btnGallery, btnPostModel;
    TextView txtRes;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";
    AppCompatImageView mImageView;
    AppCompatImageView mImageView2;
    private String img_upload;
    String returnFromServer;
    private Uri fileUri;
    boolean f=false;
    static ListView lv;
    static Context context;

    ArrayList prgmName;
    public static int [] prgmImages={R.drawable.ami,R.drawable.vansh,R.drawable.pavan,R.drawable.meet};
    public static String [] prgmNameList={"Ami","Vansh","Pavan","Meet"};

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_storage_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_file_upload_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_search_black_24dp);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_message_black_24dp);
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_settings_black_24dp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction() {
        Button captureButton = (Button) findViewById(R.id.captureButton);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        });
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

           switch (position){
               case 0:
                   Fragment timelineFragment1=new TimelineFragment();
                   return timelineFragment1;
               case 1:
                   Fragment postsFragment=new PostsFragment();
                   return postsFragment;

               case 2:
                   Fragment uploadFragment=new UploadFragment();
                   return uploadFragment;

               case 3:
                   Fragment searchFragment=new SearchFragment();
                   return searchFragment;
               case 4:
                   Fragment messageFragment=new MessageFragment();
                   return messageFragment;

               case 5:
                   Fragment settingsFragment=new SettingsFragment();
                   return settingsFragment;

           }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {

            }
            return null;
        }
    }
}
