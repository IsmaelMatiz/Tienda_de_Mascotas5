package com.example.reto2.casos_uso;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.reto2.modelos.Servicio;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CasoUsoServicio {
    public ArrayList<Servicio> llenarListaServicio(Cursor cursor){
        ArrayList<Servicio> list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            while (cursor.moveToNext()){
                Servicio servicio = new Servicio(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getString(5)
                );
                list.add(servicio);
            }
            return list;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String imageViewToString(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = java.util.Base64.getEncoder().encodeToString(byteArray);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public byte[] stringToByte(String string){
        byte[] result = java.util.Base64.getDecoder().decode(string);
        return result;
    }
}
