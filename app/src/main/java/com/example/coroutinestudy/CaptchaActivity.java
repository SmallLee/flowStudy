package com.example.coroutinestudy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.module.captcha.Captcha;

public class CaptchaActivity extends AppCompatActivity {

    private Captcha captcha;
    private Button btnMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);

        captcha = (Captcha) findViewById(R.id.captCha);
        btnMode = (Button) findViewById(R.id.btn_mode);
        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captcha.getMode() == Captcha.MODE_BAR) {
                    captcha.setMode(Captcha.MODE_NONBAR);
                    btnMode.setText("sliding bar mode");
                } else {
                    captcha.setMode(Captcha.MODE_BAR);
                    btnMode.setText("no slider mode");
                }
            }
        });
        captcha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public String onAccess(long time) {
                Toast.makeText(CaptchaActivity.this, "verification successful", Toast.LENGTH_SHORT).show();
                return "verification passed";
            }

            @Override
            public String onFailed(int count) {
                Toast.makeText(CaptchaActivity.this, "verification failed number of failures" + count, Toast.LENGTH_SHORT).show();
                return "verification failed";
            }

            @Override
            public String onMaxFailed() {
                Toast.makeText(CaptchaActivity.this, "Verified more than once, your account has been blocked", Toast.LENGTH_SHORT).show();
                return "you can go";
            }

        });
    }


    boolean isCat = true;

    public void changePicture(View view) {
        if (isCat) {
            captcha.setBitmap("https://img1.baidu.com/it/u=847956157,2750448390&fm=253&fmt=auto&app=138&f=JPEG");
        } else {
            captcha.setBitmap(com.module.captcha.R.drawable.cat);
        }
        isCat = !isCat;
    }

    boolean isSeekbar1 = false;

    public void changeProgressDrawable(View view) {
        if (isSeekbar1) {
            captcha.setSeekBarStyle(com.module.captcha.R.drawable.po_seekbar, com.module.captcha.R.drawable.thumb);
        } else {
            captcha.setSeekBarStyle(R.drawable.po_seekbar1, R.drawable.thumb1);
        }
        isSeekbar1 = !isSeekbar1;
    }
}
