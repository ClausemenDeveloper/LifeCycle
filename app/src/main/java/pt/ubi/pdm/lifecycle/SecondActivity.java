package pt.ubi.pdm.lifecycle;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textoMensagem = findViewById(R.id.texto_mensagem);
        String mensagem = getIntent().getStringExtra("mensagem");
        if (mensagem != null) {
            textoMensagem.setText(mensagem);
        }
    }
}