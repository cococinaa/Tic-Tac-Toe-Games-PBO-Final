import greenfoot.*;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color; 

public class Kotak extends Actor
{
    // ID 0 = Player (X), ID 1 = Komputer (O)
    public int ID = 0, delta = 200;

    public Kotak(int delta)
    {
        this.delta = delta;
    }

    public Kotak(int id, int delta)
    {
        ID = id;
        this.delta = delta;
    }

    // Menggambar simbol X atau O saat objek ditambahkan
    public void addedToWorld(World world)
    {
        GreenfootImage image = new GreenfootImage(delta, delta);
        int d = 30; // Margin gambar
        
        if (ID == 0) 
        {
            // Menggambar 'X' warna Merah
            Graphics2D g2 = image.getAwtImage().createGraphics();
            g2.setStroke(new BasicStroke(d)); 
            g2.setColor(java.awt.Color.RED); 

            g2.drawLine(d, d, delta - d, delta - d);
            g2.drawLine(delta - d, d, d, delta - d);
            
            g2.dispose();
        }
        else 
        {
            // Menggambar 'O' warna Hijau
            Graphics2D g2 = image.getAwtImage().createGraphics();
            g2.setStroke(new BasicStroke(d));
            g2.setColor(java.awt.Color.GREEN);
            
            g2.drawArc(d, d, image.getWidth() - 2 * d, image.getHeight() - 2 * d, 0, 360);
            g2.dispose();
        }
    
        setImage(image);
    }
    
    public void act()
    {
        // Kosong karena objek statis
    }  
}