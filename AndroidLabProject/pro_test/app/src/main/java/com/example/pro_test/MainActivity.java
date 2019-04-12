package com.example.pro_test;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private  CharSequence []its = {"拍照","从相册选择"};
    public static final int TAKE_PHOTO = 1;//拍照
    public static final int CROP_PHOTO = 2;//裁剪
    public static final int SELECT_PIC = 0;//从相册选择
    private Uri imageUri; //图片路径
    private String filename; //图片名称
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        ImageView imageView = findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("更换头像")
                        .setItems(its, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                //最好自己封装一下，方便复用
                                switch (which)
                                {
                                    case 0://拍照
                                        //图片名称 时间命名
                                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                                        Date date = new Date(System.currentTimeMillis());
                                        filename = format.format(date);
                                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                                        File outputImage = new File(path,filename+".jpg");
                                        try {
                                            if(outputImage.exists())
                                            {
                                                outputImage.delete();
                                            }
                                            outputImage.createNewFile();
                                        } catch(IOException e) {
                                            e.printStackTrace();
                                        }
                                        //将File对象转换为Uri并启动照相程序
                                        imageUri = Uri.fromFile(outputImage);
                                        Intent tTntent = new Intent("android.media.action.IMAGE_CAPTURE"); //照相
                                        tTntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
                                        startActivityForResult(tTntent,TAKE_PHOTO); //启动照相
                                        break;
                                    case 1://从相册选择
                                        intent.setAction(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
                                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                                        intent.setType("image/*");
                                        intent.putExtra("return-data", true);
                                        intent.putExtra("crop", "true");
                                        //设置宽高比例

                                        intent.putExtra("aspectX", 1);
                                        intent.putExtra("aspectY", 1);
                                        //设置裁剪图片宽高、
                                        intent.putExtra("outputX", 450);
                                        intent.putExtra("outputY", 450);
                                        startActivityForResult(intent,SELECT_PIC);
                                        break;
                                }
                            }
                        })
                        .create()
                        .show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode)
        {
            case SELECT_PIC://相册
                photo = data.getParcelableExtra("data");
                if(photo!=null)
                {
                    headImage = ImageDeal.toRoundBitmap(photo);//裁剪成圆形
                    //上传到服务器...
                    UserController.updateHeadImage(userId, headImage, handler);
                    photo.recycle();
                }
                break;
            case SELECT_CAMERA://相机
                try {
                    Intent intent = new Intent("com.android.camera.action.CROP"); //剪裁
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    //设置宽高比例
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    //设置裁剪图片宽高
                    intent.putExtra("outputX", 450);
                    intent.putExtra("outputY", 450);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    Toast.makeText(ModiUserInfoActivity.this, "剪裁图片", Toast.LENGTH_SHORT).show();
                    //广播刷新相册
                    Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intentBc.setData(imageUri);
                    this.sendBroadcast(intentBc);
                    startActivityForResult(intent, CROP_PHOTO); //设置裁剪参数显示图片至ImageView
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CROP_PHOTO:
                try {
                    //图片解析成Bitmap对象
                    Bitmap bitmap = BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(photoFile));

                    headImage = ImageDeal.toRoundBitmap(bitmap);
                    UserController.updateHeadImage(userId, headImage, handler);
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
}
