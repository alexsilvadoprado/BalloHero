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
    AGSprite game_over = null;
    AGSprite bonus_star = null;
    AGSprite poder_shield = null;
    AGSprite poder_reducao = null;
    AGSprite shield = null;

    List<AGSprite> vetor_emojis = null;
    List<AGSprite> vetor_mamonas = null;
    List<AGSprite> vetor_pregos = null;
    List<AGSprite> vetor_dardos = null;

    List<AGSprite> vetor_explosao = null;

    int[] vetor_cod_inimigos = new int[4];
    int prox_inimigo = 0;
    int prox_poder = 0;

    float[] vetor_pos_inimigos = new float[5];

    AGTimer tempo_geracao_inimigos = null;
    AGTimer tempo_game_over = null;
    AGTimer tempo_shield = null;
    AGTimer tempo_reducao = null;

    int pontuacao = 0;
    int tempo_pontuacao = 0;
    boolean is_game_over = false;

    boolean shield_ativo = false;
    boolean reducao_ativo = false;

    TelaJogo(AGGameManager vrManager)
    {
        super(vrManager);
    }

    @Override
    public void init()
    {
        prox_inimigo = 0;
        pontuacao = 0;
        tempo_pontuacao = 0;
        is_game_over = false;
        shield_ativo = false;
        reducao_ativo = false;
        prox_poder = new Random().nextInt(2);

        createSprite(R.mipmap.sprite_balao, 4, 2).bVisible = false;
        createSprite(R.mipmap.sprite_emoji, 4, 1).bVisible = false;
        createSprite(R.mipmap.barra_superior, 1, 1).bVisible = false;
        createSprite(R.mipmap.explosao, 4, 2).bVisible = false;

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

        vetor_explosao = new ArrayList<AGSprite>();

        tempo_geracao_inimigos = new AGTimer(1000);
        tempo_game_over = new AGTimer(1500);
        tempo_shield = new AGTimer(5000);
        tempo_reducao = new AGTimer(5000);

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

        bonus_star = createSprite(R.mipmap.estrela, 1, 1);
        bonus_star.setScreenPercent(15, 8);
        bonus_star.bVisible = false;

        poder_shield = createSprite(R.mipmap.shield, 1, 1);
        poder_shield.setScreenPercent(10, 10);
        poder_shield.bVisible = false;

        poder_reducao = createSprite(R.mipmap.balloon_icon, 1, 1);
        poder_reducao.setScreenPercent(10, 10);
        poder_reducao.bVisible = false;

        shield = createSprite(R.mipmap.bolha, 1, 1);
        shield.setScreenPercent(25, 25);
        shield.bVisible = false;

        game_over = createSprite(R.mipmap.game_over, 1, 1);
        game_over.setScreenPercent(100, 40);
        game_over.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        game_over.vrPosition.setY(AGScreenManager.iScreenHeight / 2);
        game_over.bVisible = false;
        game_over.bAutoRender = false;
    }

    @Override
    public void render()
    {
        super.render();
        barra_superior.render();

        game_over.render();

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

        atualizaPoderes();

        verificaColisao();

        verificaToque();

        atualizaPlacar();

        criarEstrelaBonus();

        colisaoEstrelaBonus();

        animacaoGameOver();
    }

    private void criarEstrelaBonus()
    {
        if(pontuacao == 0)
            return;

        if(pontuacao%1000 == 0)
        {
            bonus_star.bVisible = true;
            bonus_star.vrPosition.setX(vetor_pos_inimigos[new Random().nextInt(5)]);
            bonus_star.vrPosition.setY(AGScreenManager.iScreenHeight + bonus_star.getSpriteHeight() / 2);
        }
    }

    private void colisaoEstrelaBonus()
    {
        if(!bonus_star.bVisible || poder_reducao.bVisible || poder_shield.bVisible)
            return;

        bonus_star.vrPosition.fY -= 10;

        if(balao.collide(bonus_star))
        {
            bonus_star.bVisible = false;
            if(prox_poder == 0)
            {
                poder_shield.bVisible = true;
                poder_shield.vrPosition.setX(balao.vrPosition.fX);
                poder_shield.vrPosition.setY(balao.vrPosition.fY);
                prox_poder = 1;
            } else
            {
                poder_reducao.bVisible = true;
                poder_reducao.vrPosition.setX(balao.vrPosition.fX);
                poder_reducao.vrPosition.setY(balao.vrPosition.fY);
                prox_poder = 0;
            }
        }
    }

    private void atualizaPoderes()
    {
        if(shield_ativo)
        {
            tempo_shield.update();

            if(tempo_shield.isTimeEnded())
            {
                tempo_shield.restart();
                shield_ativo = false;
                shield.bVisible = false;
            }
            return;
        }

        if(reducao_ativo)
        {
            tempo_reducao.update();

            if(tempo_reducao.isTimeEnded())
            {
                tempo_reducao.restart();
                reducao_ativo = false;
                balao.setScreenPercent(20, 20);
            }
            return;
        }
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

    private void animacaoGameOver()
    {
        if(!is_game_over)
            return;

        if(game_over.fadeEnded())
        {
            pontuacao += tempo_pontuacao;
            tempo_pontuacao = 0;
            tempo_game_over.update();

            if(tempo_game_over.isTimeEnded())
            {
                vrGameManager.setCurrentScene(1);
                return;
            }
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
                if(emoji.bVisible && !is_game_over)
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
                if(mamona.bVisible && !is_game_over)
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
                if(prego.bVisible && !is_game_over)
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
                if(dardo.bVisible && !is_game_over)
                    tempo_pontuacao += 50;
                dardo.bRecycled = true;
                dardo.bVisible = false;
            }
        }
    }

    private void verificaToque()
    {
        if(AGInputManager.vrTouchEvents.screenClicked() || AGInputManager.vrTouchEvents.screenDown() && !is_game_over)
        {
            if(balao.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
                if(poder_shield.bVisible)
                {
                    poder_shield.bVisible = false;
                    shield_ativo = true;
                    shield.bVisible = true;
                    shield.vrPosition.setX(balao.vrPosition.fX);
                    shield.vrPosition.setY(balao.vrPosition.fY);
                    return;
                } else if(poder_reducao.bVisible)
                {
                    poder_reducao.bVisible = false;
                    reducao_ativo = true;
                    balao.setScreenPercent(10, 10);
                    return;
                }
            }

            if(AGInputManager.vrTouchEvents.getLastPosition().fX < balao.vrPosition.fX)
            {
                if(AGInputManager.vrTouchEvents.getLastPosition().fY > balao.vrPosition.fY + balao.getSpriteHeight() / 2)
                    return;

                criaExplosao(balao.vrPosition.fX - balao.getSpriteWidth(), balao.vrPosition.fY);
                balao.vrPosition.fX += 50;
                if(poder_shield.bVisible || poder_reducao.bVisible)
                {
                    poder_shield.vrPosition.fX += 50;
                    poder_reducao.vrPosition.fX += 50;
                }

                if(shield.bVisible)
                    shield.vrPosition.fX += 50;

                if(balao.vrPosition.getX() > AGScreenManager.iScreenWidth - balao.getSpriteWidth() / 2)
                {
                    balao.vrPosition.setX(AGScreenManager.iScreenWidth - balao.getSpriteWidth() / 2);
                }
            } else
            {
                if(AGInputManager.vrTouchEvents.getLastPosition().fY > balao.vrPosition.fY + balao.getSpriteHeight() / 2)
                    return;

                criaExplosao(balao.vrPosition.fX + balao.getSpriteWidth(), balao.vrPosition.fY);
                balao.vrPosition.fX -= 50;
                if(poder_shield.bVisible || poder_reducao.bVisible)
                {
                    poder_shield.vrPosition.fX -= 50;
                    poder_reducao.vrPosition.fX -= 50;
                }

                if(shield.bVisible)
                    shield.vrPosition.fX -= 50;

                if(balao.vrPosition.getX() < balao.getSpriteWidth() / 2)
                {
                    balao.vrPosition.setX(balao.getSpriteWidth() / 2);
                }
            }
        } else if(!AGInputManager.vrTouchEvents.screenDown())
        {
            if(AGInputManager.vrTouchEvents.getLastPosition().fX < balao.vrPosition.fX)
            {
                criaExplosao(balao.vrPosition.fX - balao.getSpriteWidth(), balao.vrPosition.fY);
                balao.vrPosition.fX += 10;
                if(poder_shield.bVisible || poder_reducao.bVisible)
                {
                    poder_shield.vrPosition.fX += 10;
                    poder_reducao.vrPosition.fX += 10;
                }

                if(shield.bVisible)
                    shield.vrPosition.fX += 10;

                if(balao.vrPosition.getX() > AGScreenManager.iScreenWidth - balao.getSpriteWidth() / 2)
                {
                    balao.vrPosition.setX(AGScreenManager.iScreenWidth - balao.getSpriteWidth() / 2);
                }
            } else
            {
                criaExplosao(balao.vrPosition.fX + balao.getSpriteWidth(), balao.vrPosition.fY);
                balao.vrPosition.fX -= 10;
                if(poder_shield.bVisible || poder_reducao.bVisible)
                {
                    poder_shield.vrPosition.fX -= 10;
                    poder_reducao.vrPosition.fX -= 10;
                }

                if(shield.bVisible)
                    shield.vrPosition.fX -= 10;

                if(balao.vrPosition.getX() < balao.getSpriteWidth() / 2)
                {
                    balao.vrPosition.setX(balao.getSpriteWidth() / 2);
                }
            }
        }
    }

    private void verificaColisao()
    {
        if(is_game_over)
            return;

        for(AGSprite emoji : vetor_emojis)
        {
            if(shield.bVisible)
            {
                if(shield.collide(emoji))
                {
                    emoji.bVisible = false;
                    emoji.bRecycled = true;
                    return;
                }
            }
            if(emoji.vrPosition.fY >= balao.vrPosition.fY - balao.getSpriteHeight() / 4)
            {
                if(balao.collide(emoji) && !emoji.bRecycled)
                {
                    balao.setCurrentAnimation(1);
                    poder_shield.bVisible = false;
                    poder_reducao.bVisible = false;
                    is_game_over = true;
                    game_over.fadeIn(2000);
                }
            }
        }

        for(AGSprite mamona : vetor_mamonas)
        {
            if(shield.bVisible)
            {
                if(shield.collide(mamona))
                {
                    mamona.bVisible = false;
                    mamona.bRecycled = true;
                    return;
                }
            }
            if(mamona.vrPosition.fY >= balao.vrPosition.fY - balao.getSpriteHeight() / 4)
            {
                if(balao.collide(mamona) && !mamona.bRecycled)
                {
                    balao.setCurrentAnimation(1);
                    poder_shield.bVisible = false;
                    poder_reducao.bVisible = false;
                    is_game_over = true;
                    game_over.fadeIn(2000);
                }
            }
        }

        for(AGSprite prego : vetor_pregos)
        {
            if(shield.bVisible)
            {
                if(shield.collide(prego))
                {
                    prego.bVisible = false;
                    prego.bRecycled = true;
                    return;
                }
            }
            if(prego.vrPosition.fY >= balao.vrPosition.fY - balao.getSpriteHeight() / 4)
            {
                if (balao.collide(prego) && !prego.bRecycled)
                {
                    balao.setCurrentAnimation(1);
                    poder_shield.bVisible = false;
                    poder_reducao.bVisible = false;
                    is_game_over = true;
                    game_over.fadeIn(2000);
                }
            }
        }

        for(AGSprite dardo : vetor_dardos)
        {
            if(shield.bVisible)
            {
                if(shield.collide(dardo))
                {
                    dardo.bVisible = false;
                    dardo.bRecycled = true;
                    return;
                }
            }
            if(dardo.vrPosition.fY >= balao.vrPosition.fY - balao.getSpriteHeight() / 4)
            {
                if(balao.collide(dardo) && !dardo.bRecycled)
                {
                    balao.setCurrentAnimation(1);
                    poder_shield.bVisible = false;
                    poder_reducao.bVisible = false;
                    is_game_over = true;
                    game_over.fadeIn(2000);
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

    private void criaExplosao(float x, float y)
    {
        for(AGSprite current : vetor_explosao)
        {
            if(current.bRecycled)
            {
                current.bRecycled = false;
                current.bVisible = true;
                current.vrPosition.setX(x);
                current.vrPosition.setY(y);

                return;
            }
        }

        AGSprite nova_explosao = createSprite(R.mipmap.explosao, 4, 2);
        nova_explosao.setScreenPercent(20, 20);
        nova_explosao.addAnimation(20, false, 0, 5);
        nova_explosao.vrPosition.setX(x);
        nova_explosao.vrPosition.setY(y);
        vetor_explosao.add(nova_explosao);
    }
}
