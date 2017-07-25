package com.posmobile.ballohero;

import com.posmobile.ballohero.AndGraph.AGGameManager;
import com.posmobile.ballohero.AndGraph.AGScene;
import com.posmobile.ballohero.AndGraph.AGScreenManager;
import com.posmobile.ballohero.AndGraph.AGSprite;

import java.util.List;

public class TelaJogo extends AGScene
{

    AGSprite balao = null;
    AGSprite emoji_do_mal = null;

    AGSprite mamona = null;
    AGSprite prego = null;
    AGSprite dardo = null;

    List<AGSprite> vetor_emojis = null;
    List<AGSprite> vetor_mamonas = null;
    List<AGSprite> vetor_pregos = null;
    List<AGSprite> vetor_dardos = null;

    int[] vetor_cod_inimigos = new int[4];
    int prox_inimigo = 0;

    TelaJogo(AGGameManager vrManager)
    {
        super(vrManager);
    }

    @Override
    public void init()
    {
        createSprite(R.mipmap.sprite_balao, 4, 1).bVisible = false;
        createSprite(R.mipmap.sprite_emoji, 4, 1).bVisible = false;

        vetor_cod_inimigos[0] = R.mipmap.sprite_emoji;
        vetor_cod_inimigos[1] = R.mipmap.mamona;
        vetor_cod_inimigos[2] = R.mipmap.prego;
        vetor_cod_inimigos[3] = R.mipmap.dardo;

//        balao = createSprite(R.mipmap.sprite_balao, 1, 4);
//        balao.setScreenPercent(20, 20);
//        balao.addAnimation(10, false, 0, 1);
//        balao.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
//        balao.vrPosition.setY(balao.getSpriteHeight());

        emoji_do_mal = createSprite(R.mipmap.sprite_emoji, 4, 1);
        emoji_do_mal.setScreenPercent(15, 8);
        emoji_do_mal.addAnimation(8, false, 0, 3);
        emoji_do_mal.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        emoji_do_mal.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        mamona = createSprite(R.mipmap.mamona, 1, 1);
        mamona.setScreenPercent(15, 8);
        mamona.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        mamona.vrPosition.setY(emoji_do_mal.vrPosition.fY - emoji_do_mal.getSpriteHeight());

        prego = createSprite(R.mipmap.prego, 1, 1);
        prego.setScreenPercent(8, 8);
        prego.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        prego.vrPosition.setY(mamona.vrPosition.fY - mamona.getSpriteHeight());

        dardo = createSprite(R.mipmap.dardo, 1, 1);
        dardo.setScreenPercent(8, 8);
        dardo.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        dardo.vrPosition.setY(prego.vrPosition.fY - prego.getSpriteHeight());
    }

    @Override
    public void restart() {}

    @Override
    public void stop() {}

    @Override
    public void loop() {}

    private void criaInimigo()
    {
        AGSprite inimigo = createSprite(vetor_cod_inimigos[prox_inimigo], prox_inimigo == 0 ? 4 : 1, 1);
        if(prox_inimigo == 0)
        {
            inimigo.setScreenPercent(15, 8);
            inimigo.addAnimation(8, false, 0, 3);
//            inimigo.vrPosition.setX();
            inimigo.vrPosition.setY(AGScreenManager.iScreenHeight + inimigo.getSpriteHeight());
        }
        else if(prox_inimigo == 1){}
        else if(prox_inimigo == 2){}
        else if(prox_inimigo == 3){}
    }
}
