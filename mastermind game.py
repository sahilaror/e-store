import random

num = random.randrange(1000, 10000)

n = int(input("Guess the 4 digit number :"))

if (n == num):
    print("Great! You Guessed the number in just 1 try! You are a Mastermind!")

else:
    ctr = 0

    while (n != num):
        ctr += 1
        count = 0

        n = str(n)

        num = str(num)

        correct = ['X']*4

        for i in range(0,4):

            if (n[i] == num[i]):
                count += 1
                correct[i] = n[i]

            else:
                continue

            
            print("Not quite the number . But you get" , count, "digit(s) correct!")

            print('\n')
            print('\n')

            n = int(input("Enter Your Choice of Number"))

    if n == num:
        ctr+=1
        print("You have become a Mastermind!")
        print("It took you only" , ctr ,"tries.")
