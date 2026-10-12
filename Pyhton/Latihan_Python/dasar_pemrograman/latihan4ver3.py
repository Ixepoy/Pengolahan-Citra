#Input
pembeli=input("Input Nama Pembeli : ")
no_hp=input("Input No. Handphone : ")
jurusan=input("Input Jurusan [SBY/BL/LMP] : ")
#proses
if jurusan=="SBY" or jurusan=="sby" or jurusan=="Sby" or jurusan=="sBy" or jurusan=="sbY" :
    namajurusan="Surabaya"
    harga=300000
elif jurusan=="BL" or jurusan=="bl" or jurusan=="Bl" or jurusan=="bL" :
    namajurusan="Bali"
    harga=350000
elif jurusan=="LMP" or jurusan=="lmp" or jurusan=="Lmp" or jurusan=="lMp" or jurusan=="lmP":
    namajurusan="Lampung"
    harga=500000
else:
    jurusan=="Inputan Salah"
    harga=0

#Input Jumlah Beli
jumlah=int(input("Masukan Jumlah Beli : "))

#proses potongan
if jumlah>=3 :
    potongan=(jumlah*harga)*0.1
else :
    potongan=0

total=(jumlah*harga)-potongan

#Cetak Hasil
print("------------------------------")
print("PENJUALAN TIKET BUS")
print("XYZ")
print("-------------------------------")
print("Nama Pembeli : ",str(pembeli))
print("No. Handphone : ",str(no_hp))
print("Kode Jurusan yang dipilih : ",str(jurusan))
print("Nama Kota Tujuan : ",str(namajurusan))
print("Harga : ",+(harga))
print("Jumlah Beli : ",+(jumlah))
print("--------------------------------")
print("potongan yang didapat : ",+(potongan))
print("Total Bayar : ",+(total))
ubay=int(input("Masukan Uang Bayar : "))
uangkembali=ubay-total
print("Uang Kembali : ",+uangkembali)