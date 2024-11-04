import java.util.Random;

public class NPC_Johnkler extends Entity{

    public NPC_Johnkler(GamePanel gp) {
        super(gp);

        direction ="down";
        speed = 0;

        getImage();
        setDialogue();
    }
    public void getImage(){

        up1 = setup("/npc/Johnkler_sprite/up1");
        up2 = setup("/npc/Johnkler_sprite/up2");
        down1 = setup("/npc/Johnkler_sprite/down1");
        down2 = setup("/npc/Johnkler_sprite/down2");
        left1 = setup("/npc/Johnkler_sprite/left1");
        left2 = setup("/npc/Johnkler_sprite/left2");
        right1 = setup("/npc/Johnkler_sprite/right1");
        right2 = setup("/npc/Johnkler_sprite/right2");
    }
    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter > 120){
            Random random = new Random();
            int i = random.nextInt(100) + 1; // random zahl von 1 bis 100

            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <=75){
                direction = "left";
            }
            if(i > 75){
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    public void setDialogue(){

        dialogues[0] = "Ich bin der Johnkler";
        dialogues[1] = "Wenn du den Schatz dieser Insel ergattern \nwillst musst du zuerst an mir vorbei!";
        dialogues[2] = "Ha! was sag ich überhaupt";
        dialogues[3] = "Du Schwächling kannst garnichts besiege \nerstmal meine Lakeien";

    }

    public void speak(){
        super.speak();
    }
}
