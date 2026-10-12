
from tabulate import tabulate # Buat bikin tabel
import pandas as pd # Buat data
import os # Buat memanggil clear command
import time # Buat mendelay

# akun sama sistem penjualan gak akan di include, karena agak kompleks buat implementasinya 
# Environmnet Variables

# Zona Data
daftar_sepatu = pd.read_csv("daftar-sepatu.csv")
harga_sepatu = pd.read_csv("daftar-sepatu.csv") # dibuat ganda karena tidak akan di konversi
daftar_sepatu['Harga'] = daftar_sepatu['Harga'].map(lambda x: f"Rp.{x:,}".format(x).replace(',','.')) # Konversi value menjadi string
merek_sepatu = pd.read_csv("merek-sepatu.csv")

# Kamus
Tabel = ["Nama Model Sepatu","Kode Model","Ukuran","Warna","Harga"]
Hasil = ["Nama Model Sepatu","Warna","Harga"]
Struk = ["Nama Model Sepatu","Warna","Ukuran","Harga"]
Merek = ["No","Nama Merek Sepatu"]

# Temp buat Proof of concepts
# users = {
#     "user": "1234",
#     "user2": "password2"
# }

Hasil_Belanja = pd.DataFrame(Hasil)

#
list_ukur = []
list_jual = []
list_model = []
list_warna = []

# Menu Awal
def greetings():
    os.system('cls||clear')
    print("======================================")
    print("  SELAMAT DATANG DI TOKO SEPATU UBSI  ")
    print("======================================")
    time.sleep(1)

# def register():
#     os.system('cls||clear')
#     print("=== REGISTER ===")
#     username = input("Username: ")
#     
#     # Check if username exists
#     if any(user["username"] == username for user in users):
#         print("Username already exists. Please try another.")
#         time.sleep(1)
#         return register()
#     
#     password = getpass.getpass("Password: ")
#     users.append({"username": username, "password": password})
#     print("Registration successful! Please log in.")
#     time.sleep(1)

# Sementara
# def login():
#     username = input("Username: ")
#     password = getpass.getpass("Password: ")  # Hides the password input
# 
#     if username in users and users[username] == password:
#         print("Login successful!")
#         daftarmenu()
#     else:
#         print("Login failed. Please check your username and password.")

def daftarmenu():
    while True:
        
        os.system('cls||clear')
        print(" TOKO SEPATU UBSI ")
        print("======================================")
        print("1. Beli Sepatu")
        print("2. Keluar")
        print("======================================")
        pilihan = int(input(f"Silahkan pilih opsi : "))

        if pilihan == 1:
            shoe = opsi_beli()
            os.system('cls||clear')
            opsi_hasil(shoe)
        elif pilihan == 2:
            break
        else:
            print("Pilihan Tidak Benar!")
        break

# Definisi
def opsi_sepatu():
    os.system('cls||clear')
    print(tabulate(merek_sepatu[Merek], headers='keys', tablefmt="psql", showindex=False))

# Define functions
def opsi_model():
    user_input = input("Masukan Kode Model (e.g., A-AA): ")
    if user_input in daftar_sepatu["Kode Model"].values: # dia nyari user_input sesuai ama kode model apa enggak
        return user_input
    else:
        print("Silahkan masukan Kode Model yang benar")
        return opsi_model()

def opsi_ukur():
    user_input = int(input("Masukan Ukuran Sepatu (e.g., 47): "))
    for ukuran in daftar_sepatu["Ukuran"].values:
        # String "39 - 45" di konversi jadi Int, 39 Sebagai Min 45 Sebagai Max
        # Angka di input sebagai value, jadi angka di cek apakah lebih dari 45 atau kurang dari 39, jika masih diantara maka sukses
        min_ukuran, max_ukuran = map(int, ukuran.split(" - "))
        if min_ukuran <= user_input <= max_ukuran:
            return user_input
    print("Silahkan masukan Ukuran yang benar")
    return opsi_ukur()

# peringatan
# Untuk handling item duplikat tidak bisa untuk saat ini
def opsi_beli():
    opsi_sepatu()
    opsi_luas = int(input(f"Input Daftar : "))
    opsi_jumlah = int(input(f"Input Jumlah : "))
    shoe = []
    for i in range (opsi_jumlah):
        os.system('cls||clear')
        if opsi_luas == 1:
            dft_spt = daftar_sepatu[Tabel]
        else:
            dft_spt = daftar_sepatu.loc[daftar_sepatu['Kode Merek'] == opsi_luas, Tabel]
        print("\nSepatu Ke - ", str(i+1))
        print(tabulate(dft_spt[Tabel], headers='keys', tablefmt="psql", showindex=False))
        model = opsi_model()
        ukur = opsi_ukur()
        shoe.append({"Kode Model": model, "Ukuran": ukur}) # data disini buat ditaruh di shoe 
    return pd.DataFrame(shoe) # kemudian dibalikan

def opsi_hasil(shoe):
    asal = daftar_sepatu.loc[daftar_sepatu['Kode Model'].isin(shoe['Kode Model']), Hasil]
    calc = harga_sepatu.loc[harga_sepatu['Kode Model'].isin(shoe['Kode Model']), Hasil] # yang bikin berbeda dari asal, 
    soei = pd.concat([shoe.reset_index(drop=True),asal.reset_index(drop=True)], axis=1, join="inner") # penggabungan
    reso = calc[["Harga"]].sum() # harga dijumlahkan semua
    print(tabulate(soei[Struk], headers='keys', tablefmt="rst", showindex=False))
    print(f"Jumlah Harga: Rp.{int(reso.values[0])}")
    opsi_bayar(reso)

# disini buat hitung operasi pembayaran
def opsi_bayar(reso):
    bayar = int(input("Jumlah Pembayaran : Rp."))
    hasil = bayar - reso.values[0]
    while True:
        if hasil >= 0:
            print(f"Uang Kembalian : Rp.{hasil}")
            print("Pembayaran Berhasil")
            break
        else:
            print("Uang Tidak Mencukupi")
            print("Pembayaran Gagal")
            break
        break

# WIP Buat daftar menu bagian jual
# def daftarjual():
#     while True:
#         os.system('cls||clear')
#         print("TOKO SEPATU UBSI  Versi Alpha  ")
#         print("======================================")
#         print("1. Daftar Merek Baru")
#         print("2. Daftar Sepatu Baru")
#         print("3. Kembali")
#         print("======================================")
#         pilihan = int(input(f"Silahkan pilih opsi : "))
# 
#         if pilihan == 1:
#             break
#         elif pilihan == 2:
#             break
#         elif pilihan == 3:
#             return daftarmenu()
#         else:
#             print("Pilihan Tidak Benar!")
#         break

# # WIP
# def jual_merek():
#     nama_merek = ("Masukan Nama Merek : ")
#     if nama_merek in merek_sepatu["Nama Merek Sepatu"].values:
#         pass

def main():
    os.system("cls||clear")
    greetings()
    daftarmenu()

if __name__ == "__main__" :
    main()