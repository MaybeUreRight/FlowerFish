package com.bagelplay.gameset.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.bagelplay.gameset.R;
import com.bagelplay.gameset.player.IMPlayListener;
import com.bagelplay.gameset.player.IMPlayer;
import com.bagelplay.gameset.player.MPlayer;
import com.bagelplay.gameset.player.MPlayerException;
import com.bagelplay.gameset.player.MinimalDisplay;
import com.bagelplay.gameset.utils.AppManager;
import com.bagelplay.gameset.utils.DimenUtil;
import com.bagelplay.gameset.utils.LogUtils;
import com.bagelplay.gameset.utils.SoundUtil;
import com.bagelplay.sdk.cocos.SDKCocosManager;

public class Main2ActivityBackup extends Activity implements View.OnClickListener {
    private ViewFlipper main2_rootview;

    private ImageView main2Hamburger;
    private ImageView main2Salad;
    private RelativeLayout languageContainer;
    private ImageView main2Exit;
    private RelativeLayout main2_videoview_container;
    private SurfaceView surfaceView;
    private MPlayer player;
    private int currentSection;

    private LinearLayout main2_second;

    private boolean click;

    private Handler handler;
    private boolean enteragain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_main2_backup);
        SDKCocosManager.getInstance(this).addWindowCallBack(this);
        AppManager.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        handler = new Handler();
        main2_rootview = findViewById(R.id.main2_rootview);

        main2_videoview_container = (RelativeLayout) main2_rootview.findViewById(R.id.main2_videoview_container);
        surfaceView = main2_videoview_container.findViewById(R.id.main2_surfaceview);
        main2_videoview_container.findViewById(R.id.main2_skip).setOnClickListener(this);

        main2_second = main2_rootview.findViewById(R.id.main2_second);
        main2Hamburger = (ImageView) main2_second.findViewById(R.id.main2_hamburger);
        main2Salad = (ImageView) main2_second.findViewById(R.id.main2_salad);
        languageContainer = (RelativeLayout) main2_second.findViewById(R.id.language_container);
        main2Exit = (ImageView) main2_second.findViewById(R.id.main2_exit);

        //是否再次进入该界面
        enteragain = getIntent().getBooleanExtra("enteragain", false);

        findViewById(R.id.language_en).setOnClickListener(this);
        findViewById(R.id.language_zh).setOnClickListener(this);
        main2Hamburger.setOnClickListener(this);
        main2Salad.setOnClickListener(this);
        main2Exit.setOnClickListener(this);

        currentSection = 1;


        if (!enteragain) {//否，这是第一次进入该界面
            click = false;

            String mUrl = "android.resource://" + getPackageName() + "/" + R.raw.game_introduction;
            if (mUrl.length() > 0) {
                try {
                    player = new MPlayer();
                    player.setSource(Main2ActivityBackup.this, mUrl);
                    player.setDisplay(new MinimalDisplay(surfaceView));
                    player.play();
                } catch (MPlayerException e) {
                    e.printStackTrace();
                }
            }
        } else {//再次进入该界面
            if (main2_videoview_container.getVisibility() != View.GONE) {
                main2_videoview_container.setVisibility(View.GONE);
            }
            main2_rootview.showNext();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    click = true;
                    SoundUtil.getInstance(Main2ActivityBackup.this).startPlaySound(R.raw.continue_or_exit_game);
                }
            }, 1500);
        }
    }

    public void next() {
        main2_rootview.setInAnimation(AnimationUtils.loadAnimation(Main2ActivityBackup.this, R.anim.fade_in));
        main2_rootview.setOutAnimation(AnimationUtils.loadAnimation(Main2ActivityBackup.this, R.anim.fade_out));

        main2_rootview.showNext();
        handler.postDelayed(() -> {
            click = false;
            SoundUtil.getInstance(Main2ActivityBackup.this).startPlaySoundWithListener(R.raw.hamburger_or_salad, new SoundUtil.MediaPlayListener() {
                @Override
                public void onPlayerCompletion() {
                    click = true;
                }
            });
        }, 300);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.language_en:
                if (click) {
                    startActivity(new Intent(Main2ActivityBackup.this, EvaluationGame2Activity.class)
                            .putExtra("language", "en")
                            .putExtra("section", currentSection));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Main2ActivityBackup.this.finish();
                        }
                    }, 1000);
                }
                break;
            case R.id.language_zh:
                if (click) {
                    startActivity(new Intent(Main2ActivityBackup.this, EvaluationGame2Activity.class)
                            .putExtra("language", "zh")
                            .putExtra("section", currentSection));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Main2ActivityBackup.this.finish();
                        }
                    }, 1000);
                }
                break;
            case R.id.main2_hamburger:
                if (click) {
                    click = false;
                    LinearLayout parent = (LinearLayout) main2Hamburger.getParent();
                    parent.setGravity(Gravity.CENTER_HORIZONTAL);
                    languageContainer.setVisibility(View.VISIBLE);
                    main2Salad.setVisibility(View.GONE);
                    currentSection = 1;
                    SoundUtil.getInstance(Main2ActivityBackup.this).startPlaySoundWithListener(R.raw.select_language, new SoundUtil.MediaPlayListener() {
                        @Override
                        public void onPlayerCompletion() {
                            click = true;
                        }
                    });
                }
                break;
            case R.id.main2_salad:
                if (click) {
                    click = false;
                    LinearLayout parent = (LinearLayout) main2Hamburger.getParent();
                    parent.setGravity(Gravity.CENTER_HORIZONTAL);
                    main2Hamburger.setVisibility(View.GONE);
                    languageContainer.setVisibility(View.VISIBLE);
                    currentSection = 2;
                    SoundUtil.getInstance(Main2ActivityBackup.this).startPlaySoundWithListener(R.raw.select_language, new SoundUtil.MediaPlayListener() {
                        @Override
                        public void onPlayerCompletion() {
                            click = true;
                        }
                    });
                }
                break;
            case R.id.main2_exit:
                if (click) {
                    if (languageContainer.getVisibility() != View.GONE) {
                        switch (currentSection) {
                            case 1:
                                main2Salad.setVisibility(View.VISIBLE);

                                break;
                            case 2:
                                main2Hamburger.setVisibility(View.VISIBLE);
                                break;
                        }

                        LinearLayout parent = (LinearLayout) main2Hamburger.getParent();
                        parent.setGravity(Gravity.LEFT);
                        languageContainer.setVisibility(View.GONE);
                    } else {//开始界面:提示是否退出应用
                        AppManager.getInstance().AppExit(this);
                    }
                }
                break;
            case R.id.main2_skip:
                player.pause();
                next();
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        setLanguageImageViewWidthAndHeight();
    }

    private void setLanguageImageViewWidthAndHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        //187*128
        double temHei = screenHeight * 1.0 * 2 / (9*1.5);
        double temWid = temHei * 187 / 128;

//        LogUtils.lb("temWid = " + temWid);
//        LogUtils.lb("temHei = " + temHei);
//        LogUtils.lb("languageContainer.getWidth() = " + languageContainer.getWidth());
//        LogUtils.lb("languageContainer.getHeight() = " + languageContainer.getHeight());

        RelativeLayout.LayoutParams paramsZH = new RelativeLayout.LayoutParams((int) temWid, (int) temHei);
        paramsZH.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        paramsZH.setMargins(DimenUtil.dip2px(this,20),0,0,0);
        languageContainer.findViewById(R.id.language_en).setLayoutParams(paramsZH);

        RelativeLayout.LayoutParams paramsEN = new RelativeLayout.LayoutParams((int) temWid, (int) temHei);
        paramsEN.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsEN.setMargins(0,0,DimenUtil.dip2px(this,20),0);
        languageContainer.findViewById(R.id.language_zh).setLayoutParams(paramsEN);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (SDKCocosManager.getInstance().dispatchKeyEvent(event))
            return true;
        return super.dispatchKeyEvent(event);
    }

    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        if (SDKCocosManager.getInstance().dispatchGenericMotionEvent(ev))
            return true;
        return super.dispatchGenericMotionEvent(ev);

    }

    protected void onStop() {
        super.onStop();
        SDKCocosManager.getInstance().onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        SDKCocosManager.getInstance().onPause();
        SoundUtil.getInstance(Main2ActivityBackup.this).stopPlaySound();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        SDKCocosManager.getInstance().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
        SDKCocosManager.getInstance().removeWindowCallBack(this);
    }

    private long mBackTime;

    @Override
    public void onBackPressed() {
        // 连续点击返回退出
        long nowTime = SystemClock.elapsedRealtime();
        long diff = nowTime - mBackTime;
        if (diff >= 500) {
            mBackTime = nowTime;
        } else {
            AppManager.getInstance().AppExit(Main2ActivityBackup.this);
        }
    }
}
