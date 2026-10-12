# Fungsi untuk menghitung alamat array
def hitung_alamat_array(alamat_awal, indeks, ukuran_tipe_data):
    alamat = alamat_awal + (indeks * ukuran_tipe_data)
    return hex(alamat)

alamat_awal = 0x011  
ukuran_tipe_data = 4  
indeks_A20 = 20
indeks_A40 = 40

alamat_A20 = hitung_alamat_array(alamat_awal, indeks_A20, ukuran_tipe_data)
alamat_A40 = hitung_alamat_array(alamat_awal, indeks_A40, ukuran_tipe_data)

print(f"Alamat A[20]: {alamat_A20}")
print(f"Alamat A[40]: {alamat_A40}")
