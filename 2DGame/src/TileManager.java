import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager{
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/worldV2.txt");
    }

    public void getTileImage(){

        //PLACEHOLDER
        setup(0, "grass_tiles/grass", false);
        setup(1, "grass_tiles/grass", false);
        setup(2, "grass_tiles/grass", false);
        setup(3, "grass_tiles/grass", false);
        setup(4, "grass_tiles/grass", false);
        setup(5, "grass_tiles/grass", false);
        setup(6, "grass_tiles/grass", false);
        setup(7, "grass_tiles/grass", false);
        setup(8, "grass_tiles/grass", false);
        setup(9, "grass_tiles/grass", false);

        //TILES
        setup(10, "grass_tiles/grass", false);
        setup(11, "grass_tiles/grass_var", false);
        setup(12, "wasser_tiles/wasser", true);
        setup(13, "wasser_tiles/wasser_var", true);
        setup(14, "wasser_tiles/wasser_corner_ul", true);
        setup(15, "wasser_tiles/wasser_up", true);
        setup(16, "wasser_tiles/wasser_corner_ur", true);
        setup(17, "wasser_tiles/wasser_left", true);
        setup(18, "wasser_tiles/wasser_right", true);
        setup(19, "wasser_tiles/wasser_corner_dl", true);
        setup(20, "wasser_tiles/wasser_down", true);
        setup(21, "wasser_tiles/wasser_corner_dr", true);
        setup(22, "wasser_tiles/wasser_point_ul", true);
        setup(23, "wasser_tiles/wasser_point_ur", true);
        setup(24, "wasser_tiles/wasser_point_dl", true);
        setup(25, "wasser_tiles/wasser_point_dr", true);
        setup(26, "sand_tiles/sand", false);
        setup(27, "sand_tiles/sand_corner_ul", false);
        setup(28, "sand_tiles/sand_up", false);
        setup(29, "sand_tiles/sand_corner_ur", false);
        setup(30, "sand_tiles/sand_left", false);
        setup(31, "sand_tiles/sand_right", false);
        setup(32, "sand_tiles/sand_corner_dl", false);
        setup(33, "sand_tiles/sand_down", false);
        setup(34, "sand_tiles/sand_corner_dr", false);
        setup(35, "sand_tiles/sand_point_ul", false);
        setup(36, "sand_tiles/sand_point_ur", false);
        setup(37, "sand_tiles/sand_point_dl", false);
        setup(38, "sand_tiles/sand_point_dr", false);
        setup(39, "grass_tiles/dirt", false);
        setup(40, "mauer_tiles/mauer", true);
        setup(41, "baum_tiles/baum", true);

    }
    public void setup(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Background_tiles/"+ imageName+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine();

                while (col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){

//        g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[1].image, 48, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[2].image, 96, 0, gp.tileSize, gp.tileSize, null);

        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize< gp.player.worldX + gp.player.screenX
            && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;

                worldRow++;

            }
        }

    }
}
