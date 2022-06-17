package org.o7planning.markat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_profil_selectNFT extends AppCompatActivity {

    private ImageView img_retour;
    private ImageView img_nft;
    private TextView txt_title;
    private TextView txt_prix;
    private Button btn_sell;

    private int[] listImg = MainActivity.listImg;
    public NFTManager dbNFT = MainActivity.dbNFT;

    private int id_user;
    private int id_nft;
    private String name;

    private Nft SelectNft;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_select_nft);

        id_user = getIntent().getIntExtra("ID_USER",0);
        id_nft = getIntent().getIntExtra("ID_NFT",0);
        name = getIntent().getStringExtra("NAME");

        img_nft = (ImageView) this.findViewById(R.id.IMG_nftPro);
        img_nft.setBackgroundResource(listImg[id_nft-1]);

        SelectNft = dbNFT.getNft(id_nft);

        txt_title = (TextView) this.findViewById(R.id.TXT_TitrePro);
        txt_title.setText(SelectNft.getName());

        txt_prix = (TextView) this.findViewById(R.id.TXT_prixPro);
        txt_prix.setText("prix d'achat d'origine est de : "+SelectNft.getPrice()+"$");

        img_retour = (ImageView) this.findViewById(R.id.IMG_backPro);
        this.img_retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonRetClicked();
            }
        });

        btn_sell = (Button) findViewById(R.id.BTN_sell);
        this.btn_sell.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonSellClicked();
            }
        });
    }

    private void buttonSellClicked()
    {
        dbNFT.setNFTOwner(0, id_nft);
        buttonRetClicked();
    }

    private void buttonRetClicked()
    {
        Intent i = new Intent(activity_profil_selectNFT.this,activity_profil.class);
        i.putExtra("ID",id_user);
        i.putExtra("NAME",name);
        startActivity(i);
        finish();
    }
}