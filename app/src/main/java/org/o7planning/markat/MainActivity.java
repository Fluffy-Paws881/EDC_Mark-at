package org.o7planning.markat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public static int currentEuro;
    public static int[] listImg;
    public static NFTManager dbNFT;
    private final List<User> userList = new ArrayList<User>();
    
    private EditText et_id ;
    private EditText et_psw ;
    private Button btn_connect;
    private Button btn_quit ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbNFT = new NFTManager(this);
        //dbNFT.deleteAll();
        dbNFT.populateNFTListeArray();

        setListImg();

        //gecko
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                //.penaltyDeath()
                .build());

        try {
            HttpURLConnection conn;
            URL url = new URL("https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=eur%2Cbtc");
            conn = (HttpURLConnection) url.openConnection();
            Log.i("mec", "je suis avant");
            conn.getResponseCode();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                Log.i("Hey", "Mec cool");
                InputStream inputStream = null;
                try {
                    inputStream = conn.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // ...que l'on transforme ici en String par simplicité d'usage (note :
                // il peut s'agit d'autre chose qu'un String pour
                // d'autres webservices, comme des images)
                String data = readStringData(inputStream);

                Log.i("Request", data);

               JSONObject js = new JSONObject(data);

                Log.i("cc", js.getJSONObject("ethereum").getString("btc"));
                currentEuro = (int) Math.round(Double.parseDouble(js.getJSONObject("ethereum").getString("eur"))) ;
            }
            else {

                String response = "FAILED"; // See documentation for more info on response handling
            }

        } catch (IOException | JSONException e) {
            //TODO Handle problems..
        }

        SQLiteManager db = new SQLiteManager(this);
        db.populateUserListeArray();

        List<User> list=  db.getAllUser();
        this.userList.addAll(list);

        et_id = (EditText) this.findViewById(R.id.ET_id);
        et_psw = (EditText) this.findViewById(R.id.ET_psw);
        btn_connect = (Button) this.findViewById(R.id.BTN_connect);
        btn_quit = (Button) this.findViewById(R.id.BTN_quit);



        //et_id.setText(""+currentEuro);
        this.btn_connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonConnectClicked();
            }
        });
        
        this.btn_quit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonQuitClicked();
            }
        });
     
        
        
    }

    private String readStringData(InputStream stream)  {
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
            //return null;

        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "not working", e);

        } finally {
            // On ferme tout les flux dans tout les cas
            if(reader != null){
                try {
                    reader.close();

                } catch (IOException exp2) {
                    Log.e(getClass().getSimpleName(), "not working again", exp2);
                }
            }
        }
        return null;
    }

    private void buttonQuitClicked()
    {
        finish();
        System.exit(0);
    }

    private void buttonConnectClicked()
    {
        if (et_id.getText().length() == 0 || et_psw.getText().length() == 0)
        {
            Toast toast=Toast.makeText(getApplicationContext(),"un ou plusieurs champs manquants",Toast.LENGTH_SHORT);
            toast.show();
        }else {
            User currentUser = null;
            String username = et_id.getText().toString();
            String psw = et_psw.getText().toString();
            for (int i= 0; i < userList.size(); i++)
            {
                   if (userList.get(i).getUser_name().equals(username) && userList.get(i).getPassword().equals(psw) )
                   {
                       currentUser = userList.get(i);
                   }
            }

            if (currentUser != null)
            {

                Intent i = new Intent(MainActivity.this,activity_profil.class);
                i.putExtra("NAME",currentUser.getUser_name());
                i.putExtra("ID",currentUser.getId());
                startActivity(i);
            }else{
                Toast toast=Toast.makeText(getApplicationContext(),"mots de passe ou identifiant incorect",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void setListImg()
    {
        int[] textureArrayWin = {
                R.drawable.chat1,
                R.drawable.chat2,
                R.drawable.chat3,
                R.drawable.chat4,
                R.drawable.chat5,
                R.drawable.chat6

        };
        listImg = textureArrayWin;
    }



}