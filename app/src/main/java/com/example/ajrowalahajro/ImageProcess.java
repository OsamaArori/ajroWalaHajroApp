package com.example.ajrowalahajro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageProcess {
    public String encode(Bitmap bit) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public Bitmap decode(String encode){
        byte[] b= Base64.decode(encode, Base64.DEFAULT);
        Bitmap bm= BitmapFactory.decodeByteArray(b,0,b.length);
        return bm;
    }

}
