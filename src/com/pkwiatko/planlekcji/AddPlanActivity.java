package com.pkwiatko.planlekcji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;

public class AddPlanActivity extends SherlockFragmentActivity {
	
	EditText etStudentName;
	EditText etClassName;
	Button btnCancelAddPlan;
	Button btnAddPlan;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setSupportProgressBarVisibility(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_plan);
		

		etStudentName = (EditText) findViewById(R.id.etStudentName);
		etClassName = (EditText) findViewById(R.id.etClassName);

		btnCancelAddPlan = (Button) findViewById(R.id.btnAddPlanCancel);
		btnAddPlan = (Button) findViewById(R.id.btnAddPlan);

		btnCancelAddPlan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				setResult(RESULT_CANCELED, resultIntent);
				finish();
			}
		});

		btnAddPlan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String student = etStudentName.getText().toString() + " " 
						+ getResources().getString(R.string.label_class_short)
						+ " "
						+ etClassName.getText().toString();

				if (student != null && !"".equals(student)) {
					Intent resultIntent = new Intent();
					resultIntent.putExtra(DatabaseHandler.PLAN_STUDENT, student);										
					setResult(RESULT_OK, resultIntent);
					finish();
				} else	{
					Intent resultIntent = new Intent();
					setResult(RESULT_CANCELED, resultIntent);
					finish();
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}
}
