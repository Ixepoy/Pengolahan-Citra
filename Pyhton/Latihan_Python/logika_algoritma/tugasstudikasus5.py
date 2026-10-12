# Input
gaji_pokok = int(input("| Masukan Gaji Pokok | : "))
jam_kerja = int(input ("| Masukan Jam Kerja  | : "))
jam_kerja1 = 200
jam_kerja2 = jam_kerja-jam_kerja1
tunjangan = 20/100*gaji_pokok

# Proses Jam Lembur Kerja
if jam_kerja>200 :
    lembur_kerja = 20000*jam_kerja2
else :
    lembur_kerja = 0

# Pajak dari Gaji kotor/gaji pokok * 10/100
gaji_kotor = gaji_pokok+lembur_kerja+tunjangan
pajak = gaji_kotor*10/100

# Gaji Bersih
gaji_bersih = gaji_kotor-pajak
print("================================================")
print("| Pajak Anda Adalah       | : Rp. ",(pajak))
print("| Tunjangan Anda Adalah   | : Rp. ",int(tunjangan))
print("| Gaji Kotor Anda Adalah  | : Rp. ",(gaji_pokok))
print("| Gaji Bersih Anda Adalah | : Rp. ",int(gaji_bersih))
print("================================================")