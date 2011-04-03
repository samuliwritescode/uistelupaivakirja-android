package fi.capeismi.fish.uistelupaivakirja.controller;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import  android.view.View.OnClickListener;

public class AddSpinnerItem extends Dialog implements OnClickListener {

	private String m_value;
	
	public AddSpinnerItem(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addspinneritem);
		Button okBtn = (Button)findViewById(R.id.OkButton);
		okBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		m_value = ((EditText)findViewById(R.id.EditText)).getText().toString();
		dismiss();		
	}
	
	public String toString()
	{
		if(m_value == null)
			return "";
		return m_value;
	}
}
