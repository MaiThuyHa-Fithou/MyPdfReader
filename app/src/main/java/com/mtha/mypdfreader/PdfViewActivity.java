package com.mtha.mypdfreader;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.mtha.mypdfreader.utils.MyFileUtils;

import java.io.File;


public class PdfViewActivity extends AppCompatActivity {
    PDFView pdfView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progressBar);
        PRDownloader.initialize(getApplicationContext());
        Intent mIntent = getIntent();
        checkPdfAction(mIntent);
    }

    private void checkPdfAction(Intent intent){
        String viewType = intent.getStringExtra("viewtype");
        if(viewType.equals("assets")){
            showPdfFromAssets(MyFileUtils.getPdfFromAssets());
        }
        if(viewType.equals("storage")){
            showPdfFromStorage();
        }
        if(viewType.equals("internet")){
            progressBar.setVisibility(View.VISIBLE);
            String fileName ="myFile.pdf";
            downloadFromInternet(MyFileUtils.getPdfUrl(),
                    MyFileUtils.getRootDirPath(this), fileName);

        }
    }

    private void downloadFromInternet(String url, String dirPath,String fileName){
        PRDownloader.download(url,dirPath,fileName).build()
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(PdfViewActivity.this,
                                "download complete", Toast.LENGTH_LONG).show();
                        File downloadFile =new File(dirPath,fileName);
                        showPdfFromFile(downloadFile);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });

    }
    private void showPdfFromFile(File file){
        pdfView.fromFile(file)
                .password(null)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        Toast.makeText(PdfViewActivity.this, "error at page ["+page+"]",
                                Toast.LENGTH_LONG).show();
                    }
                }).load();
    }
    private void showPdfFromAssets(String pdfName){
        pdfView.fromAsset(pdfName).password(null).defaultPage(0)
                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        Toast.makeText(PdfViewActivity.this, "error at page ["+page+"]",
                                Toast.LENGTH_LONG).show();
                    }
                }).load();
    }

    private void showPdfFromStorage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        showPdfResultLaucher.launch(Intent.createChooser(intent,"Select PDF"));
    }
    ActivityResultLauncher<Intent> showPdfResultLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //todo something
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Uri uri = result.getData().getData();
                        Toast.makeText(PdfViewActivity.this, "["+uri+"]",
                                Toast.LENGTH_LONG).show();
                        showPdfFromUri(uri);
                    }
                }
            }
    );

    private void showPdfFromUri(Uri uri){
        pdfView.fromUri(uri).defaultPage(0).
                spacing(10).load();
    }
}