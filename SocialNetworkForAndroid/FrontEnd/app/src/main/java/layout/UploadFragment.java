package layout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.cmpe277labs.amipa.facebookclone.DbHelper;
import com.cmpe277labs.amipa.facebookclone.HTTPRequestHandler;
import com.cmpe277labs.amipa.facebookclone.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cmpe277labs.amipa.facebookclone.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class UploadFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Intent takePictureIntent = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static int REQUEST_IMAGE_CAPTURE = 2;
    static int REQUEST_IMAGE_CHOOSE = 2;
    private static final String IMAGE_LOCATION = "fbclone277";
    private static Uri selectedImage;
    private static String imgPath="";
    private byte[] ba;
    AppCompatImageView mImageView;
    private String img_upload;
    private String imagepaths3;
    String returnFromServer;
    private DbHelper dbHelper;
    boolean f=false;
    private File uploadimagepath ;
    private File file1 ;

    private AutoCompleteTextView mPosts;
    private String imageFileName;
    private SessionManager sessionManager;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private OnFragmentInteractionListener mListener;

    public UploadFragment() {
        // Required empty public constructor
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.CAMERA},
                UploadFragment.REQUEST_IMAGE_CAPTURE);

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment UploadFragment.
     */
    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        verifyStoragePermissions(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Log.d("status",mParam1);
        Log.d("Changing text colro","Changing");
        View v= inflater.inflate(R.layout.fragment_upload, container, false);

        mPosts = (AutoCompleteTextView) v.findViewById(R.id.textPost);
        mImageView=(AppCompatImageView)v.findViewById(R.id.uplaodedImage);
        dbHelper=new DbHelper(getActivity());

        final Button b=(Button)v.findViewById(R.id.captureButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Capture","Clicking 111111111111");
                takePictureIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file1 = getOutPutImageFile();
                selectedImage = Uri.fromFile(file1);
                Log.d("FILE URI","fiiiilee uri is"+selectedImage.toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);
                if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!=null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        final Button cb=(Button)v.findViewById(R.id.galleryButton);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePictureIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!=null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CHOOSE);
                }
            }
        });

        final Button ub=(Button)v.findViewById(R.id.uploadButton);
        ub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dbHelper.open();
                    if(selectedImage != null && imgPath != null) {
                        InputStream iStream = getActivity().getContentResolver().openInputStream(selectedImage);
                        byte[] inputData = getBytes(iStream);
                        dbHelper.insertImage(inputData);
                        dbHelper.close();

                        Log.d("snmds", "000000000000");



                        Random rand = new Random();

                        int rno = rand.nextInt(10000) + 1;

                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "FB_" + timeStamp + "_" + rno;

                        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials("AKIAIMQSOJIXK7SYTGBA", "fJPbPabRqUTIjwd0rEsdmlKjOeGbDXO2Dyt8kq4V"));

                        //s3Client.createBucket( "iamgeupload" );

                        PutObjectRequest por = new PutObjectRequest("cmpefb", imageFileName, file1);

                        por.setCannedAcl(CannedAccessControlList.PublicRead);

                        s3Client.putObject(por);
                        ResponseHeaderOverrides override = new ResponseHeaderOverrides();
                        override.setContentType("image/jpeg");

                        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest("cmpefb", imageFileName);
                        // Added an hour's worth of milliseconds to the current time.
                        urlRequest.setExpiration(new Date(System.currentTimeMillis() + 3600000));
                        urlRequest.setResponseHeaders(override);


                        URL url = s3Client.generatePresignedUrl(urlRequest);


                        imagepaths3 = "https://s3-us-west-1.amazonaws.com/cmpefb" + url.getPath();

                        Log.d("path:", imagepaths3);
                    }
                    HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
                    sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());

                    String email = sessionManager.getEmail();

                    JSONObject dataJSON = new JSONObject();
                    JSONObject paramObject = new JSONObject();
                    JSONArray paramt = new JSONArray();
                    final String posts = mPosts.getText().toString();

                    paramObject.put("email", email);
                    paramObject.put("postText", posts);

                    if(imagepaths3 == "")
                    {
                        paramObject.put("postImages", paramt);
                    }
                    else {
                        paramt.put(imagepaths3);
                        paramObject.put("postImages", paramt);
                    }
                    //String paramt = add.toString();
                    dataJSON.put("data", paramObject);
                    Log.d("Meet", "params "+dataJSON.toString());

                    httpRequestHandler.sendHTTPRequest("/createPost", dataJSON, new HTTPRequestHandler.VolleyCallback(){
                        @Override
                        public void onSuccess(JSONObject result) throws JSONException {

                            Log.d("Meet", "result here "+result.toString());

                            String status = result.get("status").toString();
                            String res = result.get("result").toString();

                            if(status.equals("200"))
                            {
                                Log.d("","valid Image");
                                //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                //startActivity(i);
                                Toast.makeText(getContext(), "Post created!", Toast.LENGTH_SHORT).show();
                            }
                            else if(status.equals("400"))
                            {
                                Log.d("", "Wrong Image or Posts");
                            }

                        }
                    });


                }
                catch (IOException ioe) {
                    Log.d("nsdmnsmd","1.0101010");
                    Log.e("Save in DB Error", "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
                    dbHelper.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

         return v;
    }
    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data==null) {
            Toast.makeText(getActivity(), "Image Captured", Toast.LENGTH_SHORT)
                    .show();
            mImageView.setImageURI(selectedImage);
            mImageView.setRotation(270);
        }
        else if(requestCode==REQUEST_IMAGE_CHOOSE && resultCode == RESULT_OK && data!=null) {
            Log.d("IMAGE CHOOSER","2.2.2.2.2.2.2.Image chooser");
            selectedImage = data.getData();
            imgPath=getRealPathFromURI(selectedImage);
            file1 = new File(imgPath);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), selectedImage);
                mImageView.setImageBitmap(bitmap);
                mImageView.setMaxHeight(bitmap.getHeight());
                mImageView.setMaxWidth(bitmap.getWidth());

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
        UploadFragment.imgPath = image.getAbsolutePath();
        return image;
    }
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);

    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
       } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
       }
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
        void onFragmentInteraction();
    }
}
