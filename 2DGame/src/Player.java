import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int hasKey = 0;
    int standCounter;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        hitBox = new Rectangle();
        hitBox.x = 8;
        hitBox.y = 16 + gp.tileSize/5;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 32;
        hitBox.height = 32;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX= gp.tileSize * 23;
        worldY= gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){

        up1 = setup("/Pash_sprite/up1");
        up2 = setup("/Pash_sprite/up2");
        down1 = setup("/Pash_sprite/down1");
        down2 = setup("/Pash_sprite/down2");
        left1 = setup("/Pash_sprite/left1");
        left2 = setup("/Pash_sprite/left2");
        right1 = setup("/Pash_sprite/right1");
        right2 = setup("/Pash_sprite/right2");
    }
    public void update(){

        if(keyH.upPressed){
            direction = "up";
        }
        else if(keyH.downPressed){
            direction = "down";
        }
        else if(keyH.leftPressed){
            direction = "left";
        }
        else if(keyH.rightPressed){
            direction = "right";
        }

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){

            //CHECK TILE COLLISON
            collision = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISON
            int objIndex = gp.cChecker.checkObject(this, true);
            pickupObject(objIndex);

            //CHECK ENTITY COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //WENN KEINE KOLLISION KANN SICH DER SPIELER BEWEGEN
            if(collision == false){
                switch (direction){
                    case "up":worldY -= speed; break;
                    case "down":worldY += speed; break;
                    case "left":worldX -= speed; break;
                    case "right":worldX += speed; break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 14){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else{
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else{
            standCounter++;

            if(standCounter > 20){
                spriteNum = 1;

                standCounter = 1;
            }
        }



    }
    public void pickupObject(int i){

        if(i != 999){

            String objectName = gp.obj[i].name;

            switch (objectName){
                case"Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Du hast ein Schlüssel aufgehoben!");
                    break;
                case"Door":
                    if(hasKey > 0){
                        gp.playSE(3);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("Die Tür wurde geöffnet!");
                    }
                    else {
                        gp.ui.showMessage("Du brauchst ein Schlüssel");
                    }
                    break;
                case"Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Du fühlst dich schneller");
                    break;
                case"Chest":
                    gp.obj[i] = null;

                    gp.music.stop();
                    gp.playSE(4);
                    gp.ui.gameFinished = true;
            }

        }
    }
    public void interactNPC(int i){
        if(i != 999){

            if(gp.keyH.enterPressed || gp.keyH.spacePressed){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed = false;
        gp.keyH.spacePressed = false;
    }
    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }

}

