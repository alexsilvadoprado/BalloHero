package com.posmobile.ballohero;

import com.posmobile.ballohero.AndGraph.AGGameManager;
import com.posmobile.ballohero.AndGraph.AGInputManager;
import com.posmobile.ballohero.AndGraph.AGScene;
import com.posmobile.ballohero.AndGraph.AGScreenManager;
import com.posmobile.ballohero.AndGraph.AGSprite;
import com.posmobile.ballohero.AndGraph.AGTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelaJogo extends AGScene
{

    AGSprite[] fundo = new AGSprite[2];
    AGSprite barra_superior = null;
    AGSprite[] placar = new AGSprite[6];
    AGSprite balao = null;

    List<AGSprite> vetor_emojis = null;
    List<AGSprite> vetor_mamonas = null;
    List<AGSprite> vetor_pregos = null;
    List<AGSprite> vetor_dardos = null;

    int[] vetor_cod_inimigos = new int[4];
    int prox_inimigo = 0;

    float[] vetor_pos_inimigos = new float[5];

    AGTimer tempo_geracao_inimigos = null;

    int pontuacao = 0;
    int tempo_pontuacao = 0;
    boolean game_over = false;

    TelaJogo(AGGameManager vrManager)
    {
        super(vrManager);
    }

    @Override
    public void init()
    {
        createSprite(R.mipmap.sprite_balao, 4, 2).bVisible = false;
        createSprite(R.mipmap.sprite_emoji, 4, 1).bVisible = false;
        createSprite(R.mipmap.barra_superior, 1, 1).bVisible = false;

        for(int i = 0; i < fundo.length; i++)
        {
            fundo[i] = createSprite(R.mipmap.fundo, 1, 1);
            fundo[i].setScreenPercent(100, 100);
            fundo[i].vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        }
        fundo[0].vrPosition.setY(AGScreenManager.iScreenHeight / 2);
        fundo[1].vrPosition.setY(AGScreenManager.iScreenHeight + fundo[1].getSpriteHeight() / 2);

        barra_superior = createSprite(R.mipmap.barra_superior, 1, 1);
        barra_superior.setScreenPercent(100, 10);
        barra_superior.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        barra_superior.vrPosition.setY(AGScreenManager.iScreenHeight - barra_superior.getSpriteHeight() / 2);
        barra_superior.bAutoRender = false;

        vetor_cod_inimigos[0] = R.mipmap.sprite_emoji;
        vetor_cod_inimigos[1] = R.mipmap.mamona;
        vetor_cod_inimigos[2] = R.mipmap.prego;
        vetor_cod_inimigos[3] = R.mipmap.dardo;

        vetor_pos_inimigos[0] = AGScreenManager.iScreenWidth / 2;
        vetor_pos_inimigos[1] = AGScreenManager.iScreenWidth / 3.2f;
        vetor_pos_inimigos[2] = AGScreenManager.iScreenWidth / 8;
        vetor_pos_inimigos[3] = AGScreenManager.iScreenWidth - AGScreenManager.iScreenWidth / 3.2f;
        vetor_pos_inimigos[4] = AGScreenManager.iScreenWidth - AGScreenManager.iScreenWidth / 8;

        vetor_emojis = new ArrayList<AGSprite>();
        vetor_mamonas = new ArrayList<AGSprite>();
        vetor_pregos = new ArrayList<AGSprite>();
        vetor_dardos = new ArrayList<AGSprite>();

        tempo_geracao_inimigos = new AGTimer(1000);

        int multiplicador = 1;
        for(int pos = 0; pos < placar.length; pos++)
        {
            placar[pos] = createSprite(R.mipmap.fonte, 4, 4);
            placar[pos].setScreenPercent(10, 10);
            placar[pos].vrPosition.fY = barra_superior.vrPosition.fY;
            placar[pos].vrPosition.fX = 20 + multiplicador * placar[pos].getSpriteWidth();
            placar[pos].bAutoRender = false;
            multiplicador++;

            for(int i = 0; i < 10; i++)
            {
                placar[pos].addAnimation(1, true, i);
            }
        }

        balao = createSprite(R.mipmap.sprite_balao, 4, 2);
        balao.setScreenPercent(20, 20);
        balao.addAnimation(10, true, 0, 3);
        balao.addAnimation(15, false, 4, 7);
        balao.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        balao.vrPosition.setY(balao.getSpriteHeight() / 2);
    }

    @Override
    public void render()
    {
        super.render();
        barra_superior.render();

        for(AGSprite digito : placar)
        {
            digito.render();
        }
    }

    @Override
    public void restart() {}

    @Override
    public void stop() {}

    @Override
    public void loop()
    {
        atualizaFundo();

        criaInimigo();

        atualizaInimigos();

        verificaToque();

        verificaColisao();

        atualizaPlacar();
    }

    private void criaInimigo()
    {
        tempo_geracao_inimigos.update();

        if(!tempo_geracao_inimigos.isTimeEnded())
        {
            return;
        }

        tempo_geracao_inimigos.restart();
        if (prox_inimigo == 0)
        {
            criaEmoji();
        } else if (prox_inimigo == 1)
        {
            criaMamona();
        } else if (prox_inimigo == 2)
        {
            criaPrego();
        } else if (prox_inimigo == 3)
        {
            criaDardo();
        }
    }

    private void criaEmoji()
    {
        for(AGSprite emoji : vetor_emojis)
        {
            if(emoji.bRecycled)
            {
                emoji.bRecycled = false;
                emoji.bVisible = true;
                emoji.vrPosition.setXY(vetor_pos_inimigos[new Random().nextInt(5)],
                        (AGScreenManager.iScreenHeight - barra_superior.getSpriteHeight() / 2) + emoji.getSpriteHeight());

                prox_inimigo = 1;

                return;
            }
        }

        AGSprite inimigo = createSprite(vetor_cod_inimigos[prox_inimigo], prox_inimigo == 0 ? 4 : 1, 1);
        inimigo.setScreenPercent(15, 8);
        inimigo.addAnimation(8, true, 0, 3);
        inimigo.vrPosition.setX(vetor_pos_inimigos[new Random().nextInt(5)]);
        inimigo.vrPosition.setY((AGScreenManager.iScreenHeight - barra_superior.getSpriteHeight() / 2) + inimigo.getSpriteHeight());
        vetor_emojis.add(inimigo);

        prox_inimigo = 1;
    }

    private void criaMamona()
    {
        for(AGSprite mamona : vetor_mamonas)
        {
            if(mamona.bRecycled)
            {
                mamona.bRecycled = false;
                mamona.bVisible = true;
                mamona.vrPosition.setXY(vetor_pos_inimigos[new Random().nextInt(5)],
                        (AGScreenManager.iScreenHeight - barra_superior.getSpriteHeight() / 2) + mamona.getSpriteHeight());

                prox_inimigo = 2;

                return;
            }
        }

        AGSprite inimigo = createSprite(vetor_cod_inimigos[prox_inimigo], prox_inimigo == 0 ? 4 : 1, 1);
        inimigo.setScreenPercent(15, 8);
        inimigo.vrPosition.setX(vetor_pos_inimigos[new Random().nextInt(5)]);
        inimigo.vrPosition.setY((AGScreenManager.iScreenHeight - barra_superior.getSpriteHeight() / 2) + inimigo.getSpriteHeight());
        vetor_mamonas.add(inimigo);

        prox_inimigo = 2;
    }

    private void criaPrego()
    {
        for(AGSprite prego : vetor_pregos)
        {
            if(prego.bRecycled)
            {
                prego.bRecycled = false;
                prego.bVisible = true;
                prego.vrPosition.setXY(vetor_pos_inimigos[new Random().nextInt(5)],
                        (AGScreenManager.iScreenHeight - barra_superior.getSpriteHeight() / 2) + prego.getSpriteHeight());

                prox_inimigo = 3;

                return;
            }
        }

        AGSprite inimigo = createSprite(vetor_cod_inimigos[prox_inimigo], prox_inimigo == 0 ? 4 : 1, 1);
        inimigo.setScreenPercent(4, 8);
        inimigo.vrPosition.setX(vetor_pos_inimigos[new Random().nextInt(5)]);
        inimigo.vrPosition.setY((AGScreenManager.iScreenHeight - barra_superior.getSpriteHeight() / 2) + inimigo.getSpriteHeight());
        vetor_pregos.add(inimigo);

        prox_inimigo = 3;
    }

    private void criaDardo()
    {
        for(AGSprite dardo : vetor_dardos)
        {
            if(dardo.bRecycled)
            {
                dardo.bRecycled = false;
                dardo.bVisible = true;
                dardo.vrPosition.setXY(vetor_pos_inimigos[new Random().nextInt(5)],
                        (AGScreenManager.iScreenHeight - barra_superior.getSpriteHeight() / 2) + dardo.getSpriteHeight());

                prox_inimigo = 0;

                return;
            }
        }

        AGSprite inimigo = createSprite(vetor_cod_inimigos[prox_inimigo], prox_inimigo == 0 ? 4 : 1, 1);
        inimigo.setScreenPercent(8, 8);
        inimigo.vrPosition.setX(vetor_pos_inimigos[new Random().nextInt(5)]);
        inimigo.vrPosition.setY((AGScreenManager.iScreenHeight - barra_superior.getSpriteHeight() / 2) + inimigo.getSpriteHeight());
        vetor_dardos.add(inimigo);

        prox_inimigo = 0;
    }

    private void atualizaInimigos()
    {
        for(AGSprite emoji : vetor_emojis)
        {
            emoji.vrPosition.fY -= 15;
            if(emoji.vrPosition.fY < ((emoji.getSpriteHeight() / 2) * -1))
            {
                if(emoji.bVisible)
                    tempo_pontuacao += 50;
                emoji.bRecycled = true;
                emoji.bVisible = false;
            }
        }

        for(AGSprite mamona : vetor_mamonas)
        {
            mamona.vrPosition.fY -= 15;
            if(mamona.vrPosition.fY < ((mamona.getSpriteHeight() / 2) * -1))
            {
                if(mamona.bVisible)
                    tempo_pontuacao += 50;
                mamona.bRecycled = true;
                mamona.bVisible = false;
            }
        }

        for(AGSprite prego : vetor_pregos)
        {
            prego.vrPosition.fY -= 15;
            if(prego.vrPosition.fY < ((prego.getSpriteHeight() / 2) * -1))
            {
                if(prego.bVisible)
                    tempo_pontuacao += 50;
                prego.bRecycled = true;
                prego.bVisible = false;
            }
        }

        for(AGSprite dardo : vetor_dardos)
        {
            dardo.vrPosition.fY -= 15;
            if(dardo.vrPosition.fY < ((dardo.getSpriteHeight() / 2) * -1))
            {
                if(dardo.bVisible)
                    tempo_pontuacao += 50;
                dardo.bRecycled = true;
                dardo.bVisible = false;
            }
        }
    }

    private void verificaToque()
    {
        if(AGInputManager.vrTouchEvents.screenClicked())
        {
            if(AGInputManager.vrTouchEvents.getLastPosition().fX < AGScreenManager.iScreenWidth / 2)
            {
                balao.vrPosition.fX += 50;

                if(balao.vrPosition.getX() > AGScreenManager.iScreenWidth - balao.getSpriteWidth() / 2)
                {
                    balao.vrPosition.setX(AGScreenManager.iScreenWidth - balao.getSpriteWidth() / 2);
                }
            } else
            {
                balao.vrPosition.fX -= 50;

                if(balao.vrPosition.getX() < balao.getSpriteWidth() / 2)
                {
                    balao.vrPosition.setX(balao.getSpriteWidth() / 2);
                }
            }
        }
    }

    private void verificaColisao()
    {
        for(AGSprite emoji : vetor_emojis)
        {
            if(emoji.vrPosition.fY >= balao.vrPosition.fY - balao.getSpriteHeight() / 4)
            {
                if(balao.collide(emoji))
                {
                    balao.setCurrentAnimation(1);
                }
            }
        }

        for(AGSprite mamona : vetor_mamonas)
        {
            if(mamona.vrPosition.fY >= balao.vrPosition.fY - balao.getSpriteHeight() / 4)
            {
                if(balao.collide(mamona))
                {
                    balao.setCurrentAnimation(1);
                }
            }
        }

        for(AGSprite prego : vetor_pregos)
        {
            if(prego.vrPosition.fY >= balao.vrPosition.fY - balao.getSpriteHeight() / 4)
            {
                if (balao.collide(prego))
                {
                    balao.setCurrentAnimation(1);
                }
            }
        }

        for(AGSprite dardo : vetor_dardos)
        {
            if(dardo.vrPosition.fY >= balao.vrPosition.fY - balao.getSpriteHeight() / 4)
            {
                if(balao.collide(dardo))
                {
                    balao.setCurrentAnimation(1);
                }
            }
        }
    }

    private void atualizaFundo()
    {
        fundo[0].vrPosition.fY -= 15;
        fundo[1].vrPosition.fY -= 15;

        if(fundo[0].vrPosition.fY <= - AGScreenManager.iScreenHeight / 2)
        {
            fundo[0].vrPosition.setY(AGScreenManager.iScreenHeight + fundo[0].getSpriteHeight() / 2);
            fundo[1].vrPosition.setY(AGScreenManager.iScreenHeight / 2);
        }
        if(fundo[1].vrPosition.fY <= - AGScreenManager.iScreenHeight / 2)
        {
            fundo[1].vrPosition.setY(AGScreenManager.iScreenHeight + fundo[1].getSpriteHeight() / 2);
            fundo[0].vrPosition.setY(AGScreenManager.iScreenHeight / 2);
        }
    }

    private void atualizaPlacar()
    {
        if (tempo_pontuacao > 0)
        {
            tempo_pontuacao--;
            pontuacao++;
        }

        placar[5].setCurrentAnimation(pontuacao % 10);
        placar[4].setCurrentAnimation((pontuacao % 100) / 10);
        placar[3].setCurrentAnimation((pontuacao % 1000) / 100);
        placar[2].setCurrentAnimation((pontuacao % 10000) / 1000);
        placar[1].setCurrentAnimation((pontuacao % 100000) / 10000);
        placar[0].setCurrentAnimation(pontuacao / 100000);
    }
}
