package hr.java.unipu.projektmobilne;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

        private Button butun1;
        private TextView kilometri;
        private TextView kw;
        private TextView cm3;
        private Spinner godinaP;
        private Spinner mjenjac;
        private Spinner gar;
        private Spinner ekokat;
        private TextView rezultat;

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);






            kilometri = (TextView) findViewById(R.id.kilometri);
            kw = (TextView) findViewById(R.id.kilovati);
            cm3 = (TextView) findViewById(R.id.cm3);

            godinaP = (Spinner) findViewById(R.id.spinnerGod);
            ArrayAdapter<String> godAdapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.god));
            godAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            godinaP.setAdapter(godAdapter);

            mjenjac = (Spinner)findViewById(R.id.spinnerMjenjac);
            mjenjac.setOnItemSelectedListener(this);
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mjenjac.setAdapter(myAdapter);

            gar = (Spinner)findViewById(R.id.spinnerGarancija);
            ArrayAdapter <String> garAdapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gar));
            garAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gar.setAdapter(garAdapter);

            ekokat = (Spinner)findViewById(R.id.spinnerEuro);
            ArrayAdapter <String> euroAdapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.euro));
            euroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ekokat.setAdapter(euroAdapter);

            rezultat = (TextView) findViewById(R.id.rezultat);
            butun1 = (Button) findViewById(R.id.button1);

            final RequestQueue mRequestQueue;

            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

            // Set up the network to use HttpURLConnection as the HTTP client.
            Network network = new BasicNetwork(new HurlStack());

            // Instantiate the RequestQueue with the cache and network.
            mRequestQueue = new RequestQueue(cache, network);

            // String url ="http://209.97.137.105:3000/?km=240000&kw=130&cm3=2200&GodinaP=2007&Mjenjac=1&gar=0&EkoKat=4";
            butun1.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {

                try{
                    int kilometry = Integer.parseInt(kilometri.getText().toString());
                    int ksw = Integer.parseInt(kw.getText().toString());
                    int ccm = Integer.parseInt(cm3.getText().toString());

                }catch (Exception e){
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Upozorenje");
                    adb.setMessage("Unesite sve vrijednosti");
                    adb.setCancelable(false);
                    adb.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    adb.show();
                }

                // Instantiate the cache
                String kilometri_pravi = kilometri.getText().toString();
                String kw_pravi = kw.getText().toString();
                String cm3_pravi = cm3.getText().toString();
                String godinaP_prava = godinaP.getSelectedItem().toString();

                String mjenjac_pravi;
                if (mjenjac.getSelectedItem().toString().equals("Automatski")){
                    mjenjac_pravi = "1";
                }else{
                    mjenjac_pravi = "0";
                }

                String gar_pravi;
                if (gar.getSelectedItem().toString().equals("Da")){
                    gar_pravi = "1";
                }else{
                    gar_pravi = "0";
                }

                String ekokat_pravi = new String();
                switch (ekokat.getSelectedItem().toString()){
                    case "Euro 1":{
                        ekokat_pravi = "1";
                    }break;
                    case "Euro 2":{
                        ekokat_pravi = "2";
                    }break;
                    case "Euro 3":{
                        ekokat_pravi = "3";
                    }break;
                    case "Euro 4":{
                        ekokat_pravi = "4";
                    }break;
                    case "Euro 5":{
                        ekokat_pravi = "5";
                    }break;
                    case "Euro 6":{
                        ekokat_pravi = "6";
                    }break;
                }

                // Start the queue
                mRequestQueue.start();
                final String url ="http://209.97.137.105:3000/?km=" + kilometri_pravi + "&kw=" +
                        kw_pravi + "&cm3=" + cm3_pravi + "&GodinaP=" + godinaP_prava + "&Mjenjac=" +
                        mjenjac_pravi + "&gar=" + gar_pravi + "&EkoKat=" + ekokat_pravi;

                Log.i("MainActivity", url);

                // Formulate the request and handle the response.
                final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Do something with the response
                                String nesto = response.toString();
                                String[] splited = nesto.split("\\s+");
                                String split_one=splited[0];
                                String split_second=splited[1];
                                String split_third=splited[2];
                               // System.out.println(split_third);
                                Log.i("MainActivity", rezultat.toString());
                                rezultat.setText(split_third);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                            }
                        });

                // Add the request to the RequestQueue.
                mRequestQueue.add(stringRequest);
            }
        });
    }
}
