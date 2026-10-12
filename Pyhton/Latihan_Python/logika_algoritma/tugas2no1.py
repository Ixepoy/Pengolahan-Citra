a = int(input("Masukan Bilangan A : "))
b = int(input("Masukan Bilangan B : "))
c = int(input("Masukan Bilangan C : "))
d = int(input("Masukan Bilangan D : "))
if a > b and a > c and a > d :
    print(f"\nBilangan Terbesar Adalah : {a} ")
elif b > c and b > d :
    print(f"\nBilangan Terbesar Adalah : {b}")
elif c > d :
    print(f"\nBilangan Terbesar Adalah : {c}")
else :
    print(f"\nBilangan Terbesar Adalah : {d}")