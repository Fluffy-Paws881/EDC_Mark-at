package org.o7planning.markat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_showNFT_bout extends AppCompatActivity {

    private ImageView img_retour;
    private ImageView img_nft;
    private TextView txt_title;
    private TextView txt_prix;
    private TextView txt_comp;
    private Button btn_buy;

    private int[] listImg = MainActivity.listImg;
    public NFTManager dbNFT = MainActivity.dbNFT;
    private int id_user;
    private int id_nft;
    private String name;

    private int currentEuro = MainActivity.currentEuro;

    private Nft SelectNft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_nft_bout);

        id_user = getIntent().getIntExtra("ID_USER",0);
        id_nft = getIntent().getIntExtra("ID_NFT",0);
        name = getIntent().getStringExtra("NAME");

        img_nft = (ImageView) this.findViewById(R.id.IMG_NFT);
        img_nft.setBackgroundResource(listImg[id_nft-1]);

        SelectNft = dbNFT.getNft(id_nft);

        txt_title = (TextView) this.findViewById(R.id.TXT_nftName);
        txt_title.setText(SelectNft.getName());


        txt_prix = (TextView) this.findViewById(R.id.TXT_prix);
        txt_prix.setText("prix d'achat : "+SelectNft.getPrice()+"$ ("+SelectNft.getPriceETH()+" ETH)" );

      /*  txt_comp = (TextView) this.findViewById(R.id.TXT_compar);
        txt_prix.setText("prix d'achat : "+SelectNft.getPrice()+"$ ("+SelectNft.getPriceETH()+" ETH)" );*/

        img_retour = (ImageView) this.findViewById(R.id.IMG_bck);

        this.img_retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonRetClicked();
            }
        });

        btn_buy = (Button) findViewById(R.id.BTN_buy);
        this.btn_buy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonBuyClicked();
            }
        });
    }

    private void buttonBuyClicked()
    {
        dbNFT.setNFTOwner(id_user, id_nft);
        buttonRetClicked();
    }

    private void buttonRetClicked()
    {
        Intent i = new Intent(Activity_showNFT_bout.this,Activity_boutique.class);
        i.putExtra("ID",id_user);
        i.putExtra("NAME",name);
        startActivity(i);
        finish();
    }
}