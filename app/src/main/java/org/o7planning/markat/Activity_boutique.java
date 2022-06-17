package org.o7planning.markat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class Activity_boutique extends AppCompatActivity {

    private ImageView img_pro;
    private int[] listImg = MainActivity.listImg;
    private TextView txt_bout;
    public static NFTManager dbNFT = MainActivity.dbNFT;
    private List<Nft> nftList;
    private int id_user;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);

        id_user = getIntent().getIntExtra("ID",0);
        name = getIntent().getStringExtra("NAME");



        nftList= dbNFT.getAllNftFree();
        affichImg(listImg);


        img_pro= (ImageView) this.findViewById(R.id.IMG_retour);
        txt_bout= (TextView) this.findViewById(R.id.TXT_bout);

       // txt_bout.setText(""+id_user);



        this.img_pro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonRetClicked();
            }
        });
    }

    private void affichImg(int[] tabImg)
    {
        LinearLayout scrl = (LinearLayout) this.findViewById(R.id.LI_scrl);
        for (int i = 0; i < nftList.size(); i++)
        {
            ImageView image = new ImageView(Activity_boutique.this);
            image.setBackgroundResource(tabImg[nftList.get(i).getId() - 1]);
            int finalI = i;
            image.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)  {
                    NFTClicked(nftList.get(finalI).getId());
                }
            });
            scrl.addView(image);
        }
    }

    private void NFTClicked(int id_nft)
    {
        Intent i = new Intent(Activity_boutique.this,Activity_showNFT_bout.class);
        i.putExtra("ID_NFT",id_nft);
        i.putExtra("ID_USER",id_user);
        i.putExtra("NAME",name);
        startActivity(i);
        finish();
    }


    private void buttonRetClicked()
    {
        Intent i = new Intent(Activity_boutique.this,activity_profil.class);
        i.putExtra("NAME",name);
        i.putExtra("ID",id_user);
        startActivity(i);
        finish();
    }
}