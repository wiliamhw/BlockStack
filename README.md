# Final Project PBO
Membuat game tetris.  
\
![Main menu preivew](Mainmenu-Preview.jpg?raw=true)  
\
![Ingame preivew](Ingame-Preview.jpg?raw=true)

# Tim
#### Nama Kelompok: KamiSama
#### Judul FP: Block Stack
#### Anggota Tim:
* William Handi Wijaya 05111940000087
* Stefanus Albert Kosim 05111940000096  

## Kontrol
* Escape = pause.  
* Kiri = geser ke kiri.  
* Kanan = geser ke kanan.  
* Atas = putar ke kanan.  
* Huruf "Z" = putar ke kiri.  
* Spasi = hard drop.  
* Bawah = soft drop.  
* Huruf "C" = hold. 
* Huruf "G" = nyalakan/matikan balok hantu.  

## Sistem Scoring
* n baris yang full secara konsekutif memberikan score sebanyak (100)n^2.  
	* Un = 100(2n-1).  
	* Sn = 100n^2.  
* Score akan bertambah 1 setiap soft drop.  
* Score akan bertambah sebanyak (2 * jarak ketinggian) setiap hard drop.  

## Sistem Level  
* Semakin tinggi level, maka kecepatan turun balok akan semakin meningkat.
* Pemain akan naik satu level setiap 10 baris yang full.
* Level dimulai dari level 1 hingga level 15.
* Persamaan frekuensi turun balok (dalam millisecond)
    * Un = 12.5n(n + 22)
    * n = level

## Info Tambahan
* Nama pada scoreboard hanya bisa menampung 8 karakter.
* Nama pada scoreboard selalu diawali dengan huruf kapital.
* Asset audio diambil dari game Tetris99 di Nintendo Switch.

# Cara Download Assets
1. Download [assets](https://drive.google.com/file/d/1Suljo33B7DQ7TWR-V7rHMfZzUMnTDup-/view?usp=sharing).
2. Ekstrak file assets.
3. Copy semua file dari file assets ke folder `Java Swing\src`.
4. Import/refresh folder `Java Swing` di IDE.
