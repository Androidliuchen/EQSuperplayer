package com.eq.EQSuperPlayer.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.adapter.SpinnerAdapter;
import com.eq.EQSuperPlayer.adapter.SpinnerImageAdapter;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.bean.TimeBean;
import com.eq.EQSuperPlayer.custom.Constant;
import com.eq.EQSuperPlayer.dao.ProgramBeanDao;
import com.eq.EQSuperPlayer.dao.TimeDao;
import com.eq.EQSuperPlayer.utils.Utils;
import com.eq.EQSuperPlayer.utils.WindowSizeManager;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeActivity extends AppCompatActivity {

    @BindView(R.id.timeTofinsh)
    ImageView timeTofinsh;
    @BindView(R.id.time_title)
    TextView timeTitle;
    @BindView(R.id.time_btn)
    Button timeBtn;
    @BindView(R.id.timeToname)
    EditText timeToname;
    @BindView(R.id.timeToys)
    Spinner timeToys;
    @BindView(R.id.timeToborder)
    Spinner timeToborder;
    @BindView(R.id.timeTobordercolor)
    Spinner timeTobordercolor;
    @BindView(R.id.timeTox)
    EditText timeTox;
    @BindView(R.id.timeToy)
    EditText timeToy;
    @BindView(R.id.timeToWidth)
    EditText timeToWidth;
    @BindView(R.id.timeToHeigth)
    EditText timeToHeigth;
    @BindView(R.id.timeToarrds)
    EditText timeToarrds;
    @BindView(R.id.timeToFont)
    Spinner timeToFont;
    @BindView(R.id.timeToSize)
    Spinner timeToSize;
    @BindView(R.id.timeToColoer)
    Spinner timeToColoer;
    @BindView(R.id.timeToxz)
    Spinner timeToxz;
    @BindView(R.id.timeToxq)
    CheckBox timeToxq;
    @BindView(R.id.timeTorq)
    CheckBox timeTorq;
    @BindView(R.id.timeTosz)
    Spinner timeTosz;
    @BindView(R.id.timeTofz)
    Spinner timeTofz;
    @BindView(R.id.timeTomz)
    Spinner timeTomz;
    @BindView(R.id.timeToshibiao)
    Spinner timeToshibiao;
    @BindView(R.id.timeTofenbiao)
    Spinner timeTofenbiao;
    @BindView(R.id.timeToDay)
    EditText timeToDay;
    @BindView(R.id.huorOrMinute)
    TextView huorOrMinute;
    @BindView(R.id.timeToxqColor)
    Spinner timeToxqColor;
    @BindView(R.id.timeTorqColor)
    Spinner timeTorqColor;
    private TimeBean timeBean;
    private ProgramBean programBean;
    private List<ProgramBean> programBeans;
    private Areabean areabean = new Areabean();
    private int windowWidth;//窗口宽度
    private int windowHeight; //窗口高度
    private WindowSizeManager windowSizeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        timeBean = new TimeBean();
        int text_id = getIntent().getIntExtra(Constant.PROGRAM_ID, -1);
        timeBean = new TimeDao(this).get(text_id);
        programBean = new ProgramBeanDao(this).get(text_id);
        timeBean.setProgramBean(programBean);
        //节目名称
        timeToname.setText(timeBean.getTimeToname());
        windowSizeManager = WindowSizeManager.getSahrePreference(this);
        windowWidth = windowSizeManager.getWindowWidth();
        windowHeight = windowSizeManager.getWindowHeight();
        //区域宽高 ，坐标
        if (areabean.getWindowWidth() != 0) {
            timeToWidth.setText(areabean.getWindowWidth() + "");
            timeToHeigth.setText(areabean.getWindowHeight() + "");
        } else {
            timeToWidth.setText(windowWidth + "");
            timeToHeigth.setText(windowHeight + "");
        }
        timeTox.setText(0 + "");
        timeToy.setText(0 + "");

        //时钟样式
        timeToys.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.colck_style)));
        timeToys.setSelection(timeBean.getM_nClockType());
        //固定文字  固定文字字体，颜色，大小
        timeToarrds.setText(timeBean.getM_strClockText());
        timeToFont.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.text_font)));
        timeToFont.setSelection(timeBean.getNumber_typeface());
        TypedArray typedArray = this.getResources().obtainTypedArray(R.array.bordercolor);
        int[] color_id = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            color_id[i] = typedArray.getResourceId(i, 0);
        }
        //边框
        timeToborder.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.border)));
        timeToborder.setSelection(timeBean.getTimeToBorder());
        //边框颜色
        timeTobordercolor.setAdapter(new SpinnerImageAdapter(this, color_id));
        timeTobordercolor.setSelection(timeBean.getTimeToBorderColor());
        //字体颜色
        timeToColoer.setAdapter(new SpinnerImageAdapter(this, color_id));
        timeToColoer.setSelection(timeBean.getM_rgbClockTextColor());
        //字体大小
        timeToSize.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.text_size)));
        timeToSize.setSelection(timeBean.getM_rgbClockTextSize());
        //年月日，是否显示，颜色读取
        final CheckBox[] checkBoxes = {timeTorq, timeToxq};
        final String m_strShowForm = timeBean.getM_strShowForm();
        boolean[] str_ints = timeBean.getStrShowFormInt(m_strShowForm);
        for (int i = 0; i < m_strShowForm.length(); i++) {
//            checkBoxes[i].setChecked(str_ints[i]);
        }
        timeTorqColor.setAdapter(new SpinnerImageAdapter(this, color_id));
        timeTorqColor.setSelection(timeBean.getM_rgbDayTextColor());
        timeToxqColor.setAdapter(new SpinnerImageAdapter(this, color_id));
        timeToxqColor.setSelection(timeBean.getM_rgbWeekTextColor());
        //时差   日期  ，  天数  ， 时间
        timeToDay.setText(timeBean.getM_nDayLag() + "");
        huorOrMinute.setText("" + timeBean.getM_strTimeLag());
        huorOrMinute.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View a = TimeActivity.this.getLayoutInflater().inflate(R.layout.timeselector, null);
                final TimePicker timePicker = (TimePicker) a.findViewById(R.id.timePic1);
                timePicker.setIs24HourView(true);
                int[] times = timeBean.splitStrTimeLag(huorOrMinute.getText().toString());
                timePicker.setCurrentHour(Integer.valueOf(times[0]));
                timePicker.setCurrentMinute(Integer.valueOf(times[1]));
                Dialog builder;
                builder = new Dialog(TimeActivity.this, R.style.dialog);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        String hour = String.valueOf(timePicker.getCurrentHour());
                        String minute = String.valueOf(timePicker.getCurrentMinute());
                        if (hour.length() == 1) {
                            hour = "0" + hour;
                        }
                        if (minute.length() == 1) {
                            minute = "0" + minute;
                        }
                        String ss = hour + ":" + minute;
                        huorOrMinute.setText(ss);
                    }
                });
                builder.show();
                builder.getWindow().setContentView(a);

            }
        });
        //模拟时钟参数填写
        //时钟形状，是否显示星期，日期等文字
        timeToxz.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.colck_shape)));
        timeToxz.setSelection(timeBean.getColckShape());
        timeToxq.setChecked(timeBean.isWeekshow());
        timeTorq.setChecked(timeBean.isDateshow());
        //时标，颜色，宽高，形状
        timeToshibiao.setAdapter(new SpinnerImageAdapter(this, color_id));
        timeToshibiao.setSelection(timeBean.getHourscolor());
        //分标
        timeTofenbiao.setAdapter(new SpinnerImageAdapter(this, color_id));
        timeTofenbiao.setSelection(timeBean.getFenbiaocolorposition());
        //时针，分针，秒针的颜色
        timeTosz.setAdapter(new SpinnerImageAdapter(this, color_id));
        timeTosz.setSelection(timeBean.getHourscolor());
        timeTofz.setAdapter(new SpinnerImageAdapter(this, color_id));
        timeTofz.setSelection(timeBean.getMinutecolor());
        timeTomz.setAdapter(new SpinnerImageAdapter(this, color_id));
        timeTomz.setSelection(timeBean.getSecondcolor());


        try {
            if (Integer.parseInt(timeToWidth.getText().toString()) + Integer.parseInt(timeTox.getText().toString()) <= windowWidth
                    && Integer.parseInt(timeToHeigth.getText().toString()) + Integer.parseInt(timeToy.getText().toString()) <= windowHeight) {
                timeBean.setTimeTowidth(Integer.parseInt(timeToWidth.getText().toString()));
                timeBean.setTimeToheidht(Integer.parseInt(timeToHeigth.getText().toString()));
                timeBean.setTimeToX(Integer.parseInt(timeTox.getText().toString()));
                timeBean.setTimeToY(Integer.parseInt(timeToy.getText().toString()));
            } else {
                Toast.makeText(TimeActivity.this, "参数超出边界，请重新设置",Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException exception) {

        }
        //模拟时钟参数存储
        timeBean.setColckShape(timeToxz.getSelectedItemPosition());
        timeBean.setWeekshow(timeToxq.isChecked());
        timeBean.setDateshow(timeTorq.isChecked());
        timeBean.setShibiaocolorposition(timeToshibiao.getSelectedItemPosition());
        timeBean.setShibiaoshape(timeToshibiao.getSelectedItemPosition());
        timeBean.setFenbiaocolorposition(timeTofenbiao.getSelectedItemPosition());
        timeBean.setFenbiaoshape(timeTofenbiao.getSelectedItemPosition());
        timeBean.setHourscolor(timeTosz.getSelectedItemPosition());
        timeBean.setMinutecolor(timeTofz.getSelectedItemPosition());
        timeBean.setSecondcolor(timeTomz.getSelectedItemPosition());

        //数值时钟参数存储
        timeBean.setM_nClockType(timeToys.getSelectedItemPosition());
        timeBean.setM_strClockText(timeToarrds.getText().toString());
        timeBean.setNumber_typeface(timeToFont.getSelectedItemPosition());
        timeBean.setM_rgbClockTextColor(timeToColoer.getSelectedItemPosition());
        timeBean.setM_rgbClockTextSize(timeToSize.getSelectedItemPosition());
        //
        StringBuffer strShowForm = new StringBuffer();
        for (CheckBox cb : checkBoxes
                ) {
            if (cb.isChecked() == true) {
                strShowForm.append("1");
            } else {
                strShowForm.append("0");
            }
        }
        if (timeBean.getM_nClockType() == 3) {
            if (timeToxq.isChecked()) {
                strShowForm.replace(3, 4, "1");
            } else {
                strShowForm.replace(3, 4, "0");
            }
            if (timeTorq.isChecked()) {
                strShowForm.replace(0, 3, "111");
            } else {
                strShowForm.replace(0, 3, "000");
            }
        }

        timeBean.setM_strShowForm(strShowForm.toString());
        timeBean.setM_rgbDayTextColor(timeTorqColor.getSelectedItemPosition());
        timeBean.setM_rgbWeekTextColor(timeToxqColor.getSelectedItemPosition());
//        timeBean.setM_rgbTimeColor(wt_timecolor.getSelectedItemPosition());
//        timeBean.setM_nDayLag(Integer.parseInt(timeToDay.getText().toString()));
//        timeBean.setM_strTimeLag(huorOrMinute.getText().toString());
        new TimeDao(TimeActivity.this).update(timeBean);
        //获取画笔属性
        Paint paint = Utils.getPaint(TimeActivity.this, Utils.getPaintSize(TimeActivity.this, Integer.parseInt(TimeActivity.this.getResources().getStringArray(R.array.text_size)[timeBean.getM_rgbClockTextSize()])
                + Constant.FONT_SIZE_CORRECTION));
        Utils.setTypeface(TimeActivity.this, paint
                , (TimeActivity.this.getResources().getStringArray(R.array.typeface_path))[timeBean.getNumber_typeface()]);
        paint.setTextSize(Utils.getPaintSize(TimeActivity.this, timeBean.getM_rgbClockTextSize() + Constant.FONT_SIZE_CORRECTION)); // 字体大小 进度条参数

    }

    @OnClick({R.id.timeTofinsh, R.id.time_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timeTofinsh:
                TimeActivity.this.finish();
                break;
            case R.id.time_btn:
                TimeActivity.this.finish();
                break;
        }
    }
}
