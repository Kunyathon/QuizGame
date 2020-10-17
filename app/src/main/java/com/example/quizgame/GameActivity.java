package com.example.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizgame.model.WordItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mQuestionImageView;
    private Button[] mButtons = new Button[4];

    private String mAnswerWord;
    private Random mRandom;
    private List<WordItem> mItemList;

    TextView txtShow;
    private int score = 0;
    private int AnswerCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionImageView = findViewById(R.id.question_image_view);
        mButtons[0] = findViewById(R.id.choose_butoon_1);
        mButtons[1] = findViewById(R.id.choose_butoon_2);
        mButtons[2] = findViewById(R.id.choose_butoon_3);
        mButtons[3] = findViewById(R.id.choose_butoon_4);
        txtShow = findViewById(R.id.txtShow);

        mButtons[0].setOnClickListener(this);
        mButtons[1].setOnClickListener(this);
        mButtons[2].setOnClickListener(this);
        mButtons[3].setOnClickListener(this);


        txtShow = findViewById(R.id.txtShow);
        mItemList = new ArrayList<>(Arrays.asList(WordQuizActivity.item));
        mRandom  = new Random();
        newQuiz();
    }
    private void newQuiz(){

        int answerIndex = mRandom.nextInt(mItemList.size());

        WordItem item = mItemList.get(answerIndex);

        mQuestionImageView.setImageResource(item.imageResId);

        mAnswerWord = item.word;

        int randomButton = mRandom.nextInt(4);

        mButtons[randomButton].setText(item.word);

        mItemList.remove(item);

        Collections.shuffle(mItemList);

        // เอาคำศัพท์จาก list ที่ตำแหน่ง 0 ถึง 3 มาแสดงที่ปุ่ม 3 ปุ่มที่เหลือ โดยข้ามปุ่มที่เป็นคำตอบ
        for (int i = 0; i < 4; i++) {
            if (i == randomButton) { // ถ้า i คือ index ของปุ่มคำตอบ ให้ข้ามไป
                continue;
            }
            mButtons[i].setText(mItemList.get(i).word);

        }
    }
    private void checkRound(){
        AnswerCount++;
        if(AnswerCount==5){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("สรุปผล");
            builder.setMessage("คุณได้ "+score+" คะแนน\n\nคุณต้องการเล่นเกมใหม่หรือไม่");
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AnswerCount=0;
                    score=0;
                    txtShow.setText(score+" คะแนน");
                    mItemList = new ArrayList<>(Arrays.asList(WordQuizActivity.item));
                    newQuiz();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }else{
            newQuiz();
        }

    }

    @Override
    public void onClick(View view) {
        Button b = findViewById(view.getId());
        String buttonText = b.getText().toString();

        if (buttonText.equals(mAnswerWord)) {
            score ++;
            txtShow.setText(Integer.toString(score) + " คะแนน");
        }
        //ShowScore

        checkRound();
    }
}
