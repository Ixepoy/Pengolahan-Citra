# Fungsi untuk input elemen-elemen matriks
def input_matriks(nama_matriks):
    matriks = []
    print(f"Masukkan elemen-elemen matriks {nama_matriks} (3x3):")
    for i in range(3):
        baris = []
        for j in range(3):
            elemen = int(input(f"Masukkan elemen {nama_matriks}[{i+1}][{j+1}]: "))
            baris.append(elemen)
        matriks.append(baris)
    return matriks

# Fungsi untuk menjumlahkan dua matriks
def jumlahkan_matriks(A, B):
    C = []
    for i in range(3):
        baris = []
        for j in range(3):
            baris.append(A[i][j] + B[i][j])
        C.append(baris)
    return C

# Fungsi untuk menampilkan matriks
def tampilkan_matriks(matriks):
    for baris in matriks:
        print(baris)

# Input matriks A
matriks_A = input_matriks("A")

# Input matriks B
matriks_B = input_matriks("B")

# Menjumlahkan matriks A dan B untuk mendapatkan matriks C
matriks_C = jumlahkan_matriks(matriks_A, matriks_B)

# Menampilkan hasil matriks C
print("\nMatriks A:")
tampilkan_matriks(matriks_A)

print("\nMatriks B:")
tampilkan_matriks(matriks_B)

print("\nHasil Penjumlahan Matriks A dan B (Matriks C):")
tampilkan_matriks(matriks_C)
