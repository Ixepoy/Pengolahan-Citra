Ayam = {
    'D': 2500,  
    'P': 2000,  
    'S': 1500   
}

def tampilkan_menu():
    print("GEROBAK FRIED CHICKEN")
    print("Kode  Jenis Potong  Harga")
    print("================================================")
    print("D     Dada        Rp. 2500")
    print("P     Paha        Rp. 2000")
    print("S     Sayap       Rp. 1500")
    print("================================================")

def hitung_total(pesanan):
    print("\nGEROBAK FRIED CHICKEN")
    print("================================================")
    print("No.  Jenis Potong   Harga   Banyak  Jumlah Harga")
    print("================================================")
    
    total_harga = 0
    for i, pesanan in enumerate(pesanan, 1):
        kode, jumlah = pesanan['kode'], pesanan['jumlah']
        harga = Ayam[kode]
        subtotal = harga * jumlah
        total_harga += subtotal
        print(f"{i:<4} {kode:<13} Rp. {harga:<5} {jumlah:<7} Rp. {subtotal}")
    
    pajak = total_harga * 0.1
    total_bayar = total_harga + pajak
    
    print("================================================")
    print(f"Jumlah Bayar :   Rp. ", int(total_harga))
    print(f"Pajak 10%    :   Rp. ", int(pajak))
    print(f"Total Bayar  :   Rp. ", int(total_bayar))

def main():
    tampilkan_menu()
    pesanan = []
    
    banyak_jenis = int(input("Banyak Jenis : "))
    
    for i in range(banyak_jenis):
        print(f"\nJenis Ke - {i + 1}")
        kode = input("Kode Potong [D/P/S] : ").upper()
        if kode not in Ayam:
            print("Kode tidak ada, masukkan (D/P/S).")
            continue
        jumlah = int(input("Banyak Potong : "))
        pesanan.append({'kode' : kode, 'jumlah' : jumlah})
    
    hitung_total(pesanan)
    

if __name__ == "_main_":
    main()