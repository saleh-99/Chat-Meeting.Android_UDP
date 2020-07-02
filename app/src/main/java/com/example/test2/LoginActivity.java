package com.example.test2;

        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        final Button button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginChat();
            }
        });

    }
    void LoginChat(){
        Intent intent = new Intent(this, MainActivity.class);
        EditText port = (EditText)findViewById(R.id.Port);
        EditText Name = (EditText)findViewById(R.id.Name);
        intent.putExtra("Port",Integer.parseInt(port.getText().toString()));
        intent.putExtra("Name",Name.getText().toString());
        startActivity(intent);
        finish();
        return;
    }
}
