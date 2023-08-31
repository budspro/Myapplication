package com.example.minegames;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class Game1 extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_scissors , btn_rock , btn_paper;
    ImageView img_user_select, img_robot_select;
    TextView score_user,score_robot;
    TextView message,remainCnt;

    int win[][] = {{0,2},{1,0},{2,1}};
    int img_hands[] ={ R.drawable.gif_scissors,R.drawable.gif_rock,R.drawable.gif_paper};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        btn_scissors = findViewById(R.id.btn_scissors);
        btn_rock = findViewById(R.id.btn_rock);
        btn_paper = findViewById(R.id.btn_paper);
        img_user_select = findViewById(R.id.img_user_select);
        img_robot_select = findViewById(R.id.img_robot_select);
        score_user = findViewById(R.id.score_user);
        score_robot = findViewById(R.id.score_robot);
        message = findViewById(R.id.message);
        remainCnt = findViewById(R.id.remainCnt);

        btn_scissors.setOnClickListener(this);
        btn_rock.setOnClickListener(this);
        btn_paper.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int valUser= 0;
        switch (view.getId()){
            case R.id.btn_scissors:
                valUser =0;break;
            case R.id.btn_rock:
                valUser =1;break;
            case R.id.btn_paper:
                valUser =2;break;
        }
        img_user_select.setImageResource(img_hands[valUser]);

        int valRobot= doRobot();
        if (valRobot==valUser){
            try {
                moveGifImage(img_robot_select,img_hands[valRobot]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                moveGifImage(img_user_select,img_hands[valUser]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if((win[0][0] ==valUser &&win[0][1]==valRobot)||(win[1][0] ==valUser &&win[1][1]==valRobot)||(win[2][0] ==valUser &&win[2][1]==valRobot)){
            try {
                moveGifImage(img_user_select,img_hands[valUser]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Integer score =Integer.parseInt(score_user.getText().toString())+1;
            score_user.setText(score.toString());
        }else{
            try {
                moveGifImage(img_robot_select,img_hands[valRobot]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Integer score =Integer.parseInt(score_robot.getText().toString())+1;
            score_robot.setText(score.toString());
        }

        isGameOver();



    }

    private void isGameOver() {
        Integer cnt = Integer.parseInt(remainCnt.getText().toString());
        cnt--;
        remainCnt.setText(cnt.toString());

        if(cnt==0){
            int s_user = Integer.parseInt(score_user.getText().toString());
            int s_robot = Integer.parseInt(score_robot.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Game Over");
            if (s_user==s_robot)
                builder.setMessage("비겼습니다");
            else if(s_user>s_robot)
                builder.setMessage("축하합니다");
            else
                builder.setMessage("다음기회에");

            builder.setPositiveButton("새게임", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    remainCnt.setText("10");
                    score_user.setText("0");
                    score_robot.setText("0");
                    img_robot_select.setImageResource(R.drawable.img_empty);
                    img_user_select.setImageResource(R.drawable.img_empty);
                }
            });
            builder.setNegativeButton("이제그만", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.create();
            builder.show();

        }
    }

    private void moveGifImage(ImageView imgView, int img_hand) throws IOException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            Drawable decodeImg = ImageDecoder.decodeDrawable(ImageDecoder.createSource(getResources(),img_hand));
            imgView.setImageDrawable(decodeImg);
            AnimatedImageDrawable ani = (AnimatedImageDrawable) decodeImg;
            ani.setRepeatCount(1);
            ani.start();
        }
    }

    private int doRobot() {
        Random random = new Random();
        int valRobot =random.nextInt(3);
        img_robot_select.setImageResource(img_hands[valRobot]);
        return valRobot;
    }
}