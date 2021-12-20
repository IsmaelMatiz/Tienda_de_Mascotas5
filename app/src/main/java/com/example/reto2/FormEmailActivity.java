package com.example.reto2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reto2.databinding.ActivityFormEmailBinding;
import com.google.android.material.snackbar.Snackbar;

public class FormEmailActivity extends AppCompatActivity {

    private ActivityFormEmailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_email);

    }

    public void senEmail(View view){
        String TO = "ismael.matiz.mt@correo.usa.edu.co";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        EditText subject = (EditText) findViewById(R.id.asuntoFormEmail);
        EditText message = (EditText) findViewById(R.id.mensajeFormEmail);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ismael.matiz.mt@correo.usa.edu.co"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, message.getText());


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Snackbar.make(view,"Funcionaaaa",Snackbar.LENGTH_LONG).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
