package com.eq.EQSuperPlayer.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.adapter.RecyclerViewAdapter;
import com.eq.EQSuperPlayer.adapter.TextAdapter;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.ImageBean;
import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.bean.TableBean;
import com.eq.EQSuperPlayer.bean.TextBean;
import com.eq.EQSuperPlayer.bean.TimeBean;
import com.eq.EQSuperPlayer.bean.TotalBean;
import com.eq.EQSuperPlayer.bean.VedioBean;
import com.eq.EQSuperPlayer.custom.Constant;
import com.eq.EQSuperPlayer.custom.CustomTypeWindow;
import com.eq.EQSuperPlayer.custom.ProgramePopWindow;
import com.eq.EQSuperPlayer.dao.AreabeanDao;
import com.eq.EQSuperPlayer.dao.ImageDao;
import com.eq.EQSuperPlayer.dao.ProgramBeanDao;
import com.eq.EQSuperPlayer.dao.TextBeanDao;
import com.eq.EQSuperPlayer.dao.TimeDao;
import com.eq.EQSuperPlayer.dao.VedioDao;
import com.eq.EQSuperPlayer.utils.AnimatorUtils;
import com.eq.EQSuperPlayer.utils.FileUtils;
import com.eq.EQSuperPlayer.utils.ProgramNameItemManager;
import com.eq.EQSuperPlayer.utils.Utils;
import com.eq.EQSuperPlayer.utils.WindowSizeManager;
import com.eq.EQSuperPlayer.view.MarqueeButton;
import com.eq.EQSuperPlayer.view.ProgramListView;
import com.eq.EQSuperPlayer.view.SlidingItemListView;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProgramActivity extends Activity implements View.OnClickListener, View.OnTouchListener {
    private RelativeLayout relt;
    private ImageView out_image;
    private TextView region_text;
    private Button prog_btn, region_btn, send_btn;
    private TextBean textBean;
    private TextAdapter textAdapter;
    private ImageBean imageBean;
    private TimeBean timeBean;
    private VedioBean vedioBean;
    private SlidingItemListView regionListView;
    private ProgramListView programListView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private CustomTypeWindow customTypeWindow;
    private ProgramePopWindow programePopWindow;
    private Areabean areabean;
    private ProgramBean programBean;
    private RelativeLayout text_layout;//显示屏
    private int windowWidth;//窗口宽度
    private int windowHeight; //窗口高度
    private int containerWidth;//屏幕宽度
    private int containerHeight;//屏幕高度
    public static int selet = 0;
    public int program_itme = 0;
    public static int program_id;
    private List<TableBean> tableBeens = new ArrayList<TableBean>();//区域数组
    private List<TotalBean> totalBeens = new ArrayList<TotalBean>();
    private List<String> mDatas = new ArrayList<String>();
    float lastX_drag, lastY_drag;
    float lastX_0, lastY_0;
    float lastX_1, lastY_1;
    float lastRotation = 0;
    private PointF lastMidPoint;
    private boolean start = false;   // 启动动画
    private Animation animation = null;   //
    private PointF pointF = new PointF();
    private float lastDistance;
    private float lastScale = 1;
    private int count = 0;
    private float scale = 0;//放大系数
    private static final double ZOOM_OUT_SCALE = 0.8;//缩小系数
    private float scaleWidth = 1;
    private float scaleHeight = 1;
    //模式
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    int mode = NONE;
    private int program_name_count;
    private int zoneIndex = 0;
    private boolean isScale=false;

    private MyOnTouchListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_program);
        initView();
        initData();

    }

    /**
     * 初始化数据
     */
    private void initView() {
        xmldata();
        textBean = new TextBean();
        region_text = (TextView) findViewById(R.id.region_text);
        out_image = (ImageView) findViewById(R.id.out_iamge);
        prog_btn = (Button) findViewById(R.id.region_program);
        text_layout = (RelativeLayout) findViewById(R.id.program_text_background);
        relt = (RelativeLayout) findViewById(R.id.relt);
        listener = new MyOnTouchListener();
        //读取宽高设置
        WindowSizeManager windowSizeManager = WindowSizeManager.getSahrePreference(this);
        windowHeight = windowSizeManager.getWindowHeight();
        windowWidth = windowSizeManager.getWindowWidth();
        //显示区域宽高设置
        text_layout.getLayoutParams().width = windowWidth;
        text_layout.getLayoutParams().height = windowHeight;
        Log.d("...........", "显示屏宽高...............：" + text_layout.getLayoutParams().height);
        Log.d("...........", "显示屏宽高...............：" + text_layout.getLayoutParams().width);
        //手势缩放监听事件，有些瑕疵，有待完善
        text_layout.setOnTouchListener(listener);
        //节目区手势监听
        region_btn = (Button) findViewById(R.id.region);
        send_btn = (Button) findViewById(R.id.region_send);
        out_image.setOnClickListener(this);
        prog_btn.setOnClickListener(this);
        region_btn.setOnClickListener(this);
        send_btn.setOnClickListener(this);
    }

    /**
     * 读取数据库数据
     */
    private void initData() {
        //读取数据库数据
        Log.d("............", "program_id数值.........:" + program_id);
        areabean = new AreabeanDao(this).get(program_id);
        ForeignCollection<ProgramBean> orders = areabean.getProgramBeen();
        CloseableIterator<ProgramBean> iterator = orders.closeableIterator();
        String str = areabean.getName() + "设备下所有节目:";
        while (iterator.hasNext()) {
            programBean = iterator.next();
            str += programBean.getId() + "号:" + programBean.getName();
            Log.d("............", "str数值.........:" + str);
            program_name_count = 1;
            programBean.setAreabean(areabean);
            tableBeens.add(programBean);
            Log.d("........", "tableBeens.............:" + tableBeens.toString());
            System.out.println("查询设备所有节目个数:" + programBean.toString());
        }

    }

    /*
  获取单个节目单中的所有节目
   */
    private void initDatas(ProgramBean programBean) {
        totalBeens.clear();
        mDatas.clear();
        text_layout.removeAllViews();
        //查询是否有视频
        ForeignCollection<VedioBean> vedio = programBean.getVedioBeen();
        if (vedio != null) {
            CloseableIterator<VedioBean> iterator4 = vedio.closeableIterator();
            while (iterator4.hasNext()) {
                vedioBean = iterator4.next();
                String vedios = vedioBean.getId() + ".............." + vedioBean.getVedioName();
                Log.d("............", "vedios数值.........:" + vedios);
                vedioBean.setProgramBean(programBean);
                vedioBean.setType(Constant.AREA_TYPE_VIDEO);
                totalBeens.add(vedioBean);
                showVedio();
            }


        }
        //查询是否有图片
        ForeignCollection<ImageBean> iamge = programBean.getImageBeen();
        if (iamge != null) {
            CloseableIterator<ImageBean> iterator2 = iamge.closeableIterator();
            while (iterator2.hasNext()) {
                imageBean = iterator2.next();
                String iamges = imageBean.getIamgeId() + ".............." + imageBean.getIamgeName();
                Log.d("............", "iamges数值.........:" + iamges);
                imageBean.setProgramBean(programBean);
                imageBean.setType(Constant.AREA_TYPE_IMAGE);
                totalBeens.add(imageBean);
                showImageView();
            }
        }
        //查询是否文本内容
        ForeignCollection<TextBean> text = programBean.getTextBeen();
        if (text != null) {
            CloseableIterator<TextBean> iterator1 = text.closeableIterator();
            while (iterator1.hasNext()) {
                textBean = iterator1.next();
                String tt = textBean.getId() + "..............." + textBean.getName();
                Log.d("............", "tt数值.........:" + tt);
                Paint paint = Utils.getPaint(this, Utils.getPaintSize(this, textBean.getStSize()));//字体参数启动读取
                paint.setFakeBoldText(textBean.isStBold());
                Utils.setTypeface(this, paint
                        , (this.getResources().getStringArray(R.array.typeface_path))[textBean.getStTypeFace()]);
                textBean.setPaint(paint);
                textBean.setProgramBean(programBean);
                textBean.setType(Constant.AREA_TYPE_TEXT);
                Log.d("..............", "查询设备所有节目内容:" + textBean.toString());
                totalBeens.add(textBean);
                showText();
            }
        }

        //查询是否有时钟
        ForeignCollection<TimeBean> time = programBean.getTimeBeen();
        if (time != null) {
            CloseableIterator<TimeBean> iterator3 = time.closeableIterator();
            while (iterator3.hasNext()) {
                timeBean = iterator3.next();
                String times = timeBean.getId() + ".............." + timeBean.getTimeToname();
                Log.d("............", "times数值.........:" + times);
                timeBean.setProgramBean(programBean);
                timeBean.setType(Constant.AREA_TYPE_IMAGE);
                totalBeens.add(timeBean);
            }
        }

        for (TotalBean totalBean : totalBeens) {
            Log.d("............", "totalBean中的数据...........:" + totalBean.getType());
            switch (totalBean.getType()) {
                case Constant.AREA_TYPE_IMAGE:
                    mDatas.add(getString(R.string.image));
                    break;
                case Constant.AREA_TYPE_TEXT:
                    mDatas.add(getString(R.string.text));
                    break;
                case Constant.AREA_TYPE_TIME:
                    mDatas.add(getString(R.string.time));
                    break;
                case Constant.AREA_TYPE_VIDEO:
                    mDatas.add(getString(R.string.video));
                    break;
            }
        }
    }

    /*
      显示文本内容
    */
    private void showText() {
        int[] number_colors = new int[]{textBean.getBorderColor(), textBean.getStBackground(), textBean.getStColor()};
        for (int i = 0; i < number_colors.length; i++) {
            switch (number_colors[i]) {
                case 0:
                    number_colors[i] = Color.YELLOW;
                    break;
                case 1:
                    number_colors[i] = Color.MAGENTA;
                    break;
                case 2:
                    number_colors[i] = Color.RED;
                    break;
                case 3:
                    number_colors[i] = Color.GREEN;
                    break;
                case 4:
                    number_colors[i] = Color.WHITE;
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
        MarqueeButton textview = new MarqueeButton(this);
        textview.setText(textBean.getSingleTextValue());
        textview.setX(textBean.getX());
        textview.setY(textBean.getY());
        textview.setOnTouchListener(listener);
        textview.setTextColor(number_colors[2]);
        textview.setTextSize(textBean.getStSize());
        textview.setWidth(textBean.getWidth());
        textview.setHeight(textBean.getHeidht());
        textview.setBackgroundColor(0x0000);
        textview.setBackgroundResource(R.drawable.bodler_shape);
        text_layout.addView(textview);

    }

    /**
     * 添加ImageView控件
     **/
    private void showImageView() {
        String fileImagePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "EQImage";
        File fileAll = new File(fileImagePath);
        if (!fileAll.exists()) {
            fileAll.mkdir();
        }
        File[] files = fileAll.listFiles();

        View view = View.inflate(this, R.layout.image_viewflipler, null);

        LinearLayout mImagelayout = (LinearLayout) view.findViewById(R.id.image_layout);
        LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) mImagelayout.getLayoutParams();
        ll.width = windowWidth;
        ll.height = windowHeight;
        mImagelayout.setLayoutParams(ll);
        ViewFlipper flipler = (ViewFlipper) view.findViewById(R.id.image_flipler);

        flipler.setOnTouchListener(listener);
        int hour = imageBean.getIamgeandtime();
        flipler.setFlipInterval(hour * 1000);//设置自动播放的时间间隔为3S
        flipler.setAutoStart(true);
        flipler.setX(imageBean.getIamgeX());
        flipler.setY(imageBean.getIamgeY());
        for (int j = 0; j < files.length; j++) {
            File file1 = files[j];
            View imageviews = View.inflate(this, R.layout.image_item, null);
            ImageView imageView = (ImageView) imageviews.findViewById(R.id.imageitem);
            ViewGroup.LayoutParams il = imageView.getLayoutParams();
            il.width = imageBean.getIamgeWidth();
            il.height = imageBean.getIamgeHeidht();
            imageView.setLayoutParams(il);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(R.drawable.bodler_shape);
            Glide.with(this).load(file1.getPath()).into(imageView);
            flipler.addView(imageviews);
        }
        text_layout.addView(view);
    }

    /*
   添加视频显示
    */
    private void showVedio() {
        String fileVedioPath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "EQVedio";
        File fileAll = new File(fileVedioPath);
        if (!fileAll.exists()) {
            fileAll.mkdir();
        }
        File[] files = fileAll.listFiles();
        for (int j = 0; j < files.length; j++) {

            //定义父容器为填充窗口
            View view = View.inflate(this, R.layout.videoshow, null);
            RelativeLayout mvideolayout = (RelativeLayout) view.findViewById(R.id.videoparent);
            RelativeLayout.LayoutParams ll = (RelativeLayout.LayoutParams) mvideolayout.getLayoutParams();
            ll.width = windowWidth;
            ll.height = windowHeight;
            mvideolayout.setLayoutParams(ll);

            //定义显示播放的视频的宽高和x y 的距离
            RelativeLayout vl = (RelativeLayout) view.findViewById(R.id.videolayout);
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) vl.getLayoutParams();
            rl.width = vedioBean.getVedioWidth();
            rl.height = vedioBean.getVedioHeidht();
            vl.setLayoutParams(rl);
            vl.setOnTouchListener(listener);
            vl.setX(vedioBean.getVedioX());
            vl.setY(vedioBean.getVedioY());

            //取出视频的地址并且进行播放
            File file1 = files[j];
            VideoView vv = (VideoView) view.findViewById(R.id.videoview);
            vv.setVideoPath(file1.getPath());
            // 开始播放
            vv.start();

            vv.setOnTouchListener(listener);

            //监听视频播放完的后循环播放
            vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mPlayer) {
                    // TODO Auto-generated method stub
                    mPlayer.start();
                    mPlayer.setLooping(true);
                }
            });
            text_layout.addView(view);
        }

    }



    private View getTypeWindowListView() {
        View view = this.getLayoutInflater().inflate(R.layout.recry_itme, null);
        programListView = (ProgramListView) view.findViewById(R.id.program_listview);
        programListView.setOnTouchListener(this);
        programListView.setEmptyView(view.findViewById(R.id.myText));
        TextView textView = (TextView) view.findViewById(R.id.text_name);
        Button button = (Button) view.findViewById(R.id.pro_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //填充的数据源
                String[] arrayFruit = new String[]{getResources().getString(R.string.program_text),
                        getResources().getString(R.string.program_video),
                        getResources().getString(R.string.program_image),
//                      getResources().getString(R.string.program_time),

                };
                AlertDialog.Builder dia = new AlertDialog.Builder(ProgramActivity.this);
                dia.setTitle(getString(R.string.program_area_add))
                        .setItems(arrayFruit,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ProgramBean programBean = new ProgramBeanDao(ProgramActivity.this).get(selet);
//                                        Log.d("...............", "programBean数据......." + programBean.toString());
                                        if (which == 0) {
                                            TextBean textBean = new TextBean();
                                            textBean.setProgramBean(programBean);
                                            //创建时画笔赋值
                                            Paint paint = Utils.getPaint(ProgramActivity.this, Utils.getPaintSize(ProgramActivity.this, 15));
                                            paint.setFakeBoldText(textBean.isStBold());
                                            Utils.setTypeface(ProgramActivity.this, paint
                                                    , (ProgramActivity.this.getResources().getStringArray(R.array.typeface_path))[textBean.getStTypeFace()]);
                                            textBean.setType(Constant.AREA_TYPE_TEXT);
                                            textBean.setName(getString(R.string.text));
                                            totalBeens.add(textBean);
                                            mDatas.add(getString(R.string.text));
                                            new TextBeanDao(ProgramActivity.this).add(textBean);
                                            Log.d("..............", "textBean...........:" + textBean.toString());
                                            Log.d("..............", "totalBeens...........:" + totalBeens);
                                            dialog.dismiss();
                                            recyclerViewAdapter.notifyDataSetChanged();
                                            recyclerViewAdapter.notifyDataSetInvalidated();
                                        } else if (which == 1) {
                                            VedioBean vedioBean = new VedioBean();
                                            vedioBean.setProgramBean(programBean);
                                            vedioBean.setType(Constant.AREA_TYPE_VIDEO);
                                            vedioBean.setVedioName(getString(R.string.video));
                                            new VedioDao(ProgramActivity.this).add(vedioBean);
                                            Log.d("..............", "vedioBean...........:" + vedioBean.toString());
                                            List<VedioBean> vedioBeens = new ArrayList<VedioBean>();
                                            vedioBeens.add(vedioBean);
                                            mDatas.add(getString(R.string.video));
                                            totalBeens.add(vedioBean);
                                            dialog.dismiss();
                                            recyclerViewAdapter.notifyDataSetChanged();
                                            recyclerViewAdapter.notifyDataSetInvalidated();

                                        } else if (which == 2) {
                                            ImageBean imageBean = new ImageBean();
                                            imageBean.setProgramBean(programBean);
                                            imageBean.setType(Constant.AREA_TYPE_IMAGE);
                                            imageBean.setIamgeName(getString(R.string.image));
                                            new ImageDao(ProgramActivity.this).add(imageBean);
                                            List<ImageBean> imageBeans = new ArrayList<ImageBean>();
                                            imageBeans.add(imageBean);
                                            mDatas.add(getString(R.string.image));
                                            recyclerViewAdapter.notifyDataSetChanged();
                                            recyclerViewAdapter.notifyDataSetInvalidated();
                                            totalBeens.add(imageBean);
                                        } else if (which == 3) {
                                            TimeBean timeBean = new TimeBean();
                                            timeBean.setProgramBean(programBean);
                                            timeBean.setTimeToname(getString(R.string.time));
                                            timeBean.setType(Constant.AREA_TYPE_TIME);
                                            new TimeDao(ProgramActivity.this).add(timeBean);
                                            mDatas.add(getString(R.string.time));
                                            totalBeens.add(timeBean);
                                            dialog.dismiss();
                                            recyclerViewAdapter.notifyDataSetChanged();
                                            recyclerViewAdapter.notifyDataSetInvalidated();
                                        }
                                    }
                                });
                dia.show();
            }
        });
        textView.setText(getResources().getText(R.string.the_programme));
        final ProgramBean programBean = new ProgramBeanDao(this).get(selet);
        recyclerViewAdapter = new RecyclerViewAdapter(ProgramActivity.this, mDatas);
        recyclerViewAdapter.setmRemoveViewListener(new RecyclerViewAdapter.OnRemoveViewListener() {
            @Override
            public void onRemoveItem(int position) {
                // 删除按钮的回调，注意也可以放在adapter里面处理
                if (textBean == totalBeens.get(position)) {
                    Log.d("...............", "textBean.........:" + textBean.toString());
                    mDatas.remove(position);
                    totalBeens.remove(position);
                    new TextBeanDao(ProgramActivity.this).delete(textBean);
                    programListView.slideBack();
                    String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                            + "EQText";
                    FileUtils.deleteDir(fileTextPath);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.notifyDataSetInvalidated();
                } else if (timeBean == totalBeens.get(position)) {
                    mDatas.remove(position);
                    totalBeens.remove(position);
                    new TimeDao(ProgramActivity.this).delete(timeBean);
                    programListView.slideBack();
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.notifyDataSetInvalidated();
                } else if (imageBean == totalBeens.get(position)) {
                    mDatas.remove(position);
                    totalBeens.remove(position);
                    new ImageDao(ProgramActivity.this).delete(imageBean);
                    programListView.slideBack();
                    String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                            + "EQImage";
                    FileUtils.deleteDir(fileTextPath);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.notifyDataSetInvalidated();
                } else if (vedioBean == totalBeens.get(position)) {
                    mDatas.remove(position);
                    totalBeens.remove(position);
                    new VedioDao(ProgramActivity.this).delete(vedioBean);
                    programListView.slideBack();
                    String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                            + "EQVedio";
                    FileUtils.deleteDir(fileTextPath);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.notifyDataSetInvalidated();
                }
                //获取当前显示的节目单，进行删除后刷新显示区
                ProgramBean programBean = (ProgramBean) tableBeens.get(program_itme);
                initDatas(programBean);
            }
        });
        programListView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.notifyDataSetInvalidated();

        programListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (totalBeens.get(position).getType()) {
                    case Constant.AREA_TYPE_IMAGE:
                        intent = new Intent();
                        ImageBean imageBean = (ImageBean) totalBeens.get(position);
                        intent.setClass(ProgramActivity.this, ImageActivity.class);
                        intent.putExtra(Constant.PROGRAM_ID, imageBean.getId());
                        startActivity(intent);
                        customTypeWindow.dismiss();
                        ProgramActivity.this.finish();
                        break;
                    case Constant.AREA_TYPE_TEXT:
                        intent = new Intent();
                        TextBean textBean = (TextBean) totalBeens.get(position);
                        Log.d(".........", "textBean..........:" + textBean.getId());
                        intent.setClass(ProgramActivity.this, TextActivity.class);
                        intent.putExtra(Constant.PROGRAM_ID, textBean.getId());
                        startActivity(intent);
                        String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                                + "EQText";
                        FileUtils.deleteDir(fileTextPath);
                        customTypeWindow.dismiss();
                        ProgramActivity.this.finish();
                        break;
                    case Constant.AREA_TYPE_TIME:
                        intent = new Intent();
                        TimeBean timeBean = (TimeBean) totalBeens.get(position);
                        intent.setClass(ProgramActivity.this, TimeActivity.class);
                        intent.putExtra(Constant.PROGRAM_ID, timeBean.getId());
                        startActivity(intent);
                        customTypeWindow.dismiss();
                        ProgramActivity.this.finish();
                        break;
                    case Constant.AREA_TYPE_VIDEO:
                        intent = new Intent();
                        VedioBean vedioBean = (VedioBean) totalBeens.get(position);
                        intent.setClass(ProgramActivity.this, VedioActivity.class);
                        intent.putExtra(Constant.PROGRAM_ID, vedioBean.getId());
                        startActivity(intent);
                        customTypeWindow.dismiss();
                        ProgramActivity.this.finish();
                        break;
                }

            }
        });
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.notifyDataSetInvalidated();
        return view;
    }

    public View getPopWindowListView() {
        View view = this.getLayoutInflater().inflate(R.layout.region_itme, null);
        regionListView = (SlidingItemListView) view.findViewById(R.id.program_listname);
        regionListView.setOnTouchListener(this);
        regionListView.setEmptyView(view.findViewById(R.id.myText));
        TextView textView = (TextView) view.findViewById(R.id.text_name);
        textView.setText(getResources().getText(R.string.programlist));
        Button button = (Button) view.findViewById(R.id.text_button);
        if (tableBeens.size() == 0) {
            program_name_count = 1;
        } else {
            program_name_count = ProgramNameItemManager.getSahrePreference(ProgramActivity.this);
        }
        textAdapter = new TextAdapter(ProgramActivity.this, tableBeens);
        textAdapter.setmRemoveTextListener(new TextAdapter.OnRemoveTextListener() {
            @Override
            public void onRemoveItem(int position) {
                ProgramBean programBean = (ProgramBean) tableBeens.get(position);
                regionListView.slideBack();
                new ProgramBeanDao(ProgramActivity.this).delete(programBean.getId());
                tableBeens.remove(position);
                List<String> filePath = new ArrayList<>();
                String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "EQText";
                filePath.add(fileTextPath);
                String fileImagePath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "EQImage";
                filePath.add(fileImagePath);
                String fileVedioPath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "EQVedio";
                filePath.add(fileVedioPath);
                String PROGRAME_ROOT = Environment
                        .getExternalStorageDirectory()
                        .getAbsolutePath() + "/EQPrograme/";
                filePath.add(PROGRAME_ROOT);
                for (int i = 0; i < filePath.size(); i++) {
                    File fileAll = new File(filePath.get(i));
                    if (!fileAll.exists()) {
                        fileAll.mkdir();
                    }
                    FileUtils.deleteDir(fileAll.getPath());
                }
                textAdapter.notifyDataSetChanged();
                textAdapter.notifyDataSetInvalidated();
            }
        });
        regionListView.setAdapter(textAdapter);
        textAdapter.notifyDataSetChanged();
        textAdapter.notifyDataSetInvalidated();
        //选中的节目itme， selet为选中itme的id，program_itme为选中的那个itme
        regionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("..................", "tableBeens数据...............:" + tableBeens.toString());
                ProgramBean programBean = (ProgramBean) tableBeens.get(position);
                initDatas(programBean);
                region_text.setText(programBean.getName());
                selet = programBean.getId();
                program_itme = position;
                programePopWindow.dismiss();
                Log.d("..................", "selet数据...............:" + selet);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programBean = new ProgramBean();
                areabean = new AreabeanDao(ProgramActivity.this).get(program_id);
                programBean.setAreabean(areabean);
                programBean.setName(getResources().getText(R.string.main_program) + "" + program_name_count);
                Log.d("..............", "areabean...........:" + areabean.toString());
                program_name_count++;
                ProgramNameItemManager.setSharedPreference(ProgramActivity.this, program_name_count);
                tableBeens.add(programBean);
                new ProgramBeanDao(ProgramActivity.this).add(programBean);
                textAdapter.notifyDataSetChanged();
                textAdapter.notifyDataSetInvalidated();
            }
        });
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.out_iamge:
                this.finish();
                break;
            case R.id.region_program:
                if (programePopWindow == null) {
                    programePopWindow = new ProgramePopWindow(this, R.id.region_program);
                    programePopWindow.setView(getPopWindowListView(),1.0f, 0.57f);
                    programePopWindow.backgroundAlpha(1f);
                }
                programePopWindow.showPopupWindow(prog_btn);
                break;
            case R.id.region:
                if (tableBeens.size() != 0) {
                    if (!(region_text.getText().toString()).equals("区域管理")) {
                        if (customTypeWindow == null) {
                            customTypeWindow = new CustomTypeWindow(this, R.id.region);
                            customTypeWindow.setView(getTypeWindowListView(), 1.0f, 0.57f);
                            customTypeWindow.backgroundAlpha(1f);
                        }
                        customTypeWindow.showPopupWindow(region_btn);
                    } else {
                        Toast.makeText(ProgramActivity.this, "请先选择节目！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProgramActivity.this, "还没有节目哟！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.region_send:
                break;
        }
    }

    /*
     //显示屏启动动画
    */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 这里来获取容器的宽和高
        if (hasFocus) {
            containerWidth = relt.getHeight();
            containerHeight = relt.getWidth();
        }
    }
    class MyOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                //进入拖拽模式
                case MotionEvent.ACTION_DOWN:
                    System.out.println("uuuu"+v.getMeasuredWidth());
                    mode = DRAG;
                    lastX_drag = event.getRawX();
                    lastY_drag = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e("count", count + " ");
                    count++;
                    if (mode == DRAG) {
                        //单点触控
                        drag(v, event);
                    } else if (mode == ZOOM) {
                        //缩放倍数
                        float currentDistance = spacing(event);
                        Log.e("触摸_间距", currentDistance + "  " + lastDistance + " ");
                        if (currentDistance > lastRotation) {
                            Log.e("屏幕_缩放", "放大");
                            scale = (float) (lastScale + (float)(currentDistance-lastRotation)/lastRotation);
                            AnimatorUtils.scale(v, lastScale, scale, lastScale, scale);
                            lastScale = scale;
                        }
                        if (currentDistance < lastRotation) {
                            Log.e("屏幕_缩放", "缩小");
                            scale = (float) (lastScale -(float)(lastRotation-currentDistance)/lastRotation);
                            AnimatorUtils.scale(v, lastScale, scale, lastScale, scale);
                            lastScale = scale;
                        }
                        //获取当前手势中心点
                        lastDistance = currentDistance;

                    }
                    break;
                //进入多点触控模式
                case MotionEvent.ACTION_POINTER_DOWN:
                    lastX_0 = event.getX(0);
                    lastY_0 = event.getY(0);
                    lastX_1 = event.getX(1);
                    lastY_1 = event.getY(1);
                    mode = ZOOM;
                    //获取两个手指之间的距离
                    lastRotation = spacing(event);
                    //获取第一次的角度
                    lastMidPoint = midPoint(pointF, event);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;
                    break;
            }
            return false;
        }

        //拖拽
        public void drag(View v, MotionEvent event) {
            //  不要直接用getX和getY,这两个获取的数据已经是经过处理的,容易出现图片抖动的情况
            float distanceX = event.getRawX() - lastX_drag;
            float distanceY = event.getRawY() - lastY_drag;
            float nextY = v.getY() + distanceY;
            float nextX = v.getX() + distanceX;
            // 不能移出屏幕
            if (nextX < 0) {
                nextX = 0;
            } else if (nextX + v.getWidth() > windowWidth) {
                nextX = windowWidth - v.getWidth();
            }
            if (nextY < 0) {
                nextY = 0;
            } else if (nextY + v.getHeight() > windowHeight) {
                nextY = windowHeight - v.getHeight();
            }
            AnimatorUtils.translate(v, v.getX(), v.getY(), nextX, nextY);
            v.setX(v.getX()+nextX);
            lastX_drag = event.getRawX();
            lastY_drag = event.getRawY();
        }

        private float spacing(MotionEvent event) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        }

        private PointF midPoint(PointF point, MotionEvent event) {
            float x = event.getX(0) + event.getX(1);
            float y = event.getY(0) + event.getY(1);
            point.set(x / 2, y / 2);
            return point;
        }
    }
    /**
     * 检查扩展名，得到图片格式的文件
     *
     * @param fName 文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    public void xmldata() {
        areabean = new AreabeanDao(this).get(program_id);
        String SD_PATH = android.os.Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();
        String FILE_PATH = "/EQprograme";
        File file = new File(SD_PATH + FILE_PATH);
        if (!file.exists())
            file.mkdir();
        try {
            File file1 = new File(SD_PATH
                    + FILE_PATH
                    + "/"
                    + "programe" + program_id + ".xml");
            FileOutputStream fos = new FileOutputStream(file1);
            // 获得一个序列化工具
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fos, "utf-8");
            // 设置文件头
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "manifest");
            serializer.attribute(null, "xmlns:EQ600X=\"www.eqled.com\" xmltype=\"APP\" version", "1");
            serializer.startTag(null, "group");
            serializer.attribute(null, "index", "0");
            ForeignCollection<ProgramBean> orders = areabean.getProgramBeen();
            CloseableIterator<ProgramBean> iterator = orders.closeableIterator();
            while (iterator.hasNext()) {
                int indexId = 0;
                programBean = iterator.next();
                serializer.startTag(null, "programe");
                serializer.attribute(null, "index", indexId + "");
                indexId++;
                serializer.startTag(null, "attribute");
                serializer.text(" ");
                serializer.endTag(null, "attribute");
                TableBean tableBean = new TableBean();
                //生成文本xml
                ForeignCollection<TextBean> text = programBean.getTextBeen();
                CloseableIterator<TextBean> iteratorText = text.closeableIterator();
                if (iteratorText != null) {
                    while (iteratorText.hasNext()) {
                        textBean = iteratorText.next();
                        zoneIndex = 0;
                        serializer.startTag(null, "zone");
                        serializer.attribute(null, "index", zoneIndex + "");
                        zoneIndex++;
                        serializer.startTag(null, "zonetype");
                        serializer.text("bmp");
                        serializer.endTag(null, "zonetype");

                        serializer.startTag(null, "attribute");

                        serializer.startTag(null, "x");
                        serializer.text(String.valueOf(areabean.getArea_X()));
                        serializer.endTag(null, "x");

                        serializer.startTag(null, "y");
                        serializer.text(String.valueOf(areabean.getArea_Y()));
                        serializer.endTag(null, "y");

                        serializer.startTag(null, "width");
                        serializer.text(String.valueOf(textBean.getWidth()));
                        serializer.endTag(null, "width");

                        serializer.startTag(null, "heidht");
                        serializer.text(String.valueOf(textBean.getHeidht()));
                        serializer.endTag(null, "heidht");
                        serializer.endTag(null, "attribute");
                        // 图片列表
                        List<String> imagePathList = new ArrayList<String>();
                        // 得到sd卡内image文件夹的路径   File.separator(/)
                        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                                + "EQText";
                        // 得到该路径文件夹下所有的文件
                        File fileAll = new File(filePath);
                        File[] files = fileAll.listFiles();
                        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
                        for (int i = 0; i < files.length; i++) {
                            File file2 = files[i];
                            if (checkIsImageFile(file2.getPath())) {
                                imagePathList.add(file2.getPath());
                                String imageName = file2.getPath().substring(file2.getPath().lastIndexOf("/") + 1, file2.getPath().length());
                                serializer.startTag(null, "file");
                                serializer.attribute(null, "index", i + "");

                                serializer.startTag(null, "type");
                                serializer.text("bmp");
                                serializer.endTag(null, "type");

                                serializer.startTag(null, "name");
                                serializer.text(imageName);
                                serializer.endTag(null, "name");
                                serializer.startTag(null, "action");

                                serializer.startTag(null, "InType");
                                serializer.text(String.valueOf(textBean.getEntertrick()));
                                serializer.endTag(null, "InType");

                                serializer.startTag(null, "InStep");
                                serializer.text(String.valueOf(textBean.getEnterspeed()));
                                serializer.endTag(null, "InStep");

                                serializer.startTag(null, "OutType");
                                serializer.text(String.valueOf(textBean.getCleartrick()));
                                serializer.endTag(null, "OutType");

                                serializer.startTag(null, "OutStep");
                                serializer.text(String.valueOf(textBean.getClearspeed()));
                                serializer.endTag(null, "OutStep");

                                serializer.startTag(null, "HoldTime");
                                serializer.text(String.valueOf(textBean.getStandtime()));
                                serializer.endTag(null, "HoldTime");

                                serializer.endTag(null, "action");
                                serializer.endTag(null, "file");
                                Log.d("...............", "全部数据................:" + textBean.toString());
                            }
                        }
                        serializer.endTag(null, "zone");
                    }
                }
                //生成图片xml
                ForeignCollection<ImageBean> iamge = programBean.getImageBeen();
                CloseableIterator<ImageBean> iteratorImage = iamge.closeableIterator();
                if (iteratorImage != null) {
                    while (iteratorImage.hasNext()) {
                        imageBean = iteratorImage.next();
                        serializer.startTag(null, "zone");
                        serializer.attribute(null, "index", zoneIndex + "");
                        zoneIndex++;
                        serializer.startTag(null, "zonetype");
                        serializer.text("file");
                        serializer.endTag(null, "zonetype");
                        serializer.startTag(null, "attribute");
                        serializer.startTag(null, "x");
                        serializer.text(String.valueOf(imageBean.getIamgeX()));
                        serializer.endTag(null, "x");

                        serializer.startTag(null, "y");
                        serializer.text(String.valueOf(imageBean.getIamgeY()));
                        serializer.endTag(null, "y");

                        serializer.startTag(null, "width");
                        serializer.text(String.valueOf(imageBean.getIamgeWidth()));
                        serializer.endTag(null, "width");

                        serializer.startTag(null, "heidht");
                        serializer.text(String.valueOf(imageBean.getIamgeHeidht()));
                        serializer.endTag(null, "heidht");
                        serializer.endTag(null, "attribute");
                        // 图片列表
                        List<String> imagePathList = new ArrayList<String>();
                        // 得到sd卡内image文件夹的路径   File.separator(/)
                        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                                + "EQImage";
                        // 得到该路径文件夹下所有的文件
                        File fileAll = new File(filePath);
                        File[] files = fileAll.listFiles();
                        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
                        for (int i = 0; i < files.length; i++) {
                            File file2 = files[i];
                            if (checkIsImageFile(file2.getPath())) {
                                imagePathList.add(file2.getPath());
                                String imageName = file2.getPath().substring(file2.getPath().lastIndexOf("/") + 1, file2.getPath().length());
                                Log.d("............", "imageName............" + imageName);
                                Log.d("............", "imagePathList...................:" + imagePathList);
                                serializer.startTag(null, "file");
                                serializer.attribute(null, "index", String.valueOf(i));

                                serializer.startTag(null, "type");
                                serializer.text("2");
                                serializer.endTag(null, "type");

                                serializer.startTag(null, "name");
                                serializer.text(imageName);
                                serializer.endTag(null, "name");

                                serializer.startTag(null, "action");

                                serializer.startTag(null, "InType");
                                serializer.text(String.valueOf(imageBean.getIamgeEntertrick()));
                                serializer.endTag(null, "InType");

                                serializer.startTag(null, "InStep");
                                serializer.text(String.valueOf(imageBean.getIamgeEnterspeed()));
                                serializer.endTag(null, "InStep");

                                serializer.startTag(null, "OutType");
                                serializer.text(String.valueOf(imageBean.getIamgeCleartrick()));
                                serializer.endTag(null, "OutType");

                                serializer.startTag(null, "OutStep");
                                serializer.text(String.valueOf(imageBean.getIamgeClearspeed()));
                                serializer.endTag(null, "OutStep");

                                serializer.startTag(null, "HoldTime");
                                serializer.text(String.valueOf(imageBean.getIamgeandtime()));
                                serializer.endTag(null, "HoldTime");

                                serializer.endTag(null, "action");
                                serializer.endTag(null, "file");
                            }
                        }
                        serializer.endTag(null, "zone");
                        Log.d("...............", "全部数据................:" + imageBean.toString());
                    }
                }

                //生成视频xml
                ForeignCollection<VedioBean> vedio = programBean.getVedioBeen();
                CloseableIterator<VedioBean> iteratorVedio = vedio.closeableIterator();
                if (iteratorVedio != null) {
                    while (iteratorVedio.hasNext()) {
                        vedioBean = iteratorVedio.next();
                        serializer.startTag(null, "zone");
                        serializer.attribute(null, "index", zoneIndex + "");
                        zoneIndex++;
                        serializer.startTag(null, "zonetype");
                        serializer.text("file");
                        serializer.endTag(null, "zonetype");

                        serializer.startTag(null, "attribute");
                        serializer.startTag(null, "x");
                        serializer.text(String.valueOf(vedioBean.getVedioX()));
                        serializer.endTag(null, "x");

                        serializer.startTag(null, "y");
                        serializer.text(String.valueOf(vedioBean.getVedioY()));
                        serializer.endTag(null, "y");

                        serializer.startTag(null, "width");
                        serializer.text(String.valueOf(vedioBean.getVedioWidth()));
                        serializer.endTag(null, "width");

                        serializer.startTag(null, "heidht");
                        serializer.text(String.valueOf(vedioBean.getVedioHeidht()));
                        serializer.endTag(null, "heidht");
                        serializer.endTag(null, "attribute");
                        // 视频列表
                        List<String> imagePathList = new ArrayList<String>();
                        // 得到sd卡内image文件夹的路径   File.separator(/)
                        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                                + "EQVedio";
                        // 得到该路径文件夹下所有的文件
                        File fileAll = new File(filePath);
                        File[] files = fileAll.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            File fileVedio = files[i];
                            imagePathList.add(fileVedio.getPath());
                            String vedioName = fileVedio.getPath().substring(fileVedio.getPath().lastIndexOf("/") + 1, fileVedio.getPath().length());
                            serializer.startTag(null, "file");
                            serializer.attribute(null, "index", i + "");

                            serializer.startTag(null, "type");
                            serializer.text("1");
                            serializer.endTag(null, "type");

                            serializer.startTag(null, "name");
                            serializer.text(vedioName);
                            serializer.endTag(null, "name");

                            serializer.startTag(null, "playmode");
                            serializer.text("0");
                            serializer.endTag(null, "playmode");

                            serializer.startTag(null, "playmodeValue");
                            serializer.text("1");
                            serializer.endTag(null, "playmodeValue");

                            serializer.startTag(null, "volume");
                            serializer.text("100");
                            serializer.endTag(null, "volume");

                            serializer.endTag(null, "file");
                        }
                        serializer.endTag(null, "zone");
                        Log.d("...............", "全部数据................:" + vedioBean.toString());
                    }
                }
                //生成时间xml
                ForeignCollection<TimeBean> timeData = programBean.getTimeBeen();
                CloseableIterator<TimeBean> iteratorTime = timeData.closeableIterator();
                if (iteratorTime != null) {
                    while (iteratorTime.hasNext()) {
                        timeBean = iteratorTime.next();
                        serializer.startTag(null, "zone");
                        serializer.attribute(null, "index", zoneIndex + "");
                        zoneIndex++;
                        serializer.startTag(null, "zonetype");

                        serializer.text("clock");
                        serializer.endTag(null, "zonetype");
                        serializer.startTag(null, "attribute");
                        serializer.startTag(null, "x");
                        serializer.text(String.valueOf(areabean.getArea_X()));
                        serializer.endTag(null, "x");

                        serializer.startTag(null, "y");
                        serializer.text(String.valueOf(areabean.getArea_Y()));
                        serializer.endTag(null, "y");

                        serializer.startTag(null, "width");
                        serializer.text(String.valueOf(areabean.getWindowWidth()));
                        serializer.endTag(null, "width");

                        serializer.startTag(null, "heidht");
                        serializer.text(String.valueOf(areabean.getWindowHeight()));
                        serializer.endTag(null, "heidht");
                        serializer.endTag(null, "attribute");
                        // 图片列表
                        List<String> imagePathList = new ArrayList<String>();
                        // 得到sd卡内image文件夹的路径   File.separator(/)
                        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                                + "timeImage";
                        // 得到该路径文件夹下所有的文件
                        File fileAll = new File(filePath);
                        File[] files = fileAll.listFiles();
                        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
                        for (int i = 0; i < files.length; i++) {
                            File file2 = files[i];
                            if (checkIsImageFile(file2.getPath())) {
                                imagePathList.add(file2.getPath());
                                String imageName = file2.getPath().substring(file2.getPath().lastIndexOf("/") + 1, file2.getPath().length());
                                Log.d("............", "imageName............" + imageName);
                                Log.d("............", "imagePathList...................:" + imagePathList);
                                serializer.startTag(null, "file");
                                serializer.attribute(null, "index", "0");

                                serializer.startTag(null, "style");
                                serializer.text("1");
                                serializer.endTag(null, "style");

                                serializer.startTag(null, "font");

                                serializer.startTag(null, "name");
                                serializer.text("宋体");
                                serializer.endTag(null, "name");

                                serializer.startTag(null, "name");
                                serializer.text(imageName);
                                serializer.endTag(null, "name");

                                serializer.startTag(null, "size");
                                serializer.text("16");
                                serializer.endTag(null, "size");

                                serializer.startTag(null, "bold");
                                serializer.text(imageName);
                                serializer.endTag(null, "bold");

                                serializer.startTag(null, "underline");
                                serializer.text(imageName);
                                serializer.endTag(null, "underline");

                                serializer.startTag(null, "italic");
                                serializer.text(imageName);
                                serializer.endTag(null, "italic");

                                serializer.startTag(null, "color");
                                serializer.text(imageName);
                                serializer.endTag(null, "color");

                                serializer.endTag(null, "font");
                                serializer.startTag(null, "dateformat");
                                serializer.text("yyyy-MM-dd");
                                serializer.endTag(null, "dateformat");

                                serializer.startTag(null, "timeformat");
                                serializer.text("HH:mm:ss");
                                serializer.endTag(null, "timeformat");

                                serializer.startTag(null, "text");
                                serializer.text("北京时间");
                                serializer.endTag(null, "text");

                                serializer.startTag(null, "showdate");
                                serializer.text("1");
                                serializer.endTag(null, "showdate");

                                serializer.startTag(null, "showweek");
                                serializer.text("1");
                                serializer.endTag(null, "showweek");

                                serializer.startTag(null, "showtime");
                                serializer.text("1");
                                serializer.endTag(null, "showtime");

                                serializer.startTag(null, "multline");
                                serializer.text("1");
                                serializer.endTag(null, "multline");

                                serializer.endTag(null, "file");
                            }
                        }
                        serializer.endTag(null, "zone");
                        Log.d("...............", "全部数据................:" + imageBean.toString());
                    }
                }

                serializer.endTag(null, "programe");
            }
            serializer.endTag(null, "group");
            serializer.endTag(null, "manifest");
            serializer.endDocument();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("onTouch==========", "onTouch");
        if (regionListView != null) {
            regionListView.mode = regionListView.MODE_RIGHT;
        }
        if (programListView != null) {
            programListView.mode = programListView.MODE_RIGHT;
        }

        return false;
    }
}
