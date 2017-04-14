package com.eq.EQSuperPlayer.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.eq.EQSuperPlayer.bean.VedioBean;
import com.eq.EQSuperPlayer.custom.Constant;
import com.eq.EQSuperPlayer.dao.AreabeanDao;
import com.eq.EQSuperPlayer.dao.ProgramBeanDao;
import com.eq.EQSuperPlayer.dao.VedioDao;
import com.eq.EQSuperPlayer.utils.FileUtils;
import com.eq.EQSuperPlayer.utils.TimeChange;
import com.eq.EQSuperPlayer.utils.WindowSizeManager;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VedioActivity extends AppCompatActivity {

    private static final String TAG = "VedioActivity";
    @BindView(R.id.vedio_finsh)
    ImageView vedioFinsh;
    @BindView(R.id.vedio_send)
    Button vedioSend;
    @BindView(R.id.vedio)
    EditText vedio;
    @BindView(R.id.vedio_size)
    EditText vedioSize;
    @BindView(R.id.vedio_long)
    EditText vedioLong;
    @BindView(R.id.vedio_border)
    Spinner vedioBorder;
    @BindView(R.id.vedio_color)
    Spinner vedioColor;
    @BindView(R.id.vedio_x)
    EditText vedioX;
    @BindView(R.id.vedio_y)
    EditText vedioY;
    @BindView(R.id.vedio_width)
    EditText vedioWidth;
    @BindView(R.id.vedio_heigth)
    EditText vedioHeigth;
    @BindView(R.id.vedio_view)
    ImageView vedioView;
    @BindView(R.id.vedio_btn)
    ImageView vedioBtn;
    private static final int VIDEO_CAPTURE1 = 1;
    @BindView(R.id.vedio_nime)
    EditText vedioNime;
    @BindView(R.id.vedio_title)
    TextView vedioTitle;
    private Bitmap bitmap = null;
    private VedioBean vedioBean;
    private WindowSizeManager windowSizeManager;
    private int windowWidth;
    private int windowHeight;
    private ProgramBean programBean;
    private List<ProgramBean> programBeens;
    private Areabean areabean = new Areabean();
    public static  String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        areabean = new AreabeanDao(this).get(ProgramActivity.program_id);
        int vedio_id = getIntent().getIntExtra(Constant.PROGRAM_ID, -1);
        vedioBean = new VedioDao(this).get(vedio_id);
        programBean = new ProgramBeanDao(this).get(ProgramActivity.selet);
        //动态设置导航栏名称，用来确定该文本所属那个节目集合
        vedio.setText(programBean.getName());
        //必须添加，根据节目显示界面传过来的id来存储编辑界面的内容，不设置这项将造成本页数据不能写入对应的节目集合中....关键！！！.....
        vedioBean.setProgramBean(programBean);
        vedioTitle.setText(programBean.getName());
        windowSizeManager = WindowSizeManager.getSahrePreference(this);
        windowWidth = windowSizeManager.getWindowWidth();
        windowHeight = windowSizeManager.getWindowHeight();
        // 视频窗宽高
        if (vedioBean.getVedioWidth() != 0 && vedioBean.getVedioHeidht() != 0) {
            vedioWidth.setText(vedioBean.getVedioWidth() + "");
            vedioHeigth.setText(vedioBean.getVedioHeidht() + "");
        } else {
            vedioWidth.setText(areabean.getWindowWidth() + "");
            vedioHeigth.setText(areabean.getWindowHeight() + "");
        }
        if (vedioBean.getVedioX() != 0 || vedioBean.getVedioY() != 0){
            vedioX.setText(vedioBean.getVedioX() + "");
            vedioY.setText(vedioBean.getVedioY() + "");
        }else {
            vedioX.setText(areabean.getArea_X() + "");
            vedioY.setText(areabean.getArea_Y() + "");
        }

        TypedArray typedArray = this.getResources().obtainTypedArray(R.array.textcolor);
        int[] color_id = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            color_id[i] = typedArray.getResourceId(i, 0);
        }
        //边框
        vedioBorder.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.border)));
        vedioBorder.setSelection(vedioBean.getVedioBorder());
        //边框颜色
        vedioColor.setAdapter(new SpinnerImageAdapter(this, color_id));
        vedioColor.setSelection(vedioBean.getVedioBorderColor());
    }

    /**
     * 修改视频原数据的方法
     */
    public void vedioSave() {
        try {
            if (Integer.parseInt(vedioWidth.getText().toString()) + Integer.parseInt(vedioX.getText().toString()) <= windowWidth
                    && Integer.parseInt(vedioHeigth.getText().toString()) + Integer.parseInt(vedioY.getText().toString()) <= windowHeight) {
                vedioBean.setVedioWidth(Integer.parseInt(vedioWidth.getText().toString()));
                vedioBean.setVedioHeidht(Integer.parseInt(vedioHeigth.getText().toString()));
                vedioBean.setVedioX(Integer.parseInt(vedioX.getText().toString()));
                vedioBean.setVedioY(Integer.parseInt(vedioY.getText().toString()));
            } else {
                Toast.makeText(this, "参数超出边界，请重新设置", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {
        }
        vedioBean.setVedioName(vedio.getText().toString());
        vedioBean.setVedioBorder(vedioBorder.getSelectedItemPosition());
        vedioBean.setVedioBorderColor(vedioColor.getSelectedItemPosition());

        new VedioDao(this).update(vedioBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_CAPTURE1) {
            String fileVedioPath = Environment.getExternalStorageDirectory().toString() + File.separator
                    + "EQVedio/" +  System.currentTimeMillis() + ".mp4";
           final File file1 = new File(fileVedioPath);
            Uri uri = data.getData();
           final File file = getFileByUri(uri);
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    FileUtils.copyfile(file, file1,true );
                }
            }.start();

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
            mmr.setDataSource(file.getAbsolutePath());
            bitmap = mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            Log.d(TAG, "mime:" + mime);
            vedioNime.setText(mime);

            String bitrate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION); // 从api level 14才有，即从ICS4.0才有此功能
            Log.d(TAG, "bitrate:" + bitrate);
            String kb = TimeChange.bytes2kb(bitrate);
            Log.d(TAG, "kb:" + kb);
            String date = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
            Log.d(TAG, "date:" + date);
            Log.d("ddd", "duration==" + duration);
            int int_duration = Integer.parseInt(duration);
            String time = TimeChange.setTime(int_duration);
            vedioLong.setText(time);
            vedioSize.setText(kb);
            vedioBean.setVediohour(time);
            new VedioDao(this).update(vedioBean);
            vedioView.setImageBitmap(bitmap);

        }

    }
    public File getFileByUri(Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                Log.d(TAG, "buff:" + buff.length());
                buff.append("(").append(Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{Images.ImageColumns._ID, Images.ImageColumns.DATA}, buff.toString(), null,
                        null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
            Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }

    @OnClick({R.id.vedio_finsh, R.id.vedio_send, R.id.vedio_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vedio_finsh:
                Intent intent = new Intent(VedioActivity.this, ProgramActivity.class);
                startActivity(intent);
                VedioActivity.this.finish();
                break;
            case R.id.vedio_send://提交修改后的数据
                vedioSave();
                Log.d("..........", "vedioBean改变数据内容...............:" + vedioBean.toString());
                Intent intent1 = new Intent(VedioActivity.this, ProgramActivity.class);
                startActivity(intent1);
                VedioActivity.this.finish();
                break;

            case R.id.vedio_btn://点击打开手机视频
                String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "EQVedio";
                File file = new File(fileTextPath);
                if (!file.exists()) {
                    file.mkdir();
                }else {
                    FileUtils.deleteDir(fileTextPath);
                }
                Intent intent2 = new Intent(Intent.ACTION_PICK);
                intent2.setType("video/*");
                startActivityForResult(intent2, VIDEO_CAPTURE1);
                break;
        }
    }
}

