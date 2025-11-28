import greenfoot.*;
import greenfoot.Color;

public class garis extends Actor
{
    public void addedToWorld(World world)
    {
        int d = 15; // Ketebalan garis
        
        // Membuat gambar garis putih sepanjang lebar world
        GreenfootImage image = new GreenfootImage(world.getWidth() - 10, d);
        
        image.setColor(Color.WHITE);
        image.fill();
        setImage(image);
    }
    
    public void act()
    {
        // Objek statis
    }
}