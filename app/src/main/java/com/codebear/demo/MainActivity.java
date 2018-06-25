package com.codebear.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cb.ratingbar.CBRatingBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CBRatingBar cbRatingBar;
    CheckBox cbShowStroke;
    CheckBox cbUseGradient;
    CheckBox cbTouch;
    RadioGroup rgPath;
    RadioGroup rgDir;
    TextView showTouch;
    SeekBar progress;
    SeekBar count;
    SeekBar space;
    SeekBar size;
    SeekBar strokeColor;
    SeekBar bgColor;
    SeekBar coverColor;
    SeekBar startColor;
    SeekBar endColor;
    View vStrokeColor;
    View vBgColor;
    View vCoverColor;
    View vStartColor;
    View vEndColor;

    int[] colors = {Color.YELLOW, Color.RED, Color.BLUE, Color.WHITE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color
            .DKGRAY, Color.GRAY, Color.LTGRAY, Color.LTGRAY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setListener();
        random();
    }

    private void findViewById() {
        cbRatingBar = (CBRatingBar) findViewById(R.id.rating_bar);
        cbShowStroke = (CheckBox) findViewById(R.id.cb_show_stroke);
        cbUseGradient = (CheckBox) findViewById(R.id.cb_use_gradient);
        cbTouch = (CheckBox) findViewById(R.id.cb_touch);
        rgPath = (RadioGroup) findViewById(R.id.rg_path);
        rgDir = (RadioGroup) findViewById(R.id.rg_dir);
        showTouch = (TextView) findViewById(R.id.show_touch);
        progress = (SeekBar) findViewById(R.id.progress);
        count = (SeekBar) findViewById(R.id.count);
        space = (SeekBar) findViewById(R.id.space);
        size = (SeekBar) findViewById(R.id.size);
        strokeColor = (SeekBar) findViewById(R.id.stroke_color);
        bgColor = (SeekBar) findViewById(R.id.bg_color);
        coverColor = (SeekBar) findViewById(R.id.cover_color);
        startColor = (SeekBar) findViewById(R.id.start_color);
        endColor = (SeekBar) findViewById(R.id.end_color);
        vStrokeColor = findViewById(R.id.v_stroke_color);
        vBgColor = findViewById(R.id.v_bg_color);
        vCoverColor = findViewById(R.id.v_cover_color);
        vStartColor = findViewById(R.id.v_start_color);
        vEndColor = findViewById(R.id.v_end_color);
    }

    private void setListener() {
        cbRatingBar.setOnStarTouchListener(new CBRatingBar.OnStarTouchListener() {
            @Override
            public void onStarTouch(int touchCount) {
                showTouch.setText(touchCount + "个星星被填充");
            }
        });
        cbShowStroke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbRatingBar.setShowStroke(b);
                strokeColor.setEnabled(b);
            }
        });
        cbUseGradient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbRatingBar.setUseGradient(b);
                startColor.setEnabled(b);
                endColor.setEnabled(b);
                coverColor.setEnabled(!b);
            }
        });
        cbTouch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbRatingBar.setCanTouch(b);
                if (b) {
                    showTouch.setHint("可点击");
                } else {
                    showTouch.setHint("不能点击");
                }
                if (!b) {
                    showTouch.setText("");
                }
            }
        });
        rgPath.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_use_def0:
                        cbRatingBar.setDefaultPathData();
                        break;
                    case R.id.rb_use_def1:
                        cbRatingBar.setPathDataId(R.string.heart);
                        break;
                    case R.id.rb_use_def2:
                        cbRatingBar.setPathDataId(R.string.bird);
                        break;
                    case R.id.rb_use_def3:
                        cbRatingBar.setPathData(getResources().getString(R.string.pig));
                        break;
                }
            }
        });
        rgDir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_dir0:
                        cbRatingBar.setCoverDir(CBRatingBar.CoverDir.leftToRight);
                        break;
                    case R.id.rb_dir1:
                        cbRatingBar.setCoverDir(CBRatingBar.CoverDir.rightToLeft);
                        break;
                    case R.id.rb_dir2:
                        cbRatingBar.setCoverDir(CBRatingBar.CoverDir.topToBottom);
                        break;
                    case R.id.rb_dir3:
                        cbRatingBar.setCoverDir(CBRatingBar.CoverDir.bottomToTop);
                        break;
                }
            }
        });
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cbRatingBar.setStarProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        count.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cbRatingBar.setStarCount(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        space.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cbRatingBar.setStarSpace(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cbRatingBar.setStarSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        strokeColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cbRatingBar.setStarStrokeColor(colors[i]);
                vStrokeColor.setBackgroundColor(colors[i]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        bgColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cbRatingBar.setStarFillColor(colors[i]);
                vBgColor.setBackgroundColor(colors[i]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        coverColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cbRatingBar.setStarCoverColor(colors[i]);
                vCoverColor.setBackgroundColor(colors[i]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        startColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cbRatingBar.setStartColor(colors[i]);
                vStartColor.setBackgroundColor(colors[i]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        endColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cbRatingBar.setEndColor(colors[i]);
                vEndColor.setBackgroundColor(colors[i]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void random() {

        Random random = new Random();
        int i = random.nextInt(progress.getMax());
        progress.setProgress(i + 1);

        i = random.nextInt(count.getMax());
        count.setProgress(i + 1);

        i = random.nextInt(space.getMax());
        space.setProgress(i + 1);

        i = random.nextInt(size.getMax());
        size.setProgress(i + 1);

        i = random.nextInt(strokeColor.getMax());
        strokeColor.setProgress(i + 1);

        i = random.nextInt(bgColor.getMax());
        bgColor.setProgress(i + 1);

        i = random.nextInt(coverColor.getMax());
        coverColor.setProgress(i + 1);

        i = random.nextInt(startColor.getMax());
        startColor.setProgress(i + 1);

        i = random.nextInt(endColor.getMax());
        endColor.setProgress(i + 1);

        cbShowStroke.setChecked(true);
        cbUseGradient.setChecked(false);
        cbTouch.setChecked(false);
        startColor.setEnabled(false);
        endColor.setEnabled(false);
        coverColor.setEnabled(true);
        strokeColor.setEnabled(true);
    }
}
