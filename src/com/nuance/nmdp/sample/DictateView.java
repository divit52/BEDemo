package com.nuance.nmdp.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nuance.nmdp.speechkit.Recognition;
import com.nuance.nmdp.speechkit.Recognizer;
import com.nuance.nmdp.speechkit.SpeechError;

public class DictateView extends Activity{

	private Handler handler = null;
	private final Recognizer.Listener listener;
	private Recognizer curRecognizer;
	Button bNina, bStop;
	TextView tVNina;
	String command;
	
	public DictateView()
	{
		super();
		curRecognizer = null;
		listener = createListener();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dictate);
		
		bNina = (Button) findViewById(R.id.bNina);
		
		bStop = (Button) findViewById(R.id.bStop);
		
		tVNina = (TextView) findViewById(R.id.tVNina);
		
		bNina.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				curRecognizer = MainView.getSpeechKit().createRecognizer(Recognizer.RecognizerType.Dictation, Recognizer.EndOfSpeechDetection.Long, "en_US", listener, handler);
				curRecognizer.setListener(listener);
				curRecognizer.start();
			}
			
		});
		
		bStop.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				curRecognizer.stopRecording();
				
				
			}
			
		});
	}
	
	private Recognizer.Listener createListener() {
		return new Recognizer.Listener() {
			
			public void onResults(Recognizer recognizer, Recognition results) {
				System.out.println("Dictate Results are gathered");
				int count = results.getResultCount();
				Recognition.Result [] rs = new Recognition.Result[count];
				for (int i = 0; i < count; i++)
				{
					rs[i] = results.getResult(i);
				}
				System.out.println("Results are in: " + rs[0].getText().toString());
				String test = rs[0].getText();
				if (test.equalsIgnoreCase("HOME"))
				{
					finish();
				}
				else
				{
					Toast toast = Toast.makeText(getApplicationContext(), test, 1000);
					toast.show();
				}
				
			}
			
			public void onRecordingDone(Recognizer arg0) {
				System.out.println("Dictate Testing Done");

			}
			
			public void onRecordingBegin(Recognizer arg0) {
				// TODO Auto-generated method stub
				System.out.println("Dictate Testing Started");
				
			}
			
			public void onError(Recognizer arg0, SpeechError arg1) {
				System.out.println("Dictate Testing Error");
			}
		};
	}
	
}
