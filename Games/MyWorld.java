import greenfoot.*;
import greenfoot.Color;

public class MyWorld extends World
{
    // Variabel untuk menyimpan status permainan
    private int[] Box = new int[9]; // Array status 9 kotak (-1: kosong, 0: Player, 1: Komputer)
    private int delta = 200, TURN = 0; // delta: ukuran per kotak, TURN: giliran jalan
    private boolean GAMEOVER = false; // Status permainan berakhir
    private int WINNER = -1; // Indeks kombinasi kemenangan

    // Deklarasi objek GreenfootSound untuk musik latar
    private GreenfootSound backgroundMusic = new GreenfootSound("audio.mp3"); 

    public MyWorld()
    {
        // Mengatur ukuran World 600x600 sel
        super(600, 600, 1);
        setBackground("background.jpeg");

        // Memulai musik latar secara looping (berulang)
        backgroundMusic.playLoop();
        
        // --- Membuat Garis Grid (Papan Permainan) ---
        
        // Garis vertikal 1
        garis line = new garis();
        addObject(line, delta, (int)(0.5 * getHeight()));
        line.setRotation(90);

        // Garis vertikal 2
        line = new garis();
        addObject(line, 2 * delta, (int)(0.5 * getHeight()));
        line.setRotation(90);

        // Garis horizontal 1
        line = new garis();
        addObject(line, (int)(0.5 * getWidth()), delta);

        // Garis horizontal 2
        line = new garis();
        addObject(line, (int)(0.5 * getWidth()), 2 * delta);

        // Inisialisasi array Box (semua kosong/-1)
        for (int i = 0; i < 9; i++) {
            Box[i] = -1;
        }
        
        // Acak giliran pertama
        TURN = Greenfoot.getRandomNumber(2);
        GAMEOVER = false;
        WINNER = -1;
    }

    /**
     * Method started() dipanggil saat tombol Run ditekan.
     * Melanjutkan musik jika permainan belum berakhir.
     */
    public void started()
    {
        if (!GAMEOVER) {
            backgroundMusic.playLoop();
        }
    }

    /**
     * Method stopped() dipanggil saat tombol Pause ditekan.
     * Menjeda (pause) musik latar.
     */
    public void stopped()
    {
        backgroundMusic.pause();
    }
    
    // Menambahkan objek simbol (Kotak) ke layar
    private void addBox(int a, int px, int py)
    {
        Kotak kotak = new Kotak(a, delta);
        addObject(kotak, px, py);
    }

    // Menghitung jumlah kotak yang terisi
    private int numFilled()
    {
        int num = 0;
        for (int i = 0; i < 9; i++) {
            if (Box[i] >= 0) {
                num++;
            }
        }
        return num;
    }

    // Logika AI: Mencari posisi terbaik untuk menang atau memblokir lawan
    private int searchBestPosition(int turn)
    {
        // Daftar kombinasi kemenangan yang mungkin
        int[] cek = new int[]{0, 1, 2, 0, 2, 1, 1, 2, 0, 3, 4, 5, 3, 5, 4, 4, 5, 3, 6, 7, 8, 6, 8, 7, 7, 8, 6, 0, 3, 6, 0, 6, 3, 3, 6, 0, 1, 4, 7, 0, 4, 8, 0, 8, 4, 8, 4, 0, 2, 4, 6, 6, 4, 2, 2, 6, 4,};
        
        for (int i = 0; i < cek.length / 3; i++) {
            // Jika ada 2 kotak terisi oleh 'turn' dan 1 kotak kosong, ambil kotak kosong tersebut
            if (Box[cek[3 * i + 0]] == Box[cek[3 * i + 1]] && Box[cek[3 * i + 1]] == turn && Box[cek[3 * i + 2]] < 0) {
                return cek[3 * i + 2];
            }
        }
        return -1;
    }

    // Memeriksa apakah ada pemenang
    private int cekWin()
    {
        // Pola kemenangan: baris, kolom, dan diagonal
        int[] cek = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 3, 6, 1, 4, 7, 2, 5, 8, 0, 4, 8, 2, 4, 6};
        
        for (int i = 0; i < cek.length / 3; i++) {
            // Jika 3 kotak dalam satu pola memiliki nilai yang sama dan tidak kosong
            if (Box[cek[3 * i + 0]] == Box[cek[3 * i + 1]] && Box[cek[3 * i + 1]] == Box[cek[3 * i + 2]] && Box[cek[3 * i + 0]] >= 0) {
                WINNER = i; // Simpan indeks pola kemenangan
                return Box[cek[3 * i + 0]]; // Kembalikan ID pemenang
            }
        }
        return -1;
    }

    public void act()
    {
        int numisi = numFilled();
        int win = cekWin();

        // Cek kondisi Game Over (Penuh atau ada Pemenang)
        if (numisi == 9 || win >= 0) {
            if (!GAMEOVER) {
                backgroundMusic.stop(); // Hentikan musik
                GAMEOVER = true;
            }
        }
        
        if (!GAMEOVER)
        {
            if (TURN == 0) // Giliran Player
            {
                if (Greenfoot.mouseClicked(null))
                {
                    MouseInfo mouse = Greenfoot.getMouseInfo();
                    // Konversi koordinat mouse ke indeks grid
                    int io = (mouse.getX()) / delta % 3;
                    int jo = (mouse.getY()) / delta % 3;
                    int index = io + 3 * jo;
                    
                    if (index >= 0 && index < 9) {
                        if (Box[index] < 0) {
                            Box[index] = TURN;
                            addBox(TURN, (int)((io + 0.5) * delta), (int)((jo + 0.5) * delta));
                            TURN = (TURN == 0) ? 1 : 0;
                        }
                    }
                }
            } else { // Giliran Komputer
                // Logika AI Sederhana
                if (numisi == 0) {
                    // Langkah pertama acak
                    int a = Greenfoot.getRandomNumber(Box.length);
                    Box[a] = TURN;
                    int io = a % 3;
                    int jo = a / 3;
                    addBox(TURN, (int)((io + 0.5) * delta), (int)((jo + 0.5) * delta));
                    TURN = (TURN == 0) ? 1 : 0;
                } else if (numisi == 1 || numisi == 2) {
                    // Prioritaskan tengah jika kosong
                    if (Box[4] < 0) {
                        int a = 4;
                        int io = a % 3;
                        int jo = a / 3;
                        Box[a] = TURN;
                        addBox(TURN, (int)((io + 0.5) * delta), (int)((jo + 0.5) * delta));
                        TURN = (TURN == 0) ? 1 : 0;
                    } else {
                        // Jika tengah isi, cari acak
                        boolean pasang = false;
                        while (!pasang) {
                            int a = Greenfoot.getRandomNumber(9);
                            if (Box[a] < 0) {
                                int io = a % 3;
                                int jo = a / 3;
                                Box[a] = TURN;
                                addBox(TURN, (int)((io + 0.5) * delta), (int)((jo + 0.5) * delta));
                                TURN = (TURN == 0) ? 1 : 0;
                                pasang = true;
                            }
                        }
                    }
                } else {
                    // Langkah Lanjutan: Cari Menang atau Blokir
                    int a = searchBestPosition(1); // Coba menang
                    if (a < 0) {
                        a = searchBestPosition(0); // Coba blokir
                    }

                    if (a < 0) {
                        // Jika buntu, pilih acak
                        a = Greenfoot.getRandomNumber(Box.length);
                        while (Box[a] >= 0) {
                            a = Greenfoot.getRandomNumber(Box.length);
                        }
                    }
                    
                    if (a >= 0) {
                        int io = a % 3;
                        int jo = a / 3;
                        Box[a] = TURN;
                        addBox(TURN, (int)((io + 0.5) * delta), (int)((jo + 0.5) * delta));
                        TURN = (TURN == 0) ? 1 : 0;
                    }
                }
            }
        } else {
            // Tampilan Akhir Game
            String text = "";
            if (win >= 0) { 
                text = (win == 0) ? "Player Win" : "Computer Win";
                
                // Gambar garis kemenangan sesuai pola (WINNER)
                if (WINNER == 0) { 
                    addObject(new garisMerah(), (int)(0.5 * getWidth()), (int)(0.5 * delta));
                } else if (WINNER == 1) { 
                    addObject(new garisMerah(), (int)(0.5 * getWidth()), (int)(1.5 * delta));
                } else if (WINNER == 2) { 
                    addObject(new garisMerah(), (int)(0.5 * getWidth()), (int)(2.5 * delta));
                } else if (WINNER == 3) {
                    garisMerah rd = new garisMerah();
                    addObject(rd, (int)(0.5 * delta), (int)(0.5 * getHeight()));
                    rd.setRotation(90);
                } else if (WINNER == 4) {
                    garisMerah rd = new garisMerah();
                    addObject(rd, (int)(1.5 * delta), (int)(0.5 * getHeight()));
                    rd.setRotation(90);
                } else if (WINNER == 5) {
                    garisMerah rd = new garisMerah();
                    addObject(rd, (int)(2.5 * delta), (int)(0.5 * getHeight()));
                    rd.setRotation(90);
                } else if (WINNER == 6) {
                    garisMerah rd = new garisMerah();
                    addObject(rd, (int)(0.5 * getWidth()), (int)(0.5 * getHeight()));
                    rd.setRotation(45);
                } else if (WINNER == 7) {
                    garisMerah rd = new garisMerah();
                    addObject(rd, (int)(0.5 * getWidth()), (int)(0.5 * getHeight()));
                    rd.setRotation(-45);
                }
            } else {
                text = "Draw";
            }
            // Tampilkan teks status
            Tamat theend = new Tamat();
            theend.setImage(new GreenfootImage(text, 80, Color.YELLOW, Color.BLACK));
            addObject(theend, (int)(0.5 * getWidth()), (int)(0.5 * getHeight()));
        }
    }
}
