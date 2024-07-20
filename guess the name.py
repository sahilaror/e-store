import random

name = input("what is your name?")
print("Good Luck!" , name)

words = ['rainbow' , 'computer' , 'science' , 'programming' , 'python' ,'mathematics' ,
         'player' , 'condition' , 'reverse' , 'water' , 'board' , 'geeks']

words = random.choice(words)

print("Guess the Characters")

Guess = ''
turns = 12

while turns > 0:
     failed = 0

     for character in words:
          
          if character in Guess : 
               print(character , end = " ")

          else:
               print("_")

               failed += 1
     if failed == 0:
          
          print("you win")

          print("The Word is : " , words)

          break
     
     print()
     Guess = input("Guess a character : ")

     Guess += Guess

     if Guess not in words : 
          
          turns -= 1
          print("wrong")

          print("you have" , +turns , "more guesses")

          if turns == 0:
               print("you loose")

