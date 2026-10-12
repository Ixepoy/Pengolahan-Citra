# Fungsi untuk menghitung alamat array dua dimensi
def hitung_alamat_array_2d(alamat_awal, baris, kolom, jumlah_kolom, ukuran_tipe_data):
    alamat = alamat_awal + ((baris * jumlah_kolom + kolom) * ukuran_tipe_data)
    return hex(alamat)

alamat_awal = 0x11  
ukuran_tipe_data = 4  
jumlah_kolom = 5  

baris = 4
kolom = 3

alamat_X43 = hitung_alamat_array_2d(alamat_awal, baris, kolom, jumlah_kolom, ukuran_tipe_data)

print(f"Alamat X[4][3]: {alamat_X43}")
