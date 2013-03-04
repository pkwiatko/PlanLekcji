package com.pkwiatko.planlekcji;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pkwiatko.domain.Lesson;
import com.pkwiatko.domain.Plan;
import com.pkwiatko.domain.Subject;
import com.pkwiatko.domain.Teacher;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String DEBUG_TAG = "LessonPlan";
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 10;

	// Database Name
	private static final String DATABASE_NAME = "lessonPlan";

	// Contacts table name
	private static final String TABLE_LESSON = "lesson";
	private static final String TABLE_SUBJECT = "subject";
	private static final String TABLE_TEACHER = "teacher";
	private static final String TABLE_PLAN = "plan";

	// Contacts Table Columns names
	public static final String LESSON_KEY_ID = "_id";
	public static final String LESSON_KEY_LESSON_NO = "lesson_no";
	public static final String LESSON_KEY_START_TIME = "start_time";
	public static final String LESSON_KEY_END_TIME = "end_time";

	public static final String SUBJECT_KEY_ID = "_id";
	public static final String SUBJECT_KEY_SUBJECT = "subject";

	public static final String PLAN_KEY_ID = "_id";
	public static final String PLAN_STUDENT = "student";
	public static final String PLAN_DAY_NO = "day_no";
	public static final String PLAN_LESSON_NO = "lesson_no";
	public static final String PLAN_SUBJECT = "subject";
	public static final String PLAN_CLASSROOM = "classroom";
	public static final String PLAN_TEACHER = "teacher";

	public static final String TEACHER_KEY_ID = "_id";
	public static final String TEACHER_KEY_TEACHER = "teacher";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Lesson Table
		String CREATE_LESSON_TABLE = "CREATE TABLE " + TABLE_LESSON + "("
				+ LESSON_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ LESSON_KEY_LESSON_NO + " INTEGER, " + LESSON_KEY_START_TIME
				+ " TEXT, " + LESSON_KEY_END_TIME + " TEXT " + ")";
		db.execSQL(CREATE_LESSON_TABLE);
		Log.d(DEBUG_TAG, "Table lesson creating...");
		Log.d(DEBUG_TAG, CREATE_LESSON_TABLE);
		String CREATE_LESSON_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS idx_lesson_no ON "
				+ TABLE_LESSON + " (" + LESSON_KEY_LESSON_NO + ")";
		db.execSQL(CREATE_LESSON_INDEX);
		Log.d(DEBUG_TAG, CREATE_LESSON_INDEX);

		// Subject Table
		String CREATE_SUBJECT_TABLE = "CREATE TABLE " + TABLE_SUBJECT + "("
				+ SUBJECT_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ SUBJECT_KEY_SUBJECT + " TEXT " + ")";
		db.execSQL(CREATE_SUBJECT_TABLE);
		Log.d(DEBUG_TAG, "Table subject creating...");
		Log.d(DEBUG_TAG, CREATE_SUBJECT_TABLE);
		String CREATE_SUBJECT_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS idx_subject ON "
				+ TABLE_SUBJECT + " (" + SUBJECT_KEY_SUBJECT + ")";
		db.execSQL(CREATE_SUBJECT_INDEX);
		Log.d(DEBUG_TAG, CREATE_SUBJECT_INDEX);

		// Teacher Table
		String CREATE_TEACHER_TABLE = "CREATE TABLE " + TABLE_TEACHER + "("
				+ TEACHER_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ TEACHER_KEY_TEACHER + " TEXT " + ")";
		db.execSQL(CREATE_TEACHER_TABLE);
		Log.d(DEBUG_TAG, "Table teacher creating...");
		Log.d(DEBUG_TAG, CREATE_TEACHER_TABLE);
		String CREATE_TEACHER_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS idx_teacher ON "
				+ TABLE_TEACHER + " (" + TEACHER_KEY_TEACHER + ")";
		db.execSQL(CREATE_TEACHER_INDEX);
		Log.d(DEBUG_TAG, CREATE_TEACHER_INDEX);

		// Plan Table
		String CREATE_PLAN_TABLE = "CREATE TABLE " + TABLE_PLAN + "("
				+ PLAN_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ PLAN_STUDENT + " TEXT, " + PLAN_DAY_NO + " INTEGER, "
				+ PLAN_LESSON_NO + " INTEGER, " + PLAN_SUBJECT + " TEXT, "
				+ PLAN_CLASSROOM + " TEXT, " + PLAN_TEACHER + " TEXT " + ")";

		db.execSQL(CREATE_PLAN_TABLE);
		Log.d(DEBUG_TAG, "Table plan creating...");
		Log.d(DEBUG_TAG, CREATE_PLAN_TABLE);
		String CREATE_PLAN_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS idx_plan ON "
				+ TABLE_PLAN
				+ " ("
				+ PLAN_STUDENT
				+ ", "
				+ PLAN_DAY_NO
				+ ", "
				+ PLAN_LESSON_NO + ")";
		db.execSQL(CREATE_PLAN_INDEX);
		Log.d(DEBUG_TAG, CREATE_PLAN_INDEX);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.d(DEBUG_TAG, "Upgrade database from ver " + oldVersion + " to ver "
				+ newVersion);
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LESSON);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAN);

		// Create tables again
		onCreate(db);

	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new lesson
	void addLesson(Lesson lesson) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(LESSON_KEY_LESSON_NO, lesson.getLessonNo());
		values.put(LESSON_KEY_START_TIME, lesson.getStartTime());
		values.put(LESSON_KEY_END_TIME, lesson.getEndTime());

		// Inserting Row
		db.insert(TABLE_LESSON, null, values);
		db.close(); // Closing database connection
	}

	// Adding new subject
	void addSubject(Subject subject) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SUBJECT_KEY_SUBJECT, subject.getSubjectName());
		// values.put(SUBJECT_KEY_TEACHER, subject.getTeacherName());

		// Inserting Row
		db.insert(TABLE_SUBJECT, null, values);
		db.close(); // Closing database connection
	}

	// Adding new teacher
	void addTeacher(Teacher teacher) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TEACHER_KEY_TEACHER, teacher.getTeacherName());
		// values.put(SUBJECT_KEY_TEACHER, subject.getTeacherName());

		// Inserting Row
		db.insert(TABLE_TEACHER, null, values);
		db.close(); // Closing database connection
	}

	// Adding new plan
	void addPlan(Plan plan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PLAN_STUDENT, plan.getStudent());
		values.put(PLAN_DAY_NO, plan.getDayNo());
		values.put(PLAN_LESSON_NO, plan.getLessonNo());
		values.put(PLAN_CLASSROOM, plan.getClassroom());
		values.put(PLAN_SUBJECT, plan.getSubjectName());
		values.put(PLAN_TEACHER, plan.getTeacherName());

		// Inserting Row
		db.insert(TABLE_PLAN, null, values);
		db.close(); // Closing database connection

	}

	// Getting single lesson
	Lesson getLesson(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_LESSON, new String[] { LESSON_KEY_ID,
				LESSON_KEY_LESSON_NO, LESSON_KEY_START_TIME,
				LESSON_KEY_END_TIME }, LESSON_KEY_LESSON_NO + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
		} else {
			return null;
		}

		Lesson lesson = new Lesson(Integer.parseInt(cursor.getString(0)),
				Integer.parseInt(cursor.getString(1)), cursor.getString(2),
				cursor.getString(3));
		// return lesson
		return lesson;
	}

	// Getting single subject
	Subject getSubject(String subjectName) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_SUBJECT, new String[] { SUBJECT_KEY_ID,
				SUBJECT_KEY_SUBJECT }, SUBJECT_KEY_SUBJECT + "=?",
				new String[] { subjectName }, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
		} else {
			return null;
		}

		Subject subject = new Subject(cursor.getInt(0), cursor.getString(1));
		// return lesson
		return subject;
	}

	// Getting single teacher
	Teacher getTeacher(String teacherName) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_TEACHER, new String[] { TEACHER_KEY_ID,
				TEACHER_KEY_TEACHER }, TEACHER_KEY_TEACHER + "=?",
				new String[] { teacherName }, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
		} else {
			return null;
		}

		Teacher teacher = new Teacher(cursor.getInt(0), cursor.getString(1));
		// return lesson
		return teacher;
	}

	// Getting All Lessons
	public Cursor getAllLessons() {
		// List<Lesson> lessonList = new ArrayList<Lesson>();
		// Select All Query
		// String selectQuery = "SELECT  * FROM " + TABLE_LESSON;

		SQLiteDatabase db = this.getWritableDatabase();
		// Cursor cursor = db.rawQuery(selectQuery, null);
		Cursor cursor = db.query(TABLE_LESSON, new String[] { LESSON_KEY_ID,
				LESSON_KEY_LESSON_NO, LESSON_KEY_START_TIME,
				LESSON_KEY_END_TIME }, null, null, null, null,
				LESSON_KEY_LESSON_NO);

		// looping through all rows and adding to list
		// if (cursor.moveToFirst()) {
		// do {
		// Lesson lesson = new Lesson();
		// lesson.setId(Integer.parseInt(cursor.getString(0)));
		// lesson.setLessonNo(Integer.parseInt(cursor.getString(1)));
		// lesson.setStartTime(cursor.getString(2));
		// lesson.setEndTime(cursor.getString(3));
		//
		// // Adding contact to list
		// lessonList.add(lesson);
		// } while (cursor.moveToNext());
		// }

		// return contact list
		return cursor;
	}

	// Getting All Subjects
	public Cursor getAllSubjects() {

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(TABLE_SUBJECT, new String[] { SUBJECT_KEY_ID,
				SUBJECT_KEY_SUBJECT }, null, null, null, null,
				SUBJECT_KEY_SUBJECT);

		// return subject list
		return cursor;
	}

	// Getting All Subjects
	public Cursor getAllTeachers() {

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(TABLE_TEACHER, new String[] { TEACHER_KEY_ID,
				TEACHER_KEY_TEACHER }, null, null, null, null,
				TEACHER_KEY_TEACHER);

		// return subject list
		return cursor;
	}

	// Updating single lesson
	public int updateLesson(Lesson lesson) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		// values.put(LESSON_KEY_LESSON_NO, lesson.getLessonNo());
		values.put(LESSON_KEY_START_TIME, lesson.getStartTime());
		values.put(LESSON_KEY_END_TIME, lesson.getEndTime());

		// updating row
		int res = db.update(TABLE_LESSON, values,
				LESSON_KEY_LESSON_NO + " = ?",
				new String[] { String.valueOf(lesson.getLessonNo()) });
		db.close();
		return res;
	}

	// Updating single subject
	public int updateSubject(Subject subject) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SUBJECT_KEY_SUBJECT, subject.getSubjectName());

		// updating row
		int res = db.update(TABLE_SUBJECT, values,
				SUBJECT_KEY_SUBJECT + " = ?",
				new String[] { subject.getSubjectName() });
		db.close();
		return res;
	}

	// Updating single teacher
	public int updateTeacher(Teacher teacher) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TEACHER_KEY_TEACHER, teacher.getTeacherName());

		// updating row
		int res = db.update(TABLE_TEACHER, values,
				TEACHER_KEY_TEACHER + " = ?",
				new String[] { teacher.getTeacherName() });
		db.close();
		return res;
	}

	// Deleting single lesson
	public void deleteLesson(Lesson lesson) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LESSON, LESSON_KEY_LESSON_NO + " = ?",
				new String[] { String.valueOf(lesson.getLessonNo()) });
		db.close();
	}

	// Deleting single subject
	public void deleteSubject(Subject subject) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SUBJECT, SUBJECT_KEY_SUBJECT + " = ?",
				new String[] { subject.getSubjectName() });
		db.close();
	}

	// Deleting single teacher
	public void deleteTeacher(Teacher teacher) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TEACHER, TEACHER_KEY_TEACHER + " = ?",
				new String[] { teacher.getTeacherName() });
		db.close();
	}

	// Adding or Update lesson
	void addOrUpdateLesson(Lesson lesson) {
		SQLiteDatabase db = this.getWritableDatabase();

		Lesson l = null;
		l = getLesson(lesson.getLessonNo());
		if (l == null) {
			addLesson(lesson);
		} else {
			updateLesson(lesson);
		}

		db.close();
	}

	// Adding or Update Subject
	void addOrUpdateSubject(Subject subject) {
		SQLiteDatabase db = this.getWritableDatabase();

		Subject s = null;
		s = getSubject(subject.getSubjectName());
		if (s == null) {
			addSubject(subject);
		} else {
			updateSubject(subject);
		}

		db.close();
	}

	// Adding or Update Teacher
	void addOrUpdateTeacher(Teacher teacher) {
		SQLiteDatabase db = this.getWritableDatabase();

		Teacher t = null;
		t = getTeacher(teacher.getTeacherName());
		if (t == null) {
			addTeacher(teacher);
		} else {
			updateTeacher(teacher);
		}

		db.close();
	}

	// Getting lessons Count
	public int getLessonsCount() {
		int recCount = 0;
		String countQuery = "SELECT  * FROM " + TABLE_LESSON;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		recCount = cursor.getCount();
		cursor.close();

		// return count
		return recCount;
	}

	// Getting subject Count
	public int getSubjectsCount() {
		int recCount = 0;
		String countQuery = "SELECT  * FROM " + TABLE_SUBJECT;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		recCount = cursor.getCount();
		cursor.close();

		// return count
		return recCount;
	}

	// Getting teacher Count
	public int getTeachersCount() {
		int recCount = 0;
		String countQuery = "SELECT  * FROM " + TABLE_TEACHER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		recCount = cursor.getCount();
		cursor.close();

		// return count
		return recCount;
	}

	// Delete existing plan
	public void deletePlan(String student) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PLAN, PLAN_STUDENT + " = ?", new String[] { student });
		db.close();
	}

	// Add new plan
	public void addPlanForNewStudent(String student) {
		Cursor c = getAllLessons();
		// Add plan for all days and all lessons
		for (int i = 1; i < 6; i++) {
			c.moveToFirst();
			Plan p = new Plan();
			p.setStudent(student);
			p.setDayNo(i);
			p.setLessonNo(c.getInt(1));
			addPlan(p);
			while (c.moveToNext()) {
				p = new Plan();
				p.setStudent(student);
				p.setDayNo(i);
				p.setLessonNo(c.getInt(1));
				addPlan(p);
			}

		}
		c.close();
	}

	// Get All Students
	public List<String> getAllStudents() {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(true, TABLE_PLAN,
				new String[] { PLAN_STUDENT }, null, null, null, null,
				PLAN_STUDENT, null);
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		cursor.close();
		return list;
	}

	public Cursor getPlanByStudent(String student, int dayNo) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Log.d("getPlanByStudent", "Student: " + student + " dayNo: " +
		// dayNo);
		if (student == null)
			student = "";
		final String QUERY = "SELECT * FROM " + TABLE_PLAN
				+ " p LEFT OUTER JOIN " + TABLE_LESSON + " l ON " + " p."
				+ PLAN_LESSON_NO + " = l." + LESSON_KEY_LESSON_NO
				+ " LEFT OUTER JOIN " + TABLE_SUBJECT + " s ON " + " p."
				+ PLAN_SUBJECT + " = s." + SUBJECT_KEY_SUBJECT + " WHERE "
				+ PLAN_STUDENT + "=?" + " AND p." + PLAN_DAY_NO + "=?";

		return db.rawQuery(QUERY,
				new String[] { student, String.valueOf(dayNo) });
	}

	// Get All SubjectList
	public List<String> getAllSubjectList() {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(true, TABLE_SUBJECT,
				new String[] { SUBJECT_KEY_SUBJECT }, null, null, null, null,
				SUBJECT_KEY_SUBJECT, null);
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		cursor.close();
		return list;
	}
	
	// Get All TeacherList
	public List<String> getAllTeacherList() {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(true, TABLE_TEACHER,
				new String[] { TEACHER_KEY_TEACHER }, null, null, null, null,
				TEACHER_KEY_TEACHER, null);
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		cursor.close();
		return list;
	}


	// update Plan
	public int updatePlan(Plan plan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PLAN_SUBJECT, plan.getSubjectName());
		values.put(PLAN_CLASSROOM, plan.getClassroom());
		values.put(PLAN_TEACHER, plan.getTeacherName());

		// updating row
		int res = db.update(
				TABLE_PLAN,
				values,
				PLAN_STUDENT + " = ? AND " + PLAN_DAY_NO + " = ? AND "
						+ PLAN_LESSON_NO + " = ?",
				new String[] { plan.getStudent(),
						Integer.valueOf(plan.getDayNo()).toString(),
						Integer.valueOf(plan.getLessonNo()).toString() });
		db.close();
		return res;
	}

}
