#!/usr/bin/env python
import pandas as pd
import os


Compass = {
   "CGH" : 438000,
   "CGL" : 410000,
   "CH" : 400000
}

def pilihan_sepatubsi():
    print("=" * 50)
    print(f'''1. AeroStreet
2. Compass''')
    print("=" * 50)

def AeroStreet_Display() :
    print(f'''\n
==============================================================================
|                           |            |         |   Warna    |            |
|      Nama Model           | Kode Model | Ukuran  |------------|   Harga    |
|                           |            |         | B | W  | G |            |
==============================================================================
| 1. AeroStreet Brooklyn    | AB         | 39 - 44 | ✓ | ✓ | ✓ | Rp. 484000 |
| 2. AeroStreet Massive Low | AML        | 40 - 43 | ⨉ | ⨉ | ⨉ | Rp. 160000 |
| 3. AeroStreet Active Low  | AAL        | 38 - 41 | ⨉ | ⨉ | ⨉ | Rp. 250000 |
==============================================================================
Note :
Black (B)
White (W)
Grey (G)
          ''')

def warna_sepatu():
   print("=" * 50)
   print(f'''1. Black
2. White
3. Gray''')
   print("=" * 50)

def Compass_Display() :
    print(f'''\n
====================================================================================
|                                  |            |         |   Warna    |            |
|      Nama Model                  | Kode Model | Ukuran  |------------|   Harga    |
|                                  |            |         | B | W  | G |            |
====================================================================================
| 1. Compass Gazelle Hi            | CGH        | 39 - 44 | ✓ | ✓ | ✓ | Rp. 438000 |
| 2. Compass Gazelle Low           | CGL        | 40 - 43 | ⨉ | ⨉ | ⨉ | Rp. 410000 |
| 3. Compass Gazelle               | CG         | 38 - 41 | ⨉ | ⨉ | ⨉ | Rp. 400000 |
====================================================================================
Note :
Black (B)
White (W)
Grey (G)
          ''')

def aerostreet_display():
   harga = {
    "AeroStreet Brooklyn" : 484000,
    "AeroStreet Massive Low" : 160000,
    "AeroStreet Active Low" : 250000,
    "CGH" : 438000,
    "Compass Gazelle Low Black Gum" : 408000,
    "Compass Gazelle Hi Cream" : 400000,}
   for key, value in harga.items():
        print(f"{key}: Rp {value:,}")



# Menu Awal
os.system('cls||clear')
print(f'''\n
======================================
| SELAMAT DATANG DI TOKO SEPATU UBSI |
======================================''')



list_jual = []
list_modelsepatu = []
list_warnasepatu = []

def main():
      #list_ukuransepatu = []
      pilihan = input(f"""Apakah Anda Ingin membeli/menjual sepatu ? [B]eli / [J]ual : """)
      os.system('cls||clear')
      pilihan_sepatubsi()
      if pilihan=="Beli" or pilihan=="beli" or pilihan == "b" or pilihan == "B":         
         pilihan_sepatu = int(input("Pilih Sepatu yang Anda Inginkan [1/2] : "))
         if pilihan_sepatu==1:
               Total_Beli = int(input("Jumlah Sepatu yang ingin Anda Beli : "))
               for i in range(Total_Beli):
                  os.system('cls||clear')
                  print("Sepatu Ke - ", str(i+1))
                  AeroStreet_Display()
                  list_modelsepatu.append(int(input("Pilih No. Model Sepatu yang Anda Inginkan [1/2/3]: ")))
                  if list_modelsepatu[0]==1:
                     aerostreet_display()
                  # elif list_modelsepatu==2:
                  #    pass
                  # elif list_modelsepatu==3:
                  #    pr
                  else:
                     print("ok")
                  list_ukuransepatu = (input("Masukan Ukuran Sepatu [37 - 44]: "))
                  if list_ukuransepatu in ["37", "38", "39", "40", "41", "42", "43", "44"]:
                     warna_sepatu()
                     list_warnasepatu.append(input("Masukan No. Warna yang Anda Inginkan [1/2/3] : "))
                  else :
                   print("Ukuran Sepatu yang Anda Inginkan tidak tersedia! Silahkan pilih ukuran sepatu antara 37 - 44!")
                   break
         elif pilihan_sepatu==2:
               Total_Beli = int(input("Jumlah Sepatu yang ingin Anda Beli : "))
               for i in range(Total_Beli):
                  os.system('cls||clear')
                  print("\nSepatu Ke - ", str(i+1))
                  Compass_Display()
                  list_modelsepatu.append(input("Pilih No. Model Sepatu yang Anda Inginkan [1/2/3]: "))
                  if list_modelsepatu[-1]== 1 :
                     print("Halo")
                  else :
                     print("smth")
                  list_ukuransepatu = (input("Masukan Ukuran Sepatu [37 - 44]: "))
                  if list_ukuransepatu[i] in ["37", "38", "39", "40", "41", "42", "43", "44"]:
                   warna_sepatu()
                   list_warnasepatu.append(input("Masukan No. Warna yang Anda Inginkan [1/2/3] : "))
                  else:
                   print("Ukuran Sepatu yang Anda Inginkan tidak tersedia! Silahkan pilih ukuran sepatu antara 37 - 44!")
                   break
         else:
            print("Anda Salah Input No. Sepatu! Silahkan Input Nomor [1/2]")
      elif pilihan=="Jual" or pilihan=="jual" or pilihan == "J" or pilihan == "j":
         os.system('cls||clear')
         pilihan_sepatubsi()
         pilihan_sepatu = int(input("Pilih Sepatu yang Anda Inginkan [1/2] : "))
         if pilihan_sepatu==1:
               Total_Jual = int(input("Jumlah Sepatu yang ingin Anda jual : "))
               for i in range(Total_Jual):
                  os.system('cls||clear')
                  print("Sepatu Ke - ", str(i+1))
                  AeroStreet_Display()
                  list_modelsepatu.append(input("Pilih No. Model Sepatu yang Anda ingin jual [1/2/3]: "))
                  list_ukuransepatu.append(input("Masukan Ukuran Sepatu [37 - 44]: "))
                  if list_ukuransepatu[i] in ["37", "38", "39", "40", "41", "42", "43", "44"]:
                   warna_sepatu()
                   list_warnasepatu.append(input("Masukan No. Warna yang Anda ingin jual [1/2/3] : "))
                  else:
                   print("Ukuran Sepatu yang Anda Inginkan tidak tersedia! Silahkan pilih ukuran sepatu antara 37 - 44!")
                   break
         elif pilihan_sepatu==2:
               Total_Jual = int(input("Jumlah Sepatu yang ingin Anda jual : "))
               for i in range(Total_Jual):
                  os.system('cls||clear')
                  print("\nSepatu Ke - ", str(i+1))
                  Compass_Display()
                  list_modelsepatu.append(input("Pilih No. Model Sepatu yang Anda ingin jual [1/2/3]: "))
                  list_ukuransepatu = (input("Masukan Ukuran Sepatu [37 - 44]: "))
                  if list_ukuransepatu[i] in ["37", "38", "39", "40", "41", "42", "43", "44"]:
                   warna_sepatu()
                   list_warnasepatu.append(input("Masukan No. Warna yang Anda ingin jual [1/2/3] : "))
                  else:
                   print("Ukuran Sepatu yang Anda Inginkan tidak tersedia! Silahkan pilih ukuran sepatu antara 37 - 44!")
                   break
      else:
         os.system('cls||clear')
         print("Anda Salah Memasukan Input ! Silahkan Pilih Beli atau Jual")

if __name__ == "__main__" :
    main()