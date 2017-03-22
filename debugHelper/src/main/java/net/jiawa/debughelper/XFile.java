package net.jiawa.debughelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

/***
 * 文件操作
 * @author zhaoxin5
 *
 */
public class XFile {
	private final static String TAG = "XFile";
	private final static boolean DEBUG_SAVE_BITMAP = true;
	
	public static void saveBitmap2MyCacheFolder(Context context, final Bitmap bitmap) {
		saveBitmap2MyCacheFolder(context, bitmap, null);
	}
	
	public static void saveBitmap2MyCacheFolder(Context context, final Bitmap bitmap, final String name) {
		if(null == bitmap || null == context) {
			return;
		}
		final String cachePath = context.getCacheDir().getAbsolutePath();
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				FileOutputStream fos = null;
				try {
					String targetBmpName = (name == null) ? XUtil.getCurrentTimeStamps() : XUtil.getCurrentTimeStamps() + "_" + name;
					XLog.log(DEBUG_SAVE_BITMAP, TAG , "targetBmpName : " + targetBmpName);
					File f = new File(cachePath, targetBmpName + ".bmp");
					if (f.exists()) {
						f.delete();
					}
					fos = new FileOutputStream(f);
					if (null != fos) {
						bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
						fos.flush();
						fos.close();
						XLog.log(DEBUG_SAVE_BITMAP, TAG , targetBmpName + " saved success!");
					}
				} catch (FileNotFoundException e) {
					XLog.log(DEBUG_SAVE_BITMAP, TAG , "FileNotFoundException");
					e.printStackTrace();
				} catch (IOException e) {
					XLog.log(DEBUG_SAVE_BITMAP, TAG , "IOException");
					e.printStackTrace();
				}
				return null;
			}
		}.execute();
	}
	
	//写文件  
	public static void writeSDFile(String fileName, String write_str, Context ctx) throws IOException {    
        File file = new File(fileName);    
        FileOutputStream fos = new FileOutputStream(file);    
        byte [] bytes = write_str.getBytes();   
        fos.write(bytes);   
        fos.close();   
        
        scanFile(ctx, file);
	} 
	
	public static void scanFile(Context ctx, File f) {
		// 通知文件管理系统当前文件已更新,这样备份结束即可在电脑上查看到备份完成的文件
		Intent scanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		scanIntent.setData(Uri.fromFile(f));
		ctx.sendBroadcast(scanIntent);
	}
}
