package kusix.myflowers.fragment;

import kusix.myflowers.MainActivity;
import kusix.myflowers.R;
import kusix.myflowers.bluetooth.BluetoothActivity;
import kusix.myflowers.util.ImageUtil;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MeFragment extends Fragment{
	
	LayoutInflater inflater;
	MainActivity main;
	ListView listView;
	String[] titles={"绿萝","仙客来","马蹄莲","马蹄莲"};
	String[] texts={"阳台","阳台","客厅","卧室"};
	int[] resIds={R.drawable.lvluo,R.drawable.xiankelai,R.drawable.matilian,R.drawable.matilian};
	Resources resources;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		main = (MainActivity)this.getActivity();
		this.resources = this.getResources();
		View rootView = inflater.inflate(R.layout.fragment_me, container,
				false);
		listView=(ListView)rootView.findViewById(R.id.flower_list);
		listView.setAdapter(new ListViewAdapter(titles,texts,resIds));
		View topBar =(View)rootView.findViewById(R.id.top_bar);
		topBar.getBackground().setAlpha(250);
		
		//点击添加按钮,启动蓝牙花盆搜索流程
		Button addButton = (Button)rootView.findViewById(R.id.btn_add);
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent btIntent = new Intent(main, BluetoothActivity.class);
				startActivity(btIntent);
			}
		});
		
		
		
		return rootView;
	}
	
	

		public class ListViewAdapter extends BaseAdapter {
			View[] itemViews;

			public ListViewAdapter(String[] itemTitles, String[] itemTexts,
					int[] itemImageRes) {
				itemViews = new View[itemTitles.length];

				for (int i = 0; i < itemViews.length; i++) {
					itemViews[i] = makeItemView(itemTitles[i], itemTexts[i],
							itemImageRes[i]);
				}
			}

			public int getCount() {
				return itemViews.length;
			}

			public View getItem(int position) {
				return itemViews[position];
			}

			public long getItemId(int position) {
				return position;
			}

			private View makeItemView(String strTitle, String strText, int resId) {

				// 使用View的对象itemView与R.layout.item关联
				View itemView = inflater.inflate(R.layout.list_item_flower, null);

				// 通过findViewById()方法实例R.layout.item内各组件
				TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
				title.setText(strTitle);
				TextView text = (TextView) itemView.findViewById(R.id.itemText);
				text.setText(strText);
				ImageButton image = (ImageButton) itemView.findViewById(R.id.btn_item_edit);
				Bitmap bitmap = BitmapFactory.decodeResource(resources, resId);
				bitmap = ImageUtil.getRoundedCornerBitmap(bitmap, 40f);
				image.setImageBitmap(bitmap);
				
				ImageButton itemEditButton = (ImageButton)itemView.findViewById(R.id.btn_item_edit);
				itemEditButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						main.changeFragment(new EditFragment());
					}
				});
				return itemView;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null)
					return itemViews[position];
				return convertView;
			}
		}

	}

	
