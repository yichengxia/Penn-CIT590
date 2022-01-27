# Name: Yicheng Xia
# Penn ID: 65349745
# Statement of work: I worked alone without help.

# import the random module
# use "winnings = random.randint(2, 10)" to generate a random int from 2 - 10 and store in a variable "winnings"
import random

# unit price of a lottery ticket
constant_lottery_unit_price = 2

# unit price of an apple
constant_apple_unit_price = .99

# unit price of a can of beans
constant_canned_beans_unit_price = 1.58

# unit price of a soda
constant_soda_unit_price = 1.23

# the user has initial $5 for shopping
money = 5

# the user has spent $0 initially
money_spent = 0

# the amounts of lottery tickets, apples, cans of beans, and sodas the user has purchased
lottery_amount, apple_amount, canned_beans_amount, soda_amount = 0, 0, 0, 0

# received winnings from lottery tickets
winnings = 0

# print welcome message and item list
print("Welcome to Penn Supermarket! We provide:")
print("- Lottery tickets ($2/ea)")
print("- Apples ($0.99/ea)")
print("- Cans of beans ($1.58/ea)")
print("- Sodas ($1.23/ea)")

print("\nYou have $5 available.")
choose = input("Do you want to use $2 to purchase a lottery ticket a chance at winning $2-$10? (y/n) ")
if choose == 'y' or choose == 'Y':
    lottery_amount = 1
    # deduct the lottery price first
    money -= constant_lottery_unit_price
    # the user wins when the random number is 0 rather than 1 or 2
    # make sure the win probability is 33%
    if random.randint(0, 2) == 0:
        # generate a random integer from 2 - 10 as the prize
        winnings = random.randint(2, 10)
        print("Congratulations! You won ${}! :)".format(winnings))
        # add the prize to the user's money
        money += winnings
    else:
        print("Sorry! You did not win the lottery! :(")
else:
    # regard all other inputs as not purchasing lottery tickets
    print("No lottery tickets were purchased.")

print("\nYou have ${} available.".format(money))
choose = input("Do you want to purchase apple(s)? (y/n) ")
if choose == 'y' or choose == 'Y':
    apple_amount = input("How many apples do you want? ")
    try:
        # try to cast apple_amount to an integer
        apple_amount = int(apple_amount)
    except ValueError:
        # reset apple_amount after casting failure
        apple_amount = 0
        print("Sorry! Only positive integers are accepted! No apples were purchased. :(")
    else:
        # reject negative integers or 0
        if apple_amount < 0:
            # reset apple_amount after positive test failure
            apple_amount = 0
            print("Sorry! Only natural numbers are accepted! No apples were purchased. :(")
        # continue if it has passed casting operation and postive test
        # cast apple_amount to float to multiply it by its price (float type)
        elif money - float(apple_amount) * constant_apple_unit_price >= 0:
            # round cost and money to two decimal places
            cost = round(float(apple_amount * constant_apple_unit_price), 2)
            print("You want {} apple(s). This will cost ${}.".format(apple_amount, cost))
            money = round(money - cost, 2)
            print("You have enough money. {} apple(s) were purchased.".format(apple_amount))
        else:
            # reset apple_amount after purchase failure
            apple_amount = 0
            print("You do not have enough money. No apples were purchased.")
else:
    # regard all other inputs as not purchasing apples
    print("No apples were purchased.")

print("\nYou have ${} available.".format(money))
choose = input("Do you want to purchase can(s) of beans? (y/n) ")
if choose == 'y' or choose == 'Y':
    canned_beans_amount = input("How many cans of beans do you want? ")
    try:
        # try to cast canned_beans_amount to an integer
        canned_beans_amount = int(canned_beans_amount)
        canned_beans_amount > 0
    except ValueError:
        # reset canned_beans_amount after casting failure
        canned_beans_amount = 0
        print("Sorry! Only positive integers are accepted! No cans of beans were purchased. :(")
    else:
        # reject negative integers or 0
        if canned_beans_amount < 0:
            # reset canned_beans_amount after positive test failure
            canned_beans_amount = 0
            print("Sorry! Only natural numbers are accepted! No cans of beans were purchased. :(")
        # continue if it has passed casting operation and postive test
        # cast canned_beans_amount to float to multiply it by its price (float type)
        elif money - float(canned_beans_amount) * constant_canned_beans_unit_price >= 0:
            # round cost and money to two decimal places
            cost = round(float(canned_beans_amount * constant_canned_beans_unit_price), 2)
            print("You want {} can(s) of beans. This will cost ${}.".format(canned_beans_amount, cost))
            money = round(money - cost, 2)
            print("You have enough money. {} can(s) of beans were purchased.".format(canned_beans_amount))
        else:
            # reset canned_beans_amount after purchase failure
            canned_beans_amount = 0
            print("You do not have enough money. No cans of beans were purchased.")
else:
    # regard all other inputs as not purchasing cans of beans
    print("No cans of beans were purchased.")

print("\nYou have ${} available.".format(money))
choose = input("Do you want to purchase soda(s)? (y/n) ")
if choose == 'y' or choose == 'Y':
    soda_amount = input("How many sodas do you want? ")
    try:
        # try to cast soda_amount to an integer
        soda_amount = int(soda_amount)
        soda_amount > 0
    except ValueError:
        # reset soda_amount after casting failure
        soda_amount = 0
        print("Sorry! Only positive integers are accepted! No sodas were purchased. :(")
    else:
        # reject negative integers or 0
        if soda_amount < 0:
            # reset soda_amount after positive test failure
            soda_amount = 0
            print("Sorry! Only natural numbers are accepted! No sodas were purchased. :(")
        # continue if it has passed casting operation and postive test
        # cast soda_amount to float to multiply it by its price (float type)
        elif money - float(soda_amount) * constant_soda_unit_price >= 0:
            # round cost and money to two decimal places
            cost = round(float(soda_amount * constant_soda_unit_price), 2)
            print("You want {} soda(s). This will cost ${}.".format(soda_amount, cost))
            money = round(money - cost, 2)
            print("You have enough money. {} soda(s) were purchased.".format(soda_amount))
        else:
            # reset soda_amount after purchase failure
            soda_amount = 0
            print("You do not have enough money. No sodas were purchased.")
else:
    # regard all other inputs as not purchasing sodas
    print("No sodas were purchased.")

# print goodbye message and shopping list
print("\nPlease take your receipt:")
print("You have ${} left.".format(money))
print("You have purchased:")
print("- Lottery ticket(s): {}".format(lottery_amount))
print("  Lottery winnings: ${}".format(winnings))
print("- Apple(s): {}".format(apple_amount))
print("- Can(s) of beans: {}".format(canned_beans_amount))
print("- Soda(s): {}".format(soda_amount))
print("Have a nice day! :) - Penn Supermarket")
