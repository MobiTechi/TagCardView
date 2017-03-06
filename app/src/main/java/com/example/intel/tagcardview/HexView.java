package com.example.intel.tagcardview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.siyamed.shapeimageview.HexagonImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Intel on 22-02-2017.
 */

public class HexView extends AppCompatActivity implements View.OnClickListener, OnChartValueSelectedListener {

    //edit text
    EditText hint_editText;
    // HexagonImageView img1,img2,img3,img4,img5,img6;
    boolean check = false;
    //ImageView Img1,Img2;

    /*For dynamic spinner*/
    LinearLayout SpinnerContainer;
    Spinner day, timeFrom, timeTo;
    List<String> list;
    Button add, remove;

    /*For chart*/
    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] mParties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    private BarChart mChart;
    private TextView tvX, tvY;

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hexagonal view
        setContentView(R.layout.activity_main);
        hint_editText=(EditText)findViewById(R.id.editText);
        SpannableString styledString = new SpannableString("9-10th STD");
        styledString.setSpan(new SuperscriptSpan(), 4, 6, 0);
        styledString.setSpan(new ForegroundColorSpan(0xFFFF0000), 4, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        hint_editText.setHint(styledString);

        //This is new comment

        //dynamic spinner
        // setContentView(R.layout.scrollex);

        /*/bar chart
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // setContentView(R.layout.activity_barchart);

        mTfRegular = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");

        tvX = (TextView) findViewById(R.id.tvXMax);
        tvX.setTextSize(10);
        tvY = (TextView) findViewById(R.id.tvYMax);

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(HexView.this);
        mChart.getDescription().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawGridBackground(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTypeface(mTfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet

        int groupCount = 1000 + 1;
        int startYear = 1980;
        int endYear = startYear + groupCount;

        tvX.setText(startYear + "-" + endYear);
        tvY.setText("" + (1000));

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals4 = new ArrayList<BarEntry>();

        float randomMultiplier = 10 * 100000f;

        for (int i = startYear; i < endYear; i++) {
            yVals1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            yVals2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            yVals3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            yVals4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
        }

        BarDataSet set1, set2, set3, set4;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) mChart.getData().getDataSetByIndex(2);
            set4 = (BarDataSet) mChart.getData().getDataSetByIndex(3);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            set4.setValues(yVals4);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(yVals1, "Company A");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "Company B");
            set2.setColor(Color.rgb(164, 228, 251));
            set3 = new BarDataSet(yVals3, "Company C");
            set3.setColor(Color.rgb(242, 247, 158));
            set4 = new BarDataSet(yVals4, "Company D");
            set4.setColor(Color.rgb(255, 102, 0));

            BarData data = new BarData(set1, set2, set3, set4);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(mTfLight);

            mChart.setData(data);
        }

        // specify the width each bar should have
        mChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        mChart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mChart.getXAxis().setAxisMaximum(startYear + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        mChart.groupBars(startYear, groupSpace, barSpace);
        mChart.invalidate();



        mChart.getAxisRight().setEnabled(false);*/

        /*for dynamic spinner*/
       /* SpinnerContainer=(LinearLayout)findViewById(R.id.spinner_container);

        add=(Button)findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                day = new Spinner (HexView.this);
                timeFrom = new Spinner (HexView.this);
                timeTo = new Spinner (HexView.this);
                remove=new Button(HexView.this);
                remove.setText("Remove");

                list = new ArrayList<String>();
                list.add("Item 1");
                list.add("Item 2");
                list.add("Item 3");
                list.add("Item 4");
                list.add("Item 5");

                ArrayAdapter<String> adp = new ArrayAdapter<String>(HexView.this,android.R.layout.simple_dropdown_item_1line, list);
                day.setAdapter(adp);
                timeFrom.setAdapter(adp);
                timeTo.setAdapter(adp);

                final LinearLayout SpinnerInnerContainer=new LinearLayout(HexView.this);
                SpinnerInnerContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                SpinnerInnerContainer.setOrientation(LinearLayout.HORIZONTAL);

                remove.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                remove.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

                SpinnerContainer.addView(SpinnerInnerContainer);

                SpinnerInnerContainer.addView(day);
                SpinnerInnerContainer.addView(timeFrom);
                SpinnerInnerContainer.addView(timeTo);
                SpinnerInnerContainer.addView(remove);

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SpinnerContainer.removeView(SpinnerInnerContainer);
                    }
                });

            }
        });*/


/*Hexagonal code hexagonal boundary click*/

        // img1=(HexagonImageView)findViewById(R.id.hexagonImageView1);
        // img2=(HexagonImageView)findViewById(R.id.hexagonImageView2);
        // img3=(HexagonImageView)findViewById(R.id.hexagonImageView3);

        // img4=(HexagonImageView)findViewById(R.id.hexagonImageView4);
        //  img5=(HexagonImageView)findViewById(R.id.hexagonImageView5);
        //  img6=(HexagonImageView)findViewById(R.id.hexagonImageView6);

       /* img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);*/

        /*  img1.setDrawingCacheEnabled(true);
        img1.setOnTouchListener(changeColorListener);

        img2.setDrawingCacheEnabled(true);
        img2.setOnTouchListener(changeColorListener);

      img3.setDrawingCacheEnabled(true);
        img3.setOnTouchListener(changeColorListener);

        img4.setDrawingCacheEnabled(true);
        img4.setOnTouchListener(changeColorListener);

        img5.setDrawingCacheEnabled(true);
        img5.setOnTouchListener(changeColorListener);

        img6.setDrawingCacheEnabled(true);
        img6.setOnTouchListener(changeColorListener);*/

        /*Img1=(ImageView)findViewById(R.id.img1);
        Img2=(ImageView)findViewById(R.id.img2);

        Img1.setOnClickListener(this);
        Img2.setOnClickListener(this);*/

    }

    private final View.OnTouchListener changeColorListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
                int color = 0;
                try {
                    color = bmp.getPixel((int) event.getX(), (int) event.getY());
                } catch (Exception e) {
                    // e.printStackTrace();
                }
                if (color == Color.TRANSPARENT) {
                    Toast.makeText(HexView.this, "Color=" + color, Toast.LENGTH_SHORT).show();

                } else {

                    //  Toast.makeText(HexView.this, "Color=" + color, 1000).show();
                    onClick(v);

                }
            }
            return true;
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hexagonImageView1:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hexagonImageView2:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
           /* case R.id.hexagonImageView3:
                Toast.makeText(this, "3",1000).show();
                break;

            case R.id.hexagonImageView4:
                Toast.makeText(this, "4",1000).show();
                break;
            case R.id.hexagonImageView5:
                Toast.makeText(this, "5", 1000).show();
                break;
            case R.id.hexagonImageView6:
                Toast.makeText(this, "6",1000).show();
                break;*/
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }
}
