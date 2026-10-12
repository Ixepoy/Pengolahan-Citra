from pymongo import MongoClient

# ======================================
# KONEKSI KE MONGODB
# ======================================
client = MongoClient("mongodb://localhost:27017/")

# Memilih database
db = client["toko_online"]

# Memilih collection
produk = db["produk"]

# ======================================
# FUNCTION TAMPIL DATA
# ======================================
def tampil_produk():

    print("\n===== DATA PRODUK =====\n")

    data_produk = produk.find()

    for data in data_produk:
        print(f"""
Kode Produk : {data['kode_produk']}
Nama Produk : {data['nama_produk']}
Harga       : Rp {data['harga']}
Stok        : {data['stok']}
==============================
""")


# ======================================
# FUNCTION TAMBAH DATA
# ======================================
def tambah_produk():

    kode = input("Masukkan Kode Produk : ")
    nama = input("Masukkan Nama Produk : ")
    harga = int(input("Masukkan Harga : "))
    stok = int(input("Masukkan Stok : "))

    data = {
        "kode_produk": kode,
        "nama_produk": nama,
        "harga": harga,
        "stok": stok
    }

    produk.insert_one(data)

    print("\nData berhasil ditambahkan!\n")


# ======================================
# MENU PROGRAM
# ======================================
while True:

    print("""
===== APLIKASI TOKO ONLINE =====
1. Tampilkan Produk
2. Tambah Produk
3. Keluar
""")

    pilih = input("Pilih Menu : ")

    if pilih == "1":
        tampil_produk()

    elif pilih == "2":
        tambah_produk()

    elif pilih == "3":
        print("Program selesai.")
        break

    else:
        print("Menu tidak tersedia!")