# Input
jumlah_produk1 = int(input("| Masukan Jumlah Produk | : "))
harga_produk = int(input  ("| Masukan Harga Produk  | : "))
omset = jumlah_produk1*harga_produk
gaji_pokok = 5000000

# Proses Omset
if jumlah_produk1>100 :
    bonus = 20/100*omset
elif jumlah_produk1<=100 :
    bonus = 10/100*omset
else :
    bonus = 0

# Gaji Salesmen
gaji = (gaji_pokok+bonus)

# Cetak Hasil
print("=====================================")
print("| Gaji Anda Adalah | : Rp. ",int(gaji))
print("=====================================")