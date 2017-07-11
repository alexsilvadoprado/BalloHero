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
        TelaMenu tela_menu = new TelaMenu(this.vrManager);
        TelaJogo tela_jogo = new TelaJogo(this.vrManager);
        TelaSobre tela_sobre = new TelaSobre(this.vrManager);

        vrManager.addScene(tela_splash);
        vrManager.addScene(tela_menu);
        vrManager.addScene(tela_jogo);
        vrManager.addScene(tela_sobre);
    }
}
