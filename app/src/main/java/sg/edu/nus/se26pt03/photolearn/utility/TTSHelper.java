package sg.edu.nus.se26pt03.photolearn.utility;

import android.content.Context;
import android.content.res.Resources;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by c.banisetty on 3/14/2018.
 * Please Intiate the TTSHelper under onCreate of any component
 */

public class TTSHelper implements TextToSpeech.OnInitListener {
    TextToSpeech textToSpeech = null;
    Context ctx = null;

    public ImageButton getTtsButton() {
        return ttsButton;
    }

    public void setTtsButton(ImageButton ttsButton) {
        this.ttsButton = ttsButton;
    }

    ImageButton ttsButton = null;

    public String getTexttoSpeak() {
        return TexttoSpeak;
    }

    public void setTexttoSpeak(String texttoSpeak) {
        TexttoSpeak = texttoSpeak;
    }

    private String TexttoSpeak = "";

    public TTSHelper(Context ctx) {
        this.ctx = ctx;
        this.ttsButton = ttsButton;
        textToSpeech = new TextToSpeech(ctx, this);
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                if (s.equals("TESTID")) {
                    //TTSHelper.this.ttsButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
                }
            }

            @Override
            public void onDone(String s) {
                if (s.equals("TESTID")) {
                    //TTSHelper.this.ttsButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                }
            }

            @Override
            public void onError(String s) {

            }

            @Override
            public void onStop(String utteranceId, boolean interrupted) {
                if (utteranceId.equals("TESTID")) {
                    // TTSHelper.this.ttsButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                }
            }
        });


    }

    protected void Stop() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }


    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            int setLangresult = textToSpeech.setLanguage(Locale.getDefault());


            if (setLangresult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this.ctx, "Current Device Language is not Supported By TTS", Toast.LENGTH_LONG);
                //ttsButton.setEnabled(false);
            } else {
                //ttsButton.setEnabled(true);
            }

        }
    }
    public void stopTalking() {
        this.setTexttoSpeak("");
        this.textToSpeech.stop();
    }

    public void startandStopTalking() {

        if (this.textToSpeech.isSpeaking()) {
            this.textToSpeech.stop();
        } else {

            this.textToSpeech.speak(this.getTexttoSpeak(), TextToSpeech.QUEUE_FLUSH, null, "TESTID");


        }
    }


}
