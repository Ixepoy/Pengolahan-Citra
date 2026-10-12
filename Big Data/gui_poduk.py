from tkinter import *
from tkinter import messagebox
from pymongo import MongoClient

# =========================================
# KONEKSI KE MONGODB
# =========================================
client = MongoClient("mongodb://localhost:27017/")
db = client["toko_online"]
produk = db["produk"]

# =========================================
# FUNCTION TAMPIL DATA
# =========================================
def tampil_data():

    listbox.delete(0, END)

    data_produk = produk.find()

    for data in data_produk:

        kode = data.get("kode_produk", "-")
        nama = data.get("nama_produk", "-")
        harga = data.get("harga", 0)
        stok = data.get("stok", 0)

        teks = f"{kode} | {nama} | Rp {harga} | Stok : {stok}"

        listbox.insert(END, teks)


# =========================================
# FUNCTION TAMBAH DATA
# =========================================
def tambah_data():

    kode = entry_kode.get()
    nama = entry_nama.get()
    harga = entry_harga.get()
    stok = entry_stok.get()

    # Validasi input
    if kode == "" or nama == "" or harga == "" or stok == "":
        messagebox.showwarning("Peringatan", "Semua data harus diisi!")
        return

    data = {
        "kode_produk": kode,
        "nama_produk": nama,
        "harga": int(harga),
        "stok": int(stok)
    }

    produk.insert_one(data)

    messagebox.showinfo("Sukses", "Data berhasil ditambahkan!")

    # Kosongkan input
    entry_kode.delete(0, END)
    entry_nama.delete(0, END)
    entry_harga.delete(0, END)
    entry_stok.delete(0, END)

    tampil_data()


# =========================================
# GUI TKINTER
# =========================================
root = Tk()
root.title("Aplikasi Toko Online MongoDB")
root.geometry("700x500")
root.configure(bg="lightblue")

# =========================================
# JUDUL
# =========================================
judul = Label(
    root,
    text="APLIKASI DATA PRODUK",
    font=("Arial", 18, "bold"),
    bg="lightblue"
)

judul.pack(pady=10)

# =========================================
# FRAME INPUT
# =========================================
frame_input = Frame(root, bg="lightblue")
frame_input.pack(pady=10)

# Kode Produk
Label(frame_input, text="Kode Produk", bg="lightblue").grid(row=0, column=0, padx=5, pady=5)

entry_kode = Entry(frame_input, width=30)
entry_kode.grid(row=0, column=1)

# Nama Produk
Label(frame_input, text="Nama Produk", bg="lightblue").grid(row=1, column=0, padx=5, pady=5)

entry_nama = Entry(frame_input, width=30)
entry_nama.grid(row=1, column=1)

# Harga
Label(frame_input, text="Harga", bg="lightblue").grid(row=2, column=0, padx=5, pady=5)

entry_harga = Entry(frame_input, width=30)
entry_harga.grid(row=2, column=1)

# Stok
Label(frame_input, text="Stok", bg="lightblue").grid(row=3, column=0, padx=5, pady=5)

entry_stok = Entry(frame_input, width=30)
entry_stok.grid(row=3, column=1)

# =========================================
# BUTTON
# =========================================
btn_tambah = Button(
    root,
    text="Tambah Produk",
    width=20,
    bg="green",
    fg="white",
    command=tambah_data
)

btn_tambah.pack(pady=10)

btn_tampil = Button(
    root,
    text="Tampilkan Produk",
    width=20,
    bg="blue",
    fg="white",
    command=tampil_data
)

btn_tampil.pack(pady=5)

# =========================================
# LISTBOX
# =========================================
listbox = Listbox(root, width=90, height=15)
listbox.pack(pady=10)

# =========================================
# MENJALANKAN GUI
# =========================================
root.mainloop()