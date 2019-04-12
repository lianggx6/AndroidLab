package com.example.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelClass.demands_publish_all;
import com.example.modelClass.goods_publish_all;
import com.example.modelClass.user;
import com.example.netInterface.DataInterface;
import com.example.util.UploadUtil;
import com.example.util.UploadUtil.OnUploadProcessListener;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PublishActivity extends AppCompatActivity implements OnClickListener,OnUploadProcessListener{
	private static final String TAG = "uploadImage";

	/**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 1;  
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 2;  //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 5;

	private static String requestURL = "http://ss.sen7u.win:8080/fileUpload/file_upload";
	private Button selectButton,uploadButton;
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private ImageView imageView4;
	private ImageView imageView5;
	private ImageView imageView6;
	private ImageView imageView7;
	private ImageView imageView8;
	private ImageView imageView9;
	private ImageView imageView10;
	private ImageView imageView11;
	private ImageView imageView12;
	private TextView uploadImageResult;
	private String picPath = null;
	private ProgressDialog progressDialog;
	int cnt;
	ImageButton back;
	EditText title;
	EditText miaoshu;
	EditText price;
	EditText type;
	TextView fabu;
	boolean publishtype;
	MyApplication myApplication;
	String BASE_URL;
	Retrofit retrofit;
	DataInterface dataInterface;
	String imageurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initView();
        cnt = 0;
		imageurl = "";
        publishtype = getIntent().getExtras().getBoolean("publishtype");
        myApplication = (MyApplication) PublishActivity.this.getApplication();
		BASE_URL = "http://ss.sen7u.win:8080/Test2/api/";
		back.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
        fabu.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				String titlestr = title.getText().toString();
				String miaoshustr = miaoshu.getText().toString();
				String pricestr = price.getText().toString();
				String typestr = type.getText().toString();
				if(titlestr.isEmpty() || miaoshustr.isEmpty() || pricestr.isEmpty() || typestr.isEmpty())
					Toast.makeText(PublishActivity.this,"请填入全部信息",Toast.LENGTH_SHORT).show();
				else
				{
					retrofit = new Retrofit.Builder()
							.baseUrl(BASE_URL)
							.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
							.addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
							.build();

					if(publishtype)
					{
						dataInterface = retrofit.create(DataInterface.class);

						dataInterface.post_goods(String.valueOf(myApplication.getUser_id()),titlestr,typestr,miaoshustr,pricestr,imageurl)
								.subscribeOn(Schedulers.io())//新建线程进行网络访问
								.observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
								.subscribe(new Subscriber<goods_publish_all>() {
									@Override
									public void onCompleted() {
										System.out.print("Complete the transfer");
									}

									@Override
									public void onError(Throwable e) {
										Log.d("d", "onError:" + e.getMessage());
									}

									@Override
									public void onNext(goods_publish_all l) {
										Log.d("d", "fabu-onNext: ");
										Toast.makeText(PublishActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
									}
								});
					}
					else
					{
						dataInterface = retrofit.create(DataInterface.class);

						dataInterface.post_demands(String.valueOf(myApplication.getUser_id()),titlestr,typestr,miaoshustr,pricestr,imageurl)
								.subscribeOn(Schedulers.io())//新建线程进行网络访问
								.observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
								.subscribe(new Subscriber<demands_publish_all>() {
									@Override
									public void onCompleted() {
										System.out.print("Complete the transfer");
									}

									@Override
									public void onError(Throwable e) {
										Log.d("d", "onError:" + e.getMessage());
									}

									@Override
									public void onNext(demands_publish_all l) {
										Log.d("d", "fabu-onNext: ");
										Toast.makeText(PublishActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
									}
								});
					}
					finish();
				}

			}
		});
    }
    private void initView() {
        selectButton = (Button) this.findViewById(R.id.upload);
        selectButton.setOnClickListener(this);
        imageView1 = (ImageView) this.findViewById(R.id.upload1);
		imageView2 = (ImageView) this.findViewById(R.id.upload2);
		imageView3 = (ImageView) this.findViewById(R.id.upload3);
		imageView4 = (ImageView) this.findViewById(R.id.upload4);
		imageView5 = (ImageView) this.findViewById(R.id.upload5);
		imageView6 = (ImageView) this.findViewById(R.id.upload6);
		imageView7 = (ImageView) this.findViewById(R.id.upload7);
		imageView8 = (ImageView) this.findViewById(R.id.upload8);
		imageView9 = (ImageView) this.findViewById(R.id.upload9);
		imageView10 = (ImageView) this.findViewById(R.id.upload10);
		imageView11 = (ImageView) this.findViewById(R.id.upload11);
		imageView12 = (ImageView) this.findViewById(R.id.upload12);
        progressDialog = new ProgressDialog(this);
        back = (ImageButton) findViewById(R.id.publish_back);
        title = (EditText) findViewById(R.id.publish_biaoti);
        miaoshu = (EditText) findViewById(R.id.publish_miaoshu);
        price = (EditText) findViewById(R.id.publish_price);
        type = (EditText) findViewById(R.id.publish_type);
        fabu = (TextView) findViewById(R.id.publish_queren);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.upload:
			if(cnt<12){
				Intent intent = new Intent(this,SelectPicActivity.class);
				startActivityForResult(intent, TO_SELECT_PHOTO);
			}
			else{
				Toast.makeText(PublishActivity.this,"最多12张照片",Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Log.i(TAG, "最终选择的图片="+picPath);
			handler.sendEmptyMessage(TO_UPLOAD_FILE);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	/**
	 * 上传服务器响应回调
	 */
	@Override
	public void onUploadDone(int responseCode, String message) {
		progressDialog.dismiss();
		Message msg = Message.obtain();
		msg.what = UPLOAD_FILE_DONE;
		msg.arg1 = responseCode;
		msg.obj = message;
		handler.sendMessage(msg);
	}
	
	private void toUploadFile()
	{;
		String fileKey = "img";
		UploadUtil uploadUtil = UploadUtil.getInstance();
		uploadUtil.setOnUploadProcessListener(this);  //设置监听器监听上传状态
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "11111");
		uploadUtil.uploadFile( picPath,fileKey, requestURL,params);
	}	

	@Override
	public void onUploadProcess(int uploadSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_IN_PROCESS;
		msg.arg1 = uploadSize;
		handler.sendMessage(msg);
	}

	@Override
	public void initUpload(int fileSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_INIT_PROCESS;
		msg.arg1 = fileSize;
		handler.sendMessage(msg );
	}
		
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TO_UPLOAD_FILE:
				toUploadFile();
				break;
			
			case UPLOAD_INIT_PROCESS:				
				break;
				
			case UPLOAD_IN_PROCESS:				
				break;
				
			case UPLOAD_FILE_DONE:
				String result = "响应码："+msg.arg1+"\n响应信息："+msg.obj+"\n耗时："+UploadUtil.getRequestTime()+"秒";
				Toast.makeText(PublishActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
				Bitmap bm = BitmapFactory.decodeFile(picPath);
				switch(cnt){
					case 0:
						imageView1.setImageBitmap(bm);
						cnt++;
						break;
					case 1:
						imageView2.setImageBitmap(bm);
						cnt++;
						break;
					case 2:
						imageView3.setImageBitmap(bm);
						cnt++;
						break;
					case 3:
						imageView4.setImageBitmap(bm);
						cnt++;
						break;
					case 4:
						imageView5.setImageBitmap(bm);
						cnt++;
						break;
					case 5:
						imageView6.setImageBitmap(bm);
						cnt++;
						break;
					case 6:
						imageView7.setImageBitmap(bm);
						cnt++;
						break;
					case 7:
						imageView8.setImageBitmap(bm);
						cnt++;
						break;
					case 8:
						imageView9.setImageBitmap(bm);
						cnt++;
						break;
					case 9:
						imageView10.setImageBitmap(bm);
						cnt++;
						break;
					case 10:
						imageView11.setImageBitmap(bm);
						cnt++;
						break;
					case 11:
						imageView12.setImageBitmap(bm);
						cnt++;
						break;

				}
				String[] sArray=picPath.split("/");
				imageurl+="http://ss.sen7u.win:8080/fileUpload/upload/" + sArray[sArray.length-1]+";";
				break;
				
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
}
