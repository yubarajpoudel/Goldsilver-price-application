package com.yuvi.goldsilver;

import java.text.Normalizer.Form;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((TextView) findViewById(R.id.header)).setText(Html.fromHtml("<u>Developed By@Yubaraj Poudel</u>"));
		String weekDay = "";
		 String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
			        "August", "September", "October", "November", "December" };

		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);                

	    if (Calendar.MONDAY == dayOfWeek) weekDay = "monday";
	    else if (Calendar.TUESDAY == dayOfWeek) weekDay = "tuesday";
	    else if (Calendar.WEDNESDAY == dayOfWeek) weekDay = "wednesday";
	    else if (Calendar.THURSDAY == dayOfWeek) weekDay = "thursday";
	    else if (Calendar.FRIDAY == dayOfWeek) weekDay = "friday";
	    else if (Calendar.SATURDAY == dayOfWeek) weekDay = "saturday";
	    else if (Calendar.SUNDAY == dayOfWeek) weekDay = "sunday";
	    String month = monthName[cal.get(Calendar.MONTH)];
		((TextView) findViewById(R.id.today)).setText(cal.get(Calendar.YEAR)
				+ "  " + month  + " "
				+ weekDay);
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			AlertDialogManager alert = new AlertDialogManager();
			alert.showAlertDialog(MainActivity.this, "Turn on WiFi",
					"No Internet Connection", false);
		}
		new GetData().execute();

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle("Loading");
		dialog.setMessage("Fetching today price rates\n Please Wait...");
		return dialog;
	}

	class GetData extends AsyncTask<String, String, String> {
		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			showDialog(1);
		}

		@Override
		protected String doInBackground(String... params) {

			ServerRequest req = new ServerRequest();
			return req.requestGetHttp("http://goldpricenepal.herokuapp.com/");

		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissDialog(1);
			try {
				JSONObject jObj = new JSONObject(result);
				String gold_hall = jObj.getString("Hallmark Gold");
				String gold_tej = jObj.getString("Tejabi Gold");
				String silver = jObj.getString("Silver");
				((TextView) findViewById(R.id.silverprice)).setText("Rs "
						+ silver + " Per Tola ");
				((TextView) findViewById(R.id.goldprice))
						.setText("HallMark Gold \n Rs " + gold_hall + "Per Tola \n "
								+ "Tejabi Gold \n Rs " + gold_tej +" Per Tola ");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			AlertDialogManager alert = new AlertDialogManager();
			alert.showAlertDialog(MainActivity.this, "Yubaraj Poudel",
					"Hi me YubarajPoudel.\n"
							+ "This App is totally developed for the \n"
							+ "learning purpose. to know more about me\n"
							+ "Please feel free to call me \n "
							+ "Mob No: 00977-9842583634 \n"
							+ "Skype Id: yubarajpoudel", false);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);

	}

	public void linkme(View view) {
		Intent intent = new Intent(this, me.class);

		String links = "https://www.facebook.com/yubaraj.poudel.1";
		intent.putExtra("link", links);
		startActivity(intent);
	}
}
