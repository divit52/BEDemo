package com.nuance.nmdp.sample;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nuance.nmdp.speechkit.SpeechError;
import com.nuance.nmdp.speechkit.SpeechKit;
import com.nuance.nmdp.speechkit.Vocalizer;

public class MainView extends Activity {

	private static SpeechKit speechKit;
	private Vocalizer vocalizer;
	private Object ttsContext = null;
	Button bConv, bSay, bNext;
	EditText eTPut;
	TextView tV1;

	static SpeechKit getSpeechKit() {
		return speechKit;
	}

	public MainView() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bConv = (Button) findViewById(R.id.bConv);
		bSay = (Button) findViewById(R.id.bSay);
		bNext = (Button) findViewById(R.id.bNext);
		eTPut = (EditText) findViewById(R.id.eTPut);
		tV1 = (TextView) findViewById(R.id.tV1);

		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		if (speechKit == null) {
			speechKit = SpeechKit.initialize(getApplication()
					.getApplicationContext(), AppInfo.SpeechKitAppId,
					AppInfo.SpeechKitServer, AppInfo.SpeechKitPort,
					AppInfo.SpeechKitSsl, AppInfo.SpeechKitApplicationKey);
			speechKit.connect();

		}
		vocalizer = MainView.getSpeechKit().createVocalizerWithLanguage(
				"en_US", vocalizerListener, new Handler());

		bConv.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				tV1.setText(eTPut.getText().toString());
			}

		});

		bSay.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String text = tV1.getText().toString();
				System.out.println(text);
				ttsContext = new Object();
				vocalizer.speakString(text, ttsContext);
			}

		});
		bNext.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent next = new Intent(v.getContext(), DictateView.class);
				startActivity(next);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (speechKit != null) {
			speechKit.release();
			speechKit = null;
		}
	}

	// Create Vocalizer listener
	Vocalizer.Listener vocalizerListener = new Vocalizer.Listener() {
		public void onSpeakingBegin(Vocalizer vocalizer, String text,
				Object context) {
			System.out.println("Testing Started");

		}

		public void onSpeakingDone(Vocalizer vocalizer, String text,
				SpeechError error, Object context) {
			// Use the context to detemine if this was the final TTS phrase
			System.out.println("Testing Ended");
		}

	};

}
