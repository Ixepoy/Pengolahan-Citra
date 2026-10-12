kode_baju = input("Masukan Kode Baju [SP/AD] : ")
ukuran = input("Masukan Ukuran Baju [S/M] : ")

if kode_baju == "SP" or kode_baju == "sp" or kode_baju=="sP" or kode_baju=="Sp":
    merk = "SuperDry"
    if ukuran=="S" or ukuran=="s" :
        harga = 450000
    elif ukuran=="M" or ukuran=="m" :
        harga = 500000
    else :
        harga = 0
elif kode_baju == "AD" or kode_baju == "ad" or kode_baju=="Ad" or kode_baju=="aD":
    merk = "Adidas"
    if ukuran=="s" or ukuran=="S" :
        harga = 650000
    elif ukuran=="M" or ukuran=="m" :
        harga = 700000
    else :
        harga = 0
else :
    merk = "Anda Salah Input Kode Merk"
    harga = 0

print("-------------------------")
print("Merk Baju : ",str(merk))
print("Harga Baju : Rp.",(harga))