print("=" * 52)
print(" " * 14, "TOKO SUMBER BANGUN JAYA", " " * 20)
print("=" * 52)
print("Kode Jenis       Harga")
print("-" * 26)
print("PS   Pasir       Rp 278000")
print("BS   Batu Split  Rp 310000")
print("SM   Semen       Rp 54000")
print("-" * 26)

banyakjenis = int(input("\nBerapa Bahan Bangunan yang akan anda beli : "))
kodebahan = []
banyakbahan = []
harga_bahan = []
nama = []
harga = []
jumlahHarga = []

banyakbahan.append(int(input("Banyak Bahan : ")))

for i in range (banyakjenis) :
    print("\nBahan Bangunan Ke - ",str(i+1))
    kodebahan.append(input("Kode Bahan [PS/BS/SM] : "))
    if (kodebahan[i]=="PS" or kodebahan[i]=="Ps" or kodebahan[i]=="pS" or kodebahan[i]=="ps"):
        nama.append("Pasir")
        harga.append(278000)
    elif (kodebahan[i]=="BS" or kodebahan[i]=="Bs" or kodebahan[i]=="bS" or kodebahan[i]=="bs"):
        nama.append("Batu Split")
        harga.append(310000)
    elif (kodebahan[i]=="SM" or kodebahan[i]=="Sm" or kodebahan[i]=="sM" or kodebahan[i]=="sm"):
        nama.append("Semen")
        harga.append(54000)
        jumlahHarga = float(54000)*banyakjenis


print("=" * 52)
print("""NO Jenis Harga    Jumlah Jumhlah
    Bahan Perbahan Beli   Harga""")
print("=" * 52)
for i in range(banyakjenis):
    print("{}\t {}\t\t {}\t\t\t {}".format(i+1, nama[i], harga[i], banyakbahan[i], jumlahHarga))
print("=" * 52)