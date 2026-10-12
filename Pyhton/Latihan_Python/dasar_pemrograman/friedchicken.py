# Menu
print(''' GEROBAK FRIED CHICKEN
      ----------------------------
      Kode Jenis Potong Harga
      ----------------------------
      D    Dada         Rp. 2500
      P    Paha         Rp. 2000
      S    Sayap        Rp. 1500
      ---------------------------
      ''')

# Input
banyakjenis = int(input(" Banyak Jenis        : "))
banyak_potong = input  (" Banyak Potong       : ")

for i in range(banyakjenis) :
    jenis= input(f"\nJenis Ke - {i+1}")
    kode_potong = input("Kode Potong [D/P/S] : ").upper()
    if kode_potong=="D":
        nama = "Dada"
        harga = 2500
    elif kode_potong=="P":
        nama = "Paha"
        harga = 2000
    elif kode_potong=="S":
        nama = "Sayap"
        harga = 1500
    else :
        print("Kode Potong Tidak Valid")
jumlah= (banyak_potong)*harga


#Bayar
jumlahbayar = 1
pajak = 10/100*jumlahbayar
total= jumlahbayar-pajak

#Print
print("\nGEROBAK FRIED CHICKEN")
print("------------------------------------")
print("""NO. Jenis   Harga  Banyak Jumlah
    Potong  Satuan Beli   Harga 
      """)
print(f"{i}  {nama} {harga} {banyak_potong} {jumlah}")
print("----------------------------------")
