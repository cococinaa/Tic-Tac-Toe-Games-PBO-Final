import greenfoot.*;
import greenfoot.Color;

public class garisMerah extends garis
{
    public void addedToWorld(World world)
    {
        int d = 15;
        // Membuat gambar garis penanda kemenangan
        GreenfootImage image = new GreenfootImage(world.getWidth() - 10, d);
        
        // Mengatur warna menjadi Putih untuk kontras yang lebih baik
        image.setColor(Color.WHITE); 
        image.fill(); 
        setImage(image);
    }
    
    public void act()
    {
        // Objek statis
    }
}