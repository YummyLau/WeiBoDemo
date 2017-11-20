package yummylau.common.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import yummylau.common.R;


public class GSToastBottom extends Toast {

	TextView mContentTextView;

	public GSToastBottom(Context context, CharSequence content, int duration) {
		super(context);
		try {
			init(context, duration, content);
		} catch (Exception e) {
			Toast.makeText(context, content, duration).show();
		}
	}

	public GSToastBottom(Context context, int resId, int duration) {
		super(context);
		String content = "";
		if(resId > 0) {
			content = context.getString(resId);
		}
		try {
			init(context, duration, content);
		} catch (Exception e) {
			Toast.makeText(context, content, duration).show();
		}
	}

	private void init(Context context, int duration, CharSequence content) {
//		View view = LayoutInflater.from(context).inflate(R.layout.toast_normal, null);
//		mContentTextView = (TextView) view.findViewById(R.id.content_textview);
//		setView(view);
//		mContentTextView.setText(content);
//		setDuration(duration);
	}

}
