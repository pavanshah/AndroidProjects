package com.cmpe277labs.amipa.facebookclone;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.services.s3.model.ListNextBatchOfObjectsRequest;
import com.cmpe277labs.amipa.facebookclone.ChatCustomAdapter;
import com.cmpe277labs.amipa.facebookclone.ChatProfileCustomAdapter;
import com.cmpe277labs.amipa.facebookclone.MainActivity;
import com.cmpe277labs.amipa.facebookclone.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by meet on 25/05/2017.
 */

public class MessageBodyFragment extends AppCompatActivity {

    LinearLayout linearLayout;
    String to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_messagebody);
        Intent intent = getIntent();

        ImageView send = (ImageView) findViewById(R.id.sendMessage);
        final EditText message = (EditText) findViewById(R.id.message);
        final EditText subject = (EditText) findViewById(R.id.subject);

        final SessionManager sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());

        final String screenname_to = intent.getStringExtra("to");
        fetchConversation(screenname_to);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(MessageBodyFragment.this, "send clicked", Toast.LENGTH_SHORT).show();

                String messagetobesent = message.getText().toString();

                String sender = sessionManager.getScreenname();

                String receiver = screenname_to;

                String sub = subject.getText().toString();

                //Toast.makeText(getApplicationContext(), ""+messagetobesent+" "+sender+" "+receiver, Toast.LENGTH_LONG).show();

                sendMessage(messagetobesent, sender, receiver, sub);

            }
        });


    }


    public void update()
    {
        Log.d("pavan3", "back here");

        ViewGroup vg = (ViewGroup) findViewById (R.id.mainLayout);
        vg.invalidate();
        Log.d("pavan3", "invalidated");
    }


    public void sendMessage(String messagetobesent,String sender,String receiver, String subject)
    {
        final HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
        final SessionManager sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());

        JSONObject resultObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put("screenName", sender);
            dataObject.put("message", messagetobesent);
            dataObject.put("toScreenName", receiver);
            dataObject.put("email", sessionManager.getEmail());
            dataObject.put("subject", subject);
            resultObject.put("data", dataObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpRequestHandler.sendHTTPRequest("/sendMessage", resultObject, new HTTPRequestHandler.VolleyCallback(){
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
                update();
            }
        });

    }


    private void fetchConversation(String screenname_to) {

        Log.d("pavan3", "inside fetchconversation "+screenname_to);
        to = screenname_to;

        final HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
        final SessionManager sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());

        JSONObject resultObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put("screenName", sessionManager.getScreenname());
            dataObject.put("toScreenName", screenname_to);
            resultObject.put("data", dataObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpRequestHandler.sendHTTPRequest("/getIndividualMessage", resultObject, new HTTPRequestHandler.VolleyCallback(){
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                Log.d("pavan3", "result here "+result.toString());


                String finalConversationleft = new String();
                String finalConversationright = new String();
                //linearLayout = (LinearLayout) findViewById(R.id.layout1);
                LinearLayout main = (LinearLayout) findViewById(R.id.mainLayout);
                JSONArray jsonArray = result.getJSONArray("result");

                TextView leftTextView = (TextView) findViewById(R.id.leftText);
                TextView rightTextView = (TextView) findViewById(R.id.rightText);
                int rightCount = 0;
                int leftCount = 0;


                finalConversationright = finalConversationright+sessionManager.getScreenname() + "\n\n";
                finalConversationleft = finalConversationleft+ to + "\n\n";

                for(int i = 0 ; i < jsonArray.length() ; i++)
                {
                    Log.d("pavan3", "inside");
                    JSONObject resultJSON = jsonArray.getJSONObject(i);
                    Log.d("pavan3", "resultJSON "+resultJSON);
                    JSONObject messageJSON = resultJSON.getJSONObject("message");
                    Log.d("pavan3", "messageJSON "+messageJSON);
                    String subject = messageJSON.getString("subject");
                    String message = messageJSON.getString("message");
                    Log.d("pavan3", "message "+message);
                    String position = resultJSON.getString("side");

                    if(position.equals("right"))
                    {
                        rightCount = rightCount+4;

                        for(int j = 0 ; j < leftCount ; j++)
                            finalConversationright = finalConversationright + "\n";

                        finalConversationright = finalConversationright + subject+"\n"+message+"\n\n";

                        leftCount = 0;
                    }
                    else
                    {
                        leftCount = leftCount+4;

                        for(int j = 0 ; j < rightCount ; j++)
                            finalConversationleft = finalConversationleft + "\n";

                        finalConversationleft = finalConversationleft + subject+"\n"+message+"\n\n";

                        rightCount = 0;
                    }


                    if(i == jsonArray.length()-1)
                    {
                        leftTextView.setText(finalConversationleft);
                        rightTextView.setText(finalConversationright);
                        update();
                    }
                }

            }
        });

    }

}
