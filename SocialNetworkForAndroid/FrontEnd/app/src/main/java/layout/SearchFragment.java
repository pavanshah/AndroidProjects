package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cmpe277labs.amipa.facebookclone.CustomAdapter;
import com.cmpe277labs.amipa.facebookclone.HTTPRequestHandler;
import com.cmpe277labs.amipa.facebookclone.InvitationCustomAdapter;
import com.cmpe277labs.amipa.facebookclone.MainActivity;
import com.cmpe277labs.amipa.facebookclone.ProfileCustomAdapter;
import com.cmpe277labs.amipa.facebookclone.R;
import com.cmpe277labs.amipa.facebookclone.RequestCustomAdapter;
import com.cmpe277labs.amipa.facebookclone.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
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


    ArrayList prgmName;
    public static String [] prgmImages={};
    public static String [] prgmNameList={};
    public static String [] prgmEmailList={};
    public static String [] prgmImages2={};
    public static String [] prgmNameList2={};
    public static String [] prgmImages3={};
    public static String [] prgmNameList3={};
    public static String [] prgmEmailList3={};

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_search, container, false);
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());
        final String email = sessionManager.getEmail();
        context = getActivity().getApplicationContext();
        lv = (ListView) v.findViewById(R.id.listView);

        lv2 = (ListView) v.findViewById(R.id.listView2);

        lv3 = (ListView) v.findViewById(R.id.listView3);

        Button add=(Button)v.findViewById(R.id.addsearch);
        final EditText emailsearch=(EditText)v.findViewById(R.id.emailsearch);
         HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();

        final JSONObject dataJSON = new JSONObject();
        final JSONObject searchparamObject=new JSONObject();
        final JSONObject dataJSON2 = new JSONObject();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String es=emailsearch.getText().toString();
                    if(email == "" || es == ""){
                        Toast.makeText(getActivity().getApplicationContext(),"Give valid email",Toast.LENGTH_LONG);
                    }
                    Log.d("VANSH"," ***********es**** "+es);
                    searchparamObject.put("email",email);
                    searchparamObject.put("firstName","Vansh");
                    searchparamObject.put("lastName","Zaveri");
                    searchparamObject.put("friendEmail",es);
                    dataJSON2.put("data",searchparamObject);
                    Log.d("VANSH"," JSOn data sent "+dataJSON2.toString());
                    final HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
                    httpRequestHandler.sendHTTPRequest("/addFriendByEmail", dataJSON2, new HTTPRequestHandler.VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) throws JSONException {
                            Log.d("VANSH", "req by mail" + result.toString());
                            String status = result.get("status").toString();
                            String res = result.get("result").toString();
                            Log.d("VANSH"," "+res);
                            if (status.equals("200")) {
                                JSONObject jo=new JSONObject();
                                JSONObject djo=new JSONObject();
                                jo.put("email",email);
                                djo.put("data",jo);
                                httpRequestHandler.sendHTTPRequest("/fetchSentRequests", dataJSON, new HTTPRequestHandler.VolleyCallback() {
                                    @Override
                                    public void onSuccess(JSONObject result) throws JSONException {
                                        ArrayList<String> prgmNameList33 = new ArrayList<String>();
                                        ArrayList<String> pi33 = new ArrayList<String>();

                                        Log.d("AMI", "pending invitations " + result.toString());

                                        String status = result.get("status").toString();

                                        String res = result.get("result").toString();

                                        if (status.equals("200")) {
                                            JSONArray arr = (JSONArray) result.get("result");
                                            Log.d("AMI",")9)()  "+res);
                                            for (int i = 0; i < arr.length(); i++) {
                                                prgmNameList33.add(arr.getJSONObject(i).get("screenName").toString());
                                                Log.d("AMI","..."+i+"..."+arr.getJSONObject(i).get("screenName").toString());
                                            }
                                            prgmNameList2 = new String[prgmNameList33.size()];
                                            for (int i = 0; i < prgmNameList33.size(); i++) {
                                                prgmNameList2[i] = prgmNameList33.get(i);
                                                Log.d("AMI","..."+i+"..."+prgmNameList2[i]);
                                            }
                                            for (int i = 0; i < arr.length(); i++) {
                                                pi33.add(arr.getJSONObject(i).get("profilePic").toString());
                                                Log.d("AMI","..."+i+"..."+arr.getJSONObject(i).get("profilePic").toString());
                                            }
                                            prgmImages2 = new String[pi33.size()];
                                            for (int i = 0; i < pi33.size(); i++) {
                                                prgmImages2[i] = pi33.get(i);
                                                Log.d("AMI","..."+i+"..."+prgmImages2[i]);
                                            }

                                            if(prgmNameList2.length>0) {
                                                lv2.setAdapter(new InvitationCustomAdapter(HTTPRequestHandler.getMyContext(), prgmNameList2, prgmImages2));
                                            }
                                        }
                                        else if (status.equals("400")) {
                                            Log.d("AMI", "wrong credentials");
                                        }

                                    }
                                });

                            } else if (status.equals("400")) {
                                Log.d("", "wrong credentials");
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        JSONObject paramObject = new JSONObject();

        if (email == "") {
            Log.d("", "Null not allowed");
        }
        else {
            try {
                paramObject.put("email", email);
                dataJSON.put("data", paramObject);
                Log.d("PAVAN", "params " + dataJSON.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            httpRequestHandler.sendHTTPRequest("/browseProfileWithoutFollowingProfiles", dataJSON, new HTTPRequestHandler.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    ArrayList<String> prgmNameList33 = new ArrayList<String>();
                    ArrayList<String> prgmImageList33 = new ArrayList<String>();
                    ArrayList<String> prgmEmailList33 = new ArrayList<String>();

                    Log.d("PAVAN", "browse profile" + result.toString());

                    String status = result.get("status").toString();
                    /*for(int i=0;i<dataJSON.length();i++){
                        prgmImages3[i]=result.get(i).
                    }*/
                    String res = result.get("result").toString();

                    if (status.equals("200")) {
                        JSONArray arr = (JSONArray) result.get("result");

                        for (int i = 0; i < arr.length(); i++) {
                            Log.d("PAVAN", "screen name " + arr.getJSONObject(i).get("screenName").toString());
                            prgmNameList33.add(arr.getJSONObject(i).get("screenName").toString());
                            prgmImageList33.add(arr.getJSONObject(i).get("profilePic").toString());
                            prgmEmailList33.add(arr.getJSONObject(i).get("email").toString());
                        }
                        prgmNameList3 = new String[prgmNameList33.size()];

                        for (int i = 0; i < prgmNameList33.size(); i++) {
                            prgmNameList3[i] = prgmNameList33.get(i);
                        }
                        prgmImages3 = new String[prgmImageList33.size()];

                        for (int i = 0; i < prgmImageList33.size(); i++) {
                            prgmImages3[i] = prgmImageList33.get(i);
                        }
                        prgmEmailList3 = new String[prgmEmailList33.size()];

                        for (int i = 0; i < prgmEmailList33.size(); i++) {
                            prgmEmailList3[i] = prgmEmailList33.get(i);
                        }
                        if(prgmNameList3.length>0) {
                            lv3.setAdapter(new ProfileCustomAdapter(HTTPRequestHandler.getMyContext(), prgmNameList3, prgmImages3,prgmEmailList3));
                        }

                    } else if (status.equals("400")) {
                        Log.d("", "wrong credentials");
                    }
                }
            });
            httpRequestHandler.sendHTTPRequest("/fetchSentRequests", dataJSON, new HTTPRequestHandler.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    ArrayList<String> prgmNameList33 = new ArrayList<String>();
                    ArrayList<String> pi33 = new ArrayList<String>();

                    Log.d("AMI", "pending invitations " + result.toString());

                    String status = result.get("status").toString();

                    String res = result.get("result").toString();

                    if (status.equals("200")) {
                        JSONArray arr = (JSONArray) result.get("result");
                        Log.d("AMI",")9)()  "+res);
                        for (int i = 0; i < arr.length(); i++) {
                            prgmNameList33.add(arr.getJSONObject(i).get("screenName").toString());
                            Log.d("AMI","..."+i+"..."+arr.getJSONObject(i).get("screenName").toString());
                        }
                        prgmNameList2 = new String[prgmNameList33.size()];
                        for (int i = 0; i < prgmNameList33.size(); i++) {
                            prgmNameList2[i] = prgmNameList33.get(i);
                            Log.d("AMI","..."+i+"..."+prgmNameList2[i]);
                        }
                        for (int i = 0; i < arr.length(); i++) {
                            pi33.add(arr.getJSONObject(i).get("profilePic").toString());
                            Log.d("AMI","..."+i+"..."+arr.getJSONObject(i).get("profilePic").toString());
                        }
                        prgmImages2 = new String[pi33.size()];
                        for (int i = 0; i < pi33.size(); i++) {
                            prgmImages2[i] = pi33.get(i);
                            Log.d("AMI","..."+i+"..."+prgmImages2[i]);
                        }
                        if(prgmNameList2.length>0) {
                            lv2.setAdapter(new InvitationCustomAdapter(HTTPRequestHandler.getMyContext(), prgmNameList2, prgmImages2));
                        }
                    }
                    else if (status.equals("400")) {
                        Log.d("AMI", "wrong credentials");
                    }

                }
            });
            httpRequestHandler.sendHTTPRequest("/fetchInvitations", dataJSON, new HTTPRequestHandler.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    ArrayList<String> prgmNameList33 = new ArrayList<String>();
                    ArrayList<String> prgmEmailList33 = new ArrayList<String>();
                    ArrayList<String> prgmImageList33 = new ArrayList<String>();

                    Log.d("PREQ", "pending requests " + result.toString());

                    String status = result.get("status").toString();

                    String res = result.get("result").toString();

                    if (status.equals("200")) {
                        JSONArray arr = (JSONArray) result.get("result");
                        Log.d("PREQ",")9)()  "+res);
                        for (int i = 0; i < arr.length(); i++) {
                            prgmNameList33.add(arr.getJSONObject(i).get("screenName").toString());
                            Log.d("PREQ","..."+i+"..."+arr.getJSONObject(i).get("screenName").toString());
                        }
                        prgmNameList = new String[prgmNameList33.size()];
                        for (int i = 0; i < prgmNameList33.size(); i++) {
                            prgmNameList[i] = prgmNameList33.get(i);
                            Log.d("PREQ","..."+i+"..."+prgmNameList[i]);
                        }
                        for (int i = 0; i < arr.length(); i++) {
                            prgmEmailList33.add(arr.getJSONObject(i).get("email").toString());
                            Log.d("PREQ","..."+i+"..."+arr.getJSONObject(i).get("email").toString());
                        }
                        prgmEmailList = new String[prgmEmailList33.size()];
                        for (int i = 0; i < prgmEmailList33.size(); i++) {
                            prgmEmailList[i] = prgmEmailList33.get(i);
                            Log.d("PREQ","..."+i+"..."+prgmEmailList[i]);

                        }
                        for (int i = 0; i < arr.length(); i++) {
                            prgmImageList33.add(arr.getJSONObject(i).get("profilePic").toString());
                            Log.d("PREQ","..."+i+"..."+arr.getJSONObject(i).get("profilePic").toString());
                        }
                        prgmImages = new String[prgmImageList33.size()];
                        for (int i = 0; i < prgmImageList33.size(); i++) {
                            prgmImages[i] = prgmImageList33.get(i);
                            Log.d("PREQ","..."+i+"..."+prgmImages[i]);

                        }
                        if(prgmNameList.length>0) {
                            lv.setAdapter(new RequestCustomAdapter(HTTPRequestHandler.getMyContext(), prgmNameList, prgmEmailList, prgmImages));
                        }

                    }
                    else if (status.equals("400")) {
                        Log.d("PREQ", "wrong credentials");
                    }

                }
            });
        }
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
       /* if (mListener != null) {
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
/*
        void onFragmentInteraction(Uri uri);
*/
    }
}
