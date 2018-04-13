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

import com.cmpe277labs.amipa.facebookclone.CustomAdapter;
import com.cmpe277labs.amipa.facebookclone.HTTPRequestHandler;
import com.cmpe277labs.amipa.facebookclone.PostCustomAdapter;
import com.cmpe277labs.amipa.facebookclone.PostInfo;
import com.cmpe277labs.amipa.facebookclone.R;
import com.cmpe277labs.amipa.facebookclone.SessionManager;
import com.cmpe277labs.amipa.facebookclone.TimelineInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SessionManager sessionManager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<PostInfo> arrayList = new ArrayList<>();
    PostCustomAdapter postCustomAdapter;

    ListView lv;
    Context context;

    public static int [] prgmImages={R.drawable.ami,R.drawable.vansh,R.drawable.pavan,R.drawable.meet};


    private OnFragmentInteractionListener mListener;

    public PostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsFragment newInstance(String param1, String param2) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    public void fetchDataForPosts(){
        Log.d("pavan2", "insuide fetch post");
        final HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
        sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());

        JSONObject resultObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put("email", sessionManager.getEmail());
            resultObject.put("data", dataObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpRequestHandler.sendHTTPRequest("/fetchPost", resultObject, new HTTPRequestHandler.VolleyCallback(){
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                Log.d("pavan2", "result here "+result.toString());

                JSONArray jsonArray = result.getJSONArray("result");

                for(int i = 0 ; i < jsonArray.length() ; i++)
                {
                    PostInfo postInfo = new PostInfo();
                    JSONObject resultJSON = (JSONObject) jsonArray.get(i);
                    //String screenname = resultJSON.getString("screenName");
                    String postText = resultJSON.getString("postText");
                    JSONArray postImages = resultJSON.getJSONArray("postImages");
                    String[] postImagesArray = new String[postImages.length()];
                    for (int j = 0 ; j < postImagesArray.length ; j++)
                    {
                        postImagesArray[j] = postImages.getString(j);
                    }
                    // timelineInfo.setRelationship("me");
                    postInfo.setPostText(postText);
                    postInfo.setPostImages(postImagesArray);
                    arrayList.add(postInfo);
                }

                Log.d("pavan2", "arraylist available");
                for(int j = 0 ; j < arrayList.size() ; j++)
                {
                    Log.d("pavan2", "arraylist user text"+arrayList.get(j).getPostText());
                }

                postCustomAdapter.updateResults(arrayList);

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("pavan2", "onCreateViews");
        View v=inflater.inflate(R.layout.fragment_posts, container, false);
        fetchDataForPosts();

        postCustomAdapter = new PostCustomAdapter(super.getActivity(), arrayList);
        context=getActivity().getApplicationContext();
        lv=(ListView) v.findViewById(R.id.postlist);
        lv.setAdapter(postCustomAdapter);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //  mListener = null;
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
        //  void onFragmentInteraction(Uri uri);
    }
}
