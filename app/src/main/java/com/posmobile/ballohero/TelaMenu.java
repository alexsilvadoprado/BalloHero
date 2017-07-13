package com.posmobile.ballohero;

import com.posmobile.ballohero.AndGraph.AGGameManager;
import com.posmobile.ballohero.AndGraph.AGInputManager;
import com.posmobile.ballohero.AndGraph.AGScene;
import com.posmobile.ballohero.AndGraph.AGScreenManager;
import com.posmobile.ballohero.AndGraph.AGSprite;

public class TelaMenu extends AGScene
{

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
        btn_play = createSprite(R.mipmap.play_button, 1, 1);
        btn_play.setScreenPercent(70, 10);
        btn_play.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btn_play.vrPosition.setY(AGScreenManager.iScreenHeight - AGScreenManager.iScreenHeight / 3);

        btn_sobre = createSprite(R.mipmap.play_button, 1, 1);
        btn_sobre.setScreenPercent(70, 10);
        btn_sobre.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btn_sobre.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        btn_sair = createSprite(R.mipmap.play_button, 1, 1);
        btn_sair.setScreenPercent(70, 10);
        btn_sair.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btn_sair.vrPosition.setY(AGScreenManager.iScreenHeight / 3);
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
        }
    }
}
