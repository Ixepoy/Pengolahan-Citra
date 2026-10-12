# input
nama_mahasiswa = str(input("Input Nama : "))
jurusan = input("Input Jurusan [SI/SIA/IN/KOM]: ")
nis = int(input("Input NIS : "))

# Proses
if jurusan=="SI" or jurusan=="si" or jurusan=="Si" or jurusan=="sI":
    nama_prodi = "Sistem Informasi"
    harga=2400000
elif jurusan=="SIA" or jurusan=="sia" or jurusan=="Sia" or jurusan=="sIa" or jurusan=="siA":
    nama_prodi = "Sistem Informasi Akuntasi"
    harga = 2000000
elif jurusan=="IN" or jurusan=="in" or jurusan=="In" or jurusan=="iN":
    nama_prodi = "Informatika"
    harga = 3400000
elif jurusan=="KOM" or jurusan=="kom" or jurusan=="Kom" or jurusan=="kOm" or jurusan=="koM":
    nama_prodi = "Ilmu Komunikasi"
    harga = 2800000
else :
    jurusan == "Data Tidak Valid"
    nama_prodi ="Data Tidak Valid"
    harga = 0

# Cetak Hasil
print("\n------------------------------------------")
print("Pendaftaran Mahasiswa Baru".center(50))
print("Nama Mahasiswa : ",str(nama_mahasiswa))
print("Jurusan yang dipilih : ",str(nama_prodi))
print("Kode Jurusan : ",str(jurusan))
print("Harga : Rp.",(harga))
print("-------------------------------------------")