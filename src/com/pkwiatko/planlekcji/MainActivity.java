package com.pkwiatko.planlekcji;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsSpinner;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.Window;
import com.pkwiatko.domain.Lesson;
import com.pkwiatko.domain.Plan;
import com.pkwiatko.domain.Subject;
import com.pkwiatko.domain.Teacher;

public class MainActivity extends SherlockActivity implements
		ActionBar.TabListener, ActionBar.OnNavigationListener {

	private static final int ADD_PLAN_ACTIVITY_REQUEST_CODE = 1;
	private static final int DELETE_PLAN_ACTIVITY_REQUEST_CODE = 2;
	final Context context = this;
	public static int THEME = R.style.Theme_Sherlock;
	ActionBar actionBar;
	IcsSpinner spStudentsList;
	Spinner spTeacherList;
	Spinner spSubjectList;

	DatabaseHandler db = new DatabaseHandler(this);
	Cursor planCursor = null;
	private ListView lvPlan;

	private int currentDay = 1;
	private String currentStudent = "";
	Handler mHandler = new Handler();
	Runnable mProgressRunner = new Runnable() {
		@Override
		public void run() {
			mProgress += 2;

			// Normalize our progress along the progress bar's scale
			int progress = (Window.PROGRESS_END - Window.PROGRESS_START) / 100
					* mProgress;
			setSupportProgress(progress);

			if (mProgress < 100) {
				mHandler.postDelayed(mProgressRunner, 50);
			}
		}
	};

	private int mProgress = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setSupportProgressBarVisibility(true);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_main);

		actionBar = getSupportActionBar();
		actionBar.setTitle(getResources().getString(R.string.app_title_bar));

		initData();

		// Inflate the custom view
		View customNav = LayoutInflater.from(this).inflate(
				R.layout.custom_view, null);

		spStudentsList = (IcsSpinner) customNav
				.findViewById(R.id.spStudentsList);

		// Attach to the action bar
		getSupportActionBar().setCustomView(customNav);
		getSupportActionBar().setDisplayShowCustomEnabled(true);

		lvPlan = (ListView) findViewById(R.id.lvPlan);
		initStudentList();
		initOnClickListeners();

		// List<String> studentList = db.getAllStudents();

		//

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		String[] arrWeekdays = getResources().getStringArray(
				R.array.weekdays_short_array);
		for (String day : arrWeekdays) {
			ActionBar.Tab tab = getSupportActionBar().newTab();
			tab.setText(day);
			tab.setTabListener(this);
			getSupportActionBar().addTab(tab);
		}

	}

	private void initOnClickListeners() {
		lvPlan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v, int pos,
					long id) {
				onLongPlanListItemClick(v, pos, id);
				return true;
			}
		});

		spStudentsList
				.setOnItemSelectedListener(new IcsAdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(IcsAdapterView<?> parent,
							View view, int position, long id) {
						currentStudent = parent.getItemAtPosition(position)
								.toString();

						initPlanListView(currentStudent, currentDay);

					}

					@Override
					public void onNothingSelected(IcsAdapterView<?> parent) {
						// TODO Auto-generated method stub

					}

				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		SubMenu sub = menu.addSubMenu("");
		sub.add(1, 1, 1, getResources().getString(R.string.menu_add_plan));
		sub.add(1, 2, 2, getResources().getString(R.string.menu_delete_plan));
		sub.add(1, 3, 3, getResources().getString(R.string.menu_settings));
		sub.setIcon(THEME == R.style.Theme_Sherlock_Light ? R.drawable.abs__ic_menu_moreoverflow_holo_light
				: R.drawable.abs__ic_menu_moreoverflow_holo_dark);
		sub.getItem().setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home || item.getItemId() == 0) {
			return false;
		}

		// Add Plan Activity
		if (item.getItemId() == 1) {
			Intent intent = new Intent(getApplicationContext(),
					AddPlanActivity.class);
			startActivityForResult(intent, ADD_PLAN_ACTIVITY_REQUEST_CODE);
		}

		// Delete Plan Activity
		if (item.getItemId() == 2) {
			Intent intent = new Intent(getApplicationContext(),
					DeletePlanActivity.class);
			startActivityForResult(intent, DELETE_PLAN_ACTIVITY_REQUEST_CODE);
		}

		// Preferences
		if (item.getItemId() == 3) {
			startActivity(new Intent(getApplicationContext(), Preference.class));
		}

		return true;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		currentDay = tab.getPosition() + 1;
		initPlanListView(currentStudent, currentDay);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (ADD_PLAN_ACTIVITY_REQUEST_CODE == requestCode) {

			if (resultCode == RESULT_OK) {

				String student = data
						.getStringExtra(DatabaseHandler.PLAN_STUDENT);

				if (mProgress == 100) {
					mProgress = 0;
					mProgressRunner.run();
				}

				db.addPlanForNewStudent(student);
				initStudentList();

				currentStudent = student;

				spStudentsList
						.setSelection(((ArrayAdapter<String>) spStudentsList
								.getAdapter()).getPosition(currentStudent));

				initPlanListView(currentStudent, currentDay);
			}
		}

		if (DELETE_PLAN_ACTIVITY_REQUEST_CODE == requestCode) {
			if (resultCode == RESULT_OK) {
				db.deletePlan(currentStudent);
				initStudentList();
			}
		}

	}

	@SuppressWarnings("deprecation")
	private void initPlanListView(String student, int dayNo) {
		db = new DatabaseHandler(this);

		planCursor = db.getPlanByStudent(student, dayNo);
		startManagingCursor(planCursor);

		// the desired columns to be bound
		String[] columns = new String[] { DatabaseHandler.PLAN_LESSON_NO,
				DatabaseHandler.LESSON_KEY_START_TIME,
				DatabaseHandler.LESSON_KEY_END_TIME,
				DatabaseHandler.PLAN_SUBJECT, DatabaseHandler.PLAN_CLASSROOM,
				DatabaseHandler.PLAN_TEACHER };
		// the XML defined views which the data will be bound to
		int[] to = new int[] { R.id.list_plan_lesson_no,
				R.id.list_plan_lesson_start_time,
				R.id.list_plan_lesson_end_time, R.id.list_plan_subject,
				R.id.list_plan_classroom, R.id.list_plan_teacher };

		SimpleCursorAdapter planAdapter = new SimpleCursorAdapter(this,
				R.layout.list_plans_entry, planCursor, columns, to);

		// HomeAdapter planAdapter = new HomeAdapter(this,
		// R.layout.list_plans_entry, planCursor, columns, to);
		lvPlan.setAdapter(planAdapter);

	}

	private void initStudentList() {
		List<String> studentList = db.getAllStudents();

		ArrayAdapter<String> dataSpinerAdapter = new ArrayAdapter<String>(this,
				R.layout.sherlock_spinner_item, studentList);
		dataSpinerAdapter
				.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		spStudentsList.setAdapter(dataSpinerAdapter);

		if (studentList.size() > 0) {
			currentStudent = studentList.get(0);
			initPlanListView(currentStudent, currentDay);
		} else {
			currentStudent = "";
		}
	}
	
	@SuppressWarnings("unchecked")
	private void initTeacherList()	{
		List<String> teacherList = db.getAllTeacherList();

		ArrayAdapter<String> dataTeacherSpinerAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, teacherList);
		dataTeacherSpinerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spTeacherList.setAdapter(dataTeacherSpinerAdapter);

		spTeacherList.setSelection(((ArrayAdapter<String>) spTeacherList
				.getAdapter()).getPosition(planCursor.getString(6)));		
	}

	@SuppressWarnings("unchecked")
	private void initSubjectList()	{
		List<String> subjectList = db.getAllSubjectList();
		ArrayAdapter<String> dataSpinerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, subjectList);
		dataSpinerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSubjectList.setAdapter(dataSpinerAdapter);

		spSubjectList.setSelection(((ArrayAdapter<String>) spSubjectList
				.getAdapter()).getPosition(planCursor.getString(4)));

		
	}
	protected void onLongPlanListItemClick(View v, int pos, long id) {

		planCursor.moveToPosition(pos);

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_edit_plan_entry);
		dialog.setTitle(R.string.edit_lesson_activity);

		final TextView tvLessonNo = (TextView) dialog
				.findViewById(R.id.tvEditPlanLessonNo);
		spSubjectList = (Spinner) dialog
				.findViewById(R.id.spEditPlanSubjectList);
		spTeacherList = (Spinner) dialog
				.findViewById(R.id.spEditPlanTeacherList);
		final EditText etClassName = (EditText) dialog
				.findViewById(R.id.etEditPlanClassName);

		ImageButton ibAddTeacher = (ImageButton) dialog
				.findViewById(R.id.ibEditPlanAddTeacher);
		
		ImageButton ibAddSubject = (ImageButton) dialog
				.findViewById(R.id.ibEditPlanAddSubject);
		
		Button btnCancelAddPlan = (Button) dialog
				.findViewById(R.id.btnEditPlanCancel);
		Button btnAddPlan = (Button) dialog.findViewById(R.id.btnEditPlan);

		tvLessonNo.setText(Integer.valueOf(planCursor.getInt(3)).toString());

		initSubjectList();

		initTeacherList();
		
		etClassName.setText(planCursor.getString(5));

		btnCancelAddPlan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		btnAddPlan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Plan p = new Plan();
				p.setStudent(currentStudent);
				p.setDayNo(currentDay);
				p.setLessonNo(Integer.valueOf(tvLessonNo.getText().toString()));
				p.setSubjectName(spSubjectList.getSelectedItem().toString());
				p.setClassroom(etClassName.getText().toString());
				p.setTeacherName(spTeacherList.getSelectedItem().toString());

				db.updatePlan(p);

				initPlanListView(currentStudent, currentDay);

				dialog.dismiss();
			}
		});
		
		ibAddTeacher.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialogAddTeacher = new Dialog(context);
				dialogAddTeacher.setContentView(R.layout.dialog_add_teacher_entry);
				dialogAddTeacher.setTitle(R.string.label_add_teacher);
				
				final EditText etTeacherName = (EditText) dialogAddTeacher
						.findViewById(R.id.etAddTeacherName);
				
				Button btnCancelAddTeacher = (Button) dialogAddTeacher
						.findViewById(R.id.btnAddTeacherCancel);
				Button btnAddTeacher = (Button) dialogAddTeacher.findViewById(R.id.btnAddTeacher);
				
				
				
				btnCancelAddTeacher.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialogAddTeacher.dismiss();
					}
				});
				
				btnAddTeacher.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						if (etTeacherName.getText().toString() != null && !"".equals(etTeacherName.getText().toString()))	{
							Teacher t = new Teacher();
							t.setTeacherName(etTeacherName.getText().toString());
							db.addOrUpdateTeacher(t);
							initTeacherList();
						}						

						dialogAddTeacher.dismiss();
					}
				});
				
				
				dialogAddTeacher.show();
			}
		});
		
		ibAddSubject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialogAddSubject = new Dialog(context);
				dialogAddSubject.setContentView(R.layout.dialog_add_subject_entry);
				dialogAddSubject.setTitle(R.string.label_add_subject);
				
				final EditText etSubjectName = (EditText) dialogAddSubject
						.findViewById(R.id.etAddSubjectName);
				
				Button btnCancelAddSubject = (Button) dialogAddSubject
						.findViewById(R.id.btnAddSubjectCancel);
				Button btnAddSubject = (Button) dialogAddSubject.findViewById(R.id.btnAddSubject);
				
				
				
				btnCancelAddSubject.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialogAddSubject.dismiss();
					}
				});
				
				btnAddSubject.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						if (etSubjectName.getText().toString() != null && !"".equals(etSubjectName.getText().toString()))	{
							Subject s = new Subject();
							s.setSubjectName(etSubjectName.getText().toString());
							db.addOrUpdateSubject(s);
							initSubjectList();
						}						

						dialogAddSubject.dismiss();
					}
				});
				
				
				dialogAddSubject.show();
			}
		});


		dialog.show();

	}

	private void initData() {
		DatabaseHandler db = new DatabaseHandler(this);

		if (db.getLessonsCount() < 1) {

			// Inserting Lessons
			db.addLesson(new Lesson(1, "08:00", "08:45"));
			db.addLesson(new Lesson(2, "08:50", "09:35"));
			db.addLesson(new Lesson(3, "09:40", "10:25"));
			db.addLesson(new Lesson(4, "10:45", "11:30"));
			db.addLesson(new Lesson(5, "11:35", "12:20"));
			db.addLesson(new Lesson(6, "12:30", "13:15"));
			db.addLesson(new Lesson(7, "13:35", "14:20"));
			db.addLesson(new Lesson(8, "14:25", "15:10"));

		}

		if (db.getSubjectsCount() < 1) {
			// Inserting Subject
			db.addSubject(new Subject(""));
			db.addSubject(new Subject("Jezyk polski"));
			db.addSubject(new Subject("Jezyk angielski"));
			db.addSubject(new Subject("Jezyk niemiecki"));
			db.addSubject(new Subject("Matematyka"));
			db.addSubject(new Subject("Historia"));
			db.addSubject(new Subject("Informatyka"));
			db.addSubject(new Subject("W-F"));
			db.addSubject(new Subject("WDZ"));
			db.addSubject(new Subject("Plastyka"));
			db.addSubject(new Subject("Przyroda"));
			db.addSubject(new Subject("Religia"));
			db.addSubject(new Subject("Technika"));
			db.addSubject(new Subject("Muzyka"));
			db.addSubject(new Subject("Godz. wych."));
		}

		if (db.getTeachersCount() < 1) {
			db.addTeacher(new Teacher(""));			
		}

	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		Toast.makeText(this,
				"Got click: " + itemPosition + " itemId: " + itemId,
				Toast.LENGTH_LONG).show();
		return true;
	}

}
