# Logical Operators = evaluate multiple conditions (or, and, not)
# or = at least one conditions must be true
# and = both conditions must be true
# not = inverts the conditions (not False, not True)

# OR
#temp = 25
#is_raining =  False # is_raining harus bernilai true jika ingin dipilih

#if temp > 35 or temp < 0 or is_raining:
    #print("The outdoor event is cancelled")
#else :
    #print("The outdoor event is still schedulled")

# AND
temp = -5
is_sunny = True # NOT = it inverts the conditions, if is_sunny = true then now its false

if temp >= 28 and is_sunny:
    print("It is HOT outside")
    print("It is sunny")
elif temp <= 0 and is_sunny:
    print("It is COLD outside")
    print("It is sunny")
elif 28 > temp > 0 and is_sunny:
    print("It is WARM outside")
    print("It is sunny")
elif temp >= 28 and not is_sunny:
    print("It is HOT outside")
    print("It is CLOUDY")
elif temp <= 0 and not is_sunny:
    print("It is COLD outside")
    print("It is CLOUDY")
elif 28 > temp > 0 and not is_sunny:
    print("It is WARM outside")
    print("It is CLOUDY")