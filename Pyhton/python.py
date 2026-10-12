#nama = "asep" #ini namanya variabel string, string = teks dan string harus make tanda petik ""
#umur = 50 ini namanya variabel number gk perlu pakai tanda petik
#umur = 50.1 ini namanya variabel desimal
#is_male = False ini True or False
#print("nama dia\"asep\"")

#LIST
# list pakai []
"""angka = [1, 2, 3, 4, 5,]
friends = ["Adi", "Coqi", "Asep", "Supri", "ilham"]
friends[1:1] = ["nanas", "supra"]
print(friends.index("Adi"))"""

#TUPLE
# tuple tidak bisa dimodifikasi setelah dibuat, beda dgn list
# tuple pakai ()
"""coordinates = (1, 2)
print(coordinates[1])"""

#FUNCTIONS
# cocok untuk menjalankan beberapa perintah dan dimasukan ke dalam function
"""def say_hi():
    print("Hi")
say_hi()"""

#RETURN STATEMENTS
# hasil dari return bisa disimpan ke dlm variabel, lebih bagus ketimbang print
"""def cube(num):
    return num*num*num
print(cube(3))"""

#IF STATEMENTS
"""is_male = False
is_tall = True

if is_male:
    print("You are a male")
elif is_male or is_tall:
    print("You are either tall or a male")
else:
    print("You are not a male")"""

#IF STATEMENTS COMPARISON
"""def max_num(num1, num2, num3):
    if num1 >= num2 and num1 >= num3:
        return num1
    elif num2 >= num1 and num2 >= num3:
        return num2
    else:
        return num3

print(max_num(3,4,5))"""

#KALKULATOR
"""num1 = int(input("Masukan angka pertama : "))
op = input("Masukan operator [* / + -] : ")

if op == "*":
    print(num1 * num2)
elif op == "/":
    print(num1/num2)
elif op == "+":
    print(num1 + num2)
elif op == "-":
    print(num1 - num2)
else:
    #print("Tolong masukan operator yang sesuai")"""

#DICTIONARIES
# Jan = Key, Januari = Value dan setiap key harus unik/tdk boleh sama
"""month_conversions = {
    "Jan": "Januari",
    "Feb": "Febuari",
    "Mar": "Maret",
    "Apr": "April",
    "Mei": "Mei",
    "Jun": "Juni",
    "Jul": "Juli",
    "Agus": "Agustus",
    "Sep": "September",
    "Okt": "Oktober",
    "Nov": "November",
    "Des": "Desember"
}
print(month_conversions[input("Masukan Key [Harus Berupa Nama]: ")])"""

#WHILE LOOP
"""i = 1
while i<=10:
    print(i)
    i = i + 1
print("Looping Selesai")"""

#GAME TEBAK KATA
"""kata_rahasia = "Sate"
kata_tebak = ""
maks_tebak = 3
jumlah_tebakan = 0
kehabisan_tebakan = False

while kata_tebak != kata_rahasia and not (kehabisan_tebakan):
    if jumlah_tebakan < maks_tebak:
        kata_tebak = input("Masukan Kata Rahasia : ")
        jumlah_tebakan = jumlah_tebakan + 1
    else:
        kehabisan_tebakan = True

if kehabisan_tebakan:
    print("Anda Gagal!")
else:
    print("Anda Berhasil!")"""

#FOR LOOP
# for loop digunakan saat tahu pasti berapa kali harus perulangan 
# while loop digunakan saat tidak tahu pasti berapa kali harus perulangan
# jika parameter "kata" diisi dengan "apa" maka for loop akan berulang sebanyak 3 kali, karena kata apa terdiri dari 3 huruf
# spasi dan tanda baca juga dihitung sebagai satu karakter
"""for i in range(3):
    print("Mengecek file ke-", i)"""

#EXPONENT FUNCTION
"""def raise_power (base_num, pow_num):
    result = 1
    for index in range(pow_num):
        result = result * base_num
    return result
print(raise_power(2,3))"""

#2D LIST (LISTS DIDALAM LIST) & MESTED LOOPS
#dibawah adalah contoh nested list
# rows = baris kebawah, column = baris ke samping
# 4 rows dan 3 column
"""number_grid = [
    [1, 2 ,3],
    [4, 5, 6],
    [7, 8, 9],
    [0]
]
print(number_grid[0][0])"""

#nested for loop
"""number_grid = [
    [1, 2 ,3],
    [4, 5, 6],
    [7, 8, 9],
    [0]
]
for abc in number_grid:
    for col in abc:
        print(col)"""

#TRANSLATOR
"""def translate(kata):
    translation = ""
    for letter in kata:
        if letter.lower() in "aiueo":
            if letter.isupper():
                translation = translation + "G"
            else:
                translation = translation + "g"
        else:
            translation = translation + letter
    return translation
print(translate(input("Masukan sebuah Kata : ")))"""

#TRY/EXCEPT
"""
if-else digunakan untuk LOGIKA PROGRAM.
Kamu menggunakannya ketika kamu tahu apa yang sedang kamu periksa 
dan ingin membuat keputusan berdasarkan nilai tersebut.

try-except digunakan untuk MENANGANI KEGAGALAN.
Kamu menggunakannya ketika sebuah operasi berisiko gagal 
(biasanya karena faktor dari luar program) 
dan kamu ingin mencegah program tersebut berhenti tiba-tiba.
"""
"""try:
    angka = 10/0
    number = int(input("Masukan angka : "))
    print(number)
except ZeroDivisionError as err:
    print(err)
except ValueError:
    print("Invalid Input")"""

#READING FILES
# r = read, w = write, r+ = read & write, a = append
"""file_karyawan = open("karyawan.txt", "r+")
for karyawan in file_karyawan:
    print(karyawan)
file_karyawan.close()"""

#WRITING FILES
"""file_karyawan = open("karyawan.txt", "a")
file_karyawan.write("\nHarimau, Singa")
file_karyawan.close()"""

#MODULES & PIP
# modul adalah kode yang sudah siap pakai dan tinggal di import
# pip adalah cara install modulnya lewat cmd

#CLASSES & OBJECT
# data type cmn ada string, int, boolean dll
# class dipake untuk ngebuat data type baru
"""from murid import murid
murid1 = murid("asep", "IT", 4.0, False)
print(murid1.nama)"""

#QUIZ PILIHAN GANDA
from Question import Question

pilihan_ganda = [
    "Apa warna dari buah apel?\n A. Hijau B. Kuning C. Merah",
    "Apa warna dari buah pisang?\n A. Hitam B. Kuning C. Coklat",
    "Apa warna dari buah jeruk?\n A. Oranye B. Merah C. Hijau"
]

pertanyaannya = [
    Question(pilihan_ganda[0], "C"),
    Question(pilihan_ganda[1], "B"),
    Question(pilihan_ganda[2], "A"),
]

def run_test(pertanyaan_pertanyaan):
    nilai = 0
    for pertanyaan in pertanyaan_pertanyaan:
        jawaban = input()
    