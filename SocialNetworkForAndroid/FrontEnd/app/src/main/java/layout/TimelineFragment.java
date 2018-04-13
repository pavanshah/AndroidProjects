package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cmpe277labs.amipa.facebookclone.CustomAdapter;
import com.cmpe277labs.amipa.facebookclone.HTTPRequestHandler;
import com.cmpe277labs.amipa.facebookclone.R;
import com.cmpe277labs.amipa.facebookclone.SessionManager;
import com.cmpe277labs.amipa.facebookclone.TimelineInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimelineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Us tehe {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SessionManager sessionManager;
    ArrayList<TimelineInfo> arrayList = new ArrayList<>();
    CustomAdapter customAdapter;

    ListView lv;
    Context context;

    private OnFragmentInteractionListener mListener;

    public TimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineFragment newInstance(String param1, String param2) {
        TimelineFragment fragment = new TimelineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("post", "inside oncreate");

    }


    public void fetchDataForTimeline(){
        Log.d("pavan1", "inside fetchDataForTimeline");
        final HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
        sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());


        Log.d("pavan1", "timeline email "+sessionManager.getEmail());
        JSONObject resultObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put("email", sessionManager.getEmail());
            resultObject.put("data", dataObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpRequestHandler.sendHTTPRequest("/fetchTimeline", resultObject, new HTTPRequestHandler.VolleyCallback(){
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                Log.d("pavan1", "result here "+result.toString());
                arrayList.clear();

                JSONArray jsonArray = result.getJSONArray("result");
                Log.d("pavan1", "jsonarray "+jsonArray);
                Log.d("pavan1", "jsonarray "+jsonArray.length());

               // arrayList = new ArrayList<TimelineInfo>();

                for(int i = 0 ; i < jsonArray.length() ; i++)
                {
                    TimelineInfo timelineInfo = new TimelineInfo();
                    Log.d("pavan1", "result "+i);
                    JSONObject resultJSON = (JSONObject) jsonArray.get(i);
                    String screenname = resultJSON.getString("screenName");
                    JSONObject postObject = resultJSON.getJSONObject("post");
                    String postText = postObject.getString("postText");
                    JSONArray postImages = postObject.getJSONArray("postImages");
                    String[] postImagesArray = new String[postImages.length()];

                    for (int j = 0 ; j < postImagesArray.length ; j++)
                    {
                        postImagesArray[j] = postImages.getString(j);
                    }

                    timelineInfo.setScreenname(screenname);
                   // timelineInfo.setRelationship("me");
                    timelineInfo.setPostText(postText);
                    timelineInfo.setPostImages(postImagesArray);
                    arrayList.add(timelineInfo);
                }

                Log.d("pavan1", "arraylist available");
                for(int j = 0 ; j < arrayList.size() ; j++)
                {
                    Log.d("pavan1", "arraylist user"+arrayList.get(j).getScreenname());
                    Log.d("pavan1", "arraylist user text"+arrayList.get(j).getPostText());
                }

                customAdapter.updateResults(arrayList);
            }
        });
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        Log.d("pavan1", "inside timeline onCreateView post");
        fetchDataForTimeline();

        customAdapter = new CustomAdapter(super.getActivity(), arrayList);
        View v=inflater.inflate(R.layout.fragment_timeline, container, false);
        lv=(ListView) v.findViewById(R.id.listView);
        Log.d("here", "result sent");
        lv.setAdapter(customAdapter);

        return v;
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
