package layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.cmpe277labs.amipa.facebookclone.HTTPRequestHandler;
import com.cmpe277labs.amipa.facebookclone.LoginActivity;
import com.cmpe277labs.amipa.facebookclone.MainActivity;
import com.cmpe277labs.amipa.facebookclone.R;
import com.cmpe277labs.amipa.facebookclone.SessionManager;
import com.cmpe277labs.amipa.facebookclone.SignUpActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


import static android.app.Activity.RESULT_OK;
import static android.support.v7.appcompat.R.styleable.AppCompatImageView;
import static android.widget.Toast.LENGTH_SHORT;
import static layout.UploadFragment.REQUEST_IMAGE_CHOOSE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 */



public class SettingsFragment extends Fragment {

    private SessionManager sessionManager;
    private static Uri selectedImage;
    private static String imgPath="";
    private File file1 ;
    private String imageFileName;
    private File uploadimagepath ;
    private String imagepaths3;
    View v1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "settings";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Intent takePictureIntent = null;
    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor

    }
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        System.out.print("Inside settings fragmemnt new Instance *********************");
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.print("onCreate *********************");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


         v1 = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        final ImageButton b=(ImageButton)v1.findViewById(R.id.profilepic);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               Log.d("snmds","000000000000");

                takePictureIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!=null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CHOOSE);
                }

            }
        });

        // Inflate the layout for this fragment
        Log.d("222222","0000");
        sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());
        String email = sessionManager.getEmail();
        JSONObject param = new JSONObject();
        JSONObject msgPayload = new JSONObject();
        try{
            param.put("email",email);
            msgPayload.put("data",param);
        }catch(JSONException e){
            e.printStackTrace();
        }


        final HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();

        httpRequestHandler.sendHTTPRequest("/getProfile", msgPayload, new HTTPRequestHandler.VolleyCallback(){
            @Override
            public void onSuccess(JSONObject result) throws JSONException {

                Log.d("PAVAN", "result here "+result.toString());

                String status = result.get("status").toString();
                JSONObject resultJson = (JSONObject)result.get("result");
                String res = result.get("result").toString();

                if(status.equals("200"))
                {
                    ((EditText)v1.findViewById(R.id.screenname_text)).setText(resultJson.get("screenName").toString());
                    ((EditText)v1.findViewById(R.id.first_name_text)).setText(resultJson.get("firstName").toString());
                    ((EditText)v1.findViewById(R.id.last_name_text)).setText(resultJson.get("lastName").toString());
                    ((EditText)v1.findViewById(R.id.address_text)).setText(resultJson.get("address").toString());
                    ((EditText)v1.findViewById(R.id.profession_text)).setText(resultJson.get("profession").toString());
                    ((EditText)v1.findViewById(R.id.profession_text)).setText(resultJson.get("profession").toString());
                    ((EditText)v1.findViewById(R.id.aboutme_text)).setText(resultJson.get("aboutMe").toString());
                    ((EditText)v1.findViewById(R.id.interests_text)).setText(resultJson.get("interests").toString());

                    imagepaths3 = resultJson.get("profilePic").toString();


                   ImageButton imagebutton = ((ImageButton) v1.findViewById(R.id.profilepic));

                    Picasso.with(httpRequestHandler.getBaseContext()).load(resultJson.get("profilePic").toString()).into(imagebutton);

                    String notification = resultJson.get("notification").toString();
                    if(notification.equals("disable")){
                        ((RadioButton)v1.findViewById(R.id.disable_notification_radio)).toggle();
                    }else{
                        ((RadioButton)v1.findViewById(R.id.enable_notification_radio)).toggle();
                    }

                    String visibility = resultJson.get("visibility").toString();
                    if(visibility.equals("public")){
                        ((RadioButton)v1.findViewById(R.id.public_visibility_radio)).toggle();
                    }else{
                        ((RadioButton)v1.findViewById(R.id.friendsOnly_visibility_radio)).toggle();
                    }

                }
                else if(status.equals("400"))
                {
                    Toast.makeText(getContext(), "Error loading data!", Toast.LENGTH_LONG).show();
                    Log.d("VANSH", "ERROR");
                }

            }
        });



        RadioButton enableRadioButton = (RadioButton)v1.findViewById(R.id.enable_notification_radio);
        RadioButton disableRadioButton = (RadioButton)v1.findViewById(R.id.disable_notification_radio);
        RadioButton publicRadioButton = (RadioButton)v1.findViewById(R.id.public_visibility_radio);
        RadioButton friendsOnlyRadioButton = (RadioButton)v1.findViewById(R.id.friendsOnly_visibility_radio);
        enableRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject msgPayload = new JSONObject();
                JSONObject param = new JSONObject();

                try{
                    param.put("email",sessionManager.getEmail());
                    param.put("notification","enable");
                    msgPayload.put("data",param);
                }catch(JSONException e){
                    e.printStackTrace();
                }

                httpRequestHandler.sendHTTPRequest("/setNotificationOption", msgPayload, new HTTPRequestHandler.VolleyCallback(){
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {

                        Log.d("PAVAN", "result here "+result.toString());

                        String status = result.get("status").toString();

                        if(status.equals("200"))
                        {
                            Toast.makeText(getContext(), "Notification preference changed!", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equals("400"))
                        {
                            Toast.makeText(getContext(), "Error changing notification option!", Toast.LENGTH_LONG).show();
                            Log.d("VANSH", "ERROR");
                        }

                    }
                });
            }
        });




        disableRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject msgPayload = new JSONObject();
                JSONObject param = new JSONObject();

                try{
                    param.put("email",sessionManager.getEmail());
                    param.put("notification","disable");
                    msgPayload.put("data",param);
                }catch(JSONException e){
                    e.printStackTrace();
                }

                httpRequestHandler.sendHTTPRequest("/setNotificationOption", msgPayload, new HTTPRequestHandler.VolleyCallback(){
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {

                        Log.d("PAVAN", "result here "+result.toString());

                        String status = result.get("status").toString();

                        if(status.equals("200"))
                        {
                            Toast.makeText(getContext(), "Notification preference changed!", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equals("400"))
                        {
                            Toast.makeText(getContext(), "Error changing notification option!", Toast.LENGTH_LONG).show();
                            Log.d("VANSH", "ERROR");
                        }

                    }
                });
            }
        });

        publicRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject msgPayload = new JSONObject();
                JSONObject param = new JSONObject();

                try{
                    param.put("email",sessionManager.getEmail());
                    param.put("visibilityLevel","public");
                    msgPayload.put("data",param);
                }catch(JSONException e){
                    e.printStackTrace();
                }

                httpRequestHandler.sendHTTPRequest("/setVisibilityLevel", msgPayload, new HTTPRequestHandler.VolleyCallback(){
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {

                        Log.d("PAVAN", "result here "+result.toString());

                        String status = result.get("status").toString();

                        if(status.equals("200"))
                        {
                            Toast.makeText(getContext(), "Visibility level changed!", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equals("400"))
                        {
                            Toast.makeText(getContext(), "Error changing visibility level!", Toast.LENGTH_LONG).show();
                            Log.d("VANSH", "ERROR");
                        }

                    }
                });
            }
        });

        friendsOnlyRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject msgPayload = new JSONObject();
                JSONObject param = new JSONObject();

                try{
                    param.put("email",sessionManager.getEmail());
                    param.put("visibilityLevel","friendsOnly");
                    msgPayload.put("data",param);
                }catch(JSONException e){
                    e.printStackTrace();
                }

                httpRequestHandler.sendHTTPRequest("/setVisibilityLevel", msgPayload, new HTTPRequestHandler.VolleyCallback(){
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {

                        Log.d("PAVAN", "result here "+result.toString());

                        String status = result.get("status").toString();

                        if(status.equals("200"))
                        {
                            Toast.makeText(getContext(), "Visibility level changed!", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equals("400"))
                        {
                            Toast.makeText(getContext(), "Error changing visibility level!", Toast.LENGTH_LONG).show();
                            Log.d("VANSH", "ERROR");
                        }

                    }
                });
            }
        });
        Button logout = (Button)v1.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.setEmail("");
                sessionManager.setPassword("");
                Intent i=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });


        Button updateProfile = (Button)v1.findViewById(R.id.update_profile_button);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rand = new Random();

                int  rno = rand.nextInt(10000) + 1;

                if (android.os.Build.VERSION.SDK_INT > 9)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "FB_" + timeStamp + "_"+rno;

                AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials( "AKIAIMQSOJIXK7SYTGBA","fJPbPabRqUTIjwd0rEsdmlKjOeGbDXO2Dyt8kq4V" ) );

                //s3Client.createBucket( "iamgeupload" );
                Log.d("file 1 value",imageFileName);
                Log.d("file 1 value",file1.toString());

                PutObjectRequest por =    new PutObjectRequest( "cmpefb", imageFileName,  file1  );

                por.setCannedAcl(CannedAccessControlList.PublicRead);

                s3Client.putObject( por );
                ResponseHeaderOverrides override = new ResponseHeaderOverrides();override.setContentType( "image/jpeg" );

                GeneratePresignedUrlRequest urlRequest =    new GeneratePresignedUrlRequest( "cmpefb",  imageFileName);
                // Added an hour's worth of milliseconds to the current time.
                urlRequest.setExpiration(    new Date( System.currentTimeMillis() + 3600000 ) );
                urlRequest.setResponseHeaders( override );


                URL url = s3Client.generatePresignedUrl( urlRequest );



                imagepaths3 = "https://s3-us-west-1.amazonaws.com/cmpefb"+url.getPath();

                Log.d("path:",imagepaths3);

                JSONObject msgPayload = new JSONObject();
                JSONObject param = new JSONObject();

                try{

                    param.put("email",sessionManager.getEmail());
                    param.put("screenName", ((EditText)v1.findViewById(R.id.screenname_text)).getText());
                    param.put("firstName", ((EditText)v1.findViewById(R.id.first_name_text)).getText());
                    param.put("lastName", ((EditText)v1.findViewById(R.id.last_name_text)).getText());
                    //@MEET: tane ahiya url ni badle upload kareli url nakhvani!!....and jo user
                    // change kare j nai ne image to same j url revi joie je database mathi aavi hati e
                    param.put("profilePic", imagepaths3);
                    param.put("address", ((EditText)v1.findViewById(R.id.address_text)).getText());
                    param.put("profession", ((EditText)v1.findViewById(R.id.profession_text)).getText());
                    param.put("aboutMe", ((EditText)v1.findViewById(R.id.aboutme_text)).getText());
                    param.put("interests", ((EditText)v1.findViewById(R.id.interests_text)).getText());
                    msgPayload.put("data",param);
                }catch(JSONException e){
                    e.printStackTrace();
                }

                httpRequestHandler.sendHTTPRequest("/updateProfile", msgPayload, new HTTPRequestHandler.VolleyCallback(){
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {

                        Log.d("PAVAN", "result here "+result.toString());

                        String status = result.get("status").toString();

                        if(status.equals("200"))
                        {
                            Toast.makeText(getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equals("400"))
                        {
                            Toast.makeText(getContext(), result.get("result").toString(), Toast.LENGTH_LONG).show();
                            Log.d("VANSH", "ERROR");
                        }

                    }
                });
            }
        });


        return v1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

         if(requestCode==REQUEST_IMAGE_CHOOSE && resultCode == RESULT_OK && data!=null) {
            Log.d("IMAGE CHOOSER","2.2.2.2.2.2.2.Image chooser");
            selectedImage = data.getData();
            imgPath=getRealPathFromURI(selectedImage);
             file1 = new File(imgPath);

             Log.d("meet", imgPath);



                Picasso.with(HTTPRequestHandler.getMyContext()).load(data.getData()).noPlaceholder().centerCrop().fit()
                        .into((ImageButton)v1.findViewById(R.id.profilepic));




        }

    }

    private static File getOutPutImageFile(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "FB_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d("Storage dire","...1.1.1.1.1.1.1.1..1.1.1.1"+storageDir);
        File image = null;
        try {
            image = File.createTempFile(imageFileName,".jpg",storageDir);
            Log.d("Storage dire","...1.1.1.1.1.1.1.1..1.1.1.1"+image);
            Log.d("imagename",imageFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        SettingsFragment.imgPath = image.getAbsolutePath();
        return image;
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }
}
