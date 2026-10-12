from flask import Flask, render_template, request, redirect
from pymongo import MongoClient

app = Flask(__name__)

# ==========================================
# KONEKSI MONGODB
# ==========================================
client = MongoClient("mongodb://localhost:27017/")

db = client["akademik"]
mahasiswa = db["mahasiswa"]

# ==========================================
# HALAMAN UTAMA
# ==========================================
@app.route('/')
def index():

    data_mahasiswa = mahasiswa.find()

    return render_template(
        'index.html',
        data=data_mahasiswa
    )


# ==========================================
# TAMBAH DATA
# ==========================================
@app.route('/tambah', methods=['POST'])
def tambah():

    data = {
        "nim": request.form['nim'],
        "nama": request.form['nama'],
        "jenis_kelamin": request.form['jenis_kelamin'],
        "jurusan": request.form['jurusan'],
        "semester": int(request.form['semester']),
        "alamat": request.form['alamat']
    }

    mahasiswa.insert_one(data)

    return redirect('/')


# ==========================================
# HAPUS DATA
# ==========================================
@app.route('/hapus/<nim>')
def hapus(nim):

    mahasiswa.delete_one({"nim": nim})

    return redirect('/')


# ==========================================
# UPDATE DATA
# ==========================================
@app.route('/update/<nim>', methods=['POST'])
def update(nim):

    data_baru = {
        "nama": request.form['nama'],
        "jenis_kelamin": request.form['jenis_kelamin'],
        "jurusan": request.form['jurusan'],
        "semester": int(request.form['semester']),
        "alamat": request.form['alamat']
    }

    mahasiswa.update_one(
        {"nim": nim},
        {"$set": data_baru}
    )

    return redirect('/')


# ==========================================
# MENJALANKAN SERVER
# ==========================================
if __name__ == '__main__':
    app.run(debug=True)