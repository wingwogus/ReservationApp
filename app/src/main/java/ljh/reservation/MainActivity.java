package ljh.reservation;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout divRes;
    AutoCompleteTextView actv;
    ViewFlipper vf;
    Button btnShow, btnPrev, btnNext;
    TimePicker tp;
    String movieDay;
    int hour, min;
    TextView result;
    ImageView poster1, poster2, poster3;
    String movieName;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        divRes = findViewById(R.id.divRes);
        actv = findViewById(R.id.actv);
        vf = findViewById(R.id.poster);
        btnShow = findViewById(R.id.btnShow);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        tp = findViewById(R.id.timePicker);
        result = findViewById(R.id.result);
        poster1 = findViewById(R.id.poster1);
        poster2 = findViewById(R.id.poster2);
        poster3 = findViewById(R.id.poster3);

        //상영 날짜 지정
        movieDay = "11월23일";

        //영화 목록 배열
        String[] movies = {movieDay + ": 베놈", movieDay + ": 청설", movieDay + ": 비긴어게인"};

        ArrayAdapter<String> movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, movies);
        actv.setAdapter(movieAdapter);

        result.setTextColor(Color.GREEN);

        //포스트 보기 버튼 클릭 시 예약 체크 메소드
        btnShow.setOnClickListener(e -> {
            if (checkReservation(actv.getText().toString())) {
                showPoster();
            } else {
                divRes.setVisibility(View.INVISIBLE);
            }
        });

        //이전 및 다음 버튼 리스너 설정
        btnPrev.setOnClickListener(e -> vf.showPrevious());

        btnNext.setOnClickListener(e -> vf.showNext());

        //타임피커 시간 병동 시 LocalTime과  DateTimeFormatter를 사용해 시와 분을 항상 2자리로 출력
        tp.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            hour = hourOfDay;
            min = minute;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시mm분");
            String date = LocalTime.of(hour, min).format(formatter);
            result.setText(movieName + ": " + movieDay +" " + date + "으로 예약되었습니다.");
        });
    }

    //예약 체크 메소드
    public boolean checkReservation(String s) {
        //텍스트를 :으로 구별하여 토큰으로 나눔
        StringTokenizer tk = new StringTokenizer(s, ":");
        try {
            //앞 토큰은 날짜, 뒤 토큰은 영화 이름
            String inputMovieDay = tk.nextToken();
            movieName = tk.nextToken().trim();

            //만약 날짜가 지정한 날짜와 같지 않다면 메세지 출력 및 false 리턴
            //만약 영화 이름이 영화 목록에 없다면 메세지 출력 및 false 리턴
            if (!inputMovieDay.equals(movieDay)) {
                Toast.makeText(this, "해당 날짜에는 영화를 상영하지 않습니다", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!(movieName.equals("베놈") || movieName.equals("청설") || movieName.equals("비긴어게인"))) {
                Toast.makeText(this, "올바른 영화 이름을 적어주세요", Toast.LENGTH_SHORT).show();
                return false;
            }

            //모든 게 정상이면 true 리턴
            return true;
        } catch (NoSuchElementException e) {
            //토큰이 나눠지지 않았을 때 메세지 출력
            Toast.makeText(this, "날짜와 공연을 콜론(:)으로 구별하여 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //선택한 영화에 맞는 포스터를 보여주는 메소드
    public void showPoster() {
        divRes.setVisibility(View.VISIBLE);
        switch (movieName) {
            case "베놈":
                poster1.setImageResource(R.drawable.venom1);
                poster2.setImageResource(R.drawable.venom2);
                poster3.setImageResource(R.drawable.venom3);
                break;
            case "청설":
                poster1.setImageResource(R.drawable.chung1);
                poster2.setImageResource(R.drawable.chung2);
                poster3.setImageResource(R.drawable.chung3);
                break;
            case "비긴어게인":
                poster1.setImageResource(R.drawable.begin1);
                poster2.setImageResource(R.drawable.begin2);
                poster3.setImageResource(R.drawable.begin3);
                break;
        }
    }
}