package com.example.intel.tagcardview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Intel on 28-02-2017.
 */

public class DialogBox extends AppCompatActivity {

    Button showPOPup;

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout,Rcontainer;
    private PopupWindow mPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialogbox);

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = DialogBox.this;
        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rel_container);
        showPOPup=(Button)findViewById(R.id.button);

        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        final View customView = inflater.inflate(R.layout.activity_dialog_design,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );



        showPOPup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //make popup focusable so when it is open and click back button so it get dismissed first
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setFocusable(true);

                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }
                Rcontainer=(RelativeLayout) customView.findViewById(R.id.rel_container1);
                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                closeButton.setImageResource(R.mipmap.ic_launcher);


                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                Rcontainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();

                    }
                });

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);

               /* View popupView = layoutInflater.inflate(R.layout.activity_dialog_design, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

                popupWindow.showAsDropDown(showPOPup, 50, -30);*/
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(mPopupWindow!=null)
        {
            mPopupWindow.dismiss();
        }else
        {

        }
    }
}
