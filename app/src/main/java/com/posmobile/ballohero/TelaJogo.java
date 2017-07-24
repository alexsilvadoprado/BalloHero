package com.posmobile.ballohero;

import com.posmobile.ballohero.AndGraph.AGGameManager;
import com.posmobile.ballohero.AndGraph.AGScene;
import com.posmobile.ballohero.AndGraph.AGScreenManager;
import com.posmobile.ballohero.AndGraph.AGSprite;

public class TelaJogo extends AGScene
{

    AGSprite balao = null;

    TelaJogo(AGGameManager vrManager)
    {
        super(vrManager);
    }

    @Override
    public void init()
    {
        createSprite(R.mipmap.sprite_balao, 1, 4).bVisible = false;
        balao = createSprite(R.mipmap.sprite_balao, 4, 1);
        balao.setScreenPercent(20, 20);
        balao.addAnimation(10, false, 0, 3);
        balao.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        balao.vrPosition.setY(balao.getSpriteHeight());
    }

    @Override
    public void restart() {}

    @Override
    public void stop() {}

    @Override
    public void loop() {}
}
