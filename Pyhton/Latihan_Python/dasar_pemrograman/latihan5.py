ulang = int(input("Masukan Data Perulangan : "))
for i in range(ulang) :
    print("Data Ke - " +str(i+1))
    nim=int(input("Masukan NIM Anda : "))
    uts=int(input("Masukan Nilai UTS Anda : "))
    uas=int(input("Masukan Nilai UAS : "))
    print("NIM Anda adalah %i nilai UTS anda %i nilai UAS anda %i" % (nim,uts,uas))
    print("----------------------------------\n")