package com.storeassistant.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.storeassistant.R;

/***
 * ����
 * 
 * @author kuang email:651043704@qq.com
 * @time 2013.5.13
 * **/
public class KuangUtils {

	private static KuangUtils _rm = new KuangUtils();

	private KuangUtils() {
	}

	/***
	 * ����
	 * **/
	public static KuangUtils getInstance() {
		return _rm;
	}

	private AssetManager am;
	private static final String ENCODING = "UTF-8";
	protected String result;

	{
		System.out.println(">>>>>utils");
	}

	/**
	 * method:ͨ��id����ȡR�ļ��µ�id
	 * 
	 * @param context
	 *            ,name return int;
	 * **/
	public static int getId(Context context, String name) {
		Resources res = context.getResources();
		int i = res.getIdentifier(name, "id", context.getPackageName());
		return i;

	}

	/**
	 * method:ͨ��layout����ȡR�ļ��µ�layout
	 * 
	 * @param context
	 *            ,name return int;
	 * **/
	public static int getLayout(Context context, String name) {
		Resources res = context.getResources();
		int i = res.getIdentifier(name, "layout", context.getPackageName());
		return i;

	}

	/**
	 * method:ͨ��drawable����ȡR�ļ��µ�drawable
	 * 
	 * @param context
	 *            ,name return int;
	 * **/
	public static int getDrawable(Context context, String name) {
		Resources res = context.getResources();
		int i = res.getIdentifier(name, "drawable", context.getPackageName());
		return i;

	}

	/**
	 * method:ͨ��string����ȡR�ļ��µ�string
	 * 
	 * @param context
	 *            ,name return int;
	 * **/
	public static int getString(Context context, String name) {
		Resources res = context.getResources();
		int i = res.getIdentifier(name, "string", context.getPackageName());
		return i;

	}

	/**
	 * method:ͨ��string����ȡR�ļ��µ�string
	 * 
	 * @param context
	 *            ,name return int;
	 * **/
	public static int getAnim(Context context, String name) {
		Resources res = context.getResources();
		int i = res.getIdentifier(name, "anim", context.getPackageName());
		return i;

	}

	/**
	 * method:��ȡassetĿ¼�µ��ļ���Դ
	 * 
	 * @param _activity
	 *            ,filename return result;
	 * **/
	public String getStringFromAssets(Activity _activity, String filename) {
		String result = "";
		try {
			am = _activity.getResources().getAssets();
			InputStream is = am.open(filename);
			int length = is.available();
			byte[] buffer = new byte[length];
			is.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
			result.trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * method:�������
	 * 
	 * @param activity
	 *            return null;
	 * **/
	public static boolean enable;

	public static void initNet(final Activity activity) {

		ConnectivityManager connManager = (ConnectivityManager) activity
				.getSystemService(activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if (null == networkInfo) {
			enable = true;
			Toast.makeText(activity, "---", Toast.LENGTH_SHORT).show();
		} else {
			boolean available = networkInfo.isAvailable();
			if (available) {
			} else {
				Toast.makeText(activity, "---", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * method:�������
	 * 
	 * @param activity
	 * @param type
	 *            ����һ��int�͵�typeֵ,��ֻ�Ǽ������,û��ʱ������ʾ return null;
	 * **/
	public static void initNet(Activity activity, int type) {
		ConnectivityManager connManager = (ConnectivityManager) activity
				.getSystemService(activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if (null == networkInfo) {
			enable = true;
		} else {
			boolean available = networkInfo.isAvailable();
			if (available) {
			}
		}
	}

	/**
	 * method:get��ʽ����
	 * 
	 * @param url
	 *            return result;
	 * **/
	public String _netByGet(String _url) {
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		HttpClient client = new DefaultHttpClient(params);
		HttpGet request = new HttpGet(_url);
		try {
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * method:post��ʽ����
	 * 
	 * @param url
	 *            ,hashMap������� return result;
	 * **/
	public String _netByPost(String _url, HashMap<String, String> map) {
		String result = null;
		HttpPost request = null;
		List params = null;
		try {
			result = null;
			request = new HttpPost(_url);
			params = new ArrayList();
			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = map.get(key);
				NameValuePair pair = new BasicNameValuePair(key, value);
				params.add(pair);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 404) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * method:java.net������
	 * 
	 * @param url
	 *            return result;
	 * **/
	public String _netByJava(String _url) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			URL url = new URL(_url);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

	/**
	 * method:��ת
	 * 
	 * @param activity
	 *            ,class return result;
	 * **/
	public void pass(Activity _activity, Class cl) {
		Intent intent = new Intent(_activity, cl);
		_activity.startActivity(intent);
		_activity.finish();
		// ����
		_activity.overridePendingTransition(getAnim(_activity, "inalpha"),
				getAnim(_activity, "outalpha"));

	}

	/**
	 * ��ȡ��ǰ�İ汾��
	 * 
	 * @param activity
	 *            return version
	 * **/
	public String getVersionName(Activity _activity) throws Exception {
		PackageManager packageManager = _activity.getPackageManager();
		// getPackageName()�ǵ�ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
		PackageInfo packInfo = packageManager.getPackageInfo(
				_activity.getPackageName(), 0);
		return String.valueOf(packInfo.versionCode);
	}

	/***
	 * method:����ͼƬ
	 * 
	 * @param url
	 *            return bitmap
	 * **/
	public Bitmap getImageFromWeb(String url) {
		Bitmap bitmap = null;
		// httpGet���Ӷ���
		try {
			HttpGet httpRequest = new HttpGet(url);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// ȡ��HttpEntiy
				HttpEntity httpEntity = httpResponse.getEntity();
				// ���һ��������
				InputStream is = httpEntity.getContent();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;

	}

	public final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/txcache/";

	/***
	 * method:����ͼƬ��SD��
	 * 
	 * @param bitmap
	 *            ,filename throw IOException return null
	 * **/
	public void saveFile(Bitmap bm, String fileName) throws IOException {
		File dirFile = new File(ALBUM_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ALBUM_PATH + fileName);
		// ������
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}

	/***
	 * method:��ȡsd���ϵ�ͼƬ
	 * 
	 * @param filename
	 *            return bitmap
	 * **/
	public Bitmap getImageFromSD(String name) {
		String paths = ALBUM_PATH + name;
		File image = new File(paths);
		if (image.exists()) {
			Bitmap bit = BitmapFactory.decodeFile(paths);
			return bit;
		}
		return null;

	}

	/***
	 * method:�����ı�����
	 * 
	 * @param activity
	 *            ,fileName,content return boolean
	 * **/
	public boolean SaveContent(Activity _context, String fileName,
			String content) {
		try {
			FileOutputStream fos;
			fos = _context.openFileOutput(fileName, Context.MODE_PRIVATE);
			byte[] data = content.getBytes();
			fos.write(data);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/***
	 * method:��ȡ�ı�����
	 * 
	 * @param activity
	 *            ,fileName throws IOException return result
	 * @throws IOException
	 * **/
	public String read(Activity _context, String fileName) throws IOException

	{
		String content = null;
		FileInputStream inStream = _context.openFileInput(fileName);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = inStream.read(buffer)) != -1) {
			stream.write(buffer, 0, length);
		}
		stream.close();
		inStream.close();
		content = stream.toString();
		return content;
	}

	/***
	 * method:�ϴ�ͼƬ
	 * 
	 * @param activity
	 *            ,url,filename,id
	 * **/
	public String uploadByPost(Activity activity, String _url, String fileName,
			String id) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		String result = "";
		// �ļ���+��׺
		String file_Name = fileName.substring(fileName.lastIndexOf("/") + 1);
		try {
			URL url = new URL(_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			// �������ϴ��ļ���ص���Ϣ
			dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
					+ id + "->" + file_Name + "\"" + end);
			dos.writeBytes(end);
			FileInputStream fis = new FileInputStream(fileName);// �����ļ�
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close();
			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();
			/** ��ȡ�ӷ������˴�������Ϣ ***/
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * method:ͼƬ Բ��Ч��
	 * 
	 * @param bitmap
	 *            roundPx return bitmap
	 */
	public static Bitmap convertToRoundedCorner(Bitmap bmp, float roundPx) {
		Bitmap newBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
				Config.ARGB_8888);
		// �õ�����
		Canvas canvas = new Canvas(newBmp);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		// �ڶ����͵���������һ���򻭵�����Բ��һ�ǣ���������Բ��һ��
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bmp, rect, rect, paint);
		return newBmp;
	}

	/**
	 * method:����sd����ͼƬ/����ʱ
	 * 
	 * @param img
	 *            Ҫ����ͼƬ��ImageView
	 * @param loader
	 *            �첽������
	 * @param url
	 *            ͼƬurl return bitmap
	 */
	public void load_cache_bitmap(ImageView img, AsyImageLoader loader,
			String url, int width, int height, float corner) {
		if (enable == false) {
			CallbackImpl _img = new CallbackImpl(img);
			Bitmap drawable = loader.loadDrawable(url, _img, width, height,
					corner);
			loadDrawable(img, width, height, corner, drawable);
		} else {
			// û������SD��
			String fileName = url.substring(url.lastIndexOf("/") + 1,
					url.length());
			Bitmap drawable = getImageFromSD(fileName);
			loadDrawable(img, width, height, corner, drawable);
		}
	}

	private void loadDrawable(ImageView img, int width, int height,
			float corner, Bitmap drawable) {
		if (drawable != null) {

			drawable = KuangUtils.zoomImage(drawable, width, height);
			img.setImageBitmap(KuangUtils.convertToRoundedCorner(drawable,
					corner));
		} else {
			img.setImageResource(R.drawable.gray_default);
		}
	}

	/**
	 * method:ת��ʱ��
	 * 
	 * @param subtime
	 *            �����long��ʱ��
	 * @param type
	 *            1Ϊ�������� return ת�����
	 * **/
	public String timeFormat(long subtime, int type) {
		long newTime = subtime * 1000;
		SimpleDateFormat smp = null;
		Date date = new Date(newTime);
		if (type == 1)
			smp = new SimpleDateFormat("yyyy-MM-dd");
		else if (type == 0) {
			smp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if (type == 2) {
			smp = new SimpleDateFormat("MM-dd HH:mm");
		}
		return smp.format(date);
	}

	/**
	 * method:����ͼƬ
	 * 
	 * @param bgimage
	 *            ԭͼ
	 * @param newWidth
	 *            �¿��
	 * @param newHeight
	 *            �¸߶� return ���ź��ͼƬ
	 */
	public static Bitmap zoomImage(Bitmap bgimage, int newWidth, int newHeight) {
		Bitmap bitmap = null;
		try {
			Matrix matrix = new Matrix();
			int width = bgimage.getWidth();
			int height = bgimage.getHeight();
			if (width / height == 1) {
				float scaleWidth = ((float) newWidth) / width;
				float scaleHeight = ((float) newHeight) / height;
				matrix.postScale(scaleWidth, scaleHeight);
				bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height,
						matrix, true);
			} else if (width / height > 1) {
				float scaleHeight = ((float) newHeight) / height;
				matrix.postScale(scaleHeight, scaleHeight);
				bitmap = Bitmap.createBitmap(bgimage, 0, 0, height, height,
						matrix, true);
			} else {
				float scaleWidth = ((float) newWidth) / width;
				matrix.postScale(scaleWidth, scaleWidth);
				bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, width,
						matrix, true);
			}

		} catch (Exception e) {
			bitmap = bgimage;
		}
		return bitmap;
	}

	/**
	 * ScrollView��Ƕ��ListView,�������ø߶�<br>
	 * 
	 * @param listview
	 * @return null
	 * */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		if (listAdapter.getCount() > 0) {
			for (int i = 0; i < listAdapter.getCount(); i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			listView.setLayoutParams(params);
		}
	}

	/**
	 * WebView����<br>
	 * 
	 * @param _activity
	 *            ������
	 * @param wv
	 *            �����WebView�ؼ�
	 * @param content
	 *            WebView���ص�����
	 * @return null
	 */
	public void normal(final Activity _activity, final WebView wv,
			String content) {
		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);// ��javasrcipt����;
		wv.getSettings().setJavaScriptEnabled(true);
		// ֧������
		wv.getSettings().setBuiltInZoomControls(true);
		// ֧�����ݱ���
		wv.getSettings().setSaveFormData(true);
		wv.clearCache(true);
		wv.clearHistory();
		wv.loadDataWithBaseURL(null, "<html><body>" + content
				+ "</body></html>", "text/html", "utf-8", null);
		wv.setWebViewClient(new WebViewClient() {
			/*** ��ʼ����ҳ�� ***/
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				_activity.setProgressBarIndeterminateVisibility(true);// ���ñ������Ĺ�������ʼ
				super.onPageStarted(view, url, favicon);
			}

			/*** �������¼� ***/
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				wv.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}

			/*** ���󷵻� **/
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			public void onPageFinished(WebView view, String url) {
				_activity.setProgressBarIndeterminateVisibility(false);// ���ñ��������ֹͣ
				super.onPageFinished(view, url);
			}
		});

		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				_activity.getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
						newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}

			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}

		});
	}

	/**
	 * ����<br>
	 * 
	 * @param _activity
	 *            �����Ķ���
	 * @param nums
	 *            ��������
	 * @return null
	 **/
	public void call(final Activity _activity, final String... nums) {
		// �����ļ�ʱ��OnClickListener
		android.content.DialogInterface.OnClickListener listener1 = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent myIntentDial1 = new Intent(Intent.ACTION_DIAL,
						Uri.parse("tel:" + nums[which]));
				_activity.startActivity(myIntentDial1);
			}
		};
		new AlertDialog.Builder(_activity).setTitle("����")
				.setItems(nums, listener1)
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	/**
	 * ��̬���ܷ���
	 * 
	 * @param codeType
	 *            ������ܷ�ʽ
	 * @param content
	 *            ������ܵ�����
	 * @return ���ؼ��ܽ��
	 */
	public static String getEncode(String codeType, String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance(codeType);// ��ȡһ��ʵ������������ܷ�ʽ
			digest.reset();// ���һ��
			digest.update(content.getBytes());// д������,����ָ�����뷽ʽcontent.getBytes("utf-8");
			StringBuilder builder = new StringBuilder();
			for (byte b : digest.digest()) {
				builder.append(Integer.toHexString((b >> 4) & 0xf));// ת��16����
				builder.append(Integer.toHexString(b & 0xf));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
