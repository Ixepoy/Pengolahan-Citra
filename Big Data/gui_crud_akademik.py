from tkinter import *
from tkinter import messagebox
from pymongo import MongoClient

# ==========================================
# KONEKSI KE MONGODB
# ==========================================
client = MongoClient("mongodb://localhost:27017/")

db = client["akademik"]

mahasiswa = db["mahasiswa"]

# ==========================================
# FUNCTION TAMPIL DATA
# ==========================================
def tampil_data():

    listbox.delete(0, END)

    data_mahasiswa = mahasiswa.find()

    for data in data_mahasiswa:

        nim = data.get("nim", "-")
        nama = data.get("nama", "-")
        jk = data.get("jenis_kelamin", "-")
        jurusan = data.get("jurusan", "-")
        semester = data.get("semester", "-")
        alamat = data.get("alamat", "-")

        teks = f"{nim} | {nama} | {jk} | {jurusan} | Semester {semester} | {alamat}"

        listbox.insert(END, teks)


# ==========================================
# FUNCTION TAMBAH DATA
# ==========================================
def tambah_data():

    nim = entry_nim.get()
    nama = entry_nama.get()
    jk = entry_jk.get()
    jurusan = entry_jurusan.get()
    semester = entry_semester.get()
    alamat = entry_alamat.get()

    if nim == "" or nama == "" or jk == "" or jurusan == "" or semester == "" or alamat == "":
        messagebox.showwarning("Peringatan", "Semua data harus diisi!")
        return

    data = {
        "nim": nim,
        "nama": nama,
        "jenis_kelamin": jk,
        "jurusan": jurusan,
        "semester": int(semester),
        "alamat": alamat
    }

    mahasiswa.insert_one(data)

    messagebox.showinfo("Sukses", "Data mahasiswa berhasil ditambahkan!")

    kosongkan_input()
    tampil_data()


# ==========================================
# FUNCTION UPDATE DATA
# ==========================================
def update_data():

    nim = entry_nim.get()

    data_baru = {
        "nama": entry_nama.get(),
        "jenis_kelamin": entry_jk.get(),
        "jurusan": entry_jurusan.get(),
        "semester": int(entry_semester.get()),
        "alamat": entry_alamat.get()
    }

    hasil = mahasiswa.update_one(
        {"nim": nim},
        {"$set": data_baru}
    )

    if hasil.modified_count > 0:
        messagebox.showinfo("Sukses", "Data berhasil diupdate!")
    else:
        messagebox.showwarning("Info", "Data tidak ditemukan atau tidak berubah.")

    kosongkan_input()
    tampil_data()


# ==========================================
# FUNCTION HAPUS DATA
# ==========================================
def hapus_data():

    nim = entry_nim.get()

    hasil = mahasiswa.delete_one({"nim": nim})

    if hasil.deleted_count > 0:
        messagebox.showinfo("Sukses", "Data berhasil dihapus!")
    else:
        messagebox.showwarning("Info", "Data tidak ditemukan!")

    kosongkan_input()
    tampil_data()


# ==========================================
# FUNCTION KOSONGKAN INPUT
# ==========================================
def kosongkan_input():

    entry_nim.delete(0, END)
    entry_nama.delete(0, END)
    entry_jk.delete(0, END)
    entry_jurusan.delete(0, END)
    entry_semester.delete(0, END)
    entry_alamat.delete(0, END)


# ==========================================
# GUI TKINTER
# ==========================================
root = Tk()

root.title("CRUD Akademik MongoDB")
root.geometry("900x600")
root.configure(bg="lightblue")

# ==========================================
# JUDUL
# ==========================================
judul = Label(
    root,
    text="APLIKASI CRUD MAHASISWA",
    font=("Arial", 20, "bold"),
    bg="lightblue"
)

judul.pack(pady=10)

# ==========================================
# FRAME INPUT
# ==========================================
frame = Frame(root, bg="lightblue")
frame.pack(pady=10)

# NIM
Label(frame, text="NIM", bg="lightblue").grid(row=0, column=0, padx=5, pady=5)

entry_nim = Entry(frame, width=35)
entry_nim.grid(row=0, column=1)

# Nama
Label(frame, text="Nama", bg="lightblue").grid(row=1, column=0, padx=5, pady=5)

entry_nama = Entry(frame, width=35)
entry_nama.grid(row=1, column=1)

# Jenis Kelamin
Label(frame, text="Jenis Kelamin", bg="lightblue").grid(row=2, column=0, padx=5, pady=5)

entry_jk = Entry(frame, width=35)
entry_jk.grid(row=2, column=1)

# Jurusan
Label(frame, text="Jurusan", bg="lightblue").grid(row=3, column=0, padx=5, pady=5)

entry_jurusan = Entry(frame, width=35)
entry_jurusan.grid(row=3, column=1)

# Semester
Label(frame, text="Semester", bg="lightblue").grid(row=4, column=0, padx=5, pady=5)

entry_semester = Entry(frame, width=35)
entry_semester.grid(row=4, column=1)

# Alamat
Label(frame, text="Alamat", bg="lightblue").grid(row=5, column=0, padx=5, pady=5)

entry_alamat = Entry(frame, width=35)
entry_alamat.grid(row=5, column=1)

# ==========================================
# FRAME BUTTON
# ==========================================
frame_button = Frame(root, bg="lightblue")
frame_button.pack(pady=10)

btn_tambah = Button(
    frame_button,
    text="Tambah",
    width=15,
    bg="green",
    fg="white",
    command=tambah_data
)

btn_tambah.grid(row=0, column=0, padx=5)

btn_tampil = Button(
    frame_button,
    text="Tampilkan",
    width=15,
    bg="blue",
    fg="white",
    command=tampil_data
)

btn_tampil.grid(row=0, column=1, padx=5)

btn_update = Button(
    frame_button,
    text="Update",
    width=15,
    bg="orange",
    fg="white",
    command=update_data
)

btn_update.grid(row=0, column=2, padx=5)

btn_hapus = Button(
    frame_button,
    text="Hapus",
    width=15,
    bg="red",
    fg="white",
    command=hapus_data
)

btn_hapus.grid(row=0, column=3, padx=5)

# ==========================================
# LISTBOX
# ==========================================
listbox = Listbox(
    root,
    width=120,
    height=18
)

listbox.pack(pady=20)

# ==========================================
# MENJALANKAN GUI
# ==========================================
tampil_data()

root.mainloop()