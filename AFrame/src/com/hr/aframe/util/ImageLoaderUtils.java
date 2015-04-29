package com.hr.aframe.util;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * 同步显示图片 <li>String imageUri = "http://site.com/image.png"; // from Web</li>
 * <li>String imageUri = "file:///mnt/sdcard/image.png"; // from SD card</li>
 * <li>String imageUri = "content://media/external/audio/albumart/13"; //
 * fromcontent provider</li> <li>String imageUri = "assets://image.png"; //
 * fromassets</li> <li>String imageUri = "drawable://" + R.drawable.image; //
 * fromdrawables (only images, non-9patch)</li>
 * 
 * OOM优化<li>减少线程池中线程的个数，在ImageLoaderConfiguration中的（.threadPoolSize）中配置，推荐配置1-5</li>
 * <li>
 * 在DisplayImageOptions选项中配置bitmapConfig为Bitmap.Config.RGB_565，因为默认是ARGB_8888，
 * 使用RGB_565会比使用ARGB_8888少消耗2倍的内存</li> <li>
 * 在ImageLoaderConfiguration中配置图片的内存缓存为memoryCache(new WeakMemoryCache())
 * 或者不使用内存缓存</li> <li>
 * 在DisplayImageOptions选项中设置.imageScaleType(ImageScaleType.IN_SAMPLE_INT
 * )或者imageScaleType(ImageScaleType.EXACTLY)</li>
 * 
 * 
 * 我们在使用该框架的时候尽量的使用displayImage()方法去加载图片，loadImage()
 * 是将图片对象回调到ImageLoadingListener接口的onLoadingComplete()方法中，
 * 需要我们手动去设置到ImageView上面，displayImage()方法中，对ImageView对象使用的是Weakreferences，
 * 方便垃圾回收器回收ImageView对象，如果我们要加载固定大小的图片的时候，使用loadImage()方法需要传递一个ImageSize对象
 * ，而displayImage()方法会根据ImageView对象的测量值，或者android:layout_width and
 * android:layout_height设定的值，或者android:maxWidth and/or android:maxHeight设定的值来裁剪图片
 * */
public class ImageLoaderUtils {
	private static DisplayImageOptions mDefaultDisplayOptions;

	/**
	 * 获取图片配置参数
	 * **/
	public static DisplayImageOptions getDefaultDisplayOptions() {
		if (null == mDefaultDisplayOptions) {
			mDefaultDisplayOptions = new DisplayImageOptions.Builder()
					// .showImageOnFail(R.drawable.default_image)
					// .showImageOnLoading(R.drawable.default_image)
					.cacheInMemory(true).cacheOnDisk(true)
					.imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.displayer(new FadeInBitmapDisplayer(20)).build();
		}
		return mDefaultDisplayOptions;
	}

	public static void loadImage(String imageUri, int width, int height,
			DisplayImageOptions displayOptions, View progressView) {
		if (null == displayOptions) {
			displayOptions = getDefaultDisplayOptions();
		}
		// result Bitmap will be fit to this size
		ImageSize targetSize = new ImageSize(width, height);
		ImageLoader.getInstance().loadImage(imageUri, targetSize,
				displayOptions, new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {

					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {

					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {

					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {

					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {

					}
				});
	}

	public static void displayImage(String imageUri, ImageView imageView,
			DisplayImageOptions displayOptions, final View progressView) {
		imageView.setTag(imageUri);
		if (null == displayOptions) {
			displayOptions = getDefaultDisplayOptions();
		}
		ImageLoader.getInstance().displayImage(imageUri, imageView,
				displayOptions, new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						if (null != progressView) {
							progressView.setVisibility(View.VISIBLE);
							if (progressView instanceof ProgressBar) {
								ProgressBar progressBar = (ProgressBar) progressView;
								progressBar.setProgress(0);
							} else if (progressView instanceof TextView) {
								TextView progressBar = (TextView) progressView;
								progressBar.setText("0%");
							}
						}

					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						if (null != progressView) {
							progressView.setVisibility(View.GONE);
						}
						String message = null;
						switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
						}
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						if (null != progressView) {
							progressView.setVisibility(View.GONE);
						}
						if (!TextUtils.isEmpty(imageUri)) {
							if (imageUri.equals(view.getTag())) {
								if (view instanceof ImageView) {
									ImageView iv = (ImageView) view;
									iv.setImageBitmap(loadedImage);
								}
							}
						}
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						if (null != progressView) {
							progressView.setVisibility(View.GONE);
						}
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {
						if (null != progressView) {
							if (progressView instanceof ProgressBar) {
								ProgressBar progressBar = (ProgressBar) progressView;
								progressBar.setProgress(Math.round(100.0f
										* current / total));
							} else if (progressView instanceof TextView) {
								TextView progressBar = (TextView) progressView;
								progressBar.setText(Math.round(100.0f * current
										/ total)
										+ "%");
							}
						}

					}
				});
	}

	public static void cancel(ImageView imageView) {
		ImageLoader.getInstance().cancelDisplayTask(imageView);
	}
}
