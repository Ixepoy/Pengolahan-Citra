import pandas as pd
# Variable yg berulang menggunakan List/matriks
list_nim = []
list_nama = []
list_uts = []
list_uas = []
list_total = []

ulang = int(input(" Ulang : "))
for i in range(ulang):
    print("Data ke - ", str(i+1))
    list_nim.append(input("NIM : "))
    list_nama.append(input("Nama : "))
    list_uts.append(int(input("Nilai UTS : ")))
    list_uas.append(int(input("Nilai UAS : ")))
# Proses
for i in range(ulang):
    list_total.append((list_uas[i] + list_uts[i]) / 2)

tamu = {
    "NIM" : list_nim,
    "Nama Lengkap" : list_nama,
    "Nilai UTS" : list_uts,
    "Nilai UAS" : list_uas,
    "Rata-Rata" : list_total,
}
data_tamu = pd.DataFrame(tamu)
# Cetak
print("=================== Daftar Nilai =====================")
print(data_tamu)
print("======================================================")