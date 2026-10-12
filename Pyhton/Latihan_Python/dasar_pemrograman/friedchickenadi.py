harga_ayam = {
    'D': {'jenis': 'Dada', 'harga': 2500},
    'P': {'jenis': 'Paha', 'harga': 2000},
    'S': {'jenis': 'Sayap', 'harga': 1500},
    'U': {'jenis': 'Usus', 'harga': 3000},
    'K': {'jenis': 'Kepala', 'harga': 3500}
}

banyak_jenis = int(input("Masukkan banyak jenis: "))
total_harga = 0
pesanan = []

for i in range(banyak_jenis):
    print("\nJenis Ke-", i + 1)
    kode_potong = input("Masukkan kode potong (D/P/S/U/K): ").upper()
    banyak_beli = int(input("Masukkan banyak potong: "))

    if kode_potong in harga_ayam:
        harga_satuan = harga_ayam[kode_potong]['harga']
        jumlah_harga = harga_satuan * banyak_beli
        total_harga += jumlah_harga
        pesanan.append({
            'no': i + 1,
            'jenis': harga_ayam[kode_potong]['jenis'],
            'harga_satuan': harga_satuan,
            'banyak_beli': banyak_beli,
            'jumlah_harga': jumlah_harga
        })
    else:
        print("Kode potong tidak valid. Silakan masukkan ulang.")

pajak = total_harga * 0.1
total_bayar = total_harga + pajak

print("\nGEROBAK FRIED CHICKEN")
print("----------------------------------------------------")
print("No.  Jenis Potong  Harga Satuan  Banyak Beli  Jumlah Harga")
print("----------------------------------------------------")
for item in pesanan:
    print(str(item['no']).rjust(2), "  ", item['jenis'].ljust(12), " Rp.", str(item['harga_satuan']).ljust(10), str(item['banyak_beli']).rjust(5), "     Rp.", str(item['jumlah_harga']).ljust(5))

print("----------------------------------------------------")
print("Jumlah Bayar     Rp.", total_harga)
print("Pajak 10%        Rp.", pajak)
print("Total Bayar      Rp.", total_bayar)