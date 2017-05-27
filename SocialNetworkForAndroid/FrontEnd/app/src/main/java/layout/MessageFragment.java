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

import com.cmpe277labs.amipa.facebookclone.ChatCustomAdapter;
import com.cmpe277labs.amipa.facebookclone.ChatProfileCustomAdapter;
import com.cmpe277labs.amipa.facebookclone.HTTPRequestHandler;
import com.cmpe277labs.amipa.facebookclone.ProfileCustomAdapter;
import com.cmpe277labs.amipa.facebookclone.R;
import com.cmpe277labs.amipa.facebookclone.SessionManager;
import com.cmpe277labs.amipa.facebookclone.TimelineInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView lv;
    ListView lv2;
    ListView lv3;
    Context context;
    private SessionManager sessionManager;
    public static int [] prgmImages={R.drawable.ami};
    public static String [] prgmNameList = {};
    public static String [] prgmEmailList={"Ami","Vansh","Meet","Pavan","Pooja"};
    public static int [] prgmImages2={R.drawable.ami,R.drawable.vansh,R.drawable.pavan,R.drawable.pavan,R.drawable.pavan};
    public static String [] prgmNameList2={"Ami","Vansh","Meet","Pavan","Pooja"};
    ChatCustomAdapter chatCustomAdapter;

    private OnFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_message, container, false);

        findMessageData();

        chatCustomAdapter = new ChatCustomAdapter(getActivity(), prgmNameList, prgmImages);
        lv=(ListView)v.findViewById(R.id.chatlistview);
        lv.setAdapter(chatCustomAdapter);

        lv2=(ListView)v.findViewById(R.id.messageprofilelistview);
        lv2.setAdapter(new ChatProfileCustomAdapter(getActivity(), prgmNameList2, prgmImages2));
        return v;
    }

    private void findMessageData() {

        Log.d("pavan3", "inside findMessageData");
        final HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
        sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());

        Log.d("pavan3", "findMessageData email "+sessionManager.getScreenname());
        JSONObject resultObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put("screenName", sessionManager.getScreenname());
            resultObject.put("data", dataObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpRequestHandler.sendHTTPRequest("/getInbox", resultObject, new HTTPRequestHandler.VolleyCallback(){
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                Log.d("pavan3", "result here "+result.toString());

                JSONArray jsonArray = result.getJSONArray("result");
                Log.d("pavan3", "jsonarray "+jsonArray);
                Log.d("pavan3", "jsonarray "+jsonArray.length());

                prgmNameList = new String[jsonArray.length()];

                for(int i = 0 ; i < jsonArray.length() ; i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    prgmNameList[i] = jsonObject.getString("to");
                }

                //Log.d("pavan3", "prgmNameList "+prgmNameList[0]);
                chatCustomAdapter.updateResults(prgmNameList);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
       // void onFragmentInteraction(Uri uri);
    }
}
