# Tugas FP PBO
Membuat game tetris.  
![Main menu preivew]("mainmenu preview".jpg)  
![Ingame preivew]("ingame preview".jpg)  

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

## Sistem Scoring
* n baris yang full secara konsekutif memberikan score sebanyak (100)n^2.  
	* Un = 100 + 200(n-1).  
	* Sn = 100n^2.  
* Score akan bertambah 1 setiap soft drop.  
* Score akan bertambah sebanyak (2 * jarak ketinggian) setiap hard drop.  

## Info Tambahan
* Nama pada scoreboard hanya bisa menampung 8 karakter.
* Nama pada scoreboard selalu diawali dengan huruf kapital.

# Cara Download Assets
1. Download assets [Tetris99](https://drive.google.com/file/d/1Suljo33B7DQ7TWR-V7rHMfZzUMnTDup-/view?usp=sharing).
2. Ekstrak file assets.
3. Copy file music dan images dari file assets ke folder `Java Swing\src`.
3. Import/refresh folder `Java Swing` di IDE.
