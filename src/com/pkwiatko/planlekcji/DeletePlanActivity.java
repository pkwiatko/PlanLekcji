package com.pkwiatko.planlekcji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class DeletePlanActivity extends SherlockFragmentActivity {

	Button btnCancelDeletePlan;
	Button btnDeletePlan;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_plan);
        
        btnCancelDeletePlan = (Button) findViewById(R.id.btnDeletePlanCancel);
        btnDeletePlan = (Button) findViewById(R.id.btnDeletePlan);

        btnCancelDeletePlan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				setResult(RESULT_CANCELED, resultIntent);
				finish();
			}
		});
        
        btnDeletePlan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				setResult(RESULT_OK, resultIntent);
				finish();
			}
		});
    }
    
}
