# ==========================================
# CRUD MongoDB Database Akademik dengan Python
# Database : akademik
# ==========================================

from pymongo import MongoClient

# ==========================================
# KONEKSI KE MONGODB
# ==========================================
client = MongoClient("mongodb://localhost:27017/")

# Memilih database
db = client["akademik"]

# Memilih collection mahasiswa
mahasiswa = db["mahasiswa"]

# ==========================================
# CREATE DATA
# ==========================================
def tambah_mahasiswa():
    data = {
        "nim": input("Masukkan NIM : "),
        "nama": input("Masukkan Nama : "),
        "jenis_kelamin": input("Jenis Kelamin : "),
        "jurusan": input("Jurusan : "),
        "semester": int(input("Semester : ")),
        "alamat": input("Alamat : ")
    }

    mahasiswa.insert_one(data)
    print("Data mahasiswa berhasil ditambahkan!\n")


# ==========================================
# READ DATA
# ==========================================
def tampilkan_mahasiswa():
    print("\n===== DATA MAHASISWA =====")

    for data in mahasiswa.find():
        print(f"""
ID              : {data['_id']}
NIM             : {data['nim']}
Nama            : {data['nama']}
Jenis Kelamin   : {data['jenis_kelamin']}
Jurusan         : {data['jurusan']}
Semester        : {data['semester']}
Alamat          : {data['alamat']}
====================================
""")


# ==========================================
# UPDATE DATA
# ==========================================
def update_mahasiswa():
    nim = input("Masukkan NIM yang ingin diupdate : ")

    data_baru = {
        "nama": input("Nama Baru : "),
        "jenis_kelamin": input("Jenis Kelamin Baru : "),
        "jurusan": input("Jurusan Baru : "),
        "semester": int(input("Semester Baru : ")),
        "alamat": input("Alamat Baru : ")
    }

    mahasiswa.update_one(
        {"nim": nim},
        {"$set": data_baru}
    )

    print("Data berhasil diupdate!\n")


# ==========================================
# DELETE DATA
# ==========================================
def hapus_mahasiswa():
    nim = input("Masukkan NIM yang ingin dihapus : ")

    mahasiswa.delete_one({"nim": nim})

    print("Data berhasil dihapus!\n")


# ==========================================
# MENU PROGRAM
# ==========================================
while True:
    print("""
===== MENU CRUD AKADEMIK =====
1. Tambah Mahasiswa
2. Tampilkan Mahasiswa
3. Update Mahasiswa
4. Hapus Mahasiswa
5. Keluar
""")

    pilihan = input("Pilih menu : ")

    if pilihan == "1":
        tambah_mahasiswa()

    elif pilihan == "2":
        tampilkan_mahasiswa()

    elif pilihan == "3":
        update_mahasiswa()

    elif pilihan == "4":
        hapus_mahasiswa()

    elif pilihan == "5":
        print("Program selesai.")
        break

    else:
        print("Pilihan tidak valid!\n")