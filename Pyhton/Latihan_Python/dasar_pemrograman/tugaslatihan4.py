print("PROGRAM HITUNG GAJI KARYAWAN\n".center(50))
# Inputan
nama = input               ("| Nama Karyawan             | : ")
golongan_jabatan = input   ("| Golongan Jabatan  [1/2/3] | : ")
pendidikan = input         ("| Pendidikan [SMA/D1/D3/S1] | : ")
jumlahjam_kerja = int(input("| Jumlah Jam Kerja          | : "))

# Proses Golongan Jabatan
if golongan_jabatan=="1" :
    persentase1 = 5/100*300000
elif golongan_jabatan=="2" :
    persentase1 = 10/100*300000
elif golongan_jabatan=="3" :
    persentase1 = 15/100*300000
else :
    golongan_jabatan == "Golongan Jabatan Tidak Ditemukan"
    persentase1 = 0

# Proses Tunjangan Pendidikan
if pendidikan=="SMA" or pendidikan=="sma" or pendidikan=="Sma" or pendidikan=="sMa" or pendidikan=="smA" :
    persentase2 = 2.5/100*300000
elif pendidikan=="D1" or pendidikan=="d1" :
    persentase2 = 5/100*300000
elif pendidikan=="D3" or pendidikan=="d3" :
    persentase2 = 20/100*300000
elif pendidikan=="S1" or pendidikan=="s1" :
    persentase2 = 30/100*300000
else :
    pendidikan=="Tunjangan Pendidikan Tidak Ditemukan"
    persentase2 = 0

# Honor Lembur
minimumjam_kerja = 160
if jumlahjam_kerja > 160 :
    bonus = (jumlahjam_kerja-minimumjam_kerja)*3500
else :
    bonus = 0

# Gaji
gaji_pokok = 300000
total_gaji = int((gaji_pokok+persentase2+persentase1+bonus))

# Cetak Hasil
print("\nKaryawan yang Bernama : ",str(nama))
print("\nHonor yang Diterima")
print("Tunjangan Jabatan    : Rp.",(persentase1))
print("Tunjangan Pendidikan : Rp.",(persentase2))
print("Honor Lembur         : Rp.",(bonus))
print("                           __________________ +")
print(F"Total Gaji                Rp.{total_gaji}     ")