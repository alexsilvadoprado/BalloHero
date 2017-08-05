package com.posmobile.ballohero;

import com.posmobile.ballohero.AndGraph.AGGameManager;
import com.posmobile.ballohero.AndGraph.AGInputManager;
import com.posmobile.ballohero.AndGraph.AGScene;
import com.posmobile.ballohero.AndGraph.AGScreenManager;
import com.posmobile.ballohero.AndGraph.AGSprite;

public class TelaSobre extends AGScene
{
    AGSprite plano_fundo = null;
    AGSprite arte_game = null;
    AGSprite creditos = null;
    AGSprite btn_voltar = null;

    TelaSobre(AGGameManager vrManager)
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

        creditos = createSprite(R.mipmap.sobre, 1, 1);
        creditos.setScreenPercent(100, 40);
        creditos.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        creditos.vrPosition.setY((arte_game.vrPosition.fY - arte_game.getSpriteHeight() / 2)
                            - creditos.getSpriteHeight() / 2);

        btn_voltar = createSprite(R.mipmap.btn_voltar, 1, 1);
        btn_voltar.setScreenPercent(20, 10);
        btn_voltar.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btn_voltar.vrPosition.setY((creditos.vrPosition.fY - creditos.getSpriteHeight() / 2)
                        - btn_voltar.getSpriteHeight());
    }

    @Override
    public void restart() {}

    @Override
    public void stop() {}

    @Override
    public void loop()
    {
        if(AGInputManager.vrTouchEvents.screenClicked() ||
                AGInputManager.vrTouchEvents.backButtonClicked())
        {
            vrGameManager.setCurrentScene(1);
            return;
        }
    }
}
