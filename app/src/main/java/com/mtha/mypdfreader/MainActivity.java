package com.mtha.mypdfreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnWebView, btnFromAsset, btnFromStorage, btnFromInternet, btnFromPdfRender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnViewPager:
                intent = new Intent(this,WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFromAsset:
                intent = new Intent(this,PdfViewActivity.class);
                intent.putExtra("viewtype","assets");
                startActivity(intent);
                break;
            case R.id.btnFromStorage:
                intent = new Intent(this,PdfViewActivity.class);
                intent.putExtra("viewtype","storage");
                startActivity(intent);
                break;
            case R.id.btnFromInternet:
                intent = new Intent(this,PdfViewActivity.class);
                intent.putExtra("viewtype","internet");
                startActivity(intent);
                break;
        }
    }

    public void getViews(){
        btnWebView = findViewById(R.id.btnViewPager);
        btnFromAsset = findViewById(R.id.btnFromAsset);
        btnFromStorage = findViewById(R.id.btnFromStorage);
        btnFromInternet = findViewById(R.id.btnFromInternet);
        btnFromPdfRender = findViewById(R.id.btnPdfRender);
        btnFromPdfRender.setOnClickListener(this::onClick);
        btnWebView.setOnClickListener(this::onClick);
        btnFromAsset.setOnClickListener(this::onClick);
        btnFromStorage.setOnClickListener(this::onClick);
        btnFromInternet.setOnClickListener(this::onClick);
    }
}