package br.edu.ifpr.webandroid.exemploretrofit;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import br.edu.ifpr.webandroid.exemploretrofit.model.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final String APP_ID = "c78aebd3fcd1b027fdecc7efa238effb";
    private final String BASE_URL = "http://api.openweathermap.org/";
    private TextView mTextoCidade, mTextoStatus, mTextoHumidade, mTextoPressao;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextoCidade = (TextView) findViewById(R.id.textoCidade);
        mTextoStatus = (TextView) findViewById(R.id.textoStatus);
        mTextoHumidade = (TextView) findViewById(R.id.textoHumidade);
        mTextoPressao = (TextView) findViewById(R.id.textoPressao);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void getReport(String cidade) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherMapAPI service = retrofit.create(OpenWeatherMapAPI.class);

        Call<Weather> call = service.getWeatherReport(cidade, APP_ID);

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                try {
                    String cidade = response.body().getName();

                    String status = response.body().getWeather().get(0).getDescription();

                    String humidade = response.body().getMain().getHumidity().toString();

                    String pressao = response.body().getMain().getPressure().toString();

                    mTextoCidade.setText("Cidade  :  " + cidade);
                    mTextoStatus.setText("Status  :  " + status);
                    mTextoHumidade.setText("Humidade  : " + humidade);
                    mTextoPressao.setText("Pressão  :  " + pressao);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Weather> weatherCall,Throwable t) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.edu.ifpr.webandroid.exemploretrofit/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.edu.ifpr.webandroid.exemploretrofit/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.set_city:
                Log.d(TAG, "Selecionar Cidade");
                selecionarCidade();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selecionarCidade() {
        final EditText textoSelecionarCidade = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Selecione a Cidade")
                .setMessage("Cidade:")
                .setView(textoSelecionarCidade)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String cidade = String.valueOf(textoSelecionarCidade.getText());
                        Log.d(TAG, "Cidade selecionada: " + cidade);
                        getReport(cidade);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
    }

}
