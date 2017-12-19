package com.lll.beizertest.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lll.beizertest.R;
import com.lll.beizertest.view.signature.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 画板
 */
public class SignaturePadActivity extends AppCompatActivity {

    private SignaturePad signatureView;

    private boolean isSigned;

    /**
     * 签名图片
     */
    public static final File signPath = new File(Environment.getExternalStoragePublicDirectory("custom_view_sign").getAbsolutePath());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_pad);
        signatureView = findViewById(R.id.signatureView);
        Log.e("TAG","PackageName:"+getPackageName());
        findViewById(R.id.btn_saveSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSigned){
                    Toast.makeText(SignaturePadActivity.this, "保存", Toast.LENGTH_SHORT).show();
                    addJpgSignatureToGallery(signatureView);
                    //signatureView.clear();
                    //finish();
                }
            }
        });
        findViewById(R.id.btn_clearSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clear();
            }
        });
        signatureView.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Log.e("TAG","signatureView-onStartSigning");
            }

            @Override
            public void onSigned() {
                isSigned = true;
                Log.e("TAG","signatureView-onSigned");
            }

            @Override
            public void onClear() {
                isSigned = false;
                Log.e("TAG","signatureView-onClear");
            }
        });
    }

    /**
     * 保存文件到图库
     *
     * @param signaturePad
     * @return
     */
    public String addJpgSignatureToGallery(SignaturePad signaturePad) {
        String result = null;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signaturePad.getSignatureBitmap(), photo);
            scanMediaFile(photo);
            result = photo.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG","addJpgSignatureToGallery-IOException:"+e.getMessage());
        }
        return result;

    }

    /**
     * 获取保存路径
     *
     * @param albumName
     * @return
     */
    public File getAlbumStorageDir(String albumName) {
        File file = new File(signPath, albumName);
        if (!file.exists()) {
            file.mkdirs();
            Log.e("SignaturePad", "Directory not created");
        }else {
            Log.e("SignaturePad", "getAlbumStorageDir-mkdirs："+file.mkdirs());
        }
        return file;
    }

    /**
     * 保存bitmap为jpg
     *
     * @param bitmap
     * @param photo
     * @throws IOException
     */
    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    /**
     * 保存图片后发送系统广播扫描图库
     *
     * @param photo
     */
    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //File file = new File(signPath.getAbsolutePath());
        //clearSign(file);
    }


    /**
     * 递归清理
     */
    private void clearSign(File paths) {
        if(paths.isFile()){
            paths.delete();
            return;
        }
        File[] files = paths.listFiles();
        for(File file:files){
            clearSign(file);
        }
    }

}
