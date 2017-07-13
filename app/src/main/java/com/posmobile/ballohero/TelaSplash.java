package com.posmobile.ballohero;

import com.posmobile.ballohero.AndGraph.AGGameManager;
import com.posmobile.ballohero.AndGraph.AGScene;
import com.posmobile.ballohero.AndGraph.AGScreenManager;
import com.posmobile.ballohero.AndGraph.AGSprite;

public class TelaSplash extends AGScene
{

    AGSprite logo_apresentacao = null;
    int estado_fade = 0;

    TelaSplash(AGGameManager vrManager)
    {
        super(vrManager);
    }

    @Override
    public void init()
    {
        this.setSceneBackgroundColor(1.0f, 1.0f, 1.0f);
        logo_apresentacao = createSprite(R.mipmap.logo_apresentacao, 1, 1);
        logo_apresentacao.setScreenPercent(80, 12);
        logo_apresentacao.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        logo_apresentacao.vrPosition.setY(AGScreenManager.iScreenHeight / 2);
        logo_apresentacao.fadeIn(2000);
    }

    @Override
    public void restart() {}

    @Override
    public void stop() {}

    @Override
    public void loop()
    {
        if(estado_fade == 0)
        {
            if(logo_apresentacao.fadeEnded())
            {
                estado_fade = 1;
                logo_apresentacao.fadeOut(3000);
            }
        } else
        {
            if(logo_apresentacao.fadeEnded())
            {
                vrGameManager.setCurrentScene(1);
                return;
            }
        }
    }
}
