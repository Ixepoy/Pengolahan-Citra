anak_ayam = int(input("Masukan Anak Ayam [1<= Anak Ayam <= 100]: "))
print("\nTek kotek kotek, anak ayam turun berkotek")
for i in range(anak_ayam, 0, -1) :
    if i > 1 :
        print(f"anak ayam turunlah {i} mati satu tinggalah {i - 1}")
    else :
        print(f"anak ayam turunlah {i} mati satu tinggallah induknya")