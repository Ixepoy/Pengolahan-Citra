berat_telur = int(input("Masukan Berat Telur : "))
harga = 26000
total = harga*berat_telur
pp = float(input("Masukan Harga Ongkos adalah : Rp."))*2
uang = float(input("Masukan Jumlah Uang Anda :"))
total_pengeluaran = (uang)-(pp+total)
print("\n==== Hasil ====")
print("\nHarga Transport adalah : Rp.", pp)
print(f"Total pembelian anda adalah : Rp.", total)
print(f"Sisa uang anda adalah : Rp.", total_pengeluaran)