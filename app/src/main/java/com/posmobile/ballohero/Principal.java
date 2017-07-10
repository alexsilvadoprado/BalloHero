package com.posmobile.ballohero;

import android.os.Bundle;

import com.posmobile.ballohero.AndGraph.AGActivityGame;

public class Principal extends AGActivityGame
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(this, false);

        TelaSplash tela_splash = new TelaSplash(this.vrManager);

        vrManager.addScene(tela_splash);
    }
}
