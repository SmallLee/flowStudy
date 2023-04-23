package com.example.coroutinestudy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistry;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class CallWhatsAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_whats_app);
        Button btn = findViewById(R.id.btn);
        // SavedStateHandle
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager = getPackageManager();
                Intent launchIntent = packageManager.getLaunchIntentForPackage("com.example.module_info");
                startActivity(launchIntent);
            }
        });
        getSavedStateRegistry().registerSavedStateProvider("", new SavedStateRegistry.SavedStateProvider() {
            @NonNull
            @Override
            public Bundle saveState() {
                Bundle arg = new Bundle();
                arg.putString("name","aaa");
                arg.putString("name","bbb");
                return arg;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("age","11");
        outState.putString("age","22");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String name = savedInstanceState.getString("name");
    }

    public void gett(APIType apiType){
        // 保存状态

        Class<?> tClass = ClassUtil.INSTANCE.getTClass(apiType);
        Log.d("======" ,": " + tClass);
    }

}
