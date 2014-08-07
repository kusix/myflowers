package kusix.myflowers.fragment;

import java.io.File;

import kusix.myflowers.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class EditFragment extends Fragment {

	LayoutInflater inflater;
	Activity main;
	ImageButton takePhotoButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		main = this.getActivity();
		View rootView = inflater
				.inflate(R.layout.fragment_edit, container, false);

		takePhotoButton = (ImageButton) rootView
				.findViewById(R.id.btn_take_photo);
		takePhotoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				String path = Environment.getExternalStorageDirectory()
						.toString() + "/flowers";
				File path1 = new File(path);
				if (!path1.exists()) {
					path1.mkdirs();
				}
				File file = new File(path1, System.currentTimeMillis() + ".jpg");
				Uri mOutPutFileUri = Uri.fromFile(file);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
				startActivityForResult(intent, 0);
			}
		});

		return rootView;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			try {
				if (data != null) {
					if (data.hasExtra("data")) {
						Bitmap thunbnail = data.getParcelableExtra("data");
						takePhotoButton.setImageBitmap(thunbnail);
//						takePhotoButton.setBackgroundDrawable(new BitmapDrawable(this.getResources(),thunbnail));
					}
				} else {
					// 处理mOutPutFileUri中的完整图像
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
