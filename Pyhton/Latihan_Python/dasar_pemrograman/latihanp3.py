var1 = 'Hello Python!'
var2 = "I Love Python"
print(var1)
print(var2)
print("var1 [0]", var1[0])
print("var2 [2:6]", var2[2:6])


#print("var1[0]", var1[:4]) #:4 = ngambil huruf dr 4 kebawah 
#print("var2[2:3]", var2[2:3])
#print(len(var3))

#menggunakan kutip 3
#print('''He said, "What's there ?"''')
#menggunakan karakter escape untuk tanda kutip tunggal
#print('He said,"What\'s There ?"')
#menggunakan karakter escape untuk tanda kutip ganda
#print("He said, \"What's there?\"")
# \n = dibacanya backslash newline

#print("This is \x61 \ngood example")
#print(r"This is \x61 \ngood example") # r = raw

#menggunakan posisi default
#default_order = "{}, {} dan {}".format("Budi", "Galih", "Ratna")
#print('\nUrutan Default')
#print(default_order)
#menggunakan argument posisi
#positional_order = "{1}, {0} dan {2}".format("Budi", "Galih", "Ratna")
#print('\nUrutan Berdasarkan Posisi')
#print(positional_order)
import fractions
print(fractions.Fraction(1,3))