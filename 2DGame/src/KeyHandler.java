import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed;
    public boolean debugMode;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // PLAY STATE
        if(gp.gameState == gp.playState){
            if(code == KeyEvent.VK_W){
                upPressed = true;
            }
            if(code == KeyEvent.VK_S){
                downPressed = true;
            }
            if(code == KeyEvent.VK_A){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D){
                rightPressed = true;
            }
            if(code == KeyEvent.VK_P){
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
            if(code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
            if(code == KeyEvent.VK_ESCAPE){
                if(gp.ui.gameFinished){
                    Main.window.dispose();
                    gp.leaderb.hinzufügen(gp.ui.playTIme);
                }
                System.exit(0);
            }


            //DEBUG ENTER
            if(code == KeyEvent.VK_T){

                if(!debugMode){
                    debugMode = true;
                }
                else if(debugMode){
                    debugMode = false;
                }
            }
        }

        // PAUSE STATE
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }

        //DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
                gp.gameState = gp.playState;
            }
            // TITLE STATE
        } else if (gp.gameState == gp.titleState) {
            if(code == KeyEvent.VK_W){
                if(gp.ui.commandNum != 0){
                    gp.ui.commandNum--;
                }
            }
            if(code == KeyEvent.VK_S){
                if(gp.ui.commandNum != 1){
                    gp.ui.commandNum++;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1){
                    System.exit(0);
                }
            }
            if(code == KeyEvent.VK_SPACE){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1){
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
