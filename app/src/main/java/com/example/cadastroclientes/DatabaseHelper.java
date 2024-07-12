/*
* A classe DatabaseHelper no Android é responsável por gerenciar a criação, atualização e operações
  básicas de um banco de dados SQLite. Vamos detalhar cada responsabilidade:

  Responsabilidades da DatabaseHelper
     > Gerenciamento do Ciclo de Vida do Banco de Dados.
     > Criação: Define a estrutura do banco de dados, criando tabelas e suas colunas na primeira vez
       que o banco de dados é acessado.
     > Atualização: Gerencia a atualização da estrutura do banco de dados quando o esquema é alterado
       (por exemplo, adicionando ou removendo colunas).Operações CRUD (Create, Read, Update, Delete).

     > Inserção de Dados ( Create ): Insere novos registros na tabela do banco de dados.
       Leitura de Dados  ( Read   ): Consulta e retorna dados do banco de dados.
       Atualização de Dados ( Update  ): Atualiza registros existentes no banco de dados
       Exclusão de Dados (Delete): Remove registros do banco de dados.
*/

package com.example.cadastroclientes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clientes.db";
    private static final String TABLE_NAME = "clientes";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NOME";
    private static final String COL_3 = "EMAIL";
    private static final String COL_4 = "TELEFONE";

    public DatabaseHelper(Context context) {
        /* Chama o construtor da superclasse SQLiteOpenHelper,
           1. com o nome do banco de dados (DATABASE_NAME),
           2. o contexto da aplicação,
           3. um cursor factory (nulo neste caso),
           4. e a versão do banco de dados (1).
         */
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, EMAIL TEXT, TELEFONE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String nome, String email, String telefone) {
        // Abre o banco de dados para escrita. Se o banco de dados não estiver disponível, ele será criado.
        // Este método obtém uma instância do banco de dados SQLite para realizar operações de escrita.
        SQLiteDatabase db = this.getWritableDatabase();
        // Cria um objeto ContentValues, que é uma estrutura de dados usada para armazenar um conjunto de pares chave-valor.
        // Isso é usado para mapear nomes de coluna para valores de coluna.
        ContentValues contentValues = new ContentValues();
        // Adiciona o valor do parâmetro 'nome' ao objeto ContentValues, associando-o à coluna 'COL_2' (NOME).
        contentValues.put(COL_2, nome);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, telefone);
        // Insere os valores armazenados no objeto ContentValues na tabela especificada por 'TABLE_NAME'.
        // O segundo parâmetro é null, o que significa que não há valor padrão a ser usado para a coluna nula.
        // O método retorna o valor da linha recém-inserida, ou -1 se ocorrer um erro durante a inserção.
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getAllData() {

        /*
        * A linha SQLiteDatabase db = "this.getWritableDatabase()"; é utilizada para obter uma instância do banco de
        * dados que permite tanto leitura quanto escrita.
        * "SQLiteDatabase" é uma classe do Android que fornece os métodos necessários para interagir com um banco de
        *  dados SQLite.
        * */
        SQLiteDatabase db = this.getWritableDatabase();

        /*
        * A linha db.rawQuery("SELECT * FROM " + TABLE_NAME, null); executa uma consulta SQL no banco de dados e
        * retorna um conjunto de resultados na forma de um objeto Cursor.
        * SQL SELECT * FROM + TABLE_NAME seleciona todas as colunas de todas as linhas da tabela especificada
        * por TABLE_NAME.
        * */
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

    }
}
