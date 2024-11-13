package ljh.reservation;

import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        movieDay = "11월23일";

        String[] movies = {movieDay + ": 베놈", movieDay + ": 청설", movieDay + ": 비긴어게인"};

        ArrayAdapter<String> movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, movies);
        actv.setAdapter(movieAdapter);

        result.setTextColor(Color.GREEN);

        btnShow.setOnClickListener(e -> {
            if (checkReservation(actv.getText().toString())) {
                showPoster();
            } else {
                divRes.setVisibility(View.INVISIBLE);
            }
        });

        btnPrev.setOnClickListener(e -> {
            vf.showPrevious();
        });

        btnNext.setOnClickListener(e -> {
            vf.showNext();
        });

        tp.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            hour = hourOfDay;
            min = minute;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시mm분");
            String date = LocalTime.of(hour, min).format(formatter);
            result.setText(movieName + ": " + movieDay +" " + date + "으로 예약되었습니다.");
        });
    }

    public boolean checkReservation(String s) {
        StringTokenizer tk = new StringTokenizer(s, ":");
        try {
            String inputMovieDay = tk.nextToken();
            movieName = tk.nextToken().trim();
            if (!inputMovieDay.equals(movieDay)) {
                Toast.makeText(this, "해당 날짜에 상영되는 영화가 없습니다", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!(movieName.equals("베놈") || movieName.equals("청설") || movieName.equals("비긴어게인"))) {
                Toast.makeText(this, "올바른 영화 이름을 적어주세요", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (NoSuchElementException e) {
            Toast.makeText(this, "날짜와 공연을 콜론(:)으로 구별하여 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

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