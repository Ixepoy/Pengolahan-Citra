kata_awal = "TOKO MAINAN ANAK"
print(kata_awal.center(50))
bintang = "*********************"
print(bintang.center(50))
nama = input("Masukan Nama Pembeli : ")
kode = input("Masukan Kode Mainan : ")
harga = int(input("Masukan Harga : "))
jumlah = int(input("Masukan Jumlah Beli : "))
rumus = harga*jumlah
print("=========================")
print(f"Nama Pembeli = {nama}")
print(f"Kode Mainan  = {kode}")
print(f"Jumlah Beli  = {jumlah}")
print(f"Total        = {rumus}")