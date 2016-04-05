package com.friday.class9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements Transition.TransitionListener {

    Scene[] scenes;
    int current;
    ViewGroup rootAnimation, main;
    Transition animation;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootAnimation = (ViewGroup)findViewById(R.id.padre);
        main = (ViewGroup)findViewById(R.id.animationMain);
        button = (Button)findViewById(R.id.button);

        scenes = new Scene[2];
        scenes[0] = Scene.getSceneForLayout(main, R.layout.scene1, this);
        scenes[1] = Scene.getSceneForLayout(main, R.layout.scene2, this);
        current = 0;

        animation = TransitionInflater.from(this).inflateTransition(R.transition.mixed);
        animation.addListener(this);
    }

    public void cambioDeEscena(View v){

        TransitionManager.go(scenes[current], animation);
        current++;
        current %= scenes.length;
    }

    public void sacarBoton(View v){

        Log.d("SACAR BOTON", "****");
        Transition explosion = new Slide();
        explosion.setDuration(1000);
        TransitionManager.beginDelayedTransition(rootAnimation, explosion);

        rootAnimation.removeView(button);
    }

    public void cambiarActividad(View v){

        Intent i = new Intent(this, Camara.class);
        startActivity(i);
    }

    @Override
    public void onTransitionStart(Transition transition) {

        Log.d("TRANSITION", "START");
    }

    @Override
    public void onTransitionEnd(Transition transition) {
        Log.d("TRANSITION", "END");
    }

    @Override
    public void onTransitionCancel(Transition transition) {

    }

    @Override
    public void onTransitionPause(Transition transition) {

    }

    @Override
    public void onTransitionResume(Transition transition) {

    }
}
