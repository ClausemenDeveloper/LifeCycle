package pt.ubi.pdm.lifecycle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 *
 *
 * MainActivity é a tela principal da aplicação.
 * Esta classe demonstra os principais estados do ciclo de vida de uma Activity do Android,
 * exibindo o estado atual em um TextView e registrando logs.
 * Também demonstra o uso de Intents explícitas e implícitas.
 */
public class MainActivity extends AppCompatActivity {

    // TAG é uma constante usada para filtrar os logs no Logcat, facilitando a depuração.
    private static final String TAG = "MainActivity";
    // Referência para o TextView que exibirá o estado atual do ciclo de vida.
    private TextView textoEstado;

    /**
     * O método onCreate é o primeiro a ser chamado quando a Activity é criada.
     * É aqui que a interface do usuário (layout) é inflada e as inicializações
     * essenciais são feitas. Este método é chamado apenas uma vez durante a vida da Activity.
     *
     * @param savedInstanceState Se a Activity está sendo recriada após ter sido destruída
     *                           (ex: por rotação de tela), este Bundle contém o estado
     *                           salvo anteriormente em onSaveInstanceState().
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Chama a implementação da superclasse para completar o processo de criação.
        super.onCreate(savedInstanceState);
        // Associa o arquivo de layout 'activity_main.xml' a esta Activity.
        setContentView(R.layout.activity_main);

        // Encontra o TextView no layout pelo seu ID para que possamos manipulá-lo.
        textoEstado = findViewById(R.id.texto_estado);
        // Define o texto inicial para indicar que a Activity foi criada.
        textoEstado.setText("Estado: Criado");
        // Registra uma mensagem de depuração no Logcat.
        Log.d(TAG, "onCreate chamado");

        // --- Configuração dos Botões e Intents ---

        // Encontra os botões no layout pelos seus respectivos IDs.
        Button botaoIntentExplicito = findViewById(R.id.botao_intent_explicito);
        Button botaoIntentImplicito = findViewById(R.id.botao_intent_implicito);

        // Configura um listener de clique para o botão de Intent Explícita.
        botaoIntentExplicito.setOnClickListener(v -> {
            // Uma Intent Explícita especifica o componente exato a ser iniciado (neste caso, SecondActivity.class).
            Intent intentExplicito = new Intent(MainActivity.this, SecondActivity.class);
            // Adiciona dados extras à Intent, que podem ser recuperados pela próxima Activity.
            intentExplicito.putExtra("mensagem", "Olá da MainActivity!");
            // Inicia a SecondActivity.
            startActivity(intentExplicito);
        });

        // Configura um listener de clique para o botão de Intent Implícita.
        botaoIntentImplicito.setOnClickListener(v -> {
            // Uma Intent Implícita descreve uma ação a ser realizada (ACTION_DIAL), sem especificar qual app a executará.
            // O sistema Android encontrará um app capaz de realizar esta ação (neste caso, o discador do telefone).
            Intent intentImplicito = new Intent(Intent.ACTION_DIAL);
            // Define os dados para a ação (o número de telefone a ser discado).
            intentImplicito.setData(Uri.parse("tel:1234567890"));
            // Inicia a atividade que pode lidar com esta Intent.
            startActivity(intentImplicito);
        });

        // Verifica se há um estado salvo para restaurar.
        if (savedInstanceState != null) {
            String estadoSalvo = savedInstanceState.getString("estado");
            if (estadoSalvo != null) {
                // Restaura o texto do TextView para o que era antes da recriação.
                textoEstado.setText(estadoSalvo);
            }
        }
    }

    /**
     * Chamado quando a Activity está prestes a se tornar visível para o usuário.
     * Segue o onCreate() ou onRestart().
     */
    @Override
    protected void onStart() {
        super.onStart();
        textoEstado.setText("Estado: Iniciado");
        Log.d(TAG, "onStart chamado");
    }

    /**
     * Chamado quando a Activity está em primeiro plano e pronta para interagir com o usuário.
     * É o estado onde o app passa a maior parte do tempo.
     */
    @Override
    protected void onResume() {
        super.onResume();
        textoEstado.setText("Estado: Retomado");
        Log.d(TAG, "onResume chamado");
    }

    /**
     * Chamado quando o sistema está prestes a iniciar outra Activity.
     * A Activity atual perde o foco, mas ainda pode estar parcialmente visível.
     * É um bom lugar para salvar dados não persistentes ou parar animações.
     */
    @Override
    protected void onPause() {
        super.onPause();
        textoEstado.setText("Estado: Pausado");
        Log.d(TAG, "onPause chamado");
    }

    /**
     * Chamado quando a Activity não está mais visível para o usuário.
     * Isso pode acontecer porque outra Activity a cobriu completamente ou porque o usuário
     * navegou para a tela inicial.
     */
    @Override
    protected void onStop() {
        super.onStop();
        textoEstado.setText("Estado: Parado");
        Log.d(TAG, "onStop chamado");
    }

    /**
     * O último método chamado antes da Activity ser destruída.
     * Ocorre quando o usuário fecha o app, o sistema precisa de memória ou
     * ocorre uma mudança de configuração que recria a Activity (como rotação de tela).
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Não atualizamos o TextView aqui, pois a UI já não existe. Apenas registramos no log.
        Log.d(TAG, "onDestroy chamado");
    }

    /**
     * Chamado pelo sistema antes que a Activity seja destruída devido a uma mudança de
     * configuração (como rotação de tela) para que você possa salvar seu estado.
     * O estado é salvo em um objeto Bundle.
     *
     * @param outState O Bundle onde o estado da Activity será salvo.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Salva o texto atual do TextView no Bundle.
        outState.putString("estado", textoEstado.getText().toString());
        Log.d(TAG, "onSaveInstanceState chamado");
    }

    /**
     * Chamado após onStart() quando a Activity está sendo recriada a partir de um estado
     * salvo anteriormente. A maioria das implementações restaurará o estado da UI aqui.
     * É uma alternativa a restaurar o estado no onCreate().
     *
     * @param savedInstanceState O Bundle que foi salvo em onSaveInstanceState().
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Recupera o estado salvo do Bundle.
        String estadoSalvo = savedInstanceState.getString("estado");
        if (estadoSalvo != null) {
            // Restaura o texto do TextView.
            textoEstado.setText(estadoSalvo);
        }
        Log.d(TAG, "onRestoreInstanceState chamado");
    }
}
