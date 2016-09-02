package com.hoge.helloemojidex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.emojidex.emojidexandroid.Emojidex;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!Emojidex.getInstance().requestPermission(this))
            initialize();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == Emojidex.REQUEST_CODE)
        {
            initialize();
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initialize()
    {
        Emojidex emojidex = Emojidex.getInstance();

        emojidex.initialize(this);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(emojidex.emojify(emojidex.deEmojify(
                "emoji :heart eyes(pudding): enabled \uD83C\uDF2E static :車: text \uD83D\uDC83\uD83C\uDFFD."
        )));

        final EditText editText = (EditText)findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                editText.removeTextChangedListener(this);

                Emojidex emojidex = Emojidex.getInstance();
                editable.replace(0, editable.length(), emojidex.emojify(emojidex.deEmojify(editable)));

                editText.addTextChangedListener(this);
            }
        });
    }
}
