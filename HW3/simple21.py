# Name: Yicheng Xia
# Penn ID: 65349745
# Statement of work: I worked alone, except for:
# @source https://www.realmoneyaction.com/blackjack/strategy/
# modified for computer strategy in this program

# import the random module
# use "random_int = randint(1, 13)" to generate a random int from 1 - 13 and store in a variable "random_int"
from random import randint
from typing_extensions import runtime


def print_instructions():
    """
    Prints out instructions for the game.
    """
    print("--------- INSTRUCTIONS ---------")
    print("Let's play Simple21 (Blackjack)!")
    print("You'll play against the computer.")
    print("Try to get as close to 21 as possible, without going over.")
    print("--------- GAME STARTS ---------")


def ask_yes_or_no(prompt):
    """
    Displays the given prompt and asks the user for input.  If the user's input starts with 'y', returns True.
    If the user's input starts with 'n', returns False.
    For example, calling ask_yes_or_no("Do you want to play again? (y/n)")
    would display "Do you want to play again? (y/n)", wait for user input that starts with 'y' or 'n',
    and return True or False accordingly.
    """
    # trim leading and trailing whitespace characters
    ans = input(prompt).strip()
    # if prompt is nothing, return the function again
    if len(ans) == 0:
        return ask_yes_or_no(prompt)
    # if prompt starts with 'y' or 'Y', return True
    elif ans[0] == 'y' or ans[0] == 'Y':
        return True
    # if prompt starts with 'y' or 'Y', return False
    elif ans[0] == 'n' or ans[0] == 'N':
        return False
    # if prompt is other unacceptable characters, return the function again
    else:
        return ask_yes_or_no(prompt)


def next_card():
    """
    Returns a random "card", represented by an int between 1 and 10, inclusive.
    The "cards" are the numbers 1 through 10 and they are randomly generated, not drawn from a deck of
    limited size.  The odds of returning a 10 are four times as likely as any other value (because in an
    actual deck of cards, 10, Jack, Queen, and King all count as 10).
    """
    # generate a random integer from 1 to 13
    card = randint(1, 13)
    # regard random number >= 10 as 10
    if card >= 10:
        return 10
    # return other random number in their original value
    else:
        return card


# computer strategy modified from:
# @source https://www.realmoneyaction.com/blackjack/strategy/
def take_another_card(computer_total_points, user_visible_card):
    """
    Strategy for computer to take another card or not.  According to the computer’s own given
    total points (sum of visible cards + hidden card) and the user's sum of visible cards, you
    need to design a game strategy for the computer to win the game.
    Returns True if the strategy decides to take another card, False if the computer decides not
    to take another card.
    """
    # stand if points >= 17
    if computer_total_points >= 17:
        return False
    # stand if 13 <= points <= 16 and user shows 2 to 6
    elif 13 <= computer_total_points <= 16 and 2 <= user_visible_card <= 6:
        return False
    # stand if points == 12 and user shows 4 to 6
    elif computer_total_points == 12 and 4 <= user_visible_card <= 6:
        return False
    # hit in all other situations
    else:
        return True


def is_game_over(is_user_passed, is_computer_passed):
    """
    Determines if the game is over or not.
    If the given is_user_passed is set to True, the user has passed.
    If the given is_computer_passed is set to True, the computer has passed.
    This function returns True if both the user and the computer have passed,
    and False if either of them has not yet passed.
    """
    # game over (return True) only if both user and computer passed
    # otherwise return False
    return is_user_passed and is_computer_passed


def print_status(is_user, name, hidden_card, visible_card, total_points):
    """
    In each turn, prints out the current status of the game.
    If is_user is set to True, the given player is the user.  In this case, print out
    the user's given name, his/her hidden card points, visible card points, and total points.
    If is_user is set to False, the given player is the computer.  In this case, print out
    the computer's given name, and his/her visible card points.

    For example, calling print_status(True, "Brandon", 4, 15, 19) would print:
    Brandon has 4 hidden point(s).
    Brandon has 15 visible point(s).
    Brandon has 19 total point(s).

    As another example, calling print_status(False, "Computer", 1, 19, 20) would print:
    Computer has 19 visible point(s).
    """
    # is_user is True means user, so print hidden_card, visible_card and total_points
    if is_user:
        print("{} has {} hidden point(s).".format(name, hidden_card))
        print("{} has {} visible point(s).".format(name, visible_card))
        print("{} has {} total point(s).".format(name, total_points))
    # is_user is False means computer, so print visible_card only
    else:
        print("{} has {} visible point(s).".format(name, visible_card))


def print_winner(username, user_total_points, computer_name, computer_total_points):
    """
    Determines who won the game and prints the game results in the following format:
    - User's given name and the given user's total points
    - Computer's given name and the given computer's total points
    - The player who won the game and the total number of points he/she won by, or if it's a tie, nobody won.
    """
    print("---------- GAME OVER ----------")
    # case 1: both > 21, both lose
    if user_total_points > 21 and computer_total_points > 21:
        print("{} has {} points in total.\n{} has {} points in total.".format(username, user_total_points, computer_name, computer_total_points))
        print("Neither side wins! Both lose! :(")
    # case 2: only user_total_points > 21, computer wins
    elif user_total_points > 21:
        print("{} has {} points in total.\n{} has {} points in total.".format(username, user_total_points, computer_name, computer_total_points))
        print("{} wins by {} point(s)! {} busts! :)".format(computer_name, user_total_points - computer_total_points, username))
    # case 3: only computer_total_points > 21, user wins
    elif computer_total_points > 21:
        print("{} has {} points in total.\n{} has {} points in total.".format(username, user_total_points, computer_name, computer_total_points))
        print("{} wins by {} point(s)! {} busts! :)".format(username, computer_total_points - user_total_points, computer_name))
    # case 4: both <= 21, but user_total_points > computer_total_points, user wins
    elif user_total_points > computer_total_points:
        print("{} has {} points in total.\n{} has {} points in total.".format(username, user_total_points, computer_name, computer_total_points))
        print("{} wins by {} point(s)! :)".format(username, user_total_points - computer_total_points))
    # case 5: both <= 21, but user_total_points < computer_total_points, computer wins
    elif user_total_points < computer_total_points:
        print("{} has {} points in total.\n{} has {} points in total.".format(username, user_total_points, computer_name, computer_total_points))
        print("{} wins by {} point(s)! :)".format(computer_name, computer_total_points - user_total_points))
    # case 6: both <= 21, but user_total_points == computer_total_points, a draw
    else:
        print("{} has {} points in total.\n{} has {} points in total.".format(username, user_total_points, computer_name, computer_total_points))
        print("Neither side wins! It is a tie! :(")


def run(username, computer_name):
    """
    This function controls the overall game and logic for the given user and computer.
    """
    # mark if user passed
    is_user_passed = False
    # mark if computer passed
    is_computer_passed = False

    # call next_card() to generate one hidden and one visible card for user
    user_hidden_card = next_card()
    user_visible_card = next_card()
    # calculate total point for user
    user_total_points = user_hidden_card + user_visible_card
    # print initial status for user
    print_status(True, username, user_hidden_card, user_visible_card, user_total_points)
    # call next_card() to generate one hidden and one visible card for computer
    computer_hidden_card = next_card()
    computer_visible_card = next_card()
    # calculate total point for computer
    computer_total_points = computer_hidden_card + computer_visible_card
    # print initial status for computer
    print_status(False, computer_name, computer_hidden_card, computer_visible_card, computer_total_points)

    # call is_game_over(is_user_passed, is_computer_passed) to start game loop
    while not is_game_over(is_user_passed, is_computer_passed):
        if not is_user_passed:
            if ask_yes_or_no("Do you want to take another card? (y/n) "):
                # generate another card for user
                user_get_card = next_card()
                # update user_visible_card and user_total_points
                user_visible_card += user_get_card
                user_total_points += user_get_card
                # print updated status for user
                print("{} gets {} point(s)!".format(username, user_get_card))
                print_status(True, username, user_hidden_card, user_visible_card, user_total_points)
            # set and print user passed
            else:
                is_user_passed = True
                print("{} passed!".format(username))        
        if not is_computer_passed:
            # call take_another_card(computer_total_points, user_visible_card) to decide for computer
            if take_another_card(computer_total_points, user_visible_card):
                # generate another card for computer
                computer_get_card = next_card()
                # update computer_visible_card and computer_total_points
                computer_visible_card += computer_get_card
                computer_total_points += computer_get_card
                # print updated status for computer
                print("{} gets {} point(s)!".format(computer_name, computer_get_card))
                print_status(False, computer_name, computer_hidden_card, computer_visible_card, computer_total_points)
            # set and print computer passed
            else:
                is_computer_passed = True
                print("{} passed!".format(computer_name))

    # print winner after both passed
    print_winner(username, user_total_points, computer_name, computer_total_points)

    # ask user if he/she wants to play the game again
    if ask_yes_or_no("Do you want to play again? (y/n) "):
        # ask user if he/she wants to modify username
        if ask_yes_or_no("Do you want to modify username? (y/n) "):
            # ask user the updated username
            username = input("Update your name: ")
        # run run(username, computer_name) function again
        run(username, computer_name)
    # exit game if user do not want to play again
    else:
        print("------------- EXIT -------------")


def main():
    """
    Main Function.
    """

    # print the game instructions
    print_instructions()

    # get and set user's name
    username = input("What's your name?\r\n")

    # set computer's name
    computer_name = "Computer"

    # insert the rest of the code in the main function here
    run(username, computer_name)


if __name__ == '__main__':
    main()
