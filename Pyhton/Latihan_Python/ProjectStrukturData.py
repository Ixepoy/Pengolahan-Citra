import random

print("=" * 90)
print(" " * 33, "TOKO KOMPONEN PC ONLINE", " " * 35)
print("=" * 90)

# Data produk
produk_list = [
    # Prosesor
    {"Id": 1, "Nama": "Intel i5-12400F", "Harga": 2700000, "Kategori": "Prosesor", "Spesifikasi": "6-Core 12-Thread", "Stok": 5},
    {"Id": 2, "Nama": "AMD Ryzen 5 5600", "Harga": 2500000, "Kategori": "Prosesor", "Spesifikasi": "6-Core 12-Thread", "Stok": 4},
    {"Id": 3, "Nama": "Intel i7-13700K", "Harga": 5300000, "Kategori": "Prosesor", "Spesifikasi": "16-Core 24-Thread", "Stok": 3},
    {"Id": 4, "Nama": "AMD Ryzen 7 5800X", "Harga": 4400000, "Kategori": "Prosesor", "Spesifikasi": "8-Core 16-Thread", "Stok": 2},
    # VGA
    {"Id": 5, "Nama": "RTX 3060 12GB", "Harga": 5500000, "Kategori": "VGA", "Spesifikasi": "12GB GDDR6", "Stok": 3},
    {"Id": 6, "Nama": "RX 6600 8GB", "Harga": 4800000, "Kategori": "VGA", "Spesifikasi": "8GB GDDR6", "Stok": 5},
    {"Id": 7, "Nama": "RTX 4060 Ti", "Harga": 6600000, "Kategori": "VGA", "Spesifikasi": "16GB GDDR6", "Stok": 2},
    {"Id": 8, "Nama": "GTX 1650 4GB", "Harga": 2800000, "Kategori": "VGA", "Spesifikasi": "4GB GDDR6", "Stok": 6},
    # RAM
    {"Id": 9, "Nama": "Corsair Vengeance 16GB", "Harga": 950000, "Kategori": "RAM", "Spesifikasi": "DDR4 3200MHz", "Stok": 6},
    {"Id": 10, "Nama": "Kingston Fury 8GB", "Harga": 500000, "Kategori": "RAM", "Spesifikasi": "DDR4 2666MHz", "Stok": 7},
    {"Id": 11, "Nama": "Team Elite 16GB", "Harga": 880000, "Kategori": "RAM", "Spesifikasi": "DDR4 3200MHz", "Stok": 5},
    {"Id": 12, "Nama": "G.Skill Trident Z 32GB", "Harga": 1550000, "Kategori": "RAM", "Spesifikasi": "DDR4 3600MHz", "Stok": 3},
    # SSD
    {"Id": 13, "Nama": "Samsung 970 EVO 1TB", "Harga": 1350000, "Kategori": "SSD", "Spesifikasi": "NVMe Gen3", "Stok": 4},
    {"Id": 14, "Nama": "WD Green 480GB", "Harga": 650000, "Kategori": "SSD", "Spesifikasi": "SATA III", "Stok": 8},
    {"Id": 15, "Nama": "Kingston NV2 1TB", "Harga": 1200000, "Kategori": "SSD", "Spesifikasi": "NVMe Gen4", "Stok": 6},
    {"Id": 16, "Nama": "Crucial BX500 240GB", "Harga": 500000, "Kategori": "SSD", "Spesifikasi": "SATA III", "Stok": 9},
]

riwayat_stack = []
keranjang = []
bonus_items = ["Fan RGB", "Thermal Paste", "Mousepad Gaming"]
minimum_belanja_bonus = 3000000
metode_pembayaran = {1: "Transfer Bank", 2: "COD"}

def tampilkan_produk(kategori=None):
    print(f"{'ID':<5} {'Nama':<30} {'Harga':<15} {'Kategori':<10} {'Spesifikasi':<20} {'Stok':<5}")
    print("-" * 100)
    for item in produk_list:
        if kategori is None or item["Kategori"] == kategori:
            print(f"{item['Id']:<5} {item['Nama']:<30} RP. {item['Harga']:<12} {item['Kategori']:<10} {item['Spesifikasi']:<20} {item['Stok']:<5}")
    print("-" * 100)

while True:
    print("Kategori: 1. Prosesor  2. VGA  3. RAM  4. SSD")
    try:
        kategori = int(input("Pilih kategori (1-4): "))
        kategori_nama = {1: "Prosesor", 2: "VGA", 3: "RAM", 4: "SSD"}.get(kategori)
        if not kategori_nama:
            print("Kategori tidak valid."); continue
    except:
        print("Input harus angka."); continue

    tampilkan_produk(kategori_nama)

    try:
        pilih_id = int(input("Masukkan ID produk: "))
        produk = next((p for p in produk_list if p["Id"] == pilih_id and p["Kategori"] == kategori_nama), None)
        if not produk:
            print("ID tidak tersedia dalam kategori."); continue
    except:
        print("ID harus angka."); continue

    try:
        jumlah = int(input("Jumlah yang ingin dibeli: "))
        if jumlah <= 0 or jumlah > produk["Stok"]:
            print("Stok tidak cukup."); continue
    except:
        print("Jumlah harus angka."); continue

    produk["Stok"] -= jumlah
    keranjang.append({
        "Id": produk["Id"],
        "Nama": produk["Nama"],
        "Harga": produk["Harga"],
        "Jumlah": jumlah,
        "Kategori": produk["Kategori"],
        "Spesifikasi": produk["Spesifikasi"],
        "Subtotal": produk["Harga"] * jumlah
    })

    riwayat_stack.append(f"Beli {produk['Nama']} x{jumlah}")
    if input("Tambah produk lain? (Y/N): ").lower() == "n":
        break

# Ringkasan
if not keranjang:
    print("Keranjang kosong."); exit()

total = sum(item["Subtotal"] for item in keranjang)
diskon = 0.1 if total > 5000000 else 0
setelah_diskon = int(total * (1 - diskon))
bonus = random.choice(bonus_items) if setelah_diskon >= minimum_belanja_bonus else None

# Data Pembeli
print("\nSilakan isi data pembeli:")
nama = input("Nama Penerima   : ")
alamat = input("Alamat          : ")
telp = input("No. Telepon     : ")
kode_pos = input("Kode Pos        : ")

print("\nMetode Pembayaran:")
for k, v in metode_pembayaran.items():
    print(f"{k}. {v}")
while True:
    try:
        metode = int(input("Pilih metode: "))
        if metode in metode_pembayaran: break
        else: print("Metode tidak tersedia.")
    except:
        print("Input tidak valid!")

# Konfirmasi dan Struk
konfirmasi = input("Lanjutkan pembayaran? (Y/N): ").lower()
if konfirmasi == "y":
    print("\n" + "=" * 40)
    print(" " * 10 + "STRUK PEMBAYARAN")
    print("=" * 40)
    print(f"Nama Penerima : {nama}")
    print(f"Alamat        : {alamat}")
    print(f"No. Telepon   : {telp}")
    print(f"Kode Pos      : {kode_pos}")
    print("-" * 40)
    for item in keranjang:
        print(f"{item['Nama']} x{item['Jumlah']} = RP. {item['Subtotal']:,}")
    print("-" * 40)
    print(f"Total Sebelum Diskon : RP. {total:,}")
    print(f"Diskon 10%            : RP. {int(total * diskon):,}" if diskon else "Diskon              : -")
    print(f"Total Dibayar         : RP. {setelah_diskon:,}")
    if bonus:
        print(f"Bonus Pembelian       : {bonus}")
    print(f"Metode Pembayaran     : {metode_pembayaran[metode]}")
    if metode == 1:
        print("Bank Tujuan           : BCA")
        print("No. Rekening          : 123-456-7890")
    print("=" * 40)
    print("Terima kasih telah belanja di Toko Komponen PC!\n")

    print("Riwayat Transaksi:")
    while riwayat_stack:
        print(">>", riwayat_stack.pop())
else:
    print("Transaksi dibatalkan.")
