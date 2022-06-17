package org.o7planning.markat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class activity_profil extends AppCompatActivity {

    private NFTManager dbNFT;

    private int[] listImg = MainActivity.listImg;

    private LinearLayout li_inv;
    private TextView txt_user;
    private Button btn_dec;
    private ImageView img_bou;

    private List<Nft> nftList;

    private  int id;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        dbNFT = MainActivity.dbNFT;



         name = getIntent().getStringExtra("NAME");
         id = getIntent().getIntExtra("ID",0);

        nftList = dbNFT.getOwnerNft(id);
        li_inv = (LinearLayout) this.findViewById(R.id.LI_inventaire);
        affichImg(listImg);

        txt_user = (TextView) this.findViewById(R.id.TXT_pro);
        btn_dec = (Button) this.findViewById(R.id.BTN_deco);
        img_bou = (ImageView) this.findViewById(R.id.IMG_bout);


        txt_user.setText("vous posseder = "+nftList.size()+" NFT");
        //txt_user.setText("value"+ id);

        this.btn_dec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonDecClicked();
            }
        });

        this.img_bou.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v)  {
                buttonImgClicked();
            }
        });




    }

    private void affichImg(int[] tabImg)
    {


        for (int i = 0; i < nftList.size(); i++)
        {
            ImageView image = new ImageView(activity_profil.this);
            image.setBackgroundResource(tabImg[nftList.get(i).getId() - 1]);
            int finalI = i;
            image.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)  {
                    NFTClicked(nftList.get(finalI).getId());
                }
            });
            li_inv.addView(image);
        }
    }

    private void NFTClicked(int id_nft)
    {
        Intent i = new Intent(activity_profil.this,activity_profil_selectNFT.class);
        i.putExtra("ID_NFT",id_nft);
        i.putExtra("ID_USER",id);
        i.putExtra("NAME",name);
        startActivity(i);
        finish();
    }


    private void buttonImgClicked()
    {
        Intent i = new Intent(activity_profil.this,Activity_boutique.class);
        i.putExtra("ID",id);
        i.putExtra("NAME",name);
        startActivity(i);
        finish();
    }

    private void buttonDecClicked()
    {
        Intent i = new Intent(activity_profil.this,MainActivity.class);
        startActivity(i);
        finish();
    }

}