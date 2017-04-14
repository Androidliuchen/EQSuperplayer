package com.eq.EQSuperPlayer.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.adapter.PhotoAdapter;
import com.eq.EQSuperPlayer.adapter.SpinnerAdapter;
import com.eq.EQSuperPlayer.adapter.SpinnerImageAdapter;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.ImageBean;
import com.eq.EQSuperPlayer.bean.ImagePath;
import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.custom.Constant;
import com.eq.EQSuperPlayer.dao.AreabeanDao;
import com.eq.EQSuperPlayer.dao.ImageDao;
import com.eq.EQSuperPlayer.dao.ImagePathDao;
import com.eq.EQSuperPlayer.dao.ProgramBeanDao;
import com.eq.EQSuperPlayer.imageutils.GlideLoader;
import com.eq.EQSuperPlayer.imageutils.SpacesItemDecoration;
import com.eq.EQSuperPlayer.utils.FileUtils;
import com.eq.EQSuperPlayer.utils.WindowSizeManager;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageActivity extends Activity {

    @BindView(R.id.iamge_btn)
    ImageView iamgeBtn;
    @BindView(R.id.image_send)
    Button imageSend;
    @BindView(R.id.IM)
    EditText IM;
    @BindView(R.id.IMborder)
    Spinner IMborder;
    @BindView(R.id.IMbordercolor)
    Spinner IMbordercolor;
    @BindView(R.id.IMx)
    EditText IMx;
    @BindView(R.id.IMy)
    EditText IMy;
    @BindView(R.id.IMWidth)
    EditText IMWidth;
    @BindView(R.id.IMHeigth)
    EditText IMHeigth;
    @BindView(R.id.IMTick)
    Spinner IMTick;
    @BindView(R.id.IMClear)
    Spinner IMClear;
    @BindView(R.id.IMSpeed)
    Spinner IMSpeed;
    @BindView(R.id.IMClearSpeed)
    Spinner IMClearSpeed;
    @BindView(R.id.IMStandtime)
    EditText IMStandtime;
    @BindView(R.id.iam_btn)
    ImageView iamBtn;
    @BindView(R.id.rvResultPhoto)
    RecyclerView rvResultPhoto;
    @BindView(R.id.image_title)
    TextView imageTitle;
    private PhotoAdapter photoAdapter;
    private List<ProgramBean> programBeens;
    private List<ImagePath> paths = new ArrayList<>();
    private ImageBean imageBean;
    private ProgramBean programBean;
    private WindowSizeManager windowSizeManager;
    private int windowWidth;
    private int windowHeight;
    private int text_id;
    private Areabean areabean;
    private ImagePath imagePath = new ImagePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        initData();
        //2.声名为瀑布流的布局方式: 3列,垂直方向
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvResultPhoto.setPadding(8, 8, 8, 8);
        //3.为recyclerView设置布局管理器
        //设置item之间的间隔

        if (imagePath.getPath() != null) {
            imagePath = new ImagePathDao(this).get(text_id);
            paths.add(imagePath);
        }
        rvResultPhoto.setLayoutManager(staggeredGridLayoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(8);
        rvResultPhoto.addItemDecoration(decoration);
        photoAdapter = new PhotoAdapter(this, paths);
        rvResultPhoto.setAdapter(photoAdapter);
        photoAdapter.notifyDataSetChanged();

    }

    private void initData() {
        areabean = new AreabeanDao(this).get(ProgramActivity.program_id);
        text_id = getIntent().getIntExtra(Constant.PROGRAM_ID, -1);
        imageBean = new ImageDao(this).get(text_id);
        programBean = new ProgramBeanDao(this).get(ProgramActivity.selet);
        imageTitle.setText(programBean.getName());
        imageBean.setProgramBean(programBean);
        windowWidth = areabean.getWindowWidth();
        windowHeight = areabean.getWindowHeight() ;
        IM.setText(imageBean.getIamgeName());
        // 图片窗宽高
        if (imageBean.getIamgeWidth() != 0) {
            IMWidth.setText(imageBean.getIamgeWidth() + "");
            IMHeigth.setText(imageBean.getIamgeHeidht() + "");
        } else {
            IMWidth.setText(areabean.getWindowWidth() + "");
            IMHeigth.setText(areabean.getWindowHeight() + "");
        }
        if (imageBean.getIamgeX() != 0 || imageBean.getIamgeY() != 0) {
            IMx.setText(imageBean.getIamgeX() + "");
            IMy.setText(imageBean.getIamgeY() + "");
        } else {
            IMx.setText(areabean.getArea_X() + "");
            IMy.setText(areabean.getArea_Y() + "");
        }

        TypedArray typedArray = this.getResources().obtainTypedArray(R.array.textcolor);
        int[] color_id = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            color_id[i] = typedArray.getResourceId(i, 0);
        }
        //边框
        IMborder.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.border)));
        IMborder.setSelection(imageBean.getIamgeBorder());
        //边框颜色
        IMbordercolor.setAdapter(new SpinnerImageAdapter(this, color_id));
        IMbordercolor.setSelection(imageBean.getIamgeBorderColor());
        //进场特技
        IMTick.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.fontertrick)));
        IMTick.setSelection(imageBean.getIamgeEntertrick());
        //进场速度
        IMSpeed.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.enterspeed)));
        IMSpeed.setSelection((int) imageBean.getIamgeEnterspeed());
        //出场特技
        IMClear.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.cleartrick)));
        IMClear.setSelection(imageBean.getIamgeCleartrick());
        //清场速度
        IMClearSpeed.setAdapter(new SpinnerAdapter(this, this.getResources().getStringArray(R.array.clearspeed)));
        IMClearSpeed.setSelection((int) imageBean.getIamgeClearspeed());
        //停留时间

        IMStandtime.setText(imageBean.getIamgeandtime() + "");

    }

    public void imageSave() {
        try {
            if (Integer.parseInt(IMWidth.getText().toString()) + Integer.parseInt(IMx.getText().toString()) <= windowWidth
                    && Integer.parseInt(IMHeigth.getText().toString()) + Integer.parseInt(IMy.getText().toString()) <= windowHeight) {
                imageBean.setIamgeWidth(Integer.parseInt(IMWidth.getText().toString()));
                imageBean.setIamgeHeidht(Integer.parseInt(IMHeigth.getText().toString()));
                imageBean.setIamgeX(Integer.parseInt(IMx.getText().toString()));
                imageBean.setIamgeY(Integer.parseInt(IMy.getText().toString()));
            } else {
                Toast.makeText(this, getResources().getText(R.string.program_area_reset), Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {
        }
        imageBean.setIamgeName(IM.getText().toString());
        imageBean.setIamgeBorder(IMborder.getSelectedItemPosition());
        imageBean.setIamgeBorderColor(IMbordercolor.getSelectedItemPosition());
        imageBean.setIamgeClearspeed(IMClearSpeed.getSelectedItemPosition());
        imageBean.setIamgeCleartrick(IMClear.getSelectedItemPosition());
        imageBean.setIamgeEnterspeed(IMSpeed.getSelectedItemPosition());
        imageBean.setIamgeEntertrick(IMTick.getSelectedItemPosition());
        imageBean.setIamgeandtime(Integer.parseInt(IMStandtime.getText().toString()));
        new ImageDao(this).update(imageBean);
    }

    public static final int REQUEST_CODE = 1000;

    @OnClick({R.id.iamge_btn, R.id.image_send, R.id.iam_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iamge_btn:
                Intent intent = new Intent(ImageActivity.this, ProgramActivity.class);
                startActivity(intent);
                ImageActivity.this.finish();
                break;
            case R.id.image_send://提交数据，同时将数据写入数据库，以供节目显示用。
                imageSave();
                Intent intent1 = new Intent(ImageActivity.this, ProgramActivity.class);
                startActivity(intent1);
                ImageActivity.this.finish();
                break;
            case R.id.iam_btn://图片添加按钮，点击跳转到图片添加界面。
                String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "EQImage";
                File file = new File(fileTextPath);
                if (!file.exists()) {
                    file.mkdir();
                } else {
                    FileUtils.deleteDir(fileTextPath);
                }
                ArrayList<String> imagePath = new ArrayList<>();
                for (ImagePath imagePath1 : paths) {
                    imagePath.add(imagePath1.getPath());
                }
                ImageConfig imageConfig = new ImageConfig.Builder(
                        // GlideLoader 可用自己用的缓存库
                        new GlideLoader())
                        // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
                        .steepToolBarColor(getResources().getColor(R.color.blue))
                        // 标题的背景颜色 （默认黑色）
                        .titleBgColor(getResources().getColor(R.color.blue))
                        // 提交按钮字体的颜色  （默认白色）
                        .titleSubmitTextColor(getResources().getColor(R.color.white))
                        // 标题颜色 （默认白色）
                        .titleTextColor(getResources().getColor(R.color.white))
                        // 开启多选   （默认为多选）  (单选 为 singleSelect)
//                        .singleSelect()
                        .crop()
                        // 多选时的最大数量   （默认 9 张）
                        .mutiSelectMaxSize(9)
                        // 已选择的图片路径
                        .pathList(imagePath)
                        // 拍照后存放的图片路径（默认 /temp/picture）
                        .filePath("/ImageSelector/Pictures")
                        // 开启拍照功能 （默认开启）
                        .showCamera()
                        .requestCode(REQUEST_CODE)
                        .build();
                ImageSelector.open(ImageActivity.this, imageConfig);   // 开启图片选择器
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Log.v("............", "pathList都有什么..............：" + pathList);
            ArrayList<String> iamgID = new ArrayList<String>();
            for (String path : pathList) {
                Log.i("ImagePathList", path);
                imagePath.setPath(path);
                imagePath.setProgramBean(programBean);
                imagePath.setImageBean(imageBean);
//                new ImagePathDao(ImageActivity.this).add(imagePath);
                paths.add(imagePath);
                Log.d("............", "paths............" + paths);
                String imageName = path.substring(path.lastIndexOf("/") + 1, path.length());
                Log.d("............", "imageName............" + imageName);
                File file = new File(path);
                if (file.exists()) {
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    FileUtils.zoomImg(bm, Integer.parseInt(IMWidth.getText().toString()), Integer.parseInt(IMHeigth.getText().toString()));
                    Log.d("...............", "BM............" + bm);
                }
                iamgID.add(imageName);
                Log.d("............", "iamgID............" + iamgID);
                for (int i = 0; i < iamgID.size(); i++) {
                    imageBean.setIamgeId(iamgID);
                    new ImageDao(this).update(imageBean);
                }
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.iam_btn)
    public void onClick() {
    }
}
