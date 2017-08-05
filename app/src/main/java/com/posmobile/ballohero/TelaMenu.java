package com.posmobile.ballohero;

import com.posmobile.ballohero.AndGraph.AGGameManager;
import com.posmobile.ballohero.AndGraph.AGInputManager;
import com.posmobile.ballohero.AndGraph.AGScene;
import com.posmobile.ballohero.AndGraph.AGScreenManager;
import com.posmobile.ballohero.AndGraph.AGSoundManager;
import com.posmobile.ballohero.AndGraph.AGSprite;

public class TelaMenu extends AGScene
{
    // Declaracao de variáveis
    AGSprite plano_fundo = null;
    AGSprite arte_game = null;
    AGSprite btn_play = null;
    AGSprite btn_sobre = null;
    AGSprite btn_sair = null;

    TelaMenu(AGGameManager vrManager)
    {
        super(vrManager);
    }

    @Override
    public void init()
    {
        plano_fundo = createSprite(R.mipmap.fundo, 1, 1);
        plano_fundo.setScreenPercent(100, 100);
        plano_fundo.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        plano_fundo.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        arte_game = createSprite(R.mipmap.apresentacao, 1, 1);
        arte_game.setScreenPercent(80, 20);
        arte_game.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        arte_game.vrPosition.setY(AGScreenManager.iScreenHeight - arte_game.getSpriteHeight());

        btn_play = createSprite(R.mipmap.botao_jogar, 1, 1);
        btn_play.setScreenPercent(70, 10);
        btn_play.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btn_play.vrPosition.setY((arte_game.vrPosition.fY - arte_game.getSpriteHeight()));

        btn_sobre = createSprite(R.mipmap.botao_sobre, 1, 1);
        btn_sobre.setScreenPercent(70, 10);
        btn_sobre.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btn_sobre.vrPosition.setY(btn_play.vrPosition.fY - btn_sobre.getSpriteHeight() * 1.5f);

        btn_sair = createSprite(R.mipmap.botao_sair, 1, 1);
        btn_sair.setScreenPercent(70, 10);
        btn_sair.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btn_sair.vrPosition.setY(btn_sobre.vrPosition.fY - btn_sair.getSpriteHeight() * 1.5f);

        AGSoundManager.vrMusic.loadMusic("musica.mp3", true);
        AGSoundManager.vrMusic.play();
    }

    @Override
    public void restart() {}

    @Override
    public void stop() {}

    @Override
    public void loop()
    {
        if(AGInputManager.vrTouchEvents.screenClicked())
        {
            if(btn_play.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
                vrGameManager.setCurrentScene(2);
                return;
            }

            if(btn_sobre.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
                vrGameManager.setCurrentScene(3);
                return;
            }

            if(btn_sair.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
                vrGameManager.vrActivity.finish();
                return;
            }
        }
    }
}
