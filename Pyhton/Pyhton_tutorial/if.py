# if = Do some code only if some condition is True
# else do something else

age = int(input("Enter your age :"))
if age > 18 :
    print("You are signed up!")
else :
    print("You must be 18+ to signed up!")

# Python Calculator
operator = input("Enter an operator [+ - * /] : ")
num1 = int(input("Enter the 1st number : "))
num2 = int(input("Enter the 2nd number : "))

if operator == "+":
    result = num1 + num2
    print(result)
elif operator == "-":
    result = num1 - num2
    print(result)
elif operator == "*":
    result = num1 * num2
    print(result)
elif operator == "/":
    result = num1/num2
    print(result)
else :
    print("Please enter a valid operator!")
