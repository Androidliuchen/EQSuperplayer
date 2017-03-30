package com.eq.EQSuperPlayer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.adapter.SpinnerAdapter;
import com.eq.EQSuperPlayer.adapter.SpinnerImageAdapter;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.bean.TextBean;
import com.eq.EQSuperPlayer.custom.Constant;
import com.eq.EQSuperPlayer.dao.ProgramBeanDao;
import com.eq.EQSuperPlayer.dao.TextBeanDao;
import com.eq.EQSuperPlayer.utils.Utils;
import com.eq.EQSuperPlayer.utils.WindowSizeManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TextActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.finshamg)
    ImageView finshamg;
    @BindView(R.id.text_btn)
    Button textBtn;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.ST)
    EditText ST;
    @BindView(R.id.STborder)
    Spinner STborder;
    @BindView(R.id.STbordercolor)
    Spinner STbordercolor;
    @BindView(R.id.STx)
    EditText STx;
    @BindView(R.id.STy)
    EditText STy;
    @BindView(R.id.STWidth)
    EditText STWidth;
    @BindView(R.id.STHeigth)
    EditText STHeigth;
    @BindView(R.id.STFont)
    Spinner STFont;
    @BindView(R.id.STBold)
    CheckBox STBold;
    @BindView(R.id.STItalie)
    CheckBox STItalie;
    @BindView(R.id.STUnderlinr)
    CheckBox STUnderlinr;
    @BindView(R.id.STSize)
    Spinner STSize;
    @BindView(R.id.STColoer)
    Spinner STColoer;
    @BindView(R.id.STTick)
    Spinner STTick;
    @BindView(R.id.STSpeed)
    Spinner STSpeed;
    @BindView(R.id.STClear)
    Spinner STClear;
    @BindView(R.id.STClearSpeed)
    Spinner STClearSpeed;
    @BindView(R.id.STBackground)
    Spinner STBackground;
    @BindView(R.id.STStandtime)
    EditText STStandtime;
    @BindView(R.id.text_title)
    TextView textTitle;
    private TextBean textBean;
    private List<TextBean> textBeens;
    private ProgramBean programBean;
    private List<ProgramBean> programBeans;
    private Areabean areabean = new Areabean();
    private int windowWidth;//窗口宽度
    private int windowHeight; //窗口高度
    private WindowSizeManager windowSizeManager;
    private float textWidth; //所输入文字的的宽度
    private int textWidths;//文本框输入屏幕的宽度
    private int textHeights;//文本框输入屏幕的高度
    private int bitmapWidth;//生成图片的实际宽度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        int text_id = getIntent().getIntExtra(Constant.PROGRAM_ID, -1);
        textBean = new TextBeanDao(this).get(text_id);
        programBean = new ProgramBeanDao(this).get(ProgramActivity.selet);
        textBean.setProgramBean(programBean);
        //节目名称
        ST.setText(textBean.getName());
        windowSizeManager = WindowSizeManager.getSahrePreference(this);
        windowWidth = windowSizeManager.getWindowWidth();
        windowHeight = windowSizeManager.getWindowHeight();
        // 文本窗宽高
        if (textBean.getWidth() != 0) {
            STWidth.setText(textBean.getWidth() + "");
            STHeigth.setText(textBean.getHeidht() + "");
        } else {
            STWidth.setText(windowWidth + "");
            STHeigth.setText(windowHeight + "");
        }
        if (textBean.getX() != 0) {
            STx.setText(textBean.getX() + "");
            STy.setText(textBean.getY() + "");
        } else {
            STx.setText(0 + "");
            STy.setText(0 + "");
        }

        //字体是否加粗
        STBold.setChecked(textBean.isStBold());
        //文字是否设置斜体
        STItalie.setChecked(textBean.isStItalic());
        //文字是否添加下划线
        STUnderlinr.setChecked(textBean.isStUnderLine());
        //字体大小
        STSize.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.text_size)));
        STSize.setSelection(textBean.getStSize());
        editText.setText(textBean.getSingleTextValue());
        editText.requestFocus();
        TypedArray typedArray = this.getResources().obtainTypedArray(R.array.textcolor);
        int[] color_id = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            color_id[i] = typedArray.getResourceId(i, 0);
        }
        //边框
        STborder.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.border)));
        STborder.setSelection(textBean.getBorder());
        //边框颜色
        STbordercolor.setAdapter(new SpinnerImageAdapter(this, color_id));
        STbordercolor.setSelection(textBean.getBorderColor());
        //字体颜色
        STColoer.setAdapter(new SpinnerImageAdapter(this, color_id));
        STColoer.setSelection(textBean.getStColor());
        //字体
        STFont.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.text_font)));
        STFont.setSelection(textBean.getStTypeFace());
        //进场特技
        STTick.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.fontertrick)));
        STTick.setSelection(textBean.getEntertrick());
        //进场速度
        STSpeed.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.enterspeed)));
        STSpeed.setSelection((int) textBean.getEnterspeed());
        //出场特技
        STClear.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.cleartrick)));
        STClear.setSelection(textBean.getCleartrick());
        //清场速度
        STClearSpeed.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.clearspeed)));
        STClearSpeed.setSelection((int) textBean.getClearspeed());
        //背景色
        STBackground.setAdapter(new SpinnerImageAdapter(this, color_id));
        STBackground.setSelection(textBean.getStBackground());
        //停留时间
        STStandtime.setText(textBean.getStandtime() + "");
        textTitle.setText(programBean.getName());
    }

    public void save() {
        try {
            if (Integer.parseInt(STWidth.getText().toString()) + Integer.parseInt(STx.getText().toString()) <= windowWidth
                    && Integer.parseInt(STHeigth.getText().toString()) + Integer.parseInt(STy.getText().toString()) <= windowHeight) {
                textBean.setWidth(Integer.parseInt(STWidth.getText().toString()));
                textBean.setHeidht(Integer.parseInt(STHeigth.getText().toString()));
                textBean.setX(Integer.parseInt(STx.getText().toString()));
                textBean.setY(Integer.parseInt(STy.getText().toString()));
            } else {
                Toast.makeText(this, "参数超出边界，请重新设置", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {
        }
        textBean.setSingleTextValue(editText.getText().toString());
        textBean.setBorder(STborder.getSelectedItemPosition());
        textBean.setBorderColor(STbordercolor.getSelectedItemPosition());
        textBean.setStBold(STBold.isChecked());
        textBean.setStItalic(STItalie.isChecked());
        textBean.setStUnderLine(STUnderlinr.isChecked());
        textBean.setStSize(STSize.getSelectedItemPosition());
        textBean.setStColor(STColoer.getSelectedItemPosition());
        textBean.setStBackground(STBackground.getSelectedItemPosition());
        textBean.setClearspeed(STClearSpeed.getSelectedItemPosition());
        textBean.setCleartrick(STClear.getSelectedItemPosition());
        textBean.setEnterspeed(STSpeed.getSelectedItemPosition());
        textBean.setEntertrick(STTick.getSelectedItemPosition());
        textBean.setStandtime(Integer.parseInt(STStandtime.getText().toString()));
        //创建时画笔赋值
        Paint paint = Utils.getPaint(TextActivity.this, Utils.getPaintSize(TextActivity.this, 15));
        paint.setFakeBoldText(textBean.isStBold());
        Utils.setTypeface(TextActivity.this, paint
                , (TextActivity.this.getResources().getStringArray(R.array.typeface_path))[textBean.getStTypeFace()]);
        new TextBeanDao(TextActivity.this).update(textBean);
        Log.d("..........", "textBean的内容：" + textBean.toString());
        textBeens = new ArrayList<TextBean>();
        textBeens.add(textBean);
    }

    @OnClick({R.id.finshamg, R.id.text_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finshamg:
                finish();
                break;
            case R.id.text_btn:
                save();
                copyBitmap();
                Intent intent1 = new Intent(this, ProgramActivity.class);
                startActivity(intent1);
                TextActivity.this.finish();
                break;
        }
    }

    public Bitmap drawBitmap() {
        Paint paint = new Paint();
        paint.setTextSize(textBean.getStSize() + 3);
        String text = editText.getText().toString();
        textWidth = paint.measureText(text);
        textWidths = Integer.parseInt(STWidth.getText().toString());//文本框输入的长度
        textHeights = Integer.parseInt(STHeigth.getText().toString());//文本框输入的长度
        if (textWidth <= textWidths) {
            textWidth = textWidths;
        }
        int textHeight = textHeights;
        Log.d(".......", "textHeight........." + textHeight);
        Log.d(".......", "textWidth........." + textWidth);
        Bitmap bitmap = Bitmap.createBitmap((int) textWidth, textHeight, Bitmap.Config.ARGB_8888);
        int[] number_colors = new int[]{textBean.getBorderColor(), textBean.getStBackground(), textBean.getStColor()};
        for (int i = 0; i < number_colors.length; i++) {
            switch (number_colors[i]) {
                case 0:
                    number_colors[i] = Color.YELLOW;
                    break;
                case 1:
                    number_colors[i] = R.color.dodgerblue;
                    break;
                case 2:
                    number_colors[i] = Color.RED;
                    break;
                case 3:
                    number_colors[i] = Color.GREEN;
                    break;
                case 4:
                    number_colors[i] = R.color.mediumorchid;
                    break;
                case 5:
                    number_colors[i] = Color.BLUE;
                    break;
                case 6:
                    number_colors[i] = Color.BLACK;
                    break;
                case 7:
                    number_colors[i] = Color.GRAY;
                    break;
            }
        }
        paint.setColor(number_colors[2]); // 字体颜色 选择的 button **
        if (textBean.getStTypeFace() == 1 && textBean.isStBold() == true) {
            paint.setTypeface(Typeface.DEFAULT_BOLD);
        }
        if (textBean.isStUnderLine() == true) {
            paint.setUnderlineText(true);
        }
        if (textBean.isStItalic() == true && textBean.isStBold()) {
            paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        }
        Canvas canvas = new Canvas(bitmap); // 创建画布
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawText(text, 0, textBean.getHeidht() / 2, paint);
//        Paint paint1 = new Paint();
//        //给边框设置颜色
//        paint1.setColor(number_colors[0]);
//        //上
//        canvas.drawLine(0, 0, textWidth - 1, 0, paint1);
//        //左
//        canvas.drawLine(0, 0, 0, textHeight - 1, paint1);
//        //下
//        canvas.drawLine(0, textHeight - 1, textWidth - 1, textHeight - 1, paint1);
//        //右
//        canvas.drawLine(textWidth - 1, 0, textWidth - 1, textHeight - 1, paint1);
//        Log.d(".......", "Bitmap宽度........." + bitmap.getWidth());


        return bitmap;
    }

    public void copyBitmap() {
        int y = Integer.parseInt(STHeigth.getText().toString());
        int endBitmapWidth;
        if (drawBitmap().getWidth() <= textWidths) {
            Utils.saveMyBitmap(drawBitmap(), "text");
        } else {
            bitmapWidth = drawBitmap().getWidth();
            int bitmapCount = bitmapWidth / textWidths;//按屏幕可截取多少张图片
            if (bitmapWidth % textWidths > 0) {
                bitmapCount += 1;
                endBitmapWidth = bitmapWidth % textWidths;//最后一张图片截取的宽度
            } else {
                endBitmapWidth = textWidths;
            }
            Log.d(".......", "bitmapCount生成的图片可以截取几张图片。。。：" + bitmapCount);
            for (int i = 0; i < bitmapCount; i++) {
                int x = i * textWidths;
                Bitmap bmp;
                if (i == bitmapCount - 1) {
                    bmp = Bitmap.createBitmap(drawBitmap(), x, 0, endBitmapWidth, y, null, true);
                    zoomImg(bmp, textWidths, textHeights, i);
                    textWidths += textWidths;
                } else {
                    bmp = Bitmap.createBitmap(drawBitmap(), x, 0, textWidths, y, null, true);
                    Utils.saveMyBitmap(bmp, "text" + i);
                }


            }
        }
    }

    /**
     * 处理图片
     *
     * @param bm 所要转换的bitmap
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight, int i) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Log.d(".....", "matrix缩放比。。。" + matrix);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
//        Utils.saveMyBitmap(bm, "text" + i);
        return newbm;
    }
}