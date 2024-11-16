import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI{

    Font maruMonica;
    GamePanel gp;
    Graphics2D g2;
    BufferedImage keyImage;
    boolean messageOn = false;
    String message = "";
    int messageCounter = 0;
    boolean gameFinished = false;

    public String currentDialogue;

    double playTIme;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public  int commandNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;


        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){

        // GAME FINISHED
        if(gameFinished){

            g2.setFont(maruMonica.deriveFont(40f));
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "Du hast den Schatz gefunden!";
            x = getXforCenterText(text);
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "Deine Endzeit ist: "+dFormat.format(playTIme);
            x = getXforCenterText(text);
            y = gp.screenHeight / 2 + (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "ESC zum beenden";
            x = getXforCenterText(text);
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
            g2.setColor(Color.yellow);
            text = "Danke fürs Spielen!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        }
        // GAME GOING
        else {

            this.g2 = g2;

            g2.setFont(maruMonica);
            g2.setColor(Color.white);

            //TITLE STATE
            if(gp.gameState == gp.titleState){
                drawTitleSceen();
            }


            // PAUSE STATE
            if (gp.gameState == gp.pauseState){
                drawPauseScreen();
            }

            // DIALOGUE STATE
            if(gp.gameState == gp.dialogueState){
                drawDialogueScreen();
                drawGameScreen();
            }

            // PLAY STATE
            if (gp.gameState == gp.playState){
                drawGameScreen();
                playTIme += (double) 1/60;
            }
        }
    }

    public void drawGameScreen(){

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, gp.tileSize * gp.maxScreenCol, gp.tileSize * gp.maxScreenRow / 6);  //Obere leiste
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString(": "+ gp.player.hasKey, gp.tileSize + gp.tileSize/2, gp.tileSize + gp.tileSize/4);

        //TIME
        g2.drawString("Time: "+dFormat.format(playTIme), gp.tileSize* 16, gp.tileSize + gp.tileSize/4);

        //MESSAGE
        if(messageOn){
            g2.setFont(g2.getFont().deriveFont(30f));
            g2.drawString(message, gp.tileSize/2, gp.tileSize * 11);

            messageCounter++;

            if (messageCounter > 121){
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    public void drawTitleSceen(){

        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
        String text = "PASCHERMON";
        int x = getXforCenterText(text);
        int y = gp.tileSize * 3;

        //SHADOW
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+5, y+5);

        // MAIN COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //PASH IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2)/2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        text =  "Start";
        x = getXforCenterText(text);
        y += gp.tileSize * 5;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text =  "Beenden";
        x = getXforCenterText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x - gp.tileSize, y);
        }


    }

    public void drawPauseScreen(){

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
        String text = "PAUSIERT";
        int x = getXforCenterText(text);
        int y = gp.screenHeight/2;

        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRect(0, 0, gp.tileSize * gp.maxScreenCol, gp.tileSize * gp.maxScreenRow);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

    }

    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 15 / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize* 4;

        drawSubWindow(x, y, width, height);

        x += (gp.tileSize / 2) + (gp.tileSize / 4);
        y += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40));

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += gp.tileSize;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }
    public int getXforCenterText(String text){
        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - lenght / 2;
    }
}
