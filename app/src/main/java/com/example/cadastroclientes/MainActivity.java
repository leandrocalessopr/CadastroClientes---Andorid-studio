package com.example.cadastroclientes;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/*
  No desenvolvimento de aplicativos Android, MainActivity geralmente estende AppCompatActivity para
  garantir compatibilidade com versões mais antigas do Android e para acessar uma gama mais ampla de
  recursos da biblioteca de suporte do Android.

  Razões para Extender AppCompatActivity :
     Compatibilidade com Versões Antigas

  AppCompatActivity faz parte da biblioteca AndroidX (anteriormente AppCompat), que oferece suporte
  a funcionalidades modernas do Android em versões mais antigas do sistema operacional. Isso permite
  que desenvolvedores utilizem novos componentes e APIs enquanto ainda oferecem suporte a dispositivos
  mais antigos.

  Suporte a Action Bar

  AppCompatActivity fornece uma implementação do Action Bar (barra de ações) que é consistente em todas
  as versões do Android, permitindo uma experiência de usuário uniforme. Isso inclui suporte a temas,
  navegação, e outros componentes da interface do usuário.

  Recursos de Temas e Estilos Modernos

  A biblioteca AppCompat permite que desenvolvedores utilizem temas e estilos modernos, como Material
  Design, independentemente da versão do Android. Isso ajuda a criar uma interface de usuário mais
  atraente e consistente.

  Compatibilidade com Componentes da Biblioteca AndroidX

  Muitos componentes modernos da interface do usuário, como RecyclerView, ViewPager, e Fragment, são
  compatíveis com AppCompatActivity. Utilizando AppCompatActivity, você pode facilmente integrar e
  utilizar esses componentes no seu aplicativo.

  Suporte a Fragmentos

  AppCompatActivity oferece suporte aprimorado a fragmentos, que são componentes modulares de interface
  de usuário. Isso facilita a criação de interfaces flexíveis e dinâmicas que podem se adaptar a diferentes
  tamanhos de tela e orientações.

*/
public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editEmail, editPhone;
    Button btnSave, btnView, btnLimparCampo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editTextName);
        editEmail = findViewById(R.id.editTextEmail);
        editPhone = findViewById(R.id.editTextPhone);
        btnSave = findViewById(R.id.buttonSave);
        btnView = findViewById(R.id.buttonView);
        btnLimparCampo = findViewById(R.id.buttonLimparCampo);

        saveData();
        viewAll();
        limparCampos();
    }

    public void saveData() {
        btnSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* Responsabilidade: Insere um novo registro na tabela clientes. */
                        boolean isInserted = myDb.insertData ( editName.getText().toString(),
                                                               editEmail.getText().toString(),
                                                               editPhone.getText().toString());
                        if (isInserted)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /*
    * StringBuffer é uma classe em Java utilizada para criar objetos que podem ser
    * modificados (mutáveis). Ao contrário das strings, os objetos de StringBuffer
    * podem ser alterados sem a necessidade de criar um novo objeto.
    * */
    public void viewAll() {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*
                        * A linha Cursor res = myDb.getAllData(); executa uma operação de leitura no banco de
                        * dados SQLite e armazena o resultado em um objeto Cursor.
                        *
                        * Cursor é uma interface fornecida pelo Android que permite percorrer os resultados
                        * retornados por uma consulta ao banco de dados. Ele atua como um iterador, permitindo
                        * acessar cada linha do conjunto de resultados.
                        * */
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error", "No Data Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id : " + res.getString(0) + "\n");
                            buffer.append("Nome : " + res.getString(1) + "\n");
                            buffer.append("Email : " + res.getString(2) + "\n");
                            buffer.append("Telefone : " + res.getString(3) + "\n\n");
                        }

                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void limparCampos(){

        btnLimparCampo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editName.setText("");
                editEmail.setText("");
                editPhone.setText("");
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}